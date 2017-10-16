package org.bspv.evoucher.repository;

import java.util.List;
import java.util.UUID;

import org.bspv.evoucher.core.model.EVoucherEvent;

/**
 * 
 *
 */
public interface EVoucherEventRepository {

	/**
	 * Retrieves events by e-voucher uuid.
	 * 
	 * @param uuid
	 *            uuid of the e-voucher
	 * @return list of events
	 */
	List<EVoucherEvent> findEventsByEVoucherUuid(UUID uuid);

	/**
	 * Save a new event in the database
	 * 
	 * @param event The event to be saved
	 * @return The newly created e-voucher event.
	 */
	EVoucherEvent insert(EVoucherEvent event);

	/**
	 * Retrieves an event by its identifier.
	 * 
	 * @param id
	 *            identifier of the event to look for
	 * @return the e-voucher event
	 */
	EVoucherEvent findEventById(Long id);

}
