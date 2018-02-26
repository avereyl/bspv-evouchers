/*
 * This file is generated by jOOQ.
*/
package org.bspv.evouchers.jooq.tables;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.bspv.evouchers.jooq.Keys;
import org.bspv.evouchers.jooq.Public;
import org.bspv.evouchers.jooq.tables.records.AuthoritiesRecord;
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
public class Authorities extends TableImpl<AuthoritiesRecord> {

    private static final long serialVersionUID = -1192532310;

    /**
     * The reference instance of <code>PUBLIC.AUTHORITIES</code>
     */
    public static final Authorities AUTHORITIES = new Authorities();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AuthoritiesRecord> getRecordType() {
        return AuthoritiesRecord.class;
    }

    /**
     * The column <code>PUBLIC.AUTHORITIES.USER_ID</code>.
     */
    public final TableField<AuthoritiesRecord, UUID> USER_ID = createField("USER_ID", org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.AUTHORITIES.AUTHORITY</code>.
     */
    public final TableField<AuthoritiesRecord, String> AUTHORITY = createField("AUTHORITY", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * Create a <code>PUBLIC.AUTHORITIES</code> table reference
     */
    public Authorities() {
        this("AUTHORITIES", null);
    }

    /**
     * Create an aliased <code>PUBLIC.AUTHORITIES</code> table reference
     */
    public Authorities(String alias) {
        this(alias, AUTHORITIES);
    }

    private Authorities(String alias, Table<AuthoritiesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Authorities(String alias, Table<AuthoritiesRecord> aliased, Field<?>[] parameters) {
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
    public UniqueKey<AuthoritiesRecord> getPrimaryKey() {
        return Keys.SYS_PK_10198;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AuthoritiesRecord>> getKeys() {
        return Arrays.<UniqueKey<AuthoritiesRecord>>asList(Keys.SYS_PK_10198);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<AuthoritiesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AuthoritiesRecord, ?>>asList(Keys.FK_AUTHORITIES__USERS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authorities as(String alias) {
        return new Authorities(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Authorities rename(String name) {
        return new Authorities(name, null);
    }
}
