/**
 * 
 */
package org.bspv.evoucher.repository.jooq;

import static org.bspv.evouchers.jooq.tables.Evouchers.EVOUCHERS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bspv.evoucher.core.model.EVoucher;
import org.bspv.evoucher.core.model.EVoucher.EVoucherStatus;
import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.repository.EVoucherRepository;
import org.bspv.evoucher.repository.jooq.converter.RecordConverterFactory;
import org.bspv.evouchers.jooq.tables.records.EvouchersRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository
@Transactional
public class EVoucherJooqRepository implements EVoucherRepository {

	/**
	 * Jooq DSL context.
	 */
	private final DSLContext dslContext;

	/**
	 * Constructor.
	 * @param dslContext
	 */
	public EVoucherJooqRepository(DSLContext dslContext) {
		super();
		this.dslContext = dslContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#insert(org.bspv.evoucher.core.model.EVoucher)
	 */
	@Override
	public EVoucher insert(EVoucher eVoucher) {
		EvouchersRecord record = this.dslContext
		.insertInto(EVOUCHERS)
		.columns(EVOUCHERS.ID,
				 EVOUCHERS.CREATED_BY,
				 EVOUCHERS.CREATED_DATE,
				 EVOUCHERS.NAME,
				 EVOUCHERS.EMAIL,
				 EVOUCHERS.REQUEST_DATE,
				 EVOUCHERS.AMOUNT,
				 EVOUCHERS.STATUS,
				 EVOUCHERS.TEAM_NUMBER,
				 EVOUCHERS.DISTRIBUTION_YEAR)
			.values(eVoucher.getId(), 
					eVoucher.getCreatedBy(),
					eVoucher.getCreatedDate(), 
					eVoucher.getName(),
					eVoucher.getEmail(),
					eVoucher.getRequestDate(), 
					eVoucher.getAmount(),
					EVoucherStatus.ACTIVE.name(),
					eVoucher.getTeam().getNumber(),
					eVoucher.getTeam().getYear())
			.returning()
			.fetchOne();
		return RecordConverterFactory.convert(record);
	}


	/* (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#insertWithSilentError(org.bspv.evoucher.core.model.EVoucher)
	 */
	@Override
	public EVoucher insertWithSilentError(EVoucher eVoucher) {
		try {
			this.dslContext
				.insertInto(EVOUCHERS)
				.columns(EVOUCHERS.ID,
						 EVOUCHERS.CREATED_BY,
						 EVOUCHERS.CREATED_DATE,
						 EVOUCHERS.NAME,
						 EVOUCHERS.EMAIL,
						 EVOUCHERS.REQUEST_DATE,
						 EVOUCHERS.AMOUNT,
						 EVOUCHERS.STATUS,
						 EVOUCHERS.TEAM_NUMBER,
						 EVOUCHERS.DISTRIBUTION_YEAR)
					.values(eVoucher.getId(), 
							eVoucher.getCreatedBy(),
							eVoucher.getCreatedDate(), 
							eVoucher.getName(),
							eVoucher.getEmail(),
							eVoucher.getRequestDate(), 
							eVoucher.getAmount(),
							EVoucherStatus.ACTIVE.name(),
							eVoucher.getTeam().getNumber(),
							eVoucher.getTeam().getYear())
			.execute();
		} catch (DuplicateKeyException e) {
			this.dslContext
			.update(EVOUCHERS)
			.set(EVOUCHERS.VERSION, EVOUCHERS.VERSION.add(1))
			.where(EVOUCHERS.ID.eq(eVoucher.getId()))
			.execute();
		} finally {
			EvouchersRecord record = this.dslContext
			.selectFrom(EVOUCHERS)
			.where(EVOUCHERS.ID.eq(eVoucher.getId()))
			.fetchOne();
			eVoucher = record == null ? null : RecordConverterFactory.convert(record);
		}
		return eVoucher;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#update(org.bspv.evoucher.core.model.EVoucher)
	 */
	@Override
	public EVoucher update(EVoucher eVoucher) {
		
		Result<EvouchersRecord> results = this.dslContext
		.update(EVOUCHERS)
		.set(EVOUCHERS.VERSION, EVOUCHERS.VERSION.add(1))
		.set(EVOUCHERS.NAME, eVoucher.getName())
		.set(EVOUCHERS.EMAIL, eVoucher.getEmail())
		.set(EVOUCHERS.REQUEST_DATE, eVoucher.getRequestDate())
		.set(EVOUCHERS.AMOUNT, eVoucher.getAmount())
		.set(EVOUCHERS.LAST_MODIFIED_BY, eVoucher.getLastModifiedBy())
		.set(EVOUCHERS.LAST_MODIFIED_DATE, LocalDateTime.now())
		.where(EVOUCHERS.ID.eq(eVoucher.getId()))
		.and(EVOUCHERS.VERSION.eq(eVoucher.getVersion()))
		.returning()
		.fetch();
		
		if (results.size() != 1) {
			throw new OptimisticLockingFailureException(results.size() + " line(s) updated instead of one !");
		} else {
			return RecordConverterFactory.convert(results.get(0));
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#findAllEVouchers(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EVoucher> findAllEVouchers(Pageable pageable) {
		Page<EVoucher> page = null;
		Condition condition = EVOUCHERS.STATUS.eq(EVoucherStatus.ACTIVE.name());
		List<EVoucher> elements = this.dslContext
				.selectFrom(EVOUCHERS)
				.where(condition)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch()
				.stream()
				.map(RecordConverterFactory::convert)
				.collect(Collectors.toList());
		int nbTotalElements = this.dslContext.fetchCount(EVOUCHERS, condition);
		page = new PageImpl<>(elements, pageable, nbTotalElements);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#findAllEVouchersByTeam(org.bspv.evoucher.core.model.Team, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EVoucher> findAllEVouchersByTeam(Team team, Pageable pageable) {
		Page<EVoucher> page = null;
//		@formatter:off
		Condition condition = 
				EVOUCHERS.STATUS.eq(EVoucherStatus.ACTIVE.name())
				.and(EVOUCHERS.TEAM_NUMBER.eq(team.getNumber())
				.and(EVOUCHERS.DISTRIBUTION_YEAR.eq(team.getYear())));
		List<EVoucher> elements = this.dslContext
				.selectFrom(EVOUCHERS)
				.where(condition)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch()
				.stream()
				.map(RecordConverterFactory::convert)
				.collect(Collectors.toList());
//		@formatter:on
		int nbTotalElements = this.dslContext.fetchCount(EVOUCHERS, condition);
		page = new PageImpl<>(elements, pageable, nbTotalElements);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#findAnyEVouchers(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<EVoucher> findAnyEVouchers(Pageable pageable) {
		Page<EVoucher> page = null;
		List<EVoucher> elements = this.dslContext
				.selectFrom(EVOUCHERS)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch()
				.stream()
				.map(RecordConverterFactory::convert)
				.collect(Collectors.toList());
		int nbTotalElements = this.dslContext.fetchCount(EVOUCHERS);
		page = new PageImpl<>(elements, pageable, nbTotalElements);
		return page;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#softlyDeleteEVoucher(java.util.UUID)
	 */
	@Override
	public void softlyDeleteEVoucher(EVoucher eVoucher) {
		changeEVoucherStatus(eVoucher, EVoucherStatus.CANCELLED);
	}

	/* (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#archiveEVoucher(org.bspv.evoucher.model.EVoucher)
	 */
	@Override
	public EVoucher archiveEVoucher(EVoucher eVoucher) {
		return changeEVoucherStatus(eVoucher, EVoucherStatus.ARCHIVED);
	}
	
	/**
	 * Change the status of the e-voucher with the one given.
	 * @param eVoucher e-voucher to update
	 * @param status new status
	 * @return the updated eVoucher.
	 */
	private EVoucher changeEVoucherStatus(EVoucher eVoucher, EVoucherStatus status) {
		Result<EvouchersRecord> results = this.dslContext
				.update(EVOUCHERS)
				.set(EVOUCHERS.VERSION, EVOUCHERS.VERSION.add(1))
				.set( EVOUCHERS.STATUS, status.name())
				.set(EVOUCHERS.LAST_MODIFIED_BY, eVoucher.getLastModifiedBy())
				.set(EVOUCHERS.LAST_MODIFIED_DATE, LocalDateTime.now())
				.where(EVOUCHERS.ID.eq(eVoucher.getId()))
				.and(EVOUCHERS.VERSION.eq(eVoucher.getVersion()))
				.returning()
				.fetch();
		if (results.size() != 1) {
			throw new OptimisticLockingFailureException(results.size() + " line(s) updated instead of one !");
		}
		return RecordConverterFactory.convert(results.get(0));
	}


	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#findEVoucherById(java.util.UUID)
	 */
	@Override
	public EVoucher findEVoucherById(UUID id) {
		EVoucher eVoucher = null;
		EvouchersRecord record = this.dslContext
				.selectFrom(EVOUCHERS)
				.where(EVOUCHERS.ID.eq(id))
				.and(EVOUCHERS.STATUS.eq(EVoucherStatus.ACTIVE.name()))
				.fetchOne();
		if (record != null && record.getId() != null) {
			eVoucher = RecordConverterFactory.convert(record);
		}
		return eVoucher;
	}


	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherRepository#findAnyEVoucherById(java.util.UUID)
	 */
	@Override
	public EVoucher findAnyEVoucherById(UUID id) {
		EVoucher eVoucher = null;
		EvouchersRecord record = this.dslContext
				.selectFrom(EVOUCHERS)
				.where(EVOUCHERS.ID.eq(id))
				.fetchOne();
		if (record != null && record.getId() != null) {
			eVoucher = RecordConverterFactory.convert(record);
		}
		return eVoucher;
	}



}
