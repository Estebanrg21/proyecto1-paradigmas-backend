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
    identificacion VARCHAR(12)  NOT NULL UNIQUE CHECK (identificacion <> ''),
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

CREATE TABLE log
(
    id_log BIGINT NOT NULL AUTO_INCREMENT,
    metodo VARCHAR(255) NOT NULL,
    entidad VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL DEFAULT (CURRENT_DATE),
    PRIMARY KEY (id_log)
);


