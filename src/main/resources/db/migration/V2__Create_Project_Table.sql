CREATE SCHEMA projectschema;

ALTER SCHEMA projectschema OWNER TO admin;

CREATE TABLE projectschema.projects (
    id SERIAL PRIMARY KEY,
    title varchar(100) UNIQUE NOT NULL,
    description text NOT NULL,
    image varchar(255) NOT NULL,
    created_at timestamp without time zone,
    user_id INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES userschema.users(id) ON DELETE CASCADE
);

CREATE TABLE projectschema.posts (
    id SERIAL PRIMARY KEY,
    title varchar(100) NOT NULL,
    image varchar(255) NOT NULL,
    text text NOT NULL,
    created_at timestamp without time zone,
    project_id INT NOT NULL,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES projectschema.projects(id) ON DELETE CASCADE
);

ALTER TABLE projectschema.projects OWNER TO admin;
ALTER TABLE projectschema.posts OWNER TO admin;

GRANT USAGE ON SCHEMA projectschema TO hostuser;
GRANT USAGE ON SEQUENCE projectschema.projects_id_seq TO hostuser;
GRANT USAGE ON SEQUENCE projectschema.posts_id_seq TO hostuser;

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE projectschema.projects TO hostuser;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE projectschema.posts TO hostuser;
