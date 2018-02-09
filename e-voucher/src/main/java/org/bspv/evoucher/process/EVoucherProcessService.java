/**
 * 
 */
package org.bspv.evoucher.process;

import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.ACK;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.ARCHIVING;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.CANCELLATION;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.CREATION;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.DISPATCH;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.ERROR;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.NO_OPERATION;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.PRINTING;
import static org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType.UPDATE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bspv.evoucher.core.business.EVoucherBusinessService;
import org.bspv.evoucher.core.business.EVoucherEventBusinessService;
import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.EVoucher.EVoucherStatus;
import org.bspv.evoucher.core.model.EVoucherEvent;
import org.bspv.evoucher.core.model.EVoucherEvent.EVoucherEventType;
import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.evoucher.tech.MailingService;
import org.bspv.evoucher.tech.PrintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

/**
 * Facade handling actions on eVouchers and related events.
 */
@Slf4j
@Service
public class EVoucherProcessService {

    /**
     * Business service handling e-vouchers.
     */
    @Autowired
    private EVoucherBusinessService eVoucherBusinessService;
    /**
     * Business service handling e-voucher events.
     */
    @Autowired
    private EVoucherEventBusinessService eVoucherEventBusinessService;

    /**
     * Printing service.
     */
    @Autowired
    private PrintingService printingService;

    /**
     * Mailing service.
     */
    @Autowired
    private MailingService mailingService;

    /**
     * Return a {@link Page} of e-vouchers.
     * 
     * @param pageable
     *            page request
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<EVoucher> findEVouchers(Pageable pageable) {
        return eVoucherBusinessService.findEVouchers(pageable);
    }

    /**
     * Return a {@link Page} of e-vouchers issued by the given team.
     * 
     * @param pageable
     *            page request
     * @param team
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or principal.team == team ")
    public Page<EVoucher> findEVouchers(Team team, Pageable pageable) {
        return eVoucherBusinessService.findEVouchers(team, pageable);
    }

    /**
     * Find the eVoucher with the given uuid.
     * 
     * @param uuid
     *            uuid of the voucher to look for
     * @return An eVoucher or null if no eVoucher exists with the given uuid
     */
    @Transactional
    @PostAuthorize("hasAuthority('ADMIN') or principal.team == returnObject.team ")
    public EVoucher findEVoucher(UUID uuid) {
        return eVoucherBusinessService.findEVoucherById(uuid);
    }

    /**
     * Find eVoucher events related to the eVoucher with given uuid
     * 
     * @param uuid
     *            uuid of the voucher
     * @return List of {@link EVoucherEvent}
     */
    @Transactional
    @PostAuthorize("hasAuthority('ADMIN') or principal.team == returnObject.team ")
    public List<EVoucherEvent> findEVoucherEvents(UUID uuid) {
        List<EVoucherEvent> events = new ArrayList<>();
        events.addAll(eVoucherEventBusinessService.findEventsByEVoucherUUID(uuid));
        return events;
    }

