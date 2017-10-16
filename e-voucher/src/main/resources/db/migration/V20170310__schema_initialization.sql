DROP TABLE IF EXISTS public.users;
CREATE TABLE public.users (
	id UUID NOT NULL,
	version BIGINT NOT NULL DEFAULT 1,
	username VARCHAR(50) NOT NULL,
	secretkey  VARCHAR(128) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	email VARCHAR(50),
	PRIMARY KEY (id),
	CONSTRAINT uk_users__1 UNIQUE(username)
);

DROP TABLE IF EXISTS public.evouchers;
CREATE TABLE public.evouchers (
	id UUID NOT NULL,
	version BIGINT NOT NULL DEFAULT 1,
	created_by UUID NOT NULL,
	created_date TIMESTAMP NOT NULL,
	last_modified_by UUID,
	last_modified_date TIMESTAMP,
	name VARCHAR(75) NOT NULL,
	email VARCHAR(50),
	request_date TIMESTAMP,
	amount DECIMAL NOT NULL DEFAULT 0.0,
	status VARCHAR(50) NOT NULL,
	distribution_year INT NOT NULL,
	team_number INT NOT NULL,
	PRIMARY KEY (id),
	
	CONSTRAINT fk_evouchers__users_1 FOREIGN KEY(created_by) REFERENCES users(id),
	CONSTRAINT fk_evouchers__users_2 FOREIGN KEY(last_modified_by) REFERENCES users(id)
	
);

DROP TABLE IF EXISTS public.evoucher_events;
CREATE TABLE public.evoucher_events (
	id IDENTITY,
	evoucher_id UUID NOT NULL,
	event_type VARCHAR(50) NOT NULL,
	created_by UUID NOT NULL,
	created_date TIMESTAMP NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_evoucher_events__evouchers FOREIGN KEY(evoucher_id) REFERENCES evouchers(id),
	CONSTRAINT fk_evouchers_events__users FOREIGN KEY(created_by) REFERENCES users(id)
);


DROP TABLE IF EXISTS public.team_members;
CREATE TABLE public.team_members (
	user_id UUID NOT NULL,
	distribution_year INT NOT NULL,
	team_number INT NOT NULL,
	validity_start TIMESTAMP NOT NULL,
	validity_end TIMESTAMP NOT NULL,
	PRIMARY KEY (user_id, distribution_year, team_number),
	CONSTRAINT fk_team_members__users FOREIGN KEY(user_id) REFERENCES users(id)
);

DROP TABLE IF EXISTS public.authorities;
CREATE TABLE public.authorities (
    user_id UUID NOT NULL,
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, authority),
    CONSTRAINT fk_authorities__users FOREIGN KEY(user_id) REFERENCES users(id)
);


CREATE INDEX ix_evouchers__status ON public.evouchers(status);
CREATE INDEX ix_evoucher_events__evoucher_id ON public.evoucher_events(evoucher_id);
CREATE INDEX ix_users__username ON public.users(username);
CREATE INDEX ix_team_members__year ON public.team_members(distribution_year);
CREATE INDEX ix_team_members__number ON public.team_members(team_number);
--...
