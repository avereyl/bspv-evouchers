/*
 * This file is generated by jOOQ.
 */
package org.bspv.evouchers.jooq.tables;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bspv.evouchers.jooq.Indexes;
import org.bspv.evouchers.jooq.Keys;
import org.bspv.evouchers.jooq.Public;
import org.bspv.evouchers.jooq.tables.records.UsersRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Users extends TableImpl<UsersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>PUBLIC.USERS</code>
     */
    public static final Users USERS = new Users();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UsersRecord> getRecordType() {
        return UsersRecord.class;
    }

    /**
     * The column <code>PUBLIC.USERS.ID</code>.
     */
    public final TableField<UsersRecord, UUID> ID = createField(DSL.name("ID"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>PUBLIC.USERS.VERSION</code>.
     */
    public final TableField<UsersRecord, Long> VERSION = createField(DSL.name("VERSION"), SQLDataType.BIGINT.nullable(false).defaultValue(DSL.field("1", SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>PUBLIC.USERS.USERNAME</code>.
     */
    public final TableField<UsersRecord, String> USERNAME = createField(DSL.name("USERNAME"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.USERS.SECRETKEY</code>.
     */
    public final TableField<UsersRecord, String> SECRETKEY = createField(DSL.name("SECRETKEY"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>PUBLIC.USERS.ENABLED</code>.
     */
    public final TableField<UsersRecord, Boolean> ENABLED = createField(DSL.name("ENABLED"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field("TRUE", SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>PUBLIC.USERS.EMAIL</code>.
     */
    public final TableField<UsersRecord, String> EMAIL = createField(DSL.name("EMAIL"), SQLDataType.VARCHAR(50), this, "");

    private Users(Name alias, Table<UsersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Users(Name alias, Table<UsersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>PUBLIC.USERS</code> table reference
     */
    public Users(String alias) {
        this(DSL.name(alias), USERS);
    }

    /**
     * Create an aliased <code>PUBLIC.USERS</code> table reference
     */
    public Users(Name alias) {
        this(alias, USERS);
    }

    /**
     * Create a <code>PUBLIC.USERS</code> table reference
     */
    public Users() {
        this(DSL.name("USERS"), null);
    }

    public <O extends Record> Users(Table<O> child, ForeignKey<O, UsersRecord> key) {
        super(child, key, USERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.IX_USERS__USERNAME, Indexes.SYS_PK_10141, Indexes.UK_USERS__1);
    }

    @Override
    public UniqueKey<UsersRecord> getPrimaryKey() {
        return Keys.SYS_PK_10141;
    }

    @Override
    public List<UniqueKey<UsersRecord>> getKeys() {
        return Arrays.<UniqueKey<UsersRecord>>asList(Keys.SYS_PK_10141, Keys.UK_USERS__1);
    }

    @Override
    public Users as(String alias) {
        return new Users(DSL.name(alias), this);
    }

    @Override
    public Users as(Name alias) {
        return new Users(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(String name) {
        return new Users(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Users rename(Name name) {
        return new Users(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<UUID, Long, String, String, Boolean, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}
