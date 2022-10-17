DROP DATABASE IF EXISTS  matricula_paradigmas;
CREATE DATABASE matricula_paradigmas;

USE matricula_paradigmas;
DROP USER IF EXISTS 'matricula_admin'@'%';
CREATE USER 'matricula_admin'@'%' IDENTIFIED BY 'root123';
GRANT SELECT ON matricula_paradigmas.* TO 'matricula_admin'@'%';
GRANT INSERT ON matricula_paradigmas.* TO 'matricula_admin'@'%';
GRANT UPDATE ON matricula_paradigmas.* TO 'matricula_admin'@'%';
GRANT DELETE ON matricula_paradigmas.* TO 'matricula_admin'@'%';
GRANT CREATE ON matricula_paradigmas.* TO 'matricula_admin'@'%';

CREATE TABLE persona
(
    id_persona     BIGINT       NOT NULL AUTO_INCREMENT,
    identificacion VARCHAR(12)  NOT NULL UNIQUE,
    nombre         VARCHAR(120) NOT NULL,
    PRIMARY KEY (id_persona),
    INDEX (identificacion)
);

CREATE TABLE periodo
(
    id_periodo  BIGINT       NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_periodo)
);


CREATE TABLE materia
(
    id_materia  BIGINT       NOT NULL AUTO_INCREMENT,
    descripcion VARCHAR(255) NOT NULL,
    periodo_id  BIGINT       NOT NULL,
    cupos       INT          NOT NULL,
    FOREIGN KEY (periodo_id) REFERENCES periodo (id_periodo) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id_materia)
);

CREATE TABLE matricula
(
    id_matricula BIGINT NOT NULL AUTO_INCREMENT,
    persona_id   BIGINT NOT NULL,
    materia_id   BIGINT NOT NULL,
    FOREIGN KEY (persona_id) REFERENCES persona (id_persona) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (materia_id) REFERENCES materia (id_materia) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (id_matricula)
);

DELIMITER //

DROP PROCEDURE IF EXISTS update_cupos //
CREATE PROCEDURE update_cupos (IN id_materia BIGINT, IN operation SMALLINT) BEGIN
UPDATE matricula_paradigmas.materia
SET matricula_paradigmas.materia.cupos= matricula_paradigmas.materia.cupos + (if(operation = 1, 1, -1))
WHERE matricula_paradigmas.materia.id_materia = id_materia;
END //

DROP PROCEDURE IF EXISTS check_has_cupos //
CREATE PROCEDURE check_has_cupos (IN materia BIGINT, OUT cupos_result INT) BEGIN
    DECLARE current_cupos INT;
    SET current_cupos := (SELECT cupos FROM matricula_paradigmas.materia WHERE id_materia= materia);
    IF current_cupos > 0 THEN
        SET cupos_result := current_cupos;
    ELSEIF current_cupos <= 0 THEN
        SET cupos_result := -1;
END IF;
end //

DROP TRIGGER IF EXISTS ins_matricula//
CREATE TRIGGER  ins_matricula BEFORE INSERT ON matricula_paradigmas.matricula FOR EACH ROW
BEGIN
    DECLARE current_cupos integer;
    CALL check_has_cupos(NEW.materia_id,current_cupos);
    IF current_cupos = -1 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Limite de cupos alcanzado';
    ELSEIF current_cupos > 0 THEN
        CALL update_cupos(NEW.materia_id,0);
END IF;
END //

DROP TRIGGER IF EXISTS upd_matricula//
CREATE TRIGGER  upd_matricula BEFORE UPDATE ON matricula_paradigmas.matricula FOR EACH ROW
BEGIN
    DECLARE current_cupos integer;
    IF NEW.materia_id != OLD.materia_id THEN
        CALL check_has_cupos(NEW.materia_id,current_cupos);
        IF current_cupos = -1 THEN
            SIGNAL SQLSTATE '45001'
                SET MESSAGE_TEXT = 'Actualizacion invalida';
        ELSEIF current_cupos > 0 THEN
            CALL update_cupos(OLD.materia_id,1);
    CALL update_cupos(NEW.materia_id,0);
END IF;
END IF;
END //

DELIMITER ;



