/*
 * This file is generated by jOOQ.
*/
package org.bspv.evouchers.jooq.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.bspv.evoucher.repository.jooq.converter.LocalDateTimeConverter;
import org.bspv.evouchers.jooq.Keys;
import org.bspv.evouchers.jooq.Public;
import org.bspv.evouchers.jooq.tables.records.EvoucherEventsRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EvoucherEvents extends TableImpl<EvoucherEventsRecord> {

    private static final long serialVersionUID = 1208342541;

    /**
     * The reference instance of <code>PUBLIC.EVOUCHER_EVENTS</code>
     */
    public static final EvoucherEvents EVOUCHER_EVENTS = new EvoucherEvents();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EvoucherEventsRecord> getRecordType() {
        return EvoucherEventsRecord.class;
    }

    /**
     * The column <code>PUBLIC.EVOUCHER_EVENTS.ID</code>.
     */
    public final TableField<EvoucherEventsRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.EVOUCHER_EVENTS.EVOUCHER_ID</code>.
     */
    public final TableField<EvoucherEventsRecord, UUID> EVOUCHER_ID = createField("EVOUCHER_ID", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.EVOUCHER_EVENTS.EVENT_TYPE</code>.
     */
    public final TableField<EvoucherEventsRecord, String> EVENT_TYPE = createField("EVENT_TYPE", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.EVOUCHER_EVENTS.CREATED_BY</code>.
     */
    public final TableField<EvoucherEventsRecord, UUID> CREATED_BY = createField("CREATED_BY", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.EVOUCHER_EVENTS.CREATED_DATE</code>.
     */
    public final TableField<EvoucherEventsRecord, LocalDateTime> CREATED_DATE = createField("CREATED_DATE", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new LocalDateTimeConverter());

    /**
     * Create a <code>PUBLIC.EVOUCHER_EVENTS</code> table reference
     */
    public EvoucherEvents() {
        this("EVOUCHER_EVENTS", null);
    }

    /**
     * Create an aliased <code>PUBLIC.EVOUCHER_EVENTS</code> table reference
     */
    public EvoucherEvents(String alias) {
        this(alias, EVOUCHER_EVENTS);
    }

    private EvoucherEvents(String alias, Table<EvoucherEventsRecord> aliased) {
        this(alias, aliased, null);
    }

    private EvoucherEvents(String alias, Table<EvoucherEventsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<EvoucherEventsRecord, Long> getIdentity() {
        return Keys.IDENTITY_EVOUCHER_EVENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<EvoucherEventsRecord> getPrimaryKey() {
        return Keys.SYS_PK_10146;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<EvoucherEventsRecord>> getKeys() {
        return Arrays.<UniqueKey<EvoucherEventsRecord>>asList(Keys.SYS_PK_10146);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<EvoucherEventsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<EvoucherEventsRecord, ?>>asList(Keys.FK_EVOUCHER_EVENTS__EVOUCHERS, Keys.FK_EVOUCHERS_EVENTS__USERS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvoucherEvents as(String alias) {
        return new EvoucherEvents(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public EvoucherEvents rename(String name) {
        return new EvoucherEvents(name, null);
    }
}
