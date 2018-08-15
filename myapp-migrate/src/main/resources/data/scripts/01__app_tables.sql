
/* Drop Tables */

DROP TABLE IF EXISTS mjr_role_action;
DROP TABLE IF EXISTS mjr_action;
DROP TABLE IF EXISTS mjr_login_history;
DROP TABLE IF EXISTS mjr_user_role;
DROP TABLE IF EXISTS mjr_role;
DROP TABLE IF EXISTS mjr_setting;
DROP TABLE IF EXISTS mjr_user;
DROP TABLE IF EXISTS mjr_static_content;




/* Create Tables */

CREATE TABLE mjr_action
(
	id bigint NOT NULL UNIQUE,
	page varchar(20) NOT NULL,
	action_name varchar(50) NOT NULL,
	display_name varchar(50) NOT NULL,
	-- 0[main-action that is the main page action] 
	-- 1[sub-action that process within a page]
	-- 
	action_type smallint NOT NULL,
	url varchar NOT NULL,
	description varchar(200) NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT module_and_action_uniquekey UNIQUE (action_name)
) WITHOUT OIDS;


CREATE TABLE mjr_login_history
(
	id bigint NOT NULL UNIQUE,
	user_id int NOT NULL,
	ip_address varchar(45) NOT NULL,
	-- Operating System
	os varchar(100),
	user_agent varchar(100),
	login_date timestamp NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE mjr_role
(
	id bigint NOT NULL UNIQUE,
	name varchar(20) NOT NULL,
	type smallint DEFAULT 1 NOT NULL,
	description varchar(200) NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT company_product_role_unique UNIQUE (name, type)
) WITHOUT OIDS;


CREATE TABLE mjr_role_action
(
	role_id bigint NOT NULL,
	action_id bigint NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL
) WITHOUT OIDS;


CREATE TABLE mjr_setting
(
	id bigint NOT NULL UNIQUE,
	setting_name varchar(50) NOT NULL UNIQUE,
	setting_value varchar(200) NOT NULL,
	setting_type varchar(50) NOT NULL,
	setting_group varchar(50) NOT NULL,
	setting_sub_group varchar(50) NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT complx_setting UNIQUE (setting_name, setting_value, setting_type, setting_group, setting_sub_group)
) WITHOUT OIDS;


CREATE TABLE mjr_static_content
(
	id bigint NOT NULL UNIQUE,
	file_name varchar NOT NULL,
	file_path varchar NOT NULL,
	file_size varchar(20),
	file_type smallint NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE mjr_user
(
	id bigint NOT NULL UNIQUE,
	content_id bigint,
	name varchar(50),
	email varchar(50) NOT NULL UNIQUE,
	password varchar(500) NOT NULL,
	nrc varchar(50) NOT NULL UNIQUE,
	phone varchar(50) NOT NULL UNIQUE,
	status smallint DEFAULT 0 NOT NULL,
	age int,
	gender smallint,
	dob date,
	address varchar(200),
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;


CREATE TABLE mjr_user_role
(
	user_id bigint NOT NULL,
	role_id bigint NOT NULL,
	record_reg_id bigint NOT NULL,
	record_upd_id bigint NOT NULL,
	record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
	record_upd_date timestamp DEFAULT current_timestamp NOT NULL
) WITHOUT OIDS;



/* Create Foreign Keys */

ALTER TABLE mjr_role_action
	ADD FOREIGN KEY (action_id)
	REFERENCES mjr_action (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mjr_role_action
	ADD FOREIGN KEY (role_id)
	REFERENCES mjr_role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mjr_user_role
	ADD FOREIGN KEY (role_id)
	REFERENCES mjr_role (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE mjr_user
	ADD CONSTRAINT fk_user_content FOREIGN KEY (content_id)
	REFERENCES mjr_static_content (id)
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
;


ALTER TABLE mjr_user_role
	ADD FOREIGN KEY (user_id)
	REFERENCES mjr_user (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



/* Comments */

COMMENT ON COLUMN mjr_action.action_type IS '0[main-action that is the main page action] 
1[sub-action that process within a page]
';
COMMENT ON COLUMN mjr_login_history.os IS 'Operating System';



