-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-02-2024 a las 09:41:21
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `revistas`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `action`
--

CREATE TABLE `action` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `type_action` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `action`
--

INSERT INTO `action` (`id`, `description`, `n_delete_state`, `type_action`) VALUES
(1, 'Creación del cuestionario', 1, 'Creación del cuestionario'),
(2, 'Solicitud de evaluación', 1, 'Solicitud de evaluación'),
(3, 'Envío de observaciones del evaluador', 1, 'Envío de observaciones del evaluador'),
(4, 'Modificación tras observaciones del evaluador', 1, 'Modificación tras observaciones del evaluador'),
(5, 'Cierre manual', 1, 'Cierre manual'),
(6, 'Cierre automático por fin de la edición', 1, 'Cierre automático por fin de la edición');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `assigned_evaluators_entity`
--

CREATE TABLE `assigned_evaluators_entity` (
  `id` bigint(20) NOT NULL,
  `dnet_id` varchar(255) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `evaluation_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cat_questions`
--

CREATE TABLE `cat_questions` (
  `id` bigint(20) NOT NULL,
  `category_type` varchar(255) DEFAULT NULL,
  `ndelete_state` int(11) DEFAULT NULL,
  `orden` double DEFAULT NULL,
  `tooltip_type` varchar(255) DEFAULT NULL,
  `questionnaire_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cat_questions`
--

INSERT INTO `cat_questions` (`id`, `category_type`, `ndelete_state`, `orden`, `tooltip_type`, `questionnaire_type_id`) VALUES
(1, 'FUNDING', 1, 1, 'Funding', 1),
(2, 'GOVERNANCE', 1, 2, 'Legal Ownership, Mission and Governance', 1),
(3, 'OPEN SCIENCE', 1, 3, 'Open Science Practices', 1),
(4, 'EDITION', 1, 4, 'Editorial Management, Editorial Quality and Research Integrity', 1),
(5, 'TECHNICAL', 1, 5, 'Technical service efficiency', 1),
(6, 'VISIBILITY', 1, 6, 'Visibility, Communication, Marketing and Impact', 1),
(7, 'EDIB', 1, 7, 'Equity, Diversity, Inclusion and Belonging (EDIB), Gender and Multilingualism', 1),
(8, 'Costs', 1, 1, 'Costs', 2),
(9, 'Resources ', 1, 2, 'Resources ', 2),
(10, 'Income', 1, 3, 'Income', 2),
(11, 'Infrastructure', 1, 4, 'Infrastructure / shared services ', 2),
(12, 'Growth ', 1, 5, 'Growth ', 2),
(13, 'Control', 1, 6, 'Control, monitor and manage finances', 2),
(14, 'Plan', 1, 7, 'Plan for the future', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `config`
--

CREATE TABLE `config` (
  `id` bigint(20) NOT NULL,
  `automatic_register` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `config`
--

INSERT INTO `config` (`id`, `automatic_register`) VALUES
(1, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluation`
--

CREATE TABLE `evaluation` (
  `id` bigint(20) NOT NULL,
  `close_date` datetime DEFAULT NULL,
  `evaluation_grade` double DEFAULT NULL,
  `evaluation_state` varchar(255) DEFAULT NULL,
  `last_edited` datetime DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `questionnaire_type_id` bigint(20) DEFAULT NULL,
  `repository_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `evaluation`
--

INSERT INTO `evaluation` (`id`, `close_date`, `evaluation_grade`, `evaluation_state`, `last_edited`, `n_delete_state`, `questionnaire_type_id`, `repository_id`) VALUES
(1, NULL, 0, 'Enviado', '2024-02-29 06:40:43', 1, 2, 3),
(2, NULL, 0, 'Pendiente', '2024-02-29 06:48:50', 1, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluation_action_history_entity`
--

CREATE TABLE `evaluation_action_history_entity` (
  `id` bigint(20) NOT NULL,
  `last_edited` datetime DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `action_id_id` bigint(20) DEFAULT NULL,
  `evaluation_id_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `evaluation_action_history_entity`
--

INSERT INTO `evaluation_action_history_entity` (`id`, `last_edited`, `n_delete_state`, `action_id_id`, `evaluation_id_id`, `user_id`) VALUES
(1, '2024-02-29 07:40:44', 1, 1, 1, 3),
(2, '2024-02-29 07:48:51', 1, 1, 2, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluation_period`
--

CREATE TABLE `evaluation_period` (
  `id` bigint(20) NOT NULL,
  `delete_state` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `finish_date` datetime DEFAULT NULL,
  `start_date` datetime NOT NULL,
  `status` varchar(255) NOT NULL,
  `questionnaire_type_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `evaluation_period`
--

INSERT INTO `evaluation_period` (`id`, `delete_state`, `description`, `finish_date`, `start_date`, `status`, `questionnaire_type_id`) VALUES
(1, 1, '1111', '2400-01-19 23:00:00', '2000-01-19 23:00:00', 'Abierto', 2),
(2, 1, '543', '2400-01-19 23:00:00', '2000-01-19 23:00:00', 'Abierto', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluation_response_entity`
--

CREATE TABLE `evaluation_response_entity` (
  `id` bigint(20) NOT NULL,
  `evaluator_commentary` varchar(255) DEFAULT NULL,
  `last_edited` datetime DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `questionnaire_question_entity_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `file_entity`
--

CREATE TABLE `file_entity` (
  `id` bigint(20) NOT NULL,
  `aswer_date_time` datetime DEFAULT NULL,
  `file_format` varchar(255) DEFAULT NULL,
  `file_hash` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `file_size` bigint(20) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `normalized_document_name` varchar(255) DEFAULT NULL,
  `questionnaire_question_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(3),
(3),
(3),
(3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `initial_questions`
--

CREATE TABLE `initial_questions` (
  `id` bigint(20) NOT NULL,
  `has_file_attach` int(11) NOT NULL,
  `has_negative_extra_response` bit(1) NOT NULL,
  `has_url_text` int(11) NOT NULL,
  `help_text` varchar(1000) DEFAULT NULL,
  `is_writable_by_evaluator` int(11) NOT NULL,
  `n_delete_state` int(11) NOT NULL,
  `negative_message` varchar(255) DEFAULT NULL,
  `orden` double NOT NULL,
  `type` varchar(255) NOT NULL,
  `weight` double NOT NULL,
  `cat_question` bigint(20) NOT NULL,
  `title_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `initial_questions`
--

INSERT INTO `initial_questions` (`id`, `has_file_attach`, `has_negative_extra_response`, `has_url_text`, `help_text`, `is_writable_by_evaluator`, `n_delete_state`, `negative_message`, `orden`, `type`, `weight`, `cat_question`, `title_id`) VALUES
(1, 0, b'1', 0, '', 1, 1, '', 1.12, 'Advanced', 1, 1, 1),
(2, 0, b'1', 0, '', 1, 1, '', 1.21, 'Advanced', 1, 1, 2),
(3, 0, b'1', 0, '', 1, 1, '', 1.22, 'Basic', 1, 1, 3),
(4, 0, b'1', 0, '', 1, 1, '', 1.23, 'Basic', 1, 1, 4),
(5, 0, b'1', 0, '', 1, 1, '', 1.24, 'Basic', 1, 1, 5),
(6, 0, b'1', 0, '', 1, 1, '', 1.31, 'Advanced', 1, 1, 6),
(7, 0, b'1', 0, '', 1, 1, '', 1.32, 'Basic', 1, 1, 7),
(8, 0, b'1', 0, '', 1, 1, '', 2.11, 'Advanced', 1, 2, 8),
(9, 0, b'1', 0, '', 1, 1, '', 2.12, 'Advanced', 1, 2, 9),
(10, 0, b'1', 0, '', 1, 1, '', 2.13, 'Advanced', 1, 2, 10),
(11, 0, b'1', 0, '', 1, 1, '', 2.14, 'Advanced', 1, 2, 11),
(12, 0, b'1', 0, '', 1, 1, '', 2.21, 'Advanced', 1, 2, 12),
(13, 0, b'1', 0, '', 1, 1, '', 2.22, 'Basic', 1, 2, 13),
(14, 0, b'1', 0, '', 1, 1, '', 2.23, 'Advanced', 1, 2, 14),
(15, 0, b'1', 0, '', 1, 1, '', 2.24, 'Advanced', 1, 2, 15),
(16, 0, b'1', 0, '', 1, 1, '', 2.25, 'Advanced', 1, 2, 16),
(17, 0, b'1', 0, '', 1, 1, '', 2.26, 'Basic', 1, 2, 17),
(18, 0, b'1', 0, '', 1, 1, '', 2.31, 'Basic', 1, 2, 18),
(19, 0, b'1', 0, '', 1, 1, '', 2.32, 'Basic', 1, 2, 19),
(20, 0, b'1', 0, '', 1, 1, '', 3.11, 'Basic', 1, 3, 20),
(21, 0, b'1', 0, '', 1, 1, '', 3.12, 'Advanced', 1, 3, 21),
(22, 0, b'1', 0, '', 1, 1, '', 3.13, 'Advanced', 1, 3, 22),
(23, 0, b'1', 0, '', 1, 1, '', 3.14, 'Advanced', 1, 3, 23),
(24, 0, b'1', 0, '', 1, 1, '', 3.15, 'Basic', 1, 3, 24),
(25, 0, b'1', 0, '', 1, 1, '', 3.16, 'Basic', 1, 3, 25),
(26, 0, b'1', 0, '', 1, 1, '', 3.17, 'Basic', 1, 3, 26),
(27, 0, b'1', 0, '', 1, 1, '', 3.18, 'Basic', 1, 3, 27),
(28, 0, b'1', 0, '', 1, 1, '', 3.21, 'Advanced', 1, 3, 28),
(29, 0, b'1', 0, '', 1, 1, '', 3.22, 'Advanced', 1, 3, 29),
(30, 0, b'1', 0, '', 1, 1, '', 3.23, 'Basic', 1, 3, 30),
(31, 0, b'1', 0, '', 1, 1, '', 3.24, 'Basic', 1, 3, 31),
(32, 0, b'1', 0, '', 1, 1, '', 3.25, 'Advanced', 1, 3, 32),
(33, 0, b'1', 0, '', 1, 1, '', 3.31, 'Advanced', 1, 3, 33),
(34, 0, b'1', 0, '', 1, 1, '', 3.32, 'Basic', 1, 3, 34),
(35, 0, b'1', 0, '', 1, 1, '', 4.11, 'Advanced', 1, 4, 35),
(36, 0, b'1', 0, '', 1, 1, '', 4.12, 'Advanced', 1, 4, 36),
(37, 0, b'1', 0, '', 1, 1, '', 4.13, 'Advanced', 1, 4, 37),
(38, 0, b'1', 0, '', 1, 1, '', 4.14, 'Basic', 1, 4, 38),
(39, 0, b'1', 0, '', 1, 1, '', 4.15, 'Basic', 1, 4, 39),
(40, 0, b'1', 0, '', 1, 1, '', 4.21, 'Advanced', 1, 4, 40),
(41, 0, b'1', 0, '', 1, 1, '', 4.22, 'Advanced', 1, 4, 41),
(42, 0, b'1', 0, '', 1, 1, '', 4.23, 'Advanced', 1, 4, 42),
(43, 0, b'1', 0, '', 1, 1, '', 4.24, 'Basic', 1, 4, 43),
(44, 0, b'1', 0, '', 1, 1, '', 4.25, 'Basic', 1, 4, 44),
(45, 0, b'1', 0, '', 1, 1, '', 4.26, 'Basic', 1, 4, 45),
(46, 0, b'1', 0, '', 1, 1, '', 4.27, 'Basic', 1, 4, 46),
(47, 0, b'1', 0, '', 1, 1, '', 4.31, 'Advanced', 1, 4, 47),
(48, 0, b'1', 0, '', 1, 1, '', 4.32, 'Advanced', 1, 4, 48),
(49, 0, b'1', 0, '', 1, 1, '', 4.33, 'Advanced', 1, 4, 49),
(50, 0, b'1', 0, '', 1, 1, '', 4.34, 'Advanced', 1, 4, 50),
(51, 0, b'1', 0, '', 1, 1, '', 4.35, 'Advanced', 1, 4, 51),
(52, 0, b'1', 0, '', 1, 1, '', 4.36, 'Advanced', 1, 4, 52),
(53, 0, b'1', 0, '', 1, 1, '', 4.37, 'Advanced', 1, 4, 53),
(54, 0, b'1', 0, '', 1, 1, '', 4.41, 'Basic', 1, 4, 54),
(55, 0, b'1', 0, '', 1, 1, '', 4.42, 'Advanced', 1, 4, 55),
(56, 0, b'1', 0, '', 1, 1, '', 4.43, 'Advanced', 1, 4, 56),
(57, 0, b'1', 0, '', 1, 1, '', 4.44, 'Advanced', 1, 4, 57),
(58, 0, b'1', 0, '', 1, 1, '', 4.45, 'Basic', 1, 4, 58),
(59, 0, b'1', 0, '', 1, 1, '', 5.11, 'Advanced', 1, 5, 59),
(60, 0, b'1', 0, '', 1, 1, '', 5.12, 'Basic', 1, 5, 60),
(61, 0, b'1', 0, '', 1, 1, '', 5.13, 'Advanced', 1, 5, 61),
(62, 0, b'1', 0, '', 1, 1, '', 5.14, 'Advanced', 1, 5, 62),
(63, 0, b'1', 0, '', 1, 1, '', 5.15, 'Basic', 1, 5, 63),
(64, 0, b'1', 0, '', 1, 1, '', 5.16, 'Advanced', 1, 5, 64),
(65, 0, b'1', 0, '', 1, 1, '', 5.17, 'Basic', 1, 5, 65),
(66, 0, b'1', 0, '', 1, 1, '', 5.18, 'Advanced', 1, 5, 66),
(67, 0, b'1', 0, '', 1, 1, '', 5.21, 'Basic', 1, 5, 67),
(68, 0, b'1', 0, '', 1, 1, '', 5.22, 'Advanced', 1, 5, 68),
(69, 0, b'1', 0, '', 1, 1, '', 5.23, 'Basic', 1, 5, 69),
(70, 0, b'1', 0, '', 1, 1, '', 5.24, 'Advanced', 1, 5, 70),
(71, 0, b'1', 0, '', 1, 1, '', 5.25, 'Advanced', 1, 5, 71),
(72, 0, b'1', 0, '', 1, 1, '', 5.26, 'Basic', 1, 5, 72),
(73, 0, b'1', 0, '', 1, 1, '', 5.27, 'Basic', 1, 5, 73),
(74, 0, b'1', 0, '', 1, 1, '', 5.28, 'Advanced', 1, 5, 74),
(75, 0, b'1', 0, '', 1, 1, '', 5.29, 'Basic', 1, 5, 75),
(76, 0, b'1', 0, '', 1, 1, '', 5.31, 'Basic', 1, 5, 76),
(77, 0, b'1', 0, '', 1, 1, '', 5.32, 'Basic', 1, 5, 77),
(78, 0, b'1', 0, '', 1, 1, '', 6.11, 'Advanced', 1, 6, 78),
(79, 0, b'1', 0, '', 1, 1, '', 6.12, 'Advanced', 1, 6, 79),
(80, 0, b'1', 0, '', 1, 1, '', 6.21, 'Basic', 1, 6, 80),
(81, 0, b'1', 0, '', 1, 1, '', 6.22, 'Basic', 1, 6, 81),
(82, 0, b'1', 0, '', 1, 1, '', 6.23, 'Basic', 1, 6, 82),
(83, 0, b'1', 0, '', 1, 1, '', 6.24, 'Basic', 1, 6, 83),
(84, 0, b'1', 0, '', 1, 1, '', 6.31, 'Basic', 1, 6, 84),
(85, 0, b'1', 0, '', 1, 1, '', 6.32, 'Basic', 1, 6, 85),
(86, 0, b'1', 0, '', 1, 1, '', 7.11, 'Basic', 1, 7, 86),
(87, 0, b'1', 0, '', 1, 1, '', 7.12, 'Basic', 1, 7, 87),
(88, 0, b'1', 0, '', 1, 1, '', 7.13, 'Advanced', 1, 7, 88),
(89, 0, b'1', 0, '', 1, 1, '', 7.14, 'Advanced', 1, 7, 89),
(90, 0, b'1', 0, '', 1, 1, '', 7.15, 'Advanced', 1, 7, 90),
(91, 0, b'1', 0, '', 1, 1, '', 7.21, 'Advanced', 1, 7, 91),
(92, 0, b'1', 0, '', 1, 1, '', 7.22, 'Basic', 1, 7, 92),
(93, 0, b'1', 0, '', 1, 1, '', 7.31, 'Basic', 1, 7, 93),
(94, 0, b'1', 0, '', 1, 1, '', 7.32, 'Basic', 1, 7, 94),
(95, 0, b'1', 0, '', 1, 1, '', 7.33, 'Advanced', 1, 7, 95),
(96, 0, b'1', 0, '', 1, 1, '', 7.34, 'Advanced', 1, 7, 96),
(97, 0, b'1', 0, '', 1, 1, '', 7.35, 'Basic', 1, 7, 97),
(98, 0, b'1', 0, '', 1, 1, '', 7.36, 'Basic', 1, 7, 98),
(99, 0, b'1', 0, '', 1, 1, '', 7.37, 'Basic', 1, 7, 99),
(100, 0, b'1', 0, '', 1, 1, '', 1.1, 'Basic', 1, 8, 100),
(101, 0, b'1', 0, '', 1, 1, '', 1.2, 'Basic', 1, 8, 101),
(102, 0, b'1', 0, '', 1, 1, '', 1.3, 'Basic', 1, 8, 102),
(103, 0, b'1', 0, '', 1, 1, '', 1.4, 'Basic', 1, 8, 103),
(104, 0, b'1', 0, '', 1, 1, '', 1.5, 'Basic', 1, 8, 104),
(105, 0, b'1', 0, '', 1, 1, '', 1.6, 'Basic', 1, 8, 105),
(106, 0, b'1', 0, '', 1, 1, '', 2.1, 'Basic', 1, 9, 106),
(107, 0, b'1', 0, '', 1, 1, '', 2.2, 'Basic', 1, 9, 107),
(108, 0, b'1', 0, '', 1, 1, '', 2.3, 'Basic', 1, 9, 108),
(109, 0, b'1', 0, '', 1, 1, '', 2.4, 'Basic', 1, 9, 109),
(110, 0, b'1', 0, '', 1, 1, '', 2.5, 'Basic', 1, 9, 110),
(111, 0, b'1', 0, '', 1, 1, '', 2.6, 'Basic', 1, 9, 111),
(112, 0, b'1', 0, '', 1, 1, '', 3.1, 'Basic', 1, 10, 112),
(113, 0, b'1', 0, '', 1, 1, '', 3.2, 'Basic', 1, 10, 113),
(114, 0, b'1', 0, '', 1, 1, '', 3.3, 'Basic', 1, 10, 114),
(115, 0, b'1', 0, '', 1, 1, '', 3.4, 'Basic', 1, 10, 115),
(116, 0, b'1', 0, '', 1, 1, '', 3.5, 'Basic', 1, 10, 116),
(117, 0, b'1', 0, '', 1, 1, '', 3.6, 'Basic', 1, 10, 117),
(118, 0, b'1', 0, '', 1, 1, '', 4.1, 'Basic', 1, 11, 118),
(119, 0, b'1', 0, '', 1, 1, '', 4.2, 'Basic', 1, 11, 119),
(120, 0, b'1', 0, '', 1, 1, '', 4.3, 'Basic', 1, 11, 120),
(121, 0, b'1', 0, '', 1, 1, '', 4.4, 'Basic', 1, 11, 121),
(122, 0, b'1', 0, '', 1, 1, '', 4.5, 'Basic', 1, 11, 122),
(123, 0, b'1', 0, '', 1, 1, '', 4.6, 'Basic', 1, 11, 123),
(124, 0, b'1', 0, '', 1, 1, '', 5.1, 'Basic', 1, 12, 124),
(125, 0, b'1', 0, '', 1, 1, '', 5.2, 'Basic', 1, 12, 125),
(126, 0, b'1', 0, '', 1, 1, '', 5.3, 'Basic', 1, 12, 126),
(127, 0, b'1', 0, '', 1, 1, '', 5.4, 'Basic', 1, 12, 127),
(128, 0, b'1', 0, '', 1, 1, '', 5.5, 'Basic', 1, 12, 128),
(129, 0, b'1', 0, '', 1, 1, '', 5.6, 'Basic', 1, 12, 129),
(130, 0, b'1', 0, '', 1, 1, '', 6.1, 'Basic', 1, 13, 130),
(131, 0, b'1', 0, '', 1, 1, '', 6.2, 'Basic', 1, 13, 131),
(132, 0, b'1', 0, '', 1, 1, '', 6.3, 'Basic', 1, 13, 132),
(133, 0, b'1', 0, '', 1, 1, '', 6.4, 'Basic', 1, 13, 133),
(134, 0, b'1', 0, '', 1, 1, '', 6.5, 'Basic', 1, 13, 134),
(135, 0, b'1', 0, '', 1, 1, '', 6.6, 'Basic', 1, 13, 135),
(136, 0, b'1', 0, '', 1, 1, '', 7.1, 'Basic', 1, 14, 136),
(137, 0, b'1', 0, '', 1, 1, '', 7.2, 'Basic', 1, 14, 137),
(138, 0, b'1', 0, '', 1, 1, '', 7.3, 'Basic', 1, 14, 138),
(139, 0, b'1', 0, '', 1, 1, '', 7.4, 'Basic', 1, 14, 139),
(140, 0, b'1', 0, '', 1, 1, '', 7.5, 'Basic', 1, 14, 140),
(141, 0, b'1', 0, '', 1, 1, '', 7.6, 'Basic', 1, 14, 141);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `institucion`
--

CREATE TABLE `institucion` (
  `id` bigint(20) NOT NULL,
  `acronimo` varchar(255) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `institucion`
--

INSERT INTO `institucion` (`id`, `acronimo`, `n_delete_state`, `nombre`, `url`) VALUES
(1, 'SOLTEL', 1, 'Soltel', 'www.soltel.es'),
(2, 'FECYT', NULL, 'FECYT', 'www.fecyt.es'),
(3, 'DFGGFSD', NULL, 'GFDGFD', 'dfgdgfsdgfs');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `password_recover`
--

CREATE TABLE `password_recover` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `question`
--

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL,
  `has_file_attach` int(11) DEFAULT NULL,
  `has_negative_extra_response` bit(1) NOT NULL,
  `has_url_text` int(11) DEFAULT NULL,
  `help_text` text DEFAULT NULL,
  `is_writable_by_evaluator` int(11) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `negative_message` varchar(255) DEFAULT NULL,
  `orden` double DEFAULT NULL,
  `type_question` varchar(255) DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `cat_question_id` bigint(20) DEFAULT NULL,
  `period_id` bigint(20) DEFAULT NULL,
  `title_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `question`
--

INSERT INTO `question` (`id`, `has_file_attach`, `has_negative_extra_response`, `has_url_text`, `help_text`, `is_writable_by_evaluator`, `n_delete_state`, `negative_message`, `orden`, `type_question`, `weight`, `cat_question_id`, `period_id`, `title_id`) VALUES
(1, 0, b'1', 0, '', 1, 1, '', 1.1, 'BASIC', 1, 8, 1, 100),
(2, 0, b'1', 0, '', 1, 1, '', 1.2, 'BASIC', 1, 8, 1, 101),
(3, 0, b'1', 0, '', 1, 1, '', 1.3, 'BASIC', 1, 8, 1, 102),
(4, 0, b'1', 0, '', 1, 1, '', 1.4, 'BASIC', 1, 8, 1, 103),
(5, 0, b'1', 0, '', 1, 1, '', 1.5, 'BASIC', 1, 8, 1, 104),
(6, 0, b'1', 0, '', 1, 1, '', 1.6, 'BASIC', 1, 8, 1, 105),
(7, 0, b'1', 0, '', 1, 1, '', 2.1, 'BASIC', 1, 9, 1, 106),
(8, 0, b'1', 0, '', 1, 1, '', 2.2, 'BASIC', 1, 9, 1, 107),
(9, 0, b'1', 0, '', 1, 1, '', 2.3, 'BASIC', 1, 9, 1, 108),
(10, 0, b'1', 0, '', 1, 1, '', 2.4, 'BASIC', 1, 9, 1, 109),
(11, 0, b'1', 0, '', 1, 1, '', 2.5, 'BASIC', 1, 9, 1, 110),
(12, 0, b'1', 0, '', 1, 1, '', 2.6, 'BASIC', 1, 9, 1, 111),
(13, 0, b'1', 0, '', 1, 1, '', 3.1, 'BASIC', 1, 10, 1, 112),
(14, 0, b'1', 0, '', 1, 1, '', 3.2, 'BASIC', 1, 10, 1, 113),
(15, 0, b'1', 0, '', 1, 1, '', 3.3, 'BASIC', 1, 10, 1, 114),
(16, 0, b'1', 0, '', 1, 1, '', 3.4, 'BASIC', 1, 10, 1, 115),
(17, 0, b'1', 0, '', 1, 1, '', 3.5, 'BASIC', 1, 10, 1, 116),
(18, 0, b'1', 0, '', 1, 1, '', 3.6, 'BASIC', 1, 10, 1, 117),
(19, 0, b'1', 0, '', 1, 1, '', 4.1, 'BASIC', 1, 11, 1, 118),
(20, 0, b'1', 0, '', 1, 1, '', 4.2, 'BASIC', 1, 11, 1, 119),
(21, 0, b'1', 0, '', 1, 1, '', 4.3, 'BASIC', 1, 11, 1, 120),
(22, 0, b'1', 0, '', 1, 1, '', 4.4, 'BASIC', 1, 11, 1, 121),
(23, 0, b'1', 0, '', 1, 1, '', 4.5, 'BASIC', 1, 11, 1, 122),
(24, 0, b'1', 0, '', 1, 1, '', 4.6, 'BASIC', 1, 11, 1, 123),
(25, 0, b'1', 0, '', 1, 1, '', 5.1, 'BASIC', 1, 12, 1, 124),
(26, 0, b'1', 0, '', 1, 1, '', 5.2, 'BASIC', 1, 12, 1, 125),
(27, 0, b'1', 0, '', 1, 1, '', 5.3, 'BASIC', 1, 12, 1, 126),
(28, 0, b'1', 0, '', 1, 1, '', 5.4, 'BASIC', 1, 12, 1, 127),
(29, 0, b'1', 0, '', 1, 1, '', 5.5, 'BASIC', 1, 12, 1, 128),
(30, 0, b'1', 0, '', 1, 1, '', 5.6, 'BASIC', 1, 12, 1, 129),
(31, 0, b'1', 0, '', 1, 1, '', 6.1, 'BASIC', 1, 13, 1, 130),
(32, 0, b'1', 0, '', 1, 1, '', 6.2, 'BASIC', 1, 13, 1, 131),
(33, 0, b'1', 0, '', 1, 1, '', 6.3, 'BASIC', 1, 13, 1, 132),
(34, 0, b'1', 0, '', 1, 1, '', 6.4, 'BASIC', 1, 13, 1, 133),
(35, 0, b'1', 0, '', 1, 1, '', 6.5, 'BASIC', 1, 13, 1, 134),
(36, 0, b'1', 0, '', 1, 1, '', 6.6, 'BASIC', 1, 13, 1, 135),
(37, 0, b'1', 0, '', 1, 1, '', 7.1, 'BASIC', 1, 14, 1, 136),
(38, 0, b'1', 0, '', 1, 1, '', 7.2, 'BASIC', 1, 14, 1, 137),
(39, 0, b'1', 0, '', 1, 1, '', 7.3, 'BASIC', 1, 14, 1, 138),
(40, 0, b'1', 0, '', 1, 1, '', 7.4, 'BASIC', 1, 14, 1, 139),
(41, 0, b'1', 0, '', 1, 1, '', 7.5, 'BASIC', 1, 14, 1, 140),
(42, 0, b'1', 0, '', 1, 1, '', 7.6, 'BASIC', 1, 14, 1, 141),
(43, 0, b'1', 0, '', 1, 1, '', 1.12, 'ADVANCED', 1, 1, 2, 1),
(44, 0, b'1', 0, '', 1, 1, '', 1.21, 'ADVANCED', 1, 1, 2, 2),
(45, 0, b'1', 0, '', 1, 1, '', 1.22, 'BASIC', 1, 1, 2, 3),
(46, 0, b'1', 0, '', 1, 1, '', 1.23, 'BASIC', 1, 1, 2, 4),
(47, 0, b'1', 0, '', 1, 1, '', 1.24, 'BASIC', 1, 1, 2, 5),
(48, 0, b'1', 0, '', 1, 1, '', 1.31, 'ADVANCED', 1, 1, 2, 6),
(49, 0, b'1', 0, '', 1, 1, '', 1.32, 'BASIC', 1, 1, 2, 7),
(50, 0, b'1', 0, '', 1, 1, '', 2.11, 'ADVANCED', 1, 2, 2, 8),
(51, 0, b'1', 0, '', 1, 1, '', 2.12, 'ADVANCED', 1, 2, 2, 9),
(52, 0, b'1', 0, '', 1, 1, '', 2.13, 'ADVANCED', 1, 2, 2, 10),
(53, 0, b'1', 0, '', 1, 1, '', 2.14, 'ADVANCED', 1, 2, 2, 11),
(54, 0, b'1', 0, '', 1, 1, '', 2.21, 'ADVANCED', 1, 2, 2, 12),
(55, 0, b'1', 0, '', 1, 1, '', 2.22, 'BASIC', 1, 2, 2, 13),
(56, 0, b'1', 0, '', 1, 1, '', 2.23, 'ADVANCED', 1, 2, 2, 14),
(57, 0, b'1', 0, '', 1, 1, '', 2.24, 'ADVANCED', 1, 2, 2, 15),
(58, 0, b'1', 0, '', 1, 1, '', 2.25, 'ADVANCED', 1, 2, 2, 16),
(59, 0, b'1', 0, '', 1, 1, '', 2.26, 'BASIC', 1, 2, 2, 17),
(60, 0, b'1', 0, '', 1, 1, '', 2.31, 'BASIC', 1, 2, 2, 18),
(61, 0, b'1', 0, '', 1, 1, '', 2.32, 'BASIC', 1, 2, 2, 19),
(62, 0, b'1', 0, '', 1, 1, '', 3.11, 'BASIC', 1, 3, 2, 20),
(63, 0, b'1', 0, '', 1, 1, '', 3.12, 'ADVANCED', 1, 3, 2, 21),
(64, 0, b'1', 0, '', 1, 1, '', 3.13, 'ADVANCED', 1, 3, 2, 22),
(65, 0, b'1', 0, '', 1, 1, '', 3.14, 'ADVANCED', 1, 3, 2, 23),
(66, 0, b'1', 0, '', 1, 1, '', 3.15, 'BASIC', 1, 3, 2, 24),
(67, 0, b'1', 0, '', 1, 1, '', 3.16, 'BASIC', 1, 3, 2, 25),
(68, 0, b'1', 0, '', 1, 1, '', 3.17, 'BASIC', 1, 3, 2, 26),
(69, 0, b'1', 0, '', 1, 1, '', 3.18, 'BASIC', 1, 3, 2, 27),
(70, 0, b'1', 0, '', 1, 1, '', 3.21, 'ADVANCED', 1, 3, 2, 28),
(71, 0, b'1', 0, '', 1, 1, '', 3.22, 'ADVANCED', 1, 3, 2, 29),
(72, 0, b'1', 0, '', 1, 1, '', 3.23, 'BASIC', 1, 3, 2, 30),
(73, 0, b'1', 0, '', 1, 1, '', 3.24, 'BASIC', 1, 3, 2, 31),
(74, 0, b'1', 0, '', 1, 1, '', 3.25, 'ADVANCED', 1, 3, 2, 32),
(75, 0, b'1', 0, '', 1, 1, '', 3.31, 'ADVANCED', 1, 3, 2, 33),
(76, 0, b'1', 0, '', 1, 1, '', 3.32, 'BASIC', 1, 3, 2, 34),
(77, 0, b'1', 0, '', 1, 1, '', 4.11, 'ADVANCED', 1, 4, 2, 35),
(78, 0, b'1', 0, '', 1, 1, '', 4.12, 'ADVANCED', 1, 4, 2, 36),
(79, 0, b'1', 0, '', 1, 1, '', 4.13, 'ADVANCED', 1, 4, 2, 37),
(80, 0, b'1', 0, '', 1, 1, '', 4.14, 'BASIC', 1, 4, 2, 38),
(81, 0, b'1', 0, '', 1, 1, '', 4.15, 'BASIC', 1, 4, 2, 39),
(82, 0, b'1', 0, '', 1, 1, '', 4.21, 'ADVANCED', 1, 4, 2, 40),
(83, 0, b'1', 0, '', 1, 1, '', 4.22, 'ADVANCED', 1, 4, 2, 41),
(84, 0, b'1', 0, '', 1, 1, '', 4.23, 'ADVANCED', 1, 4, 2, 42),
(85, 0, b'1', 0, '', 1, 1, '', 4.24, 'BASIC', 1, 4, 2, 43),
(86, 0, b'1', 0, '', 1, 1, '', 4.25, 'BASIC', 1, 4, 2, 44),
(87, 0, b'1', 0, '', 1, 1, '', 4.26, 'BASIC', 1, 4, 2, 45),
(88, 0, b'1', 0, '', 1, 1, '', 4.27, 'BASIC', 1, 4, 2, 46),
(89, 0, b'1', 0, '', 1, 1, '', 4.31, 'ADVANCED', 1, 4, 2, 47),
(90, 0, b'1', 0, '', 1, 1, '', 4.32, 'ADVANCED', 1, 4, 2, 48),
(91, 0, b'1', 0, '', 1, 1, '', 4.33, 'ADVANCED', 1, 4, 2, 49),
(92, 0, b'1', 0, '', 1, 1, '', 4.34, 'ADVANCED', 1, 4, 2, 50),
(93, 0, b'1', 0, '', 1, 1, '', 4.35, 'ADVANCED', 1, 4, 2, 51),
(94, 0, b'1', 0, '', 1, 1, '', 4.36, 'ADVANCED', 1, 4, 2, 52),
(95, 0, b'1', 0, '', 1, 1, '', 4.37, 'ADVANCED', 1, 4, 2, 53),
(96, 0, b'1', 0, '', 1, 1, '', 4.41, 'BASIC', 1, 4, 2, 54),
(97, 0, b'1', 0, '', 1, 1, '', 4.42, 'ADVANCED', 1, 4, 2, 55),
(98, 0, b'1', 0, '', 1, 1, '', 4.43, 'ADVANCED', 1, 4, 2, 56),
(99, 0, b'1', 0, '', 1, 1, '', 4.44, 'ADVANCED', 1, 4, 2, 57),
(100, 0, b'1', 0, '', 1, 1, '', 4.45, 'BASIC', 1, 4, 2, 58),
(101, 0, b'1', 0, '', 1, 1, '', 5.11, 'ADVANCED', 1, 5, 2, 59),
(102, 0, b'1', 0, '', 1, 1, '', 5.12, 'BASIC', 1, 5, 2, 60),
(103, 0, b'1', 0, '', 1, 1, '', 5.13, 'ADVANCED', 1, 5, 2, 61),
(104, 0, b'1', 0, '', 1, 1, '', 5.14, 'ADVANCED', 1, 5, 2, 62),
(105, 0, b'1', 0, '', 1, 1, '', 5.15, 'BASIC', 1, 5, 2, 63),
(106, 0, b'1', 0, '', 1, 1, '', 5.16, 'ADVANCED', 1, 5, 2, 64),
(107, 0, b'1', 0, '', 1, 1, '', 5.17, 'BASIC', 1, 5, 2, 65),
(108, 0, b'1', 0, '', 1, 1, '', 5.18, 'ADVANCED', 1, 5, 2, 66),
(109, 0, b'1', 0, '', 1, 1, '', 5.21, 'BASIC', 1, 5, 2, 67),
(110, 0, b'1', 0, '', 1, 1, '', 5.22, 'ADVANCED', 1, 5, 2, 68),
(111, 0, b'1', 0, '', 1, 1, '', 5.23, 'BASIC', 1, 5, 2, 69),
(112, 0, b'1', 0, '', 1, 1, '', 5.24, 'ADVANCED', 1, 5, 2, 70),
(113, 0, b'1', 0, '', 1, 1, '', 5.25, 'ADVANCED', 1, 5, 2, 71),
(114, 0, b'1', 0, '', 1, 1, '', 5.26, 'BASIC', 1, 5, 2, 72),
(115, 0, b'1', 0, '', 1, 1, '', 5.27, 'BASIC', 1, 5, 2, 73),
(116, 0, b'1', 0, '', 1, 1, '', 5.28, 'ADVANCED', 1, 5, 2, 74),
(117, 0, b'1', 0, '', 1, 1, '', 5.29, 'BASIC', 1, 5, 2, 75),
(118, 0, b'1', 0, '', 1, 1, '', 5.31, 'BASIC', 1, 5, 2, 76),
(119, 0, b'1', 0, '', 1, 1, '', 5.32, 'BASIC', 1, 5, 2, 77),
(120, 0, b'1', 0, '', 1, 1, '', 6.11, 'ADVANCED', 1, 6, 2, 78),
(121, 0, b'1', 0, '', 1, 1, '', 6.12, 'ADVANCED', 1, 6, 2, 79),
(122, 0, b'1', 0, '', 1, 1, '', 6.21, 'BASIC', 1, 6, 2, 80),
(123, 0, b'1', 0, '', 1, 1, '', 6.22, 'BASIC', 1, 6, 2, 81),
(124, 0, b'1', 0, '', 1, 1, '', 6.23, 'BASIC', 1, 6, 2, 82),
(125, 0, b'1', 0, '', 1, 1, '', 6.24, 'BASIC', 1, 6, 2, 83),
(126, 0, b'1', 0, '', 1, 1, '', 6.31, 'BASIC', 1, 6, 2, 84),
(127, 0, b'1', 0, '', 1, 1, '', 6.32, 'BASIC', 1, 6, 2, 85),
(128, 0, b'1', 0, '', 1, 1, '', 7.11, 'BASIC', 1, 7, 2, 86),
(129, 0, b'1', 0, '', 1, 1, '', 7.12, 'BASIC', 1, 7, 2, 87),
(130, 0, b'1', 0, '', 1, 1, '', 7.13, 'ADVANCED', 1, 7, 2, 88),
(131, 0, b'1', 0, '', 1, 1, '', 7.14, 'ADVANCED', 1, 7, 2, 89),
(132, 0, b'1', 0, '', 1, 1, '', 7.15, 'ADVANCED', 1, 7, 2, 90),
(133, 0, b'1', 0, '', 1, 1, '', 7.21, 'ADVANCED', 1, 7, 2, 91),
(134, 0, b'1', 0, '', 1, 1, '', 7.22, 'BASIC', 1, 7, 2, 92),
(135, 0, b'1', 0, '', 1, 1, '', 7.31, 'BASIC', 1, 7, 2, 93),
(136, 0, b'1', 0, '', 1, 1, '', 7.32, 'BASIC', 1, 7, 2, 94),
(137, 0, b'1', 0, '', 1, 1, '', 7.33, 'ADVANCED', 1, 7, 2, 95),
(138, 0, b'1', 0, '', 1, 1, '', 7.34, 'ADVANCED', 1, 7, 2, 96),
(139, 0, b'1', 0, '', 1, 1, '', 7.35, 'BASIC', 1, 7, 2, 97),
(140, 0, b'1', 0, '', 1, 1, '', 7.36, 'BASIC', 1, 7, 2, 98),
(141, 0, b'1', 0, '', 1, 1, '', 7.37, 'BASIC', 1, 7, 2, 99);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `questionnaire_answer_entity`
--

CREATE TABLE `questionnaire_answer_entity` (
  `id` bigint(20) NOT NULL,
  `answer` bit(1) DEFAULT NULL,
  `answer_date_time` datetime DEFAULT NULL,
  `answer_text` varchar(255) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `negative_extra_point` int(11) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `file_id` bigint(20) DEFAULT NULL,
  `questionnaire_question_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `questionnaire_answer_entity`
--

INSERT INTO `questionnaire_answer_entity` (`id`, `answer`, `answer_date_time`, `answer_text`, `n_delete_state`, `negative_extra_point`, `observaciones`, `file_id`, `questionnaire_question_id`) VALUES
(1, NULL, '2024-02-29 08:39:41', '', 1, NULL, '', NULL, 37);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `questionnaire_entity`
--

CREATE TABLE `questionnaire_entity` (
  `id` bigint(20) NOT NULL,
  `creation_date` datetime NOT NULL,
  `delete_state` int(11) DEFAULT NULL,
  `state` varchar(255) NOT NULL,
  `evaluation_id` bigint(20) NOT NULL,
  `period_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `questionnaire_entity`
--

INSERT INTO `questionnaire_entity` (`id`, `creation_date`, `delete_state`, `state`, `evaluation_id`, `period_id`) VALUES
(1, '2024-02-29 06:40:43', 1, 'No enviado', 1, 1),
(2, '2024-02-29 06:48:50', 1, 'No enviado', 2, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `questionnaire_question_entity`
--

CREATE TABLE `questionnaire_question_entity` (
  `id` bigint(20) NOT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `question_id` bigint(20) DEFAULT NULL,
  `questionnaire_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `questionnaire_question_entity`
--

INSERT INTO `questionnaire_question_entity` (`id`, `n_delete_state`, `question_id`, `questionnaire_id`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 1),
(3, 1, 3, 1),
(4, 1, 4, 1),
(5, 1, 5, 1),
(6, 1, 6, 1),
(7, 1, 7, 1),
(8, 1, 8, 1),
(9, 1, 9, 1),
(10, 1, 10, 1),
(11, 1, 11, 1),
(12, 1, 12, 1),
(13, 1, 13, 1),
(14, 1, 14, 1),
(15, 1, 15, 1),
(16, 1, 16, 1),
(17, 1, 17, 1),
(18, 1, 18, 1),
(19, 1, 19, 1),
(20, 1, 20, 1),
(21, 1, 21, 1),
(22, 1, 22, 1),
(23, 1, 23, 1),
(24, 1, 24, 1),
(25, 1, 25, 1),
(26, 1, 26, 1),
(27, 1, 27, 1),
(28, 1, 28, 1),
(29, 1, 29, 1),
(30, 1, 30, 1),
(31, 1, 31, 1),
(32, 1, 32, 1),
(33, 1, 33, 1),
(34, 1, 34, 1),
(35, 1, 35, 1),
(36, 1, 36, 1),
(37, 1, 37, 1),
(38, 1, 38, 1),
(39, 1, 39, 1),
(40, 1, 40, 1),
(41, 1, 41, 1),
(42, 1, 42, 1),
(43, 1, 43, 2),
(44, 1, 44, 2),
(45, 1, 45, 2),
(46, 1, 46, 2),
(47, 1, 47, 2),
(48, 1, 48, 2),
(49, 1, 49, 2),
(50, 1, 50, 2),
(51, 1, 51, 2),
(52, 1, 52, 2),
(53, 1, 53, 2),
(54, 1, 54, 2),
(55, 1, 55, 2),
(56, 1, 56, 2),
(57, 1, 57, 2),
(58, 1, 58, 2),
(59, 1, 59, 2),
(60, 1, 60, 2),
(61, 1, 61, 2),
(62, 1, 62, 2),
(63, 1, 63, 2),
(64, 1, 64, 2),
(65, 1, 65, 2),
(66, 1, 66, 2),
(67, 1, 67, 2),
(68, 1, 68, 2),
(69, 1, 69, 2),
(70, 1, 70, 2),
(71, 1, 71, 2),
(72, 1, 72, 2),
(73, 1, 73, 2),
(74, 1, 74, 2),
(75, 1, 75, 2),
(76, 1, 76, 2),
(77, 1, 77, 2),
(78, 1, 78, 2),
(79, 1, 79, 2),
(80, 1, 80, 2),
(81, 1, 81, 2),
(82, 1, 82, 2),
(83, 1, 83, 2),
(84, 1, 84, 2),
(85, 1, 85, 2),
(86, 1, 86, 2),
(87, 1, 87, 2),
(88, 1, 88, 2),
(89, 1, 89, 2),
(90, 1, 90, 2),
(91, 1, 91, 2),
(92, 1, 92, 2),
(93, 1, 93, 2),
(94, 1, 94, 2),
(95, 1, 95, 2),
(96, 1, 96, 2),
(97, 1, 97, 2),
(98, 1, 98, 2),
(99, 1, 99, 2),
(100, 1, 100, 2),
(101, 1, 101, 2),
(102, 1, 102, 2),
(103, 1, 103, 2),
(104, 1, 104, 2),
(105, 1, 105, 2),
(106, 1, 106, 2),
(107, 1, 107, 2),
(108, 1, 108, 2),
(109, 1, 109, 2),
(110, 1, 110, 2),
(111, 1, 111, 2),
(112, 1, 112, 2),
(113, 1, 113, 2),
(114, 1, 114, 2),
(115, 1, 115, 2),
(116, 1, 116, 2),
(117, 1, 117, 2),
(118, 1, 118, 2),
(119, 1, 119, 2),
(120, 1, 120, 2),
(121, 1, 121, 2),
(122, 1, 122, 2),
(123, 1, 123, 2),
(124, 1, 124, 2),
(125, 1, 125, 2),
(126, 1, 126, 2),
(127, 1, 127, 2),
(128, 1, 128, 2),
(129, 1, 129, 2),
(130, 1, 130, 2),
(131, 1, 131, 2),
(132, 1, 132, 2),
(133, 1, 133, 2),
(134, 1, 134, 2),
(135, 1, 135, 2),
(136, 1, 136, 2),
(137, 1, 137, 2),
(138, 1, 138, 2),
(139, 1, 139, 2),
(140, 1, 140, 2),
(141, 1, 141, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `questionnaire_to_close_entity`
--

CREATE TABLE `questionnaire_to_close_entity` (
  `id` bigint(20) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `questionnaire_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `questionnaire_type`
--

CREATE TABLE `questionnaire_type` (
  `id` bigint(20) NOT NULL,
  `en` varchar(255) DEFAULT NULL,
  `es` varchar(255) DEFAULT NULL,
  `ndelete_state` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `questionnaire_type`
--

INSERT INTO `questionnaire_type` (`id`, `en`, `es`, `ndelete_state`) VALUES
(1, 'EQSIP', 'EQSIP', 1),
(2, 'Sustainability', 'Sostenibilidad', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `repository`
--

CREATE TABLE `repository` (
  `id` bigint(20) NOT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `institucion_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `repository`
--

INSERT INTO `repository` (`id`, `n_delete_state`, `institucion_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `name`) VALUES
(1, 'ADMINISTRADOR'),
(2, 'EDITOR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol_relation`
--

CREATE TABLE `rol_relation` (
  `id` bigint(20) NOT NULL,
  `rol_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol_relation`
--

INSERT INTO `rol_relation` (`id`, `rol_id`, `user_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 3),
(4, 1, 3),
(5, 1, 1),
(6, 1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `system_logs`
--

CREATE TABLE `system_logs` (
  `id` bigint(20) NOT NULL,
  `action_type` varchar(255) DEFAULT NULL,
  `date_time` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `n_repositories` int(11) DEFAULT NULL,
  `source_ip` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `system_logs`
--

INSERT INTO `system_logs` (`id`, `action_type`, `date_time`, `user_id`, `n_delete_state`, `n_repositories`, `source_ip`, `status`) VALUES
(1, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireTypeController: getAll', '2024-02-29 07:39:57', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(2, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireTypeController: getAll', '2024-02-29 07:39:57', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(3, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:40:00', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(4, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:40:00', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(5, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:40:00', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(6, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:40:00', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(7, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireTypeController: getAll', '2024-02-29 07:40:16', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(8, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireTypeController: getAll', '2024-02-29 07:40:16', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(9, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:40:18', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(10, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:40:18', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(11, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:40:18', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(12, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:40:18', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(13, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getByQuestionnaireType', '2024-02-29 07:40:23', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(14, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getByQuestionnaireType', '2024-02-29 07:40:23', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(15, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: create', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(16, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: create', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(17, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(18, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getById', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(19, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getById', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(20, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(21, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(22, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:40:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(23, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:40:42', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(24, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:40:42', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(25, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:40:42', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(26, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:40:42', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(27, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getAll', '2024-02-29 07:40:43', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(28, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getAll', '2024-02-29 07:40:43', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(29, 'Invocación del método: es.soltel.recolecta.controller.EvaluationController: create', '2024-02-29 07:40:43', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(30, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationController: create', '2024-02-29 07:40:43', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(31, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: create', '2024-02-29 07:40:43', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(32, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: create', '2024-02-29 07:40:44', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(33, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: findByEvaluationId', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(34, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: findByEvaluationId', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(35, 'Invocación del método: es.soltel.recolecta.controller.CatQuestionController: getByQuestionnaireType', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(36, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(37, 'Finalización exitosa del método: es.soltel.recolecta.controller.CatQuestionController: getByQuestionnaireType', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(38, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(39, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: findByEvaluationId', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(40, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: findByEvaluationId', '2024-02-29 07:40:45', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(41, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:48:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(42, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:48:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(43, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:48:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(44, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:48:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(45, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getAll', '2024-02-29 07:48:34', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(46, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getAll', '2024-02-29 07:48:34', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(47, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getByQuestionnaireType', '2024-02-29 07:48:37', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(48, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getByQuestionnaireType', '2024-02-29 07:48:37', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(49, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: create', '2024-02-29 07:48:46', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(50, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: create', '2024-02-29 07:48:47', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(51, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:48:47', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(52, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getById', '2024-02-29 07:48:47', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(53, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getById', '2024-02-29 07:48:47', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(54, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:48:47', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(55, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:48:47', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(56, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:48:48', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(57, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:48:49', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(58, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:48:49', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(59, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:48:49', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(60, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:48:49', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(61, 'Invocación del método: es.soltel.recolecta.controller.EvaluationPeriodController: getAll', '2024-02-29 07:48:50', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(62, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationPeriodController: getAll', '2024-02-29 07:48:50', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(63, 'Invocación del método: es.soltel.recolecta.controller.EvaluationController: create', '2024-02-29 07:48:50', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(64, 'Finalización exitosa del método: es.soltel.recolecta.controller.EvaluationController: create', '2024-02-29 07:48:50', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(65, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: create', '2024-02-29 07:48:50', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(66, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: create', '2024-02-29 07:48:51', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(67, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: findByEvaluationId', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(68, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: findByEvaluationId', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(69, 'Invocación del método: es.soltel.recolecta.controller.CatQuestionController: getByQuestionnaireType', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(70, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(71, 'Finalización exitosa del método: es.soltel.recolecta.controller.CatQuestionController: getByQuestionnaireType', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(72, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(73, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: findByEvaluationId', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(74, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: findByEvaluationId', '2024-02-29 07:48:52', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(75, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:50:29', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(76, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:50:29', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(77, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:50:29', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(78, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:50:29', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(79, 'Invocación del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:50:31', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(80, 'Finalización exitosa del método: es.soltel.recolecta.controller.RepositoryController: findRepositoryByUserId', '2024-02-29 07:50:31', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(81, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:50:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(82, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: getByUserId', '2024-02-29 07:50:32', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(83, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireController: findByEvaluationId', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(84, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireController: findByEvaluationId', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(85, 'Invocación del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(86, 'Invocación del método: es.soltel.recolecta.controller.CatQuestionController: getByQuestionnaireType', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(87, 'Finalización exitosa del método: es.soltel.recolecta.controller.CatQuestionController: getByQuestionnaireType', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(88, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionController: getQuestionsByEvaluationPeriodId', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(89, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: findByEvaluationId', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(90, 'Finalización exitosa del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: findByEvaluationId', '2024-02-29 07:50:33', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Finished'),
(91, 'Invocación del método: es.soltel.recolecta.controller.QuestionnaireAnswerController: interactedQuestionnaireAnswer', '2024-02-29 08:39:41', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Started'),
(92, 'Error en el método: es.soltel.recolecta.controller.QuestionnaireAnswerController: interactedQuestionnaireAnswer', '2024-02-29 08:39:42', '3', 1, NULL, '0:0:0:0:0:0:0:1', 'Error');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `titles`
--

CREATE TABLE `titles` (
  `id` bigint(20) NOT NULL,
  `eng` varchar(2000) DEFAULT NULL,
  `esp` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `titles`
--

INSERT INTO `titles` (`id`, `eng`, `esp`) VALUES
(1, '1 - No paywalls. The IP publishes its journals without charging fees to authors for publishing or to readers for reading. IPs do not charge mandatory fees to the journals they publish, nor are any other types of publishing fees involved. (REQUIRED)', '1 - No paywalls. The IP publishes its journals without charging fees to authors for publishing or to readers for reading. IPs do not charge mandatory fees to the journals they publish, nor are any other types of publishing fees involved. (REQUIRED)'),
(2, '3 - Financial support. The IP is directly or indirectly funded by public funds or other revenue streams to enable free access to the author and reader, ideally covering all costs. (REQUIRED)', '3 - Financial support. The IP is directly or indirectly funded by public funds or other revenue streams to enable free access to the author and reader, ideally covering all costs. (REQUIRED)'),
(3, '4 - Costs. Costs are identified year-on-year. IPs are able to plan their annual costs and to balance them with expected incomes and in-kind contributions using a tracking system, such as a budget. (DESIRED)', '4 - 	Costs. Costs are identified year-on-year. IPs are able to plan their annual costs and to balance them with expected incomes and in-kind contributions using a tracking system, such as a budget. (DESIRED)'),
(4, '5 - Sustainability plan. The IP considers the medium-term economic viability of its Diamond OA model. It has a clear overview of available funding sources and other relevant external and internal (in-kind) resources, aligned with set expectations of future maintenance and developmental costs. In achieving its goals, an IP preferably deploys collaborative strategies and uses common open infrastructures, to cut costs and raise efficiency. (DESIRED) ', '5 - Sustainability plan. The IP considers the medium-term economic viability of its Diamond OA model. It has a clear overview of available funding sources and other relevant external and internal (in-kind) resources, aligned with set expectations of future maintenance and developmental costs. In achieving its goals, an IP preferably deploys collaborative strategies and uses common open infrastructures, to cut costs and raise efficiency. (DESIRED) '),
(5, '6 - Transparency on funding. An explicit statement about the IP’s funding streams is available on the IP website. The in-kind and voluntary contributions are acknowledged. (DESIRED)', '6 - Transparency on funding. An explicit statement about the IP’s funding streams is available on the IP website. The in-kind and voluntary contributions are acknowledged. (DESIRED)'),
(6, '7 - Editorial operations. Editorial operations related to content and peer review are independent and free from influence from the bodies that financially support the IP or bodies that support individual publications of the IP. (REQUIRED)', '7 - Editorial operations. Editorial operations related to content and peer review are independent and free from influence from the bodies that financially support the IP or bodies that support individual publications of the IP. (REQUIRED)'),
(7, '8 - Revenue streams. The origin of the revenue streams is in line with the values, expectations, and traditions in the disciplines the IP is serving. They do not have an impact on editorial independence. Any conflicts of interest between additional revenue streams (including commercial activity) and authors, reviewers or editors are clearly indicated. (DESIRED)', '8 - Revenue streams. The origin of the revenue streams is in line with the values, expectations, and traditions in the disciplines the IP is serving. They do not have an impact on editorial independence. Any conflicts of interest between additional revenue streams (including commercial activity) and authors, reviewers or editors are clearly indicated. (DESIRED)'),
(8, '1 - Scholarly community. The IP is owned by the scholarly community, i.e. scholarly organisation, and not by a commercial publisher. (REQUIRED)', '1 - Scholarly community. The IP is owned by the scholarly community, i.e. scholarly organisation, and not by a commercial publisher. (REQUIRED)'),
(9, '2 - Ownership statement. The IP has a defined statement about the ownership of the individual journals it publishes. It includes the legal parameters governing the relationship between the IP and its published journals, the determination of ownership for each title, and the explicit definition of the rights/duties afforded to editors within the IP in a precise and unambiguous articulation. This also includes details about the discontinuation of the individual journal, and the transfer and preservation of its assets. (REQUIRED)', '2 - Ownership statement. The IP has a defined statement about the ownership of the individual journals it publishes. It includes the legal parameters governing the relationship between the IP and its published journals, the determination of ownership for each title, and the explicit definition of the rights/duties afforded to editors within the IP in a precise and unambiguous articulation. This also includes details about the discontinuation of the individual journal, and the transfer and preservation of its assets. (REQUIRED)'),
(10, '3 - Changes in ownership. Changes in the ownership, relationships and rights/duties must be handled with care and transparently by IPs. A change in the service provider (for example, publishing infrastructure) can be achieved without changing the journal title, owner, or publisher. (REQUIRED)', '3 - Changes in ownership. Changes in the ownership, relationships and rights/duties must be handled with care and transparently by IPs. A change in the service provider (for example, publishing infrastructure) can be achieved without changing the journal title, owner, or publisher. (REQUIRED)'),
(11, '4 - Transparency in ownership. The IP offers information about its ownership structure on its website. (REQUIRED)', '4 - Transparency in ownership. The IP offers information about its ownership structure on its website. (REQUIRED)'),
(12, '5 - Mission. All journals published by the IP ensure that their mission statement, aims, or scope are easily accessible on their website, with a clear structure and content. (REQUIRED)', '5 - Mission. All journals published by the IP ensure that their mission statement, aims, or scope are easily accessible on their website, with a clear structure and content. (REQUIRED)'),
(13, '6 - Scholarly community driven. The IP governance has mechanisms to liaise with scholarly community stakeholders and to allow their input on its strategic direction and decision-making. This information is displayed on the IP website. (DESIRED)', '6 - 	Scholarly community driven. The IP governance has mechanisms to liaise with scholarly community stakeholders and to allow their input on its strategic direction and decision-making. This information is displayed on the IP website. (DESIRED)'),
(14, '7 - Selection procedure. The IP makes sure that all its journals have procedures for the selection of members of editorial bodies that should include details of their mandate’s length, the regular renewal process, and clearly defined procedures for the dissolution of the board. This information is displayed on each journal’s website. (REQUIRED)', '7 - 	Selection procedure. The IP makes sure that all its journals have procedures for the selection of members of editorial bodies that should include details of their mandate’s length, the regular renewal process, and clearly defined procedures for the dissolution of the board. This information is displayed on each journal’s website. (REQUIRED)'),
(15, '8 - Co-publishing. The IP makes sure that relationships among co-publishers are defined by a formal agreement. It is also clearly indicated that the publication is a co-publication on the IP website.  (REQUIRED)', '8 - Co-publishing. The IP makes sure that relationships among co-publishers are defined by a formal agreement. It is also clearly indicated that the publication is a co-publication on the IP website.  (REQUIRED)'),
(16, '9 - Roles and responsibilities. All journals of the IP must have a clear definition of the roles and responsibilities of the IP, editorial bodies, owners and publishers towards authors, reviewers, readers and the scientific community, journal and platform owners, IP, and the public. Roles and responsibilities related to the peer review process are described in detail, and crucial aspects of the peer review process must not be left to publication technicians or AI.  (REQUIRED)', '9 - 	Roles and responsibilities. All journals of the IP must have a clear definition of the roles and responsibilities of the IP, editorial bodies, owners and publishers towards authors, reviewers, readers and the scientific community, journal and platform owners, IP, and the public. Roles and responsibilities related to the peer review process are described in detail, and crucial aspects of the peer review process must not be left to publication technicians or AI.  (REQUIRED)'),
(17, '10 - Transparency in editorial board selection. The IP must offer information about the editorial board selection protocol on its website. (DESIRED)', '10 - Transparency in editorial board selection. The IP must offer information about the editorial board selection protocol on its website. (DESIRED)'),
(18, '11 - Policy between IP and SPs. IPs might have commercial and non-commercial relationships with various SPs that are responsible for distinct technical and non-technical aspects of the workflow (e.g. ownership of infrastructure, copy-editing and typesetting services used, etc.). The IP is clear about the workflow and the use of SPs and relationships with them. These might be different for each SP and for different journals. (DESIRED)', '11 - Policy between IP and SPs. IPs might have commercial and non-commercial relationships with various SPs that are responsible for distinct technical and non-technical aspects of the workflow (e.g. ownership of infrastructure, copy-editing and typesetting services used, etc.). The IP is clear about the workflow and the use of SPs and relationships with them. These might be different for each SP and for different journals. (DESIRED)'),
(19, '12 - Policy between IP journals and SPs. The IP has transparent protocols guiding relationships with all SPs involved in the production of individual journals based on the legal agreements. (DESIRED)', '12 - Policy between IP journals and SPs. The IP has transparent protocols guiding relationships with all SPs involved in the production of individual journals based on the legal agreements. (DESIRED)'),
(20, '1 - Open Science policy. The IP has an Open Science policy that shows it is aware of the value of the OS and understands what it entails. (DESIRED)', '1 - Open Science policy. The IP has an Open Science policy that shows it is aware of the value of the OS and understands what it entails. (DESIRED)'),
(21, '2 - Open Access. The IP publishes its journals in Open Access. (REQUIRED)', '2 - Open Access. The IP publishes its journals in Open Access. (REQUIRED)'),
(22, '3 - Facilitating compliance with Open Access mandates. The IP will enable compliance of their authors with the open access mandates of their funding agencies, as well as the institutional, and/or national OA policies regarding journal articles. (REQUIRED)', '3 - Facilitating compliance with Open Access mandates. The IP will enable compliance of their authors with the open access mandates of their funding agencies, as well as the institutional, and/or national OA policies regarding journal articles. (REQUIRED)'),
(23, '4 - Underlying research data. Recognizing the essential role of the availability of the article\'s underlying data in supporting conclusions and reproducibility, the IP implements an output-level policy for this data in all its journals.This policy can be different for different journals. This information is displayed on the IP/journal  website. (REQUIRED)', '4 - Underlying research data. Recognizing the essential role of the availability of the article\'s underlying data in supporting conclusions and reproducibility, the IP implements an output-level policy for this data in all its journals.This policy can be different for different journals. This information is displayed on the IP/journal  website. (REQUIRED)'),
(24, '5 - Data policy content. The IP policy encourages the submission of underlying data for publications to be available to editors and reviewers during the manuscript review process. Additionally, it stipulates that this data will be accessible to all individuals by the time of publication in a FAIR manner through repositories, persistent identifiers (PIDs) and their connection from the publication to the data and from the data to the publication, and publicly available metadata.  (DESIRED)', '5 - Data policy content. The IP policy encourages the submission of underlying data for publications to be available to editors and reviewers during the manuscript review process. Additionally, it stipulates that this data will be accessible to all individuals by the time of publication in a FAIR manner through repositories, persistent identifiers (PIDs) and their connection from the publication to the data and from the data to the publication, and publicly available metadata.  (DESIRED)'),
(25, '6 - Research protocols and methods. The IP has an output-level policy on research protocols and methods availability for all its journals. It encourages sharing them in public repositories, using PIDs for making the relevant connections. This is a good open science practice that allows others to replicate and build on published work. This information is displayed on the IP website.  (DESIRED)', '6 - Research protocols and methods. The IP has an output-level policy on research protocols and methods availability for all its journals. It encourages sharing them in public repositories, using PIDs for making the relevant connections. This is a good open science practice that allows others to replicate and build on published work. This information is displayed on the IP website.  (DESIRED)'),
(26, '7 - Open research software. To facilitate reproducibility and FAIRification of research, the IP encourages the use of free/open source software. To this end, in all its journals, it defines a policy on the availability of research software and asks authors for a statement of availability.  (DESIRED)', '7 - Open research software. To facilitate reproducibility and FAIRification of research, the IP encourages the use of free/open source software. To this end, in all its journals, it defines a policy on the availability of research software and asks authors for a statement of availability.  (DESIRED)'),
(27, '8 - Publication and sharing of negative scientific results. IPs acknowledge that the publication of negative or unexpected scientific results and data that do not confirm the initial hypotheses and experimental designs of the authors contribute to the advancement of science and scholarship. (DESIRED)', '8 - Publication and sharing of negative scientific results. IPs acknowledge that the publication of negative or unexpected scientific results and data that do not confirm the initial hypotheses and experimental designs of the authors contribute to the advancement of science and scholarship. (DESIRED)'),
(28, '9 - Rights retention policy. The IP guarantees that authors retain sufficient rights for their works to enable them to be openly accessible and immediately reusable, in all its journals.  (REQUIRED)', '9 - Rights retention policy. The IP guarantees that authors retain sufficient rights for their works to enable them to be openly accessible and immediately reusable, in all its journals.  (REQUIRED)'),
(29, '10 - Open Licence. All contributions are published under an open licence (preferably CC-BY) to ensure further reuse without restrictions. (REQUIRED)', '10 - Open Licence. All contributions are published under an open licence (preferably CC-BY) to ensure further reuse without restrictions. (REQUIRED)'),
(30, '11 - Third-party copyright. The IP has a clear policy on reusing third-party materials in journal articles and how to deal with all the complexities that arise from combining elements with different usage rights.  (DESIRED)', '11 - Third-party copyright. The IP has a clear policy on reusing third-party materials in journal articles and how to deal with all the complexities that arise from combining elements with different usage rights.  (DESIRED)'),
(31, '12 - User’s rights. The IP provides their users with complete and reliable information about the terms of use of all its journals content and services through its website. Users’ rights, conditions of reuse, and redistribution of content and metadata are clearly described and labelled in human and computer-readable form, using standardised systems of open licences and rights statements. (DESIRED)', '12 - User’s rights. The IP provides their users with complete and reliable information about the terms of use of all its journals content and services through its website. Users’ rights, conditions of reuse, and redistribution of content and metadata are clearly described and labelled in human and computer-readable form, using standardised systems of open licences and rights statements. (DESIRED)'),
(32, '13 - Transparency on rights retention publication policy. Publishing agreements or terms of use describe the content ownership and reuse rights. This information is publicly available on the IP website. (REQUIRED)', '13 - Transparency on rights retention publication policy. Publishing agreements or terms of use describe the content ownership and reuse rights. This information is publicly available on the IP website. (REQUIRED)'),
(33, '14 - Deposits of published articles. The IP allows dissemination of the article preprint version at any time, the Author Accepted Manuscript (AAM) version after acceptance, and/or the Version of Record (VoR) after publication in an Open Access repository of the authors\' choice. The IP may require authors to choose repositories that guarantee that the final version of the work is referenced. (REQUIRED)', '14 - Deposits of published articles. The IP allows dissemination of the article preprint version at any time, the Author Accepted Manuscript (AAM) version after acceptance, and/or the Version of Record (VoR) after publication in an Open Access repository of the authors\' choice. The IP may require authors to choose repositories that guarantee that the final version of the work is referenced. (REQUIRED)'),
(34, '15 - Acceptance of preprints. The IP accepts the submission of unreviewed and peer-reviewed preprints that are already available on preprint servers or in open repositories. (DESIRED)', '15 - Acceptance of preprints. The IP accepts the submission of unreviewed and peer-reviewed preprints that are already available on preprint servers or in open repositories. (DESIRED)'),
(35, '1 - Editorial independence. Editors-in-chief and/or Editorial Board have full responsibility over the entire editorial content of each journal published by the IP. (REQUIRED)', '1 - Editorial independence. Editors-in-chief and/or Editorial Board have full responsibility over the entire editorial content of each journal published by the IP. (REQUIRED)'),
(36, '2 - Editorial bodies transparency. All journals of the IP have a clearly defined and publicly displayed composition and constitution of its editorial bodies including: the names of the members of the editorial bodies; their editorial functions and roles; the names of the members of the editorial board and their affiliations; their PIDs and links to their institutional profiles to unambiguously specify the identity and affiliation of individual editorial bodies and board members.  (REQUIRED)', '2 - Editorial bodies transparency. All journals of the IP have a clearly defined and publicly displayed composition and constitution of its editorial bodies including: the names of the members of the editorial bodies; their editorial functions and roles; the names of the members of the editorial board and their affiliations; their PIDs and links to their institutional profiles to unambiguously specify the identity and affiliation of individual editorial bodies and board members.  (REQUIRED)'),
(37, '3 - Communication procedures between journals and IP. There are established procedures to facilitate communication between the editorial bodies of each individual journal and the IP. These procedures aim to discuss political, commercial, or other incidents that might compromise the scientific credibility of the publication. They also facilitate the agreement on collaborative measures to ensure that such incidents do not influence the editor\'s decisions. Correspondence between referees, authors and publishers is subject to legal protection and kept confidential as needed. (REQUIRED)', '3 - Communication procedures between journals and IP. There are established procedures to facilitate communication between the editorial bodies of each individual journal and the IP. These procedures aim to discuss political, commercial, or other incidents that might compromise the scientific credibility of the publication. They also facilitate the agreement on collaborative measures to ensure that such incidents do not influence the editor\'s decisions. Correspondence between referees, authors and publishers is subject to legal protection and kept confidential as needed. (REQUIRED)'),
(38, '4 - Skills/training. The IP supports and/or provides continuous community-oriented training and education of journal editors and authors, which is essential in navigating the rapidly changing scholarly communication environment. The IP promotes high-quality, inclusive, and impactful academic publishing practices by equipping stakeholders with the knowledge and skills necessary to adapt to technological, ethical, and policy changes and open science principles. (DESIRED)', '4 - Skills/training. The IP supports and/or provides continuous community-oriented training and education of journal editors and authors, which is essential in navigating the rapidly changing scholarly communication environment. The IP promotes high-quality, inclusive, and impactful academic publishing practices by equipping stakeholders with the knowledge and skills necessary to adapt to technological, ethical, and policy changes and open science principles. (DESIRED)'),
(39, '5 - Engaging stakeholders. The IP supports and encourages its stakeholders\' engagement in initiatives, communities and associations promoting high-quality publishing practices and open science principles.  (DESIRED)', '5 - Engaging stakeholders. The IP supports and encourages its stakeholders\' engagement in initiatives, communities and associations promoting high-quality publishing practices and open science principles.  (DESIRED)'),
(40, '6 - Peer review. The IP guarantees that all submitted manuscripts undergo a rigorous evaluation process before and/or after publication that is in line with accepted practices in the relevant discipline. This evaluation process can involve peer review, or another type of evaluation by more than one competent person who has no conflict of interest with the author(s). (REQUIRED)', '6 - Peer review. The IP guarantees that all submitted manuscripts undergo a rigorous evaluation process before and/or after publication that is in line with accepted practices in the relevant discipline. This evaluation process can involve peer review, or another type of evaluation by more than one competent person who has no conflict of interest with the author(s). (REQUIRED)'),
(41, '7 - Peer-review policy and procedures. The IP guarantees that all its journals\' websites publish a policy describing the evaluation or review process (both internal and external), indicating whether it is double-anonymous, single-anonymous, open peer review, etc., and specifying the tasks expected of reviewers. It will indicate whether reviews will be public or not  (in which case, it will be specified whether they are transmitted to the author in full or edited). It also specifies the type of manuscript evaluation process. Evaluation can take place before or after publication, depending on the review model adopted: pre-publication peer review, post-publication peer review (Publish, Review, Curate – PRC - models), overlay journals, etc. (REQUIRED)', '7 - Peer-review policy and procedures. The IP guarantees that all its journals\' websites publish a policy describing the evaluation or review process (both internal and external), indicating whether it is double-anonymous, single-anonymous, open peer review, etc., and specifying the tasks expected of reviewers. It will indicate whether reviews will be public or not  (in which case, it will be specified whether they are transmitted to the author in full or edited). It also specifies the type of manuscript evaluation process. Evaluation can take place before or after publication, depending on the review model adopted: pre-publication peer review, post-publication peer review (Publish, Review, Curate – PRC - models), overlay journals, etc. (REQUIRED)'),
(42, '8 - Lack of endogeny. The IP guarantees that manuscripts being reviewed by a closed circle of people who are well acquainted with each other or work in the same institution are minimised. The IP is also proactively highlighting when an editorial board member publishes in their own journal and how they recused themselves from the usual editorial and peer review process, providing this information at the article level for relevant articles. A formal recusal process is also described in the editorial policy to help manage a potential Conflict of Interest of an editor or reviewer and avoid receiving preferential treatment. (REQUIRED)', '8 - Lack of endogeny. The IP guarantees that manuscripts being reviewed by a closed circle of people who are well acquainted with each other or work in the same institution are minimised. The IP is also proactively highlighting when an editorial board member publishes in their own journal and how they recused themselves from the usual editorial and peer review process, providing this information at the article level for relevant articles. A formal recusal process is also described in the editorial policy to help manage a potential Conflict of Interest of an editor or reviewer and avoid receiving preferential treatment. (REQUIRED)'),
(43, '9 - Open peer review. The IP provides reviewers of all its journals with the possibility of signing and/or publishing their reviews (either with their identity only visible to the editor, author, and the other reviewers, or with their identity visible to all readers), and/or the IP makes reviews publicly available to a broader community for providing comments and participating in the assessment process. (DESIRED)', '9 - Open peer review. The IP provides reviewers of all its journals with the possibility of signing and/or publishing their reviews (either with their identity only visible to the editor, author, and the other reviewers, or with their identity visible to all readers), and/or the IP makes reviews publicly available to a broader community for providing comments and participating in the assessment process. (DESIRED)'),
(44, '10 - Other contributors’ copyright. The IP guarantees that reviewers and other contributors hold the copyright of their reviews and contributions, and that editorial bodies and institutions retain ownership of all correspondence and mailing lists compiled on the electronic submission system put at their disposal by the IP for all its journals.  (DESIRED) ', '10 - Other contributors’ copyright. The IP guarantees that reviewers and other contributors hold the copyright of their reviews and contributions, and that editorial bodies and institutions retain ownership of all correspondence and mailing lists compiled on the electronic submission system put at their disposal by the IP for all its journals.  (DESIRED) '),
(45, '11 - Acknowledgement of reviewers. The IP guarantees that all its journals publish the list of reviewers (with their consent) on a regular basis, at least every three years. (DESIRED)', '11 - Acknowledgement of reviewers. The IP guarantees that all its journals publish the list of reviewers (with their consent) on a regular basis, at least every three years. (DESIRED)'),
(46, '12 - Incentives and rewards. The IP has an incentives and rewards policy available to all its journals that guarantees reviewers get proper acknowledgement and reward editorial work as an academic activity by the institution employing the editor. (DESIRED)', '12 - Incentives and rewards. The IP has an incentives and rewards policy available to all its journals that guarantees reviewers get proper acknowledgement and reward editorial work as an academic activity by the institution employing the editor. (DESIRED)'),
(47, '13 - Guidelines for author(s). The IP guarantees that all its journals have clear guidelines for authors on its website. These guidelines must contain information on: how to send manuscripts; type of accepted files; supplementary materials and accepted data files; style guidelines and manuscript writing requirements for the correct preparation of titles, abstracts, keywords, professional affiliation, and bibliographic references; the editorial process followed by submissions: criteria for acceptance or editorial flow, review process, proofreading, estimated time between each part of the process, review protocols, and selection and publication criteria. (REQUIRED)', '13 - Guidelines for author(s). The IP guarantees that all its journals have clear guidelines for authors on its website. These guidelines must contain information on: how to send manuscripts; type of accepted files; supplementary materials and accepted data files; style guidelines and manuscript writing requirements for the correct preparation of titles, abstracts, keywords, professional affiliation, and bibliographic references; the editorial process followed by submissions: criteria for acceptance or editorial flow, review process, proofreading, estimated time between each part of the process, review protocols, and selection and publication criteria. (REQUIRED)'),
(48, '14 - Guidelines for reviewers. The IP provides reviewers with clear instructions and guidance (reviewing forms, free text options, and checklists) on the journal’s aims and scope and what is expected of them in the review process. (REQUIRED)', '14 - Guidelines for reviewers. The IP provides reviewers with clear instructions and guidance (reviewing forms, free text options, and checklists) on the journal’s aims and scope and what is expected of them in the review process. (REQUIRED)'),
(49, '15 - Manual of style. The IP guarantees that each of its journals apply a manual of style. It includes the appropriate use of symbols, units, nomenclature, statistics, standards, and similar items, specifying the citation style adopted.  (REQUIRED)', '15 - Manual of style. The IP guarantees that each of its journals apply a manual of style. It includes the appropriate use of symbols, units, nomenclature, statistics, standards, and similar items, specifying the citation style adopted.  (REQUIRED)'),
(50, '16 - Suitable layout. The IP guarantees that each of its journals have a homogeneous layout.  (REQUIRED)', '16 - Suitable layout. The IP guarantees that each of its journals have a homogeneous layout.  (REQUIRED)'),
(51, '17 - Proofreading correction. The IP ensures that standard copy-editing and proofreading procedures are applied in all journals. (REQUIRED)', '17 - Proofreading correction. The IP ensures that standard copy-editing and proofreading procedures are applied in all journals. (REQUIRED)'),
(52, '18 - Languages of submission. The IP guarantees that all its journals clearly indicate on their website the languages in which manuscripts can be submitted. (REQUIRED)', '18 - Languages of submission. The IP guarantees that all its journals clearly indicate on their website the languages in which manuscripts can be submitted. (REQUIRED)'),
(53, '19 - Publishing timelines. The IP ensures that all its journals have a regular schedule of publication, either issue by issue or via continuous publication. Continuous publication is recommended in the interest of Open Science. The date of submission, acceptance and publication is visible for each article. (REQUIRED)', '19 - Publishing timelines. The IP ensures that all its journals have a regular schedule of publication, either issue by issue or via continuous publication. Continuous publication is recommended in the interest of Open Science. The date of submission, acceptance and publication is visible for each article. (REQUIRED)'),
(54, '21 - Guidelines for authorship and/or contributorship. The IP provides authorship and/or contributorship guidance, respecting the norms of relevant research disciplines. Contributions for deserving authorship include not only the writing but also the activities related to the conceptualisation and execution of the research, collection and production of the research data/materials, analysis and interpretation. Agreement on how these contributions will be acknowledged in the publication must be reached before submission of the manuscript, preferably early in the research process. The IP supports good communication between all parties within the research to prevent or resolve possible disputes and authorship manipulation. The contribution of each researcher/collaborator should be published in the journal article. (DESIRED)  ', '21 - Guidelines for authorship and/or contributorship. The IP provides authorship and/or contributorship guidance, respecting the norms of relevant research disciplines. Contributions for deserving authorship include not only the writing but also the activities related to the conceptualisation and execution of the research, collection and production of the research data/materials, analysis and interpretation. Agreement on how these contributions will be acknowledged in the publication must be reached before submission of the manuscript, preferably early in the research process. The IP supports good communication between all parties within the research to prevent or resolve possible disputes and authorship manipulation. The contribution of each researcher/collaborator should be published in the journal article. (DESIRED)  '),
(55, '22 - Research and publication ethics. The IP guarantees that all its journals adhere to international standards and codes of ethics or have their own publicly accessible code of ethics. This information is displayed on the IP website.  (REQUIRED)', '22 - Research and publication ethics. The IP guarantees that all its journals adhere to international standards and codes of ethics or have their own publicly accessible code of ethics. This information is displayed on the IP website.  (REQUIRED)'),
(56, '23 - Conflict of interest. The IP guarantees that all its journals have consistent workflows requiring authors, editors, and reviewers to disclose general and financial conflicts of interest or the absence thereof (i.e. in the Conflict-of-Interest statement). This information is displayed on the IP website.  (REQUIRED)', '23 - Conflict of interest. The IP guarantees that all its journals have consistent workflows requiring authors, editors, and reviewers to disclose general and financial conflicts of interest or the absence thereof (i.e. in the Conflict-of-Interest statement). This information is displayed on the IP website.  (REQUIRED)'),
(57, '24 - Misconduct policy. The IP guarantees that all its journals have a policy on how plagiarism, fabrication (making up data), falsification (manipulating materials, equipment, data, images or processes), complaints appeals/allegations of research misconduct, and corrections, withdrawals and retractions are handled. This policy is displayed on the IP website. (REQUIRED)', '24 - Misconduct policy. The IP guarantees that all its journals have a policy on how plagiarism, fabrication (making up data), falsification (manipulating materials, equipment, data, images or processes), complaints appeals/allegations of research misconduct, and corrections, withdrawals and retractions are handled. This policy is displayed on the IP website. (REQUIRED)'),
(58, 'Guidelines for Artificial Intelligence. The IP has a guideline on generative AI tools, respecting changes of the research process in a technology-enhanced environment, and is informing and educating researchers/authors, reviewers and editors about responsible use of generative AI tools. This policy is displayed on the IP website. (DESIRED)', 'Guidelines for Artificial Intelligence. The IP has a guideline on generative AI tools, respecting changes of the research process in a technology-enhanced environment, and is informing and educating researchers/authors, reviewers and editors about responsible use of generative AI tools. This policy is displayed on the IP website. (DESIRED)'),
(59, '1 - Use of platform. The IP guarantees that a digital publishing platform supports online submission, editorial, and publishing workflows of all its journals. (REQUIRED)', '1 - Use of platform. The IP guarantees that a digital publishing platform supports online submission, editorial, and publishing workflows of all its journals. (REQUIRED)'),
(60, '2 - Documentation. The IP guarantees that all its journals are supplied with user instructions and documentation for editorial staff and end users, and have a General Terms and Conditions for the use of the publishing infrastructure or platform. This information is displayed on their website. (DESIRED)', '2 - Documentation. The IP guarantees that all its journals are supplied with user instructions and documentation for editorial staff and end users, and have a General Terms and Conditions for the use of the publishing infrastructure or platform. This information is displayed on their website. (DESIRED)'),
(61, '3 - Security. The IP ensures that the infrastructure complies with the security standards established by law. When no standard exists in the region, the IP will apply at least those measures necessary and sufficient to keep the system protected from malicious intrusions. (REQUIRED)', '3 - Security. The IP ensures that the infrastructure complies with the security standards established by law. When no standard exists in the region, the IP will apply at least those measures necessary and sufficient to keep the system protected from malicious intrusions. (REQUIRED)'),
(62, '4 - Basic functionalities. The IP guarantees that all its publishing platforms have basic functionalities like assisting in the publishing workflow, being compliant with standards, allowing multilingual support, preferably including an accessible, responsive and usable interface, being interoperable or being able to support rich metadata. (REQUIRED)', '4 - Basic functionalities. The IP guarantees that all its publishing platforms have basic functionalities like assisting in the publishing workflow, being compliant with standards, allowing multilingual support, preferably including an accessible, responsive and usable interface, being interoperable or being able to support rich metadata. (REQUIRED)'),
(63, '5 - Advanced functionalities. The IP guarantees (where relevant) that all its publishing platforms offer advanced functionalities like post-publication evaluation and commenting, support for multimedia, and open peer review. (DESIRED)', '5 - Advanced functionalities. The IP guarantees (where relevant) that all its publishing platforms offer advanced functionalities like post-publication evaluation and commenting, support for multimedia, and open peer review. (DESIRED)'),
(64, '6 - Basic infrastructure management. The IP guarantees that all its publishing platforms are well maintained, updated, regularly backed up and protected against security threats. (REQUIRED)', '6 - Basic infrastructure management. The IP guarantees that all its publishing platforms are well maintained, updated, regularly backed up and protected against security threats. (REQUIRED)'),
(65, '7 - Advanced infrastructure management. The IP guarantees that all its publishing platforms are maintained and developed following best practices and standards for IT service management to ensure improved efficiency, quality and consistency, risk reduction, and continuous improvement. (DESIRED)', '7 - Advanced infrastructure management. The IP guarantees that all its publishing platforms are maintained and developed following best practices and standards for IT service management to ensure improved efficiency, quality and consistency, risk reduction, and continuous improvement. (DESIRED)'),
(66, '8 - Long term preservation. The IP has a publicly displayed archival and digital preservation policy which is consistently implemented. The published content is deposited in at least one digital preservation service.  (REQUIRED)', '8 - Long term preservation. The IP has a publicly displayed archival and digital preservation policy which is consistently implemented. The published content is deposited in at least one digital preservation service.  (REQUIRED)'),
(67, '9 - Interoperability protocols. The IP guarantees that all its publishing platforms support widely adopted metadata exchange protocols (OAI-PMH, API) and most usual metadata schemas. The IP’s publishing platforms also support bulk export of metadata and they indicate on their website which interoperability protocols are used and how to access them. (DESIRED) ', '9 - Interoperability protocols. The IP guarantees that all its publishing platforms support widely adopted metadata exchange protocols (OAI-PMH, API) and most usual metadata schemas. The IP’s publishing platforms also support bulk export of metadata and they indicate on their website which interoperability protocols are used and how to access them. (DESIRED) '),
(68, '10 - Core metadata. The IP guarantees that all its journals provide the following essential metadata on landing pages and via metadata exchange protocols, in human and machine-readable formats and under CC0 licence for each published item: title, full names and institutional affiliations – including country/region – of all contributing author(s), abstracts and keywords, funding information (as a minimum the name of the funder and the grant number/identifier), and information about the open access status, copyright holder and licensing. (REQUIRED)', '10 - Core metadata. The IP guarantees that all its journals provide the following essential metadata on landing pages and via metadata exchange protocols, in human and machine-readable formats and under CC0 licence for each published item: title, full names and institutional affiliations – including country/region – of all contributing author(s), abstracts and keywords, funding information (as a minimum the name of the funder and the grant number/identifier), and information about the open access status, copyright holder and licensing. (REQUIRED)'),
(69, '11 - Complete metadata. Complete metadata, including bibliographic references, are immediately deposited in a registration agency in line with open citation initiatives. (DESIRED)', '11 - Complete metadata. Complete metadata, including bibliographic references, are immediately deposited in a registration agency in line with open citation initiatives. (DESIRED)'),
(70, '12 - Persistent identifiers. The IP guarantees that all its journals provide a dedicated unique URL (landing page) and a persistent identifier for each published item. Standard numbers and other persistent identifiers for articles, contributors, as well as other relevant persistent identifiers, are also provided in human and machine-readable formats. (REQUIRED)', '12 - Persistent identifiers. The IP guarantees that all its journals provide a dedicated unique URL (landing page) and a persistent identifier for each published item. Standard numbers and other persistent identifiers for articles, contributors, as well as other relevant persistent identifiers, are also provided in human and machine-readable formats. (REQUIRED)'),
(71, '13 - Registration of persistent identifiers. The IP guarantees that the article identifiers are registered with registration agencies immediately at publication. (REQUIRED)', '13 - Registration of persistent identifiers. The IP guarantees that the article identifiers are registered with registration agencies immediately at publication. (REQUIRED)'),
(72, '14 - Text and data mining. The IP guarantees that all its publishing platform supports automatic downloading, extraction and indexing of the full texts and the associated metadata with the aim of improving the visibility and usability of the published content. (DESIRED)', '14 - Text and data mining. The IP guarantees that all its publishing platform supports automatic downloading, extraction and indexing of the full texts and the associated metadata with the aim of improving the visibility and usability of the published content. (DESIRED)'),
(73, '15 - Formats. The IP guarantees that all its journals tag their full-text content in interoperable formats and provide access in multiple digital formats (e.g. PDF, HTML, XML, ePub, etc.), at least one of which is suitable for preservation. (DESIRED)', '15 - Formats. The IP guarantees that all its journals tag their full-text content in interoperable formats and provide access in multiple digital formats (e.g. PDF, HTML, XML, ePub, etc.), at least one of which is suitable for preservation. (DESIRED)'),
(74, '16 - Citations. The IP guarantees that all its journals specify how to reference published articles (how to cite), and offer different options for different standards (APA, Harvard, ISO, Vancouver or other). (REQUIRED)', '16 - Citations. The IP guarantees that all its journals specify how to reference published articles (how to cite), and offer different options for different standards (APA, Harvard, ISO, Vancouver or other). (REQUIRED)'),
(75, '17 - Personal Data Protection. The IP guarantees that all its journals comply with the General Data Protection Regulation (GDPR) as well as all relevant personal data regulations. This policy is displayed on the IP website and ensured. (DESIRED)', '17 - Personal Data Protection. The IP guarantees that all its journals comply with the General Data Protection Regulation (GDPR) as well as all relevant personal data regulations. This policy is displayed on the IP website and ensured. (DESIRED)'),
(76, '18 - Open source. The IP strives to ensure that the publishing infrastructure of all its journals is based on free and open-source software, with publicly available code. This facilitates interoperability, the sharing of expertise, and collaboration between institutional publishers, while at the same time allowing them to retain know-how and technological autonomy to avoid vendor lock-in and adapt developments to their local needs.  (DESIRED)', '18 - Open source. The IP strives to ensure that the publishing infrastructure of all its journals is based on free and open-source software, with publicly available code. This facilitates interoperability, the sharing of expertise, and collaboration between institutional publishers, while at the same time allowing them to retain know-how and technological autonomy to avoid vendor lock-in and adapt developments to their local needs.  (DESIRED)'),
(77, '19 - Return to the community. The IP participates in the development community by contributing bugs detected, translations into local languages, documentation, bug fixing or developments to promote collective growth. (DESIRED)', '19 - Return to the community. The IP participates in the development community by contributing bugs detected, translations into local languages, documentation, bug fixing or developments to promote collective growth. (DESIRED)'),
(78, '1 - Visibility. The IP makes sure that reasonable technical measures are taken towards improving the visibility of all its journals in search engines (general and academic), and aggregators. (REQUIRED)', '1 - Visibility. The IP makes sure that reasonable technical measures are taken towards improving the visibility of all its journals in search engines (general and academic), and aggregators. (REQUIRED)'),
(79, '2 - Discoverability. The IP works to increase the discoverability of its published content by registering its platform for harvesting by relevant discovery services and aggregator databases, and by submitting its journals to abstracting and indexing databases and citation indexes. (REQUIRED)', '2 - Discoverability. The IP works to increase the discoverability of its published content by registering its platform for harvesting by relevant discovery services and aggregator databases, and by submitting its journals to abstracting and indexing databases and citation indexes. (REQUIRED)'),
(80, '3 - Communication channels. The IP provides all its journals with unhindered and reliable channels for communication and dissemination of their content to academia and society at large. The use of social media and social networking, collaboration with the media and the use of traditional and modern dissemination methods, which  help spread the content to a broader audience, are guided by the IPs dissemination policies.  (DESIRED)', '3 - Communication channels. The IP provides all its journals with unhindered and reliable channels for communication and dissemination of their content to academia and society at large. The use of social media and social networking, collaboration with the media and the use of traditional and modern dissemination methods, which  help spread the content to a broader audience, are guided by the IPs dissemination policies.  (DESIRED)');
INSERT INTO `titles` (`id`, `eng`, `esp`) VALUES
(81, '4 - Community management. The community of users of the IP services is regularly informed of developments, policy changes, updates, new features, and functionalities, as well as about new publications. All the information provided by the IP is accurate, reliable, regularly updated, and not misleading in any way. (DESIRED)', '4 - Community management. The community of users of the IP services is regularly informed of developments, policy changes, updates, new features, and functionalities, as well as about new publications. All the information provided by the IP is accurate, reliable, regularly updated, and not misleading in any way. (DESIRED)'),
(82, '5 - Marketing. The IP engages in appropriate and well-targeted promotional activities (including solicitation of manuscripts for their publications). It must support the promotion of all its journals\' published content (e.g. by inviting post-publication reviews of outputs, inviting and moderating post-publication online comments, writing press releases, working with the media) in order to reach broader sectors of society. (DESIRED)', '5 - Marketing. The IP engages in appropriate and well-targeted promotional activities (including solicitation of manuscripts for their publications). It must support the promotion of all its journals\' published content (e.g. by inviting post-publication reviews of outputs, inviting and moderating post-publication online comments, writing press releases, working with the media) in order to reach broader sectors of society. (DESIRED)'),
(83, '6 - Visual identity. The IP provides a common visual identity for all its journals (e.g. by logos, corporate images, colours, etc.). (DESIRED)', '6 - Visual identity. The IP provides a common visual identity for all its journals (e.g. by logos, corporate images, colours, etc.). (DESIRED)'),
(84, '7 - Metrics. The IP guarantees that all its journals offer comprehensive, accurate and reliable metric indicators detailing content usage, e.g. article-level metrics (visits, views, downloads, citations), along with publication-level metrics, altmetric indicators, and geographical distribution of visitors. (DESIRED)', '7 - Metrics. The IP guarantees that all its journals offer comprehensive, accurate and reliable metric indicators detailing content usage, e.g. article-level metrics (visits, views, downloads, citations), along with publication-level metrics, altmetric indicators, and geographical distribution of visitors. (DESIRED)'),
(85, '8 - Analytical tools. The IP is clear on the analytical tools, algorithms, methodologies and/or external service providers that are employed for data generation and collection. This requirement is aligned with data protection regulation. (DESIRED)', '8 - Analytical tools. The IP is clear on the analytical tools, algorithms, methodologies and/or external service providers that are employed for data generation and collection. This requirement is aligned with data protection regulation. (DESIRED)'),
(86, '1 - EDIB policy at the IP level. The IP has a policy that sets principles, commitments and actions for promoting EDIB in terms of linguistic, gender, cultural, academic, geographical, institutional, economic backgrounds and disabilities within its governing and management bodies, its editorial staff and boards, as well as reviewer pools and authors pool. It includes a Gender Equity Plan (GEP). This information is displayed on the IP website. (DESIRED)', '1 - EDIB policy at the IP level. The IP has a policy that sets principles, commitments and actions for promoting EDIB in terms of linguistic, gender, cultural, academic, geographical, institutional, economic backgrounds and disabilities within its governing and management bodies, its editorial staff and boards, as well as reviewer pools and authors pool. It includes a Gender Equity Plan (GEP). This information is displayed on the IP website. (DESIRED)'),
(87, '2 - EDIB monitoring. The IP monitors progress in its journals’ EDIB policies and GEP. For that purpose, it collects and makes available data on gender balance, on country of  origin, on organisational affiliation, and on the proportion of early career researchers (1-7 years from degree) among the members of the governing and management bodies, of the editorial staff and boards, of the reviewer pools and of the authors\' pool. This is done without detracting from individuals’ rights to not report some of this data if they don\'t wish to. (DESIRED)', '2 - EDIB monitoring. The IP monitors progress in its journals’ EDIB policies and GEP. For that purpose, it collects and makes available data on gender balance, on country of  origin, on organisational affiliation, and on the proportion of early career researchers (1-7 years from degree) among the members of the governing and management bodies, of the editorial staff and boards, of the reviewer pools and of the authors\' pool. This is done without detracting from individuals’ rights to not report some of this data if they don\'t wish to. (DESIRED)'),
(88, '3 - Equity in submissions and decisions. The IP guarantees that all their journals accept submission of manuscripts within their thematic scope and language from all potential authors and that decision-making concerning content acceptance is without regard to authors’ language, race, gender, age, sexual orientation, religious belief, ethnic origin, geographic location, or political philosophy. (REQUIRED)', '3 - Equity in submissions and decisions. The IP guarantees that all their journals accept submission of manuscripts within their thematic scope and language from all potential authors and that decision-making concerning content acceptance is without regard to authors’ language, race, gender, age, sexual orientation, religious belief, ethnic origin, geographic location, or political philosophy. (REQUIRED)'),
(89, '4 - Bias-free language. The IP uses bias-free language related to age, disability, gender, racial and ethnic identity, sexual orientation, and socioeconomic status in all its communications and public information. (REQUIRED)', '4 - Bias-free language. The IP uses bias-free language related to age, disability, gender, racial and ethnic identity, sexual orientation, and socioeconomic status in all its communications and public information. (REQUIRED)'),
(90, '5 - Research data sensitiveness. The IP guarantees that all its journals require authors to inform whether the underlying research data of their publications are sensitive to age, disability status, sex, gender identity, racial and ethnic identity, sexual orientation, and /or socioeconomic status. (REQUIRED)', '5 - Research data sensitiveness. The IP guarantees that all its journals require authors to inform whether the underlying research data of their publications are sensitive to age, disability status, sex, gender identity, racial and ethnic identity, sexual orientation, and /or socioeconomic status. (REQUIRED)'),
(91, '6 - Accessible website: The IP guarantees that its websites and those of the journals are accessible under the terms of applicable international, national or local laws and policies. (REQUIRED).', '6 - Accessible website: The IP guarantees that its websites and those of the journals are accessible under the terms of applicable international, national or local laws and policies. (REQUIRED).'),
(92, '7 - Monitoring. The IP collects and makes available data on the amount of feedback received relating to shortcomings in all their journals\' accessibility standards, as well as a record of improvements to the standards. (DESIRED)', '7 - Monitoring. The IP collects and makes available data on the amount of feedback received relating to shortcomings in all their journals\' accessibility standards, as well as a record of improvements to the standards. (DESIRED)'),
(93, '8 - Abstracts. The IP guarantees that all its journals facilitate that abstracts are published in at least two languages, where relevant. (DESIRED) ', '8 - Abstracts. The IP guarantees that all its journals facilitate that abstracts are published in at least two languages, where relevant. (DESIRED) '),
(94, '9 - Plain language summary. The IP guarantees that all its journals provide a plain language summary alongside the traditional scientific abstract. (DESIRED) ', '9 - Plain language summary. The IP guarantees that all its journals provide a plain language summary alongside the traditional scientific abstract. (DESIRED) '),
(95, '10 - Full text. The IP’s journals can publish full texts in more than one language, either bilingual, simultaneously as separate documents in the same journal, or sequentially in other journals. (REQUIRED)', '10 - Full text. The IP’s journals can publish full texts in more than one language, either bilingual, simultaneously as separate documents in the same journal, or sequentially in other journals. (REQUIRED)'),
(96, '11 - Website and content. The IP recommends that all its journals’ websites offer multilingual content. The information given on the site must be the same in all languages. (REQUIRED)', '11 - Website and content. The IP recommends that all its journals’ websites offer multilingual content. The information given on the site must be the same in all languages. (REQUIRED)'),
(97, '12 - Translation. The IP guarantees that all its journals provide support for human translation and language-check services to authors. (DESIRED) ', '12 - Translation. The IP guarantees that all its journals provide support for human translation and language-check services to authors. (DESIRED) '),
(98, '13 - Language technologies. The IP encourages all its journals to integrate a computer assisted translation (CAT) tool/solution on the website if tools that can provide sufficiently good translations are available, and encourages journals to provide machine-translation friendly abstracts. Automatic machine translations will not be used for publishing manuscripts in language(s) other than the original without the supervision of translators and/or experts. (DESIRED) ', '13 - Language technologies. The IP encourages all its journals to integrate a computer assisted translation (CAT) tool/solution on the website if tools that can provide sufficiently good translations are available, and encourages journals to provide machine-translation friendly abstracts. Automatic machine translations will not be used for publishing manuscripts in language(s) other than the original without the supervision of translators and/or experts. (DESIRED) '),
(99, '14 - Metadata translation. The IP recommends that all its journals offer metadata in English if the language of the text is not English. (DESIRED)  ', '14 - Metadata translation. The IP recommends that all its journals offer metadata in English if the language of the text is not English. (DESIRED)  '),
(100, '1. We had no major concerns with our costs last year.', '1. We had no major concerns with our costs last year.'),
(101, '2. Our costs were in balance with our budget last year.', '2. Our costs were in balance with our budget last year.'),
(102, '3. Our costs were in balance with our budget in the last 3 years.', '3. Our costs were in balance with our budget in the last 3 years.'),
(103, '4. A cost increase or discontinuation of the external services we use would not put our service at risk.', '4. A cost increase or discontinuation of the external services we use would not put our service at risk.'),
(104, '5. If in-kind contributions stopped, we would not need to stop publishing.', '5. If in-kind contributions stopped, we would not need to stop publishing.'),
(105, '6. We have a contingency plan in the event that our costs significantly increase.', '6. We have a contingency plan in the event that our costs significantly increase.'),
(106, '1. We have sufficient financial resources and staff to provide the service we need for next year (excluding peer reviewers).', '1. We have sufficient financial resources and staff to provide the service we need for next year (excluding peer reviewers).'),
(107, '2. We have enough financial resources and staff to provide the service we need in the next 3 years (excluding peer reviewers).', '2. We have enough financial resources and staff to provide the service we need in the next 3 years (excluding peer reviewers).'),
(108, '3. We have at least two, non-interdependent stable funding streams.', '3. We have at least two, non-interdependent stable funding streams.'),
(109, '4. We have the capability to continue activities if income streams are disrupted.', '4. We have the capability to continue activities if income streams are disrupted.'),
(110, '5. We do not need grants or sponsorship to cover deficits.', '5. We do not need grants or sponsorship to cover deficits.'),
(111, '6. We are not regularly at the risk of having to close down.', '6. We are not regularly at the risk of having to close down.'),
(112, '1. Our revenue streams are relatively stable.', '1. Our revenue streams are relatively stable.'),
(113, '2. We do not rely on grants, subsidies, sponsorship or philanthropic funds to cover key operational costs.', '2. We do not rely on grants, subsidies, sponsorship or philanthropic funds to cover key operational costs.'),
(114, '3. Last year we did not need grants, subsidies, sponsorship or philanthropic funds.', '3. Last year we did not need grants, subsidies, sponsorship or philanthropic funds.'),
(115, '4. In the last 3 years, we have not needed grants, subsidies, sponsorship or philanthropic funds.', '4. In the last 3 years, we have not needed grants, subsidies, sponsorship or philanthropic funds.'),
(116, '5. In the last 3 years, we successfully raised the external funds we needed.', '5. In the last 3 years, we successfully raised the external funds we needed.'),
(117, '6. Our revenue streams neither have a negative impact on quality nor on our editorial independence.', '6. Our revenue streams neither have a negative impact on quality nor on our editorial independence.'),
(118, '1. For core activities, we only use free services and infrastructure, but only to keep costs down.', '1. For core activities, we only use free services and infrastructure, but only to keep costs down.'),
(119, '2. We collaborate with others in order to cut costs and raise efficiency.', '2. We collaborate with others in order to cut costs and raise efficiency.'),
(120, '3. We use shared open infrastructures in order to cut costs and raise efficiency.', '3. We use shared open infrastructures in order to cut costs and raise efficiency.'),
(121, '4. We develop shared services for others.', '4. We develop shared services for others.'),
(122, '5. We develop shared services for others and gain sufficient income from it to cover operational costs.', '5. We develop shared services for others and gain sufficient income from it to cover operational costs.'),
(123, '6. We develop shared services for others and gain sufficient income from it to cover operational and development costs.', '6. We develop shared services for others and gain sufficient income from it to cover operational and development costs.'),
(124, '1. The volume of our publishing services has grown in the last year, as has our budget proportionally more or less.', '1. The volume of our publishing services has grown in the last year, as has our budget proportionally more or less.'),
(125, '2. The volume of our publishing activities and services has grown in the last 3 years, as has our budget proportionally more or less.', '2. The volume of our publishing activities and services has grown in the last 3 years, as has our budget proportionally more or less.'),
(126, '3. We would like to grow or expand our servcies and have the necessary resoruces to do so.', '3. We would like to grow or expand our servcies and have the necessary resoruces to do so.'),
(127, '4. We have a budget for upgrades and improvements.', '4. We have a budget for upgrades and improvements.'),
(128, '5. We work hard to have the funds to grow our service.', '5. We work hard to have the funds to grow our service.'),
(129, '6. Year on year we have the funds to grow our service.', '6. Year on year we have the funds to grow our service.'),
(130, '1. We have a fair overview of our income and/or expenses.', '1. We have a fair overview of our income and/or expenses.'),
(131, '2. We have a good overview of our income and/or expenses.', '2. We have a good overview of our income and/or expenses.'),
(132, '3. Expenses are tracked year-on-year.', '3. Expenses are tracked year-on-year.'),
(133, '4. We follow an itemised budget, e.g. by expense and income type, including in-kind contributions.', '4. We follow an itemised budget, e.g. by expense and income type, including in-kind contributions.'),
(134, '5. We create and maintain an officially approved annual operating budget.', '5. We create and maintain an officially approved annual operating budget.'),
(135, '6. We have clear checks and balances in place to prevent fraud, theft, etc.', '6. We have clear checks and balances in place to prevent fraud, theft, etc.'),
(136, '1. Our funding sources are stable for the next year.', '1. Our funding sources are stable for the next year.'),
(137, '2. Our funding sources are stable for the next 3 years.', '2. Our funding sources are stable for the next 3 years.'),
(138, '3. We financially plan for the future, and need to.', '3. We financially plan for the future, and need to.'),
(139, '4. We have a strategy to guarantee the medium-term (3-year) economic viability of our service.', '4. We have a strategy to guarantee the medium-term (3-year) economic viability of our service.'),
(140, '5. We plan for different financial scenarios.', '5. We plan for different financial scenarios.'),
(141, '6. We have a plan in place if operations had to cease.', '6. We have a plan in place if operations had to cease.'),
(143, '1 - No paywalls. The IP publishes its journals without charging fees to authors for publishing or to readers for reading. IPs do not charge mandatory fees to the journals they publish, nor are any other types of publishing fees involved. (REQUIRED)', '1 - No paywalls. The IP publishes its journals without charging fees to authors for publishing or to readers for reading. IPs do not charge mandatory fees to the journals they publish, nor are any other types of publishing fees involved. (REQUIRED)');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `afiliacion_institucional` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `pais` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `active`, `afiliacion_institucional`, `apellidos`, `email`, `last_login`, `n_delete_state`, `nombre`, `pais`, `password`) VALUES
(3, b'1', 'DFGSDF', 'gfdgfd', 'antonio.gutierrez@soltel.es', NULL, 1, 'gfdr', 'DZ', 'tt/aiR0oiZmDPCiDHTkffQ==');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_repository`
--

CREATE TABLE `user_repository` (
  `id` bigint(20) NOT NULL,
  `n_delete_state` int(11) DEFAULT NULL,
  `repository_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user_repository`
--

INSERT INTO `user_repository` (`id`, `n_delete_state`, `repository_id`, `user_id`) VALUES
(1, 1, 1, 1),
(2, 1, 2, 2),
(3, 1, 3, 3);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `action`
--
ALTER TABLE `action`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `assigned_evaluators_entity`
--
ALTER TABLE `assigned_evaluators_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK20ygkx0n5t6m3l571rfsgwvat` (`evaluation_id`),
  ADD KEY `FKtg1o4xojh7qshpg3288kc1grj` (`user_id`);

--
-- Indices de la tabla `cat_questions`
--
ALTER TABLE `cat_questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKi8noh2xjyomuuxgcgd7obvkl4` (`questionnaire_type_id`);

--
-- Indices de la tabla `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `evaluation`
--
ALTER TABLE `evaluation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjl2wvvvpb9gphlkctv37wtwiv` (`questionnaire_type_id`),
  ADD KEY `FK1glt3r5gq1lwxf6atj16kwj8r` (`repository_id`);

--
-- Indices de la tabla `evaluation_action_history_entity`
--
ALTER TABLE `evaluation_action_history_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrnsd8t2hx3pmq005mjervspup` (`action_id_id`),
  ADD KEY `FKt5n60ydsfhmyuu9x89h79x2mq` (`evaluation_id_id`),
  ADD KEY `FKafdqn6ud1lu59jy1ihr659vfu` (`user_id`);

--
-- Indices de la tabla `evaluation_period`
--
ALTER TABLE `evaluation_period`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9ms6srkdvq7ccbndtc6w8lbh9` (`questionnaire_type_id`);

--
-- Indices de la tabla `evaluation_response_entity`
--
ALTER TABLE `evaluation_response_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKq8pbekevs0rv2rs7uiq7tnin5` (`questionnaire_question_entity_id`);

--
-- Indices de la tabla `file_entity`
--
ALTER TABLE `file_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKp4fumjp20kxhy1r7ngjs54txx` (`questionnaire_question_id`);

--
-- Indices de la tabla `initial_questions`
--
ALTER TABLE `initial_questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1cbg9r7oj0klihaie20wcji3t` (`cat_question`),
  ADD KEY `FKof3v1y8rlfq4uja25wed1kn2` (`title_id`);

--
-- Indices de la tabla `institucion`
--
ALTER TABLE `institucion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `password_recover`
--
ALTER TABLE `password_recover`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_on2j9mgppdsffsh45tbow1pbc` (`user_id`);

--
-- Indices de la tabla `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt000viglnqtt541n44inv1wdw` (`cat_question_id`),
  ADD KEY `FK94o6v5qnve6iee87ktt4k5ah` (`period_id`),
  ADD KEY `FKoawq5l2psns1dl36lu1dk7mpb` (`title_id`);

--
-- Indices de la tabla `questionnaire_answer_entity`
--
ALTER TABLE `questionnaire_answer_entity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKymouwo2qg4ybtq8kv5ridlcv` (`questionnaire_question_id`),
  ADD KEY `FKlu95jxiel0g44bphysoxby0e6` (`file_id`);

--
-- Indices de la tabla `questionnaire_entity`
--
ALTER TABLE `questionnaire_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKacibmg0atccuf0lht8mdow9pq` (`evaluation_id`),
  ADD KEY `FKj6gv38ncnb6oylfvfjbmqjfmb` (`period_id`);

--
-- Indices de la tabla `questionnaire_question_entity`
--
ALTER TABLE `questionnaire_question_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjt3nug8caqyrd674djswk0f3p` (`question_id`),
  ADD KEY `FK2f9nk0xsykwa66im9ftl23uag` (`questionnaire_id`);

--
-- Indices de la tabla `questionnaire_to_close_entity`
--
ALTER TABLE `questionnaire_to_close_entity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK8owec4xkrpwq3g7gfwle74uor` (`questionnaire_id`);

--
-- Indices de la tabla `questionnaire_type`
--
ALTER TABLE `questionnaire_type`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `repository`
--
ALTER TABLE `repository`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7uj9eohddlxbh2f9utkf2sa0r` (`institucion_id`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rol_relation`
--
ALTER TABLE `rol_relation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKau11a1ibj66au9i1dio2g96bh` (`rol_id`),
  ADD KEY `FKdfrosg07rbf3l04gu9x1ru7j0` (`user_id`);

--
-- Indices de la tabla `system_logs`
--
ALTER TABLE `system_logs`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `titles`
--
ALTER TABLE `titles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user_repository`
--
ALTER TABLE `user_repository`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpvcdld6lfvv7i7w92pwd38g9b` (`repository_id`),
  ADD KEY `FKr2bip45m4vvwbnfnywdd7wgfn` (`user_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `assigned_evaluators_entity`
--
ALTER TABLE `assigned_evaluators_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `cat_questions`
--
ALTER TABLE `cat_questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `config`
--
ALTER TABLE `config`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `evaluation`
--
ALTER TABLE `evaluation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `evaluation_action_history_entity`
--
ALTER TABLE `evaluation_action_history_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `evaluation_response_entity`
--
ALTER TABLE `evaluation_response_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `file_entity`
--
ALTER TABLE `file_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `initial_questions`
--
ALTER TABLE `initial_questions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=242;

--
-- AUTO_INCREMENT de la tabla `institucion`
--
ALTER TABLE `institucion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `question`
--
ALTER TABLE `question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=142;

--
-- AUTO_INCREMENT de la tabla `questionnaire_answer_entity`
--
ALTER TABLE `questionnaire_answer_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `questionnaire_entity`
--
ALTER TABLE `questionnaire_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `questionnaire_question_entity`
--
ALTER TABLE `questionnaire_question_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=142;

--
-- AUTO_INCREMENT de la tabla `questionnaire_to_close_entity`
--
ALTER TABLE `questionnaire_to_close_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `questionnaire_type`
--
ALTER TABLE `questionnaire_type`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `repository`
--
ALTER TABLE `repository`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `rol_relation`
--
ALTER TABLE `rol_relation`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `system_logs`
--
ALTER TABLE `system_logs`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT de la tabla `titles`
--
ALTER TABLE `titles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=285;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `user_repository`
--
ALTER TABLE `user_repository`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `assigned_evaluators_entity`
--
ALTER TABLE `assigned_evaluators_entity`
  ADD CONSTRAINT `FK20ygkx0n5t6m3l571rfsgwvat` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation` (`id`),
  ADD CONSTRAINT `FKtg1o4xojh7qshpg3288kc1grj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Filtros para la tabla `cat_questions`
--
ALTER TABLE `cat_questions`
  ADD CONSTRAINT `FKi8noh2xjyomuuxgcgd7obvkl4` FOREIGN KEY (`questionnaire_type_id`) REFERENCES `questionnaire_type` (`id`);

--
-- Filtros para la tabla `evaluation`
--
ALTER TABLE `evaluation`
  ADD CONSTRAINT `FK1glt3r5gq1lwxf6atj16kwj8r` FOREIGN KEY (`repository_id`) REFERENCES `user_repository` (`id`),
  ADD CONSTRAINT `FKjl2wvvvpb9gphlkctv37wtwiv` FOREIGN KEY (`questionnaire_type_id`) REFERENCES `questionnaire_type` (`id`);

--
-- Filtros para la tabla `evaluation_action_history_entity`
--
ALTER TABLE `evaluation_action_history_entity`
  ADD CONSTRAINT `FKafdqn6ud1lu59jy1ihr659vfu` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKrnsd8t2hx3pmq005mjervspup` FOREIGN KEY (`action_id_id`) REFERENCES `action` (`id`),
  ADD CONSTRAINT `FKt5n60ydsfhmyuu9x89h79x2mq` FOREIGN KEY (`evaluation_id_id`) REFERENCES `evaluation` (`id`);

--
-- Filtros para la tabla `evaluation_period`
--
ALTER TABLE `evaluation_period`
  ADD CONSTRAINT `FK9ms6srkdvq7ccbndtc6w8lbh9` FOREIGN KEY (`questionnaire_type_id`) REFERENCES `questionnaire_type` (`id`);

--
-- Filtros para la tabla `evaluation_response_entity`
--
ALTER TABLE `evaluation_response_entity`
  ADD CONSTRAINT `FKq8pbekevs0rv2rs7uiq7tnin5` FOREIGN KEY (`questionnaire_question_entity_id`) REFERENCES `questionnaire_question_entity` (`id`);

--
-- Filtros para la tabla `file_entity`
--
ALTER TABLE `file_entity`
  ADD CONSTRAINT `FKp4fumjp20kxhy1r7ngjs54txx` FOREIGN KEY (`questionnaire_question_id`) REFERENCES `questionnaire_question_entity` (`id`);

--
-- Filtros para la tabla `initial_questions`
--
ALTER TABLE `initial_questions`
  ADD CONSTRAINT `FK1cbg9r7oj0klihaie20wcji3t` FOREIGN KEY (`cat_question`) REFERENCES `cat_questions` (`id`),
  ADD CONSTRAINT `FKof3v1y8rlfq4uja25wed1kn2` FOREIGN KEY (`title_id`) REFERENCES `titles` (`id`);

--
-- Filtros para la tabla `password_recover`
--
ALTER TABLE `password_recover`
  ADD CONSTRAINT `FKh5my8ua002hmicc6gl4ubgrt2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Filtros para la tabla `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FK94o6v5qnve6iee87ktt4k5ah` FOREIGN KEY (`period_id`) REFERENCES `evaluation_period` (`id`),
  ADD CONSTRAINT `FKoawq5l2psns1dl36lu1dk7mpb` FOREIGN KEY (`title_id`) REFERENCES `titles` (`id`),
  ADD CONSTRAINT `FKt000viglnqtt541n44inv1wdw` FOREIGN KEY (`cat_question_id`) REFERENCES `cat_questions` (`id`);

--
-- Filtros para la tabla `questionnaire_answer_entity`
--
ALTER TABLE `questionnaire_answer_entity`
  ADD CONSTRAINT `FKlu95jxiel0g44bphysoxby0e6` FOREIGN KEY (`file_id`) REFERENCES `file_entity` (`id`),
  ADD CONSTRAINT `FKooaj8ga642cnrpew2l5banrb5` FOREIGN KEY (`questionnaire_question_id`) REFERENCES `questionnaire_question_entity` (`id`);

--
-- Filtros para la tabla `questionnaire_entity`
--
ALTER TABLE `questionnaire_entity`
  ADD CONSTRAINT `FKacibmg0atccuf0lht8mdow9pq` FOREIGN KEY (`evaluation_id`) REFERENCES `evaluation` (`id`),
  ADD CONSTRAINT `FKj6gv38ncnb6oylfvfjbmqjfmb` FOREIGN KEY (`period_id`) REFERENCES `evaluation_period` (`id`);

--
-- Filtros para la tabla `questionnaire_question_entity`
--
ALTER TABLE `questionnaire_question_entity`
  ADD CONSTRAINT `FK2f9nk0xsykwa66im9ftl23uag` FOREIGN KEY (`questionnaire_id`) REFERENCES `questionnaire_entity` (`id`),
  ADD CONSTRAINT `FKjt3nug8caqyrd674djswk0f3p` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Filtros para la tabla `questionnaire_to_close_entity`
--
ALTER TABLE `questionnaire_to_close_entity`
  ADD CONSTRAINT `FK9rb8c31ukolmx33a366ct1ncy` FOREIGN KEY (`questionnaire_id`) REFERENCES `questionnaire_entity` (`id`);

--
-- Filtros para la tabla `repository`
--
ALTER TABLE `repository`
  ADD CONSTRAINT `FK7uj9eohddlxbh2f9utkf2sa0r` FOREIGN KEY (`institucion_id`) REFERENCES `institucion` (`id`);

--
-- Filtros para la tabla `rol_relation`
--
ALTER TABLE `rol_relation`
  ADD CONSTRAINT `FKau11a1ibj66au9i1dio2g96bh` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`),
  ADD CONSTRAINT `FKdfrosg07rbf3l04gu9x1ru7j0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Filtros para la tabla `user_repository`
--
ALTER TABLE `user_repository`
  ADD CONSTRAINT `FKpvcdld6lfvv7i7w92pwd38g9b` FOREIGN KEY (`repository_id`) REFERENCES `repository` (`id`),
  ADD CONSTRAINT `FKr2bip45m4vvwbnfnywdd7wgfn` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
