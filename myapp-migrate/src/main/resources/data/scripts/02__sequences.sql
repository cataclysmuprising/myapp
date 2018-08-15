-- User
CREATE SEQUENCE mjr_user_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_user_seq OWNER TO postgres;
ALTER TABLE mjr_user ALTER COLUMN id SET DEFAULT nextval('mjr_user_seq');

-- Static Content
CREATE SEQUENCE mjr_static_content_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_static_content_seq OWNER TO postgres;
ALTER TABLE mjr_static_content ALTER COLUMN id SET DEFAULT nextval('mjr_static_content_seq');

-- Role
CREATE SEQUENCE mjr_role_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_role_seq OWNER TO postgres;
ALTER TABLE mjr_role ALTER COLUMN id SET DEFAULT nextval('mjr_role_seq');

-- Action
CREATE SEQUENCE mjr_action_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_action_seq OWNER TO postgres;
ALTER TABLE mjr_action ALTER COLUMN id SET DEFAULT nextval('mjr_action_seq');

-- Setting
CREATE SEQUENCE mjr_setting_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_setting_seq OWNER TO postgres;
ALTER TABLE mjr_setting ALTER COLUMN id SET DEFAULT nextval('mjr_setting_seq');

-- Login History
CREATE SEQUENCE mjr_login_history_seq
	START 1000
    INCREMENT 1
   	NO MAXVALUE
    CACHE 1;
	
ALTER SEQUENCE mjr_login_history_seq OWNER TO postgres;
ALTER TABLE mjr_login_history ALTER COLUMN id SET DEFAULT nextval('mjr_login_history_seq');