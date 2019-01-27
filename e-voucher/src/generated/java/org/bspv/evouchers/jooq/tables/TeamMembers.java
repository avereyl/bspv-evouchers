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
import org.bspv.evouchers.jooq.tables.records.TeamMembersRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class TeamMembers extends TableImpl<TeamMembersRecord> {

    private static final long serialVersionUID = -1969750765;

    /**
     * The reference instance of <code>public.team_members</code>
     */
    public static final TeamMembers TEAM_MEMBERS = new TeamMembers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TeamMembersRecord> getRecordType() {
        return TeamMembersRecord.class;
    }

    /**
     * The column <code>public.team_members.user_id</code>.
     */
    public final TableField<TeamMembersRecord, UUID> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.team_members.distribution_year</code>.
     */
    public final TableField<TeamMembersRecord, Integer> DISTRIBUTION_YEAR = createField("distribution_year", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.team_members.team_number</code>.
     */
    public final TableField<TeamMembersRecord, Integer> TEAM_NUMBER = createField("team_number", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.team_members.validity_start</code>.
     */
    public final TableField<TeamMembersRecord, LocalDateTime> VALIDITY_START = createField("validity_start", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new LocalDateTimeConverter());

    /**
     * The column <code>public.team_members.validity_end</code>.
     */
    public final TableField<TeamMembersRecord, LocalDateTime> VALIDITY_END = createField("validity_end", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "", new LocalDateTimeConverter());

    /**
     * Create a <code>public.team_members</code> table reference
     */
    public TeamMembers() {
        this("team_members", null);
    }

    /**
     * Create an aliased <code>public.team_members</code> table reference
     */
    public TeamMembers(String alias) {
        this(alias, TEAM_MEMBERS);
    }

    private TeamMembers(String alias, Table<TeamMembersRecord> aliased) {
        this(alias, aliased, null);
    }

    private TeamMembers(String alias, Table<TeamMembersRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<TeamMembersRecord> getPrimaryKey() {
        return Keys.TEAM_MEMBERS_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TeamMembersRecord>> getKeys() {
        return Arrays.<UniqueKey<TeamMembersRecord>>asList(Keys.TEAM_MEMBERS_PKEY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<TeamMembersRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TeamMembersRecord, ?>>asList(Keys.TEAM_MEMBERS__FK_TEAM_MEMBERS__USERS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TeamMembers as(String alias) {
        return new TeamMembers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TeamMembers rename(String name) {
        return new TeamMembers(name, null);
    }
}