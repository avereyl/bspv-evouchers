package org.bspv.evoucher.core.business;

import java.util.UUID;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.EVoucher.EVoucherStatus;
import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.repository.EVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 
 *
 */
@Service
public class EVoucherBusinessService {

	/**
	 * Repository handling {@link EVoucher}s.
	 */
	@Autowired
	private EVoucherRepository eVoucherRepository;

	/**
	 * Save the given e-voucher.
	 * 
	 * @param eVoucher
	 *            The e-voucher to save
	 * @return the saved e-voucher
	 */
	public EVoucher save(final EVoucher eVoucher) {
		EVoucher savedEVoucher;
		if (eVoucher.isNew()) {
			savedEVoucher = this.eVoucherRepository.insert(eVoucher);
		} else {
			savedEVoucher = this.eVoucherRepository.update(eVoucher);
		}
		return savedEVoucher;
	}

	/**
	 * Save a new e-voucher.
	 * 
	 * @param eVoucher
	 *            The e-voucher to save
	 * @return the saved e-voucher
	 */
	public EVoucher saveNewEVoucher(final EVoucher eVoucher) {
		if (!eVoucher.isNew()) {
			throw new IllegalArgumentException("Only new eVoucher (with version 0L) can be created here.");
		}
		return this.eVoucherRepository.insertWithSilentError(eVoucher);
	}

	/**
	 * delete the given e-voucher. (soft deletion)
	 * 
	 * @param eVoucher
	 *            e-voucher to delete
	 */
	public void deleteEVoucher(EVoucher eVoucher) {
		eVoucherRepository.softlyDeleteEVoucher(eVoucher);
	}

	/**
	 * archive the given e-voucher.
	 * 
	 * @param eVoucher
	 *            e-voucher to archive
	 * @return the archived e-voucher
	 */
	public EVoucher archiveEVoucher(EVoucher eVoucher) {
		return eVoucherRepository.archiveEVoucher(eVoucher);
	}

	/**
	 * Try to find a valid e-voucher with the given uuid. Return null if not found.
	 * 
	 * @see EVoucherStatus#ACTIVE
	 * 
	 * @param uuid
	 *            uuid of the e-voucher
	 * @return an {@link EVoucher} or null.
	 */
	public EVoucher findEVoucherById(UUID uuid) {
		return this.eVoucherRepository.findEVoucherById(uuid);
	}

	/**
	 * Find all valid e-vouchers (paginated)
	 * 
	 * @see EVoucherStatus#ACTIVE
	 * @param pageable
	 *            The pageable
	 * @return a page of valid {@link EVoucher}s
	 */
	public Page<EVoucher> findEVouchers(Pageable pageable) {
		return this.eVoucherRepository.findAllEVouchers(pageable);
	}

	/**
	 * Find all valid e-vouchers issued by the given team (paginated)
	 * 
	 * @see EVoucherStatus#ACTIVE
	 * @param pageable
	 *            The pageable
	 * @param team
	 * @return a page of valid {@link EVoucher}s
	 */
	public Page<EVoucher> findEVouchers(Team team, Pageable pageable) {
		return this.eVoucherRepository.findAllEVouchersByTeam(team, pageable);
	}

}
