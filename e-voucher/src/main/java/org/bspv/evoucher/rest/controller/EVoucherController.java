/**
 * 
 */
package org.bspv.evoucher.rest.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.EVoucherEvent;
import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.process.EVoucherProcessService;
import org.bspv.evoucher.rest.beans.EVoucherBean;
import org.bspv.evoucher.rest.controller.exception.NotFoundException;
import org.bspv.evoucher.rest.controller.helper.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 *
 */
@RestController("/")
@RequestMapping(produces = "application/json")
public class EVoucherController {

    /**
     * Service handling e-vouchers.
     */
    @Autowired
    private EVoucherProcessService eVoucherProcessService;

    /**
     */
    @GetMapping("")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Save the eVoucher, print it and send it by mail. A stream of {@link EVoucherEvent} is sent back to the client.
     * 
     * @param eVoucherBean
     *            The eVoucher to process.
     * @param user
     *            The user asking to process the e-voucher.
     */
    @PostMapping("eVouchers/")
    public ResponseBodyEmitter processEVoucher(@RequestBody @Validated EVoucherBean eVoucherBean,
            @AuthenticationPrincipal User user) {
        SseEmitter emitter = new SseEmitter();
        Integer year = eVoucherBean.getDistributionYear();
        Integer number = eVoucherBean.getTeamNumber();
        year = (year == null || year <= 0) ? user.getTeam().getYear() : year;
        number = (number == null || number <= 0) ? user.getTeam().getNumber() : number;
        Team team = Team.builder().forYear(year).withNumber(number).build();
//		@formatter:off
 		EVoucher eVoucher = EVoucher.builder()
				.withId(UUID.fromString(eVoucherBean.getUuid()))
				.withVersion(0L) //e-voucher can only be processed once, use of optimistic locking to guarantee it. 
				.withName(eVoucherBean.getName())
				.withAmount(new BigDecimal(eVoucherBean.getAmount()))
				.withEmail(eVoucherBean.getEmail())
				.requestDate(eVoucherBean.getRequestDate())
				.createdBy(user.getId())
				.createdDate(LocalDateTime.now())
				.withTeam(team)
				.build();
//		@formatter:on
        // get the event stream from the process service handling the e-voucher
        Observable<EVoucherEvent> eventStream = eVoucherProcessService.processEVoucher(eVoucher);
        // subscribe to this observable and act on change or error.
//		@formatter:off
		eventStream.subscribeOn(Schedulers.io())
			.subscribe(
					event -> notifyProgress(emitter, event),
					emitter::completeWithError,
					emitter::complete);
//		@formatter:on
        return emitter;
    }

