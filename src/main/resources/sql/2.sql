DROP PROCEDURE IF EXISTS simple_proc;

DELIMITER //

CREATE PROCEDURE simple_proc (OUT param_1 INTEGER)
BEGIN
    SELECT count(*) INTO param_1 FROM USERS_TABLE;
END//

DELIMITER ;

INSERT INTO data_base_version (version) VALUES (2);

COMMIT;
