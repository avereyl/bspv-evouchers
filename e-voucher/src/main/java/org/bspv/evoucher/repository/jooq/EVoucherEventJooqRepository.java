/**
 * 
 */
package org.bspv.evoucher.repository.jooq;

import static org.bspv.evouchers.jooq.tables.EvoucherEvents.EVOUCHER_EVENTS;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bspv.evoucher.core.model.EVoucherEvent;
import org.bspv.evoucher.repository.EVoucherEventRepository;
import org.bspv.evoucher.repository.jooq.converter.RecordConverterFactory;
import org.bspv.evouchers.jooq.tables.records.EvoucherEventsRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository
@Transactional
public class EVoucherEventJooqRepository implements EVoucherEventRepository {

	/**
	 * Jooq DSL context.
	 */
	private final DSLContext dslContext;

	/**
	 * Constructor.
	 * @param dslContext
	 */
	public EVoucherEventJooqRepository(DSLContext dslContext) {
		super();
		this.dslContext = dslContext;
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherEventRepository#findEventsByEVoucherId(java.util.UUID)
	 */
	@Override
	public List<EVoucherEvent> findEventsByEVoucherUuid(UUID uuid) {
		return this.dslContext
				.selectFrom(EVOUCHER_EVENTS)
				.where(EVOUCHER_EVENTS.EVOUCHER_ID.equal(uuid))
				.fetch()
				.stream()
				.map(RecordConverterFactory::convert)
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherEventRepository#insert(org.bspv.evoucher.model.EVoucherEvent)
	 */
	@Override
	public EVoucherEvent insert(EVoucherEvent event) {
		EvoucherEventsRecord record = this.dslContext
			.insertInto(EVOUCHER_EVENTS)
			.columns(EVOUCHER_EVENTS.ID,
					 EVOUCHER_EVENTS.EVOUCHER_ID,
					 EVOUCHER_EVENTS.EVENT_TYPE, 
					 EVOUCHER_EVENTS.CREATED_BY,
					 EVOUCHER_EVENTS.CREATED_DATE)
			.values(event.getId(), 
					event.getEVoucherUuid(), 
					event.getEventType().name(), 
					event.getCreatedBy(),
					event.getCreatedDate())
			.returning()
			.fetchOne();
		return RecordConverterFactory.convert(record);
	}

	/*
	 * (non-Javadoc)
	 * @see org.bspv.evoucher.repository.EVoucherEventRepository#findEventById(java.lang.Long)
	 */
	@Override
	public EVoucherEvent findEventById(Long id) {
		EvoucherEventsRecord record = this.dslContext
				.selectFrom(EVOUCHER_EVENTS)
				.where(EVOUCHER_EVENTS.ID.equal(id))
				.fetchOne();
		return RecordConverterFactory.convert(record);
	}

}
