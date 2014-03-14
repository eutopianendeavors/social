DROP TABLE data_base_version;

DROP PROCEDURE simple_proc;

CREATE TABLE data_base_version
  (
    version          INTEGER NOT NULL,
    last_updated TIMESTAMP NOT NULL,
    PRIMARY KEY ( version )
  );

DELIMITER //

CREATE PROCEDURE simple_proc (OUT param_1 INTEGER)
BEGIN
    SELECT count(*) INTO param_1 FROM USERS_TABLE;
END//

DELIMITER ;

CREATE TRIGGER last_updated_trigger BEFORE INSERT ON data_base_version FOR EACH ROW SET NEW.last_updated = CURRENT_TIMESTAMP;

insert into data_base_version (version) values (1);

commit;
