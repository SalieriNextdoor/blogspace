CREATE SCHEMA userschema;

ALTER SCHEMA userschema OWNER TO admin;

CREATE SEQUENCE userschema.user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE userschema.user_id_seq OWNER TO admin;

CREATE TABLE userschema.users (
      id integer DEFAULT nextval('userschema.user_id_seq'::regclass) NOT NULL,
      username character varying(100),
      email character varying(250),
      password character varying(100),
      created_at timestamp without time zone
);

ALTER TABLE userschema.users OWNER TO admin;

ALTER TABLE ONLY userschema.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

GRANT ALL ON SCHEMA userschema TO hostuser;

GRANT SELECT,USAGE ON SEQUENCE userschema.user_id_seq TO hostuser;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE userschema.users TO hostuser;
