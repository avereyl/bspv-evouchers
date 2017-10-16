/**
 * 
 */
package org.bspv.evoucher.repository;

import java.util.UUID;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository handling eVouchers.
 */
public interface EVoucherRepository {

	/**
	 * Insert a new eVoucher, raises an exception if an eVoucher with same identifier already exists.
	 * 
	 * @param eVoucher
	 *            The eVoucher to be inserted
	 * @return The newly created eVoucher
	 */
	EVoucher insert(EVoucher eVoucher);

	/**
	 * Insert a new eVoucher, if an eVoucher with same identifier already exists then increment its version.
	 * 
	 * @param eVoucher
	 *            The eVoucher to be inserted
	 * @return The newly created eVoucher
	 */
	EVoucher insertWithSilentError(EVoucher eVoucher);

	/**
	 * Update e-voucher values, not its status !
	 * 
	 * @param eVoucher
	 * @return the updated eVoucher
	 */
	EVoucher update(EVoucher eVoucher);

	/**
	 * Find all (not deleted) eVouchers according given pagination request.
	 * @param pageable Pagination request
	 * @return A {@link Page} of eVouchers.
	 */
	Page<EVoucher> findAllEVouchers(Pageable pageable);

	/**
	 * Find all (not deleted) eVouchers issued by a team according given pagination request.
	 * @param pageable Pagination request
	 * @param team The team
	 * @return A {@link Page} of eVouchers.
	 */
	Page<EVoucher> findAllEVouchersByTeam(Team team, Pageable pageable);

	/**
	 * Find all (deleted and not deleted) eVouchers according given pagination request.
	 * @param pageable Pagination request
	 * @return A {@link Page} of eVouchers.
	 */
	Page<EVoucher> findAnyEVouchers(Pageable pageable);

	/**
	 * Softly delete the given eVoucher.
	 * @param eVoucher The eVoucher to delete
	 */
	void softlyDeleteEVoucher(EVoucher eVoucher);

	/**
	 * Archive the given eVoucher.
	 * @param eVoucher The eVoucher to archive
	 * @return the updated eVoucher
	 */
	EVoucher archiveEVoucher(EVoucher eVoucher);

	/**
	 * Find the eVoucher (not deleted) with the given id.
	 * @param uuid id of the eVoucher
	 * @return The eVoucher
	 */
	EVoucher findEVoucherById(UUID uuid);

	/**
	 *  Find the eVoucher (deleted and not deleted) with the given id.
	 * @param uuid id of the eVoucher
	 * @return The eVoucher
	 */
	EVoucher findAnyEVoucherById(UUID uuid);

}
