/*
 * This file is generated by jOOQ.
*/
package org.bspv.evouchers.jooq.tables.records;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.annotation.Generated;

import org.bspv.evouchers.jooq.tables.Evouchers;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;


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
public class EvouchersRecord extends UpdatableRecordImpl<EvouchersRecord> implements Record13<UUID, Long, UUID, LocalDateTime, UUID, LocalDateTime, String, String, LocalDateTime, BigDecimal, String, Integer, Integer> {

    private static final long serialVersionUID = 277479379;

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.ID</code>.
     */
    public void setId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.ID</code>.
     */
    public UUID getId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.VERSION</code>.
     */
    public void setVersion(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.VERSION</code>.
     */
    public Long getVersion() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.CREATED_BY</code>.
     */
    public void setCreatedBy(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.CREATED_BY</code>.
     */
    public UUID getCreatedBy() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.CREATED_DATE</code>.
     */
    public void setCreatedDate(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.CREATED_DATE</code>.
     */
    public LocalDateTime getCreatedDate() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.LAST_MODIFIED_BY</code>.
     */
    public void setLastModifiedBy(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.LAST_MODIFIED_BY</code>.
     */
    public UUID getLastModifiedBy() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.LAST_MODIFIED_DATE</code>.
     */
    public void setLastModifiedDate(LocalDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.LAST_MODIFIED_DATE</code>.
     */
    public LocalDateTime getLastModifiedDate() {
        return (LocalDateTime) get(5);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.NAME</code>.
     */
    public void setName(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.NAME</code>.
     */
    public String getName() {
        return (String) get(6);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.EMAIL</code>.
     */
    public void setEmail(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.EMAIL</code>.
     */
    public String getEmail() {
        return (String) get(7);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.REQUEST_DATE</code>.
     */
    public void setRequestDate(LocalDateTime value) {
        set(8, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.REQUEST_DATE</code>.
     */
    public LocalDateTime getRequestDate() {
        return (LocalDateTime) get(8);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.AMOUNT</code>.
     */
    public void setAmount(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.AMOUNT</code>.
     */
    public BigDecimal getAmount() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.STATUS</code>.
     */
    public void setStatus(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.STATUS</code>.
     */
    public String getStatus() {
        return (String) get(10);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.DISTRIBUTION_YEAR</code>.
     */
    public void setDistributionYear(Integer value) {
        set(11, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.DISTRIBUTION_YEAR</code>.
     */
    public Integer getDistributionYear() {
        return (Integer) get(11);
    }

    /**
     * Setter for <code>PUBLIC.EVOUCHERS.TEAM_NUMBER</code>.
     */
    public void setTeamNumber(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>PUBLIC.EVOUCHERS.TEAM_NUMBER</code>.
     */
    public Integer getTeamNumber() {
        return (Integer) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UUID> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<UUID, Long, UUID, LocalDateTime, UUID, LocalDateTime, String, String, LocalDateTime, BigDecimal, String, Integer, Integer> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row13<UUID, Long, UUID, LocalDateTime, UUID, LocalDateTime, String, String, LocalDateTime, BigDecimal, String, Integer, Integer> valuesRow() {
        return (Row13) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field1() {
        return Evouchers.EVOUCHERS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return Evouchers.EVOUCHERS.VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field3() {
        return Evouchers.EVOUCHERS.CREATED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field4() {
        return Evouchers.EVOUCHERS.CREATED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UUID> field5() {
        return Evouchers.EVOUCHERS.LAST_MODIFIED_BY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field6() {
        return Evouchers.EVOUCHERS.LAST_MODIFIED_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Evouchers.EVOUCHERS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Evouchers.EVOUCHERS.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<LocalDateTime> field9() {
        return Evouchers.EVOUCHERS.REQUEST_DATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<BigDecimal> field10() {
        return Evouchers.EVOUCHERS.AMOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return Evouchers.EVOUCHERS.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field12() {
        return Evouchers.EVOUCHERS.DISTRIBUTION_YEAR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field13() {
        return Evouchers.EVOUCHERS.TEAM_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value3() {
        return getCreatedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value4() {
        return getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID value5() {
        return getLastModifiedBy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value6() {
        return getLastModifiedDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime value9() {
        return getRequestDate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal value10() {
        return getAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value12() {
        return getDistributionYear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value13() {
        return getTeamNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value1(UUID value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value2(Long value) {
        setVersion(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value3(UUID value) {
        setCreatedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value4(LocalDateTime value) {
        setCreatedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value5(UUID value) {
        setLastModifiedBy(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value6(LocalDateTime value) {
        setLastModifiedDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value7(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value8(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value9(LocalDateTime value) {
        setRequestDate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value10(BigDecimal value) {
        setAmount(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value11(String value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value12(Integer value) {
        setDistributionYear(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord value13(Integer value) {
        setTeamNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvouchersRecord values(UUID value1, Long value2, UUID value3, LocalDateTime value4, UUID value5, LocalDateTime value6, String value7, String value8, LocalDateTime value9, BigDecimal value10, String value11, Integer value12, Integer value13) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EvouchersRecord
     */
    public EvouchersRecord() {
        super(Evouchers.EVOUCHERS);
    }

    /**
     * Create a detached, initialised EvouchersRecord
     */
    public EvouchersRecord(UUID id, Long version, UUID createdBy, LocalDateTime createdDate, UUID lastModifiedBy, LocalDateTime lastModifiedDate, String name, String email, LocalDateTime requestDate, BigDecimal amount, String status, Integer distributionYear, Integer teamNumber) {
        super(Evouchers.EVOUCHERS);

        set(0, id);
        set(1, version);
        set(2, createdBy);
        set(3, createdDate);
        set(4, lastModifiedBy);
        set(5, lastModifiedDate);
        set(6, name);
        set(7, email);
        set(8, requestDate);
        set(9, amount);
        set(10, status);
        set(11, distributionYear);
        set(12, teamNumber);
    }
}
