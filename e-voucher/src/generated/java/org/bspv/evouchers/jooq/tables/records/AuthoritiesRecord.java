/*
 * This file is generated by jOOQ.
*/
package org.bspv.evouchers.jooq.tables.records;


import java.util.UUID;

import javax.annotation.Generated;

import org.bspv.evouchers.jooq.tables.Authorities;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


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
public class AuthoritiesRecord extends UpdatableRecordImpl<AuthoritiesRecord> implements Record2<UUID, String> {

    private static final long serialVersionUID = -752099252;

    /**
     * Setter for <code>public.authorities.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.authorities.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.authorities.authority</code>.
     */
    public void setAuthority(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.authorities.authority</code>.
     */
    public String getAuthority() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<UUID, String> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<UUID, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row2<UUID, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Authorities.AUTHORITIES.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Authorities.AUTHORITIES.AUTHORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getAuthority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthoritiesRecord value1(UUID value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthoritiesRecord value2(String value) {
        setAuthority(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthoritiesRecord values(UUID value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuthoritiesRecord
     */
    public AuthoritiesRecord() {
        super(Authorities.AUTHORITIES);
    }

    /**
     * Create a detached, initialised AuthoritiesRecord
     */
    public AuthoritiesRecord(UUID userId, String authority) {
        super(Authorities.AUTHORITIES);

        set(0, userId);
        set(1, authority);
    }
}
