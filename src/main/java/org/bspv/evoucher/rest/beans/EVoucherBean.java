/**
 * 
 */
package org.bspv.evoucher.rest.beans;

import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMin;

import org.bspv.evoucher.rest.beans.validation.ValidUUID;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 *
 */
@Data
public class EVoucherBean {

	/**
	 * UUID of this e-voucher.
	 */
	@ValidUUID
	private String uuid;
	
	/**
	 * Name of the person or the company targeted by this voucher.
	 */
	@NotBlank
	private String name;
	
	/**
	 * Version of the voucher.
	 */
	private Long version = 0L;
	
	/**
	 * Amount declared on the e-voucher.
	 */
	@DecimalMin("0.01")
	private Float amount;
	
	/**
	 * Email of the person or the company targeted by this voucher.
	 */
	private String email;
	
	/**
	 * Date and time of the voucher's request.
	 */
	private LocalDateTime requestDate;
	
	/**
	 * Number of the team issuing the e-voucher.
	 */
	private Integer teamNumber;
	
	/**
	 * Year of distribution.
	 */
	private Integer distributionYear;

}
