/*
 * This file is generated by jOOQ.
*/
package org.bspv.evouchers.jooq;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.evoucher_events_id_seq</code>
     */
    public static final Sequence<Long> EVOUCHER_EVENTS_ID_SEQ = new SequenceImpl<Long>("evoucher_events_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
