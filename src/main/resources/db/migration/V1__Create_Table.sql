CREATE SCHEMA userschema;

ALTER SCHEMA userschema OWNER TO admin;

CREATE TABLE userschema.users (
      id SERIAL PRIMARY KEY,
      username character varying(100) UNIQUE,
      email character varying(250) UNIQUE,
      password character varying(100),
      created_at timestamp without time zone
);

ALTER TABLE userschema.users OWNER TO admin;

GRANT USAGE ON SCHEMA userschema TO hostuser;
GRANT USAGE ON SEQUENCE userschema.users_id_seq TO hostuser;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE userschema.users TO hostuser;
