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
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 917911854;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.authorities</code>.
     */
    public final Authorities AUTHORITIES = org.bspv.evouchers.jooq.tables.Authorities.AUTHORITIES;

    /**
     * The table <code>public.evoucher_events</code>.
     */
    public final EvoucherEvents EVOUCHER_EVENTS = org.bspv.evouchers.jooq.tables.EvoucherEvents.EVOUCHER_EVENTS;

    /**
     * The table <code>public.evouchers</code>.
     */
    public final Evouchers EVOUCHERS = org.bspv.evouchers.jooq.tables.Evouchers.EVOUCHERS;

    /**
     * The table <code>public.schema_version</code>.
     */
    public final SchemaVersion SCHEMA_VERSION = org.bspv.evouchers.jooq.tables.SchemaVersion.SCHEMA_VERSION;

    /**
     * The table <code>public.team_members</code>.
     */
    public final TeamMembers TEAM_MEMBERS = org.bspv.evouchers.jooq.tables.TeamMembers.TEAM_MEMBERS;

    /**
     * The table <code>public.users</code>.
     */
    public final Users USERS = org.bspv.evouchers.jooq.tables.Users.USERS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
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
            Sequences.EVOUCHER_EVENTS_ID_SEQ);
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
            EvoucherEvents.EVOUCHER_EVENTS,
            Evouchers.EVOUCHERS,
            SchemaVersion.SCHEMA_VERSION,
            TeamMembers.TEAM_MEMBERS,
            Users.USERS);
    }
}