    /**
     * Return the eVoucher with the given uuid.
     * 
     * @param uuid
     *            id of the targeted voucher
     * @return The latest status
     */
    @GetMapping("eVouchers/{uuid}")
    public ResponseEntity<EVoucher> readEVoucher(@PathVariable("uuid") UUID uuid) {
        EVoucher eVoucher = this.eVoucherProcessService.findEVoucher(uuid);
        return (eVoucher == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(eVoucher, HttpStatus.OK);
    }

    /**
     * Return the list of eVoucher events for the given uuid.
     * 
     * @param uuid
     *            id of the targeted voucher
     * @return The list of events
     */
    @GetMapping("eVouchers/{uuid}/events")
    public ResponseEntity<List<EVoucherEvent>> readEVoucherEvents(@PathVariable(value = "uuid") UUID uuid) {
        return ResponseEntity.ok(this.eVoucherProcessService.findEVoucherEvents(uuid));
    }

    /**
     * Return a page of eVouchers. E-vouchers are filtered by user's team.
     * 
     * @return Page of e-vouchers.
     */
    @GetMapping(value = "eVouchers/")
    public ResponseEntity<Page<EVoucher>> readEVouchers(@RequestParam(value = "range", required = false) String range,
            @AuthenticationPrincipal(errorOnInvalidType = true) User user) {
        PageRequest pageRequest = PaginationHelper.rangeToPageRequest(range);
        Page<EVoucher> page = this.eVoucherProcessService.findEVouchers(user.getTeam(), pageRequest);
        // handle exceptions : eg range unsatisfiable...
        return ResponseEntity.ok(page);
    }

    /**
     * Save the eVoucher. {@link EVoucher} is sent back to the client.
     * 
     * @param eVoucher
     *            The eVoucher to process.
     * @param principal
     *            The principal responsible for the request.
     */
    @PutMapping("eVouchers/")
    public ResponseEntity<EVoucher> saveEVoucher(@RequestBody @Validated EVoucherBean eVoucherBean,
            @AuthenticationPrincipal User user) {
//		@formatter:off
	    boolean isNew = eVoucherBean.getVersion() == null || eVoucherBean.getVersion() == 0;
	    Integer year = eVoucherBean.getDistributionYear();
        Integer number = eVoucherBean.getTeamNumber();
        year = (year == null || year <= 0) ? user.getTeam().getYear() : year;
        number = (number == null || number <= 0) ? user.getTeam().getNumber() : number;
        Team team = Team.builder().forYear(year).withNumber(number).build();
		EVoucher eVoucher = EVoucher.builder()
				.withId(UUID.fromString(eVoucherBean.getUuid()))
				.withVersion(eVoucherBean.getVersion())
				.withName(eVoucherBean.getName())
				.withAmount(new BigDecimal(eVoucherBean.getAmount()))
				.withEmail(eVoucherBean.getEmail())
				.requestDate(eVoucherBean.getRequestDate())
				.createdBy(isNew ? user.getId() : null)
				.createdDate(isNew ? LocalDateTime.now() : null)
				.lastModifiedBy(isNew ? null : user.getId())
				.lastModifiedDate(isNew ? null : LocalDateTime.now())
				.withTeam(team)
				.build();
//		@formatter:on
        // save the e-voucher
        eVoucher = eVoucherProcessService.saveEVoucher(eVoucher, user);
        // TODO handle errors...
        return new ResponseEntity<>(eVoucher, HttpStatus.OK);
    }

    /**
     * Cancel the eVoucher with the given uuid. (soft deletion)
     * 
     * @param uuid
     *            id of the targeted voucher
     * @return No content
     */
    @DeleteMapping("eVouchers/{uuid}")
    public ResponseEntity<EVoucher> cancelEVoucher(@PathVariable(value = "uuid") UUID uuid,
            @AuthenticationPrincipal User user) {
        this.eVoucherProcessService.cancelEVoucher(uuid, user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Print and send the eVoucher with the given uuid. This eVoucher is supposed to exist already.
     * 
     * @param uuid
     *            id of the voucher to print
     * @return The latest status
     */
    @PostMapping("eVouchers/{uuid}/dispatch")
    public ResponseBodyEmitter printAndSendEVoucher(@PathVariable(value = "uuid") UUID uuid,
            @AuthenticationPrincipal User user) {
        SseEmitter emitter = new SseEmitter();

        Observable<EVoucherEvent> eventStream = eVoucherProcessService.printAndSendEVoucher(uuid, user);
        // subscribe to this observable and act on change or error.
        eventStream.subscribeOn(Schedulers.io())
//		@formatter:off
			.subscribe(
					event -> notifyProgress(emitter, event),
					emitter::completeWithError,
					emitter::complete);
//		@formatter:on
        return emitter;
    }

    /**
     * Get eVoucher print.
     * 
     * @param uuid
     *            id of the eVoucher to print.
     * @param user
     *            user asking for the print.
     * @return The print as a PDF
     * @throws IOException
     */
    @GetMapping(value = "eVouchers/{uuid}/print")
    public ResponseEntity<ByteArrayResource> print(@PathVariable(value = "uuid") UUID uuid,
            @AuthenticationPrincipal User user) throws IOException {
        ByteArrayOutputStream baos = eVoucherProcessService.printEvoucher(uuid, user);
        if (baos == null) {
            // 404
            throw new NotFoundException();
        }
        ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_PDF);
        respHeaders.setContentLength(resource.contentLength());
        respHeaders.set("Content-disposition", "inline; filename=" + uuid.toString() + ".pdf");
        return ResponseEntity.ok().headers(respHeaders).body(resource);
    }

    /**
     * Method to send an {@link EVoucherEvent} through the given {@link SseEmitter}.
     * 
     * @param emitter
     *            the SSE emitter
     * @param event
     *            The event to send
     */
    private void notifyProgress(SseEmitter emitter, EVoucherEvent event) {
        try {
            emitter.send(event);
        } catch (IOException ex) {
            emitter.completeWithError(ex);
        }
    }

}
