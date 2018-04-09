/**
 * 
 */
package org.bspv.evoucher.core.model;

import java.util.Collection;
import java.util.UUID;

import org.bspv.security.model.ServiceGrantedAuthority;
import org.springframework.security.core.CredentialsContainer;

import lombok.Getter;

/**
 * Represents a user of this service.
 * This class is not immutable as the {@link CredentialsContainer} interface should let the password/key to be erased.
 */
public final class User extends org.bspv.security.model.User {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 3180450620024125623L;

	/**
	 * Builder class for {@link User}.
	 * 
	 */
	public static class Builder extends org.bspv.security.model.User.Builder {

		/** @see User#team */
		private Team team;

		/**
		 * Constructor of the builder.
		 * 
		 */
		private Builder() {
			super();
		}
		
        private Builder fromUser(User user) {
            super.fromUser(user);
            this.team = user.team;
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#id(java.util.UUID)
         */
        @Override
        public Builder id(UUID id) {
            super.id(id);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#version(java.lang.Long)
         */
        @Override
        public Builder version(Long version) {
            super.version(version);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#username(java.lang.String)
         */
        @Override
        public Builder username(String userName) {
            super.username(userName);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#email(java.lang.String)
         */
        @Override
        public Builder email(String email) {
            super.email(email);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#password(java.lang.String)
         */
        @Override
        public Builder password(String password) {
            super.password(password);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#enable(boolean)
         */
        @Override
        public Builder enable(boolean enabled) {
            super.enable(enabled);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#authority(org.bspv.security.model.ServiceGrantedAuthority)
         */
        @Override
        public Builder authority(ServiceGrantedAuthority authority) {
            super.authority(authority);
            return this;
        }

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#authorities(java.util.Collection)
         */
        @Override
        public Builder authorities(Collection<ServiceGrantedAuthority> authorities) {
            super.authorities(authorities);
            return this;
        }

        /**
		 * 
		 * @param team
		 * @return this builder instance
		 */
		public Builder team(Team team) {
			this.team = team;
			return this;
		}

        /* (non-Javadoc)
         * @see org.bspv.security.model.User.Builder#build()
         */
        @Override
        public User build() {
            return new User(this);
        }
		
		
	}
	
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
	    super(builder);
		this.team = builder.team;
	}

    /* (non-Javadoc)
     * @see org.bspv.security.model.User#toBuilder()
     */
    @Override
    public Builder toBuilder() {
        return User.builder().fromUser(this);
    }
}
