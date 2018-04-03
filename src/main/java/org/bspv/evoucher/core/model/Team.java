/**
 * 
 */
package org.bspv.evoucher.core.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This immutable class represents a team. A team is tight to a year. At instant t, only
 * one team with same year and number can be valid (see validity timeframe in DB)
 */
@ToString
@EqualsAndHashCode(of = { "year", "number" })
public final class Team implements Serializable {

	/**
	 * Generated Serial Version UID.
	 */
	private static final long serialVersionUID = 3505789685021653962L;
	
	/**
	 * Constant for the "year" attribute, used externally.
	 * @see Team#year
	 */
	public static final String YEAR_FIELD = "year";
	/**
	 * Constant for the "number" attribute, used externally.
	 * @see Team#number
	 */
	public static final String NUMBER_FIELD = "number";

	/**
	 * Builder class for {@link Team}.
	 * 
	 */
	public static class Builder {
		
		/** @see Team#year */
		private Integer year;
		/** @see Team#number */
		private Integer number;
		

		/**
		 * Constructor of the builder.
		 * 
		 */
		private Builder() {
			super();
		}

		/**
		 * build the team calling the constructor or the Team class.
		 * 
		 * @return new instance of {@link Team}
		 */
		public Team build() {
			return new Team(this);
		}

		/**
		 * Set the distribution year.
		 * 
		 * @param distributionYear
		 * @return this builder instance
		 */
		public Builder forYear(Integer year) {
			this.year = year;
			return this;
		}
		/**
		 * Set the number.
		 * 
		 * @param number
		 * @return this builder instance
		 */
		public Builder withNumber(Integer number) {
			this.number = number;
			return this;
		}
		
	}

	/**
	 * Year of the distribution campaign.
	 */
	@Getter
	private final Integer year;
	/**
	 * Team number.
	 */
	@Getter
	private final Integer number;
	
		
	/**
	 * Static method to access the builder.
	 * 
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Default constructor.
	 */
	private Team(Builder builder) {
		super();
		this.year = builder.year;
		this.number = builder.number;
	}

	/**
	 * @param year
	 * @param number
	 */
	public Team(Integer year, Integer number) {
		super();
		this.year = year;
		this.number = number;
	}
	
	
}
