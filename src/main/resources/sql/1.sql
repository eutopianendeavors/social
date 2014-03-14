DROP TABLE IF EXISTS data_base_version;

CREATE TABLE data_base_version
  (
    version          INTEGER NOT NULL,
    last_updated TIMESTAMP NOT NULL,
    PRIMARY KEY ( version )
  );

CREATE TRIGGER last_updated_trigger BEFORE INSERT ON data_base_version FOR EACH ROW SET NEW.last_updated = CURRENT_TIMESTAMP;

INSERT INTO data_base_version (version) VALUES (1);

COMMIT;