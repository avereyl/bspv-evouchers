/**
 * 
 */
package org.bspv.evoucher.core.business;

import java.util.List;
import java.util.UUID;

import org.bspv.evoucher.core.model.EVoucherEvent;
import org.bspv.evoucher.repository.EVoucherEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class EVoucherEventBusinessService {

	/**
	 * Repository handling eVouchers events.
	 */
	@Autowired
	private EVoucherEventRepository eVoucherEventRepository;

	/**
	 * Return the list of events related to the e-voucher whose uuid is in
	 * parameter.
	 * 
	 * @param uuid
	 *            uuid of the evoucher
	 * @return list of events
	 */
	public List<EVoucherEvent> findEventsByEVoucherUUID(UUID uuid) {
		return this.eVoucherEventRepository.findEventsByEVoucherUuid(uuid);
	}

	/**
	 * Save an event.
	 * 
	 * @param event
	 *            event to save
	 * @return the saved event
	 */
	public EVoucherEvent save(EVoucherEvent event) {
		return this.eVoucherEventRepository.insert(event);
	}

}
