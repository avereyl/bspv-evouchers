/*
 * This file is generated by jOOQ.
*/
package org.bspv.evouchers.jooq;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.bspv.evouchers.jooq.tables.Authorities;
import org.bspv.evouchers.jooq.tables.EvoucherEvents;
import org.bspv.evouchers.jooq.tables.Evouchers;
import org.bspv.evouchers.jooq.tables.SchemaVersion;
import org.bspv.evouchers.jooq.tables.TeamMembers;
import org.bspv.evouchers.jooq.tables.Users;
import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -1218334086;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.AUTHORITIES</code>.
     */
    public final Authorities AUTHORITIES = org.bspv.evouchers.jooq.tables.Authorities.AUTHORITIES;

    /**
     * The table <code>PUBLIC.EVOUCHERS</code>.
     */
    public final Evouchers EVOUCHERS = org.bspv.evouchers.jooq.tables.Evouchers.EVOUCHERS;

    /**
     * The table <code>PUBLIC.EVOUCHER_EVENTS</code>.
     */
    public final EvoucherEvents EVOUCHER_EVENTS = org.bspv.evouchers.jooq.tables.EvoucherEvents.EVOUCHER_EVENTS;

    /**
     * The table <code>PUBLIC.TEAM_MEMBERS</code>.
     */
    public final TeamMembers TEAM_MEMBERS = org.bspv.evouchers.jooq.tables.TeamMembers.TEAM_MEMBERS;

    /**
     * The table <code>PUBLIC.USERS</code>.
     */
    public final Users USERS = org.bspv.evouchers.jooq.tables.Users.USERS;

    /**
     * The table <code>PUBLIC.schema_version</code>.
     */
    public final SchemaVersion SCHEMA_VERSION = org.bspv.evouchers.jooq.tables.SchemaVersion.SCHEMA_VERSION;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.SYSTEM_SEQUENCE_73BD2400_E15F_4179_8DF0_B33888E9A3D4);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Authorities.AUTHORITIES,
            Evouchers.EVOUCHERS,
            EvoucherEvents.EVOUCHER_EVENTS,
            TeamMembers.TEAM_MEMBERS,
            Users.USERS,
            SchemaVersion.SCHEMA_VERSION);
    }
}
