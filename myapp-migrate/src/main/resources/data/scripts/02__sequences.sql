-- User
CREATE SEQUENCE mjr_user_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_user_seq OWNER TO postgres;
ALTER TABLE mjr_user ALTER COLUMN id SET DEFAULT nextval('mjr_user_seq');