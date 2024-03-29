/*
 * This file is generated by jOOQ.
 */
package org.bspv.evouchers.jooq;


import org.bspv.evouchers.jooq.tables.Authorities;
import org.bspv.evouchers.jooq.tables.EvoucherEvents;
import org.bspv.evouchers.jooq.tables.Evouchers;
import org.bspv.evouchers.jooq.tables.FlywaySchemaHistory;
import org.bspv.evouchers.jooq.tables.TeamMembers;
import org.bspv.evouchers.jooq.tables.Users;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in PUBLIC.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FK_AUTHORITIES__USERS = Internal.createIndex(DSL.name("FK_AUTHORITIES__USERS"), Authorities.AUTHORITIES, new OrderField[] { Authorities.AUTHORITIES.USER_ID }, false);
    public static final Index FK_EVOUCHER_EVENTS__EVOUCHERS = Internal.createIndex(DSL.name("FK_EVOUCHER_EVENTS__EVOUCHERS"), EvoucherEvents.EVOUCHER_EVENTS, new OrderField[] { EvoucherEvents.EVOUCHER_EVENTS.EVOUCHER_ID }, false);
    public static final Index FK_EVOUCHERS__USERS_1 = Internal.createIndex(DSL.name("FK_EVOUCHERS__USERS_1"), Evouchers.EVOUCHERS, new OrderField[] { Evouchers.EVOUCHERS.CREATED_BY }, false);
    public static final Index FK_EVOUCHERS__USERS_2 = Internal.createIndex(DSL.name("FK_EVOUCHERS__USERS_2"), Evouchers.EVOUCHERS, new OrderField[] { Evouchers.EVOUCHERS.LAST_MODIFIED_BY }, false);
    public static final Index FK_EVOUCHERS_EVENTS__USERS = Internal.createIndex(DSL.name("FK_EVOUCHERS_EVENTS__USERS"), EvoucherEvents.EVOUCHER_EVENTS, new OrderField[] { EvoucherEvents.EVOUCHER_EVENTS.CREATED_BY }, false);
    public static final Index FK_TEAM_MEMBERS__USERS = Internal.createIndex(DSL.name("FK_TEAM_MEMBERS__USERS"), TeamMembers.TEAM_MEMBERS, new OrderField[] { TeamMembers.TEAM_MEMBERS.USER_ID }, false);
    public static final Index FLYWAY_SCHEMA_HISTORY_PK = Internal.createIndex(DSL.name("flyway_schema_history_pk"), FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex(DSL.name("flyway_schema_history_s_idx"), FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS }, false);
    public static final Index IX_EVOUCHER_EVENTS__EVOUCHER_ID = Internal.createIndex(DSL.name("IX_EVOUCHER_EVENTS__EVOUCHER_ID"), EvoucherEvents.EVOUCHER_EVENTS, new OrderField[] { EvoucherEvents.EVOUCHER_EVENTS.EVOUCHER_ID }, false);
    public static final Index IX_EVOUCHERS__STATUS = Internal.createIndex(DSL.name("IX_EVOUCHERS__STATUS"), Evouchers.EVOUCHERS, new OrderField[] { Evouchers.EVOUCHERS.STATUS }, false);
    public static final Index IX_TEAM_MEMBERS__NUMBER = Internal.createIndex(DSL.name("IX_TEAM_MEMBERS__NUMBER"), TeamMembers.TEAM_MEMBERS, new OrderField[] { TeamMembers.TEAM_MEMBERS.TEAM_NUMBER }, false);
    public static final Index IX_TEAM_MEMBERS__YEAR = Internal.createIndex(DSL.name("IX_TEAM_MEMBERS__YEAR"), TeamMembers.TEAM_MEMBERS, new OrderField[] { TeamMembers.TEAM_MEMBERS.DISTRIBUTION_YEAR }, false);
    public static final Index IX_USERS__USERNAME = Internal.createIndex(DSL.name("IX_USERS__USERNAME"), Users.USERS, new OrderField[] { Users.USERS.USERNAME }, false);
    public static final Index SYS_PK_10141 = Internal.createIndex(DSL.name("SYS_PK_10141"), Users.USERS, new OrderField[] { Users.USERS.ID }, true);
    public static final Index SYS_PK_10156 = Internal.createIndex(DSL.name("SYS_PK_10156"), Evouchers.EVOUCHERS, new OrderField[] { Evouchers.EVOUCHERS.ID }, true);
    public static final Index SYS_PK_10172 = Internal.createIndex(DSL.name("SYS_PK_10172"), EvoucherEvents.EVOUCHER_EVENTS, new OrderField[] { EvoucherEvents.EVOUCHER_EVENTS.ID }, true);
    public static final Index SYS_PK_10184 = Internal.createIndex(DSL.name("SYS_PK_10184"), TeamMembers.TEAM_MEMBERS, new OrderField[] { TeamMembers.TEAM_MEMBERS.USER_ID, TeamMembers.TEAM_MEMBERS.DISTRIBUTION_YEAR, TeamMembers.TEAM_MEMBERS.TEAM_NUMBER }, true);
    public static final Index SYS_PK_10193 = Internal.createIndex(DSL.name("SYS_PK_10193"), Authorities.AUTHORITIES, new OrderField[] { Authorities.AUTHORITIES.USER_ID, Authorities.AUTHORITIES.AUTHORITY }, true);
    public static final Index UK_USERS__1 = Internal.createIndex(DSL.name("UK_USERS__1"), Users.USERS, new OrderField[] { Users.USERS.USERNAME }, true);
}
