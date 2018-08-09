
/* Drop Tables */

DROP TABLE IF EXISTS mjr_user;




/* Create Tables */

CREATE TABLE mjr_user
(
  id              bigint NOT NULL UNIQUE,
  content_id      bigint,
  name            varchar(50),
  email           varchar(50) NOT NULL UNIQUE,
  password        varchar(500) NOT NULL,
  nrc             varchar(50) NOT NULL UNIQUE,
  phone           varchar(50) NOT NULL UNIQUE,
  status          smallint DEFAULT 0 NOT NULL,
  age             int,
  gender          smallint,
  dob             date,
  address         varchar(200),
  record_reg_id   bigint NOT NULL,
  record_upd_id   bigint NOT NULL,
  record_reg_date timestamp DEFAULT current_timestamp NOT NULL,
  record_upd_date timestamp DEFAULT current_timestamp NOT NULL,
	PRIMARY KEY (id)
) WITHOUT OIDS;



