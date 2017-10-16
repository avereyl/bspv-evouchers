/**
 * 
 */
package org.bspv.evoucher.core.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/**
 * Represents a user of this service.
 * This class is not immutable as the {@link CredentialsContainer} interface should let the password/key to be erased.
 */
@ToString
@EqualsAndHashCode(of = { "username" })
public final class User implements Serializable, UserDetails, CredentialsContainer {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 3180450620024125623L;

	/**
	 * Builder class for {@link User}.
	 * 
	 */
	public static class Builder {

		/** @see User#id */
		private UUID id;
		/** @see User#version */
		private Long version;
		/** @see User#username */
		private String username;
		/** @see User#privateKey */
		private String privateKey;
		/** @see User#enabled */
		private boolean enabled = true;
		/** @see User#email */
		private String email;
		/** @see User#authorities */
		private Set<GrantedAuthority> authorities = new HashSet<>();
		/** @see User#team */
		private Team team;

		/**
		 * Constructor of the builder.
		 * 
		 */
		private Builder() {
			super();
		}

		/**
		 * build the user calling the constructor or the User class.
		 * 
		 * @return new instance of {@link User}
		 */
		public User build() {
			return new User(this);
		}

		/**
		 * 
		 * @param id
		 * @return this builder instance
		 */
		public Builder withId(UUID id) {
			this.id = id;
			return this;
		}

		/**
		 * 
		 * @param version
		 * @return this builder instance
		 */
		public Builder withVersion(Long version) {
			this.version = version;
			return this;
		}

		/**
		 * 
		 * @param userName
		 * @return this builder instance
		 */
		public Builder withUserName(@NonNull String userName) {
			this.username = userName;
			return this;
		}

		/**
		 * @param email
		 * @return this builder instance
		 */
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		/**
		 * @param key
		 * @return this builder instance
		 */
		public Builder withKey(@NonNull String key) {
			this.privateKey = key;
			return this;
		}

		/**
		 * @param enabled
		 * @return this builder instance
		 */
		public Builder enable(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		/**
		 * @param authority
		 * @return this builder instance
		 */
		public Builder withAuthority(GrantedAuthority authority) {
			this.authorities.add(authority);
			return this;
		}

		/**
		 * @param authorities
		 * @return this builder instance
		 */
		public Builder withAuthorities(Collection<GrantedAuthority> authorities) {
			this.authorities.addAll(authorities);
			return this;
		}

		/**
		 * 
		 * @param team
		 * @return this builder instance
		 */
		public Builder withTeam(Team team) {
			this.team = team;
			return this;
		}
	}

	/**
	 * Unique identifier of the user.
	 */
	@Getter
	private final UUID id;

	/**
	 * Version of the bean
	 */
	@Getter
	private final Long version;

	/**
	 * Unique userName.
	 */
	@Getter
	private final String username;

	/**
	 * User key.
	 */
	@Getter
	private String privateKey;

	/**
	 * Flag indicating if the user is enabled.
	 */
	@Getter
	private final boolean enabled;

	/**
	 * User's email.
	 */
	@Getter
	private final String email;

	/**
	 * Set of {@link Authority}s.
	 */
	@Getter
	private final Set<GrantedAuthority> authorities = new HashSet<>();
	
	/**
	 * Current team of the user.
	 */
	@Getter
	private final Team team;

	/**
	 * Static method to access the builder.
	 * 
	 * @return a new {@link Builder} instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Constructor. Builder pattern.
	 * 
	 * @param builder
	 */
	private User(Builder builder) {
		this.id = builder.id != null ? builder.id : UUID.randomUUID();
		this.version = builder.version != null ? builder.version : 0L;
		this.username = builder.username;
		this.privateKey = builder.privateKey;
		this.enabled = builder.enabled;
		this.email = builder.email;
		this.authorities.addAll(builder.authorities);
		this.team = builder.team;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.CredentialsContainer#eraseCredentials()
	 */
	@Override
	public void eraseCredentials() {
		this.privateKey = null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {
		return this.privateKey;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}
