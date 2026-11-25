

DROP DATABASE IF EXISTS gestion_academica;
CREATE DATABASE gestion_academica
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE gestion_academica;

-- ------------------------------------------------------------
-- Usuario (clase padre) con sus subclases Estudiante y Docente
-- model: Usuario(id, nombre, codigo)
-- tipo COORDINADOR: el Proxy del historial permite ese rol
-- ------------------------------------------------------------
CREATE TABLE usuario (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(100) NOT NULL,
    codigo  VARCHAR(20)  NOT NULL UNIQUE,
    tipo    ENUM('ESTUDIANTE','DOCENTE','COORDINADOR') NOT NULL
) ENGINE=InnoDB;

-- model: Estudiante(tieneDeuda)
CREATE TABLE estudiante (
    id           INT PRIMARY KEY,
    tiene_deuda  BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_estudiante_usuario
        FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- model: Docente(especialidad)
CREATE TABLE docente (
    id            INT PRIMARY KEY,
    especialidad  VARCHAR(100),
    CONSTRAINT fk_docente_usuario
        FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- PlanEstudio (patrón Builder)
-- model: PlanEstudio(carrera, anioVigencia, creditosTotales, requiereTesis)
-- ------------------------------------------------------------
CREATE TABLE plan_estudio (
    id_plan           INT AUTO_INCREMENT PRIMARY KEY,
    carrera           VARCHAR(100) NOT NULL,
    anio_vigencia     INT NOT NULL,
    creditos_totales  INT NOT NULL DEFAULT 0,
    requiere_tesis    BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_plan UNIQUE (carrera, anio_vigencia)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Módulo / Especialidad (patrón Composite: ModuloCompuesto)
-- Un módulo puede contener otros módulos (id_modulo_padre)
-- ------------------------------------------------------------
CREATE TABLE modulo (
    id_modulo        INT AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    id_plan          INT NOT NULL,
    id_modulo_padre  INT NULL,
    CONSTRAINT fk_modulo_plan  FOREIGN KEY (id_plan)         REFERENCES plan_estudio(id_plan),
    CONSTRAINT fk_modulo_padre FOREIGN KEY (id_modulo_padre) REFERENCES modulo(id_modulo)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Modalidad (patrón Factory Method: Presencial / Virtual)
-- ------------------------------------------------------------
CREATE TABLE modalidad (
    id_modalidad  INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(30) NOT NULL UNIQUE
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Curso
-- model: Curso(idCurso, nombre, creditos, vacantesDisponibles)
-- codigo: lo usa la plantilla (Prototype: codigoCurso)
-- costo_base: lo usa CursoBase (Decorator: costoBase)
-- id_docente: opcional, no está en el código (para saber quién dicta)
-- ------------------------------------------------------------
CREATE TABLE curso (
    id_curso              INT AUTO_INCREMENT PRIMARY KEY,
    codigo                VARCHAR(20)  NOT NULL UNIQUE,
    nombre                VARCHAR(100) NOT NULL,
    creditos              INT NOT NULL,
    vacantes_disponibles  INT NOT NULL DEFAULT 0,
    costo_base            DECIMAL(10,2) NOT NULL DEFAULT 0,
    id_plan               INT NOT NULL,
    id_modulo             INT NULL,
    id_modalidad          INT NOT NULL,
    id_docente            INT NULL,
    CONSTRAINT chk_curso_creditos CHECK (creditos > 0),
    CONSTRAINT chk_curso_vacantes CHECK (vacantes_disponibles >= 0),
    CONSTRAINT fk_curso_plan      FOREIGN KEY (id_plan)      REFERENCES plan_estudio(id_plan),
    CONSTRAINT fk_curso_modulo    FOREIGN KEY (id_modulo)    REFERENCES modulo(id_modulo),
    CONSTRAINT fk_curso_modalidad FOREIGN KEY (id_modalidad) REFERENCES modalidad(id_modalidad),
    CONSTRAINT fk_curso_docente   FOREIGN KEY (id_docente)   REFERENCES docente(id)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Plantilla de curso (patrón Prototype: PlantillaS)
-- ------------------------------------------------------------
CREATE TABLE plantilla_curso (
    id_plantilla          INT AUTO_INCREMENT PRIMARY KEY,
    codigo_curso          VARCHAR(20) NOT NULL UNIQUE,
    estructura_contenido  TEXT NOT NULL,
    CONSTRAINT fk_plantilla_curso
        FOREIGN KEY (codigo_curso) REFERENCES curso(codigo)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Matrícula
-- model: Matricula(idMatricula, estudiante, curso, estado, fechaRegistro)
-- estados: PENDIENTE (inicial), CONFIRMADA, ANULADA
-- ------------------------------------------------------------
CREATE TABLE matricula (
    id_matricula    INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante   INT NOT NULL,
    id_curso        INT NOT NULL,
    estado          ENUM('PENDIENTE','CONFIRMADA','ANULADA')
                    NOT NULL DEFAULT 'PENDIENTE',
    fecha_registro  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_matricula_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id),
    CONSTRAINT fk_matricula_curso      FOREIGN KEY (id_curso)      REFERENCES curso(id_curso),
    CONSTRAINT uq_matricula UNIQUE (id_estudiante, id_curso)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Historial de estados de la matrícula
-- (patrones State y Memento: MatriculaMemento guarda el estado previo)
-- ------------------------------------------------------------
CREATE TABLE historial_estado_matricula (
    id_historial     INT AUTO_INCREMENT PRIMARY KEY,
    id_matricula     INT NOT NULL,
    estado_anterior  VARCHAR(15) NOT NULL,
    estado_nuevo     VARCHAR(15) NOT NULL,
    fecha            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hist_matricula FOREIGN KEY (id_matricula) REFERENCES matricula(id_matricula)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Notas y asistencia
-- service: IServicioEvaluacion.registrarNota / registrarAsistencia
-- (Estudiante + Curso). periodo sale del historial: "2026-1: ... Nota: 18"
-- ------------------------------------------------------------
CREATE TABLE nota (
    id_nota        INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante  INT NOT NULL,
    id_curso       INT NOT NULL,
    periodo        VARCHAR(10) NOT NULL,
    nota           DECIMAL(4,2) NOT NULL,
    CONSTRAINT chk_nota CHECK (nota BETWEEN 0 AND 20),
    CONSTRAINT fk_nota_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id),
    CONSTRAINT fk_nota_curso      FOREIGN KEY (id_curso)      REFERENCES curso(id_curso),
    CONSTRAINT uq_nota UNIQUE (id_estudiante, id_curso, periodo)
) ENGINE=InnoDB;

CREATE TABLE asistencia (
    id_asistencia  INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante  INT NOT NULL,
    id_curso       INT NOT NULL,
    fecha          DATE NOT NULL,
    CONSTRAINT fk_asist_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id),
    CONSTRAINT fk_asist_curso      FOREIGN KEY (id_curso)      REFERENCES curso(id_curso),
    CONSTRAINT uq_asistencia UNIQUE (id_estudiante, id_curso, fecha)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Pagos (patrón Adapter: IPagoService.procesarPagoMatricula)
-- monto en soles (S/.)
-- ------------------------------------------------------------
CREATE TABLE pago (
    id_pago        INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante  INT NOT NULL,
    monto          DECIMAL(10,2) NOT NULL,
    fecha_pago     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_pago_monto CHECK (monto > 0),
    CONSTRAINT fk_pago_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Seguro y cobertura (patrón Bridge: Seguro + ICobertura)
-- ------------------------------------------------------------
CREATE TABLE cobertura (
    id_cobertura  INT AUTO_INCREMENT PRIMARY KEY,
    descripcion   VARCHAR(200) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE seguro (
    id_seguro      INT AUTO_INCREMENT PRIMARY KEY,
    id_estudiante  INT NOT NULL,
    tipo           VARCHAR(50) NOT NULL,       -- ej. 'CONVENIO' (SeguroConvenio)
    id_cobertura   INT NOT NULL,
    CONSTRAINT fk_seguro_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiante(id),
    CONSTRAINT fk_seguro_cobertura  FOREIGN KEY (id_cobertura)  REFERENCES cobertura(id_cobertura)
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Notificaciones (INotificacionService, Observer, Abstract Factory)
-- ------------------------------------------------------------
CREATE TABLE notificacion (
    id_notificacion  INT AUTO_INCREMENT PRIMARY KEY,
    destinatario     VARCHAR(20)  NOT NULL,      -- código del alumno
    mensaje          VARCHAR(255) NOT NULL,
    canal            ENUM('EMAIL') NOT NULL DEFAULT 'EMAIL',
    fecha            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Auditoría (AuditoriaService y AuditoriaManager - Singleton)
-- ------------------------------------------------------------
CREATE TABLE auditoria (
    id_auditoria  INT AUTO_INCREMENT PRIMARY KEY,
    fecha         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario       VARCHAR(20)  NOT NULL,        -- código o 'SISTEMA'
    operacion     VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Vistas (IServicioReportes y HistorialService)
-- ------------------------------------------------------------

-- generarReporteMatricula()
CREATE VIEW v_reporte_matricula AS
SELECT m.id_matricula, u.codigo, u.nombre AS estudiante,
       c.nombre AS curso, m.estado, m.fecha_registro
FROM matricula m
JOIN usuario u ON u.id = m.id_estudiante
JOIN curso c   ON c.id_curso = m.id_curso;

-- generarReporteRendimiento(idCarrera): promedio y aprobados por plan
-- (nota mínima aprobatoria = 13; ajústala si tu universidad usa otra)
CREATE VIEW v_reporte_rendimiento AS
SELECT p.id_plan, p.carrera,
       ROUND(AVG(n.nota), 2)                      AS promedio,
       SUM(n.nota >= 13)                          AS aprobados,
       COUNT(*)                                   AS total_notas
FROM nota n
JOIN curso c         ON c.id_curso = n.id_curso
JOIN plan_estudio p  ON p.id_plan = c.id_plan
GROUP BY p.id_plan, p.carrera;

-- consultarHistorial(codigo): "2026-1: Diseño de patrones - Nota: 18"
CREATE VIEW v_historial_academico AS
SELECT u.codigo,
       CONCAT(n.periodo, ': ', c.nombre, ' - Nota: ', n.nota) AS historial
FROM nota n
JOIN usuario u ON u.id = n.id_estudiante
JOIN curso c   ON c.id_curso = n.id_curso;

-- ------------------------------------------------------------
-- Triggers
-- ------------------------------------------------------------
DELIMITER //

-- ValidadorMatricula: sin deuda y con vacantes
CREATE TRIGGER trg_matricula_validar
BEFORE INSERT ON matricula
FOR EACH ROW
BEGIN
    IF (SELECT tiene_deuda FROM estudiante WHERE id = NEW.id_estudiante) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'VALIDACIÓN FALLIDA: el estudiante presenta deudas pendientes';
    END IF;
    IF (SELECT vacantes_disponibles FROM curso WHERE id_curso = NEW.id_curso) <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'VALIDACIÓN FALLIDA: el curso no tiene vacantes disponibles';
    END IF;
END//

-- Matricula.confirmar() / anular() + Memento + Auditoría
CREATE TRIGGER trg_matricula_cambio_estado
AFTER UPDATE ON matricula
FOR EACH ROW
BEGIN
    IF OLD.estado <> NEW.estado THEN
        INSERT INTO historial_estado_matricula (id_matricula, estado_anterior, estado_nuevo)
        VALUES (NEW.id_matricula, OLD.estado, NEW.estado);

        IF NEW.estado = 'CONFIRMADA' THEN
            -- Curso.reducirVacante()
            UPDATE curso
               SET vacantes_disponibles = vacantes_disponibles - 1
             WHERE id_curso = NEW.id_curso
               AND vacantes_disponibles > 0;

            INSERT INTO auditoria (usuario, operacion)
            VALUES ((SELECT codigo FROM usuario WHERE id = NEW.id_estudiante),
                    CONCAT('MATRÍCULA CONFIRMADA en ',
                           (SELECT nombre FROM curso WHERE id_curso = NEW.id_curso)));
        ELSEIF NEW.estado = 'ANULADA' THEN
            INSERT INTO auditoria (usuario, operacion)
            VALUES ('SISTEMA',
                    CONCAT('ANULACIÓN DE MATRÍCULA ID: ', NEW.id_matricula));
        END IF;
    END IF;
END//

DELIMITER ;

-- ------------------------------------------------------------
-- Datos de prueba
-- ------------------------------------------------------------
INSERT INTO usuario (nombre, codigo, tipo) VALUES
    ('Ana Torres',   'U2024001', 'ESTUDIANTE'),
    ('Luis Ramos',   'U2024002', 'ESTUDIANTE'),
    ('Carla Díaz',   'U2024003', 'ESTUDIANTE'),
    ('Dr. Pérez',    'D0001',    'DOCENTE'),
    ('Mg. Salazar',  'D0002',    'DOCENTE'),
    ('Lic. Gonzales','C0001',    'COORDINADOR');

INSERT INTO estudiante (id, tiene_deuda) VALUES
    (1, FALSE),
    (2, TRUE),      -- Luis tiene deuda: no puede matricularse
    (3, FALSE);

INSERT INTO docente (id, especialidad) VALUES
    (4, 'Ingeniería de Software'),
    (5, 'Bases de Datos');

INSERT INTO modalidad (nombre) VALUES ('Presencial'), ('Virtual');

INSERT INTO plan_estudio (carrera, anio_vigencia, creditos_totales, requiere_tesis) VALUES
    ('Ingeniería de Sistemas', 2024, 220, TRUE),
    ('Administración',         2024, 200, FALSE);

INSERT INTO modulo (nombre, id_plan, id_modulo_padre) VALUES
    ('Ingeniería de Software', 1, NULL),     -- 1
    ('Diseño y Arquitectura',  1, 1),         -- 2 (dentro del módulo 1)
    ('Datos',                  1, NULL);      -- 3

INSERT INTO curso (codigo, nombre, creditos, vacantes_disponibles, costo_base,
                   id_plan, id_modulo, id_modalidad, id_docente) VALUES
    ('DP101', 'Diseño de Patrones',   4, 30, 850.00, 1, 2, 1, 4),
    ('BD101', 'Bases de Datos',       4, 25, 850.00, 1, 3, 2, 5),
    ('SO101', 'Sistemas Operativos',  3,  1, 700.00, 1, 3, 1, 4);

INSERT INTO plantilla_curso (codigo_curso, estructura_contenido) VALUES
    ('DP101', 'Unidad 1: SOLID | Unidad 2: Creacionales | Unidad 3: Estructurales | Unidad 4: Comportamiento');

INSERT INTO matricula (id_estudiante, id_curso) VALUES
    (1, 1),
    (1, 2),
    (3, 3);

-- Confirmar matrículas (dispara el trigger: baja vacantes, historial y auditoría)
UPDATE matricula SET estado = 'CONFIRMADA' WHERE id_matricula IN (1, 2, 3);

INSERT INTO nota (id_estudiante, id_curso, periodo, nota) VALUES
    (1, 1, '2026-1', 18),
    (1, 2, '2026-1', 16);

INSERT INTO asistencia (id_estudiante, id_curso, fecha) VALUES
    (1, 1, '2026-08-10'),
    (1, 1, '2026-08-12');

INSERT INTO pago (id_estudiante, monto) VALUES
    (1, 850.00),
    (3, 700.00);

INSERT INTO cobertura (descripcion) VALUES
    ('Cobertura total del 100% en trámites y seguro médico.');

INSERT INTO seguro (id_estudiante, tipo, id_cobertura) VALUES
    (1, 'CONVENIO', 1),
    (3, 'CONVENIO', 1);

INSERT INTO notificacion (destinatario, mensaje) VALUES
    ('U2024001', 'Se ha registrado con éxito su matrícula en Diseño de Patrones'),
    ('U2024003', 'Se ha registrado con éxito su matrícula en Sistemas Operativos');

-- ------------------------------------------------------------
-- Consultas de ejemplo
-- ------------------------------------------------------------

-- 1) Reporte de matrículas
SELECT * FROM v_reporte_matricula;

-- 2) Reporte de rendimiento por carrera
SELECT * FROM v_reporte_rendimiento;

-- 3) Historial académico de un estudiante
SELECT historial FROM v_historial_academico WHERE codigo = 'U2024001';

-- 4) Cursos con vacantes, modalidad y docente
SELECT c.nombre AS curso, c.creditos, mo.nombre AS modalidad,
       u.nombre AS docente, c.vacantes_disponibles
FROM curso c
JOIN modalidad mo   ON mo.id_modalidad = c.id_modalidad
LEFT JOIN usuario u ON u.id = c.id_docente
WHERE c.vacantes_disponibles > 0;

-- 5) Créditos totales por módulo, sumando sus submódulos (Composite)
WITH RECURSIVE arbol AS (
    SELECT id_modulo, id_modulo AS raiz FROM modulo
    UNION ALL
    SELECT m.id_modulo, a.raiz
    FROM modulo m JOIN arbol a ON m.id_modulo_padre = a.id_modulo
)
SELECT mo.nombre AS modulo, COALESCE(SUM(c.creditos), 0) AS creditos_totales
FROM arbol a
JOIN modulo mo       ON mo.id_modulo = a.raiz
LEFT JOIN curso c    ON c.id_modulo = a.id_modulo
GROUP BY mo.id_modulo, mo.nombre;

-- 6) Estados por los que pasó cada matrícula (Memento)
SELECT * FROM historial_estado_matricula;

-- 7) Estudiantes con seguro y total pagado
SELECT u.nombre, s.tipo, co.descripcion, SUM(p.monto) AS total_pagado
FROM seguro s
JOIN usuario u    ON u.id = s.id_estudiante
JOIN cobertura co ON co.id_cobertura = s.id_cobertura
LEFT JOIN pago p  ON p.id_estudiante = s.id_estudiante
GROUP BY u.nombre, s.tipo, co.descripcion;

-- 8) Registro de auditoría
SELECT * FROM auditoria ORDER BY id_auditoria DESC;

-- Prueba de validación (descomenta para ver el error del trigger):
-- INSERT INTO matricula (id_estudiante, id_curso) VALUES (2, 1);
