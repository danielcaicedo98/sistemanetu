-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-08-2021 a las 03:25:51
-- Versión del servidor: 10.4.20-MariaDB
-- Versión de PHP: 7.3.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemanetu`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `id_Empleado` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`id_Empleado`) VALUES
(1841205);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `chat`
--

CREATE TABLE `chat` (
  `codigo_Empleado_1` int(11) NOT NULL,
  `codigo_Empleado_2` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `mensaje` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dependencia`
--

CREATE TABLE `dependencia` (
  `id_Dependencia` int(11) NOT NULL,
  `nombre_Dependencia` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `dependencia`
--

INSERT INTO `dependencia` (`id_Dependencia`, `nombre_Dependencia`) VALUES
(5, 'DEPARTAMENTOS'),
(4, 'ESCUELAS'),
(2, 'FACULTADES'),
(3, 'INSTITUTOS'),
(1, 'VICERRECTORÍA');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `codigo_Empleado` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `sexo` varchar(10) NOT NULL,
  `dependencia` int(11) NOT NULL,
  `subdependencia` int(11) NOT NULL,
  `descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`codigo_Empleado`, `nombre`, `correo`, `sexo`, `dependencia`, `subdependencia`, `descripcion`) VALUES
(1841205, 'Camila', 'camila.sanchez@correounivalle.edu.co', 'Femenino', 2, 5, ''),
(1925763, 'Dilan Caicedo', 'dilan.bergano@correounivalle.edu.co', 'Masculino', 4, 27, ''),
(1925765, 'Daniel Caicedo', 'daniel.andres.caicedo@correounivalle.edu.co', 'Masculino', 4, 26, 'i');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

CREATE TABLE `login` (
  `codigo_Empleado` int(11) NOT NULL,
  `contraseña` varchar(25) NOT NULL,
  `estado` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `login`
--

INSERT INTO `login` (`codigo_Empleado`, `contraseña`, `estado`) VALUES
(1925765, 'password3', 0),
(1925763, 'igacifiabd', 0),
(1841205, 'ijhgfefjcg', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE `mensaje` (
  `codigo_Empleado_1` int(11) NOT NULL,
  `codigo_Empleado_2` int(11) NOT NULL,
  `abierto` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificaciones`
--

CREATE TABLE `notificaciones` (
  `codigo_Empleado_1` int(11) NOT NULL,
  `codigo_Empleado_2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion`
--

CREATE TABLE `publicacion` (
  `id_Publicacion` int(11) NOT NULL,
  `codigo_Empleado` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `contenido` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subdependencia`
--

CREATE TABLE `subdependencia` (
  `id_Subdependencia` int(11) NOT NULL,
  `nombre_Subdependencia` varchar(50) NOT NULL,
  `id_Dependencia` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `subdependencia`
--

INSERT INTO `subdependencia` (`id_Subdependencia`, `nombre_Subdependencia`, `id_Dependencia`) VALUES
(1, 'ACADÉMICA', 1),
(2, 'ADMINISTRATIVA', 1),
(3, 'BIENESTAR UNIVERSITARIO', 1),
(4, 'INVESTIGACIONES', 1),
(5, 'ARTES INTEGRADAS', 2),
(6, 'CIENCIAS NATURALES Y EXACTAS', 2),
(7, 'CIENCIAS DE LA ADMINISTRACIÓN', 2),
(8, 'SALUD', 2),
(9, 'CIENCIAS SOCIALES Y ECONÓMICAS', 2),
(10, 'HUMANIDADES', 2),
(11, 'INGENIERÍA', 2),
(12, 'EDUCACIÓN Y PEDAGOGÍA', 3),
(13, 'PSICOLOGÍA', 3),
(14, 'ARQUITECTURA', 4),
(15, 'BACTEROLIOGÍA Y LABORATORIO CLÍNICO', 4),
(16, 'CIENCIAS BÁSICAS', 4),
(17, 'CIENCIAS DEL LENGUAJE', 4),
(18, 'COMUNICACIÓN SOCIAL', 4),
(19, 'ENFERMERÍA', 4),
(20, 'ESTADÍSTICA', 4),
(21, 'ESTUDIOS LITERARIOS', 4),
(22, 'INGENIERÍA CIVIL Y GEOMÁTICA', 4),
(23, 'INGENIERÍA DE ALIMENTOS', 4),
(24, 'INGENIERÍA DE MATERIALES', 4),
(25, 'INGENIERÍA DE RECURSOS NATURALES Y DEL AMBIENTE', 4),
(26, 'INGENIERÍA DE SISTEMAS Y COMPUTACIÓN', 4),
(27, 'INGENIERÍA ELÉCTRICA Y ELECTRÓNICA', 4),
(28, 'INGENIERÍA INDUSTRIAL', 4),
(29, 'INGENIERÍA MECÁNICA', 4),
(30, 'INGENIERÍA QUÍMICA', 4),
(31, 'MEDICINA', 4),
(32, 'MÚSICA', 4),
(33, 'ODONTOLOGÍA', 4),
(34, 'REHABILITACIÓN HUMANA', 4),
(35, 'SALUD PÚBLICA', 4),
(36, 'ARTES ESCÉNICAS', 5),
(37, 'ARTES VISUALES Y ESTÉTICA', 5),
(38, 'BIOLOGÍA', 5),
(39, 'CIENCIAS SOCIALES', 5),
(40, 'DISEÑO', 5),
(41, 'DIRECCIÓN Y GESTIÓN ADMINISTRATIVA', 5),
(42, 'ECONOMÍA', 5),
(43, 'FILOSOFÍA', 5),
(44, 'FÍSICA', 5),
(45, 'GEOGRAFÍA', 5),
(46, 'HISTORIA', 5),
(47, 'MATEMÁTICAS', 5),
(48, 'MEDICINA INTERNA', 5),
(49, 'PROCESOS, INFORMACIÓN, CONTABILIDAD Y FINANZAS', 5),
(50, 'QUÍMICA', 5);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD KEY `administrador_FK` (`id_Empleado`);

--
-- Indices de la tabla `chat`
--
ALTER TABLE `chat`
  ADD KEY `codigo_Empleado_1` (`codigo_Empleado_1`),
  ADD KEY `codigo_Empleado_2` (`codigo_Empleado_2`);

--
-- Indices de la tabla `dependencia`
--
ALTER TABLE `dependencia`
  ADD PRIMARY KEY (`id_Dependencia`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre_Dependencia`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`codigo_Empleado`),
  ADD KEY `dependencia` (`dependencia`),
  ADD KEY `subdependencia` (`subdependencia`);

--
-- Indices de la tabla `login`
--
ALTER TABLE `login`
  ADD KEY `empleadoFK` (`codigo_Empleado`);

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD KEY `codigo_Empleado_1` (`codigo_Empleado_1`),
  ADD KEY `codigo_Empleado_2` (`codigo_Empleado_2`);

--
-- Indices de la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD KEY `codigo_Empleado_1` (`codigo_Empleado_1`),
  ADD KEY `codigo_Empleado_2` (`codigo_Empleado_2`);

--
-- Indices de la tabla `publicacion`
--
ALTER TABLE `publicacion`
  ADD PRIMARY KEY (`id_Publicacion`),
  ADD KEY `codigo_Empleado` (`codigo_Empleado`);

--
-- Indices de la tabla `subdependencia`
--
ALTER TABLE `subdependencia`
  ADD PRIMARY KEY (`id_Subdependencia`),
  ADD UNIQUE KEY `nombre_UNIQUE` (`nombre_Subdependencia`),
  ADD KEY `DependenciaSubdependenciaFK` (`id_Dependencia`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `publicacion`
--
ALTER TABLE `publicacion`
  MODIFY `id_Publicacion` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD CONSTRAINT `administrador_FK` FOREIGN KEY (`id_Empleado`) REFERENCES `empleado` (`codigo_Empleado`);

--
-- Filtros para la tabla `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `empleado1FK` FOREIGN KEY (`codigo_Empleado_1`) REFERENCES `empleado` (`codigo_Empleado`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `empleado2FK` FOREIGN KEY (`codigo_Empleado_2`) REFERENCES `empleado` (`codigo_Empleado`);

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`dependencia`) REFERENCES `dependencia` (`id_Dependencia`),
  ADD CONSTRAINT `empleado_ibfk_2` FOREIGN KEY (`subdependencia`) REFERENCES `subdependencia` (`id_Subdependencia`);

--
-- Filtros para la tabla `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `empleadoFK` FOREIGN KEY (`codigo_Empleado`) REFERENCES `empleado` (`codigo_Empleado`);

--
-- Filtros para la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD CONSTRAINT `Empleado1MensajeFK` FOREIGN KEY (`codigo_Empleado_1`) REFERENCES `empleado` (`codigo_Empleado`),
  ADD CONSTRAINT `Empleado2MensajeFK` FOREIGN KEY (`codigo_Empleado_2`) REFERENCES `empleado` (`codigo_Empleado`);

--
-- Filtros para la tabla `notificaciones`
--
ALTER TABLE `notificaciones`
  ADD CONSTRAINT `Empleado1NotificacionFK` FOREIGN KEY (`codigo_Empleado_1`) REFERENCES `empleado` (`codigo_Empleado`),
  ADD CONSTRAINT `Empleado2NotificacionFK` FOREIGN KEY (`codigo_Empleado_2`) REFERENCES `empleado` (`codigo_Empleado`);

--
-- Filtros para la tabla `publicacion`
--
ALTER TABLE `publicacion`
  ADD CONSTRAINT `EmpleadoPublicacionFK` FOREIGN KEY (`codigo_Empleado`) REFERENCES `empleado` (`codigo_Empleado`);

--
-- Filtros para la tabla `subdependencia`
--
ALTER TABLE `subdependencia`
  ADD CONSTRAINT `DependenciaSubdependenciaFK` FOREIGN KEY (`id_Dependencia`) REFERENCES `dependencia` (`id_Dependencia`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
