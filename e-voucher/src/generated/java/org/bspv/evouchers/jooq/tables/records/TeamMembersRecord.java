/*
 * This file is generated by jOOQ.
 */
package org.bspv.evouchers.jooq.tables.records;


import java.time.LocalDateTime;
import java.util.UUID;

import org.bspv.evouchers.jooq.tables.TeamMembers;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TeamMembersRecord extends UpdatableRecordImpl<TeamMembersRecord> implements Record5<UUID, Integer, Integer, LocalDateTime, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>PUBLIC.TEAM_MEMBERS.USER_ID</code>.
     */
    public void setUserId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>PUBLIC.TEAM_MEMBERS.USER_ID</code>.
     */
    public UUID getUserId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>PUBLIC.TEAM_MEMBERS.DISTRIBUTION_YEAR</code>.
     */
    public void setDistributionYear(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>PUBLIC.TEAM_MEMBERS.DISTRIBUTION_YEAR</code>.
     */
    public Integer getDistributionYear() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>PUBLIC.TEAM_MEMBERS.TEAM_NUMBER</code>.
     */
    public void setTeamNumber(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>PUBLIC.TEAM_MEMBERS.TEAM_NUMBER</code>.
     */
    public Integer getTeamNumber() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>PUBLIC.TEAM_MEMBERS.VALIDITY_START</code>.
     */
    public void setValidityStart(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>PUBLIC.TEAM_MEMBERS.VALIDITY_START</code>.
     */
    public LocalDateTime getValidityStart() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>PUBLIC.TEAM_MEMBERS.VALIDITY_END</code>.
     */
    public void setValidityEnd(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>PUBLIC.TEAM_MEMBERS.VALIDITY_END</code>.
     */
    public LocalDateTime getValidityEnd() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<UUID, Integer, Integer> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<UUID, Integer, Integer, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<UUID, Integer, Integer, LocalDateTime, LocalDateTime> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return TeamMembers.TEAM_MEMBERS.USER_ID;
    }

    @Override
    public Field<Integer> field2() {
        return TeamMembers.TEAM_MEMBERS.DISTRIBUTION_YEAR;
    }

    @Override
    public Field<Integer> field3() {
        return TeamMembers.TEAM_MEMBERS.TEAM_NUMBER;
    }

    @Override
    public Field<LocalDateTime> field4() {
        return TeamMembers.TEAM_MEMBERS.VALIDITY_START;
    }

    @Override
    public Field<LocalDateTime> field5() {
        return TeamMembers.TEAM_MEMBERS.VALIDITY_END;
    }

    @Override
    public UUID component1() {
        return getUserId();
    }

    @Override
    public Integer component2() {
        return getDistributionYear();
    }

    @Override
    public Integer component3() {
        return getTeamNumber();
    }

    @Override
    public LocalDateTime component4() {
        return getValidityStart();
    }

    @Override
    public LocalDateTime component5() {
        return getValidityEnd();
    }

    @Override
    public UUID value1() {
        return getUserId();
    }

    @Override
    public Integer value2() {
        return getDistributionYear();
    }

    @Override
    public Integer value3() {
        return getTeamNumber();
    }

    @Override
    public LocalDateTime value4() {
        return getValidityStart();
    }

    @Override
    public LocalDateTime value5() {
        return getValidityEnd();
    }

    @Override
    public TeamMembersRecord value1(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public TeamMembersRecord value2(Integer value) {
        setDistributionYear(value);
        return this;
    }

    @Override
    public TeamMembersRecord value3(Integer value) {
        setTeamNumber(value);
        return this;
    }

    @Override
    public TeamMembersRecord value4(LocalDateTime value) {
        setValidityStart(value);
        return this;
    }

    @Override
    public TeamMembersRecord value5(LocalDateTime value) {
        setValidityEnd(value);
        return this;
    }

    @Override
    public TeamMembersRecord values(UUID value1, Integer value2, Integer value3, LocalDateTime value4, LocalDateTime value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TeamMembersRecord
     */
    public TeamMembersRecord() {
        super(TeamMembers.TEAM_MEMBERS);
    }

    /**
     * Create a detached, initialised TeamMembersRecord
     */
    public TeamMembersRecord(UUID userId, Integer distributionYear, Integer teamNumber, LocalDateTime validityStart, LocalDateTime validityEnd) {
        super(TeamMembers.TEAM_MEMBERS);

        setUserId(userId);
        setDistributionYear(distributionYear);
        setTeamNumber(teamNumber);
        setValidityStart(validityStart);
        setValidityEnd(validityEnd);
    }
}
