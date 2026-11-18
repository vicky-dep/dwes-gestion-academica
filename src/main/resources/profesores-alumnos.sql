USE gestion_academica;

CREATE TABLE IF NOT EXISTS `tutoria_tfg` (
  `profesor_id` INT UNSIGNED NOT NULL,
  `alumno_id` INT UNSIGNED NOT NULL,
  `nota` DECIMAL(4,2) NULL,
  `observaciones` VARCHAR(500),
  PRIMARY KEY (`profesor_id`, `alumno_id`),
  INDEX `fk_tutoria_tfg_alumno_idx` (`alumno_id` ASC),
  CONSTRAINT `fk_tutoria_tfg_profesor`
    FOREIGN KEY (`profesor_id`)
    REFERENCES `profesor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tutoria_tfg_alumno`
    FOREIGN KEY (`alumno_id`)
    REFERENCES `alumno` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;