    /**
     * Save an eVoucher. (Status is not change by this method)
     * 
     * @param eVoucher
     *            The eVoucher to be saved
     * @return the save eVoucher
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or principal.team == eVoucher.team ")
    public EVoucher saveEVoucher(EVoucher eVoucher, User user) {
        UUID auditor = user.getId();
        EVoucher savedEVoucher = eVoucherBusinessService.save(eVoucher);
        EVoucherEventType eventType = (savedEVoucher.getVersion() == 1) ? CREATION : UPDATE;
        EVoucherEvent event = EVoucherEvent.builderFor(savedEVoucher.getId()).createdBy(auditor).withKey(eventType)
                .build();
        eVoucherEventBusinessService.save(event);
        return savedEVoucher;
    }

    /**
     * Delete the eVoucher with given uuid.
     * 
     * @param uuid
     *            uuid of the eVoucher to delete
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void cancelEVoucher(UUID uuid, User user) {
        UUID auditor = user.getId();
        try {
            EVoucher eVoucher = eVoucherBusinessService.findEVoucherById(uuid);
            if (eVoucher != null) {
                eVoucherBusinessService.deleteEVoucher(eVoucher);
                EVoucherEvent event = EVoucherEvent.builderFor(uuid).createdBy(auditor).withKey(CANCELLATION).build();
                eVoucherEventBusinessService.save(event);
            }
        } catch (OptimisticLockingFailureException e) {
            log.info("The voucher {} is already deleted or a concurrent access occurs.", uuid);
            throw e;
        }
    }

    /**
     * Archive the eVoucher with given uuid.
     * 
     * @param uuid
     *            uuid of the eVoucher to delete
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public void archiveEVoucher(UUID uuid, User user) {
        UUID auditor = user.getId();
        try {
            EVoucher eVoucher = eVoucherBusinessService.findEVoucherById(uuid);
            if (eVoucher != null && EVoucherStatus.ACTIVE.equals(eVoucher.getStatus())) {
                eVoucherBusinessService.archiveEVoucher(eVoucher);
                EVoucherEvent event = EVoucherEvent.builderFor(uuid).createdBy(auditor).withKey(ARCHIVING).build();
                eVoucherEventBusinessService.save(event);
            }
        } catch (OptimisticLockingFailureException e) {
            log.info("The voucher {} is already deleted or a concurrent access occurs.", uuid);
            throw e;
        }
    }

    /**
     * This method process an e-voucher: it is saved, printed and sent.
     * 
     * @param eVoucher
     *            The e-voucher to process
     * @return An {@link Observable} of {@link EVoucherEvent}
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or principal.team == eVoucher.team ")
    public Observable<EVoucherEvent> processEVoucher(final EVoucher eVoucher) {
        UUID eVoucherUUID = eVoucher.getId();
        UUID auditor = eVoucher.getCreatedBy();
        log.info("Processing eVoucher {} for team {}", eVoucherUUID, eVoucher.getTeam());
        return Observable.create(new OnSubscribe<EVoucherEvent>() {
            /* (non-Javadoc) @see rx.functions.Action1#call(java.lang.Object) */
            @Override
            public void call(Subscriber<? super EVoucherEvent> subscriber) {
                EVoucherEvent event = EVoucherEvent.builderFor(eVoucherUUID).withKey(ACK).createdBy(auditor).build();
                try {
                    // acknowledgement
                    subscriber.onNext(event);

                    // registration
                    final EVoucher workingEVoucher = eVoucherBusinessService.saveNewEVoucher(eVoucher);
                    if (workingEVoucher.getVersion() != 1L) {
                        // eVoucher already submitting and so ignored
                        event = EVoucherEvent.builderBasedOn(event).withKey(NO_OPERATION).build();
                        subscriber.onNext(event);
                    } else {
                        // registration ok...
                        event = EVoucherEvent.builderBasedOn(event).withKey(CREATION).build();
                        event = eVoucherEventBusinessService.save(event);
                        subscriber.onNext(event);

                        // printing
                        ByteArrayOutputStream baos = printingService.printOriginalEVoucher(workingEVoucher);
                        event = EVoucherEvent.builderBasedOn(event).withKey(PRINTING).build();
                        event = eVoucherEventBusinessService.save(event);
                        subscriber.onNext(event);

                        // mailing
                        mailingService.sendEVoucherPrint(workingEVoucher, baos);
                        event = EVoucherEvent.builderBasedOn(event).withKey(DISPATCH).build();
                        event = eVoucherEventBusinessService.save(event);
                        subscriber.onNext(event);
                    }
                    // end of process
                    subscriber.onCompleted();
                } catch (Exception e) {
                    log.error("Error occcurred when processing the e-voucher.", e);
                    event = EVoucherEvent.builderBasedOn(event).withKey(ERROR).build();
                    subscriber.onNext(event);
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * 
     * @param eVoucher
     * @return
     */
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Observable<EVoucherEvent> printAndSendEVoucher(UUID uuid, User user) {
        UUID auditor = user.getId();
        return Observable.create(new OnSubscribe<EVoucherEvent>() {
            /* (non-Javadoc) @see rx.functions.Action1#call(java.lang.Object) */
            @Override
            public void call(Subscriber<? super EVoucherEvent> subscriber) {
                EVoucherEvent event = EVoucherEvent.builderFor(uuid).withKey(ACK).createdBy(auditor).build();
                try {
                    // acknowledgement
                    subscriber.onNext(event);

                    // reading of the e-voucher
                    final EVoucher workingEVoucher = eVoucherBusinessService.findEVoucherById(uuid);
                    if (workingEVoucher == null) {
                        // eVoucher not existing (or not valid)
                        event = EVoucherEvent.builderBasedOn(event).withKey(ERROR).build();
                        subscriber.onNext(event);
                    } else {
                        // printing
                        ByteArrayOutputStream baos = printingService.printDuplicataEVoucher(workingEVoucher);
                        event = EVoucherEvent.builderBasedOn(event).withKey(PRINTING).build();
                        event = eVoucherEventBusinessService.save(event);
                        subscriber.onNext(event);

                        // mailing
                        mailingService.sendEVoucherPrint(workingEVoucher, baos);
                        event = EVoucherEvent.builderBasedOn(event).withKey(DISPATCH).build();
                        event = eVoucherEventBusinessService.save(event);
                        subscriber.onNext(event);
                    }
                    // end of process
                    subscriber.onCompleted();
                } catch (Exception e) {
                    log.error("Error occcurred when processing the e-voucher.", e);
                    event = EVoucherEvent.builderBasedOn(event).withKey(ERROR).build();
                    subscriber.onNext(event);
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Print the eVoucher and save a printing event for the eVoucher with the given id.
     * 
     * @param uuid The id of the eVoucher to print.
     * @param user User asking for the print.
     * @return The print of the eVoucher with given id
     * @throws IOException In case of i/o exception
     */
    @Transactional
    public ByteArrayOutputStream printEvoucher(UUID uuid, User user) throws IOException {
        UUID auditor = user.getId();
        ByteArrayOutputStream baos = null;
        final EVoucher workingEVoucher = eVoucherBusinessService.findEVoucherById(uuid);
        if (workingEVoucher == null) {
            // eVoucher not existing (or not valid)
            return baos;
        } else if (!user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))
                && !user.getTeam().equals(workingEVoucher.getTeam())) {
            // user not authorized (nor admin neither in the relevant team)
            throw new AccessDeniedException("Access denied to print of eVoucher " + uuid.toString());
        } else {
            // printing
            baos = printingService.printDuplicataEVoucher(workingEVoucher);
            EVoucherEvent event = EVoucherEvent.builderFor(uuid).withKey(PRINTING).createdBy(auditor).build();
            eVoucherEventBusinessService.save(event);
        }
        return baos;
    }

}
