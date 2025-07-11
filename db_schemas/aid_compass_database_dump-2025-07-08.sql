--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: a_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.a_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.a_seq OWNER TO admin;

--
-- Name: app_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.app_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.app_seq OWNER TO admin;

--
-- Name: appointment_duration_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.appointment_duration_seq
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.appointment_duration_seq OWNER TO admin;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: appointment_durations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.appointment_durations (
    appointment_duration bigint NOT NULL,
    id bigint NOT NULL,
    owner_id uuid NOT NULL
);


ALTER TABLE public.appointment_durations OWNER TO admin;

--
-- Name: appointments; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.appointments (
    date date NOT NULL,
    end_t time(6) without time zone NOT NULL,
    start_t time(6) without time zone NOT NULL,
    status smallint NOT NULL,
    type smallint NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    customer_id uuid NOT NULL,
    volunteer_id uuid NOT NULL,
    review character varying(1000),
    description character varying(255) NOT NULL,
    CONSTRAINT appointments_type_check CHECK (((type >= 0) AND (type <= 1)))
);


ALTER TABLE public.appointments OWNER TO admin;

--
-- Name: authorities; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authorities (
    id bigint NOT NULL,
    authority character varying(255) NOT NULL,
    CONSTRAINT authorities_authority_check CHECK (((authority)::text = ANY (ARRAY['ROLE_ANONYMOUS'::text, 'ROLE_UNCONFIRMED_USER'::text, 'ROLE_USER'::text, 'ROLE_CUSTOMER'::text, 'ROLE_DOCTOR'::text, 'ROLE_JURIST'::text, 'ROLE_ADMIN'::text, 'ROLE_SCHEDULE_TASK_SERVICE'::text, 'ROLE_PROMETHEUS_SERVICE'::text])))
);


ALTER TABLE public.authorities OWNER TO admin;

--
-- Name: avatars; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.avatars (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    avatar_url character varying(255) NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL
);


ALTER TABLE public.avatars OWNER TO admin;

--
-- Name: cont_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.cont_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cont_seq OWNER TO admin;

--
-- Name: cont_t_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.cont_t_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cont_t_seq OWNER TO admin;

--
-- Name: contact_progreses; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.contact_progreses (
    filled_email boolean,
    filled_phone boolean,
    user_id uuid NOT NULL
);


ALTER TABLE public.contact_progreses OWNER TO admin;

--
-- Name: contact_types; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.contact_types (
    id integer NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT contact_types_type_check CHECK (((type)::text = ANY (ARRAY[('EMAIL'::character varying)::text, ('PHONE_NUMBER'::character varying)::text])))
);


ALTER TABLE public.contact_types OWNER TO admin;

--
-- Name: contacts; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.contacts (
    is_confirmed boolean NOT NULL,
    is_linked_to boolean NOT NULL,
    is_primary boolean NOT NULL,
    type_id integer NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    owner_id uuid NOT NULL,
    contact character varying(255) NOT NULL
);


ALTER TABLE public.contacts OWNER TO admin;

--
-- Name: continue_flags; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.continue_flags (
    batch_size integer NOT NULL,
    id integer NOT NULL,
    page_number integer,
    should_continue boolean NOT NULL,
    task_type_code integer NOT NULL
);


ALTER TABLE public.continue_flags OWNER TO admin;

--
-- Name: continue_flags_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

ALTER TABLE public.continue_flags ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.continue_flags_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: customers; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.customers (
    birthday_date date,
    gender smallint NOT NULL,
    profile_progress integer,
    profile_status_id integer NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    detail_id bigint NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    id uuid NOT NULL,
    first_name character varying(20),
    last_name character varying(20),
    second_name character varying(20),
    CONSTRAINT customers_gender_check CHECK (((gender >= 0) AND (gender <= 1)))
);


ALTER TABLE public.customers OWNER TO admin;

--
-- Name: deactivated_auth_token; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.deactivated_auth_token (
    deactivate_at timestamp(6) with time zone NOT NULL,
    id uuid NOT NULL
);


ALTER TABLE public.deactivated_auth_token OWNER TO admin;

--
-- Name: detail_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.detail_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.detail_seq OWNER TO admin;

--
-- Name: doc_spec_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.doc_spec_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.doc_spec_seq OWNER TO admin;

--
-- Name: doctor_specialization_relations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctor_specialization_relations (
    specialization_id integer NOT NULL,
    doctor_id uuid NOT NULL
);


ALTER TABLE public.doctor_specialization_relations OWNER TO admin;

--
-- Name: doctor_specializations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctor_specializations (
    id integer NOT NULL,
    specialization character varying(255) NOT NULL,
    CONSTRAINT doctor_specializations_specialization_check CHECK (((specialization)::text = ANY (ARRAY[('ALLERGOLOGIST'::character varying)::text, ('CARDIOLOGIST'::character varying)::text, ('DENTIST'::character varying)::text, ('ENDOCRINOLOGIST'::character varying)::text, ('GASTROENTEROLOGIST'::character varying)::text, ('GENERAL_PRACTITIONER'::character varying)::text, ('GYNECOLOGIST'::character varying)::text, ('IMMUNOLOGIST'::character varying)::text, ('INFECTIOUS_DISEASE_SPECIALIST'::character varying)::text, ('NEPHROLOGIST'::character varying)::text, ('NEUROLOGIST'::character varying)::text, ('NEUROSURGEON'::character varying)::text, ('NUTRITIONIST'::character varying)::text, ('OCCUPATIONAL_THERAPIST'::character varying)::text, ('ONCOLOGIST'::character varying)::text, ('OPHTHALMOLOGIST'::character varying)::text, ('ORTHOPEDIST'::character varying)::text, ('OTOLARYNGOLOGIST'::character varying)::text, ('PEDIATRICIAN'::character varying)::text, ('PSYCHIATRIST'::character varying)::text, ('PSYCHOLOGIST'::character varying)::text, ('PSYCHOTHERAPIST'::character varying)::text, ('PULMONOLOGIST'::character varying)::text, ('REHABILITATION_SPECIALIST'::character varying)::text, ('RHEUMATOLOGIST'::character varying)::text, ('SPEECH_THERAPIST'::character varying)::text, ('SPORTS_MEDICINE_SPECIALIST'::character varying)::text, ('SURGEON'::character varying)::text])))
);


ALTER TABLE public.doctor_specializations OWNER TO admin;

--
-- Name: doctors; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.doctors (
    gender smallint NOT NULL,
    is_approved boolean NOT NULL,
    profile_progress integer NOT NULL,
    profile_status_id integer NOT NULL,
    working_experience integer NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    detail_id bigint NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    id uuid NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    second_name character varying(20) NOT NULL,
    specialization_detail character varying(255) NOT NULL,
    CONSTRAINT doctors_gender_check CHECK (((gender >= 0) AND (gender <= 1)))
);


ALTER TABLE public.doctors OWNER TO admin;

--
-- Name: jur_spec_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.jur_spec_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.jur_spec_seq OWNER TO admin;

--
-- Name: jur_type_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.jur_type_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.jur_type_seq OWNER TO admin;

--
-- Name: jurist_specialization_relations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.jurist_specialization_relations (
    specialization_id integer NOT NULL,
    jurist_id uuid NOT NULL
);


ALTER TABLE public.jurist_specialization_relations OWNER TO admin;

--
-- Name: jurist_specializations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.jurist_specializations (
    id integer NOT NULL,
    specialization character varying(255) NOT NULL,
    CONSTRAINT jurist_specializations_specialization_check CHECK (((specialization)::text = ANY (ARRAY[('REFUGEE_RIGHTS'::character varying)::text, ('ADMINISTRATIVE_LAW'::character varying)::text, ('SOCIAL_LAW'::character varying)::text, ('HOUSING_LAW'::character varying)::text, ('PROPERTY_LAW'::character varying)::text, ('FAMILY_LAW'::character varying)::text, ('MIGRATION_LAW'::character varying)::text])))
);


ALTER TABLE public.jurist_specializations OWNER TO admin;

--
-- Name: jurist_types; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.jurist_types (
    id integer NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT jurist_types_type_check CHECK (((type)::text = ANY (ARRAY[('NOTARY'::character varying)::text, ('LAWYER'::character varying)::text, ('LEGAL_AID'::character varying)::text])))
);


ALTER TABLE public.jurist_types OWNER TO admin;

--
-- Name: jurists; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.jurists (
    gender smallint NOT NULL,
    is_approved boolean NOT NULL,
    profile_progress integer NOT NULL,
    profile_status_id integer NOT NULL,
    type_id integer NOT NULL,
    working_experience integer NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    detail_id bigint NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    id uuid NOT NULL,
    first_name character varying(20) NOT NULL,
    last_name character varying(20) NOT NULL,
    second_name character varying(20) NOT NULL,
    specialization_detail character varying(255) NOT NULL,
    CONSTRAINT jurists_gender_check CHECK (((gender >= 0) AND (gender <= 1)))
);


ALTER TABLE public.jurists OWNER TO admin;

--
-- Name: profile_status; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.profile_status (
    id integer NOT NULL,
    status character varying(255) NOT NULL,
    CONSTRAINT profile_status_status_check CHECK (((status)::text = ANY (ARRAY[('INCOMPLETE'::character varying)::text, ('COMPLETE'::character varying)::text])))
);


ALTER TABLE public.profile_status OWNER TO admin;

--
-- Name: profile_status_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

ALTER TABLE public.profile_status ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.profile_status_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: schedule_progreses; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.schedule_progreses (
    filled_appointment_duration boolean,
    filled_first_work_day boolean,
    user_id uuid NOT NULL
);


ALTER TABLE public.schedule_progreses OWNER TO admin;

--
-- Name: services; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.services (
    id uuid NOT NULL,
    authority_id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    service_name character varying NOT NULL,
    password character varying NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    is_locked boolean NOT NULL
);


ALTER TABLE public.services OWNER TO admin;

--
-- Name: task_journal; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.task_journal (
    batch_size integer NOT NULL,
    page_number integer,
    task_status_code integer NOT NULL,
    task_type_code integer NOT NULL,
    end_t timestamp(6) with time zone,
    execution_time bigint,
    id bigint NOT NULL,
    start_t timestamp(6) with time zone NOT NULL
);


ALTER TABLE public.task_journal OWNER TO admin;

--
-- Name: task_journal_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

ALTER TABLE public.task_journal ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.task_journal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: task_types; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.task_types (
    id integer NOT NULL,
    type character varying(255) NOT NULL,
    CONSTRAINT task_types_type_check CHECK (((type)::text = ANY ((ARRAY['DELETE_INTERVAL'::character varying, 'MARK_APPOINTMENT_SKIPPED'::character varying, 'NOTIFY_BEFORE_APPOINTMENT'::character varying])::text[])))
);


ALTER TABLE public.task_types OWNER TO admin;

--
-- Name: task_types_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

ALTER TABLE public.task_types ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.task_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: user_authority; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_authority (
    authrity_id bigint NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE public.user_authority OWNER TO admin;

--
-- Name: user_details; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_details (
    created_at timestamp(6) with time zone NOT NULL,
    id bigint NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL,
    user_id uuid NOT NULL,
    about_myself character varying(235),
    address character varying(255)
);


ALTER TABLE public.user_details OWNER TO admin;

--
-- Name: users; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.users (
    is_expired boolean NOT NULL,
    is_locked boolean NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    email_id bigint,
    updated_at timestamp(6) with time zone NOT NULL,
    id uuid NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO admin;

--
-- Name: w_i_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.w_i_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.w_i_seq OWNER TO admin;

--
-- Name: work_intervals; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.work_intervals (
    date date NOT NULL,
    end_t time(6) without time zone NOT NULL,
    start_t time(6) without time zone NOT NULL,
    id bigint NOT NULL,
    owner_id uuid NOT NULL
);


ALTER TABLE public.work_intervals OWNER TO admin;

--
-- Data for Name: appointment_durations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.appointment_durations (appointment_duration, id, owner_id) FROM stdin;
30	1	01977e79-f272-7f27-bed4-a81b480f17fd
30	2	01977e79-f2d6-7075-a662-c4d24ed8bb3e
30	3	01977e79-f336-722a-8a36-233ae9fe22f7
30	4	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
30	5	01977e79-f3fe-7b11-a9c7-e104137309cc
30	6	01977e79-f469-7573-ba3a-604cd272778f
30	8	01977e79-f542-7b27-95ab-c64f22f444ee
30	9	01977e79-f59f-7958-879a-20e6a5dbfbae
30	10	01977e79-f603-7282-bc16-c98712d0e44d
30	11	01977e79-f664-7879-b2ec-fe94caaacc28
30	12	01977e79-f6c6-7707-aedb-fe3468f41140
30	13	01977e79-f727-734f-abdb-bcd9c7e3ec00
30	14	01977e79-f788-7b5f-84df-0e1fea0599f3
30	15	01977e79-f7e9-7b37-ac67-06a8797bd7d4
30	16	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
30	17	01977e79-f8b4-7c00-8caa-745ef40934a6
30	18	01977e79-f90c-7eff-9b23-192773bf98bb
30	19	01977e79-f975-78ec-81e9-6e13cd6d568a
30	20	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
30	21	01977e79-fa48-76ee-b5ac-b933c85acc84
30	22	01977e79-faa9-7b86-a332-e10ef4ed001b
30	23	01977e79-fb15-743c-9d5a-b00ac2953735
30	24	01977e79-fb74-7d65-8dd8-fb25557c7426
30	25	01977e79-fbdd-7845-982f-a684fb6c9078
30	27	01977e79-fca6-7b48-a3a4-2c362c16081c
30	28	01977e79-fd05-7a4c-9854-1f283f24c417
30	29	01977e79-fd6e-737b-bdb8-d563d1ac8899
30	30	01977e79-fdcd-7647-9bfc-c4f208c7d607
30	31	01977e79-fe37-75a1-8b40-2fc606a56218
30	32	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
30	33	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
30	34	01977e79-ff63-73de-9441-b73d75415356
30	35	01977e79-ffca-7f6d-8b09-70a69d42bf8c
30	36	01977e7a-0026-79ac-a236-50d54f042a62
30	37	01977e7a-008b-7cb4-b539-be89d4f12cfc
30	38	01977e7a-00eb-72f3-8dd8-014dcf711be4
30	39	01977e7a-014a-74b6-81f5-7e8e435a3d75
30	40	01977e7a-01ab-77da-bcb4-15df70386907
45	41	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
15	42	0197c761-f334-7314-be66-7ed27933b7be
45	26	01977e79-fc3d-7e26-94be-b6531796eb62
\.


--
-- Data for Name: appointments; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.appointments (date, end_t, start_t, status, type, created_at, id, updated_at, customer_id, volunteer_id, review, description) FROM stdin;
2025-07-04	17:00:00	16:30:00	0	1	2025-06-17 15:20:18.99274+00	3	2025-06-17 15:20:18.99274+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-11	13:00:00	12:30:00	0	1	2025-06-17 15:20:19.369925+00	7	2025-06-17 15:20:19.369925+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	08:30:00	08:00:00	0	1	2025-06-17 15:20:19.5173+00	8	2025-06-17 15:20:19.5173+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-05	23:30:00	23:00:00	0	1	2025-06-17 15:20:19.53704+00	9	2025-06-17 15:20:19.53704+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-11	19:30:00	19:00:00	0	1	2025-06-17 15:20:19.609366+00	10	2025-06-17 15:20:19.609366+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-14	08:30:00	08:00:00	0	1	2025-06-17 15:20:19.95295+00	15	2025-06-17 15:20:19.95295+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-05	22:30:00	22:00:00	0	1	2025-06-17 15:20:20.093029+00	16	2025-06-17 15:20:20.093029+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-06-26	00:30:00	00:00:00	0	0	2025-06-17 15:20:20.241473+00	17	2025-06-17 15:20:20.241473+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-07	20:30:00	20:00:00	0	1	2025-06-17 15:20:20.258498+00	18	2025-06-17 15:20:20.258498+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-03	18:30:00	18:00:00	0	1	2025-06-17 15:20:20.394572+00	19	2025-06-17 15:20:20.394572+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-10	22:30:00	22:00:00	0	0	2025-06-17 15:20:20.441958+00	20	2025-06-17 15:20:20.441958+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-08	08:30:00	08:00:00	0	0	2025-06-17 15:20:20.537208+00	21	2025-06-17 15:20:20.537208+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	18:00:00	17:30:00	0	1	2025-06-17 15:20:20.689513+00	22	2025-06-17 15:20:20.689513+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-14	11:00:00	10:30:00	0	1	2025-06-17 15:20:20.708411+00	23	2025-06-17 15:20:20.708411+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-14	17:30:00	17:00:00	0	0	2025-06-17 15:20:21.00962+00	24	2025-06-17 15:20:21.00962+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-11	20:00:00	19:30:00	0	0	2025-06-17 15:20:21.039829+00	25	2025-06-17 15:20:21.039829+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-07	15:00:00	14:30:00	0	1	2025-06-17 15:20:21.058843+00	26	2025-06-17 15:20:21.058843+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-06	17:00:00	16:30:00	0	0	2025-06-17 15:20:21.075298+00	27	2025-06-17 15:20:21.075298+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-05	20:30:00	20:00:00	0	1	2025-06-17 15:20:21.102003+00	28	2025-06-17 15:20:21.102003+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-05	10:00:00	09:30:00	0	1	2025-06-17 15:20:21.122681+00	29	2025-06-17 15:20:21.122681+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-08	09:30:00	09:00:00	0	1	2025-06-17 15:20:21.141431+00	30	2025-06-17 15:20:21.141431+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-06-24	14:00:00	13:30:00	0	0	2025-06-17 15:46:32.267999+00	1598	2025-06-17 15:46:32.267999+00	01977e7c-e1ea-7674-a453-c35ee7829b40	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	тестовий запис
2025-07-09	20:30:00	20:00:00	0	1	2025-06-17 15:20:21.168987+00	31	2025-06-17 15:20:21.168987+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-06-19	01:00:00	00:30:00	3	1	2025-06-17 15:20:18.78512+00	1	2025-06-17 15:20:18.78512+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-06-20	08:30:00	08:00:00	3	1	2025-06-17 15:20:19.113714+00	4	2025-06-17 15:20:19.113714+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-15	21:00:00	20:30:00	0	0	2025-06-17 15:20:21.208066+00	32	2025-06-17 15:20:21.208066+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-05	17:30:00	17:00:00	0	1	2025-06-17 15:20:21.255794+00	33	2025-06-17 15:20:21.255794+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-14	17:00:00	16:30:00	0	1	2025-06-17 15:20:21.437276+00	36	2025-06-17 15:20:21.437276+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-08	11:00:00	10:30:00	0	1	2025-06-17 15:20:21.603843+00	37	2025-06-17 15:20:21.603843+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-15	21:00:00	20:30:00	0	0	2025-06-17 15:20:21.653692+00	38	2025-06-17 15:20:21.653692+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-08	14:00:00	13:30:00	0	0	2025-06-17 15:20:21.687414+00	39	2025-06-17 15:20:21.687414+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-14	15:00:00	14:30:00	0	0	2025-06-17 15:20:21.730993+00	40	2025-06-17 15:20:21.730993+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-11	12:30:00	12:00:00	0	1	2025-06-17 15:20:21.832112+00	41	2025-06-17 15:20:21.832112+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-04	14:30:00	14:00:00	0	1	2025-06-17 15:20:21.876905+00	42	2025-06-17 15:20:21.876905+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-13	08:30:00	08:00:00	0	0	2025-06-17 15:20:22.024487+00	43	2025-06-17 15:20:22.024487+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-10	09:30:00	09:00:00	0	1	2025-06-17 15:20:22.054401+00	44	2025-06-17 15:20:22.054401+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-05	19:00:00	18:30:00	0	0	2025-06-17 15:20:22.105481+00	45	2025-06-17 15:20:22.105481+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-26	09:30:00	09:00:00	0	1	2025-06-17 15:20:22.443946+00	47	2025-06-17 15:20:22.443946+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-12	20:30:00	20:00:00	0	1	2025-06-17 15:20:22.474694+00	49	2025-06-17 15:20:22.474694+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-05	14:30:00	14:00:00	0	0	2025-06-17 15:20:22.57284+00	50	2025-06-17 15:20:22.57284+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-13	08:30:00	08:00:00	0	1	2025-06-17 15:20:22.631271+00	51	2025-06-17 15:20:22.631271+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-03	19:00:00	18:30:00	0	1	2025-06-17 15:20:22.715183+00	52	2025-06-17 15:20:22.715183+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-06-26	11:00:00	10:30:00	0	1	2025-06-17 15:20:22.81821+00	54	2025-06-17 15:20:22.81821+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:22.868446+00	55	2025-06-17 15:20:22.868446+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-05	09:30:00	09:00:00	0	0	2025-06-17 15:20:22.897411+00	56	2025-06-17 15:20:22.897411+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-11	19:30:00	19:00:00	0	1	2025-06-17 15:20:22.912199+00	57	2025-06-17 15:20:22.912199+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-05	18:00:00	17:30:00	0	0	2025-06-17 15:20:22.985582+00	59	2025-06-17 15:20:22.985582+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-06	20:30:00	20:00:00	0	0	2025-06-17 15:20:23.019281+00	60	2025-06-17 15:20:23.019281+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-13	14:00:00	13:30:00	0	0	2025-06-17 15:20:23.110197+00	61	2025-06-17 15:20:23.110197+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-09	12:30:00	12:00:00	0	1	2025-06-17 15:20:45.607567+00	667	2025-06-17 15:20:45.607567+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-09	16:00:00	15:30:00	0	0	2025-06-17 15:20:23.12466+00	62	2025-06-17 15:20:23.12466+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-08	08:30:00	08:00:00	0	0	2025-06-17 15:20:54.715475+00	976	2025-06-17 15:20:54.715475+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Висипання на шкірі, діарея або запори, блювання.
2025-06-20	08:30:00	08:00:00	3	0	2025-06-17 15:20:22.346254+00	46	2025-06-17 15:20:22.346254+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-05	17:30:00	17:00:00	0	0	2025-06-17 15:20:23.204934+00	63	2025-06-17 15:20:23.204934+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-11	18:30:00	18:00:00	0	1	2025-06-17 15:20:23.249969+00	64	2025-06-17 15:20:23.249969+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-14	16:30:00	16:00:00	0	0	2025-06-17 15:20:23.287316+00	65	2025-06-17 15:20:23.287316+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-11	16:30:00	16:00:00	0	0	2025-06-17 15:20:23.301623+00	66	2025-06-17 15:20:23.301623+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у горлі, що не минає понад тиждень, труднощі при ковтанні.
2025-07-06	14:00:00	13:30:00	0	1	2025-06-17 15:20:23.334033+00	67	2025-06-17 15:20:23.334033+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-09	22:00:00	21:30:00	0	1	2025-06-17 15:20:23.391796+00	68	2025-06-17 15:20:23.391796+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-09	11:00:00	10:30:00	0	0	2025-06-17 15:20:23.521716+00	69	2025-06-17 15:20:23.521716+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-06	18:00:00	17:30:00	0	1	2025-06-17 15:20:23.636119+00	70	2025-06-17 15:20:23.636119+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-10	21:00:00	20:30:00	0	1	2025-06-17 15:20:23.682126+00	71	2025-06-17 15:20:23.682126+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-11	16:00:00	15:30:00	0	0	2025-06-17 15:20:23.730589+00	72	2025-06-17 15:20:23.730589+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-05	14:30:00	14:00:00	0	0	2025-06-17 15:20:23.835868+00	73	2025-06-17 15:20:23.835868+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-09	20:00:00	19:30:00	0	0	2025-06-17 15:20:23.859411+00	74	2025-06-17 15:20:23.859411+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-08	23:00:00	22:30:00	0	0	2025-06-17 15:20:23.88489+00	75	2025-06-17 15:20:23.88489+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-08	14:30:00	14:00:00	0	0	2025-06-17 15:20:23.900173+00	76	2025-06-17 15:20:23.900173+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-04	19:30:00	19:00:00	0	1	2025-06-17 15:20:23.916187+00	77	2025-06-17 15:20:23.916187+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-07	08:30:00	08:00:00	0	0	2025-06-17 15:20:23.971122+00	78	2025-06-17 15:20:23.971122+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-13	12:30:00	12:00:00	0	0	2025-06-17 15:20:24.007448+00	79	2025-06-17 15:20:24.007448+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-15	17:30:00	17:00:00	0	1	2025-06-17 15:20:24.043091+00	80	2025-06-17 15:20:24.043091+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-10	21:30:00	21:00:00	0	1	2025-06-17 15:20:24.272226+00	82	2025-06-17 15:20:24.272226+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-10	08:30:00	08:00:00	0	1	2025-06-17 15:20:24.30105+00	83	2025-06-17 15:20:24.30105+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-08	17:30:00	17:00:00	0	1	2025-06-17 15:20:24.33317+00	84	2025-06-17 15:20:24.33317+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-06	14:00:00	13:30:00	0	0	2025-06-17 15:20:24.387755+00	85	2025-06-17 15:20:24.387755+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-12	18:30:00	18:00:00	0	0	2025-06-17 15:20:24.414521+00	86	2025-06-17 15:20:24.414521+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-06	00:30:00	00:00:00	0	1	2025-06-17 15:20:24.464365+00	87	2025-06-17 15:20:24.464365+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-11	15:00:00	14:30:00	0	0	2025-06-17 15:20:24.496973+00	88	2025-06-17 15:20:24.496973+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-11	19:00:00	18:30:00	0	1	2025-06-17 15:20:24.601578+00	90	2025-06-17 15:20:24.601578+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-12	23:30:00	23:00:00	0	0	2025-06-17 15:20:24.620886+00	91	2025-06-17 15:20:24.620886+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-07-06	08:30:00	08:00:00	0	1	2025-06-17 15:20:45.628163+00	668	2025-06-17 15:20:45.628163+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-04	10:00:00	09:30:00	0	0	2025-06-17 15:20:24.682414+00	93	2025-06-17 15:20:24.682414+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-03	16:00:00	15:30:00	0	1	2025-06-17 15:20:24.712962+00	94	2025-06-17 15:20:24.712962+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:24.969344+00	95	2025-06-17 15:20:24.969344+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-06-24	08:30:00	08:00:00	0	0	2025-06-17 15:20:25.026212+00	96	2025-06-17 15:20:25.026212+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Синдром перевтоми: втрата витривалості, безсоння після тренувань.
2025-07-08	17:00:00	16:30:00	0	1	2025-06-17 15:20:25.129774+00	99	2025-06-17 15:20:25.129774+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:25.156914+00	100	2025-06-17 15:20:25.156914+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-06	11:30:00	11:00:00	0	1	2025-06-17 15:20:25.190342+00	101	2025-06-17 15:20:25.190342+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-10	10:00:00	09:30:00	0	0	2025-06-17 15:20:25.211954+00	102	2025-06-17 15:20:25.211954+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення стільця: запори, діарея, або їх чергування без явної причини.
2025-07-05	16:00:00	15:30:00	0	0	2025-06-17 15:20:25.234866+00	103	2025-06-17 15:20:25.234866+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-05	16:00:00	15:30:00	0	1	2025-06-17 15:20:25.257514+00	104	2025-06-17 15:20:25.257514+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-13	10:00:00	09:30:00	0	1	2025-06-17 15:20:25.38874+00	105	2025-06-17 15:20:25.38874+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-14	22:00:00	21:30:00	0	1	2025-06-17 15:20:25.406927+00	106	2025-06-17 15:20:25.406927+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-08	16:30:00	16:00:00	0	1	2025-06-17 15:20:25.448938+00	107	2025-06-17 15:20:25.448938+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-03	10:00:00	09:30:00	0	0	2025-06-17 15:20:25.495416+00	108	2025-06-17 15:20:25.495416+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-03	12:30:00	12:00:00	0	1	2025-06-17 15:20:25.641375+00	109	2025-06-17 15:20:25.641375+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Зміни настрою, дратівливість, безпричинне підвищення пітливості.
2025-07-14	13:30:00	13:00:00	0	0	2025-06-17 15:20:25.662969+00	110	2025-06-17 15:20:25.662969+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-08	21:00:00	20:30:00	0	0	2025-06-17 15:20:25.774909+00	112	2025-06-17 15:20:25.774909+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-04	22:00:00	21:30:00	0	0	2025-06-17 15:20:25.810022+00	113	2025-06-17 15:20:25.810022+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-04	21:00:00	20:30:00	0	1	2025-06-17 15:20:25.847691+00	114	2025-06-17 15:20:25.847691+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-03	14:00:00	13:30:00	0	1	2025-06-17 15:20:25.882743+00	115	2025-06-17 15:20:25.882743+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-06	08:30:00	08:00:00	0	0	2025-06-17 15:20:25.92927+00	116	2025-06-17 15:20:25.92927+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-14	14:00:00	13:30:00	0	0	2025-06-17 15:20:26.181262+00	119	2025-06-17 15:20:26.181262+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-05	17:30:00	17:00:00	0	1	2025-06-17 15:20:26.231241+00	120	2025-06-17 15:20:26.231241+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-13	18:00:00	17:30:00	0	0	2025-06-17 15:20:26.253051+00	121	2025-06-17 15:20:26.253051+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення стільця: запори, діарея, або їх чергування без явної причини.
2025-07-14	09:30:00	09:00:00	0	1	2025-06-17 15:20:53.58183+00	943	2025-06-17 15:20:53.58183+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-06-26	22:30:00	22:00:00	2	1	2025-06-21 04:00:02.957491+00	1652	2025-06-21 04:00:02.957491+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	assdfgh
2025-06-26	12:30:00	12:00:00	0	1	2025-06-21 21:19:25.06918+00	1703	2025-06-21 21:19:25.06918+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	kljhgf
2025-07-07	18:30:00	18:00:00	0	1	2025-06-17 15:20:26.27613+00	122	2025-06-17 15:20:26.27613+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-14	18:00:00	17:30:00	0	0	2025-06-17 15:20:26.3862+00	124	2025-06-17 15:20:26.3862+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-07	21:00:00	20:30:00	0	0	2025-06-17 15:20:26.487379+00	126	2025-06-17 15:20:26.487379+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-11	09:30:00	09:00:00	0	1	2025-06-17 15:20:26.505163+00	127	2025-06-17 15:20:26.505163+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-07	18:30:00	18:00:00	0	0	2025-06-17 15:20:26.587564+00	128	2025-06-17 15:20:26.587564+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-10	11:30:00	11:00:00	0	1	2025-06-17 15:20:26.61446+00	129	2025-06-17 15:20:26.61446+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-05	21:30:00	21:00:00	0	1	2025-06-17 15:20:26.732194+00	130	2025-06-17 15:20:26.732194+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-14	20:30:00	20:00:00	0	0	2025-06-17 15:20:26.747494+00	131	2025-06-17 15:20:26.747494+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-13	08:30:00	08:00:00	0	0	2025-06-17 15:20:26.780513+00	132	2025-06-17 15:20:26.780513+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-10	13:30:00	13:00:00	0	1	2025-06-17 15:20:26.851752+00	133	2025-06-17 15:20:26.851752+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Зміни настрою, дратівливість, безпричинне підвищення пітливості.
2025-07-05	14:00:00	13:30:00	0	1	2025-06-17 15:20:26.934557+00	134	2025-06-17 15:20:26.934557+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-10	08:30:00	08:00:00	0	0	2025-06-17 15:20:26.960213+00	135	2025-06-17 15:20:26.960213+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-14	14:00:00	13:30:00	0	1	2025-06-17 15:20:27.079404+00	136	2025-06-17 15:20:27.079404+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-08	18:30:00	18:00:00	0	0	2025-06-17 15:20:27.130901+00	138	2025-06-17 15:20:27.130901+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-09	19:30:00	19:00:00	0	1	2025-06-17 15:20:27.187666+00	140	2025-06-17 15:20:27.187666+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-13	08:30:00	08:00:00	0	0	2025-06-17 15:20:27.25591+00	142	2025-06-17 15:20:27.25591+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-13	08:30:00	08:00:00	0	0	2025-06-17 15:20:27.282101+00	143	2025-06-17 15:20:27.282101+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-10	19:00:00	18:30:00	0	1	2025-06-17 15:20:27.309255+00	144	2025-06-17 15:20:27.309255+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-13	09:30:00	09:00:00	0	1	2025-06-17 15:20:27.326255+00	145	2025-06-17 15:20:27.326255+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff63-73de-9441-b73d75415356	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-12	22:00:00	21:30:00	0	0	2025-06-17 15:20:27.37825+00	147	2025-06-17 15:20:27.37825+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Обмеження рухливості, скутість м’язів, загальна слабкість.
2025-07-09	20:30:00	20:00:00	0	0	2025-06-17 15:20:27.410396+00	148	2025-06-17 15:20:27.410396+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	22:30:00	22:00:00	0	1	2025-06-17 15:20:27.544951+00	149	2025-06-17 15:20:27.544951+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-06-24	08:30:00	08:00:00	0	0	2025-06-17 15:20:27.600234+00	150	2025-06-17 15:20:27.600234+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-15	17:00:00	16:30:00	0	0	2025-06-17 15:20:27.675056+00	151	2025-06-17 15:20:27.675056+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-03	18:30:00	18:00:00	0	1	2025-06-17 15:20:27.811419+00	153	2025-06-17 15:20:27.811419+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-09	14:30:00	14:00:00	0	0	2025-06-17 15:20:27.852553+00	154	2025-06-17 15:20:27.852553+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-ff63-73de-9441-b73d75415356	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-06	10:30:00	10:00:00	0	1	2025-06-17 15:20:27.874242+00	155	2025-06-17 15:20:27.874242+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-09	22:30:00	22:00:00	0	1	2025-06-17 15:20:27.930309+00	156	2025-06-17 15:20:27.930309+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:27.954191+00	157	2025-06-17 15:20:27.954191+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-09	16:30:00	16:00:00	0	1	2025-06-17 15:20:28.029983+00	158	2025-06-17 15:20:28.029983+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-06	22:30:00	22:00:00	0	1	2025-06-17 15:20:28.061532+00	159	2025-06-17 15:20:28.061532+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-05	15:30:00	15:00:00	0	1	2025-06-17 15:20:28.083723+00	160	2025-06-17 15:20:28.083723+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-12	16:00:00	15:30:00	0	0	2025-06-17 15:20:28.106407+00	161	2025-06-17 15:20:28.106407+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Порушення сну, часті пробудження, відчуття тривоги та нервозності.
2025-07-04	20:00:00	19:30:00	0	1	2025-06-17 15:20:28.191279+00	162	2025-06-17 15:20:28.191279+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-08	15:30:00	15:00:00	0	0	2025-06-17 15:20:28.26628+00	163	2025-06-17 15:20:28.26628+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-08	15:00:00	14:30:00	0	0	2025-06-17 15:20:28.314344+00	164	2025-06-17 15:20:28.314344+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-05	11:30:00	11:00:00	0	1	2025-06-17 15:20:28.406651+00	165	2025-06-17 15:20:28.406651+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-11	08:30:00	08:00:00	0	1	2025-06-17 15:20:28.510737+00	167	2025-06-17 15:20:28.510737+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-15	14:00:00	13:30:00	0	0	2025-06-17 15:20:28.528289+00	168	2025-06-17 15:20:28.528289+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-05	16:00:00	15:30:00	0	1	2025-06-17 15:20:28.546585+00	169	2025-06-17 15:20:28.546585+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-04	08:30:00	08:00:00	0	0	2025-06-17 15:20:28.562864+00	170	2025-06-17 15:20:28.562864+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-13	08:30:00	08:00:00	0	0	2025-06-17 15:20:28.57779+00	171	2025-06-17 15:20:28.57779+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-07	16:30:00	16:00:00	0	1	2025-06-17 15:20:28.5932+00	172	2025-06-17 15:20:28.5932+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-06	13:30:00	13:00:00	0	0	2025-06-17 15:20:28.614732+00	173	2025-06-17 15:20:28.614732+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-15	10:00:00	09:30:00	0	1	2025-06-17 15:20:28.637238+00	174	2025-06-17 15:20:28.637238+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-08	09:30:00	09:00:00	0	1	2025-06-17 15:20:28.661168+00	175	2025-06-17 15:20:28.661168+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-11	23:00:00	22:30:00	0	0	2025-06-17 15:20:28.704226+00	176	2025-06-17 15:20:28.704226+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-10	14:30:00	14:00:00	0	0	2025-06-17 15:20:28.744211+00	177	2025-06-17 15:20:28.744211+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-03	16:30:00	16:00:00	0	0	2025-06-17 15:20:28.761484+00	178	2025-06-17 15:20:28.761484+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-06	20:00:00	19:30:00	0	1	2025-06-17 15:20:28.798792+00	179	2025-06-17 15:20:28.798792+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-10	11:00:00	10:30:00	0	0	2025-06-17 15:20:28.889813+00	180	2025-06-17 15:20:28.889813+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-08	11:00:00	10:30:00	0	0	2025-06-17 15:20:28.908662+00	181	2025-06-17 15:20:28.908662+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-10	18:30:00	18:00:00	0	0	2025-06-17 15:20:28.979691+00	183	2025-06-17 15:20:28.979691+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-05	18:00:00	17:30:00	0	1	2025-06-17 15:20:29.090884+00	184	2025-06-17 15:20:29.090884+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-15	21:00:00	20:30:00	0	1	2025-06-17 15:20:29.141672+00	185	2025-06-17 15:20:29.141672+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-07-04	13:00:00	12:30:00	0	0	2025-06-17 15:20:29.174824+00	186	2025-06-17 15:20:29.174824+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-04	20:00:00	19:30:00	0	1	2025-06-17 15:20:29.195644+00	187	2025-06-17 15:20:29.195644+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Синдром перевтоми: втрата витривалості, безсоння після тренувань.
2025-07-07	09:30:00	09:00:00	0	1	2025-06-17 15:20:29.305517+00	188	2025-06-17 15:20:29.305517+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-05	13:30:00	13:00:00	0	0	2025-06-17 15:20:29.327271+00	189	2025-06-17 15:20:29.327271+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-13	11:30:00	11:00:00	0	0	2025-06-17 15:20:29.471323+00	190	2025-06-17 15:20:29.471323+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-11	09:30:00	09:00:00	0	1	2025-06-17 15:20:29.653228+00	192	2025-06-17 15:20:29.653228+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-11	15:30:00	15:00:00	0	1	2025-06-17 15:20:29.671218+00	193	2025-06-17 15:20:29.671218+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-03	10:30:00	10:00:00	0	0	2025-06-17 15:20:29.704248+00	194	2025-06-17 15:20:29.704248+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-13	21:00:00	20:30:00	0	1	2025-06-17 15:20:29.729289+00	195	2025-06-17 15:20:29.729289+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-13	19:30:00	19:00:00	0	0	2025-06-17 15:20:29.908828+00	198	2025-06-17 15:20:29.908828+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-06	19:00:00	18:30:00	0	1	2025-06-17 15:20:29.93455+00	199	2025-06-17 15:20:29.93455+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-11	16:30:00	16:00:00	0	1	2025-06-17 15:20:29.959465+00	200	2025-06-17 15:20:29.959465+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-04	12:00:00	11:30:00	0	0	2025-06-17 15:20:29.994042+00	201	2025-06-17 15:20:29.994042+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-11	19:00:00	18:30:00	0	1	2025-06-17 15:20:30.010884+00	202	2025-06-17 15:20:30.010884+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-09	10:00:00	09:30:00	0	0	2025-06-17 15:20:30.085131+00	204	2025-06-17 15:20:30.085131+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-15	13:30:00	13:00:00	0	0	2025-06-17 15:20:30.121779+00	205	2025-06-17 15:20:30.121779+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-15	18:30:00	18:00:00	0	1	2025-06-17 15:20:30.147757+00	206	2025-06-17 15:20:30.147757+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-15	15:00:00	14:30:00	0	1	2025-06-17 15:20:30.220585+00	207	2025-06-17 15:20:30.220585+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-06	09:30:00	09:00:00	0	0	2025-06-17 15:20:30.247812+00	208	2025-06-17 15:20:30.247812+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-14	08:30:00	08:00:00	0	1	2025-06-17 15:20:30.348682+00	210	2025-06-17 15:20:30.348682+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-06	12:30:00	12:00:00	0	0	2025-06-17 15:20:30.373752+00	211	2025-06-17 15:20:30.373752+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-06-26	12:30:00	12:00:00	0	1	2025-06-17 15:20:53.610179+00	944	2025-06-17 15:20:53.610179+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-06-20	00:30:00	00:00:00	3	1	2025-06-17 15:20:28.957253+00	182	2025-06-17 15:20:28.957253+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-13	16:00:00	15:30:00	0	1	2025-06-17 15:20:30.454202+00	212	2025-06-17 15:20:30.454202+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-04	12:30:00	12:00:00	0	0	2025-06-17 15:20:30.518444+00	213	2025-06-17 15:20:30.518444+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-06	17:00:00	16:30:00	0	0	2025-06-17 15:20:30.575394+00	214	2025-06-17 15:20:30.575394+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:20:30.590712+00	215	2025-06-17 15:20:30.590712+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-15	20:00:00	19:30:00	0	0	2025-06-17 15:20:30.713976+00	217	2025-06-17 15:20:30.713976+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-09	13:30:00	13:00:00	0	0	2025-06-17 15:20:30.770757+00	218	2025-06-17 15:20:30.770757+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у суглобах, особливо вранці, що зменшується при русі.
2025-07-13	08:30:00	08:00:00	0	1	2025-06-17 15:20:30.80487+00	219	2025-06-17 15:20:30.80487+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-15	09:30:00	09:00:00	0	0	2025-06-17 15:20:30.831802+00	220	2025-06-17 15:20:30.831802+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-13	13:30:00	13:00:00	0	0	2025-06-17 15:20:30.876571+00	221	2025-06-17 15:20:30.876571+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-07	08:30:00	08:00:00	0	0	2025-06-17 15:20:30.944486+00	223	2025-06-17 15:20:30.944486+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-07	12:30:00	12:00:00	0	1	2025-06-17 15:20:30.963283+00	224	2025-06-17 15:20:30.963283+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-03	17:00:00	16:30:00	0	0	2025-06-17 15:20:30.979687+00	225	2025-06-17 15:20:30.979687+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-14	08:30:00	08:00:00	0	0	2025-06-17 15:20:31.026794+00	226	2025-06-17 15:20:31.026794+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-05	11:00:00	10:30:00	0	1	2025-06-17 15:20:31.042145+00	227	2025-06-17 15:20:31.042145+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-09	14:00:00	13:30:00	0	1	2025-06-17 15:20:31.160974+00	230	2025-06-17 15:20:31.160974+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:20:31.200834+00	231	2025-06-17 15:20:31.200834+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-08	16:00:00	15:30:00	0	1	2025-06-17 15:20:31.221908+00	232	2025-06-17 15:20:31.221908+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-05	09:30:00	09:00:00	0	1	2025-06-17 15:20:31.234851+00	233	2025-06-17 15:20:31.234851+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-12	08:30:00	08:00:00	0	0	2025-06-17 15:20:31.254628+00	234	2025-06-17 15:20:31.254628+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-08	12:30:00	12:00:00	0	0	2025-06-17 15:20:31.401256+00	236	2025-06-17 15:20:31.401256+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-13	14:30:00	14:00:00	0	1	2025-06-17 15:20:31.437678+00	237	2025-06-17 15:20:31.437678+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-15	08:30:00	08:00:00	0	1	2025-06-17 15:20:31.503534+00	238	2025-06-17 15:20:31.503534+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-13	17:00:00	16:30:00	0	0	2025-06-17 15:20:31.522195+00	239	2025-06-17 15:20:31.522195+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-03	15:00:00	14:30:00	0	0	2025-06-17 15:20:31.551401+00	240	2025-06-17 15:20:31.551401+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-13	18:30:00	18:00:00	0	1	2025-06-17 15:20:31.576652+00	241	2025-06-17 15:20:31.576652+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-03	13:00:00	12:30:00	2	0	2025-06-17 15:20:30.903786+00	222	2025-06-17 15:20:30.903786+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-04	16:30:00	16:00:00	0	1	2025-06-17 15:20:31.609071+00	242	2025-06-17 15:20:31.609071+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:31.669194+00	243	2025-06-17 15:20:31.669194+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-04	17:00:00	16:30:00	0	1	2025-06-17 15:20:31.687957+00	244	2025-06-17 15:20:31.687957+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-11	19:00:00	18:30:00	0	1	2025-06-17 15:20:31.706446+00	245	2025-06-17 15:20:31.706446+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f603-7282-bc16-c98712d0e44d	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-03	12:30:00	12:00:00	0	0	2025-06-17 15:20:31.822557+00	248	2025-06-17 15:20:31.822557+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-09	01:00:00	00:30:00	0	1	2025-06-17 15:20:31.837693+00	249	2025-06-17 15:20:31.837693+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Порушення сну, часті пробудження, відчуття тривоги та нервозності.
2025-07-04	09:30:00	09:00:00	0	1	2025-06-17 15:20:31.852022+00	250	2025-06-17 15:20:31.852022+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-10	08:30:00	08:00:00	0	0	2025-06-17 15:20:31.923129+00	251	2025-06-17 15:20:31.923129+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-12	08:30:00	08:00:00	0	0	2025-06-17 15:20:32.052918+00	253	2025-06-17 15:20:32.052918+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-07	08:30:00	08:00:00	0	1	2025-06-17 15:20:32.092416+00	254	2025-06-17 15:20:32.092416+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-03	12:30:00	12:00:00	0	0	2025-06-17 15:20:32.111338+00	255	2025-06-17 15:20:32.111338+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-04	11:00:00	10:30:00	0	0	2025-06-17 15:20:32.150601+00	256	2025-06-17 15:20:32.150601+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Травми зв’язок, розтягнення, що не минають протягом кількох тижнів.
2025-07-03	11:00:00	10:30:00	0	0	2025-06-17 15:20:32.176006+00	257	2025-06-17 15:20:32.176006+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Потреба в індивідуальному підборі навантажень після хвороби або травми.
2025-07-07	13:00:00	12:30:00	0	1	2025-06-17 15:20:32.206206+00	258	2025-06-17 15:20:32.206206+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-15	11:00:00	10:30:00	0	1	2025-06-17 15:20:32.253542+00	259	2025-06-17 15:20:32.253542+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-13	11:30:00	11:00:00	0	1	2025-06-17 15:20:32.2976+00	260	2025-06-17 15:20:32.2976+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-14	08:30:00	08:00:00	0	1	2025-06-17 15:20:32.323403+00	261	2025-06-17 15:20:32.323403+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-04	18:30:00	18:00:00	0	0	2025-06-17 15:20:32.382153+00	262	2025-06-17 15:20:32.382153+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-08	18:30:00	18:00:00	0	0	2025-06-17 15:20:32.398454+00	263	2025-06-17 15:20:32.398454+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-08	09:30:00	09:00:00	0	1	2025-06-17 15:20:32.413013+00	264	2025-06-17 15:20:32.413013+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:32.433535+00	265	2025-06-17 15:20:32.433535+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-10	08:30:00	08:00:00	0	0	2025-06-17 15:20:32.47268+00	266	2025-06-17 15:20:32.47268+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-04	11:00:00	10:30:00	0	0	2025-06-17 15:20:32.490106+00	267	2025-06-17 15:20:32.490106+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-07	16:00:00	15:30:00	0	1	2025-06-17 15:20:32.523818+00	268	2025-06-17 15:20:32.523818+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-03	11:30:00	11:00:00	0	0	2025-06-17 15:20:32.592685+00	270	2025-06-17 15:20:32.592685+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-13	10:00:00	09:30:00	0	0	2025-06-17 15:20:32.62464+00	271	2025-06-17 15:20:32.62464+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-05	22:30:00	22:00:00	0	1	2025-06-17 15:20:32.639801+00	272	2025-06-17 15:20:32.639801+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-11	21:30:00	21:00:00	0	0	2025-06-17 15:20:32.741014+00	274	2025-06-17 15:20:32.741014+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-11	14:00:00	13:30:00	0	0	2025-06-17 15:20:32.90986+00	277	2025-06-17 15:20:32.90986+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-09	20:30:00	20:00:00	0	0	2025-06-17 15:20:33.001937+00	280	2025-06-17 15:20:33.001937+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-06	17:30:00	17:00:00	0	1	2025-06-17 15:20:33.04108+00	281	2025-06-17 15:20:33.04108+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-15	19:00:00	18:30:00	0	0	2025-06-17 15:20:33.058388+00	282	2025-06-17 15:20:33.058388+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-03	13:00:00	12:30:00	0	0	2025-06-17 15:20:33.261334+00	285	2025-06-17 15:20:33.261334+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-ff63-73de-9441-b73d75415356	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-15	15:30:00	15:00:00	0	1	2025-06-17 15:20:33.314138+00	286	2025-06-17 15:20:33.314138+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-15	08:30:00	08:00:00	0	1	2025-06-17 15:20:33.420496+00	288	2025-06-17 15:20:33.420496+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Потреба в індивідуальному підборі навантажень після хвороби або травми.
2025-07-08	12:30:00	12:00:00	0	0	2025-06-17 15:20:33.439219+00	289	2025-06-17 15:20:33.439219+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Тривожність, депресія, часті зміни настрою.
2025-07-04	08:30:00	08:00:00	0	0	2025-06-17 15:20:33.454462+00	290	2025-06-17 15:20:33.454462+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-08	15:00:00	14:30:00	0	0	2025-06-17 15:20:33.498539+00	291	2025-06-17 15:20:33.498539+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-13	16:30:00	16:00:00	0	0	2025-06-17 15:20:33.524635+00	292	2025-06-17 15:20:33.524635+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-05	21:00:00	20:30:00	0	1	2025-06-17 15:20:33.560524+00	293	2025-06-17 15:20:33.560524+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-11	14:30:00	14:00:00	0	1	2025-06-17 15:20:33.6228+00	295	2025-06-17 15:20:33.6228+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-09	13:30:00	13:00:00	0	0	2025-06-17 15:20:33.671076+00	297	2025-06-17 15:20:33.671076+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-05	13:30:00	13:00:00	0	1	2025-06-17 15:20:33.686166+00	298	2025-06-17 15:20:33.686166+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-09	13:30:00	13:00:00	0	1	2025-06-17 15:20:33.702992+00	299	2025-06-17 15:20:33.702992+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-04	20:30:00	20:00:00	0	0	2025-06-17 15:20:33.735134+00	300	2025-06-17 15:20:33.735134+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-12	21:00:00	20:30:00	0	1	2025-06-17 15:20:33.768446+00	301	2025-06-17 15:20:33.768446+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-14	14:30:00	14:00:00	0	1	2025-06-17 15:20:40.805216+00	512	2025-06-17 15:20:40.805216+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f469-7573-ba3a-604cd272778f	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-04	16:00:00	15:30:00	2	0	2025-06-17 15:20:33.823359+00	302	2025-06-17 15:20:33.823359+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-05	11:30:00	11:00:00	1	0	2025-06-17 15:20:32.766277+00	275	2025-06-17 15:20:32.766277+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	{"reason":"rhenlfpdgkojnid"}	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-06-19	08:30:00	08:00:00	3	0	2025-06-17 15:20:32.671833+00	273	2025-06-17 15:20:32.671833+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-03	15:00:00	14:30:00	0	0	2025-06-17 15:20:33.845022+00	303	2025-06-17 15:20:33.845022+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-07	15:30:00	15:00:00	0	0	2025-06-17 15:20:33.873077+00	304	2025-06-17 15:20:33.873077+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-10	18:00:00	17:30:00	0	1	2025-06-17 15:20:33.90215+00	305	2025-06-17 15:20:33.90215+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Постійна слабкість, сонливість, порушення терморегуляції тіла.
2025-07-11	10:00:00	09:30:00	0	0	2025-06-17 15:20:33.943699+00	306	2025-06-17 15:20:33.943699+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-04	12:30:00	12:00:00	0	1	2025-06-17 15:20:33.961561+00	307	2025-06-17 15:20:33.961561+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-14	20:30:00	20:00:00	0	1	2025-06-17 15:20:33.979214+00	308	2025-06-17 15:20:33.979214+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-11	11:30:00	11:00:00	0	0	2025-06-17 15:20:33.996427+00	309	2025-06-17 15:20:33.996427+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-04	11:00:00	10:30:00	0	1	2025-06-17 15:20:34.018792+00	310	2025-06-17 15:20:34.018792+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-14	19:00:00	18:30:00	0	0	2025-06-17 15:20:34.033805+00	311	2025-06-17 15:20:34.033805+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-05	11:30:00	11:00:00	0	0	2025-06-17 15:20:34.078887+00	312	2025-06-17 15:20:34.078887+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f469-7573-ba3a-604cd272778f	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-11	21:00:00	20:30:00	0	1	2025-06-17 15:20:34.093434+00	313	2025-06-17 15:20:34.093434+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-13	10:30:00	10:00:00	0	0	2025-06-17 15:20:34.141626+00	314	2025-06-17 15:20:34.141626+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-10	18:30:00	18:00:00	0	0	2025-06-17 15:20:34.218235+00	316	2025-06-17 15:20:34.218235+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-06	15:30:00	15:00:00	0	1	2025-06-17 15:20:34.240975+00	317	2025-06-17 15:20:34.240975+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-12	19:30:00	19:00:00	0	0	2025-06-17 15:20:34.313541+00	319	2025-06-17 15:20:34.313541+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-11	16:30:00	16:00:00	0	0	2025-06-17 15:20:34.367975+00	320	2025-06-17 15:20:34.367975+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-04	10:30:00	10:00:00	0	0	2025-06-17 15:20:34.430273+00	322	2025-06-17 15:20:34.430273+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-12	11:00:00	10:30:00	0	1	2025-06-17 15:20:34.456342+00	323	2025-06-17 15:20:34.456342+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-15	18:30:00	18:00:00	0	0	2025-06-17 15:20:34.478657+00	324	2025-06-17 15:20:34.478657+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення стільця: запори, діарея, або їх чергування без явної причини.
2025-07-11	19:00:00	18:30:00	0	0	2025-06-17 15:20:34.559734+00	325	2025-06-17 15:20:34.559734+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-08	00:30:00	00:00:00	0	1	2025-06-17 15:20:34.579115+00	326	2025-06-17 15:20:34.579115+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-10	20:00:00	19:30:00	0	0	2025-06-17 15:20:34.628331+00	328	2025-06-17 15:20:34.628331+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-08	20:00:00	19:30:00	0	0	2025-06-17 15:20:34.651478+00	329	2025-06-17 15:20:34.651478+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-07	18:00:00	17:30:00	0	0	2025-06-17 15:20:34.688879+00	330	2025-06-17 15:20:34.688879+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-07	11:00:00	10:30:00	0	0	2025-06-17 15:20:34.704965+00	331	2025-06-17 15:20:34.704965+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-03	20:30:00	20:00:00	0	1	2025-06-17 15:20:34.763541+00	332	2025-06-17 15:20:34.763541+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-25	14:00:00	13:15:00	0	1	2025-07-07 09:34:15.275244+00	2252	2025-07-07 11:59:40.966461+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fc3d-7e26-94be-b6531796eb62	\N	тделалі
2025-07-09	12:30:00	12:00:00	0	0	2025-06-17 15:20:34.822547+00	333	2025-06-17 15:20:34.822547+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-04	22:00:00	21:30:00	0	0	2025-06-17 15:20:34.865567+00	334	2025-06-17 15:20:34.865567+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-08	13:30:00	13:00:00	0	0	2025-06-17 15:20:34.916085+00	336	2025-06-17 15:20:34.916085+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-10	16:00:00	15:30:00	0	0	2025-06-17 15:20:34.933373+00	337	2025-06-17 15:20:34.933373+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-09	18:30:00	18:00:00	0	1	2025-06-17 15:20:34.951407+00	338	2025-06-17 15:20:34.951407+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Обмеження рухливості, скутість м’язів, загальна слабкість.
2025-07-07	13:30:00	13:00:00	0	1	2025-06-17 15:20:35.011442+00	339	2025-06-17 15:20:35.011442+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-11	20:00:00	19:30:00	0	0	2025-06-17 15:20:35.028768+00	340	2025-06-17 15:20:35.028768+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-15	20:00:00	19:30:00	0	0	2025-06-17 15:20:35.045368+00	341	2025-06-17 15:20:35.045368+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-07	19:00:00	18:30:00	0	0	2025-06-17 15:20:35.082654+00	342	2025-06-17 15:20:35.082654+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-14	17:00:00	16:30:00	0	1	2025-06-17 15:20:35.104587+00	343	2025-06-17 15:20:35.104587+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f603-7282-bc16-c98712d0e44d	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-05	10:30:00	10:00:00	0	0	2025-06-17 15:20:35.136311+00	344	2025-06-17 15:20:35.136311+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-06-26	08:30:00	08:00:00	0	1	2025-06-17 15:20:35.166711+00	345	2025-06-17 15:20:35.166711+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-09	16:30:00	16:00:00	0	0	2025-06-17 15:20:35.210539+00	346	2025-06-17 15:20:35.210539+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-04	08:30:00	08:00:00	0	1	2025-06-17 15:20:35.239948+00	347	2025-06-17 15:20:35.239948+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-09	21:00:00	20:30:00	0	0	2025-06-17 15:20:35.278571+00	348	2025-06-17 15:20:35.278571+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-0026-79ac-a236-50d54f042a62	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-04	11:30:00	11:00:00	0	1	2025-06-17 15:20:35.336212+00	350	2025-06-17 15:20:35.336212+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-07	12:30:00	12:00:00	0	0	2025-06-17 15:20:35.368688+00	351	2025-06-17 15:20:35.368688+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-08	11:00:00	10:30:00	0	1	2025-06-17 15:20:35.386706+00	352	2025-06-17 15:20:35.386706+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-09	17:30:00	17:00:00	0	1	2025-06-17 15:20:35.404292+00	353	2025-06-17 15:20:35.404292+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-12	18:30:00	18:00:00	0	1	2025-06-17 15:20:35.451085+00	354	2025-06-17 15:20:35.451085+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-14	17:30:00	17:00:00	0	0	2025-06-17 15:20:35.48779+00	355	2025-06-17 15:20:35.48779+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-05	20:00:00	19:30:00	0	0	2025-06-17 15:20:35.503336+00	356	2025-06-17 15:20:35.503336+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-04	13:30:00	13:00:00	0	0	2025-06-17 15:20:35.520075+00	357	2025-06-17 15:20:35.520075+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-07	11:00:00	10:30:00	0	0	2025-06-17 15:20:35.54646+00	358	2025-06-17 15:20:35.54646+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-06	17:30:00	17:00:00	0	1	2025-06-17 15:20:35.637296+00	360	2025-06-17 15:20:35.637296+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-14	13:30:00	13:00:00	0	1	2025-06-17 15:20:35.652371+00	361	2025-06-17 15:20:35.652371+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-06	21:00:00	20:30:00	0	1	2025-06-17 15:20:35.705417+00	362	2025-06-17 15:20:35.705417+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-12	22:00:00	21:30:00	0	0	2025-06-17 15:20:40.818242+00	513	2025-06-17 15:20:40.818242+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-11	10:00:00	09:30:00	0	0	2025-06-17 15:20:35.753239+00	363	2025-06-17 15:20:35.753239+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-12	10:30:00	10:00:00	0	1	2025-06-17 15:20:35.778594+00	364	2025-06-17 15:20:35.778594+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-13	15:30:00	15:00:00	0	1	2025-06-17 15:20:35.820266+00	365	2025-06-17 15:20:35.820266+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-10	10:00:00	09:30:00	0	0	2025-06-17 15:20:35.864652+00	366	2025-06-17 15:20:35.864652+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-07	11:30:00	11:00:00	0	0	2025-06-17 15:20:36.020075+00	368	2025-06-17 15:20:36.020075+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-08	11:30:00	11:00:00	0	1	2025-06-17 15:20:36.037175+00	369	2025-06-17 15:20:36.037175+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Зниження концентрації уваги, забудькуватість, проблеми з пам’яттю.
2025-07-05	14:00:00	13:30:00	0	0	2025-06-17 15:20:36.053705+00	370	2025-06-17 15:20:36.053705+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Соціальна ізоляція, втрата інтересу до звичних справ.
2025-07-15	18:30:00	18:00:00	0	0	2025-06-17 15:20:36.080876+00	371	2025-06-17 15:20:36.080876+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-13	13:00:00	12:30:00	0	1	2025-06-17 15:20:36.125047+00	372	2025-06-17 15:20:36.125047+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-06	14:00:00	13:30:00	0	0	2025-06-17 15:20:36.163215+00	373	2025-06-17 15:20:36.163215+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-09	12:30:00	12:00:00	0	0	2025-06-17 15:20:36.189248+00	374	2025-06-17 15:20:36.189248+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-12	20:00:00	19:30:00	0	0	2025-06-17 15:20:36.222848+00	375	2025-06-17 15:20:36.222848+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-13	13:00:00	12:30:00	0	1	2025-06-17 15:20:36.272279+00	376	2025-06-17 15:20:36.272279+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-04	14:00:00	13:30:00	0	0	2025-06-17 15:20:36.319755+00	377	2025-06-17 15:20:36.319755+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-13	12:00:00	11:30:00	0	0	2025-06-17 15:20:36.411364+00	379	2025-06-17 15:20:36.411364+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-12	14:30:00	14:00:00	0	1	2025-06-17 15:20:36.43827+00	380	2025-06-17 15:20:36.43827+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-13	11:00:00	10:30:00	0	1	2025-06-17 15:20:36.454541+00	381	2025-06-17 15:20:36.454541+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f603-7282-bc16-c98712d0e44d	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-07	15:00:00	14:30:00	0	0	2025-06-17 15:20:36.469119+00	382	2025-06-17 15:20:36.469119+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Підвищена температура тіла без виявленої інфекції або запального процесу.
2025-07-03	14:30:00	14:00:00	0	0	2025-06-17 15:20:36.495928+00	383	2025-06-17 15:20:36.495928+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-14	15:00:00	14:30:00	0	1	2025-06-17 15:20:36.509943+00	384	2025-06-17 15:20:36.509943+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-13	10:30:00	10:00:00	0	1	2025-06-17 15:20:36.596473+00	386	2025-06-17 15:20:36.596473+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у горлі, що не минає понад тиждень, труднощі при ковтанні.
2025-07-06	15:30:00	15:00:00	0	1	2025-06-17 15:20:36.613431+00	387	2025-06-17 15:20:36.613431+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-10	17:00:00	16:30:00	0	0	2025-06-17 15:20:36.640248+00	388	2025-06-17 15:20:36.640248+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Хронічний біль у суглобах після спортивних тренувань.
2025-07-03	10:00:00	09:30:00	0	1	2025-06-17 15:20:36.673809+00	389	2025-06-17 15:20:36.673809+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-06-24	09:30:00	09:00:00	0	1	2025-06-17 15:20:36.698799+00	390	2025-06-17 15:20:36.698799+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-03	18:30:00	18:00:00	0	0	2025-06-17 15:20:36.720825+00	391	2025-06-17 15:20:36.720825+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-03	18:30:00	18:00:00	0	1	2025-06-17 15:20:36.743643+00	392	2025-06-17 15:20:36.743643+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ff63-73de-9441-b73d75415356	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-04	10:30:00	10:00:00	0	0	2025-06-17 15:20:36.8217+00	394	2025-06-17 15:20:36.8217+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-07	18:00:00	17:30:00	0	0	2025-06-17 15:20:36.846924+00	395	2025-06-17 15:20:36.846924+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-0026-79ac-a236-50d54f042a62	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-05	19:00:00	18:30:00	0	0	2025-06-17 15:20:36.917433+00	396	2025-06-17 15:20:36.917433+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-03	17:30:00	17:00:00	0	1	2025-06-17 15:20:36.941914+00	397	2025-06-17 15:20:36.941914+00	01977e79-f1a4-7811-840e-404568a4376e	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-13	21:00:00	20:30:00	0	1	2025-06-17 15:20:36.994627+00	398	2025-06-17 15:20:36.994627+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-12	13:30:00	13:00:00	0	0	2025-06-17 15:20:37.026569+00	399	2025-06-17 15:20:37.026569+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Зниження концентрації уваги, забудькуватість, проблеми з пам’яттю.
2025-07-11	18:30:00	18:00:00	0	0	2025-06-17 15:20:37.152583+00	400	2025-06-17 15:20:37.152583+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-11	08:30:00	08:00:00	0	0	2025-06-17 15:20:37.194274+00	401	2025-06-17 15:20:37.194274+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-03	10:30:00	10:00:00	0	0	2025-06-17 15:20:37.245368+00	402	2025-06-17 15:20:37.245368+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-08	18:30:00	18:00:00	0	1	2025-06-17 15:20:37.30272+00	403	2025-06-17 15:20:37.30272+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-11	17:30:00	17:00:00	0	0	2025-06-17 15:20:37.37285+00	404	2025-06-17 15:20:37.37285+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-07	18:30:00	18:00:00	0	0	2025-06-17 15:20:37.436732+00	405	2025-06-17 15:20:37.436732+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-13	13:00:00	12:30:00	0	0	2025-06-17 15:20:37.461506+00	406	2025-06-17 15:20:37.461506+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:37.488037+00	407	2025-06-17 15:20:37.488037+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-06	19:30:00	19:00:00	0	0	2025-06-17 15:20:37.509287+00	408	2025-06-17 15:20:37.509287+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-09	15:30:00	15:00:00	0	0	2025-06-17 15:20:37.612344+00	410	2025-06-17 15:20:37.612344+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-10	08:30:00	08:00:00	0	1	2025-06-17 15:20:37.690766+00	411	2025-06-17 15:20:37.690766+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Проблеми з обміном речовин, часті головні болі і запаморочення.
2025-07-13	14:30:00	14:00:00	0	1	2025-06-17 15:20:37.768977+00	412	2025-06-17 15:20:37.768977+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-14	15:00:00	14:30:00	0	0	2025-06-17 15:20:37.787077+00	413	2025-06-17 15:20:37.787077+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-08	21:00:00	20:30:00	0	1	2025-06-17 15:20:37.821969+00	414	2025-06-17 15:20:37.821969+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-15	12:30:00	12:00:00	0	0	2025-06-17 15:20:37.840313+00	415	2025-06-17 15:20:37.840313+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-03	21:00:00	20:30:00	0	0	2025-06-17 15:20:37.858032+00	416	2025-06-17 15:20:37.858032+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-07	19:30:00	19:00:00	0	0	2025-06-17 15:20:37.95789+00	419	2025-06-17 15:20:37.95789+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-04	09:30:00	09:00:00	0	0	2025-06-17 15:20:38.005483+00	421	2025-06-17 15:20:38.005483+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-13	13:30:00	13:00:00	0	0	2025-06-17 15:20:38.056427+00	422	2025-06-17 15:20:38.056427+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-13	12:30:00	12:00:00	0	0	2025-06-17 15:20:38.079755+00	423	2025-06-17 15:20:38.079755+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-05	16:30:00	16:00:00	0	1	2025-06-17 15:20:38.096918+00	424	2025-06-17 15:20:38.096918+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-05	14:00:00	13:30:00	0	1	2025-06-17 15:20:38.148828+00	425	2025-06-17 15:20:38.148828+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-08	19:00:00	18:30:00	0	1	2025-06-17 15:20:38.205793+00	426	2025-06-17 15:20:38.205793+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Загальна слабкість, підвищена втомлюваність навіть після невеликих навантажень.
2025-07-12	11:00:00	10:30:00	0	0	2025-06-17 15:20:38.232936+00	427	2025-06-17 15:20:38.232936+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-03	14:00:00	13:30:00	0	1	2025-06-17 15:20:38.292029+00	428	2025-06-17 15:20:38.292029+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-08	12:30:00	12:00:00	0	1	2025-06-17 15:20:38.307248+00	429	2025-06-17 15:20:38.307248+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-10	09:30:00	09:00:00	0	0	2025-06-17 15:20:38.329294+00	430	2025-06-17 15:20:38.329294+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-21	08:30:00	08:00:00	0	0	2025-06-17 15:20:38.354674+00	431	2025-06-17 15:20:38.354674+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-06	15:00:00	14:30:00	0	0	2025-06-17 15:20:38.368435+00	432	2025-06-17 15:20:38.368435+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-13	18:00:00	17:30:00	0	0	2025-06-17 15:20:38.400388+00	433	2025-06-17 15:20:38.400388+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-0026-79ac-a236-50d54f042a62	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-14	20:30:00	20:00:00	0	0	2025-06-17 15:20:38.443635+00	434	2025-06-17 15:20:38.443635+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-09	14:00:00	13:30:00	0	0	2025-06-17 15:20:38.460705+00	435	2025-06-17 15:20:38.460705+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-07	17:00:00	16:30:00	0	1	2025-06-17 15:20:38.486958+00	436	2025-06-17 15:20:38.486958+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-06	19:00:00	18:30:00	0	0	2025-06-17 15:20:38.50328+00	437	2025-06-17 15:20:38.50328+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-14	10:00:00	09:30:00	0	0	2025-06-17 15:20:38.525516+00	438	2025-06-17 15:20:38.525516+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-04	20:30:00	20:00:00	0	0	2025-06-17 15:20:38.540008+00	439	2025-06-17 15:20:38.540008+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-06	11:30:00	11:00:00	0	1	2025-06-17 15:20:38.556052+00	440	2025-06-17 15:20:38.556052+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-06-20	10:00:00	09:30:00	0	0	2025-06-17 15:20:38.579336+00	441	2025-06-17 15:20:38.579336+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-10	17:00:00	16:30:00	0	0	2025-06-17 15:20:38.606232+00	442	2025-06-17 15:20:38.606232+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичне оніміння кінцівок, поколювання, слабкість м’язів.
2025-07-13	16:00:00	15:30:00	0	1	2025-06-17 15:20:38.625194+00	443	2025-06-17 15:20:38.625194+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-09	16:30:00	16:00:00	0	0	2025-06-17 15:20:38.64192+00	444	2025-06-17 15:20:38.64192+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-11	10:00:00	09:30:00	0	0	2025-06-17 15:20:38.65912+00	445	2025-06-17 15:20:38.65912+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-15	11:30:00	11:00:00	0	1	2025-06-17 15:20:38.676479+00	446	2025-06-17 15:20:38.676479+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Проблеми з обміном речовин, часті головні болі і запаморочення.
2025-07-13	18:30:00	18:00:00	0	0	2025-06-17 15:20:38.700561+00	447	2025-06-17 15:20:38.700561+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-04	14:30:00	14:00:00	0	1	2025-06-17 15:20:38.720477+00	448	2025-06-17 15:20:38.720477+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-06-20	13:30:00	13:00:00	0	0	2025-06-17 15:20:38.750177+00	449	2025-06-17 15:20:38.750177+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-03	14:00:00	13:30:00	0	0	2025-06-17 15:20:38.781691+00	450	2025-06-17 15:20:38.781691+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-15	17:00:00	16:30:00	0	1	2025-06-17 15:20:38.819236+00	451	2025-06-17 15:20:38.819236+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-15	20:00:00	19:30:00	0	1	2025-06-17 15:20:40.842038+00	514	2025-06-17 15:20:40.842038+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-14	23:30:00	23:00:00	0	0	2025-06-17 15:20:38.856293+00	452	2025-06-17 15:20:38.856293+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-08	13:30:00	13:00:00	0	1	2025-06-17 15:20:38.877945+00	453	2025-06-17 15:20:38.877945+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-03	23:00:00	22:30:00	0	0	2025-06-17 15:20:38.895658+00	454	2025-06-17 15:20:38.895658+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-07	20:30:00	20:00:00	0	0	2025-06-17 15:20:38.93317+00	455	2025-06-17 15:20:38.93317+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-05	20:00:00	19:30:00	0	1	2025-06-17 15:20:38.948551+00	456	2025-06-17 15:20:38.948551+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-06	15:00:00	14:30:00	0	1	2025-06-17 15:20:38.970553+00	457	2025-06-17 15:20:38.970553+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-15	19:30:00	19:00:00	0	1	2025-06-17 15:20:38.985053+00	458	2025-06-17 15:20:38.985053+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-11	10:00:00	09:30:00	0	1	2025-06-17 15:20:39.017399+00	459	2025-06-17 15:20:39.017399+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-26	09:30:00	09:00:00	0	1	2025-06-17 15:20:39.106417+00	460	2025-06-17 15:20:39.106417+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-04	13:30:00	13:00:00	0	1	2025-06-17 15:20:39.144036+00	461	2025-06-17 15:20:39.144036+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-12	10:30:00	10:00:00	0	1	2025-06-17 15:20:39.165698+00	462	2025-06-17 15:20:39.165698+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-04	14:00:00	13:30:00	0	1	2025-06-17 15:20:39.187658+00	463	2025-06-17 15:20:39.187658+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-26	12:30:00	12:00:00	0	0	2025-06-17 15:20:39.23842+00	464	2025-06-17 15:20:39.23842+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-05	08:30:00	08:00:00	0	1	2025-06-17 15:20:39.311784+00	466	2025-06-17 15:20:39.311784+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f603-7282-bc16-c98712d0e44d	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-06	17:00:00	16:30:00	0	1	2025-06-17 15:20:39.32661+00	467	2025-06-17 15:20:39.32661+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-06-20	12:00:00	11:30:00	0	1	2025-06-17 15:20:39.392553+00	468	2025-06-17 15:20:39.392553+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-08	21:00:00	20:30:00	0	1	2025-06-17 15:20:39.435353+00	469	2025-06-17 15:20:39.435353+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-11	12:30:00	12:00:00	0	0	2025-06-17 15:20:39.45281+00	470	2025-06-17 15:20:39.45281+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-15	14:30:00	14:00:00	0	0	2025-06-17 15:20:39.498166+00	471	2025-06-17 15:20:39.498166+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-13	00:30:00	00:00:00	0	1	2025-06-17 15:20:39.550737+00	472	2025-06-17 15:20:39.550737+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-06-20	13:30:00	13:00:00	0	0	2025-06-17 15:20:39.598855+00	473	2025-06-17 15:20:39.598855+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-0026-79ac-a236-50d54f042a62	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-14	10:00:00	09:30:00	0	1	2025-06-17 15:20:39.62701+00	474	2025-06-17 15:20:39.62701+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f469-7573-ba3a-604cd272778f	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-08	12:30:00	12:00:00	0	0	2025-06-17 15:20:39.644837+00	475	2025-06-17 15:20:39.644837+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Часті головні болі, що супроводжуються нудотою та чутливістю до світла.
2025-07-05	11:30:00	11:00:00	0	1	2025-06-17 15:20:39.663548+00	476	2025-06-17 15:20:39.663548+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-12	13:30:00	13:00:00	0	1	2025-06-17 15:20:39.716785+00	477	2025-06-17 15:20:39.716785+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Часті головні болі, що супроводжуються нудотою та чутливістю до світла.
2025-07-05	19:30:00	19:00:00	0	0	2025-06-17 15:20:39.738344+00	478	2025-06-17 15:20:39.738344+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-06-19	09:30:00	09:00:00	0	1	2025-06-17 15:20:39.76011+00	479	2025-06-17 15:20:39.76011+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-03	16:00:00	15:30:00	0	1	2025-06-17 15:20:39.79156+00	481	2025-06-17 15:20:39.79156+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-11	18:30:00	18:00:00	0	0	2025-06-17 16:26:33.605541+00	1601	2025-06-17 16:26:33.605541+00	01977e7c-e1ea-7674-a453-c35ee7829b40	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	тестовий запис 3
2025-07-05	11:30:00	11:00:00	0	1	2025-06-17 15:20:39.814783+00	482	2025-06-17 15:20:39.814783+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-11	11:30:00	11:00:00	0	0	2025-06-17 15:20:39.852061+00	483	2025-06-17 15:20:39.852061+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-03	10:00:00	09:30:00	0	0	2025-06-17 15:20:39.901521+00	485	2025-06-17 15:20:39.901521+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-ff63-73de-9441-b73d75415356	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-06-20	11:00:00	10:30:00	0	1	2025-06-17 15:20:39.92809+00	486	2025-06-17 15:20:39.92809+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-ff63-73de-9441-b73d75415356	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-06	22:00:00	21:30:00	0	0	2025-06-17 15:20:39.951013+00	487	2025-06-17 15:20:39.951013+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-09	11:30:00	11:00:00	0	0	2025-06-17 15:20:39.964508+00	488	2025-06-17 15:20:39.964508+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-15	20:30:00	20:00:00	0	0	2025-06-17 15:20:40.002081+00	489	2025-06-17 15:20:40.002081+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-14	08:30:00	08:00:00	0	0	2025-06-17 15:20:40.018938+00	490	2025-06-17 15:20:40.018938+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-0026-79ac-a236-50d54f042a62	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-10	11:00:00	10:30:00	0	1	2025-06-17 15:20:40.064503+00	491	2025-06-17 15:20:40.064503+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-03	20:00:00	19:30:00	0	1	2025-06-17 15:20:40.090489+00	492	2025-06-17 15:20:40.090489+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-06-20	18:00:00	17:30:00	0	0	2025-06-17 15:20:40.120113+00	493	2025-06-17 15:20:40.120113+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-15	12:30:00	12:00:00	0	1	2025-06-17 15:20:40.152602+00	494	2025-06-17 15:20:40.152602+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-11	08:30:00	08:00:00	0	1	2025-06-17 15:20:40.167816+00	495	2025-06-17 15:20:40.167816+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:40.259025+00	496	2025-06-17 15:20:40.259025+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-15	13:30:00	13:00:00	0	1	2025-06-17 15:20:40.276666+00	497	2025-06-17 15:20:40.276666+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Підвищений артеріальний тиск, що складно контролюється медикаментами.
2025-07-10	22:30:00	22:00:00	0	1	2025-06-17 15:20:40.31624+00	498	2025-06-17 15:20:40.31624+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-09	21:30:00	21:00:00	0	1	2025-06-17 15:20:40.390319+00	499	2025-06-17 15:20:40.390319+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-05	12:00:00	11:30:00	0	1	2025-06-17 15:20:40.404552+00	500	2025-06-17 15:20:40.404552+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-10	14:30:00	14:00:00	0	0	2025-06-17 15:20:40.434759+00	501	2025-06-17 15:20:40.434759+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-05	10:00:00	09:30:00	0	1	2025-06-17 15:20:40.499419+00	502	2025-06-17 15:20:40.499419+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-08	20:30:00	20:00:00	0	0	2025-06-17 15:20:40.545229+00	503	2025-06-17 15:20:40.545229+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-0026-79ac-a236-50d54f042a62	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-11	14:00:00	13:30:00	0	1	2025-06-17 15:20:40.602892+00	505	2025-06-17 15:20:40.602892+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-04	21:00:00	20:30:00	0	1	2025-06-17 15:20:40.701687+00	507	2025-06-17 15:20:40.701687+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-15	11:30:00	11:00:00	0	1	2025-06-17 15:20:40.725637+00	508	2025-06-17 15:20:40.725637+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-07	14:30:00	14:00:00	0	1	2025-06-17 15:20:40.740551+00	509	2025-06-17 15:20:40.740551+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-05	17:00:00	16:30:00	0	0	2025-06-17 15:20:40.760973+00	510	2025-06-17 15:20:40.760973+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-12	19:00:00	18:30:00	0	1	2025-06-17 15:20:40.786635+00	511	2025-06-17 15:20:40.786635+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Порушення чутливості та рухової функції кінцівок.
2025-06-23	08:30:00	08:00:00	2	1	2025-06-21 21:21:23.700615+00	1705	2025-06-21 21:21:23.700615+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	лщошргп
2025-06-28	17:00:00	16:30:00	2	1	2025-06-21 21:23:08.287042+00	1706	2025-06-21 21:23:08.287042+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	oiuytdyuiop
2025-06-19	10:00:00	09:30:00	2	1	2025-06-17 15:20:39.884446+00	484	2025-06-17 15:20:39.884446+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Тривожність, депресія, часті зміни настрою.
2025-07-15	15:00:00	14:30:00	0	1	2025-06-17 15:20:40.869109+00	515	2025-06-17 15:20:40.869109+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-08	18:00:00	17:30:00	0	0	2025-06-17 15:20:40.886619+00	516	2025-06-17 15:20:40.886619+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-11	11:30:00	11:00:00	0	1	2025-06-17 15:20:40.91124+00	517	2025-06-17 15:20:40.91124+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-09	19:00:00	18:30:00	0	0	2025-06-17 15:20:40.930327+00	518	2025-06-17 15:20:40.930327+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-12	17:00:00	16:30:00	0	1	2025-06-17 15:20:40.98185+00	519	2025-06-17 15:20:40.98185+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-07	15:00:00	14:30:00	0	1	2025-06-17 15:20:41.002523+00	520	2025-06-17 15:20:41.002523+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-06-20	12:00:00	11:30:00	0	1	2025-06-17 15:20:41.046365+00	521	2025-06-17 15:20:41.046365+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-06	16:30:00	16:00:00	0	0	2025-06-17 15:20:41.130252+00	522	2025-06-17 15:20:41.130252+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-12	08:30:00	08:00:00	0	0	2025-06-17 15:20:41.177033+00	523	2025-06-17 15:20:41.177033+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-06	18:30:00	18:00:00	0	0	2025-06-17 15:20:41.208706+00	524	2025-06-17 15:20:41.208706+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-09	19:00:00	18:30:00	0	0	2025-06-17 15:20:41.233456+00	525	2025-06-17 15:20:41.233456+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Потреба у відновленні після перенесеного інсульту або травми.
2025-07-08	16:00:00	15:30:00	0	1	2025-06-17 15:20:41.251169+00	526	2025-06-17 15:20:41.251169+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Підвищений артеріальний тиск, що складно контролюється медикаментами.
2025-07-06	23:00:00	22:30:00	0	1	2025-06-17 15:20:41.278317+00	527	2025-06-17 15:20:41.278317+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-08	15:00:00	14:30:00	0	0	2025-06-17 15:20:41.305689+00	528	2025-06-17 15:20:41.305689+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-10	21:30:00	21:00:00	0	0	2025-06-17 15:20:41.380245+00	529	2025-06-17 15:20:41.380245+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-04	15:00:00	14:30:00	0	1	2025-06-17 15:20:41.428709+00	530	2025-06-17 15:20:41.428709+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-12	18:00:00	17:30:00	0	1	2025-06-17 15:20:41.447164+00	531	2025-06-17 15:20:41.447164+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-06-20	08:30:00	08:00:00	0	1	2025-06-17 15:20:41.486081+00	532	2025-06-17 15:20:41.486081+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-09	11:00:00	10:30:00	0	1	2025-06-17 15:20:41.522857+00	533	2025-06-17 15:20:41.522857+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-11	11:30:00	11:00:00	0	0	2025-06-17 15:20:41.557666+00	534	2025-06-17 15:20:41.557666+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-05	11:30:00	11:00:00	0	1	2025-06-17 15:20:41.578459+00	535	2025-06-17 15:20:41.578459+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-09	08:30:00	08:00:00	0	0	2025-06-17 15:20:41.59687+00	536	2025-06-17 15:20:41.59687+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-06	16:30:00	16:00:00	0	0	2025-06-17 15:20:41.622501+00	537	2025-06-17 15:20:41.622501+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-09	17:30:00	17:00:00	0	0	2025-06-17 15:20:41.639402+00	538	2025-06-17 15:20:41.639402+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-09	17:00:00	16:30:00	0	0	2025-06-17 15:20:41.670476+00	539	2025-06-17 15:20:41.670476+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-11	13:30:00	13:00:00	0	0	2025-06-17 15:20:41.695327+00	540	2025-06-17 15:20:41.695327+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-07	14:30:00	14:00:00	0	0	2025-06-17 15:20:41.748314+00	541	2025-06-17 15:20:41.748314+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f603-7282-bc16-c98712d0e44d	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-06-19	11:00:00	10:30:00	0	0	2025-06-17 15:20:41.800084+00	542	2025-06-17 15:20:41.800084+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-07	19:00:00	18:30:00	0	0	2025-06-17 15:20:45.55289+00	666	2025-06-17 15:20:45.55289+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-06-19	10:30:00	10:00:00	0	1	2025-06-17 15:20:41.856016+00	544	2025-06-17 15:20:41.856016+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-14	12:00:00	11:30:00	0	1	2025-06-17 15:20:41.875501+00	545	2025-06-17 15:20:41.875501+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-13	13:00:00	12:30:00	0	1	2025-06-17 15:20:41.897707+00	546	2025-06-17 15:20:41.897707+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Зміни кольору і кількості сечі, набряки кінцівок, особливо вранці.
2025-07-07	22:00:00	21:30:00	0	1	2025-06-17 15:20:41.941869+00	547	2025-06-17 15:20:41.941869+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення стільця: запори, діарея, або їх чергування без явної причини.
2025-07-07	08:30:00	08:00:00	0	1	2025-06-17 15:20:41.973147+00	548	2025-06-17 15:20:41.973147+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Зниження концентрації уваги, забудькуватість, проблеми з пам’яттю.
2025-07-11	16:00:00	15:30:00	0	0	2025-06-17 15:20:41.993338+00	549	2025-06-17 15:20:41.993338+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-05	19:30:00	19:00:00	0	1	2025-06-17 15:20:42.011476+00	550	2025-06-17 15:20:42.011476+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-14	21:30:00	21:00:00	0	0	2025-06-17 15:20:42.02817+00	551	2025-06-17 15:20:42.02817+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-06	14:30:00	14:00:00	0	0	2025-06-17 15:20:42.054938+00	552	2025-06-17 15:20:42.054938+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-04	09:30:00	09:00:00	0	1	2025-06-17 15:20:42.088151+00	553	2025-06-17 15:20:42.088151+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-04	13:00:00	12:30:00	0	0	2025-06-17 15:20:42.136725+00	554	2025-06-17 15:20:42.136725+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-13	11:00:00	10:30:00	0	0	2025-06-17 15:20:42.1852+00	555	2025-06-17 15:20:42.1852+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-04	20:00:00	19:30:00	0	0	2025-06-17 15:20:42.218846+00	556	2025-06-17 15:20:42.218846+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-04	10:00:00	09:30:00	0	1	2025-06-17 15:20:42.246888+00	557	2025-06-17 15:20:42.246888+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-04	15:00:00	14:30:00	0	1	2025-06-17 15:20:42.309154+00	558	2025-06-17 15:20:42.309154+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-03	08:30:00	08:00:00	0	1	2025-06-17 15:20:42.33848+00	559	2025-06-17 15:20:42.33848+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-04	12:00:00	11:30:00	0	1	2025-06-17 15:20:42.367615+00	560	2025-06-17 15:20:42.367615+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-11	19:30:00	19:00:00	0	1	2025-06-17 15:20:42.38914+00	561	2025-06-17 15:20:42.38914+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-08	16:00:00	15:30:00	0	0	2025-06-17 15:20:42.455303+00	562	2025-06-17 15:20:42.455303+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-05	14:30:00	14:00:00	0	1	2025-06-17 15:20:42.473774+00	563	2025-06-17 15:20:42.473774+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-11	17:00:00	16:30:00	0	1	2025-06-17 15:20:42.493154+00	564	2025-06-17 15:20:42.493154+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-03	14:00:00	13:30:00	0	1	2025-06-17 15:20:42.538978+00	565	2025-06-17 15:20:42.538978+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-19	08:30:00	08:00:00	0	0	2025-06-17 15:20:42.581442+00	566	2025-06-17 15:20:42.581442+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Висипання на шкірі, діарея або запори, блювання.
2025-07-03	11:00:00	10:30:00	0	0	2025-06-17 15:20:42.600338+00	567	2025-06-17 15:20:42.600338+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-05	20:30:00	20:00:00	0	0	2025-06-17 15:20:42.623899+00	568	2025-06-17 15:20:42.623899+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f469-7573-ba3a-604cd272778f	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-13	13:30:00	13:00:00	0	0	2025-06-17 15:20:42.641012+00	569	2025-06-17 15:20:42.641012+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-14	21:00:00	20:30:00	0	1	2025-06-17 15:20:42.665255+00	570	2025-06-17 15:20:42.665255+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-13	21:00:00	20:30:00	0	0	2025-06-17 15:20:42.725488+00	571	2025-06-17 15:20:42.725488+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-13	11:00:00	10:30:00	0	1	2025-06-17 15:20:42.763037+00	572	2025-06-17 15:20:42.763037+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-07	10:00:00	09:30:00	0	0	2025-06-17 15:20:42.786178+00	573	2025-06-17 15:20:42.786178+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-09	16:00:00	15:30:00	2	0	2025-06-21 21:23:29.769459+00	1707	2025-06-21 21:23:29.769459+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	;lpoiuyftyuio
2025-07-15	17:30:00	17:00:00	0	1	2025-06-17 15:20:42.807908+00	574	2025-06-17 15:20:42.807908+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-10	17:00:00	16:30:00	0	1	2025-06-17 15:20:42.87434+00	575	2025-06-17 15:20:42.87434+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:42.912143+00	576	2025-06-17 15:20:42.912143+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-15	12:00:00	11:30:00	0	1	2025-06-17 15:20:42.966541+00	577	2025-06-17 15:20:42.966541+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-06-19	08:30:00	08:00:00	0	0	2025-06-17 15:20:43.033129+00	578	2025-06-17 15:20:43.033129+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-14	12:00:00	11:30:00	0	0	2025-06-17 15:20:43.097447+00	579	2025-06-17 15:20:43.097447+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-08	21:30:00	21:00:00	0	0	2025-06-17 15:20:43.135324+00	580	2025-06-17 15:20:43.135324+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-04	23:00:00	22:30:00	0	0	2025-06-17 15:20:43.159452+00	581	2025-06-17 15:20:43.159452+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-08	12:30:00	12:00:00	0	1	2025-06-17 15:20:43.181121+00	582	2025-06-17 15:20:43.181121+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-05	11:30:00	11:00:00	0	0	2025-06-17 15:20:43.198872+00	583	2025-06-17 15:20:43.198872+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Синдром перевтоми: втрата витривалості, безсоння після тренувань.
2025-07-15	21:00:00	20:30:00	0	0	2025-06-17 15:20:43.23915+00	584	2025-06-17 15:20:43.23915+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-06-19	08:30:00	08:00:00	0	1	2025-06-17 15:20:43.264044+00	585	2025-06-17 15:20:43.264044+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	14:30:00	14:00:00	0	0	2025-06-17 15:20:43.283081+00	586	2025-06-17 15:20:43.283081+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-12	10:00:00	09:30:00	0	0	2025-06-17 15:20:43.309865+00	587	2025-06-17 15:20:43.309865+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-08	17:00:00	16:30:00	0	0	2025-06-17 15:20:43.352103+00	589	2025-06-17 15:20:43.352103+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Обмеження рухливості, скутість м’язів, загальна слабкість.
2025-07-07	16:00:00	15:30:00	0	1	2025-06-17 15:20:43.373161+00	590	2025-06-17 15:20:43.373161+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-06-20	15:00:00	14:30:00	0	0	2025-06-17 15:20:43.4093+00	591	2025-06-17 15:20:43.4093+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:43.424827+00	592	2025-06-17 15:20:43.424827+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Порушення сну, тривожність, панічні атаки.
2025-07-07	15:30:00	15:00:00	0	0	2025-06-17 15:20:43.446606+00	593	2025-06-17 15:20:43.446606+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-21	08:30:00	08:00:00	0	0	2025-06-17 15:20:43.485469+00	594	2025-06-17 15:20:43.485469+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-03	20:00:00	19:30:00	0	1	2025-06-17 15:20:43.503434+00	595	2025-06-17 15:20:43.503434+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-06	23:00:00	22:30:00	0	1	2025-06-17 15:20:43.53823+00	596	2025-06-17 15:20:43.53823+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-21	09:30:00	09:00:00	0	0	2025-06-17 15:20:43.573121+00	597	2025-06-17 15:20:43.573121+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-08	08:30:00	08:00:00	0	1	2025-06-17 15:20:43.594234+00	598	2025-06-17 15:20:43.594234+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-10	16:00:00	15:30:00	0	1	2025-06-17 15:20:43.617929+00	599	2025-06-17 15:20:43.617929+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-04	12:30:00	12:00:00	0	1	2025-06-17 15:20:43.674631+00	600	2025-06-17 15:20:43.674631+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-04	15:00:00	14:30:00	0	1	2025-06-17 15:20:43.702001+00	601	2025-06-17 15:20:43.702001+00	01977e79-f1a4-7811-840e-404568a4376e	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-05	13:00:00	12:30:00	0	0	2025-06-17 15:20:43.728913+00	602	2025-06-17 15:20:43.728913+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-03	19:00:00	18:30:00	0	0	2025-06-17 15:20:43.784062+00	603	2025-06-17 15:20:43.784062+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-24	10:30:00	10:00:00	0	0	2025-06-17 15:20:43.810877+00	604	2025-06-17 15:20:43.810877+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Постійна слабкість, сонливість, порушення терморегуляції тіла.
2025-07-05	21:00:00	20:30:00	0	0	2025-06-17 15:20:43.825996+00	605	2025-06-17 15:20:43.825996+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-05	11:30:00	11:00:00	0	0	2025-06-17 15:20:43.842055+00	606	2025-06-17 15:20:43.842055+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-14	16:30:00	16:00:00	0	0	2025-06-17 15:20:43.857449+00	607	2025-06-17 15:20:43.857449+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Запаморочення, порушення координації рухів, падіння без видимої причини.
2025-07-04	23:30:00	23:00:00	0	0	2025-06-17 15:20:43.879394+00	608	2025-06-17 15:20:43.879394+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-09	10:00:00	09:30:00	0	1	2025-06-17 15:20:43.908582+00	609	2025-06-17 15:20:43.908582+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Загальна слабкість, нудота, втрата апетиту, що зростає з часом.
2025-07-05	18:00:00	17:30:00	0	0	2025-06-17 15:20:43.926066+00	610	2025-06-17 15:20:43.926066+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-10	18:00:00	17:30:00	0	1	2025-06-17 15:20:43.944195+00	611	2025-06-17 15:20:43.944195+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Зміни кольору і кількості сечі, набряки кінцівок, особливо вранці.
2025-07-04	11:30:00	11:00:00	0	0	2025-06-17 15:20:43.962033+00	612	2025-06-17 15:20:43.962033+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-12	20:30:00	20:00:00	0	1	2025-06-17 15:20:43.988255+00	613	2025-06-17 15:20:43.988255+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-13	13:00:00	12:30:00	0	1	2025-06-17 15:20:44.005021+00	614	2025-06-17 15:20:44.005021+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-06	17:00:00	16:30:00	0	0	2025-06-17 15:20:44.021183+00	615	2025-06-17 15:20:44.021183+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-05	13:00:00	12:30:00	0	1	2025-06-17 15:20:44.036143+00	616	2025-06-17 15:20:44.036143+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-05	09:30:00	09:00:00	0	0	2025-06-17 15:20:44.053788+00	617	2025-06-17 15:20:44.053788+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-12	21:00:00	20:30:00	0	0	2025-06-17 15:20:44.06865+00	618	2025-06-17 15:20:44.06865+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-06-19	10:00:00	09:30:00	0	0	2025-06-17 15:20:44.105436+00	619	2025-06-17 15:20:44.105436+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-11	09:30:00	09:00:00	0	1	2025-06-17 15:20:44.225024+00	620	2025-06-17 15:20:44.225024+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-03	13:00:00	12:30:00	0	0	2025-06-17 15:20:44.238641+00	621	2025-06-17 15:20:44.238641+00	01977e79-f1a4-7811-840e-404568a4376e	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-05	23:30:00	23:00:00	0	1	2025-06-17 15:20:44.251291+00	622	2025-06-17 15:20:44.251291+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-08	10:00:00	09:30:00	0	0	2025-06-17 15:20:44.2781+00	623	2025-06-17 15:20:44.2781+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Часті отити, біль у вусі, зниження слуху.
2025-07-09	20:30:00	20:00:00	0	0	2025-06-17 15:20:44.295009+00	624	2025-06-17 15:20:44.295009+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Часті головні болі, що супроводжуються нудотою та чутливістю до світла.
2025-07-13	12:00:00	11:30:00	0	0	2025-06-17 15:20:44.334366+00	625	2025-06-17 15:20:44.334366+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-07-15	09:30:00	09:00:00	0	1	2025-06-17 15:20:44.361641+00	626	2025-06-17 15:20:44.361641+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-11	11:30:00	11:00:00	0	1	2025-06-17 15:20:44.380231+00	627	2025-06-17 15:20:44.380231+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-06-19	10:00:00	09:30:00	0	0	2025-06-17 15:20:44.410052+00	628	2025-06-17 15:20:44.410052+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-10	08:30:00	08:00:00	0	1	2025-06-17 15:20:44.441308+00	629	2025-06-17 15:20:44.441308+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-04	14:30:00	14:00:00	0	1	2025-06-17 15:20:44.471482+00	630	2025-06-17 15:20:44.471482+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-12	13:00:00	12:30:00	0	1	2025-06-17 15:20:44.487545+00	631	2025-06-17 15:20:44.487545+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-08	19:00:00	18:30:00	0	0	2025-06-17 15:20:44.504856+00	632	2025-06-17 15:20:44.504856+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-15	21:30:00	21:00:00	0	0	2025-06-17 15:20:44.549824+00	633	2025-06-17 15:20:44.549824+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-11	13:00:00	12:30:00	0	1	2025-06-17 15:20:44.571209+00	634	2025-06-17 15:20:44.571209+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-15	11:00:00	10:30:00	0	1	2025-06-17 15:20:44.59313+00	635	2025-06-17 15:20:44.59313+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-10	09:30:00	09:00:00	0	1	2025-06-17 15:20:44.620932+00	636	2025-06-17 15:20:44.620932+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-10	20:00:00	19:30:00	0	1	2025-06-17 15:20:44.637546+00	637	2025-06-17 15:20:44.637546+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-21	08:30:00	08:00:00	0	1	2025-06-17 15:20:44.708625+00	638	2025-06-17 15:20:44.708625+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-10	16:00:00	15:30:00	0	1	2025-06-17 15:20:44.72813+00	639	2025-06-17 15:20:44.72813+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-06-20	10:30:00	10:00:00	0	1	2025-06-17 15:20:44.813109+00	640	2025-06-17 15:20:44.813109+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-09	16:00:00	15:30:00	0	0	2025-06-17 15:20:44.84853+00	641	2025-06-17 15:20:44.84853+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-08	14:00:00	13:30:00	0	0	2025-06-17 15:20:44.872732+00	642	2025-06-17 15:20:44.872732+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Хронічна закладеність носа, відсутність полегшення після використання крапель.
2025-07-10	19:30:00	19:00:00	0	0	2025-06-17 15:20:44.902427+00	643	2025-06-17 15:20:44.902427+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-07	12:30:00	12:00:00	0	1	2025-06-17 15:20:44.954379+00	644	2025-06-17 15:20:44.954379+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-09	18:00:00	17:30:00	0	0	2025-06-17 15:20:45.002415+00	645	2025-06-17 15:20:45.002415+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-13	18:30:00	18:00:00	0	1	2025-06-17 15:20:45.034387+00	646	2025-06-17 15:20:45.034387+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-06-20	19:00:00	18:30:00	0	0	2025-06-17 15:20:45.091653+00	648	2025-06-17 15:20:45.091653+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-08	11:00:00	10:30:00	0	0	2025-06-17 15:20:45.110477+00	649	2025-06-17 15:20:45.110477+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-08	09:30:00	09:00:00	0	1	2025-06-17 15:20:45.12861+00	650	2025-06-17 15:20:45.12861+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-11	19:30:00	19:00:00	0	1	2025-06-17 15:20:45.148406+00	651	2025-06-17 15:20:45.148406+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-13	17:30:00	17:00:00	0	1	2025-06-17 15:20:45.173279+00	652	2025-06-17 15:20:45.173279+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-03	19:30:00	19:00:00	0	0	2025-06-17 15:20:45.236258+00	653	2025-06-17 15:20:45.236258+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-06	09:30:00	09:00:00	0	1	2025-06-17 15:20:45.251876+00	654	2025-06-17 15:20:45.251876+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-06	18:00:00	17:30:00	0	0	2025-06-17 15:20:45.285299+00	655	2025-06-17 15:20:45.285299+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-06-19	12:30:00	12:00:00	0	1	2025-06-17 15:20:45.337497+00	657	2025-06-17 15:20:45.337497+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-07	11:30:00	11:00:00	0	0	2025-06-17 15:20:45.356927+00	658	2025-06-17 15:20:45.356927+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-20	08:30:00	08:00:00	0	1	2025-06-17 15:20:45.395484+00	659	2025-06-17 15:20:45.395484+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-06	19:30:00	19:00:00	0	1	2025-06-17 15:20:45.415481+00	660	2025-06-17 15:20:45.415481+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-09	19:30:00	19:00:00	0	0	2025-06-17 15:20:45.43188+00	661	2025-06-17 15:20:45.43188+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-05	21:00:00	20:30:00	0	1	2025-06-17 15:20:45.482861+00	663	2025-06-17 15:20:45.482861+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-06	16:30:00	16:00:00	0	0	2025-06-17 15:20:45.515088+00	664	2025-06-17 15:20:45.515088+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-07	14:00:00	13:30:00	0	1	2025-06-17 15:20:45.53339+00	665	2025-06-17 15:20:45.53339+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-13	19:00:00	18:30:00	0	1	2025-06-17 15:20:45.674796+00	669	2025-06-17 15:20:45.674796+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-14	13:00:00	12:30:00	0	0	2025-06-17 15:20:45.68998+00	670	2025-06-17 15:20:45.68998+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-14	10:00:00	09:30:00	0	1	2025-06-17 15:20:45.748+00	671	2025-06-17 15:20:45.748+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-08	09:30:00	09:00:00	0	0	2025-06-17 15:20:45.809362+00	672	2025-06-17 15:20:45.809362+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-04	16:30:00	16:00:00	0	0	2025-06-17 15:20:45.825514+00	673	2025-06-17 15:20:45.825514+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-03	21:30:00	21:00:00	0	0	2025-06-17 15:20:45.840594+00	674	2025-06-17 15:20:45.840594+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-10	21:00:00	20:30:00	0	0	2025-06-17 15:20:45.862729+00	675	2025-06-17 15:20:45.862729+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-06	13:30:00	13:00:00	0	0	2025-06-17 15:20:45.878085+00	676	2025-06-17 15:20:45.878085+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-11	22:00:00	21:30:00	0	1	2025-06-17 15:20:45.910033+00	677	2025-06-17 15:20:45.910033+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-15	20:30:00	20:00:00	0	1	2025-06-17 15:20:45.930181+00	678	2025-06-17 15:20:45.930181+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-09	10:00:00	09:30:00	0	0	2025-06-17 15:20:45.981351+00	679	2025-06-17 15:20:45.981351+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-13	10:00:00	09:30:00	0	0	2025-06-17 15:20:46.025305+00	680	2025-06-17 15:20:46.025305+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-03	14:30:00	14:00:00	0	0	2025-06-17 15:20:46.046238+00	681	2025-06-17 15:20:46.046238+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-12	08:30:00	08:00:00	0	0	2025-06-17 15:20:46.068245+00	682	2025-06-17 15:20:46.068245+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-15	22:00:00	21:30:00	0	0	2025-06-17 15:20:46.086749+00	683	2025-06-17 15:20:46.086749+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Часті головні болі, що супроводжуються нудотою та чутливістю до світла.
2025-07-15	19:30:00	19:00:00	0	1	2025-06-17 15:20:46.112867+00	684	2025-06-17 15:20:46.112867+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Тривожність, депресія, часті зміни настрою.
2025-07-13	17:00:00	16:30:00	0	0	2025-06-17 15:20:46.138425+00	685	2025-06-17 15:20:46.138425+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-14	17:00:00	16:30:00	0	1	2025-06-17 15:20:46.179312+00	686	2025-06-17 15:20:46.179312+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-08	09:30:00	09:00:00	0	1	2025-06-17 15:20:46.22359+00	688	2025-06-17 15:20:46.22359+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-04	18:00:00	17:30:00	0	0	2025-06-17 15:20:46.270505+00	689	2025-06-17 15:20:46.270505+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-09	17:30:00	17:00:00	0	0	2025-06-17 15:20:46.286597+00	690	2025-06-17 15:20:46.286597+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-10	09:30:00	09:00:00	0	0	2025-06-17 15:20:46.329612+00	691	2025-06-17 15:20:46.329612+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-11	18:00:00	17:30:00	0	0	2025-06-17 15:20:46.348242+00	692	2025-06-17 15:20:46.348242+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-15	18:00:00	17:30:00	0	1	2025-06-17 15:20:46.381449+00	693	2025-06-17 15:20:46.381449+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-15	20:30:00	20:00:00	0	0	2025-06-17 15:20:46.414195+00	694	2025-06-17 15:20:46.414195+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-08	14:00:00	13:30:00	0	1	2025-06-17 15:20:46.444634+00	695	2025-06-17 15:20:46.444634+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-15	18:30:00	18:00:00	0	0	2025-06-17 15:20:46.467704+00	696	2025-06-17 15:20:46.467704+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-03	14:30:00	14:00:00	0	0	2025-06-17 15:20:46.499998+00	697	2025-06-17 15:20:46.499998+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-06	13:00:00	12:30:00	0	1	2025-06-17 15:20:46.524241+00	698	2025-06-17 15:20:46.524241+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-06	21:00:00	20:30:00	0	1	2025-06-17 15:20:46.542626+00	699	2025-06-17 15:20:46.542626+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-12	19:30:00	19:00:00	0	0	2025-06-17 15:20:46.563895+00	700	2025-06-17 15:20:46.563895+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичний біль у попереку, що посилюється при фізичному навантаженні.
2025-07-03	18:00:00	17:30:00	0	1	2025-06-17 15:20:46.597969+00	701	2025-06-17 15:20:46.597969+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-13	13:30:00	13:00:00	0	1	2025-06-17 15:20:46.657071+00	702	2025-06-17 15:20:46.657071+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-07	20:00:00	19:30:00	0	1	2025-06-17 15:20:46.674046+00	703	2025-06-17 15:20:46.674046+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-15	16:00:00	15:30:00	0	0	2025-06-17 15:20:46.707686+00	705	2025-06-17 15:20:46.707686+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-04	12:00:00	11:30:00	0	1	2025-06-17 15:20:46.740921+00	707	2025-06-17 15:20:46.740921+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f469-7573-ba3a-604cd272778f	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-14	18:30:00	18:00:00	0	0	2025-06-17 15:20:46.75835+00	708	2025-06-17 15:20:46.75835+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-08	10:00:00	09:30:00	0	0	2025-06-17 15:20:46.77652+00	709	2025-06-17 15:20:46.77652+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Затримка розвитку, відставання у вазі та зрості.
2025-07-13	12:00:00	11:30:00	0	0	2025-06-17 15:20:46.825024+00	711	2025-06-17 15:20:46.825024+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Порушення сну, тривожність, панічні атаки.
2025-06-19	08:30:00	08:00:00	0	0	2025-06-17 15:20:46.859027+00	712	2025-06-17 15:20:46.859027+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-04	15:00:00	14:30:00	0	0	2025-06-17 15:20:46.886477+00	713	2025-06-17 15:20:46.886477+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:46.902256+00	714	2025-06-17 15:20:46.902256+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-11	11:00:00	10:30:00	0	0	2025-06-17 15:20:46.925901+00	715	2025-06-17 15:20:46.925901+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-04	16:30:00	16:00:00	0	0	2025-06-17 15:20:46.942394+00	716	2025-06-17 15:20:46.942394+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-04	19:00:00	18:30:00	0	1	2025-06-17 15:20:46.97386+00	717	2025-06-17 15:20:46.97386+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-04	18:30:00	18:00:00	0	0	2025-06-17 15:20:46.992412+00	718	2025-06-17 15:20:46.992412+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-13	19:00:00	18:30:00	0	1	2025-06-17 15:20:47.018975+00	719	2025-06-17 15:20:47.018975+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-06	13:30:00	13:00:00	0	1	2025-06-17 15:20:47.056779+00	720	2025-06-17 15:20:47.056779+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-15	15:00:00	14:30:00	0	0	2025-06-17 15:20:47.073282+00	721	2025-06-17 15:20:47.073282+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-08	21:00:00	20:30:00	0	0	2025-06-17 15:20:47.094488+00	722	2025-06-17 15:20:47.094488+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-11	14:30:00	14:00:00	0	0	2025-06-17 15:20:47.112161+00	723	2025-06-17 15:20:47.112161+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-04	15:30:00	15:00:00	0	0	2025-06-17 15:20:47.191028+00	724	2025-06-17 15:20:47.191028+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Хронічний біль у суглобах після спортивних тренувань.
2025-07-03	20:00:00	19:30:00	0	0	2025-06-17 15:20:47.21133+00	725	2025-06-17 15:20:47.21133+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-11	18:30:00	18:00:00	0	1	2025-06-17 15:20:47.230886+00	726	2025-06-17 15:20:47.230886+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-13	18:30:00	18:00:00	0	0	2025-06-17 15:20:47.265269+00	727	2025-06-17 15:20:47.265269+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-09	14:30:00	14:00:00	0	1	2025-06-17 15:20:47.279857+00	728	2025-06-17 15:20:47.279857+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Підвищена температура тіла без виявленої інфекції або запального процесу.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:20:47.295711+00	729	2025-06-17 15:20:47.295711+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-06-19	11:30:00	11:00:00	2	1	2025-06-17 15:20:46.805862+00	710	2025-06-17 15:20:46.805862+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-14	20:30:00	20:00:00	0	1	2025-06-17 15:20:47.309463+00	730	2025-06-17 15:20:47.309463+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-05	10:00:00	09:30:00	0	1	2025-06-17 15:20:47.327895+00	731	2025-06-17 15:20:47.327895+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:47.346512+00	732	2025-06-17 15:20:47.346512+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-03	10:30:00	10:00:00	0	0	2025-06-17 15:20:47.365736+00	733	2025-06-17 15:20:47.365736+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-06	12:30:00	12:00:00	0	1	2025-06-17 15:20:47.403502+00	734	2025-06-17 15:20:47.403502+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-14	20:00:00	19:30:00	0	1	2025-06-17 15:20:47.429127+00	735	2025-06-17 15:20:47.429127+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-11	17:30:00	17:00:00	0	0	2025-06-17 15:20:47.480873+00	736	2025-06-17 15:20:47.480873+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-06-19	11:00:00	10:30:00	0	0	2025-06-17 15:20:47.513335+00	737	2025-06-17 15:20:47.513335+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-15	12:30:00	12:00:00	0	1	2025-06-17 15:20:47.54181+00	738	2025-06-17 15:20:47.54181+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-08	12:00:00	11:30:00	0	0	2025-06-17 15:20:47.569878+00	739	2025-06-17 15:20:47.569878+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-13	21:30:00	21:00:00	0	1	2025-06-17 15:20:47.596371+00	740	2025-06-17 15:20:47.596371+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-ff63-73de-9441-b73d75415356	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-10	17:30:00	17:00:00	0	1	2025-06-17 15:20:47.613447+00	741	2025-06-17 15:20:47.613447+00	01977e79-f1a4-7811-840e-404568a4376e	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-08	15:00:00	14:30:00	0	1	2025-06-17 15:20:47.630862+00	742	2025-06-17 15:20:47.630862+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-06-24	12:00:00	11:30:00	0	1	2025-06-17 15:20:47.664667+00	743	2025-06-17 15:20:47.664667+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Зміни настрою, дратівливість, безпричинне підвищення пітливості.
2025-07-05	11:30:00	11:00:00	0	0	2025-06-17 15:20:47.679837+00	744	2025-06-17 15:20:47.679837+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-08	11:00:00	10:30:00	0	1	2025-06-17 15:20:47.695142+00	745	2025-06-17 15:20:47.695142+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-13	08:30:00	08:00:00	0	0	2025-06-17 15:20:47.716507+00	746	2025-06-17 15:20:47.716507+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-11	18:30:00	18:00:00	0	1	2025-06-17 15:20:47.744272+00	747	2025-06-17 15:20:47.744272+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Висипання на шкірі, діарея або запори, блювання.
2025-07-14	19:30:00	19:00:00	0	1	2025-06-17 15:20:47.82722+00	749	2025-06-17 15:20:47.82722+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-08	20:30:00	20:00:00	0	0	2025-06-17 15:20:47.843473+00	750	2025-06-17 15:20:47.843473+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-05	18:30:00	18:00:00	0	1	2025-06-17 15:20:47.859183+00	751	2025-06-17 15:20:47.859183+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-22	08:30:00	08:00:00	0	1	2025-06-17 15:20:47.8893+00	752	2025-06-17 15:20:47.8893+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Труднощі у спілкуванні з близькими, внутрішні конфлікти.
2025-07-08	16:00:00	15:30:00	0	0	2025-06-17 15:20:47.933094+00	753	2025-06-17 15:20:47.933094+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-06-20	20:30:00	20:00:00	0	0	2025-06-17 15:20:47.962247+00	754	2025-06-17 15:20:47.962247+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-14	13:30:00	13:00:00	0	0	2025-06-17 15:20:47.97968+00	755	2025-06-17 15:20:47.97968+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	20:00:00	19:30:00	0	1	2025-06-17 15:20:48.006071+00	756	2025-06-17 15:20:48.006071+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-19	09:30:00	09:00:00	0	1	2025-06-17 15:20:48.048409+00	757	2025-06-17 15:20:48.048409+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-13	14:30:00	14:00:00	0	1	2025-06-17 15:20:48.073565+00	758	2025-06-17 15:20:48.073565+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-09	19:30:00	19:00:00	0	0	2025-06-17 15:20:48.088808+00	759	2025-06-17 15:20:48.088808+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-13	20:00:00	19:30:00	2	1	2025-06-21 21:28:27.024645+00	1708	2025-06-21 21:28:27.024645+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	poiuyftyguiop
2025-06-21	10:00:00	09:30:00	0	0	2025-06-17 15:20:48.129218+00	760	2025-06-17 15:20:48.129218+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-04	19:30:00	19:00:00	0	1	2025-06-17 15:20:48.156977+00	761	2025-06-17 15:20:48.156977+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-10	08:30:00	08:00:00	0	1	2025-06-17 15:20:48.195692+00	762	2025-06-17 15:20:48.195692+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-06	09:30:00	09:00:00	0	0	2025-06-17 15:20:48.241383+00	763	2025-06-17 15:20:48.241383+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-08	18:00:00	17:30:00	0	0	2025-06-17 15:20:48.280404+00	764	2025-06-17 15:20:48.280404+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Біль у суглобах, особливо вранці, що зменшується при русі.
2025-07-11	15:30:00	15:00:00	0	0	2025-06-17 15:20:48.321036+00	766	2025-06-17 15:20:48.321036+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-06-22	10:00:00	09:30:00	0	0	2025-06-17 15:20:48.365815+00	767	2025-06-17 15:20:48.365815+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Постійне відчуття тривоги, напади паніки без видимої причини.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:20:48.395694+00	768	2025-06-17 15:20:48.395694+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-11	17:30:00	17:00:00	0	0	2025-06-17 15:20:48.43043+00	769	2025-06-17 15:20:48.43043+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f469-7573-ba3a-604cd272778f	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-07	16:30:00	16:00:00	0	0	2025-06-17 15:20:48.516417+00	771	2025-06-17 15:20:48.516417+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-09	15:30:00	15:00:00	0	0	2025-06-17 15:20:48.56806+00	772	2025-06-17 15:20:48.56806+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	09:30:00	09:00:00	0	1	2025-06-17 15:20:48.608527+00	773	2025-06-17 15:20:48.608527+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-12	12:00:00	11:30:00	0	0	2025-06-17 15:20:48.651585+00	774	2025-06-17 15:20:48.651585+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-05	15:00:00	14:30:00	0	0	2025-06-17 15:20:48.68106+00	775	2025-06-17 15:20:48.68106+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ff63-73de-9441-b73d75415356	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-08	10:30:00	10:00:00	0	1	2025-06-17 15:20:48.725438+00	776	2025-06-17 15:20:48.725438+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-14	10:00:00	09:30:00	0	0	2025-06-17 15:20:48.744413+00	777	2025-06-17 15:20:48.744413+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-04	17:00:00	16:30:00	0	1	2025-06-17 15:20:48.764597+00	778	2025-06-17 15:20:48.764597+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-15	23:00:00	22:30:00	0	1	2025-06-17 15:20:48.784432+00	779	2025-06-17 15:20:48.784432+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-13	18:00:00	17:30:00	0	1	2025-06-17 15:20:48.819781+00	780	2025-06-17 15:20:48.819781+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-15	11:30:00	11:00:00	0	0	2025-06-17 15:20:48.891313+00	781	2025-06-17 15:20:48.891313+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-04	18:00:00	17:30:00	0	0	2025-06-17 15:20:48.918938+00	782	2025-06-17 15:20:48.918938+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-10	16:00:00	15:30:00	0	1	2025-06-17 15:20:48.937874+00	783	2025-06-17 15:20:48.937874+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Висипання на шкірі, діарея або запори, блювання.
2025-07-08	21:30:00	21:00:00	0	0	2025-06-17 15:20:48.991695+00	784	2025-06-17 15:20:48.991695+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-10	15:30:00	15:00:00	0	0	2025-06-17 15:20:49.019993+00	785	2025-06-17 15:20:49.019993+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Запаморочення, порушення координації рухів, падіння без видимої причини.
2025-07-14	13:00:00	12:30:00	0	0	2025-06-17 15:20:49.060726+00	786	2025-06-17 15:20:49.060726+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Зміни кольору і кількості сечі, набряки кінцівок, особливо вранці.
2025-07-11	12:00:00	11:30:00	0	1	2025-06-17 15:20:49.099432+00	787	2025-06-17 15:20:49.099432+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-05	13:30:00	13:00:00	0	0	2025-06-17 15:20:49.150088+00	788	2025-06-17 15:20:49.150088+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-07	08:30:00	08:00:00	0	1	2025-06-17 15:20:49.210869+00	789	2025-06-17 15:20:49.210869+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-14	17:30:00	17:00:00	0	0	2025-06-17 15:20:49.23213+00	790	2025-06-17 15:20:49.23213+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-14	21:30:00	21:00:00	0	0	2025-06-17 15:20:49.252127+00	791	2025-06-17 15:20:49.252127+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-14	16:30:00	16:00:00	0	1	2025-06-17 15:20:49.284233+00	792	2025-06-17 15:20:49.284233+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-04	20:30:00	20:00:00	0	0	2025-06-17 15:20:49.353083+00	793	2025-06-17 15:20:49.353083+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-11	20:00:00	19:30:00	0	0	2025-06-17 15:20:49.370657+00	794	2025-06-17 15:20:49.370657+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-15	13:00:00	12:30:00	0	0	2025-06-17 15:20:49.387448+00	795	2025-06-17 15:20:49.387448+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у горлі, що не минає понад тиждень, труднощі при ковтанні.
2025-07-05	16:30:00	16:00:00	0	1	2025-06-17 15:20:49.431073+00	796	2025-06-17 15:20:49.431073+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f469-7573-ba3a-604cd272778f	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-05	20:00:00	19:30:00	0	1	2025-06-17 15:20:49.44811+00	797	2025-06-17 15:20:49.44811+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-07	11:00:00	10:30:00	0	1	2025-06-17 15:20:49.478088+00	799	2025-06-17 15:20:49.478088+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-07	22:30:00	22:00:00	0	0	2025-06-17 15:20:49.507711+00	800	2025-06-17 15:20:49.507711+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-12	17:00:00	16:30:00	0	0	2025-06-17 15:20:49.526964+00	801	2025-06-17 15:20:49.526964+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-12	16:00:00	15:30:00	0	0	2025-06-17 15:20:49.548747+00	802	2025-06-17 15:20:49.548747+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-08	20:00:00	19:30:00	0	1	2025-06-17 15:20:49.565583+00	803	2025-06-17 15:20:49.565583+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-11	14:00:00	13:30:00	0	1	2025-06-17 15:20:49.588991+00	804	2025-06-17 15:20:49.588991+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Потреба в індивідуальному підборі навантажень після хвороби або травми.
2025-07-10	13:30:00	13:00:00	0	1	2025-06-17 15:20:49.607282+00	805	2025-06-17 15:20:49.607282+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичне оніміння кінцівок, поколювання, слабкість м’язів.
2025-07-14	20:30:00	20:00:00	0	0	2025-06-17 15:20:49.631881+00	806	2025-06-17 15:20:49.631881+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-07	20:00:00	19:30:00	0	1	2025-06-17 15:20:49.659474+00	807	2025-06-17 15:20:49.659474+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-11	21:00:00	20:30:00	0	0	2025-06-17 15:20:49.679923+00	808	2025-06-17 15:20:49.679923+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-06-19	10:30:00	10:00:00	0	1	2025-06-17 15:20:49.711758+00	809	2025-06-17 15:20:49.711758+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-07	12:00:00	11:30:00	0	1	2025-06-17 15:20:49.72571+00	810	2025-06-17 15:20:49.72571+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-03	14:00:00	13:30:00	0	1	2025-06-17 15:20:49.765274+00	811	2025-06-17 15:20:49.765274+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-11	18:00:00	17:30:00	0	0	2025-06-17 15:20:49.785217+00	812	2025-06-17 15:20:49.785217+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-09	16:30:00	16:00:00	0	0	2025-06-17 15:20:49.802805+00	813	2025-06-17 15:20:49.802805+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-13	10:30:00	10:00:00	0	0	2025-06-17 15:20:49.829032+00	814	2025-06-17 15:20:49.829032+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-15	17:30:00	17:00:00	0	1	2025-06-17 15:20:49.852384+00	815	2025-06-17 15:20:49.852384+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-10	17:00:00	16:30:00	0	1	2025-06-17 15:20:49.868508+00	816	2025-06-17 15:20:49.868508+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-10	18:00:00	17:30:00	0	0	2025-06-17 15:20:49.883698+00	817	2025-06-17 15:20:49.883698+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-05	21:00:00	20:30:00	0	1	2025-06-17 15:20:49.898806+00	818	2025-06-17 15:20:49.898806+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	17:30:00	17:00:00	0	0	2025-06-17 15:20:49.920617+00	819	2025-06-17 15:20:49.920617+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-13	23:00:00	22:30:00	0	1	2025-06-17 15:20:49.936768+00	820	2025-06-17 15:20:49.936768+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-11	12:30:00	12:00:00	0	0	2025-06-17 15:20:49.996196+00	822	2025-06-17 15:20:49.996196+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-10	20:00:00	19:30:00	0	0	2025-06-17 15:20:50.031663+00	823	2025-06-17 15:20:50.031663+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Висипання на шкірі, діарея або запори, блювання.
2025-07-07	19:30:00	19:00:00	0	1	2025-06-17 15:20:50.093961+00	824	2025-06-17 15:20:50.093961+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-04	15:30:00	15:00:00	0	0	2025-06-17 15:20:50.116718+00	825	2025-06-17 15:20:50.116718+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-13	19:00:00	18:30:00	0	1	2025-06-17 15:20:50.148186+00	826	2025-06-17 15:20:50.148186+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-10	18:00:00	17:30:00	0	0	2025-06-17 15:20:50.165193+00	827	2025-06-17 15:20:50.165193+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-07	08:30:00	08:00:00	0	0	2025-06-17 15:20:50.199545+00	828	2025-06-17 15:20:50.199545+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-10	14:30:00	14:00:00	0	0	2025-06-17 15:20:50.217458+00	829	2025-06-17 15:20:50.217458+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-14	18:00:00	17:30:00	0	0	2025-06-17 15:20:50.235227+00	830	2025-06-17 15:20:50.235227+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-09	22:30:00	22:00:00	0	0	2025-06-17 15:20:50.260154+00	831	2025-06-17 15:20:50.260154+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-0026-79ac-a236-50d54f042a62	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-09	16:30:00	16:00:00	0	1	2025-06-17 15:20:50.276836+00	832	2025-06-17 15:20:50.276836+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-08	10:00:00	09:30:00	0	1	2025-06-17 15:20:50.291306+00	833	2025-06-17 15:20:50.291306+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-19	11:30:00	11:00:00	0	0	2025-06-17 15:20:50.330165+00	834	2025-06-17 15:20:50.330165+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-09	17:00:00	16:30:00	0	1	2025-06-17 15:20:50.362038+00	835	2025-06-17 15:20:50.362038+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-12	22:00:00	21:30:00	0	1	2025-06-17 15:20:50.389828+00	836	2025-06-17 15:20:50.389828+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-14	18:30:00	18:00:00	0	1	2025-06-17 15:20:50.407161+00	837	2025-06-17 15:20:50.407161+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-06-26	11:00:00	10:30:00	0	0	2025-06-17 15:20:50.437141+00	838	2025-06-17 15:20:50.437141+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-07	18:30:00	18:00:00	0	1	2025-06-17 15:20:50.470993+00	839	2025-06-17 15:20:50.470993+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-11	22:30:00	22:00:00	0	1	2025-06-17 15:20:50.492691+00	840	2025-06-17 15:20:50.492691+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-05	13:00:00	12:30:00	0	0	2025-06-17 15:20:50.540437+00	841	2025-06-17 15:20:50.540437+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Синдром перевтоми: втрата витривалості, безсоння після тренувань.
2025-07-07	16:30:00	16:00:00	0	0	2025-06-17 15:20:50.575217+00	842	2025-06-17 15:20:50.575217+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Загальна слабкість, підвищена втомлюваність навіть після невеликих навантажень.
2025-07-12	14:30:00	14:00:00	0	1	2025-06-17 15:20:50.592842+00	843	2025-06-17 15:20:50.592842+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Потреба у відновленні після перенесеного інсульту або травми.
2025-07-08	08:30:00	08:00:00	0	1	2025-06-17 15:20:50.629879+00	844	2025-06-17 15:20:50.629879+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-14	15:00:00	14:30:00	0	1	2025-06-17 15:20:50.647154+00	845	2025-06-17 15:20:50.647154+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-06	09:30:00	09:00:00	0	1	2025-06-17 15:20:50.6726+00	846	2025-06-17 15:20:50.6726+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-04	17:00:00	16:30:00	0	0	2025-06-17 15:20:50.689235+00	847	2025-06-17 15:20:50.689235+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-06-19	12:30:00	12:00:00	0	0	2025-06-17 15:20:50.74038+00	848	2025-06-17 15:20:50.74038+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-08	19:30:00	19:00:00	0	1	2025-06-17 15:20:50.786029+00	849	2025-06-17 15:20:50.786029+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-08	12:00:00	11:30:00	0	0	2025-06-17 15:20:50.811666+00	850	2025-06-17 15:20:50.811666+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-0026-79ac-a236-50d54f042a62	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-12	18:00:00	17:30:00	0	0	2025-06-17 15:20:50.829553+00	851	2025-06-17 15:20:50.829553+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-13	17:00:00	16:30:00	0	0	2025-06-17 15:20:50.849683+00	852	2025-06-17 15:20:50.849683+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff63-73de-9441-b73d75415356	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-04	18:30:00	18:00:00	0	1	2025-06-17 15:20:50.91333+00	853	2025-06-17 15:20:50.91333+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-09	12:30:00	12:00:00	0	0	2025-06-17 15:20:50.941983+00	854	2025-06-17 15:20:50.941983+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f469-7573-ba3a-604cd272778f	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-05	12:30:00	12:00:00	0	0	2025-06-17 15:20:50.965323+00	855	2025-06-17 15:20:50.965323+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-10	12:30:00	12:00:00	0	1	2025-06-17 15:20:50.990713+00	856	2025-06-17 15:20:50.990713+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Зміни настрою, дратівливість, безпричинне підвищення пітливості.
2025-07-06	15:00:00	14:30:00	0	1	2025-06-17 15:20:51.007973+00	857	2025-06-17 15:20:51.007973+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-07	09:30:00	09:00:00	0	1	2025-06-17 15:20:51.035917+00	858	2025-06-17 15:20:51.035917+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-03	13:00:00	12:30:00	0	0	2025-06-17 15:20:51.054558+00	859	2025-06-17 15:20:51.054558+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-11	16:30:00	16:00:00	0	0	2025-06-17 15:20:51.073822+00	860	2025-06-17 15:20:51.073822+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-07	08:30:00	08:00:00	0	1	2025-06-17 15:20:51.089503+00	861	2025-06-17 15:20:51.089503+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-06	11:30:00	11:00:00	0	1	2025-06-17 15:20:51.126829+00	862	2025-06-17 15:20:51.126829+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-06	12:30:00	12:00:00	0	0	2025-06-17 15:20:51.140039+00	863	2025-06-17 15:20:51.140039+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-08	10:00:00	09:30:00	0	1	2025-06-17 15:20:51.152421+00	864	2025-06-17 15:20:51.152421+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-11	22:00:00	21:30:00	0	1	2025-06-17 15:20:51.196097+00	866	2025-06-17 15:20:51.196097+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-06-20	13:30:00	13:00:00	0	1	2025-06-17 15:20:51.23492+00	867	2025-06-17 15:20:51.23492+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ff63-73de-9441-b73d75415356	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-14	12:30:00	12:00:00	0	0	2025-06-17 15:20:51.252731+00	868	2025-06-17 15:20:51.252731+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-12	14:30:00	14:00:00	0	1	2025-06-17 15:20:51.357826+00	870	2025-06-17 15:20:51.357826+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Підвищена температура тіла без виявленої інфекції або запального процесу.
2025-07-08	21:30:00	21:00:00	0	1	2025-06-17 15:20:51.383477+00	871	2025-06-17 15:20:51.383477+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-07	18:00:00	17:30:00	0	0	2025-06-17 15:20:51.461174+00	872	2025-06-17 15:20:51.461174+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Психоемоційне вигорання, втрата мотивації до роботи та особистого розвитку.
2025-07-13	11:00:00	10:30:00	0	0	2025-06-17 15:20:51.494245+00	873	2025-06-17 15:20:51.494245+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-06	12:30:00	12:00:00	0	0	2025-06-17 15:20:51.509351+00	874	2025-06-17 15:20:51.509351+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-04	16:30:00	16:00:00	0	0	2025-06-17 15:20:51.544308+00	876	2025-06-17 15:20:51.544308+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-14	11:00:00	10:30:00	0	1	2025-06-17 15:20:51.57423+00	877	2025-06-17 15:20:51.57423+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-06-20	22:00:00	21:30:00	0	1	2025-06-17 15:20:51.604607+00	878	2025-06-17 15:20:51.604607+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-21	11:30:00	11:00:00	0	1	2025-06-17 15:20:51.652104+00	879	2025-06-17 15:20:51.652104+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-06-20	12:30:00	12:00:00	0	0	2025-06-17 15:20:51.685432+00	880	2025-06-17 15:20:51.685432+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-09	12:00:00	11:30:00	2	0	2025-06-17 15:20:51.175658+00	865	2025-06-17 15:20:51.175658+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-05	16:00:00	15:30:00	0	0	2025-06-17 15:20:51.703413+00	881	2025-06-17 15:20:51.703413+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-07	16:30:00	16:00:00	0	0	2025-06-17 15:20:51.746379+00	882	2025-06-17 15:20:51.746379+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-03	15:00:00	14:30:00	0	1	2025-06-17 15:20:51.760359+00	883	2025-06-17 15:20:51.760359+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ff63-73de-9441-b73d75415356	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-04	14:00:00	13:30:00	0	1	2025-06-17 15:20:51.784181+00	884	2025-06-17 15:20:51.784181+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-08	22:00:00	21:30:00	0	1	2025-06-17 15:20:51.850433+00	885	2025-06-17 15:20:51.850433+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-12	10:00:00	09:30:00	0	1	2025-06-17 15:20:51.8673+00	886	2025-06-17 15:20:51.8673+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-13	08:30:00	08:00:00	0	1	2025-06-17 15:20:51.913267+00	887	2025-06-17 15:20:51.913267+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-04	14:30:00	14:00:00	0	0	2025-06-17 15:20:51.928016+00	888	2025-06-17 15:20:51.928016+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-12	19:00:00	18:30:00	0	0	2025-06-17 15:20:51.944084+00	889	2025-06-17 15:20:51.944084+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-07-08	11:00:00	10:30:00	0	0	2025-06-17 15:20:51.957912+00	890	2025-06-17 15:20:51.957912+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-07	17:30:00	17:00:00	0	1	2025-06-17 15:20:51.97193+00	891	2025-06-17 15:20:51.97193+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	19:00:00	18:30:00	0	1	2025-06-17 15:20:51.988601+00	892	2025-06-17 15:20:51.988601+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-14	10:30:00	10:00:00	0	1	2025-06-17 15:20:52.02431+00	893	2025-06-17 15:20:52.02431+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-08	22:30:00	22:00:00	0	1	2025-06-17 15:20:52.058231+00	894	2025-06-17 15:20:52.058231+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:52.075672+00	895	2025-06-17 15:20:52.075672+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-12	20:30:00	20:00:00	0	1	2025-06-17 15:20:52.092022+00	896	2025-06-17 15:20:52.092022+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-05	08:30:00	08:00:00	0	1	2025-06-17 15:20:52.148402+00	897	2025-06-17 15:20:52.148402+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-04	15:30:00	15:00:00	0	1	2025-06-17 15:20:52.171464+00	898	2025-06-17 15:20:52.171464+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-21	10:00:00	09:30:00	0	1	2025-06-17 15:20:52.200457+00	899	2025-06-17 15:20:52.200457+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-14	14:00:00	13:30:00	0	0	2025-06-17 15:20:52.263822+00	900	2025-06-17 15:20:52.263822+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-19	11:00:00	10:30:00	0	1	2025-06-17 15:20:52.311159+00	901	2025-06-17 15:20:52.311159+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-11	20:30:00	20:00:00	0	0	2025-06-17 15:20:52.333752+00	902	2025-06-17 15:20:52.333752+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-03	17:30:00	17:00:00	0	0	2025-06-17 15:20:52.357927+00	903	2025-06-17 15:20:52.357927+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-ff63-73de-9441-b73d75415356	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-15	16:00:00	15:30:00	0	0	2025-06-17 15:20:52.396022+00	904	2025-06-17 15:20:52.396022+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-12	12:00:00	11:30:00	0	0	2025-06-17 15:20:52.427344+00	905	2025-06-17 15:20:52.427344+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-12	08:30:00	08:00:00	0	1	2025-06-17 15:20:52.459924+00	906	2025-06-17 15:20:52.459924+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-13	20:00:00	19:30:00	0	0	2025-06-17 15:20:52.482685+00	907	2025-06-17 15:20:52.482685+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-11	18:30:00	18:00:00	0	0	2025-06-17 15:20:52.545939+00	908	2025-06-17 15:20:52.545939+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-11	22:30:00	22:00:00	0	1	2025-06-17 15:20:52.563023+00	909	2025-06-17 15:20:52.563023+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-04	08:30:00	08:00:00	0	0	2025-06-17 15:20:52.579194+00	910	2025-06-17 15:20:52.579194+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-12	12:30:00	12:00:00	0	1	2025-06-17 15:20:52.697725+00	913	2025-06-17 15:20:52.697725+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-15	15:30:00	15:00:00	0	0	2025-06-17 15:20:52.720244+00	914	2025-06-17 15:20:52.720244+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-14	15:30:00	15:00:00	0	1	2025-06-17 15:20:52.742174+00	915	2025-06-17 15:20:52.742174+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-07	14:00:00	13:30:00	0	0	2025-06-17 15:20:52.771073+00	916	2025-06-17 15:20:52.771073+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-09	17:30:00	17:00:00	0	1	2025-06-17 15:20:52.804575+00	917	2025-06-17 15:20:52.804575+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-0026-79ac-a236-50d54f042a62	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-11	16:00:00	15:30:00	0	0	2025-06-17 15:20:52.83348+00	918	2025-06-17 15:20:52.83348+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-10	14:30:00	14:00:00	0	1	2025-06-17 15:20:52.858083+00	919	2025-06-17 15:20:52.858083+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-14	16:30:00	16:00:00	0	1	2025-06-17 15:20:52.894282+00	920	2025-06-17 15:20:52.894282+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-07	17:30:00	17:00:00	0	1	2025-06-17 15:20:52.953033+00	921	2025-06-17 15:20:52.953033+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-14	18:30:00	18:00:00	0	0	2025-06-17 15:20:52.971036+00	922	2025-06-17 15:20:52.971036+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-05	19:00:00	18:30:00	0	1	2025-06-17 15:20:53.013397+00	923	2025-06-17 15:20:53.013397+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-06-20	08:30:00	08:00:00	0	1	2025-06-17 15:20:53.043173+00	924	2025-06-17 15:20:53.043173+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичний біль у попереку, що посилюється при фізичному навантаженні.
2025-07-07	11:00:00	10:30:00	0	0	2025-06-17 15:20:53.062063+00	925	2025-06-17 15:20:53.062063+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-05	19:00:00	18:30:00	0	1	2025-06-17 15:20:53.089744+00	926	2025-06-17 15:20:53.089744+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	13:00:00	12:30:00	0	0	2025-06-17 15:20:53.121413+00	927	2025-06-17 15:20:53.121413+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-03	09:30:00	09:00:00	0	0	2025-06-17 15:20:53.142892+00	928	2025-06-17 15:20:53.142892+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-08	19:00:00	18:30:00	0	1	2025-06-17 15:20:53.166007+00	929	2025-06-17 15:20:53.166007+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-14	17:00:00	16:30:00	0	0	2025-06-17 15:20:53.193608+00	930	2025-06-17 15:20:53.193608+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-04	10:30:00	10:00:00	0	0	2025-06-17 15:20:53.228074+00	932	2025-06-17 15:20:53.228074+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-10	13:00:00	12:30:00	0	0	2025-06-17 15:20:53.245389+00	933	2025-06-17 15:20:53.245389+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-06-24	10:00:00	09:30:00	0	1	2025-06-17 15:20:53.275046+00	934	2025-06-17 15:20:53.275046+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Підвищена температура тіла без виявленої інфекції або запального процесу.
2025-07-03	21:00:00	20:30:00	0	0	2025-06-17 15:20:53.300068+00	935	2025-06-17 15:20:53.300068+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-06	16:30:00	16:00:00	0	0	2025-06-17 15:20:53.318941+00	936	2025-06-17 15:20:53.318941+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-13	09:30:00	09:00:00	0	0	2025-06-17 15:20:53.340512+00	937	2025-06-17 15:20:53.340512+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-09	20:00:00	19:30:00	0	0	2025-06-17 15:20:53.355181+00	938	2025-06-17 15:20:53.355181+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-10	14:30:00	14:00:00	0	0	2025-06-17 15:20:53.377985+00	939	2025-06-17 15:20:53.377985+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-07	11:30:00	11:00:00	0	0	2025-06-17 15:20:53.420446+00	940	2025-06-17 15:20:53.420446+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-13	10:30:00	10:00:00	0	1	2025-06-17 15:20:53.496198+00	941	2025-06-17 15:20:53.496198+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-08	16:00:00	15:30:00	0	0	2025-06-17 15:20:53.566369+00	942	2025-06-17 15:20:53.566369+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-10	12:30:00	12:00:00	0	1	2025-06-17 15:20:53.636499+00	945	2025-06-17 15:20:53.636499+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-07	13:30:00	13:00:00	0	0	2025-06-17 15:20:53.654105+00	946	2025-06-17 15:20:53.654105+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-07	12:30:00	12:00:00	0	0	2025-06-17 15:20:53.684849+00	947	2025-06-17 15:20:53.684849+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-07	19:00:00	18:30:00	0	0	2025-06-17 15:20:53.704479+00	948	2025-06-17 15:20:53.704479+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-06-20	16:00:00	15:30:00	0	1	2025-06-17 15:20:53.765493+00	950	2025-06-17 15:20:53.765493+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-06-19	13:00:00	12:30:00	0	1	2025-06-17 15:20:53.791487+00	951	2025-06-17 15:20:53.791487+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-06	10:00:00	09:30:00	0	0	2025-06-17 15:20:53.827651+00	952	2025-06-17 15:20:53.827651+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-09	08:30:00	08:00:00	0	1	2025-06-17 15:20:53.852916+00	953	2025-06-17 15:20:53.852916+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-08	14:00:00	13:30:00	0	1	2025-06-17 15:20:53.911491+00	954	2025-06-17 15:20:53.911491+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-11	19:30:00	19:00:00	0	0	2025-06-17 15:20:53.946524+00	955	2025-06-17 15:20:53.946524+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-09	11:00:00	10:30:00	0	1	2025-06-17 15:20:53.967153+00	956	2025-06-17 15:20:53.967153+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-03	16:00:00	15:30:00	0	1	2025-06-17 15:20:53.988912+00	957	2025-06-17 15:20:53.988912+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-06	11:00:00	10:30:00	0	1	2025-06-17 15:20:54.004514+00	958	2025-06-17 15:20:54.004514+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-03	17:00:00	16:30:00	0	0	2025-06-17 15:20:54.022415+00	959	2025-06-17 15:20:54.022415+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-12	09:30:00	09:00:00	0	0	2025-06-17 15:20:54.040045+00	960	2025-06-17 15:20:54.040045+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-10	12:00:00	11:30:00	0	1	2025-06-17 15:20:54.062401+00	961	2025-06-17 15:20:54.062401+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f603-7282-bc16-c98712d0e44d	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-14	20:00:00	19:30:00	0	0	2025-06-17 15:20:54.079514+00	962	2025-06-17 15:20:54.079514+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-14	23:00:00	22:30:00	0	0	2025-06-17 15:20:54.107146+00	963	2025-06-17 15:20:54.107146+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-11	13:00:00	12:30:00	0	0	2025-06-17 15:20:54.124282+00	964	2025-06-17 15:20:54.124282+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-04	20:00:00	19:30:00	0	1	2025-06-17 15:20:54.141516+00	965	2025-06-17 15:20:54.141516+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-14	09:30:00	09:00:00	0	1	2025-06-17 15:20:54.158536+00	966	2025-06-17 15:20:54.158536+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-06-19	12:00:00	11:30:00	0	0	2025-06-17 15:20:54.186372+00	967	2025-06-17 15:20:54.186372+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-04	13:30:00	13:00:00	0	0	2025-06-17 15:20:54.202753+00	968	2025-06-17 15:20:54.202753+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-07	10:00:00	09:30:00	0	1	2025-06-17 15:20:54.221196+00	969	2025-06-17 15:20:54.221196+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:54.2402+00	970	2025-06-17 15:20:54.2402+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-15	19:30:00	19:00:00	0	1	2025-06-17 15:20:54.261471+00	971	2025-06-17 15:20:54.261471+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-07	08:30:00	08:00:00	0	0	2025-06-17 15:20:54.297298+00	972	2025-06-17 15:20:54.297298+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:20:54.456326+00	973	2025-06-17 15:20:54.456326+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-04	20:30:00	20:00:00	0	0	2025-06-17 15:20:54.535855+00	974	2025-06-17 15:20:54.535855+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-10	10:30:00	10:00:00	0	0	2025-06-17 15:20:54.582556+00	975	2025-06-17 15:20:54.582556+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-05	21:30:00	21:00:00	0	0	2025-06-17 15:20:54.735159+00	977	2025-06-17 15:20:54.735159+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Психоемоційне вигорання, втрата мотивації до роботи та особистого розвитку.
2025-07-13	14:30:00	14:00:00	0	1	2025-06-17 15:20:54.841574+00	978	2025-06-17 15:20:54.841574+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-13	20:30:00	20:00:00	0	0	2025-06-17 15:20:54.864438+00	979	2025-06-17 15:20:54.864438+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-12	16:30:00	16:00:00	0	0	2025-06-17 15:20:54.896091+00	980	2025-06-17 15:20:54.896091+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Постійне відчуття тривоги, напади паніки без видимої причини.
2025-07-11	10:30:00	10:00:00	0	1	2025-06-17 15:20:54.94591+00	981	2025-06-17 15:20:54.94591+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-04	14:00:00	13:30:00	0	0	2025-06-17 15:20:54.967973+00	982	2025-06-17 15:20:54.967973+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-11	10:30:00	10:00:00	0	0	2025-06-17 15:20:54.982686+00	983	2025-06-17 15:20:54.982686+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-04	16:30:00	16:00:00	0	0	2025-06-17 15:20:54.998215+00	984	2025-06-17 15:20:54.998215+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-03	14:00:00	13:30:00	0	1	2025-06-17 15:20:55.013057+00	985	2025-06-17 15:20:55.013057+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ff63-73de-9441-b73d75415356	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-14	19:30:00	19:00:00	0	0	2025-06-17 15:20:55.066376+00	986	2025-06-17 15:20:55.066376+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-09	20:00:00	19:30:00	0	1	2025-06-17 15:20:55.084114+00	987	2025-06-17 15:20:55.084114+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:55.104044+00	988	2025-06-17 15:20:55.104044+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-04	13:30:00	13:00:00	0	1	2025-06-17 15:20:55.122812+00	989	2025-06-17 15:20:55.122812+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-05	13:00:00	12:30:00	0	1	2025-06-17 15:20:55.152773+00	990	2025-06-17 15:20:55.152773+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-04	08:30:00	08:00:00	0	0	2025-06-17 15:20:55.174801+00	991	2025-06-17 15:20:55.174801+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:55.255603+00	992	2025-06-17 15:20:55.255603+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-10	12:00:00	11:30:00	0	1	2025-06-17 15:20:55.306771+00	993	2025-06-17 15:20:55.306771+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-07	18:00:00	17:30:00	0	0	2025-06-17 15:20:55.324835+00	994	2025-06-17 15:20:55.324835+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-11	10:30:00	10:00:00	0	0	2025-06-17 15:20:55.364877+00	995	2025-06-17 15:20:55.364877+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-07	13:00:00	12:30:00	0	0	2025-06-17 15:20:55.379745+00	996	2025-06-17 15:20:55.379745+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-09	19:00:00	18:30:00	0	0	2025-06-17 15:20:55.394287+00	997	2025-06-17 15:20:55.394287+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-15	11:00:00	10:30:00	0	0	2025-06-17 15:20:55.410581+00	998	2025-06-17 15:20:55.410581+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-06-20	11:00:00	10:30:00	0	0	2025-06-17 15:20:55.440791+00	999	2025-06-17 15:20:55.440791+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-06	08:30:00	08:00:00	0	1	2025-06-17 15:20:55.476627+00	1000	2025-06-17 15:20:55.476627+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-10	10:00:00	09:30:00	0	1	2025-06-17 15:20:55.49407+00	1001	2025-06-17 15:20:55.49407+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-09	11:00:00	10:30:00	0	0	2025-06-17 15:20:55.521903+00	1002	2025-06-17 15:20:55.521903+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-20	09:30:00	09:00:00	0	1	2025-06-17 15:20:55.550909+00	1003	2025-06-17 15:20:55.550909+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-09	18:30:00	18:00:00	0	1	2025-06-17 15:20:55.579265+00	1004	2025-06-17 15:20:55.579265+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-06-19	12:30:00	12:00:00	0	0	2025-06-17 15:20:55.610431+00	1005	2025-06-17 15:20:55.610431+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-06-21	11:00:00	10:30:00	0	1	2025-06-17 15:20:55.650332+00	1006	2025-06-17 15:20:55.650332+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-06	15:00:00	14:30:00	0	1	2025-06-17 15:20:55.668208+00	1007	2025-06-17 15:20:55.668208+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-03	11:30:00	11:00:00	0	1	2025-06-17 15:20:55.686062+00	1008	2025-06-17 15:20:55.686062+00	01977e79-f1a4-7811-840e-404568a4376e	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-08	18:00:00	17:30:00	0	1	2025-06-17 15:20:55.719626+00	1009	2025-06-17 15:20:55.719626+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Затримка розвитку, відставання у вазі та зрості.
2025-07-04	21:30:00	21:00:00	0	0	2025-06-17 15:20:55.735384+00	1010	2025-06-17 15:20:55.735384+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Потреба в індивідуальному підборі навантажень після хвороби або травми.
2025-07-15	17:00:00	16:30:00	0	1	2025-06-17 15:20:55.77033+00	1011	2025-06-17 15:20:55.77033+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-03	18:30:00	18:00:00	0	0	2025-06-17 15:20:55.785966+00	1012	2025-06-17 15:20:55.785966+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f603-7282-bc16-c98712d0e44d	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-14	17:30:00	17:00:00	0	1	2025-06-17 15:20:55.802202+00	1013	2025-06-17 15:20:55.802202+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-06	12:30:00	12:00:00	0	1	2025-06-17 15:20:55.81749+00	1014	2025-06-17 15:20:55.81749+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-13	15:00:00	14:30:00	0	0	2025-06-17 15:20:55.835446+00	1015	2025-06-17 15:20:55.835446+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-15	13:30:00	13:00:00	0	1	2025-06-17 15:20:55.886486+00	1016	2025-06-17 15:20:55.886486+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-11	17:30:00	17:00:00	0	1	2025-06-17 15:20:55.931068+00	1017	2025-06-17 15:20:55.931068+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-14	17:00:00	16:30:00	0	0	2025-06-17 15:20:55.955815+00	1018	2025-06-17 15:20:55.955815+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-05	20:30:00	20:00:00	0	1	2025-06-17 15:20:55.979285+00	1019	2025-06-17 15:20:55.979285+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-15	13:30:00	13:00:00	0	1	2025-06-17 15:20:56.010963+00	1020	2025-06-17 15:20:56.010963+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-08	19:00:00	18:30:00	0	0	2025-06-17 15:20:56.064034+00	1021	2025-06-17 15:20:56.064034+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-12	12:30:00	12:00:00	0	0	2025-06-17 15:20:56.092936+00	1022	2025-06-17 15:20:56.092936+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-15	15:00:00	14:30:00	0	0	2025-06-17 15:20:56.178401+00	1023	2025-06-17 15:20:56.178401+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-03	20:00:00	19:30:00	0	0	2025-06-17 15:20:56.201239+00	1024	2025-06-17 15:20:56.201239+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f469-7573-ba3a-604cd272778f	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-13	16:30:00	16:00:00	0	0	2025-06-17 15:20:56.242208+00	1025	2025-06-17 15:20:56.242208+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-09	19:00:00	18:30:00	0	1	2025-06-17 15:20:56.269849+00	1026	2025-06-17 15:20:56.269849+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-11	17:00:00	16:30:00	0	0	2025-06-17 15:20:56.310261+00	1027	2025-06-17 15:20:56.310261+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-12	11:30:00	11:00:00	0	1	2025-06-17 15:20:56.362971+00	1029	2025-06-17 15:20:56.362971+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-13	10:00:00	09:30:00	0	0	2025-06-17 15:20:56.37887+00	1030	2025-06-17 15:20:56.37887+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-09	14:00:00	13:30:00	0	1	2025-06-17 15:20:56.409415+00	1031	2025-06-17 15:20:56.409415+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f469-7573-ba3a-604cd272778f	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-04	19:30:00	19:00:00	0	1	2025-06-17 15:20:56.425302+00	1032	2025-06-17 15:20:56.425302+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-11	23:00:00	22:30:00	0	0	2025-06-17 15:20:56.476267+00	1034	2025-06-17 15:20:56.476267+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-03	11:00:00	10:30:00	0	0	2025-06-17 15:20:56.498324+00	1035	2025-06-17 15:20:56.498324+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-04	15:00:00	14:30:00	0	0	2025-06-17 15:20:56.519758+00	1036	2025-06-17 15:20:56.519758+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-10	22:30:00	22:00:00	0	1	2025-06-17 15:20:56.571598+00	1037	2025-06-17 15:20:56.571598+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-08	09:30:00	09:00:00	0	1	2025-06-17 15:20:56.588127+00	1038	2025-06-17 15:20:56.588127+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-0026-79ac-a236-50d54f042a62	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-03	10:00:00	09:30:00	0	1	2025-06-17 15:20:56.624202+00	1039	2025-06-17 15:20:56.624202+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-07	12:00:00	11:30:00	0	1	2025-06-17 15:20:56.650554+00	1040	2025-06-17 15:20:56.650554+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-06	14:30:00	14:00:00	0	1	2025-06-17 15:20:56.668555+00	1041	2025-06-17 15:20:56.668555+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-07	11:00:00	10:30:00	0	1	2025-06-17 15:20:56.687753+00	1042	2025-06-17 15:20:56.687753+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-06	19:00:00	18:30:00	0	1	2025-06-17 15:20:56.70968+00	1043	2025-06-17 15:20:56.70968+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-06	08:30:00	08:00:00	0	0	2025-06-17 15:20:56.743126+00	1044	2025-06-17 15:20:56.743126+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-08	08:30:00	08:00:00	0	0	2025-06-17 15:20:56.759993+00	1045	2025-06-17 15:20:56.759993+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-0026-79ac-a236-50d54f042a62	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-04	10:30:00	10:00:00	0	0	2025-06-17 15:20:56.792045+00	1046	2025-06-17 15:20:56.792045+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f469-7573-ba3a-604cd272778f	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-04	11:00:00	10:30:00	0	1	2025-06-17 15:20:56.818361+00	1047	2025-06-17 15:20:56.818361+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Затримка розвитку, відставання у вазі та зрості.
2025-07-06	20:00:00	19:30:00	0	1	2025-06-17 15:20:56.835994+00	1048	2025-06-17 15:20:56.835994+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-06	14:00:00	13:30:00	0	1	2025-06-17 15:20:56.892432+00	1050	2025-06-17 15:20:56.892432+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-05	14:30:00	14:00:00	0	1	2025-06-17 15:20:56.912989+00	1051	2025-06-17 15:20:56.912989+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-04	21:30:00	21:00:00	0	1	2025-06-17 15:20:56.986973+00	1052	2025-06-17 15:20:56.986973+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Схильність до алергічних реакцій та аутоімунних процесів.
2025-07-09	21:00:00	20:30:00	0	1	2025-06-17 15:20:57.002631+00	1053	2025-06-17 15:20:57.002631+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-10	18:30:00	18:00:00	0	1	2025-06-17 15:20:57.016173+00	1054	2025-06-17 15:20:57.016173+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-07	08:30:00	08:00:00	0	1	2025-06-17 15:20:57.03204+00	1055	2025-06-17 15:20:57.03204+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-11	23:30:00	23:00:00	0	1	2025-06-17 15:20:57.058206+00	1056	2025-06-17 15:20:57.058206+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-05	00:00:00	23:30:00	0	0	2025-06-17 15:20:57.106088+00	1058	2025-06-17 15:20:57.106088+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-14	19:00:00	18:30:00	0	0	2025-06-17 15:20:57.125225+00	1059	2025-06-17 15:20:57.125225+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-0026-79ac-a236-50d54f042a62	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-12	08:30:00	08:00:00	0	1	2025-06-17 15:20:57.169804+00	1060	2025-06-17 15:20:57.169804+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-04	14:00:00	13:30:00	0	1	2025-06-17 15:20:57.191539+00	1061	2025-06-17 15:20:57.191539+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-15	10:00:00	09:30:00	0	1	2025-06-17 15:20:57.206228+00	1062	2025-06-17 15:20:57.206228+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-09	15:30:00	15:00:00	0	1	2025-06-17 15:20:57.221508+00	1063	2025-06-17 15:20:57.221508+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-0026-79ac-a236-50d54f042a62	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-15	10:00:00	09:30:00	0	1	2025-06-17 15:20:57.244585+00	1064	2025-06-17 15:20:57.244585+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-11	12:30:00	12:00:00	0	1	2025-06-17 15:20:57.297109+00	1065	2025-06-17 15:20:57.297109+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-15	14:00:00	13:30:00	0	0	2025-06-17 15:20:57.322548+00	1066	2025-06-17 15:20:57.322548+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-06-19	08:30:00	08:00:00	0	1	2025-06-17 15:20:57.351374+00	1067	2025-06-17 15:20:57.351374+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Схильність до алергічних реакцій та аутоімунних процесів.
2025-07-07	10:00:00	09:30:00	0	1	2025-06-17 15:20:57.372971+00	1068	2025-06-17 15:20:57.372971+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-06-19	10:30:00	10:00:00	0	1	2025-06-17 15:20:57.40487+00	1069	2025-06-17 15:20:57.40487+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-03	22:00:00	21:30:00	0	1	2025-06-17 15:20:57.433232+00	1070	2025-06-17 15:20:57.433232+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f603-7282-bc16-c98712d0e44d	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-14	12:00:00	11:30:00	0	0	2025-06-17 15:20:57.486931+00	1071	2025-06-17 15:20:57.486931+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Потреба у відновленні після перенесеного інсульту або травми.
2025-07-15	11:00:00	10:30:00	0	1	2025-06-17 15:20:57.506578+00	1072	2025-06-17 15:20:57.506578+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-15	20:00:00	19:30:00	0	0	2025-06-17 15:20:57.523536+00	1073	2025-06-17 15:20:57.523536+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-14	12:00:00	11:30:00	0	0	2025-06-17 15:20:57.541318+00	1074	2025-06-17 15:20:57.541318+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Часті інфекційні захворювання, повільне загоєння ран.
2025-07-04	21:00:00	20:30:00	0	1	2025-06-17 15:20:57.569186+00	1075	2025-06-17 15:20:57.569186+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-07	19:00:00	18:30:00	0	1	2025-06-17 15:20:57.61496+00	1076	2025-06-17 15:20:57.61496+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-14	18:00:00	17:30:00	0	0	2025-06-17 15:20:57.636941+00	1077	2025-06-17 15:20:57.636941+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-08	19:30:00	19:00:00	0	0	2025-06-17 15:20:57.662073+00	1078	2025-06-17 15:20:57.662073+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-11	12:30:00	12:00:00	0	0	2025-06-17 15:20:57.682396+00	1079	2025-06-17 15:20:57.682396+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-09	13:00:00	12:30:00	0	0	2025-06-17 15:20:57.701292+00	1080	2025-06-17 15:20:57.701292+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-13	09:30:00	09:00:00	0	1	2025-06-17 15:20:57.720247+00	1081	2025-06-17 15:20:57.720247+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-06	15:00:00	14:30:00	0	1	2025-06-17 15:20:57.748184+00	1082	2025-06-17 15:20:57.748184+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-09	21:00:00	20:30:00	0	0	2025-06-17 15:20:57.764865+00	1083	2025-06-17 15:20:57.764865+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-07	18:30:00	18:00:00	0	1	2025-06-17 15:20:57.791288+00	1084	2025-06-17 15:20:57.791288+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-06-20	12:00:00	11:30:00	0	0	2025-06-17 15:20:57.81709+00	1085	2025-06-17 15:20:57.81709+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-13	09:30:00	09:00:00	0	1	2025-06-17 15:20:57.830661+00	1086	2025-06-17 15:20:57.830661+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-06-20	17:00:00	16:30:00	0	1	2025-06-17 15:20:57.856888+00	1087	2025-06-17 15:20:57.856888+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-11	14:00:00	13:30:00	0	1	2025-06-17 15:20:57.87418+00	1088	2025-06-17 15:20:57.87418+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-07-06	15:30:00	15:00:00	0	0	2025-06-17 15:20:57.904297+00	1089	2025-06-17 15:20:57.904297+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Часті інфекційні захворювання, повільне загоєння ран.
2025-07-09	12:30:00	12:00:00	0	1	2025-06-17 15:20:57.921471+00	1090	2025-06-17 15:20:57.921471+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:57.971132+00	1091	2025-06-17 15:20:57.971132+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-08	21:30:00	21:00:00	0	0	2025-06-17 15:20:58.000493+00	1093	2025-06-17 15:20:58.000493+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-08	08:30:00	08:00:00	0	1	2025-06-17 15:20:58.037217+00	1094	2025-06-17 15:20:58.037217+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-04	14:30:00	14:00:00	0	1	2025-06-17 15:20:58.05982+00	1095	2025-06-17 15:20:58.05982+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-06-20	09:30:00	09:00:00	0	1	2025-06-17 15:20:58.133012+00	1096	2025-06-17 15:20:58.133012+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-06-26	14:00:00	13:30:00	2	0	2025-06-17 15:21:08.028128+00	1454	2025-06-17 15:21:08.028128+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-04	17:30:00	17:00:00	0	0	2025-06-17 15:20:58.157926+00	1097	2025-06-17 15:20:58.157926+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-11	09:30:00	09:00:00	0	1	2025-06-17 15:20:58.181252+00	1098	2025-06-17 15:20:58.181252+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-10	12:30:00	12:00:00	0	1	2025-06-17 15:20:58.195826+00	1099	2025-06-17 15:20:58.195826+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-11	11:00:00	10:30:00	0	1	2025-06-17 15:20:58.227166+00	1100	2025-06-17 15:20:58.227166+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-04	12:30:00	12:00:00	0	0	2025-06-17 15:20:58.241179+00	1101	2025-06-17 15:20:58.241179+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-06	14:30:00	14:00:00	0	1	2025-06-17 15:20:58.290806+00	1102	2025-06-17 15:20:58.290806+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-12	10:00:00	09:30:00	0	1	2025-06-17 15:20:58.344102+00	1103	2025-06-17 15:20:58.344102+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Постійне відчуття тривоги, напади паніки без видимої причини.
2025-07-09	17:00:00	16:30:00	0	1	2025-06-17 15:20:58.377309+00	1104	2025-06-17 15:20:58.377309+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f469-7573-ba3a-604cd272778f	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-07	22:30:00	22:00:00	0	1	2025-06-17 15:20:58.413357+00	1105	2025-06-17 15:20:58.413357+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Затримка розвитку, відставання у вазі та зрості.
2025-07-04	13:00:00	12:30:00	0	1	2025-06-17 15:20:58.428296+00	1106	2025-06-17 15:20:58.428296+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-13	21:00:00	20:30:00	0	1	2025-06-17 15:20:58.442702+00	1107	2025-06-17 15:20:58.442702+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-05	15:30:00	15:00:00	0	1	2025-06-17 15:20:58.463149+00	1108	2025-06-17 15:20:58.463149+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-13	14:00:00	13:30:00	0	1	2025-06-17 15:20:58.480519+00	1109	2025-06-17 15:20:58.480519+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-08	23:00:00	22:30:00	0	0	2025-06-17 15:20:58.515207+00	1110	2025-06-17 15:20:58.515207+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-15	16:00:00	15:30:00	0	1	2025-06-17 15:20:58.578452+00	1111	2025-06-17 15:20:58.578452+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-14	19:00:00	18:30:00	0	0	2025-06-17 15:20:58.59385+00	1112	2025-06-17 15:20:58.59385+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-09	12:30:00	12:00:00	0	0	2025-06-17 15:20:58.608324+00	1113	2025-06-17 15:20:58.608324+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-13	22:00:00	21:30:00	0	0	2025-06-17 15:20:58.645728+00	1114	2025-06-17 15:20:58.645728+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-09	13:30:00	13:00:00	0	1	2025-06-17 15:20:58.661383+00	1115	2025-06-17 15:20:58.661383+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-09	14:00:00	13:30:00	0	0	2025-06-17 15:20:58.678362+00	1116	2025-06-17 15:20:58.678362+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-11	15:30:00	15:00:00	0	0	2025-06-17 15:20:58.713782+00	1117	2025-06-17 15:20:58.713782+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-14	17:00:00	16:30:00	0	1	2025-06-17 15:20:58.734119+00	1118	2025-06-17 15:20:58.734119+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-03	17:30:00	17:00:00	0	1	2025-06-17 15:20:58.770148+00	1119	2025-06-17 15:20:58.770148+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-08	15:00:00	14:30:00	0	0	2025-06-17 15:20:58.784874+00	1120	2025-06-17 15:20:58.784874+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Запаморочення, порушення координації рухів, падіння без видимої причини.
2025-07-08	14:00:00	13:30:00	0	1	2025-06-17 15:20:58.799001+00	1121	2025-06-17 15:20:58.799001+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-11	20:30:00	20:00:00	0	1	2025-06-17 15:20:58.838993+00	1122	2025-06-17 15:20:58.838993+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f603-7282-bc16-c98712d0e44d	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-05	08:30:00	08:00:00	0	0	2025-06-17 15:20:58.877208+00	1123	2025-06-17 15:20:58.877208+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Тривала депресія, втрата інтересу до життя, ізоляція.
2025-06-24	13:30:00	13:00:00	0	1	2025-06-17 15:20:58.907309+00	1124	2025-06-17 15:20:58.907309+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Зміни настрою, дратівливість, безпричинне підвищення пітливості.
2025-07-03	21:00:00	20:30:00	0	0	2025-06-17 15:20:58.950769+00	1126	2025-06-17 15:20:58.950769+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Тривала депресія, втрата інтересу до життя, ізоляція.
2025-07-03	20:00:00	19:30:00	0	1	2025-06-17 15:20:58.967555+00	1127	2025-06-17 15:20:58.967555+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-11	15:00:00	14:30:00	0	1	2025-06-17 15:20:59.010236+00	1128	2025-06-17 15:20:59.010236+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-06	16:00:00	15:30:00	0	0	2025-06-17 15:20:59.029753+00	1129	2025-06-17 15:20:59.029753+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-14	19:30:00	19:00:00	0	1	2025-06-17 15:20:59.070714+00	1130	2025-06-17 15:20:59.070714+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-10	13:30:00	13:00:00	0	1	2025-06-17 15:20:59.150753+00	1132	2025-06-17 15:20:59.150753+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-05	16:00:00	15:30:00	0	0	2025-06-17 15:20:59.174108+00	1133	2025-06-17 15:20:59.174108+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-04	19:00:00	18:30:00	0	1	2025-06-17 15:20:59.223268+00	1134	2025-06-17 15:20:59.223268+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:20:59.239291+00	1135	2025-06-17 15:20:59.239291+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-06	08:30:00	08:00:00	0	0	2025-06-17 15:20:59.254646+00	1136	2025-06-17 15:20:59.254646+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-09	22:30:00	22:00:00	0	0	2025-06-17 15:20:59.284284+00	1137	2025-06-17 15:20:59.284284+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-06-26	13:30:00	13:00:00	0	1	2025-06-17 15:20:59.327287+00	1138	2025-06-17 15:20:59.327287+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-13	17:00:00	16:30:00	0	1	2025-06-17 15:20:59.34976+00	1139	2025-06-17 15:20:59.34976+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-12	16:30:00	16:00:00	0	1	2025-06-17 15:20:59.380846+00	1140	2025-06-17 15:20:59.380846+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-04	14:30:00	14:00:00	0	1	2025-06-17 15:20:59.400007+00	1141	2025-06-17 15:20:59.400007+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-04	21:00:00	20:30:00	0	0	2025-06-17 15:20:59.445174+00	1143	2025-06-17 15:20:59.445174+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-05	10:00:00	09:30:00	0	0	2025-06-17 15:20:59.460038+00	1144	2025-06-17 15:20:59.460038+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Соціальна ізоляція, втрата інтересу до звичних справ.
2025-07-06	11:30:00	11:00:00	0	1	2025-06-17 15:20:59.487419+00	1145	2025-06-17 15:20:59.487419+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Загальна слабкість, ознаки запалення чи інфекції.
2025-07-11	15:00:00	14:30:00	0	1	2025-06-17 15:20:59.511054+00	1146	2025-06-17 15:20:59.511054+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Порушення сну, часті пробудження, відчуття тривоги та нервозності.
2025-07-14	12:00:00	11:30:00	0	0	2025-06-17 15:20:59.545184+00	1147	2025-06-17 15:20:59.545184+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-08	18:30:00	18:00:00	0	1	2025-06-17 15:20:59.563195+00	1148	2025-06-17 15:20:59.563195+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-06-19	11:30:00	11:00:00	0	1	2025-06-17 15:20:59.593111+00	1149	2025-06-17 15:20:59.593111+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-10	00:00:00	23:30:00	0	0	2025-06-17 15:20:59.635369+00	1150	2025-06-17 15:20:59.635369+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-06-20	14:30:00	14:00:00	0	0	2025-06-17 15:20:59.670669+00	1151	2025-06-17 15:20:59.670669+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-ff63-73de-9441-b73d75415356	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-07	19:00:00	18:30:00	0	0	2025-06-17 15:20:59.690001+00	1152	2025-06-17 15:20:59.690001+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-09	10:00:00	09:30:00	0	1	2025-06-17 15:20:59.708405+00	1153	2025-06-17 15:20:59.708405+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-15	10:00:00	09:30:00	0	0	2025-06-17 15:20:59.725522+00	1154	2025-06-17 15:20:59.725522+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-11	09:30:00	09:00:00	0	0	2025-06-17 15:20:59.754252+00	1155	2025-06-17 15:20:59.754252+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-05	12:30:00	12:00:00	0	0	2025-06-17 15:20:59.771658+00	1156	2025-06-17 15:20:59.771658+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-11	08:30:00	08:00:00	0	1	2025-06-17 15:20:59.794588+00	1157	2025-06-17 15:20:59.794588+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-11	17:00:00	16:30:00	0	1	2025-06-22 12:47:05.6222+00	1752	2025-06-22 12:47:05.6222+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	lpeokejgiro
2025-06-27	19:30:00	19:00:00	2	1	2025-06-22 16:40:42.899899+00	1903	2025-06-22 16:40:42.899899+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	iiugyuftydfugihop
2025-07-13	19:30:00	19:00:00	0	1	2025-06-17 15:20:59.808935+00	1158	2025-06-17 15:20:59.808935+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-06	18:30:00	18:00:00	0	0	2025-06-17 15:20:59.824121+00	1159	2025-06-17 15:20:59.824121+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-06	11:00:00	10:30:00	0	1	2025-06-17 15:20:59.838074+00	1160	2025-06-17 15:20:59.838074+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-14	15:00:00	14:30:00	0	0	2025-06-17 15:20:59.850892+00	1161	2025-06-17 15:20:59.850892+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-09	21:30:00	21:00:00	0	1	2025-06-17 15:20:59.881017+00	1162	2025-06-17 15:20:59.881017+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-15	09:30:00	09:00:00	0	0	2025-06-17 15:20:59.898251+00	1163	2025-06-17 15:20:59.898251+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-05	10:00:00	09:30:00	0	1	2025-06-17 15:20:59.915112+00	1164	2025-06-17 15:20:59.915112+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Набряки, почервоніння, виділення з рани, підвищення температури.
2025-07-04	00:00:00	23:30:00	0	1	2025-06-17 15:20:59.931803+00	1165	2025-06-17 15:20:59.931803+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f469-7573-ba3a-604cd272778f	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-11	11:00:00	10:30:00	0	1	2025-06-17 15:20:59.956837+00	1166	2025-06-17 15:20:59.956837+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-08	17:30:00	17:00:00	0	1	2025-06-17 15:20:59.984537+00	1167	2025-06-17 15:20:59.984537+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Загальна слабкість, підвищена втомлюваність навіть після невеликих навантажень.
2025-07-08	18:00:00	17:30:00	0	0	2025-06-17 15:21:00.00902+00	1168	2025-06-17 15:21:00.00902+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у суглобах, особливо вранці, що зменшується при русі.
2025-07-13	13:00:00	12:30:00	0	0	2025-06-17 15:21:00.038421+00	1169	2025-06-17 15:21:00.038421+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-07	12:30:00	12:00:00	0	1	2025-06-17 15:21:00.05288+00	1170	2025-06-17 15:21:00.05288+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-07	17:00:00	16:30:00	0	0	2025-06-17 15:21:00.083153+00	1171	2025-06-17 15:21:00.083153+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-10	14:00:00	13:30:00	0	1	2025-06-17 15:21:00.115697+00	1172	2025-06-17 15:21:00.115697+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-08	14:00:00	13:30:00	0	1	2025-06-17 15:21:00.132967+00	1173	2025-06-17 15:21:00.132967+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-09	11:00:00	10:30:00	0	0	2025-06-17 15:21:00.150054+00	1174	2025-06-17 15:21:00.150054+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-14	11:00:00	10:30:00	0	0	2025-06-17 15:21:00.166907+00	1175	2025-06-17 15:21:00.166907+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-14	17:00:00	16:30:00	0	0	2025-06-17 15:21:00.205531+00	1176	2025-06-17 15:21:00.205531+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-06	13:30:00	13:00:00	0	1	2025-06-17 15:21:00.219946+00	1177	2025-06-17 15:21:00.219946+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-05	21:00:00	20:30:00	0	0	2025-06-17 15:21:00.233233+00	1178	2025-06-17 15:21:00.233233+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-13	13:00:00	12:30:00	0	1	2025-06-17 15:21:00.254031+00	1179	2025-06-17 15:21:00.254031+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-12	22:00:00	21:30:00	0	0	2025-06-17 15:21:00.275429+00	1180	2025-06-17 15:21:00.275429+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-12	13:30:00	13:00:00	0	0	2025-06-17 15:21:00.3014+00	1181	2025-06-17 15:21:00.3014+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-03	22:00:00	21:30:00	0	1	2025-06-17 15:21:00.333097+00	1182	2025-06-17 15:21:00.333097+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-0026-79ac-a236-50d54f042a62	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-03	09:30:00	09:00:00	0	1	2025-06-17 15:21:00.358908+00	1183	2025-06-17 15:21:00.358908+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-13	08:30:00	08:00:00	0	1	2025-06-17 15:21:00.375383+00	1184	2025-06-17 15:21:00.375383+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Схильність до алергічних реакцій та аутоімунних процесів.
2025-07-06	10:00:00	09:30:00	0	1	2025-06-17 15:21:00.433907+00	1185	2025-06-17 15:21:00.433907+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Збільшення лімфатичних вузлів без видимих причин.
2025-07-04	21:00:00	20:30:00	0	1	2025-06-17 15:21:00.44771+00	1186	2025-06-17 15:21:00.44771+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-09	09:30:00	09:00:00	0	1	2025-06-17 15:21:00.462039+00	1187	2025-06-17 15:21:00.462039+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-14	16:00:00	15:30:00	0	1	2025-06-17 15:21:00.483583+00	1188	2025-06-17 15:21:00.483583+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-04	19:00:00	18:30:00	0	1	2025-06-17 15:21:00.505861+00	1189	2025-06-17 15:21:00.505861+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f469-7573-ba3a-604cd272778f	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-09	16:00:00	15:30:00	0	1	2025-06-17 15:21:00.523076+00	1190	2025-06-17 15:21:00.523076+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Обмеження рухливості, скутість м’язів, загальна слабкість.
2025-07-12	08:30:00	08:00:00	0	1	2025-06-17 15:21:00.5584+00	1191	2025-06-17 15:21:00.5584+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-14	15:30:00	15:00:00	0	1	2025-06-17 15:21:00.575845+00	1192	2025-06-17 15:21:00.575845+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичне оніміння кінцівок, поколювання, слабкість м’язів.
2025-06-20	10:30:00	10:00:00	0	0	2025-06-17 15:21:00.636561+00	1193	2025-06-17 15:21:00.636561+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-08	15:30:00	15:00:00	0	0	2025-06-17 15:21:00.66744+00	1194	2025-06-17 15:21:00.66744+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-14	12:00:00	11:30:00	0	0	2025-06-17 15:21:00.726265+00	1195	2025-06-17 15:21:00.726265+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-03	12:30:00	12:00:00	0	0	2025-06-17 15:21:00.775598+00	1196	2025-06-17 15:21:00.775598+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-12	14:30:00	14:00:00	0	1	2025-06-17 15:21:00.808951+00	1197	2025-06-17 15:21:00.808951+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-09	08:30:00	08:00:00	0	0	2025-06-17 15:21:00.860263+00	1198	2025-06-17 15:21:00.860263+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-11	14:30:00	14:00:00	0	1	2025-06-17 15:21:00.877017+00	1199	2025-06-17 15:21:00.877017+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-12	09:30:00	09:00:00	0	1	2025-06-17 15:21:00.903244+00	1200	2025-06-17 15:21:00.903244+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-15	16:00:00	15:30:00	0	1	2025-06-17 15:21:00.922425+00	1201	2025-06-17 15:21:00.922425+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Соціальна ізоляція, втрата інтересу до звичних справ.
2025-07-11	16:00:00	15:30:00	0	0	2025-06-17 15:21:00.97066+00	1202	2025-06-17 15:21:00.97066+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f469-7573-ba3a-604cd272778f	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-13	16:30:00	16:00:00	0	0	2025-06-17 15:21:00.994247+00	1203	2025-06-17 15:21:00.994247+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-08	12:00:00	11:30:00	0	0	2025-06-17 15:21:01.015932+00	1204	2025-06-17 15:21:01.015932+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-14	14:30:00	14:00:00	0	1	2025-06-17 15:21:01.036285+00	1205	2025-06-17 15:21:01.036285+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-14	23:00:00	22:30:00	0	0	2025-06-17 15:21:01.069792+00	1206	2025-06-17 15:21:01.069792+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-03	11:00:00	10:30:00	0	0	2025-06-17 15:21:01.087631+00	1207	2025-06-17 15:21:01.087631+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-15	11:00:00	10:30:00	0	1	2025-06-17 15:21:01.105438+00	1208	2025-06-17 15:21:01.105438+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-07	14:00:00	13:30:00	0	0	2025-06-17 15:21:01.122761+00	1209	2025-06-17 15:21:01.122761+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-15	16:30:00	16:00:00	0	0	2025-06-17 15:21:01.205376+00	1211	2025-06-17 15:21:01.205376+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-10	13:30:00	13:00:00	0	0	2025-06-17 15:21:01.234504+00	1212	2025-06-17 15:21:01.234504+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-11	10:30:00	10:00:00	0	0	2025-06-17 15:21:01.281223+00	1214	2025-06-17 15:21:01.281223+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-05	20:00:00	19:30:00	0	0	2025-06-17 15:21:01.303496+00	1215	2025-06-17 15:21:01.303496+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-04	22:00:00	21:30:00	0	1	2025-06-17 15:21:01.329592+00	1216	2025-06-17 15:21:01.329592+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-13	14:00:00	13:30:00	0	0	2025-06-17 15:21:01.356543+00	1217	2025-06-17 15:21:01.356543+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-13	12:30:00	12:00:00	0	0	2025-06-17 15:21:03.239781+00	1279	2025-06-17 15:21:03.239781+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-09	10:00:00	09:30:00	1	1	2025-06-17 15:21:01.25823+00	1213	2025-06-17 15:21:01.25823+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fca6-7b48-a3a4-2c362c16081c	{"reason":"склад захоплено"}	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-13	21:30:00	21:00:00	0	1	2025-06-17 15:21:01.375176+00	1218	2025-06-17 15:21:01.375176+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-09	22:00:00	21:30:00	0	0	2025-06-17 15:21:01.405009+00	1219	2025-06-17 15:21:01.405009+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-05	11:30:00	11:00:00	0	1	2025-06-17 15:21:01.421938+00	1220	2025-06-17 15:21:01.421938+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-09	18:30:00	18:00:00	0	1	2025-06-17 15:21:01.436417+00	1221	2025-06-17 15:21:01.436417+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-09	19:30:00	19:00:00	0	1	2025-06-17 15:21:01.464514+00	1222	2025-06-17 15:21:01.464514+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-13	20:00:00	19:30:00	0	0	2025-06-17 15:21:01.478011+00	1223	2025-06-17 15:21:01.478011+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Складнощі з розумінням мови оточуючих або побудовою фраз.
2025-07-04	09:30:00	09:00:00	0	1	2025-06-17 15:21:01.501824+00	1224	2025-06-17 15:21:01.501824+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-14	21:00:00	20:30:00	0	1	2025-06-17 15:21:01.546699+00	1225	2025-06-17 15:21:01.546699+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-06-22	11:30:00	11:00:00	0	0	2025-06-17 15:21:01.590687+00	1226	2025-06-17 15:21:01.590687+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Психоемоційне вигорання, втрата мотивації до роботи та особистого розвитку.
2025-07-15	15:30:00	15:00:00	0	0	2025-06-17 15:21:01.637288+00	1227	2025-06-17 15:21:01.637288+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-06-22	13:00:00	12:30:00	0	1	2025-06-17 15:21:01.669089+00	1228	2025-06-17 15:21:01.669089+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Постійне відчуття тривоги, напади паніки без видимої причини.
2025-07-03	22:00:00	21:30:00	0	1	2025-06-17 15:21:01.686297+00	1229	2025-06-17 15:21:01.686297+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-15	10:00:00	09:30:00	0	1	2025-06-17 15:21:01.712351+00	1230	2025-06-17 15:21:01.712351+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-04	13:30:00	13:00:00	0	0	2025-06-17 15:21:01.730615+00	1231	2025-06-17 15:21:01.730615+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-12	15:30:00	15:00:00	0	1	2025-06-17 15:21:01.764665+00	1232	2025-06-17 15:21:01.764665+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ff63-73de-9441-b73d75415356	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-04	21:30:00	21:00:00	0	0	2025-06-17 15:21:01.840808+00	1233	2025-06-17 15:21:01.840808+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-11	10:00:00	09:30:00	0	0	2025-06-17 15:21:01.856999+00	1234	2025-06-17 15:21:01.856999+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-09	21:00:00	20:30:00	0	1	2025-06-17 15:21:01.88593+00	1235	2025-06-17 15:21:01.88593+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-03	18:00:00	17:30:00	0	0	2025-06-17 15:21:01.906314+00	1236	2025-06-17 15:21:01.906314+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-14	20:30:00	20:00:00	0	0	2025-06-17 15:21:01.979156+00	1237	2025-06-17 15:21:01.979156+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Підвищений артеріальний тиск, що складно контролюється медикаментами.
2025-07-07	20:30:00	20:00:00	0	0	2025-06-17 15:21:01.998577+00	1238	2025-06-17 15:21:01.998577+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Тривала депресія, втрата інтересу до життя, ізоляція.
2025-07-15	09:30:00	09:00:00	0	1	2025-06-17 15:21:02.028104+00	1239	2025-06-17 15:21:02.028104+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Проблеми з обміном речовин, часті головні болі і запаморочення.
2025-06-24	15:00:00	14:30:00	0	0	2025-06-17 15:21:02.161101+00	1242	2025-06-17 15:21:02.161101+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-03	09:30:00	09:00:00	0	0	2025-06-17 15:21:02.208707+00	1243	2025-06-17 15:21:02.208707+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення стільця: запори, діарея, або їх чергування без явної причини.
2025-07-06	15:30:00	15:00:00	0	1	2025-06-17 15:21:02.232373+00	1244	2025-06-17 15:21:02.232373+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-15	19:00:00	18:30:00	0	1	2025-06-17 15:21:02.272525+00	1245	2025-06-17 15:21:02.272525+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-09	13:30:00	13:00:00	0	1	2025-06-17 15:21:02.294458+00	1246	2025-06-17 15:21:02.294458+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-13	11:00:00	10:30:00	0	0	2025-06-17 15:21:02.35138+00	1247	2025-06-17 15:21:02.35138+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-07	10:00:00	09:30:00	0	1	2025-06-17 15:21:02.373542+00	1248	2025-06-17 15:21:02.373542+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-12	15:00:00	14:30:00	0	1	2025-06-17 15:21:02.414138+00	1249	2025-06-17 15:21:02.414138+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-11	14:30:00	14:00:00	0	1	2025-06-17 15:21:02.431976+00	1250	2025-06-17 15:21:02.431976+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-12	10:00:00	09:30:00	0	0	2025-06-17 15:21:02.459082+00	1251	2025-06-17 15:21:02.459082+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-09	20:30:00	20:00:00	0	0	2025-06-17 15:21:02.485477+00	1252	2025-06-17 15:21:02.485477+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-06-24	16:00:00	15:30:00	0	1	2025-06-17 15:21:02.532486+00	1253	2025-06-17 15:21:02.532486+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Постійна слабкість, сонливість, порушення терморегуляції тіла.
2025-07-03	10:30:00	10:00:00	0	1	2025-06-17 15:21:02.584918+00	1254	2025-06-17 15:21:02.584918+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-13	14:00:00	13:30:00	0	1	2025-06-17 15:21:02.62573+00	1256	2025-06-17 15:21:02.62573+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-05	10:00:00	09:30:00	0	1	2025-06-17 15:21:02.643834+00	1257	2025-06-17 15:21:02.643834+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-08	13:00:00	12:30:00	0	0	2025-06-17 15:21:02.665948+00	1258	2025-06-17 15:21:02.665948+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-06-21	12:30:00	12:00:00	0	0	2025-06-17 15:21:02.743422+00	1259	2025-06-17 15:21:02.743422+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-10	08:30:00	08:00:00	0	0	2025-06-17 15:21:02.784546+00	1260	2025-06-17 15:21:02.784546+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-08	17:00:00	16:30:00	0	1	2025-06-17 15:21:02.809813+00	1261	2025-06-17 15:21:02.809813+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-14	19:30:00	19:00:00	0	1	2025-06-17 15:21:02.827411+00	1262	2025-06-17 15:21:02.827411+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-09	15:00:00	14:30:00	0	1	2025-06-17 15:21:02.844137+00	1263	2025-06-17 15:21:02.844137+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-04	14:30:00	14:00:00	0	1	2025-06-17 15:21:02.858946+00	1264	2025-06-17 15:21:02.858946+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-04	00:00:00	23:30:00	0	0	2025-06-17 15:21:02.874953+00	1265	2025-06-17 15:21:02.874953+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-03	13:00:00	12:30:00	0	0	2025-06-17 15:21:02.897342+00	1266	2025-06-17 15:21:02.897342+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-07	19:00:00	18:30:00	0	1	2025-06-17 15:21:02.912228+00	1267	2025-06-17 15:21:02.912228+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-11	20:30:00	20:00:00	0	1	2025-06-17 15:21:02.930727+00	1268	2025-06-17 15:21:02.930727+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-05	22:00:00	21:30:00	0	1	2025-06-17 15:21:02.948102+00	1269	2025-06-17 15:21:02.948102+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-06-20	23:00:00	22:30:00	0	0	2025-06-17 15:21:02.98592+00	1270	2025-06-17 15:21:02.98592+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-12	13:30:00	13:00:00	0	1	2025-06-17 15:21:03.003195+00	1271	2025-06-17 15:21:03.003195+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-04	18:00:00	17:30:00	0	1	2025-06-17 15:21:03.062982+00	1272	2025-06-17 15:21:03.062982+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-12	08:30:00	08:00:00	0	1	2025-06-17 15:21:03.097029+00	1273	2025-06-17 15:21:03.097029+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Психоемоційне вигорання, втрата мотивації до роботи та особистого розвитку.
2025-07-05	13:00:00	12:30:00	0	1	2025-06-17 15:21:03.129355+00	1275	2025-06-17 15:21:03.129355+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-11	12:00:00	11:30:00	0	1	2025-06-17 15:21:03.147725+00	1276	2025-06-17 15:21:03.147725+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Порушення сну, часті пробудження, відчуття тривоги та нервозності.
2025-06-19	14:30:00	14:00:00	0	0	2025-06-17 15:21:03.181366+00	1277	2025-06-17 15:21:03.181366+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f469-7573-ba3a-604cd272778f	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-06-20	18:00:00	17:30:00	0	1	2025-06-17 15:21:03.222105+00	1278	2025-06-17 15:21:03.222105+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-27	13:30:00	13:00:00	0	0	2025-06-22 12:50:20.219475+00	1802	2025-06-22 12:50:20.219475+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	dwlpkofjeif
2025-07-15	17:30:00	17:00:00	0	0	2025-06-17 15:21:03.254687+00	1280	2025-06-17 15:21:03.254687+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-08	12:00:00	11:30:00	0	1	2025-06-17 15:21:03.284291+00	1281	2025-06-17 15:21:03.284291+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-03	08:30:00	08:00:00	0	1	2025-06-17 15:21:03.31322+00	1282	2025-06-17 15:21:03.31322+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-14	22:00:00	21:30:00	0	1	2025-06-17 15:21:03.359994+00	1283	2025-06-17 15:21:03.359994+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-04	15:00:00	14:30:00	0	1	2025-06-17 15:21:03.40438+00	1284	2025-06-17 15:21:03.40438+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-09	11:00:00	10:30:00	0	1	2025-06-17 15:21:03.426207+00	1285	2025-06-17 15:21:03.426207+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-04	16:00:00	15:30:00	0	1	2025-06-17 15:21:03.4627+00	1286	2025-06-17 15:21:03.4627+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-07	09:30:00	09:00:00	0	0	2025-06-17 15:21:03.477533+00	1287	2025-06-17 15:21:03.477533+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-0026-79ac-a236-50d54f042a62	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-04	19:30:00	19:00:00	0	0	2025-06-17 15:21:03.501859+00	1288	2025-06-17 15:21:03.501859+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-05	15:00:00	14:30:00	0	1	2025-06-17 15:21:03.545554+00	1290	2025-06-17 15:21:03.545554+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-03	17:30:00	17:00:00	0	1	2025-06-17 15:21:03.587503+00	1291	2025-06-17 15:21:03.587503+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-13	15:00:00	14:30:00	0	0	2025-06-17 15:21:03.641875+00	1292	2025-06-17 15:21:03.641875+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-14	13:00:00	12:30:00	0	0	2025-06-17 15:21:03.711062+00	1293	2025-06-17 15:21:03.711062+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Підвищена температура тіла без виявленої інфекції або запального процесу.
2025-07-11	14:30:00	14:00:00	0	0	2025-06-17 15:21:03.746742+00	1295	2025-06-17 15:21:03.746742+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-05	12:30:00	12:00:00	0	0	2025-06-17 15:21:03.764292+00	1296	2025-06-17 15:21:03.764292+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Тривала депресія, втрата інтересу до життя, ізоляція.
2025-06-19	13:00:00	12:30:00	0	0	2025-06-17 15:21:03.793731+00	1297	2025-06-17 15:21:03.793731+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-04	15:30:00	15:00:00	0	0	2025-06-17 15:21:03.81059+00	1298	2025-06-17 15:21:03.81059+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-06	15:00:00	14:30:00	0	1	2025-06-17 15:21:03.843586+00	1299	2025-06-17 15:21:03.843586+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-08	15:00:00	14:30:00	0	0	2025-06-17 15:21:03.885429+00	1300	2025-06-17 15:21:03.885429+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-14	18:00:00	17:30:00	0	1	2025-06-17 15:21:03.913256+00	1301	2025-06-17 15:21:03.913256+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-09	15:30:00	15:00:00	0	1	2025-06-17 15:21:03.931804+00	1302	2025-06-17 15:21:03.931804+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-04	13:30:00	13:00:00	0	0	2025-06-17 15:21:03.949605+00	1303	2025-06-17 15:21:03.949605+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-10	15:30:00	15:00:00	0	0	2025-06-17 15:21:03.966405+00	1304	2025-06-17 15:21:03.966405+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-07	17:30:00	17:00:00	0	1	2025-06-17 15:21:03.999457+00	1305	2025-06-17 15:21:03.999457+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	18:30:00	18:00:00	0	0	2025-06-17 15:21:04.018695+00	1306	2025-06-17 15:21:04.018695+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-ff63-73de-9441-b73d75415356	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-10	20:30:00	20:00:00	0	1	2025-06-17 15:21:04.036207+00	1307	2025-06-17 15:21:04.036207+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-12	15:30:00	15:00:00	0	1	2025-06-17 15:21:04.053863+00	1308	2025-06-17 15:21:04.053863+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-14	19:30:00	19:00:00	0	0	2025-06-17 15:21:04.068208+00	1309	2025-06-17 15:21:04.068208+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-05	17:00:00	16:30:00	0	0	2025-06-17 15:21:04.095999+00	1310	2025-06-17 15:21:04.095999+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Синдром перевтоми: втрата витривалості, безсоння після тренувань.
2025-07-05	00:00:00	23:30:00	0	0	2025-06-17 15:21:04.109985+00	1311	2025-06-17 15:21:04.109985+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Часті напади алергічного нежитю, що супроводжуються свербінням та сльозотечею.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:21:04.125587+00	1312	2025-06-17 15:21:04.125587+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-11	13:30:00	13:00:00	0	0	2025-06-17 15:21:04.143874+00	1313	2025-06-17 15:21:04.143874+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-09	10:00:00	09:30:00	0	0	2025-06-17 15:21:04.160877+00	1314	2025-06-17 15:21:04.160877+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-04	16:00:00	15:30:00	0	0	2025-06-17 15:21:04.210388+00	1315	2025-06-17 15:21:04.210388+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-07	19:30:00	19:00:00	0	1	2025-06-17 15:21:04.263938+00	1316	2025-06-17 15:21:04.263938+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-15	19:30:00	19:00:00	0	1	2025-06-17 15:21:04.300903+00	1317	2025-06-17 15:21:04.300903+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-20	11:00:00	10:30:00	0	0	2025-06-17 15:21:04.352206+00	1318	2025-06-17 15:21:04.352206+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-15	19:00:00	18:30:00	0	1	2025-06-17 15:21:04.3839+00	1319	2025-06-17 15:21:04.3839+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-12	15:30:00	15:00:00	0	1	2025-06-17 15:21:04.401221+00	1320	2025-06-17 15:21:04.401221+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-05	21:30:00	21:00:00	0	1	2025-06-17 15:21:04.426896+00	1321	2025-06-17 15:21:04.426896+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-ff63-73de-9441-b73d75415356	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-08	18:30:00	18:00:00	0	0	2025-06-17 15:21:04.450269+00	1322	2025-06-17 15:21:04.450269+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-07	21:30:00	21:00:00	0	0	2025-06-17 15:21:04.47194+00	1323	2025-06-17 15:21:04.47194+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-12	12:00:00	11:30:00	0	1	2025-06-17 15:21:04.504171+00	1324	2025-06-17 15:21:04.504171+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Постійне відчуття тривоги, напади паніки без видимої причини.
2025-06-19	11:00:00	10:30:00	0	0	2025-06-17 15:21:04.546005+00	1326	2025-06-17 15:21:04.546005+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Періодичні підвищення температури тіла без явних інфекцій.
2025-07-04	12:30:00	12:00:00	0	0	2025-06-17 15:21:04.572132+00	1327	2025-06-17 15:21:04.572132+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Набряки, почервоніння, виділення з рани, підвищення температури.
2025-07-05	17:30:00	17:00:00	0	1	2025-06-17 15:21:04.589763+00	1328	2025-06-17 15:21:04.589763+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Психоемоційне вигорання, втрата мотивації до роботи та особистого розвитку.
2025-07-13	14:00:00	13:30:00	0	0	2025-06-17 15:21:04.609987+00	1329	2025-06-17 15:21:04.609987+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Хронічна закладеність носа, відсутність полегшення після використання крапель.
2025-07-06	19:00:00	18:30:00	0	1	2025-06-17 15:21:04.672351+00	1330	2025-06-17 15:21:04.672351+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-08	18:00:00	17:30:00	0	1	2025-06-17 15:21:04.687408+00	1331	2025-06-17 15:21:04.687408+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Труднощі у спілкуванні з близькими, внутрішні конфлікти.
2025-07-15	13:00:00	12:30:00	0	0	2025-06-17 15:21:04.705963+00	1332	2025-06-17 15:21:04.705963+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Проблеми з обміном речовин, часті головні болі і запаморочення.
2025-07-14	20:30:00	20:00:00	0	1	2025-06-17 15:21:04.730126+00	1333	2025-06-17 15:21:04.730126+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-04	11:30:00	11:00:00	0	0	2025-06-17 15:21:04.769391+00	1334	2025-06-17 15:21:04.769391+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-09	20:30:00	20:00:00	0	0	2025-06-17 15:21:04.814281+00	1335	2025-06-17 15:21:04.814281+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-14	09:30:00	09:00:00	0	1	2025-06-17 15:21:04.835713+00	1336	2025-06-17 15:21:04.835713+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-06-19	10:00:00	09:30:00	0	0	2025-06-17 15:21:04.864971+00	1337	2025-06-17 15:21:04.864971+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-15	15:00:00	14:30:00	0	1	2025-06-17 15:21:04.88007+00	1338	2025-06-17 15:21:04.88007+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-10	20:30:00	20:00:00	0	0	2025-06-17 15:21:04.894631+00	1339	2025-06-17 15:21:04.894631+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-12	16:30:00	16:00:00	0	1	2025-06-17 15:21:06.688154+00	1401	2025-06-17 15:21:06.688154+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Висипання на шкірі, діарея або запори, блювання.
2025-07-10	11:00:00	10:30:00	0	1	2025-06-17 15:21:04.914264+00	1340	2025-06-17 15:21:04.914264+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-06	19:30:00	19:00:00	0	0	2025-06-17 15:21:04.958864+00	1341	2025-06-17 15:21:04.958864+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-13	10:30:00	10:00:00	0	1	2025-06-17 15:21:04.988424+00	1342	2025-06-17 15:21:04.988424+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-07	18:00:00	17:30:00	0	1	2025-06-17 15:21:05.077385+00	1343	2025-06-17 15:21:05.077385+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Соціальна ізоляція, втрата інтересу до звичних справ.
2025-07-07	12:00:00	11:30:00	0	0	2025-06-17 15:21:05.142879+00	1345	2025-06-17 15:21:05.142879+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-06-24	11:30:00	11:00:00	0	0	2025-06-17 15:21:05.181306+00	1346	2025-06-17 15:21:05.181306+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-11	16:30:00	16:00:00	0	0	2025-06-17 15:21:05.205052+00	1347	2025-06-17 15:21:05.205052+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Травми зв’язок, розтягнення, що не минають протягом кількох тижнів.
2025-07-10	09:30:00	09:00:00	0	1	2025-06-17 15:21:05.224887+00	1348	2025-06-17 15:21:05.224887+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Хронічний біль у суглобах після спортивних тренувань.
2025-07-11	21:30:00	21:00:00	0	0	2025-06-17 15:21:05.258125+00	1349	2025-06-17 15:21:05.258125+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Синдром перевтоми: втрата витривалості, безсоння після тренувань.
2025-07-11	18:30:00	18:00:00	0	1	2025-06-17 15:21:05.279055+00	1350	2025-06-17 15:21:05.279055+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-10	12:30:00	12:00:00	0	0	2025-06-17 15:21:05.295811+00	1351	2025-06-17 15:21:05.295811+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-19	14:00:00	13:30:00	0	1	2025-06-17 15:21:05.330062+00	1352	2025-06-17 15:21:05.330062+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-09	14:00:00	13:30:00	0	0	2025-06-17 15:21:05.347252+00	1353	2025-06-17 15:21:05.347252+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-08	13:00:00	12:30:00	0	1	2025-06-17 15:21:05.365217+00	1354	2025-06-17 15:21:05.365217+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-09	10:00:00	09:30:00	0	0	2025-06-17 15:21:05.38323+00	1355	2025-06-17 15:21:05.38323+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-05	14:30:00	14:00:00	0	0	2025-06-17 15:21:05.421011+00	1356	2025-06-17 15:21:05.421011+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-14	09:30:00	09:00:00	0	1	2025-06-17 15:21:05.491247+00	1357	2025-06-17 15:21:05.491247+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-19	12:00:00	11:30:00	0	0	2025-06-17 15:21:05.517884+00	1358	2025-06-17 15:21:05.517884+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-10	14:30:00	14:00:00	0	0	2025-06-17 15:21:05.534554+00	1359	2025-06-17 15:21:05.534554+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-11	20:30:00	20:00:00	0	0	2025-06-17 15:21:05.560103+00	1360	2025-06-17 15:21:05.560103+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-11	16:00:00	15:30:00	0	1	2025-06-17 15:21:05.586082+00	1361	2025-06-17 15:21:05.586082+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-13	17:30:00	17:00:00	0	0	2025-06-17 15:21:05.621923+00	1362	2025-06-17 15:21:05.621923+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у горлі, що не минає понад тиждень, труднощі при ковтанні.
2025-07-05	22:30:00	22:00:00	0	0	2025-06-17 15:21:05.639567+00	1363	2025-06-17 15:21:05.639567+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-04	16:00:00	15:30:00	0	0	2025-06-17 15:21:05.665346+00	1364	2025-06-17 15:21:05.665346+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-15	16:00:00	15:30:00	0	0	2025-06-17 15:21:05.688474+00	1365	2025-06-17 15:21:05.688474+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-09	10:00:00	09:30:00	0	1	2025-06-17 15:21:05.710675+00	1366	2025-06-17 15:21:05.710675+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у суглобах, особливо вранці, що зменшується при русі.
2025-07-09	22:30:00	22:00:00	0	1	2025-06-17 15:21:05.732477+00	1367	2025-06-17 15:21:05.732477+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Порушення сну, часті пробудження, відчуття тривоги та нервозності.
2025-07-04	17:30:00	17:00:00	0	1	2025-06-17 15:21:05.776087+00	1368	2025-06-17 15:21:05.776087+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-06	00:00:00	23:30:00	0	1	2025-06-17 15:21:05.794544+00	1369	2025-06-17 15:21:05.794544+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-10	19:00:00	18:30:00	0	1	2025-06-17 15:21:05.873795+00	1371	2025-06-17 15:21:05.873795+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-05	20:00:00	19:30:00	0	1	2025-06-17 15:21:05.908706+00	1372	2025-06-17 15:21:05.908706+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-06-20	13:00:00	12:30:00	0	0	2025-06-17 15:21:05.934529+00	1373	2025-06-17 15:21:05.934529+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-06-20	12:00:00	11:30:00	0	0	2025-06-17 15:21:05.964187+00	1374	2025-06-17 15:21:05.964187+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-03	12:00:00	11:30:00	0	1	2025-06-17 15:21:05.985322+00	1375	2025-06-17 15:21:05.985322+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-06-19	16:00:00	15:30:00	0	1	2025-06-17 15:21:06.017705+00	1376	2025-06-17 15:21:06.017705+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-19	13:00:00	12:30:00	0	0	2025-06-17 15:21:06.046489+00	1377	2025-06-17 15:21:06.046489+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-14	14:00:00	13:30:00	0	1	2025-06-17 15:21:06.063077+00	1378	2025-06-17 15:21:06.063077+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-06-26	14:30:00	14:00:00	0	0	2025-06-17 15:21:06.140957+00	1380	2025-06-17 15:21:06.140957+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-04	15:30:00	15:00:00	0	0	2025-06-17 15:21:06.158337+00	1381	2025-06-17 15:21:06.158337+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-05	21:00:00	20:30:00	0	0	2025-06-17 15:21:06.173792+00	1382	2025-06-17 15:21:06.173792+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Проблеми з обміном речовин, часті головні болі і запаморочення.
2025-07-15	15:00:00	14:30:00	0	0	2025-06-17 15:21:06.18919+00	1383	2025-06-17 15:21:06.18919+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Зміни кольору і кількості сечі, набряки кінцівок, особливо вранці.
2025-07-15	15:30:00	15:00:00	0	0	2025-06-17 15:21:06.228589+00	1384	2025-06-17 15:21:06.228589+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Потреба в індивідуальному підборі навантажень після хвороби або травми.
2025-06-19	09:30:00	09:00:00	0	1	2025-06-17 15:21:06.25565+00	1385	2025-06-17 15:21:06.25565+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-05	08:30:00	08:00:00	0	1	2025-06-17 15:21:06.272417+00	1386	2025-06-17 15:21:06.272417+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-15	10:00:00	09:30:00	0	1	2025-06-17 15:21:06.289527+00	1387	2025-06-17 15:21:06.289527+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-08	19:30:00	19:00:00	0	1	2025-06-17 15:21:06.312499+00	1388	2025-06-17 15:21:06.312499+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-13	22:30:00	22:00:00	0	0	2025-06-17 15:21:06.388102+00	1390	2025-06-17 15:21:06.388102+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Тривала депресія, втрата інтересу до життя, ізоляція.
2025-07-05	12:00:00	11:30:00	0	1	2025-06-17 15:21:06.409664+00	1391	2025-06-17 15:21:06.409664+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-10	10:30:00	10:00:00	0	0	2025-06-17 15:21:06.438167+00	1392	2025-06-17 15:21:06.438167+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-20	13:30:00	13:00:00	0	0	2025-06-17 15:21:06.478548+00	1393	2025-06-17 15:21:06.478548+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-05	19:30:00	19:00:00	0	1	2025-06-17 15:21:06.493498+00	1394	2025-06-17 15:21:06.493498+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-10	12:00:00	11:30:00	0	0	2025-06-17 15:21:06.512802+00	1395	2025-06-17 15:21:06.512802+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-10	17:00:00	16:30:00	0	0	2025-06-17 15:21:06.558242+00	1396	2025-06-17 15:21:06.558242+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-14	10:30:00	10:00:00	0	1	2025-06-17 15:21:06.571182+00	1397	2025-06-17 15:21:06.571182+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-04	08:30:00	08:00:00	0	0	2025-06-17 15:21:06.590451+00	1398	2025-06-17 15:21:06.590451+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-11	12:00:00	11:30:00	0	1	2025-06-17 15:21:06.63552+00	1399	2025-06-17 15:21:06.63552+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-06-26	16:00:00	15:30:00	0	1	2025-06-17 15:21:06.671816+00	1400	2025-06-17 15:21:06.671816+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-06-24	08:30:00	08:00:00	2	0	2025-06-22 13:05:23.791499+00	1852	2025-06-22 13:05:23.791499+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	kjihuwhf
2025-07-15	15:00:00	14:30:00	0	1	2025-06-17 15:21:06.704977+00	1402	2025-06-17 15:21:06.704977+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-03	15:30:00	15:00:00	0	1	2025-06-17 15:21:06.764215+00	1403	2025-06-17 15:21:06.764215+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-06	01:30:00	01:00:00	0	0	2025-06-17 15:21:06.780424+00	1404	2025-06-17 15:21:06.780424+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-04	16:00:00	15:30:00	0	1	2025-06-17 15:21:06.82371+00	1405	2025-06-17 15:21:06.82371+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-09	15:00:00	14:30:00	0	1	2025-06-17 15:21:06.871041+00	1406	2025-06-17 15:21:06.871041+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-14	19:00:00	18:30:00	0	0	2025-06-17 15:21:06.898767+00	1407	2025-06-17 15:21:06.898767+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-12	09:30:00	09:00:00	0	0	2025-06-17 15:21:06.914962+00	1408	2025-06-17 15:21:06.914962+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-12	23:30:00	23:00:00	0	1	2025-06-17 15:21:06.937084+00	1409	2025-06-17 15:21:06.937084+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-10	18:30:00	18:00:00	0	1	2025-06-17 15:21:06.969436+00	1410	2025-06-17 15:21:06.969436+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-14	08:30:00	08:00:00	0	1	2025-06-17 15:21:06.983789+00	1411	2025-06-17 15:21:06.983789+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-08	10:00:00	09:30:00	0	1	2025-06-17 15:21:07.017111+00	1412	2025-06-17 15:21:07.017111+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-05	20:00:00	19:30:00	0	0	2025-06-17 15:21:07.030834+00	1413	2025-06-17 15:21:07.030834+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-07	08:30:00	08:00:00	0	0	2025-06-17 15:21:07.045933+00	1414	2025-06-17 15:21:07.045933+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-13	16:00:00	15:30:00	0	0	2025-06-17 15:21:07.067017+00	1415	2025-06-17 15:21:07.067017+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Психоемоційне вигорання, втрата мотивації до роботи та особистого розвитку.
2025-07-07	21:00:00	20:30:00	0	1	2025-06-17 15:21:07.081013+00	1416	2025-06-17 15:21:07.081013+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Перепади дихання при контакті з пилком рослин, утруднене дихання в сезон цвітіння.
2025-07-04	23:00:00	22:30:00	0	0	2025-06-17 15:21:07.100406+00	1417	2025-06-17 15:21:07.100406+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f469-7573-ba3a-604cd272778f	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-08	13:30:00	13:00:00	0	0	2025-06-17 15:21:07.12292+00	1418	2025-06-17 15:21:07.12292+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-09	08:30:00	08:00:00	0	1	2025-06-17 15:21:07.1397+00	1419	2025-06-17 15:21:07.1397+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичне оніміння кінцівок, поколювання, слабкість м’язів.
2025-07-08	19:00:00	18:30:00	0	0	2025-06-17 15:21:07.177733+00	1420	2025-06-17 15:21:07.177733+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Відчуття шуму або дзвону у вухах, порушення рівноваги.
2025-07-04	16:30:00	16:00:00	0	1	2025-06-17 15:21:07.216395+00	1421	2025-06-17 15:21:07.216395+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f469-7573-ba3a-604cd272778f	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-03	20:30:00	20:00:00	0	0	2025-06-17 15:21:07.233186+00	1422	2025-06-17 15:21:07.233186+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-05	21:30:00	21:00:00	0	0	2025-06-17 15:21:07.248239+00	1423	2025-06-17 15:21:07.248239+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-06	18:00:00	17:30:00	0	1	2025-06-17 15:21:07.270232+00	1424	2025-06-17 15:21:07.270232+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-08	12:30:00	12:00:00	0	1	2025-06-17 15:21:07.29275+00	1425	2025-06-17 15:21:07.29275+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-12	13:30:00	13:00:00	0	0	2025-06-17 15:21:07.315037+00	1426	2025-06-17 15:21:07.315037+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-09	08:30:00	08:00:00	0	0	2025-06-17 15:21:07.342779+00	1427	2025-06-17 15:21:07.342779+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fbdd-7845-982f-a684fb6c9078	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-09	17:00:00	16:30:00	0	0	2025-06-17 15:21:07.357123+00	1428	2025-06-17 15:21:07.357123+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-08	12:30:00	12:00:00	0	0	2025-06-17 15:21:07.388142+00	1429	2025-06-17 15:21:07.388142+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-10	17:00:00	16:30:00	0	1	2025-06-17 15:21:07.418801+00	1430	2025-06-17 15:21:07.418801+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-12	12:00:00	11:30:00	0	1	2025-06-17 15:21:07.434036+00	1431	2025-06-17 15:21:07.434036+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-14	19:00:00	18:30:00	0	1	2025-06-17 15:21:07.456053+00	1432	2025-06-17 15:21:07.456053+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-04	11:00:00	10:30:00	0	0	2025-06-17 15:21:07.485607+00	1433	2025-06-17 15:21:07.485607+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Схильність до алергічних реакцій та аутоімунних процесів.
2025-07-05	21:30:00	21:00:00	0	1	2025-06-17 15:21:07.507475+00	1434	2025-06-17 15:21:07.507475+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-20	09:30:00	09:00:00	0	0	2025-06-17 15:21:07.546836+00	1435	2025-06-17 15:21:07.546836+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Обмеження рухливості, скутість м’язів, загальна слабкість.
2025-07-08	11:00:00	10:30:00	0	1	2025-06-17 15:21:07.57823+00	1436	2025-06-17 15:21:07.57823+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-07	08:30:00	08:00:00	0	1	2025-06-17 15:21:07.599954+00	1437	2025-06-17 15:21:07.599954+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Потреба у відновленні після перенесеного інсульту або травми.
2025-07-09	18:30:00	18:00:00	0	0	2025-06-17 15:21:07.632993+00	1439	2025-06-17 15:21:07.632993+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Часті головні болі, що супроводжуються нудотою та чутливістю до світла.
2025-07-15	16:30:00	16:00:00	0	1	2025-06-17 15:21:07.649364+00	1440	2025-06-17 15:21:07.649364+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-07	08:30:00	08:00:00	0	0	2025-06-17 15:21:07.681844+00	1441	2025-06-17 15:21:07.681844+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-08	20:30:00	20:00:00	0	1	2025-06-17 15:21:07.697576+00	1442	2025-06-17 15:21:07.697576+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Труднощі у спілкуванні з близькими, внутрішні конфлікти.
2025-07-08	17:30:00	17:00:00	0	1	2025-06-17 15:21:07.712315+00	1443	2025-06-17 15:21:07.712315+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-15	16:00:00	15:30:00	0	1	2025-06-17 15:21:07.766116+00	1444	2025-06-17 15:21:07.766116+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-15	12:30:00	12:00:00	0	0	2025-06-17 15:21:07.794218+00	1446	2025-06-17 15:21:07.794218+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-06-19	14:00:00	13:30:00	0	1	2025-06-17 15:21:07.822797+00	1447	2025-06-17 15:21:07.822797+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-05	17:30:00	17:00:00	0	0	2025-06-17 15:21:07.91419+00	1448	2025-06-17 15:21:07.91419+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-10	16:00:00	15:30:00	0	0	2025-06-17 15:21:07.937047+00	1449	2025-06-17 15:21:07.937047+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття тяжкості в правому підребер’ї, нудота після їжі.
2025-07-03	08:30:00	08:00:00	0	1	2025-06-17 15:21:07.951893+00	1450	2025-06-17 15:21:07.951893+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Гострий біль у ділянці травми або після операції.
2025-07-13	13:00:00	12:30:00	0	0	2025-06-17 15:21:07.96683+00	1451	2025-06-17 15:21:07.96683+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Обмеження рухливості, скутість м’язів, загальна слабкість.
2025-07-13	19:00:00	18:30:00	0	1	2025-06-17 15:21:07.983422+00	1452	2025-06-17 15:21:07.983422+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Збільшення лімфатичних вузлів без видимих причин.
2025-07-03	19:30:00	19:00:00	0	0	2025-06-17 15:21:07.999214+00	1453	2025-06-17 15:21:07.999214+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-15	19:30:00	19:00:00	0	0	2025-06-17 15:21:08.054635+00	1455	2025-06-17 15:21:08.054635+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Травми зв’язок, розтягнення, що не минають протягом кількох тижнів.
2025-07-03	08:30:00	08:00:00	0	0	2025-06-17 15:21:08.069717+00	1456	2025-06-17 15:21:08.069717+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-11	12:30:00	12:00:00	0	0	2025-06-17 15:21:08.119499+00	1458	2025-06-17 15:21:08.119499+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Підвищена температура тіла без видимої причини.
2025-07-05	10:30:00	10:00:00	0	0	2025-06-17 15:21:08.155968+00	1459	2025-06-17 15:21:08.155968+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-04	16:00:00	15:30:00	0	0	2025-06-17 15:21:08.2144+00	1460	2025-06-17 15:21:08.2144+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-12	10:00:00	09:30:00	0	1	2025-06-17 15:21:08.254323+00	1461	2025-06-17 15:21:08.254323+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-03	14:30:00	14:00:00	0	1	2025-06-17 15:21:08.278188+00	1462	2025-06-17 15:21:08.278188+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-15	09:30:00	09:00:00	0	0	2025-06-17 15:21:08.294066+00	1463	2025-06-17 15:21:08.294066+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-03	09:30:00	09:00:00	2	0	2025-06-17 15:21:07.779744+00	1445	2025-06-17 15:21:07.779744+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-04	12:00:00	11:30:00	2	1	2025-06-17 15:21:08.097204+00	1457	2025-06-17 15:21:08.097204+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-07-06	10:00:00	09:30:00	0	0	2025-06-17 15:21:08.310075+00	1464	2025-06-17 15:21:08.310075+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Неналежні умови у тимчасовому гуртожитку для переселенців, відсутність опалення та води.
2025-07-15	08:30:00	08:00:00	0	0	2025-06-17 15:21:08.325244+00	1465	2025-06-17 15:21:08.325244+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-15	12:00:00	11:30:00	0	1	2025-06-17 15:21:08.361516+00	1466	2025-06-17 15:21:08.361516+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Порушення пам’яті, концентрації уваги, розлади мислення.
2025-07-05	13:30:00	13:00:00	0	1	2025-06-17 15:21:08.404087+00	1467	2025-06-17 15:21:08.404087+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Труднощі у спілкуванні з близькими, внутрішні конфлікти.
2025-07-06	11:00:00	10:30:00	0	0	2025-06-17 15:21:08.418022+00	1468	2025-06-17 15:21:08.418022+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-06-19	08:30:00	08:00:00	0	0	2025-06-17 15:21:08.500447+00	1469	2025-06-17 15:21:08.500447+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Зниження апетиту, неприємний присмак у роті, іноді блювання.
2025-07-06	00:00:00	23:30:00	0	1	2025-06-17 15:21:08.524622+00	1470	2025-06-17 15:21:08.524622+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-06-20	00:00:00	23:30:00	0	0	2025-06-17 15:21:08.585214+00	1471	2025-06-17 15:21:08.585214+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-04	13:00:00	12:30:00	0	0	2025-06-17 15:21:08.608036+00	1472	2025-06-17 15:21:08.608036+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-14	13:30:00	13:00:00	0	0	2025-06-17 15:21:08.630959+00	1473	2025-06-17 15:21:08.630959+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-06	08:30:00	08:00:00	0	1	2025-06-17 15:21:08.645751+00	1474	2025-06-17 15:21:08.645751+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-14	14:30:00	14:00:00	0	0	2025-06-17 15:21:08.661051+00	1475	2025-06-17 15:21:08.661051+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-06-19	11:00:00	10:30:00	0	1	2025-06-17 15:21:08.720339+00	1476	2025-06-17 15:21:08.720339+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-20	13:30:00	13:00:00	0	0	2025-06-17 15:21:08.746797+00	1477	2025-06-17 15:21:08.746797+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-07	13:30:00	13:00:00	0	1	2025-06-17 15:21:08.843499+00	1479	2025-06-17 15:21:08.843499+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f603-7282-bc16-c98712d0e44d	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-08	16:30:00	16:00:00	0	0	2025-06-17 15:21:08.876908+00	1480	2025-06-17 15:21:08.876908+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-06-24	17:30:00	17:00:00	0	1	2025-06-17 15:21:08.903203+00	1481	2025-06-17 15:21:08.903203+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Потреба в індивідуальному підборі навантажень після хвороби або травми.
2025-07-11	14:30:00	14:00:00	0	1	2025-06-17 15:21:08.926243+00	1482	2025-06-17 15:21:08.926243+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Висипання на шкірі, які викликають сильний свербіж і почервоніння.
2025-07-15	17:30:00	17:00:00	0	0	2025-06-17 15:21:08.941966+00	1483	2025-06-17 15:21:08.941966+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Збільшення лімфатичних вузлів без видимих причин.
2025-07-09	14:00:00	13:30:00	0	1	2025-06-17 15:21:08.971159+00	1484	2025-06-17 15:21:08.971159+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-06-19	14:30:00	14:00:00	0	1	2025-06-17 15:21:09.010653+00	1485	2025-06-17 15:21:09.010653+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-05	10:00:00	09:30:00	0	0	2025-06-17 15:21:09.026879+00	1486	2025-06-17 15:21:09.026879+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-07-04	08:30:00	08:00:00	0	1	2025-06-17 15:21:09.043379+00	1487	2025-06-17 15:21:09.043379+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f469-7573-ba3a-604cd272778f	\N	Відчуття стискання і болю в грудній клітці під час фізичного навантаження.
2025-06-20	14:00:00	13:30:00	0	1	2025-06-17 15:21:09.078216+00	1488	2025-06-17 15:21:09.078216+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Стягнення аліментів із колишнього чоловіка, який виїхав за кордон та ухиляється від сплати.
2025-07-04	13:30:00	13:00:00	0	1	2025-06-17 15:21:09.129214+00	1490	2025-06-17 15:21:09.129214+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-05	23:00:00	22:30:00	0	0	2025-06-17 15:21:09.15517+00	1491	2025-06-17 15:21:09.15517+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-ff63-73de-9441-b73d75415356	\N	Відмова відділу реєстрації у прописці дитини-переселенця у гуртожитку батьків.
2025-07-03	18:30:00	18:00:00	0	1	2025-06-17 15:21:09.168932+00	1492	2025-06-17 15:21:09.168932+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-03	14:30:00	14:00:00	0	0	2025-06-17 15:21:09.188783+00	1493	2025-06-17 15:21:09.188783+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e7a-01ab-77da-bcb4-15df70386907	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-08	16:30:00	16:00:00	0	0	2025-06-17 15:21:09.201933+00	1494	2025-06-17 15:21:09.201933+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-15	16:00:00	15:30:00	2	0	2025-06-17 15:21:09.106247+00	1489	2025-06-17 15:21:09.106247+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-06-21	12:00:00	11:30:00	0	0	2025-06-17 15:21:09.273384+00	1495	2025-06-17 15:21:09.273384+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-04	14:30:00	14:00:00	0	0	2025-06-17 15:21:09.301221+00	1496	2025-06-17 15:21:09.301221+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Різке погіршення функції сечового міхура або кишечника.
2025-07-14	12:00:00	11:30:00	0	1	2025-06-17 15:21:09.316069+00	1497	2025-06-17 15:21:09.316069+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f469-7573-ba3a-604cd272778f	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-12	14:30:00	14:00:00	0	1	2025-06-17 15:21:09.336478+00	1498	2025-06-17 15:21:09.336478+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-06-19	11:30:00	11:00:00	0	0	2025-06-17 15:21:09.362255+00	1499	2025-06-17 15:21:09.362255+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-13	17:30:00	17:00:00	0	0	2025-06-17 15:21:09.39553+00	1501	2025-06-17 15:21:09.39553+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Періодичний біль у попереку, що посилюється при фізичному навантаженні.
2025-07-06	21:30:00	21:00:00	0	0	2025-06-17 15:21:09.41471+00	1502	2025-06-17 15:21:09.41471+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-05	19:30:00	19:00:00	0	1	2025-06-17 15:21:09.431235+00	1503	2025-06-17 15:21:09.431235+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f975-78ec-81e9-6e13cd6d568a	\N	Набряки, почервоніння та підвищена температура у ділянці суглобів.
2025-07-14	12:30:00	12:00:00	0	1	2025-06-17 15:21:09.494322+00	1504	2025-06-17 15:21:09.494322+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-01ab-77da-bcb4-15df70386907	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-21	13:00:00	12:30:00	0	0	2025-06-17 15:21:09.519068+00	1505	2025-06-17 15:21:09.519068+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-07-13	11:30:00	11:00:00	0	1	2025-06-17 15:21:09.536449+00	1506	2025-06-17 15:21:09.536449+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-08	15:00:00	14:30:00	0	1	2025-06-17 15:21:09.551514+00	1507	2025-06-17 15:21:09.551514+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-14	08:30:00	08:00:00	0	0	2025-06-17 15:21:09.576873+00	1508	2025-06-17 15:21:09.576873+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-07	12:30:00	12:00:00	0	1	2025-06-17 15:21:09.598876+00	1509	2025-06-17 15:21:09.598876+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-13	20:00:00	19:30:00	0	0	2025-06-17 15:21:09.639779+00	1510	2025-06-17 15:21:09.639779+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-08	00:30:00	00:00:00	0	1	2025-06-17 15:21:09.654996+00	1511	2025-06-17 15:21:09.654996+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-06	09:30:00	09:00:00	0	1	2025-06-17 15:21:09.671976+00	1512	2025-06-17 15:21:09.671976+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-06-19	14:00:00	13:30:00	0	1	2025-06-17 15:21:09.699641+00	1513	2025-06-17 15:21:09.699641+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-15	15:00:00	14:30:00	0	0	2025-06-17 15:21:09.727215+00	1514	2025-06-17 15:21:09.727215+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-10	21:30:00	21:00:00	0	0	2025-06-17 15:21:09.742199+00	1515	2025-06-17 15:21:09.742199+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-13	17:30:00	17:00:00	0	0	2025-06-17 15:21:09.763127+00	1516	2025-06-17 15:21:09.763127+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-06	19:30:00	19:00:00	0	0	2025-06-17 15:21:09.791193+00	1517	2025-06-17 15:21:09.791193+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Схильність до алергічних реакцій та аутоімунних процесів.
2025-07-08	12:30:00	12:00:00	0	0	2025-06-17 15:21:09.812623+00	1518	2025-06-17 15:21:09.812623+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Хронічний біль у м'язах та суглобах, що ускладнює виконання побутових дій.
2025-07-09	18:00:00	17:30:00	0	0	2025-06-17 15:21:09.826887+00	1519	2025-06-17 15:21:09.826887+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-07-04	10:00:00	09:30:00	0	1	2025-06-17 15:21:09.847418+00	1520	2025-06-17 15:21:09.847418+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-08	14:30:00	14:00:00	0	0	2025-06-17 15:21:09.867072+00	1521	2025-06-17 15:21:09.867072+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Порушення координації, потреба у відновленні навичок самообслуговування.
2025-07-08	14:00:00	13:30:00	0	1	2025-06-17 15:21:09.880016+00	1522	2025-06-17 15:21:09.880016+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-08	08:30:00	08:00:00	0	0	2025-06-17 15:21:09.899835+00	1523	2025-06-17 15:21:09.899835+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f90c-7eff-9b23-192773bf98bb	\N	Обмеження рухливості після переломів або операцій на суглобах.
2025-07-15	16:30:00	16:00:00	0	0	2025-06-17 15:21:09.928015+00	1524	2025-06-17 15:21:09.928015+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Хронічний біль у суглобах після спортивних тренувань.
2025-07-06	12:30:00	12:00:00	0	0	2025-06-17 15:21:09.941643+00	1525	2025-06-17 15:21:09.941643+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Збільшення лімфатичних вузлів без видимих причин.
2025-07-14	12:00:00	11:30:00	0	0	2025-06-17 15:21:09.976559+00	1526	2025-06-17 15:21:09.976559+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-03	20:30:00	20:00:00	0	1	2025-06-17 15:21:09.991176+00	1527	2025-06-17 15:21:09.991176+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-13	13:30:00	13:00:00	0	1	2025-06-17 15:21:10.004277+00	1528	2025-06-17 15:21:10.004277+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-07-09	23:30:00	23:00:00	0	1	2025-06-17 15:21:10.025141+00	1529	2025-06-17 15:21:10.025141+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-0026-79ac-a236-50d54f042a62	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-06-20	14:30:00	14:00:00	0	0	2025-06-17 15:21:10.086566+00	1530	2025-06-17 15:21:10.086566+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-09	18:00:00	17:30:00	0	0	2025-06-17 15:21:10.110505+00	1531	2025-06-17 15:21:10.110505+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-06-20	15:30:00	15:00:00	0	1	2025-06-17 15:21:10.144343+00	1532	2025-06-17 15:21:10.144343+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-10	11:00:00	10:30:00	0	0	2025-06-17 15:21:10.161067+00	1533	2025-06-17 15:21:10.161067+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Часті головні болі, що супроводжуються нудотою та чутливістю до світла.
2025-07-05	16:00:00	15:30:00	0	1	2025-06-17 15:21:10.178504+00	1534	2025-06-17 15:21:10.178504+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-12	18:30:00	18:00:00	0	0	2025-06-17 15:21:10.209929+00	1535	2025-06-17 15:21:10.209929+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f59f-7958-879a-20e6a5dbfbae	\N	Загальна слабкість, нудота, втрата апетиту, що зростає з часом.
2025-07-13	14:30:00	14:00:00	0	1	2025-06-17 15:21:10.23535+00	1536	2025-06-17 15:21:10.23535+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f3fe-7b11-a9c7-e104137309cc	\N	Тривалі напади астми, що погіршуються у присутності домашнього пилу.
2025-07-08	21:00:00	20:30:00	0	1	2025-06-17 15:21:10.26684+00	1537	2025-06-17 15:21:10.26684+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f8b4-7c00-8caa-745ef40934a6	\N	Загальна слабкість, ознаки запалення чи інфекції.
2025-06-21	16:00:00	15:30:00	0	1	2025-06-17 15:21:10.291448+00	1538	2025-06-17 15:21:10.291448+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-07-05	21:30:00	21:00:00	0	0	2025-06-17 15:21:10.304839+00	1539	2025-06-17 15:21:10.304839+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення чутливості та рухової функції кінцівок.
2025-07-07	15:00:00	14:30:00	0	1	2025-06-17 15:21:10.318107+00	1540	2025-06-17 15:21:10.318107+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-09	15:00:00	14:30:00	0	1	2025-06-17 15:21:10.336151+00	1541	2025-06-17 15:21:10.336151+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f469-7573-ba3a-604cd272778f	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-07-12	13:00:00	12:30:00	0	0	2025-06-17 15:21:10.356347+00	1542	2025-06-17 15:21:10.356347+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f788-7b5f-84df-0e1fea0599f3	\N	Біль у горлі, що не минає понад тиждень, труднощі при ковтанні.
2025-07-06	16:00:00	15:30:00	0	1	2025-06-17 15:21:10.384035+00	1544	2025-06-17 15:21:10.384035+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-08	13:30:00	13:00:00	0	1	2025-06-17 15:21:10.398854+00	1545	2025-06-17 15:21:10.398854+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-faa9-7b86-a332-e10ef4ed001b	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-06-19	11:30:00	11:00:00	0	0	2025-06-17 15:21:10.430647+00	1546	2025-06-17 15:21:10.430647+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-05	16:30:00	16:00:00	0	0	2025-06-17 15:21:10.444906+00	1547	2025-06-17 15:21:10.444906+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Позбавлення батьківських прав через систематичне ухилення від виховання дитини.
2025-07-11	22:00:00	21:30:00	0	0	2025-06-17 15:21:10.458554+00	1548	2025-06-17 15:21:10.458554+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-13	16:00:00	15:30:00	0	1	2025-06-17 15:21:10.485189+00	1549	2025-06-17 15:21:10.485189+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-09	12:00:00	11:30:00	0	0	2025-06-17 15:21:10.49992+00	1550	2025-06-17 15:21:10.49992+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Притягнення посадової особи до відповідальності за незаконну відмову у прийомі документів.
2025-07-09	10:00:00	09:30:00	0	1	2025-06-17 15:21:10.576405+00	1551	2025-06-17 15:21:10.576405+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-ff63-73de-9441-b73d75415356	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-07-03	15:00:00	14:30:00	0	1	2025-06-17 15:21:10.599759+00	1552	2025-06-17 15:21:10.599759+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-06	18:30:00	18:00:00	0	0	2025-06-17 15:21:10.638812+00	1553	2025-06-17 15:21:10.638812+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Уповільнений розвиток мовлення, обмежений словниковий запас.
2025-07-13	11:00:00	10:30:00	0	0	2025-06-17 15:21:10.669169+00	1554	2025-06-17 15:21:10.669169+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f542-7b27-95ab-c64f22f444ee	\N	Труднощі у спілкуванні з близькими, внутрішні конфлікти.
2025-07-03	19:00:00	18:30:00	0	0	2025-06-17 15:21:10.690341+00	1555	2025-06-17 15:21:10.690341+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f664-7879-b2ec-fe94caaacc28	\N	Різкі коливання ваги без видимих причин, підвищена чи знижена апетитність.
2025-07-05	12:30:00	12:00:00	0	1	2025-06-17 15:21:10.728114+00	1556	2025-06-17 15:21:10.728114+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f469-7573-ba3a-604cd272778f	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-03	18:30:00	18:00:00	0	1	2025-06-17 15:21:10.75049+00	1557	2025-06-17 15:21:10.75049+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-14	08:30:00	08:00:00	0	1	2025-06-17 15:21:10.801879+00	1558	2025-06-17 15:21:10.801879+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-04	20:30:00	20:00:00	0	1	2025-06-17 15:21:10.818285+00	1559	2025-06-17 15:21:10.818285+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-14	11:00:00	10:30:00	0	0	2025-06-17 15:21:10.834447+00	1560	2025-06-17 15:21:10.834447+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-05	21:30:00	21:00:00	0	0	2025-06-17 15:21:10.857546+00	1561	2025-06-17 15:21:10.857546+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-06	14:00:00	13:30:00	0	1	2025-06-17 15:21:10.906344+00	1562	2025-06-17 15:21:10.906344+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-07-08	10:00:00	09:30:00	0	0	2025-06-17 15:21:10.928436+00	1563	2025-06-17 15:21:10.928436+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-07-10	15:00:00	14:30:00	0	1	2025-06-17 15:21:10.945278+00	1564	2025-06-17 15:21:10.945278+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-05	12:30:00	12:00:00	0	0	2025-06-17 15:21:10.964238+00	1565	2025-06-17 15:21:10.964238+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-09	15:00:00	14:30:00	0	1	2025-06-17 15:21:11.017415+00	1566	2025-06-17 15:21:11.017415+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-07	10:00:00	09:30:00	0	0	2025-06-17 15:21:11.034322+00	1567	2025-06-17 15:21:11.034322+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-f603-7282-bc16-c98712d0e44d	\N	Порушення стільця: запори, діарея, або їх чергування без явної причини.
2025-07-06	19:30:00	19:00:00	0	0	2025-06-17 15:21:11.067269+00	1568	2025-06-17 15:21:11.067269+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-11	18:00:00	17:30:00	0	1	2025-06-17 15:21:11.083149+00	1569	2025-06-17 15:21:11.083149+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-07-13	17:30:00	17:00:00	0	0	2025-06-17 15:21:11.097988+00	1570	2025-06-17 15:21:11.097988+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Порушення вимови окремих звуків у дитини після 4 років.
2025-07-03	12:00:00	11:30:00	0	1	2025-06-17 15:21:11.154883+00	1571	2025-06-17 15:21:11.154883+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Поганий апетит, порушення сну, плаксивість у дитини.
2025-07-07	13:00:00	12:30:00	0	1	2025-06-17 15:21:11.207205+00	1572	2025-06-17 15:21:11.207205+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-07-13	00:30:00	00:00:00	0	1	2025-06-17 15:21:11.221361+00	1573	2025-06-17 15:21:11.221361+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-ff63-73de-9441-b73d75415356	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-14	13:00:00	12:30:00	0	1	2025-06-17 15:21:11.235472+00	1574	2025-06-17 15:21:11.235472+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-08	19:30:00	19:00:00	0	1	2025-06-17 15:21:11.256165+00	1575	2025-06-17 15:21:11.256165+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-07-04	18:00:00	17:30:00	0	1	2025-06-17 15:21:11.275744+00	1576	2025-06-17 15:21:11.275744+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fb15-743c-9d5a-b00ac2953735	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-07-07	20:00:00	19:30:00	0	1	2025-06-17 15:21:11.305317+00	1577	2025-06-17 15:21:11.305317+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-06-19	15:30:00	15:00:00	0	1	2025-06-17 15:21:11.337019+00	1578	2025-06-17 15:21:11.337019+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-11	20:00:00	19:30:00	0	1	2025-06-17 15:21:11.35018+00	1579	2025-06-17 15:21:11.35018+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-09	20:00:00	19:30:00	0	0	2025-06-17 15:21:11.370185+00	1580	2025-06-17 15:21:11.370185+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-05	00:00:00	23:30:00	0	1	2025-06-17 15:21:11.397485+00	1581	2025-06-17 15:21:11.397485+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-24	13:00:00	12:30:00	0	1	2025-06-17 15:21:11.429482+00	1582	2025-06-17 15:21:11.429482+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	\N	Зниження концентрації уваги, забудькуватість, проблеми з пам’яттю.
2025-07-14	14:30:00	14:00:00	0	0	2025-06-17 15:21:11.443542+00	1583	2025-06-17 15:21:11.443542+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	\N	Заїкання, неритмічне мовлення, особливо в емоційно напружених ситуаціях.
2025-07-15	19:00:00	18:30:00	0	1	2025-06-17 15:21:11.495407+00	1584	2025-06-17 15:21:11.495407+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	\N	Затримка розвитку, відставання у вазі та зрості.
2025-07-13	19:00:00	18:30:00	0	0	2025-06-17 15:21:11.521571+00	1585	2025-06-17 15:21:11.521571+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-07-14	12:30:00	12:00:00	0	0	2025-06-17 15:21:11.563871+00	1587	2025-06-17 15:21:11.563871+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-07-11	20:00:00	19:30:00	0	0	2025-06-17 15:21:11.583177+00	1588	2025-06-17 15:21:11.583177+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-10	20:00:00	19:30:00	0	0	2025-06-17 15:21:11.612728+00	1589	2025-06-17 15:21:11.612728+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Відмова держоргану в забезпеченні безплатним проїздом для ВПО при евакуації.
2025-07-07	21:00:00	20:30:00	0	0	2025-06-17 15:21:11.636315+00	1590	2025-06-17 15:21:11.636315+00	01977e79-eada-7090-8882-1256bfe48b6c	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Оскарження протоколу про адміністративне правопорушення, складеного із порушенням процедури.
2025-07-06	14:30:00	14:00:00	0	1	2025-06-17 15:21:11.65962+00	1591	2025-06-17 15:21:11.65962+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-10	10:00:00	09:30:00	0	0	2025-06-17 15:21:11.691367+00	1592	2025-06-17 15:21:11.691367+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-07-06	08:30:00	08:00:00	0	1	2025-06-17 15:21:11.772148+00	1594	2025-06-17 15:21:11.772148+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-ff63-73de-9441-b73d75415356	\N	Оскарження відмови ДМС у продовженні посвідки на тимчасове проживання іноземця-волонтера.
2025-07-12	20:30:00	20:00:00	0	1	2025-06-17 15:21:11.787019+00	1595	2025-06-17 15:21:11.787019+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-fa48-76ee-b5ac-b933c85acc84	\N	Підготовка документів для набуття статусу біженця в ЄС після виїзду з України.
2025-07-15	18:00:00	17:30:00	0	0	2025-06-17 15:21:11.81007+00	1596	2025-06-17 15:21:11.81007+00	01977e79-f1a4-7811-840e-404568a4376e	01977e7a-014a-74b6-81f5-7e8e435a3d75	\N	Визнання протиправною бездіяльності держслужбовця щодо видачі дозволу на будівництво.
2025-07-11	08:30:00	08:00:00	0	0	2025-06-17 15:21:11.825719+00	1597	2025-06-17 15:21:11.825719+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-20	08:30:00	08:00:00	2	1	2025-06-17 15:20:19.856754+00	14	2025-06-17 15:20:19.856754+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Реєстратор відмовив у внесенні права власності на земельну ділянку через збій реєстру.
2025-06-20	10:00:00	09:30:00	1	1	2025-06-17 15:20:21.421819+00	35	2025-06-17 15:20:21.421819+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-ffca-7f6d-8b09-70a69d42bf8c	{"reason":"Успішно проведено консультацію"}	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-20	12:00:00	11:30:00	2	1	2025-06-17 15:21:01.176191+00	1210	2025-06-17 15:21:01.176191+00	01977e79-e89a-74ae-8914-3607235689dd	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-26	08:30:00	08:00:00	2	1	2025-06-17 15:20:19.682724+00	11	2025-06-17 15:20:19.682724+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f272-7f27-bed4-a81b480f17fd	\N	Підвищення температури тіла, озноб, загальна слабкість і біль у м’язах.
2025-06-19	08:30:00	08:00:00	1	0	2025-06-17 15:20:26.162913+00	118	2025-06-17 15:20:26.162913+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fca6-7b48-a3a4-2c362c16081c	{"reason":"квакча удалась"}	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-06-19	14:00:00	13:30:00	2	0	2025-06-17 15:20:52.673805+00	912	2025-06-17 15:20:52.673805+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-07-04	14:00:00	13:30:00	2	0	2025-06-17 15:20:51.30104+00	869	2025-06-17 15:20:51.30104+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-07-04	11:00:00	10:30:00	2	0	2025-06-17 15:20:59.095796+00	1131	2025-06-17 15:20:59.095796+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-19	10:00:00	09:30:00	2	0	2025-06-17 15:20:29.562563+00	191	2025-06-17 15:20:29.562563+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-04	18:00:00	17:30:00	1	1	2025-06-17 15:20:33.65709+00	296	2025-06-17 15:20:33.65709+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-fca6-7b48-a3a4-2c362c16081c	{"reason":"тест\\n1229"}	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-07-04	20:30:00	20:00:00	2	0	2025-06-17 15:20:45.300847+00	656	2025-06-17 15:20:45.300847+00	01977e79-f007-77be-ab68-b61fa9a91a6c	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-07-04	17:00:00	16:30:00	2	0	2025-06-17 15:20:40.58707+00	504	2025-06-17 15:20:40.58707+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-19	15:00:00	14:30:00	2	0	2025-06-17 15:21:05.828559+00	1370	2025-06-17 15:21:05.828559+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-06-19	17:30:00	17:00:00	1	0	2025-06-17 15:21:08.796166+00	1478	2025-06-17 15:21:08.796166+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-fca6-7b48-a3a4-2c362c16081c	{"reason":"все окей"}	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-22	08:30:00	08:00:00	0	1	2025-06-21 03:43:30.62455+00	1602	2025-06-21 03:43:30.62455+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	памагги
2025-06-19	08:30:00	08:00:00	2	0	2025-06-17 15:20:21.294921+00	34	2025-06-17 15:20:21.294921+00	01977e79-f210-7ff9-a57a-ff1746221754	01977e79-f2d6-7075-a662-c4d24ed8bb3e	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-06-26	14:30:00	14:00:00	2	1	2025-06-22 13:06:05.553349+00	1853	2025-06-22 13:06:05.553349+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	kofjiroeg
2025-07-06	13:30:00	13:00:00	2	1	2025-06-17 15:21:10.370921+00	1543	2025-06-17 15:21:10.370921+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	Відмова у видачі довідки ВПО після повторної реєстрації за новою адресою.
2025-07-03	10:00:00	09:30:00	0	1	2025-06-22 16:42:41.590963+00	1904	2025-06-22 16:42:41.590963+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	удзлщазоушпк\n\n
2025-06-27	15:00:00	14:30:00	2	1	2025-06-22 16:39:59.501503+00	1902	2025-06-22 16:39:59.501503+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	fjeopjfep
2025-06-19	08:30:00	08:00:00	3	1	2025-06-17 15:20:19.213097+00	5	2025-06-17 15:20:19.213097+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-19	08:30:00	08:00:00	3	0	2025-06-17 15:20:19.723197+00	12	2025-06-17 15:20:19.723197+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-06-20	00:30:00	00:00:00	3	1	2025-06-17 15:20:19.823656+00	13	2025-06-17 15:20:19.823656+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-19	08:30:00	08:00:00	3	1	2025-06-17 15:20:19.283807+00	6	2025-06-17 15:20:19.283807+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Спір між співвласниками квартири щодо порядку користування спільним майном.
2025-06-21	10:00:00	09:30:00	3	1	2025-06-17 15:20:24.110462+00	81	2025-06-17 15:20:24.110462+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-06-19	00:30:00	00:00:00	3	1	2025-06-17 15:20:24.541366+00	89	2025-06-17 15:20:24.541366+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f603-7282-bc16-c98712d0e44d	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-06-19	08:30:00	08:00:00	3	1	2025-06-17 15:20:24.657367+00	92	2025-06-17 15:20:24.657367+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Поява висипань, збільшення лімфатичних вузлів, болючість при пальпації.
2025-06-20	09:30:00	09:00:00	3	1	2025-06-17 15:20:25.058814+00	97	2025-06-17 15:20:25.058814+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Стабільне підвищення артеріального тиску, що не реагує на базові медикаменти.
2025-06-19	09:30:00	09:00:00	3	1	2025-06-17 15:20:25.089709+00	98	2025-06-17 15:20:25.089709+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-06-21	11:30:00	11:00:00	3	0	2025-06-17 15:20:25.714051+00	111	2025-06-17 15:20:25.714051+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-06-20	11:30:00	11:00:00	3	0	2025-06-17 15:20:26.014735+00	117	2025-06-17 15:20:26.014735+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-23	08:30:00	08:00:00	3	0	2025-06-21 21:00:20.569664+00	1702	2025-06-21 21:00:20.569664+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-f7e9-7b37-ac67-06a8797bd7d4	{"reason":"завершно"}	тестова квакча
2025-06-21	12:30:00	12:00:00	3	1	2025-06-17 15:20:26.369943+00	123	2025-06-17 15:20:26.369943+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Різке погіршення функції сечового міхура або кишечника.
2025-06-20	10:30:00	10:00:00	3	1	2025-06-17 15:20:26.447388+00	125	2025-06-17 15:20:26.447388+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Набряки ніг до кінця дня, посилення втоми і слабкості.
2025-06-19	09:30:00	09:00:00	3	1	2025-06-17 15:20:27.114744+00	137	2025-06-17 15:20:27.114744+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e7a-008b-7cb4-b539-be89d4f12cfc	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-06-20	08:30:00	08:00:00	3	0	2025-06-17 15:20:27.164213+00	139	2025-06-17 15:20:27.164213+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-ff63-73de-9441-b73d75415356	\N	Супровід процедури отримання громадянства України дитиною, народженою за кордоном.
2025-06-19	08:30:00	08:00:00	3	1	2025-06-17 15:20:27.236667+00	141	2025-06-17 15:20:27.236667+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-fe37-75a1-8b40-2fc606a56218	\N	Скасування рішення місцевої ради про самовільне підвищення тарифів на комунальні послуги.
2025-06-20	08:30:00	08:00:00	3	1	2025-06-17 15:20:27.784944+00	152	2025-06-17 15:20:27.784944+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-20	09:30:00	09:00:00	3	1	2025-06-17 15:20:28.450453+00	166	2025-06-17 15:20:28.450453+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e79-ff63-73de-9441-b73d75415356	\N	Скасування заборони в’їзду іноземному громадянину через помилку прикордонної служби.
2025-06-20	10:00:00	09:30:00	3	1	2025-06-17 15:20:29.891847+00	197	2025-06-17 15:20:29.891847+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-06-19	08:30:00	08:00:00	3	0	2025-06-17 15:20:30.052001+00	203	2025-06-17 15:20:30.052001+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-19	10:00:00	09:30:00	3	1	2025-06-17 15:20:29.759054+00	196	2025-06-17 15:20:29.759054+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	Тривалий сухий або вологий кашель, іноді з виділенням мокротиння.
2025-06-20	12:30:00	12:00:00	3	0	2025-06-17 15:20:31.340059+00	235	2025-06-17 15:20:31.340059+00	01977e79-e89a-74ae-8914-3607235689dd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-06-20	16:00:00	15:30:00	3	1	2025-06-17 15:47:21.885796+00	1599	2025-06-17 15:47:21.885796+00	01977e7c-e1ea-7674-a453-c35ee7829b40	01977e79-ff63-73de-9441-b73d75415356	\N	тестовий запис 2
2025-06-20	12:00:00	11:30:00	3	1	2025-06-17 15:20:31.764842+00	246	2025-06-17 15:20:31.764842+00	01977e79-ef42-7285-bf99-a45e727741b8	01977e79-f336-722a-8a36-233ae9fe22f7	\N	Часті серцебиття, перебої у роботі серця, відчуття нестачі повітря.
2025-06-19	11:30:00	11:00:00	3	0	2025-06-17 15:20:32.016292+00	252	2025-06-17 15:20:32.016292+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fca6-7b48-a3a4-2c362c16081c	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-20	14:00:00	13:30:00	3	0	2025-06-17 15:20:32.57433+00	269	2025-06-17 15:20:32.57433+00	01977e79-f0db-7638-8703-6f6a9ce99f22	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-19	09:30:00	09:00:00	3	1	2025-06-17 15:20:33.149863+00	283	2025-06-17 15:20:33.149863+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd6e-737b-bdb8-d563d1ac8899	\N	Спадковий спір між братами щодо розподілу приватного будинку та земельної ділянки.
2025-07-06	11:30:00	11:00:00	0	1	2025-07-05 02:55:47.486758+00	2102	2025-07-05 02:55:47.486758+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-fc3d-7e26-94be-b6531796eb62	\N	notch\n
2025-06-19	10:00:00	09:30:00	3	0	2025-06-17 15:20:33.24462+00	284	2025-06-17 15:20:33.24462+00	01977e79-f1a4-7811-840e-404568a4376e	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Неправильне нарахування пенсії після переміщення до іншого регіону.
2025-06-21	14:00:00	13:30:00	3	0	2025-06-17 15:20:33.39009+00	287	2025-06-17 15:20:33.39009+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-06-20	08:30:00	08:00:00	3	0	2025-06-17 15:20:33.606917+00	294	2025-06-17 15:20:33.606917+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	01977e7a-01ab-77da-bcb4-15df70386907	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-20	15:30:00	15:00:00	3	1	2025-06-17 15:20:34.202186+00	315	2025-06-17 15:20:34.202186+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Накладення арешту на квартиру без належної ухвали суду — вимога зняти обтяження.
2025-06-19	11:00:00	10:30:00	3	0	2025-06-17 15:20:34.266448+00	318	2025-06-17 15:20:34.266448+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fb74-7d65-8dd8-fb25557c7426	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-06-20	08:30:00	08:00:00	3	0	2025-06-17 15:20:34.899194+00	335	2025-06-17 15:20:34.899194+00	01977e79-f146-7970-be87-46500bbd6779	01977e7a-0026-79ac-a236-50d54f042a62	\N	Виселення орендаря без рішення суду та без належного повідомлення.
2025-06-20	11:00:00	10:30:00	3	0	2025-06-17 15:20:35.318597+00	349	2025-06-17 15:20:35.318597+00	01977e79-ee12-7598-b6d1-f711a70583a5	01977e79-fdcd-7647-9bfc-c4f208c7d607	\N	Затримка виплати допомоги по безробіттю понад встановлений законом строк.
2025-06-20	10:00:00	09:30:00	3	1	2025-06-17 15:20:35.915897+00	367	2025-06-17 15:20:35.915897+00	01977e79-f074-779b-9674-3b07f474ce2c	01977e7a-0026-79ac-a236-50d54f042a62	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-06-20	08:30:00	08:00:00	3	1	2025-06-17 15:20:36.354306+00	378	2025-06-17 15:20:36.354306+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Встановлення порядку спілкування батька з дитиною після розлучення батьків-переселенців.
2025-06-21	15:00:00	14:30:00	3	1	2025-06-17 15:20:36.539011+00	385	2025-06-17 15:20:36.539011+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e79-f727-734f-abdb-bcd9c7e3ec00	\N	Відчуття оніміння і поколювання в ділянці шиї або попереку.
2025-06-23	08:30:00	08:00:00	3	1	2025-06-21 21:20:50.626049+00	1704	2025-06-21 21:20:50.626049+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	\N	паевкапнгрр
2025-06-20	16:30:00	16:00:00	3	1	2025-06-17 15:20:36.770994+00	393	2025-06-17 15:20:36.770994+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-ffca-7f6d-8b09-70a69d42bf8c	\N	Спроба рейдерського захоплення складу під виглядом фіктивного договору оренди.
2025-06-20	11:00:00	10:30:00	3	0	2025-06-17 15:20:37.89686+00	417	2025-06-17 15:20:37.89686+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	01977e7a-0026-79ac-a236-50d54f042a62	\N	Відсутність компенсації за пошкоджене житло після обстрілу — необхідність звернення до суду.
2025-06-19	08:30:00	08:00:00	3	0	2025-06-17 15:20:37.934878+00	418	2025-06-17 15:20:37.934878+00	01977e79-eda2-7ae5-8b09-e1073fda1348	01977e7a-00eb-72f3-8dd8-014dcf711be4	\N	Відмова у призначенні субсидії через відсутність прописки на новому місці проживання.
2025-06-20	09:30:00	09:00:00	3	0	2025-06-17 15:20:37.987041+00	420	2025-06-17 15:20:37.987041+00	01977e79-eee1-75ec-9162-a1f466f494e2	01977e79-fd05-7a4c-9854-1f283f24c417	\N	Поділ спільно набутого майна подружжя, що залишилось у прифронтовій зоні.
2025-07-04	09:30:00	09:00:00	2	1	2025-06-17 15:20:57.98593+00	1092	2025-06-17 15:20:57.98593+00	01977e79-ee76-703d-a9b1-6501c51b721a	01977e79-f603-7282-bc16-c98712d0e44d	\N	Періодичні болі в животі, що посилюються після прийому їжі, здуття.
2025-07-03	11:30:00	11:00:00	2	0	2025-06-17 15:20:46.723544+00	706	2025-06-17 15:20:46.723544+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f603-7282-bc16-c98712d0e44d	\N	Сильні болі у спині, що не проходять при консервативному лікуванні.
2025-07-03	12:30:00	12:00:00	2	0	2025-06-17 15:21:06.329485+00	1389	2025-06-17 15:21:06.329485+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-f6c6-7707-aedb-fe3468f41140	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-03	10:30:00	10:00:00	2	1	2025-06-17 15:21:11.747847+00	1593	2025-06-17 15:21:11.747847+00	01977e79-efa8-720f-a2d9-9538e1f05127	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Зняття з обліку одержувача соціальної допомоги без попереднього повідомлення.
2025-07-07	10:00:00	09:30:00	0	0	2025-07-04 22:53:39.560057+00	2052	2025-07-04 22:53:39.560057+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	repeateble_read test
2025-07-04	14:30:00	14:00:00	2	0	2025-06-17 15:20:57.086307+00	1057	2025-06-17 15:20:57.086307+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	01977e79-fc3d-7e26-94be-b6531796eb62	\N	Позбавлення соціальної допомоги на дітей-переселенців без офіційного повідомлення.
2025-07-09	08:30:00	08:00:00	2	1	2025-06-17 15:20:52.605317+00	911	2025-06-17 15:20:52.605317+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f469-7573-ba3a-604cd272778f	\N	Нудота, блювання, діарея на фоні підвищеної температури.
2025-07-09	08:30:00	08:00:00	2	1	2025-07-07 13:52:10.931241+00	2302	2025-07-07 13:52:10.931241+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	weyeuy7u345
2025-07-09	08:30:00	08:00:00	0	1	2025-07-07 13:52:58.542016+00	2303	2025-07-07 13:52:58.542016+00	01977e79-f146-7970-be87-46500bbd6779	01977e79-f7e9-7b37-ac67-06a8797bd7d4	\N	tyeuyeuy
\.


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authorities (id, authority) FROM stdin;
1	ROLE_ANONYMOUS
2	ROLE_UNCONFIRMED_USER
3	ROLE_USER
4	ROLE_CUSTOMER
5	ROLE_DOCTOR
6	ROLE_JURIST
7	ROLE_ADMIN
8	ROLE_SCHEDULE_TASK_SERVICE
9	ROLE_PROMETHEUS_SERVICE
\.


--
-- Data for Name: avatars; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.avatars (id, user_id, avatar_url, created_at, updated_at) FROM stdin;
01979008-8898-7a40-817f-50f4e81220d1	01977e7a-01ab-77da-bcb4-15df70386907	https://aidcompass.blob.core.windows.net/avatars/01977e7a-01ab-77da-bcb4-15df70386907-avatar.jpg	2025-06-21 01:09:27.845188+00	2025-06-21 01:09:27.845188+00
0197900c-fd1d-7f93-9ec1-1c5888f6e494	01977ecf-4f42-72b5-8a44-701a5fdb4a9e	https://aidcompass.blob.core.windows.net/avatars/01977ecf-4f42-72b5-8a44-701a5fdb4a9e-avatar.jpg	2025-06-21 01:14:19.807804+00	2025-06-21 01:14:19.807804+00
0197900d-d601-7738-8407-0c82f9087803	01977e79-efa8-720f-a2d9-9538e1f05127	https://aidcompass.blob.core.windows.net/avatars/01977e79-efa8-720f-a2d9-9538e1f05127-avatar.jpg	2025-06-21 01:15:15.332741+00	2025-06-21 01:15:15.332741+00
0197900e-92a6-729a-bf84-031cee441156	01977e79-f272-7f27-bed4-a81b480f17fd	https://aidcompass.blob.core.windows.net/avatars/01977e79-f272-7f27-bed4-a81b480f17fd-avatar.jpg	2025-06-21 01:16:03.624277+00	2025-06-21 01:16:03.624277+00
01979012-ed7b-7bee-af72-a72670d1c6e7	01977e79-eee1-75ec-9162-a1f466f494e2	https://aidcompass.blob.core.windows.net/avatars/01977e79-eee1-75ec-9162-a1f466f494e2-avatar.jpg	2025-06-21 01:20:49.02104+00	2025-06-21 01:20:49.02104+00
01979013-8728-7839-8ce3-c3ae59aede26	01977e79-f0db-7638-8703-6f6a9ce99f22	https://aidcompass.blob.core.windows.net/avatars/01977e79-f0db-7638-8703-6f6a9ce99f22-avatar.jpg	2025-06-21 01:21:28.363435+00	2025-06-21 01:21:28.363435+00
01979014-050f-7dac-b812-8858874f6e14	01977e79-f542-7b27-95ab-c64f22f444ee	https://aidcompass.blob.core.windows.net/avatars/01977e79-f542-7b27-95ab-c64f22f444ee-avatar.jpg	2025-06-21 01:22:00.593837+00	2025-06-21 01:22:00.593837+00
01979016-bab0-7a49-beb3-8c84243e29d8	01977e79-fa48-76ee-b5ac-b933c85acc84	https://aidcompass.blob.core.windows.net/avatars/01977e79-fa48-76ee-b5ac-b933c85acc84-avatar.jpg	2025-06-21 01:24:58.162676+00	2025-06-21 01:24:58.162676+00
01979017-7869-7ca1-abb5-fdd06e10fb63	01977e79-faa9-7b86-a332-e10ef4ed001b	https://aidcompass.blob.core.windows.net/avatars/01977e79-faa9-7b86-a332-e10ef4ed001b-avatar.jpg	2025-06-21 01:25:46.731013+00	2025-06-21 01:25:46.731013+00
01979017-f318-7a27-88b8-86425589e811	01977e79-fb15-743c-9d5a-b00ac2953735	https://aidcompass.blob.core.windows.net/avatars/01977e79-fb15-743c-9d5a-b00ac2953735-avatar.jpg	2025-06-21 01:26:18.138016+00	2025-06-21 01:26:18.138016+00
01979018-f114-7255-a716-0b6bb4a56f14	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	https://aidcompass.blob.core.windows.net/avatars/01977e79-fe99-7c06-9eaf-c6db0f1ddd60-avatar.jpg	2025-06-21 01:27:23.157639+00	2025-06-21 01:27:23.157639+00
01979023-7d83-70aa-9cd5-bf4aece468dd	01977e79-fca6-7b48-a3a4-2c362c16081c	https://aidcompass.blob.core.windows.net/avatars/01977e79-fca6-7b48-a3a4-2c362c16081c-avatar.jpg	2025-06-21 01:38:54.470265+00	2025-06-21 01:38:54.470265+00
0197908c-0da2-7882-96be-b5d2b0b92944	01977e79-e89a-74ae-8914-3607235689dd	https://aidcompass.blob.core.windows.net/avatars/01977e79-e89a-74ae-8914-3607235689dd-avatar.jpg	2025-06-21 03:33:07.118567+00	2025-06-21 03:33:07.118567+00
01979094-99d0-7d09-b4ed-f2508bc1bba3	01977e79-f210-7ff9-a57a-ff1746221754	https://aidcompass.blob.core.windows.net/avatars/01977e79-f210-7ff9-a57a-ff1746221754-avatar.jpg	2025-06-21 03:42:27.285018+00	2025-06-21 03:42:27.285018+00
01979097-cac3-77f0-9035-3a2847901957	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	https://aidcompass.blob.core.windows.net/avatars/01977e79-f849-7f1a-a0c1-70bc3d7dae0c-avatar.jpg	2025-06-21 03:45:56.422213+00	2025-06-21 03:45:56.422213+00
01979098-f6e0-7da2-83a7-8a8e061f53a6	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	https://aidcompass.blob.core.windows.net/avatars/01977e79-ed42-78d3-87ff-6fcc0b3d6de5-avatar.jpg	2025-06-21 03:47:13.25105+00	2025-06-21 03:47:13.25105+00
0197944d-a35c-730c-9312-fba7611d0ec3	01977e79-f7e9-7b37-ac67-06a8797bd7d4	https://aidcompass.blob.core.windows.net/avatars/01977e79-f7e9-7b37-ac67-06a8797bd7d4-avatar.jpg	2025-06-21 21:03:25.535019+00	2025-06-21 21:03:25.535019+00
0197944e-41f9-7c4e-b068-325d0c376620	01977e79-eda2-7ae5-8b09-e1073fda1348	https://aidcompass.blob.core.windows.net/avatars/01977e79-eda2-7ae5-8b09-e1073fda1348-avatar.jpg	2025-06-21 21:04:06.139319+00	2025-06-21 21:04:06.139319+00
01979490-e50e-771e-bd5e-90dd84525962	01977e79-fd05-7a4c-9854-1f283f24c417	https://aidcompass.blob.core.windows.net/avatars/01977e79-fd05-7a4c-9854-1f283f24c417-avatar.jpg	2025-06-21 22:16:53.278612+00	2025-06-21 22:16:53.278612+00
019794a7-cc90-7269-9ce4-85d44fc880cd	01977e79-f1a4-7811-840e-404568a4376e	https://aidcompass.blob.core.windows.net/avatars/01977e79-f1a4-7811-840e-404568a4376e-avatar.jpg	2025-06-21 22:41:54.329995+00	2025-06-21 22:41:54.329995+00
01979016-058a-76a6-be51-20bcba338a2a	01977e79-f727-734f-abdb-bcd9c7e3ec00	https://aidcompass.blob.core.windows.net/avatars/01977e79-f727-734f-abdb-bcd9c7e3ec00-avatar.jpg	2025-06-21 01:24:11.788331+00	2025-06-21 23:08:09.71198+00
019794c2-3aee-72cc-9fc6-ccd301f1332d	01977e79-ec66-7a5c-a3fa-859bde202bcd	https://aidcompass.blob.core.windows.net/avatars/01977e79-ec66-7a5c-a3fa-859bde202bcd-avatar.jpg	2025-06-21 23:10:46.514554+00	2025-06-21 23:11:41.720986+00
01979014-fde9-76aa-be91-622ff73b48d5	01977e79-f788-7b5f-84df-0e1fea0599f3	https://aidcompass.blob.core.windows.net/avatars/01977e79-f788-7b5f-84df-0e1fea0599f3-avatar.jpg	2025-06-21 01:23:04.29989+00	2025-06-21 23:12:30.503079+00
0197c766-cb96-7233-9c70-6e4bc74490a2	0197c761-f334-7314-be66-7ed27933b7be	https://aidcompass.blob.core.windows.net/avatars/0197c761-f334-7314-be66-7ed27933b7be-avatar.jpg	2025-07-01 19:11:32.249178+00	2025-07-01 19:11:32.249178+00
\.


--
-- Data for Name: contact_progreses; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.contact_progreses (filled_email, filled_phone, user_id) FROM stdin;
t	t	01977e79-e89a-74ae-8914-3607235689dd
t	t	01977e79-ea6e-77ae-b3d1-3eec79e27af2
t	t	01977e79-eada-7090-8882-1256bfe48b6c
t	t	01977e79-eb5c-7d9d-9999-16de2fa2b5ca
t	t	01977e79-ebea-7cdd-a03a-ad8365e0b5b6
t	t	01977e79-ec66-7a5c-a3fa-859bde202bcd
t	t	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda
t	t	01977e79-ed42-78d3-87ff-6fcc0b3d6de5
t	t	01977e79-eda2-7ae5-8b09-e1073fda1348
t	t	01977e79-ee12-7598-b6d1-f711a70583a5
t	t	01977e79-ee76-703d-a9b1-6501c51b721a
t	t	01977e79-eee1-75ec-9162-a1f466f494e2
t	t	01977e79-ef42-7285-bf99-a45e727741b8
t	t	01977e79-efa8-720f-a2d9-9538e1f05127
t	t	01977e79-f007-77be-ab68-b61fa9a91a6c
t	t	01977e79-f074-779b-9674-3b07f474ce2c
t	t	01977e79-f0db-7638-8703-6f6a9ce99f22
t	t	01977e79-f146-7970-be87-46500bbd6779
t	t	01977e79-f1a4-7811-840e-404568a4376e
t	t	01977e79-f210-7ff9-a57a-ff1746221754
t	t	01977e79-f272-7f27-bed4-a81b480f17fd
t	t	01977e79-f2d6-7075-a662-c4d24ed8bb3e
t	t	01977e79-f336-722a-8a36-233ae9fe22f7
t	t	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
t	t	01977e79-f3fe-7b11-a9c7-e104137309cc
t	t	01977e79-f469-7573-ba3a-604cd272778f
t	t	01977e79-f4d4-765d-8949-0cea4be46b5c
t	t	01977e79-f542-7b27-95ab-c64f22f444ee
t	t	01977e79-f59f-7958-879a-20e6a5dbfbae
t	t	01977e79-f603-7282-bc16-c98712d0e44d
t	t	01977e79-f664-7879-b2ec-fe94caaacc28
t	t	01977e79-f6c6-7707-aedb-fe3468f41140
t	t	01977e79-f727-734f-abdb-bcd9c7e3ec00
t	t	01977e79-f788-7b5f-84df-0e1fea0599f3
t	t	01977e79-f7e9-7b37-ac67-06a8797bd7d4
t	t	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
t	t	01977e79-f8b4-7c00-8caa-745ef40934a6
t	t	01977e79-f90c-7eff-9b23-192773bf98bb
t	t	01977e79-f975-78ec-81e9-6e13cd6d568a
t	t	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
t	t	01977e79-fa48-76ee-b5ac-b933c85acc84
t	t	01977e79-faa9-7b86-a332-e10ef4ed001b
t	t	01977e79-fb15-743c-9d5a-b00ac2953735
t	t	01977e79-fb74-7d65-8dd8-fb25557c7426
t	t	01977e79-fbdd-7845-982f-a684fb6c9078
t	t	01977e79-fc3d-7e26-94be-b6531796eb62
t	t	01977e79-fca6-7b48-a3a4-2c362c16081c
t	t	01977e79-fd05-7a4c-9854-1f283f24c417
t	t	01977e79-fd6e-737b-bdb8-d563d1ac8899
t	t	01977e79-fdcd-7647-9bfc-c4f208c7d607
t	t	01977e79-fe37-75a1-8b40-2fc606a56218
t	t	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
t	t	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
t	t	01977e79-ff63-73de-9441-b73d75415356
t	t	01977e79-ffca-7f6d-8b09-70a69d42bf8c
t	t	01977e7a-0026-79ac-a236-50d54f042a62
t	t	01977e7a-008b-7cb4-b539-be89d4f12cfc
t	t	01977e7a-00eb-72f3-8dd8-014dcf711be4
t	t	01977e7a-014a-74b6-81f5-7e8e435a3d75
t	t	01977e7a-01ab-77da-bcb4-15df70386907
t	t	01977e7c-e1ea-7674-a453-c35ee7829b40
t	t	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
t	t	0197c761-f334-7314-be66-7ed27933b7be
t	t	0197c2a9-3654-765a-8960-d68688042130
\.


--
-- Data for Name: contact_types; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.contact_types (id, type) FROM stdin;
1	EMAIL
2	PHONE_NUMBER
\.


--
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.contacts (is_confirmed, is_linked_to, is_primary, type_id, created_at, id, updated_at, owner_id, contact) FROM stdin;
t	f	t	1	2025-06-17 15:20:08.098524+00	1	2025-06-17 15:20:08.098524+00	01977e79-e89a-74ae-8914-3607235689dd	yuliia.koval846@gmail.com
t	f	t	1	2025-06-17 15:20:08.561518+00	2	2025-06-17 15:20:08.561518+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	marina.koval396@gmail.com
t	f	t	1	2025-06-17 15:20:08.669974+00	3	2025-06-17 15:20:08.669974+00	01977e79-eada-7090-8882-1256bfe48b6c	andrii.kryva192@gmail.com
t	f	t	1	2025-06-17 15:20:08.800609+00	4	2025-06-17 15:20:08.800609+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	dmytro.bondar314@gmail.com
t	f	t	1	2025-06-17 15:20:08.941831+00	5	2025-06-17 15:20:08.941831+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	iryna.melnyk771@gmail.com
t	f	t	1	2025-06-17 15:20:09.06438+00	6	2025-06-17 15:20:09.06438+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	oksana.kovale452@gmail.com
t	f	t	1	2025-06-17 15:20:09.176954+00	7	2025-06-17 15:20:09.176954+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	kateryna.shevchenko489@gmail.com
t	f	t	1	2025-06-17 15:20:09.380529+00	9	2025-06-17 15:20:09.380529+00	01977e79-eda2-7ae5-8b09-e1073fda1348	iryna.melnyk834@gmail.com
t	f	t	1	2025-06-17 15:20:09.493617+00	10	2025-06-17 15:20:09.493617+00	01977e79-ee12-7598-b6d1-f711a70583a5	dmytro.tkachuk209@gmail.com
t	f	t	1	2025-06-17 15:20:09.592869+00	11	2025-06-17 15:20:09.592869+00	01977e79-ee76-703d-a9b1-6501c51b721a	kateryna.petrynko663@gmail.com
t	f	t	1	2025-06-17 15:20:09.700067+00	12	2025-06-17 15:20:09.700067+00	01977e79-eee1-75ec-9162-a1f466f494e2	oksana.hnatiuk985@gmail.com
t	f	t	1	2025-06-17 15:20:09.796662+00	13	2025-06-17 15:20:09.796662+00	01977e79-ef42-7285-bf99-a45e727741b8	marina.kryva154@gmail.com
t	f	t	1	2025-06-17 15:20:09.899239+00	14	2025-06-17 15:20:09.899239+00	01977e79-efa8-720f-a2d9-9538e1f05127	yuliia.tkachuk623@gmail.com
t	f	t	1	2025-06-17 15:20:09.994612+00	15	2025-06-17 15:20:09.994612+00	01977e79-f007-77be-ab68-b61fa9a91a6c	andrii.melnyk958@gmail.com
t	f	t	1	2025-06-17 15:20:10.102897+00	16	2025-06-17 15:20:10.102897+00	01977e79-f074-779b-9674-3b07f474ce2c	dmytro.bondar582@gmail.com
t	f	t	1	2025-06-17 15:20:10.205786+00	17	2025-06-17 15:20:10.205786+00	01977e79-f0db-7638-8703-6f6a9ce99f22	iryna.litvyn120@gmail.com
t	f	t	1	2025-06-17 15:20:10.312575+00	18	2025-06-17 15:20:10.312575+00	01977e79-f146-7970-be87-46500bbd6779	olenna.koval942@gmail.com
t	f	t	1	2025-06-17 15:20:10.407306+00	19	2025-06-17 15:20:10.407306+00	01977e79-f1a4-7811-840e-404568a4376e	vladyslav.hnatiuk397@gmail.com
t	f	t	1	2025-06-17 15:20:10.514464+00	20	2025-06-17 15:20:10.514464+00	01977e79-f210-7ff9-a57a-ff1746221754	kateryna.shevchenko875@gmail.com
t	f	t	1	2025-06-17 15:20:10.612598+00	21	2025-06-17 15:20:10.612598+00	01977e79-f272-7f27-bed4-a81b480f17fd	marina.tkachuk712@gmail.com
t	f	t	1	2025-06-17 15:20:10.714176+00	22	2025-06-17 15:20:10.714176+00	01977e79-f2d6-7075-a662-c4d24ed8bb3e	andrii.shevchenko378@gmail.com
t	f	t	1	2025-06-17 15:20:10.808658+00	23	2025-06-17 15:20:10.808658+00	01977e79-f336-722a-8a36-233ae9fe22f7	oksana.kryva502@gmail.com
t	f	t	1	2025-06-17 15:20:10.914382+00	24	2025-06-17 15:20:10.914382+00	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	olenna.petrynko438@gmail.com
t	f	t	1	2025-06-17 15:20:11.008901+00	25	2025-06-17 15:20:11.008901+00	01977e79-f3fe-7b11-a9c7-e104137309cc	dmytro.ilyov761@gmail.com
t	f	t	1	2025-06-17 15:20:11.116117+00	26	2025-06-17 15:20:11.116117+00	01977e79-f469-7573-ba3a-604cd272778f	vladyslav.koval188@gmail.com
t	f	t	1	2025-06-17 15:20:11.332052+00	28	2025-06-17 15:20:11.332052+00	01977e79-f542-7b27-95ab-c64f22f444ee	iryna.shevchenko333@gmail.com
t	f	t	1	2025-06-17 15:20:11.426329+00	29	2025-06-17 15:20:11.426329+00	01977e79-f59f-7958-879a-20e6a5dbfbae	kateryna.kryva609@gmail.com
t	f	t	1	2025-06-17 15:20:11.525683+00	30	2025-06-17 15:20:11.525683+00	01977e79-f603-7282-bc16-c98712d0e44d	marina.bondar205@gmail.com
t	f	t	1	2025-06-17 15:20:11.622579+00	31	2025-06-17 15:20:11.622579+00	01977e79-f664-7879-b2ec-fe94caaacc28	andrii.hnatiuk990@gmail.com
t	f	t	1	2025-06-17 15:20:11.720576+00	32	2025-06-17 15:20:11.720576+00	01977e79-f6c6-7707-aedb-fe3468f41140	oksana.tkachuk431@gmail.com
t	f	t	1	2025-06-17 15:20:11.817615+00	33	2025-06-17 15:20:11.817615+00	01977e79-f727-734f-abdb-bcd9c7e3ec00	dmytro.melnyk782@gmail.com
t	f	t	1	2025-06-17 15:20:11.91477+00	34	2025-06-17 15:20:11.91477+00	01977e79-f788-7b5f-84df-0e1fea0599f3	kateryna.bondar213@gmail.com
t	f	t	1	2025-06-17 15:20:12.01104+00	35	2025-06-17 15:20:12.01104+00	01977e79-f7e9-7b37-ac67-06a8797bd7d4	marina.litvyn365@gmail.com
t	f	t	1	2025-06-17 15:20:12.108099+00	36	2025-06-17 15:20:12.108099+00	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	yuliia.petrynko754@gmail.com
t	f	t	1	2025-06-17 15:20:12.214123+00	37	2025-06-17 15:20:12.214123+00	01977e79-f8b4-7c00-8caa-745ef40934a6	olenna.kryva288@gmail.com
t	f	t	1	2025-06-17 15:20:12.30266+00	38	2025-06-17 15:20:12.30266+00	01977e79-f90c-7eff-9b23-192773bf98bb	vladyslav.shevchenko126@gmail.com
t	f	t	1	2025-06-17 15:20:12.408263+00	39	2025-06-17 15:20:12.408263+00	01977e79-f975-78ec-81e9-6e13cd6d568a	iryna.koval687@gmail.com
t	f	t	1	2025-06-17 15:20:12.507326+00	40	2025-06-17 15:20:12.507326+00	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	andrii.tkachuk851@gmail.com
t	f	t	1	2025-06-17 15:20:12.619295+00	41	2025-06-17 15:20:12.619295+00	01977e79-fa48-76ee-b5ac-b933c85acc84	dmytro.kryva329@gmail.com
t	f	t	1	2025-06-17 15:20:12.716661+00	42	2025-06-17 15:20:12.716661+00	01977e79-faa9-7b86-a332-e10ef4ed001b	oksana.litvyn674@gmail.com
t	f	t	1	2025-06-17 15:20:12.823097+00	43	2025-06-17 15:20:12.823097+00	01977e79-fb15-743c-9d5a-b00ac2953735	kateryna.hnatiuk598@gmail.com
t	f	t	1	2025-06-17 15:20:12.918637+00	44	2025-06-17 15:20:12.918637+00	01977e79-fb74-7d65-8dd8-fb25557c7426	marina.melnyk120@gmail.com
t	f	t	1	2025-06-17 15:20:13.023604+00	45	2025-06-17 15:20:13.023604+00	01977e79-fbdd-7845-982f-a684fb6c9078	olenna.shevchenko743@gmail.com
t	f	t	1	2025-06-17 15:20:13.12024+00	46	2025-06-17 15:20:13.12024+00	01977e79-fc3d-7e26-94be-b6531796eb62	iryna.bondar401@gmail.com
t	f	t	1	2025-06-17 15:20:13.22438+00	47	2025-06-17 15:20:13.22438+00	01977e79-fca6-7b48-a3a4-2c362c16081c	yuliia.kryva523@gmail.com
t	f	t	1	2025-06-17 15:20:13.319434+00	48	2025-06-17 15:20:13.319434+00	01977e79-fd05-7a4c-9854-1f283f24c417	vladyslav.petrynko444@gmail.com
t	f	t	1	2025-06-17 15:20:13.4243+00	49	2025-06-17 15:20:13.4243+00	01977e79-fd6e-737b-bdb8-d563d1ac8899	andrii.koval991@gmail.com
t	f	t	1	2025-06-17 15:20:13.519412+00	50	2025-06-17 15:20:13.519412+00	01977e79-fdcd-7647-9bfc-c4f208c7d607	kateryna.tkachuk385@gmail.com
t	f	t	1	2025-06-17 15:20:13.625422+00	51	2025-06-17 15:20:13.625422+00	01977e79-fe37-75a1-8b40-2fc606a56218	marina.shevchenko567@gmail.com
t	f	t	1	2025-06-17 15:20:13.724173+00	52	2025-06-17 15:20:13.724173+00	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	iryna.tkachuk229@gmail.com
t	f	t	1	2025-06-17 15:20:13.830262+00	53	2025-06-17 15:20:13.830262+00	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	olenna.melnyk300@gmail.com
t	f	t	1	2025-06-17 15:20:13.925847+00	54	2025-06-17 15:20:13.925847+00	01977e79-ff63-73de-9441-b73d75415356	dmytro.koval967@gmail.com
t	f	t	1	2025-06-17 15:20:14.028648+00	55	2025-06-17 15:20:14.028648+00	01977e79-ffca-7f6d-8b09-70a69d42bf8c	oksana.petrynko212@gmail.com
t	f	t	1	2025-06-17 15:20:14.120377+00	56	2025-06-17 15:20:14.120377+00	01977e7a-0026-79ac-a236-50d54f042a62	yuliia.hnatiuk688@gmail.com
t	f	t	1	2025-06-17 15:20:14.221452+00	57	2025-06-17 15:20:14.221452+00	01977e7a-008b-7cb4-b539-be89d4f12cfc	andrii.bondar322@gmail.com
t	f	t	1	2025-06-17 15:20:14.316806+00	58	2025-06-17 15:20:14.316806+00	01977e7a-00eb-72f3-8dd8-014dcf711be4	vladyslav.melnyk708@gmail.com
t	f	t	1	2025-06-17 15:20:14.412926+00	59	2025-06-17 15:20:14.412926+00	01977e7a-014a-74b6-81f5-7e8e435a3d75	iryna.kryva544@gmail.com
t	f	t	1	2025-06-17 15:20:14.50916+00	60	2025-06-17 15:20:14.50916+00	01977e7a-01ab-77da-bcb4-15df70386907	kateryna.koval190@gmail.com
t	f	t	2	2025-06-17 15:20:17.484719+00	61	2025-06-17 15:20:17.484719+00	01977e79-e89a-74ae-8914-3607235689dd	+380503456789
t	f	t	2	2025-06-17 15:20:17.560413+00	62	2025-06-17 15:20:17.560413+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	+380673214567
t	f	t	2	2025-06-17 15:20:17.577458+00	63	2025-06-17 15:20:17.577458+00	01977e79-eada-7090-8882-1256bfe48b6c	+380931234567
t	f	t	2	2025-06-17 15:20:17.59228+00	64	2025-06-17 15:20:17.59228+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	+380631112223
t	f	t	2	2025-06-17 15:20:17.607722+00	65	2025-06-17 15:20:17.607722+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	+380731234555
t	f	t	2	2025-06-17 15:20:17.624013+00	66	2025-06-17 15:20:17.624013+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	+380991234111
t	f	t	2	2025-06-17 15:20:17.641201+00	67	2025-06-17 15:20:17.641201+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	+380681234888
t	f	t	2	2025-06-17 15:20:17.659177+00	68	2025-06-17 15:20:17.659177+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	+380661234444
t	f	t	2	2025-06-17 15:20:17.679074+00	69	2025-06-17 15:20:17.679074+00	01977e79-eda2-7ae5-8b09-e1073fda1348	+380973456123
t	f	t	2	2025-06-17 15:20:17.697096+00	70	2025-06-17 15:20:17.697096+00	01977e79-ee12-7598-b6d1-f711a70583a5	+380503333444
t	f	t	2	2025-06-17 15:20:17.715779+00	71	2025-06-17 15:20:17.715779+00	01977e79-ee76-703d-a9b1-6501c51b721a	+380673456789
t	f	t	2	2025-06-17 15:20:17.734406+00	72	2025-06-17 15:20:17.734406+00	01977e79-eee1-75ec-9162-a1f466f494e2	+380934567321
t	f	t	2	2025-06-17 15:20:17.751044+00	73	2025-06-17 15:20:17.751044+00	01977e79-ef42-7285-bf99-a45e727741b8	+380636789012
t	f	t	2	2025-06-17 15:20:17.766883+00	74	2025-06-17 15:20:17.766883+00	01977e79-efa8-720f-a2d9-9538e1f05127	+380735555666
t	f	t	2	2025-06-17 15:20:17.78259+00	75	2025-06-17 15:20:17.78259+00	01977e79-f007-77be-ab68-b61fa9a91a6c	+380996666777
t	f	t	2	2025-06-17 15:20:17.796855+00	76	2025-06-17 15:20:17.796855+00	01977e79-f074-779b-9674-3b07f474ce2c	+380683333999
t	f	t	2	2025-06-17 15:20:17.811561+00	77	2025-06-17 15:20:17.811561+00	01977e79-f0db-7638-8703-6f6a9ce99f22	+380664444555
t	f	t	2	2025-06-17 15:20:17.82946+00	78	2025-06-17 15:20:17.82946+00	01977e79-f146-7970-be87-46500bbd6779	+380975555111
t	f	t	2	2025-06-17 15:20:17.846981+00	79	2025-06-17 15:20:17.846981+00	01977e79-f1a4-7811-840e-404568a4376e	+380507891234
t	f	t	2	2025-06-17 15:20:17.865908+00	80	2025-06-17 15:20:17.865908+00	01977e79-f210-7ff9-a57a-ff1746221754	+380679999000
t	f	t	2	2025-06-17 15:20:17.884113+00	81	2025-06-17 15:20:17.884113+00	01977e79-f272-7f27-bed4-a81b480f17fd	+380931112233
t	f	t	2	2025-06-17 15:20:17.904642+00	82	2025-06-17 15:20:17.904642+00	01977e79-f2d6-7075-a662-c4d24ed8bb3e	+380671234321
t	f	t	2	2025-06-17 15:20:17.924179+00	83	2025-06-17 15:20:17.924179+00	01977e79-f336-722a-8a36-233ae9fe22f7	+380501234321
t	f	t	2	2025-06-17 15:20:17.942622+00	84	2025-06-17 15:20:17.942622+00	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	+380632345678
t	f	t	2	2025-06-17 15:20:17.961799+00	85	2025-06-17 15:20:17.961799+00	01977e79-f3fe-7b11-a9c7-e104137309cc	+380991112244
t	f	t	2	2025-06-17 15:20:17.97903+00	86	2025-06-17 15:20:17.97903+00	01977e79-f469-7573-ba3a-604cd272778f	+380663333222
t	f	t	2	2025-06-17 15:20:18.010277+00	88	2025-06-17 15:20:18.010277+00	01977e79-f542-7b27-95ab-c64f22f444ee	+380733334455
t	f	t	2	2025-06-17 15:20:18.02556+00	89	2025-06-17 15:20:18.02556+00	01977e79-f59f-7958-879a-20e6a5dbfbae	+380934444111
t	f	t	2	2025-06-17 15:20:18.042848+00	90	2025-06-17 15:20:18.042848+00	01977e79-f603-7282-bc16-c98712d0e44d	+380674444333
t	f	t	2	2025-06-17 15:20:18.065309+00	91	2025-06-17 15:20:18.065309+00	01977e79-f664-7879-b2ec-fe94caaacc28	+380509999888
t	f	t	2	2025-06-17 15:20:18.084431+00	92	2025-06-17 15:20:18.084431+00	01977e79-f6c6-7707-aedb-fe3468f41140	+380679876543
t	f	t	2	2025-06-17 15:20:18.10357+00	93	2025-06-17 15:20:18.10357+00	01977e79-f727-734f-abdb-bcd9c7e3ec00	+380935432189
t	f	t	2	2025-06-17 15:20:18.122958+00	94	2025-06-17 15:20:18.122958+00	01977e79-f788-7b5f-84df-0e1fea0599f3	+380631231231
t	f	t	2	2025-06-17 15:20:18.141959+00	95	2025-06-17 15:20:18.141959+00	01977e79-f7e9-7b37-ac67-06a8797bd7d4	+380731111999
t	f	t	2	2025-06-17 15:20:18.159953+00	96	2025-06-17 15:20:18.159953+00	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	+380993210123
t	f	t	2	2025-06-17 15:20:18.177422+00	97	2025-06-17 15:20:18.177422+00	01977e79-f8b4-7c00-8caa-745ef40934a6	+380685555444
t	f	t	2	2025-06-17 15:20:18.193287+00	98	2025-06-17 15:20:18.193287+00	01977e79-f90c-7eff-9b23-192773bf98bb	+380666666777
t	f	t	2	2025-06-17 15:20:18.209496+00	99	2025-06-17 15:20:18.209496+00	01977e79-f975-78ec-81e9-6e13cd6d568a	+380972222333
t	f	t	2	2025-06-17 15:20:18.225572+00	100	2025-06-17 15:20:18.225572+00	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	+380508888000
t	f	t	2	2025-06-17 15:20:18.244042+00	101	2025-06-17 15:20:18.244042+00	01977e79-fa48-76ee-b5ac-b933c85acc84	+380671112345
t	f	t	2	2025-06-17 15:20:18.264319+00	102	2025-06-17 15:20:18.264319+00	01977e79-faa9-7b86-a332-e10ef4ed001b	+380931111234
t	f	t	2	2025-06-17 15:20:18.284816+00	103	2025-06-17 15:20:18.284816+00	01977e79-fb15-743c-9d5a-b00ac2953735	+380661234999
t	f	t	2	2025-06-17 15:20:18.304146+00	104	2025-06-17 15:20:18.304146+00	01977e79-fb74-7d65-8dd8-fb25557c7426	+380991234678
t	f	t	2	2025-06-17 15:20:18.323219+00	105	2025-06-17 15:20:18.323219+00	01977e79-fbdd-7845-982f-a684fb6c9078	+380684444123
t	f	t	2	2025-06-17 15:20:18.341616+00	106	2025-06-17 15:20:18.341616+00	01977e79-fc3d-7e26-94be-b6531796eb62	+380636666111
t	f	t	2	2025-06-17 15:20:18.359744+00	107	2025-06-17 15:20:18.359744+00	01977e79-fca6-7b48-a3a4-2c362c16081c	+380735551234
t	f	t	2	2025-06-17 15:20:18.376142+00	108	2025-06-17 15:20:18.376142+00	01977e79-fd05-7a4c-9854-1f283f24c417	+380976543210
t	f	t	2	2025-06-17 15:20:18.393141+00	109	2025-06-17 15:20:18.393141+00	01977e79-fd6e-737b-bdb8-d563d1ac8899	+380502221111
t	f	t	2	2025-06-17 15:20:18.40822+00	110	2025-06-17 15:20:18.40822+00	01977e79-fdcd-7647-9bfc-c4f208c7d607	+380674445555
t	f	t	2	2025-06-17 15:20:18.4225+00	111	2025-06-17 15:20:18.4225+00	01977e79-fe37-75a1-8b40-2fc606a56218	+380935555000
t	f	t	2	2025-06-17 15:20:18.440967+00	112	2025-06-17 15:20:18.440967+00	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	+380632222444
t	f	t	2	2025-06-17 15:20:18.459128+00	113	2025-06-17 15:20:18.459128+00	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	+380993333222
t	f	t	2	2025-06-17 15:20:18.478541+00	114	2025-06-17 15:20:18.478541+00	01977e79-ff63-73de-9441-b73d75415356	+380689999123
t	f	t	2	2025-06-17 15:20:18.497756+00	115	2025-06-17 15:20:18.497756+00	01977e79-ffca-7f6d-8b09-70a69d42bf8c	+380665556666
t	f	t	2	2025-06-17 15:20:18.519727+00	116	2025-06-17 15:20:18.519727+00	01977e7a-0026-79ac-a236-50d54f042a62	+380973321123
t	f	t	2	2025-06-17 15:20:18.539858+00	117	2025-06-17 15:20:18.539858+00	01977e7a-008b-7cb4-b539-be89d4f12cfc	+380505555777
t	f	t	2	2025-06-17 15:20:18.558829+00	118	2025-06-17 15:20:18.558829+00	01977e7a-00eb-72f3-8dd8-014dcf711be4	+380679876000
t	f	t	2	2025-06-17 15:20:18.575097+00	119	2025-06-17 15:20:18.575097+00	01977e7a-014a-74b6-81f5-7e8e435a3d75	+380931234321
t	f	t	2	2025-06-17 15:20:18.594182+00	120	2025-06-17 15:20:18.594182+00	01977e7a-01ab-77da-bcb4-15df70386907	+380501111222
t	t	t	1	2025-06-17 15:23:38.007896+00	121	2025-06-17 15:23:38.007896+00	01977e7c-e1ea-7674-a453-c35ee7829b40	t30095758@gmail.com
t	f	t	2	2025-06-17 15:24:53.437539+00	122	2025-06-17 15:24:53.437539+00	01977e7c-e1ea-7674-a453-c35ee7829b40	+380674827391
t	t	t	1	2025-06-17 16:53:36.324398+00	123	2025-06-17 16:53:36.324398+00	01977ecf-4f42-72b5-8a44-701a5fdb4a9e	testvolunteer94@gmail.com
t	f	t	2	2025-06-17 16:55:02.074684+00	124	2025-06-17 16:55:02.074684+00	01977ecf-4f42-72b5-8a44-701a5fdb4a9e	+380671234567
f	t	t	1	2025-06-17 15:20:09.284885+00	8	2025-06-29 20:36:33.117789+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	dmitriy.iliyov32@gmail.com
t	t	t	1	2025-07-01 19:06:30.637563+00	162	2025-07-01 19:06:30.637563+00	0197c761-f334-7314-be66-7ed27933b7be	s2kr49@gmail.com
t	f	t	2	2025-07-01 19:10:18.103887+00	182	2025-07-01 19:10:18.103887+00	0197c761-f334-7314-be66-7ed27933b7be	+380909892348
\.


--
-- Data for Name: continue_flags; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.continue_flags (batch_size, id, page_number, should_continue, task_type_code) FROM stdin;
50	1	0	t	0
50	2	0	t	1
50	3	0	t	2
\.


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.customers (birthday_date, gender, profile_progress, profile_status_id, created_at, detail_id, updated_at, id, first_name, last_name, second_name) FROM stdin;
1991-04-16	0	8	2	2025-06-17 15:20:14.628393+00	1	2025-06-17 15:20:14.628393+00	01977e79-e89a-74ae-8914-3607235689dd	Ігор	Коваль	Михайлович
2001-07-01	1	8	2	2025-06-17 15:20:14.69215+00	2	2025-06-17 15:20:14.69215+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	Тетяна	Ткачук	Ігорівна
1999-06-18	1	8	2	2025-06-17 15:20:14.705384+00	3	2025-06-17 15:20:14.705384+00	01977e79-eada-7090-8882-1256bfe48b6c	Оксана	Романюк	Олександрівна
1981-08-02	0	8	2	2025-06-17 15:20:14.716434+00	4	2025-06-17 15:20:14.716434+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	Богдан	Яковенко	Арсенович
1988-03-18	0	8	2	2025-06-17 15:20:14.729257+00	5	2025-06-17 15:20:14.729257+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	Юрій	Петренко	Ігорович
1997-09-11	0	8	2	2025-06-17 15:20:14.749708+00	6	2025-06-17 15:20:14.749708+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	Тарас	Даниленко	Сергійович
1987-05-22	1	8	2	2025-06-17 15:20:14.761226+00	7	2025-06-17 15:20:14.761226+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	Катерина	Семенюк	Андрївна
1994-04-13	1	8	2	2025-06-17 15:20:14.772653+00	8	2025-06-17 15:20:14.772653+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	Алла	Мельник	Віталіївна
1997-07-20	1	8	2	2025-06-17 15:20:14.782831+00	9	2025-06-17 15:20:14.782831+00	01977e79-eda2-7ae5-8b09-e1073fda1348	Людмила	Крива	Андрївна
2002-10-02	1	8	2	2025-06-17 15:20:14.791974+00	10	2025-06-17 15:20:14.791974+00	01977e79-ee12-7598-b6d1-f711a70583a5	Ірина	Семенюк	Максимівна
1986-05-08	1	8	2	2025-06-17 15:20:14.812842+00	12	2025-06-17 15:20:14.812842+00	01977e79-eee1-75ec-9162-a1f466f494e2	Олена	Захарова	Василівна
2000-09-19	0	8	2	2025-06-17 15:20:14.823112+00	13	2025-06-17 15:20:14.823112+00	01977e79-ef42-7285-bf99-a45e727741b8	Юрій	Сидоренко	Михайлович
1998-07-15	1	8	2	2025-06-17 15:20:14.835292+00	14	2025-06-17 15:20:14.835292+00	01977e79-efa8-720f-a2d9-9538e1f05127	Олена	Романюк	Василівна
2000-07-23	1	8	2	2025-06-17 15:20:14.847077+00	15	2025-06-17 15:20:14.847077+00	01977e79-f007-77be-ab68-b61fa9a91a6c	Ганна	Романюк	Ігорівна
1985-10-02	0	8	2	2025-06-17 15:20:14.86016+00	16	2025-06-17 15:20:14.86016+00	01977e79-f074-779b-9674-3b07f474ce2c	Максим	Даниленко	Михайлович
1980-09-25	0	8	2	2025-06-17 15:20:14.872862+00	17	2025-06-17 15:20:14.872862+00	01977e79-f0db-7638-8703-6f6a9ce99f22	Богдан	Лисенко	Юрійович
1987-03-11	0	8	2	2025-06-17 15:20:14.885041+00	18	2025-06-17 15:20:14.885041+00	01977e79-f146-7970-be87-46500bbd6779	Максим	Козачук	Васильович
1994-01-11	1	8	2	2025-06-17 15:20:14.898407+00	19	2025-06-17 15:20:14.898407+00	01977e79-f1a4-7811-840e-404568a4376e	Юлія	Бондар	Іванівна
1996-06-13	0	8	2	2025-06-17 15:20:14.911306+00	20	2025-06-17 15:20:14.911306+00	01977e79-f210-7ff9-a57a-ff1746221754	Юрій	Гнатюк	Олегович
2004-02-16	0	8	2	2025-06-17 15:24:30.324251+00	61	2025-06-17 15:24:30.324251+00	01977e7c-e1ea-7674-a453-c35ee7829b40	Дмитро	Ільйов	Андрійович
1994-08-25	0	8	2	2025-06-17 15:20:14.802892+00	11	2025-06-25 23:25:09.315162+00	01977e79-ee76-703d-a9b1-6501c51b721a	Віталій	Яковенкокс	Іванович
\.


--
-- Data for Name: deactivated_auth_token; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.deactivated_auth_token (deactivate_at, id) FROM stdin;
2025-06-17 16:34:03.949714+00	01977e7d-e903-7703-908d-75b0415e7bd1
2025-06-17 17:04:08.045659+00	01977ed0-66a8-7f69-ace1-e787c2a9b8e4
2025-06-17 17:04:42.430348+00	01977ed9-7376-7eb5-90ce-6f05b4911b20
2025-06-17 17:06:30.957921+00	01977edb-1297-7499-bc42-e4c974863158
2025-06-17 17:24:34.965093+00	01977ee4-105c-74b6-b8d8-830ad5f4bfd0
2025-06-17 23:03:09.778903+00	01978002-a00f-7589-a790-56cd2ae4abfd
2025-06-17 23:03:38.224331+00	01978022-22c3-74f7-ab77-28bc08bba140
2025-06-21 01:14:03.736026+00	01979008-44d4-7990-aa3a-df5ac7d5e5a9
2025-06-21 01:14:50.681392+00	0197900c-db63-7ad1-9175-a21fa1435cd4
2025-06-21 01:15:47.055096+00	0197900d-9718-7255-b74c-1f9d33c2daab
2025-06-21 01:20:25.147502+00	0197900e-6e34-742c-a36c-628715fbd69a
2025-06-21 01:21:07.043868+00	01979012-acca-7451-b1b9-b9b4e2e12985
2025-06-21 01:21:37.906248+00	01979013-517c-7f14-b01e-9063b5fc33d9
2025-06-21 01:22:40.913007+00	01979013-c523-7562-a8bc-baf2be3aab17
2025-06-21 01:23:52.543033+00	01979014-bb43-7bd0-add6-77d3dcfc6505
2025-06-21 01:24:31.402234+00	01979015-d437-77a3-9e20-4b4a8048600f
2025-06-21 01:25:22.978127+00	01979016-98bc-75bb-a3b1-688392ccae95
2025-06-21 01:26:02.890927+00	01979017-4af0-7423-ad14-2bcb0fdeed88
2025-06-21 01:27:03.031111+00	01979017-d01e-729a-968d-988fb9d5b60c
2025-06-21 01:38:16.545292+00	01979018-bb62-766a-9085-ebac6b39364d
2025-06-21 03:41:53.323934+00	0197907e-4390-70ad-945f-6b75f8e5bbe7
2025-06-21 03:45:22.917106+00	01979023-0503-72b2-bf81-b288d6308fa2
2025-06-21 03:46:57.730903+00	01979094-4677-7266-b905-d49a9d2d8bc6
2025-06-21 20:56:31.44233+00	01979097-aaa8-75ca-855d-7b4de13e89b4
2025-06-21 21:48:44.722187+00	0197944a-9312-79f5-9bb6-77ebb9960442
2025-06-21 22:41:00.811064+00	01979490-af6a-7f59-b326-77f076857019
2025-06-21 22:42:01.159743+00	019794a7-8dcb-72bb-ad43-894d3fd68a29
2025-06-21 23:09:16.815952+00	019794a7-ff52-7e4d-a0df-0727e10e02f7
2025-06-21 23:10:31.262321+00	019794c1-55eb-799a-887f-cb0032ea6778
2025-06-21 23:11:03.205815+00	019794c2-1ccb-70c5-9fa7-74a19a90f437
2025-06-21 23:12:04.9475+00	019794c2-97e3-7b28-9404-94c1aecd987a
2025-06-22 16:27:09.83132+00	019794c3-afd0-737f-b072-1342f94cc905
2025-06-25 22:32:34.832202+00	0197a928-3355-7a1f-b775-af1dac506eef
2025-06-25 23:23:54.722827+00	0197a967-a7b0-7689-860e-fcfab5a37fff
2025-06-27 18:40:39.500765+00	0197b2a4-01c3-79c4-922e-5ca90f5eea22
2025-06-27 18:49:58.391722+00	0197b2b1-bfaf-75db-81b4-c75ac022a60d
2025-06-28 21:58:39.767411+00	0197b814-7f6b-732a-ba38-6655c3ef7a39
2025-06-28 22:01:00.476314+00	0197b88d-22d8-7003-9be2-73e8eb83325f
2025-06-29 01:35:26.166819+00	0197b948-1a2c-7810-9c93-8eaec472e49d
2025-06-29 20:40:57.496416+00	0197bd69-bd4c-79b6-b764-359e59f24b39
2025-06-30 21:14:05.379067+00	0197c2aa-90c7-7e1c-9046-7bffc42c63f3
2025-06-30 21:18:14.066495+00	0197c2b0-ff70-76f4-ac23-1f92244ff38e
2025-06-30 21:19:00.956653+00	0197c2b4-f29f-715c-a4b4-a111d6194347
2025-07-01 19:05:52.387693+00	0197c2b5-51d3-7bb6-aa08-db1efe116a12
2025-07-01 19:11:53.467611+00	0197c763-8d8c-7529-b6c0-409ee63eb048
2025-07-02 10:33:10.486936+00	0197cab1-118a-7b29-a727-730b969293de
2025-07-02 10:33:27.708026+00	0197cab2-af5e-700b-ba50-76fd9225cb43
2025-07-02 10:34:02.66851+00	0197cab2-ef49-73f6-bd0d-052974cca0c3
2025-07-02 10:34:19.084962+00	0197cab3-78b9-70fe-95ce-6391653147c5
2025-07-02 23:03:24.124849+00	0197cab3-bec0-703c-8611-0cf9df4516b8
2025-07-03 16:31:58.733816+00	0197cd62-1af9-7b94-9f82-1ba97f6a9d5b
2025-07-03 16:32:21.013782+00	0197d121-968e-731e-878b-4b3ab928c57d
2025-07-04 22:46:14.560512+00	0197d6be-c6c2-70fb-873a-ac28a16fc7a0
2025-07-04 22:52:53.861109+00	0197d7a3-a9b8-7705-80a9-fb996b2a7bc4
2025-07-07 01:01:54.640528+00	0197e267-3108-780e-85e2-9a88c386a69e
2025-07-07 02:16:33.624562+00	0197e267-75aa-7f6a-8c90-a2126aa2fc4a
2025-07-07 09:25:40.095349+00	0197e433-ba0a-70dc-a3ad-4d172dba29a9
2025-07-07 09:40:54.050756+00	0197e441-379a-7048-9cb4-9e7bbedf97f1
2025-07-07 15:14:19.796457+00	0197e442-9ef9-7f57-92c3-6686e81d20e1
2025-07-08 00:18:09.380012+00	0197e6c0-ae4a-7b7d-93db-60ee5af90cb3
\.


--
-- Data for Name: doctor_specialization_relations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.doctor_specialization_relations (specialization_id, doctor_id) FROM stdin;
9	01977e79-f272-7f27-bed4-a81b480f17fd
2	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2	01977e79-f336-722a-8a36-233ae9fe22f7
19	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
1	01977e79-f3fe-7b11-a9c7-e104137309cc
2	01977e79-f469-7573-ba3a-604cd272778f
9	01977e79-f469-7573-ba3a-604cd272778f
22	01977e79-f542-7b27-95ab-c64f22f444ee
11	01977e79-f59f-7958-879a-20e6a5dbfbae
10	01977e79-f59f-7958-879a-20e6a5dbfbae
12	01977e79-f603-7282-bc16-c98712d0e44d
5	01977e79-f603-7282-bc16-c98712d0e44d
4	01977e79-f664-7879-b2ec-fe94caaacc28
27	01977e79-f664-7879-b2ec-fe94caaacc28
9	01977e79-f6c6-7707-aedb-fe3468f41140
12	01977e79-f727-734f-abdb-bcd9c7e3ec00
25	01977e79-f788-7b5f-84df-0e1fea0599f3
18	01977e79-f788-7b5f-84df-0e1fea0599f3
2	01977e79-f7e9-7b37-ac67-06a8797bd7d4
9	01977e79-f7e9-7b37-ac67-06a8797bd7d4
26	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
28	01977e79-f8b4-7c00-8caa-745ef40934a6
8	01977e79-f8b4-7c00-8caa-745ef40934a6
24	01977e79-f90c-7eff-9b23-192773bf98bb
25	01977e79-f975-78ec-81e9-6e13cd6d568a
20	01977e79-f975-78ec-81e9-6e13cd6d568a
1	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
6	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
\.


--
-- Data for Name: doctor_specializations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.doctor_specializations (id, specialization) FROM stdin;
1	ALLERGOLOGIST
2	CARDIOLOGIST
3	DENTIST
4	ENDOCRINOLOGIST
5	GASTROENTEROLOGIST
6	GENERAL_PRACTITIONER
7	GYNECOLOGIST
8	IMMUNOLOGIST
9	INFECTIOUS_DISEASE_SPECIALIST
10	NEPHROLOGIST
11	NEUROLOGIST
12	NEUROSURGEON
13	NUTRITIONIST
14	OCCUPATIONAL_THERAPIST
15	ONCOLOGIST
16	OPHTHALMOLOGIST
17	ORTHOPEDIST
18	OTOLARYNGOLOGIST
19	PEDIATRICIAN
20	PSYCHIATRIST
21	PSYCHOLOGIST
22	PSYCHOTHERAPIST
23	PULMONOLOGIST
24	REHABILITATION_SPECIALIST
25	RHEUMATOLOGIST
26	SPEECH_THERAPIST
27	SPORTS_MEDICINE_SPECIALIST
28	SURGEON
\.


--
-- Data for Name: doctors; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.doctors (gender, is_approved, profile_progress, profile_status_id, working_experience, created_at, detail_id, updated_at, id, first_name, last_name, second_name, specialization_detail) FROM stdin;
0	t	16	2	17	2025-06-17 15:20:15.901744+00	35	2025-06-17 15:20:15.901958+00	01977e79-f7e9-7b37-ac67-06a8797bd7d4	Богдан	Семенюк	Ігорович	Діагностика та лікування хвороб серця і судин, контроль артеріального тиску.
1	t	16	2	9	2025-06-17 15:20:15.479384+00	28	2025-06-17 15:20:15.479535+00	01977e79-f542-7b27-95ab-c64f22f444ee	Ганна	Поліщук	Ігорівна	Проведення психотерапевтичних сесій при психологічних труднощах.
1	t	16	2	16	2025-06-17 15:20:14.927309+00	21	2025-06-17 15:20:14.927775+00	01977e79-f272-7f27-bed4-a81b480f17fd	Ірина	Шевченко	Арсенівна	Діагностика та лікування бактеріальних, вірусних і паразитарних інфекцій.
1	t	16	2	5	2025-06-17 15:20:15.548799+00	29	2025-06-17 15:20:15.548987+00	01977e79-f59f-7958-879a-20e6a5dbfbae	Тетяна	Сидоренко	Богданівна	Лікування неврологічних розладів: головний біль, порушення сну, епілепсія.
0	t	16	2	14	2025-06-17 15:20:15.968661+00	36	2025-06-17 15:20:15.968893+00	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	Максим	Гнатюк	Павлович	Корекція мовленнєвих порушень у дітей і дорослих.
1	t	16	2	12	2025-06-17 15:20:15.605922+00	30	2025-06-17 15:20:15.60607+00	01977e79-f603-7282-bc16-c98712d0e44d	Алла	Ткачук	Максимівна	Хірургічне лікування захворювань мозку, хребта та нервової системи.
0	t	16	2	11	2025-06-17 15:20:16.023612+00	37	2025-06-17 15:20:16.023738+00	01977e79-f8b4-7c00-8caa-745ef40934a6	Сергій	Яковенко	Арсенович	Проведення хірургічних втручань з метою відновлення здоров’я.
0	t	16	2	20	2025-06-17 15:20:16.091955+00	38	2025-06-17 15:20:16.092103+00	01977e79-f90c-7eff-9b23-192773bf98bb	Андрій	Коваль	Тарасович	Фізична та психологічна реабілітація після хвороб або травм.
1	t	16	2	6	2025-06-17 15:20:15.669689+00	31	2025-06-17 15:20:15.669856+00	01977e79-f664-7879-b2ec-fe94caaacc28	Катерина	Коваль	Андрївна	Ведення пацієнтів з порушеннями гормонального фону та обміну речовин.
0	t	16	2	1	2025-06-17 15:20:16.152144+00	39	2025-06-17 15:20:16.152269+00	01977e79-f975-78ec-81e9-6e13cd6d568a	Богдан	Семенюк	Богданович	Діагностика та лікування ревматоїдного артриту й інших хвороб суглобів.
1	t	16	2	2	2025-06-17 15:20:15.729113+00	32	2025-06-17 15:20:15.729283+00	01977e79-f6c6-7707-aedb-fe3468f41140	Людмила	Поліщук	Тарасівна	Діагностика та лікування бактеріальних, вірусних і паразитарних інфекцій.
0	t	16	2	15	2025-06-17 15:20:16.203406+00	40	2025-06-17 15:20:16.203523+00	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	Юрій	Савченко	Михайлович	Допомога при алергічних реакціях, ведення пацієнтів з імунопатологіями.
1	t	16	2	5	2025-06-17 15:20:15.072145+00	22	2025-06-17 15:20:15.072729+00	01977e79-f2d6-7075-a662-c4d24ed8bb3e	Світлана	Захарова	Віталіївна	Діагностика та лікування хвороб серця і судин, контроль артеріального тиску.
1	t	16	2	6	2025-06-17 15:20:15.152135+00	23	2025-06-17 15:20:15.152276+00	01977e79-f336-722a-8a36-233ae9fe22f7	Юлія	Крива	Ігорівна	Діагностика та лікування хвороб серця і судин, контроль артеріального тиску.
1	t	16	2	10	2025-06-17 15:20:15.208443+00	24	2025-06-17 15:20:15.208832+00	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	Наталія	Коваленко	Михайлівна	Медичне обслуговування дітей з перших днів життя до підліткового віку.
0	t	16	2	5	2025-06-17 15:20:15.786309+00	33	2025-06-17 15:20:15.786446+00	01977e79-f727-734f-abdb-bcd9c7e3ec00	Богдан	Козачук	Богданович	Хірургічне лікування захворювань мозку, хребта та нервової системи.
0	t	16	2	9	2025-06-17 15:20:15.280943+00	25	2025-06-17 15:20:15.281104+00	01977e79-f3fe-7b11-a9c7-e104137309cc	Тарас	Бондар	Арсенович	Допомога при алергічних реакціях, ведення пацієнтів з імунопатологіями.
1	t	16	2	4	2025-06-17 15:20:15.350518+00	26	2025-06-17 15:20:15.350797+00	01977e79-f469-7573-ba3a-604cd272778f	Ірина	Даниленко	Тарасівна	Діагностика та лікування хвороб серця і судин, контроль артеріального тиску.
0	t	16	2	10	2025-06-17 15:20:15.833621+00	34	2025-06-17 15:20:15.83385+00	01977e79-f788-7b5f-84df-0e1fea0599f3	Юрій	Литвин	Максимович	Діагностика та лікування ревматоїдного артриту й інших хвороб суглобів.
\.


--
-- Data for Name: jurist_specialization_relations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.jurist_specialization_relations (specialization_id, jurist_id) FROM stdin;
7	01977e79-fa48-76ee-b5ac-b933c85acc84
7	01977e79-faa9-7b86-a332-e10ef4ed001b
5	01977e79-faa9-7b86-a332-e10ef4ed001b
4	01977e79-fb15-743c-9d5a-b00ac2953735
3	01977e79-fb74-7d65-8dd8-fb25557c7426
3	01977e79-fbdd-7845-982f-a684fb6c9078
4	01977e79-fbdd-7845-982f-a684fb6c9078
3	01977e79-fca6-7b48-a3a4-2c362c16081c
5	01977e79-fca6-7b48-a3a4-2c362c16081c
6	01977e79-fd05-7a4c-9854-1f283f24c417
7	01977e79-fd6e-737b-bdb8-d563d1ac8899
5	01977e79-fd6e-737b-bdb8-d563d1ac8899
3	01977e79-fdcd-7647-9bfc-c4f208c7d607
2	01977e79-fe37-75a1-8b40-2fc606a56218
1	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
4	01977e79-ff63-73de-9441-b73d75415356
7	01977e79-ff63-73de-9441-b73d75415356
4	01977e7a-0026-79ac-a236-50d54f042a62
7	01977e7a-0026-79ac-a236-50d54f042a62
4	01977e7a-008b-7cb4-b539-be89d4f12cfc
6	01977e7a-008b-7cb4-b539-be89d4f12cfc
3	01977e7a-00eb-72f3-8dd8-014dcf711be4
2	01977e7a-014a-74b6-81f5-7e8e435a3d75
5	01977e7a-01ab-77da-bcb4-15df70386907
5	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
4	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
4	01977e79-ffca-7f6d-8b09-70a69d42bf8c
7	0197c761-f334-7314-be66-7ed27933b7be
3	01977e79-fc3d-7e26-94be-b6531796eb62
\.


--
-- Data for Name: jurist_specializations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.jurist_specializations (id, specialization) FROM stdin;
1	REFUGEE_RIGHTS
2	ADMINISTRATIVE_LAW
3	SOCIAL_LAW
4	HOUSING_LAW
5	PROPERTY_LAW
6	FAMILY_LAW
7	MIGRATION_LAW
\.


--
-- Data for Name: jurist_types; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.jurist_types (id, type) FROM stdin;
1	NOTARY
2	LAWYER
3	LEGAL_AID
\.


--
-- Data for Name: jurists; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.jurists (gender, is_approved, profile_progress, profile_status_id, type_id, working_experience, created_at, detail_id, updated_at, id, first_name, last_name, second_name, specialization_detail) FROM stdin;
1	t	16	2	2	14	2025-06-17 15:20:16.40273+00	43	2025-06-17 15:20:16.402974+00	01977e79-fb15-743c-9d5a-b00ac2953735	Юлія	Савченко	Михайлівна	Правова допомога у вирішенні житлових спорів, оформленні прав власності, оренди або виселення.
0	t	16	2	3	14	2025-06-17 15:20:17.41393+00	60	2025-06-17 15:20:17.414027+00	01977e7a-01ab-77da-bcb4-15df70386907	Богдан	Бондар	Юрійович	Врегулювання майнових спорів, захист прав на рухоме та нерухоме майно.
0	t	16	2	3	15	2025-06-17 15:20:16.991562+00	53	2025-06-17 15:20:16.99166+00	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	Андрій	Семенюк	Олександрович	Захист прав внутрішньо переміщених осіб, надання правових консультацій та допомога у відновленні документів.
1	t	16	2	2	19	2025-06-17 15:20:16.463561+00	44	2025-06-17 15:20:16.463724+00	01977e79-fb74-7d65-8dd8-fb25557c7426	Марина	Гнатюк	Богданівна	Консультації щодо соціальних виплат, пільг, пенсійного забезпечення та інших видів соціального захисту.
0	t	16	2	3	16	2025-06-17 15:20:16.756173+00	49	2025-06-17 15:20:16.756298+00	01977e79-fd6e-737b-bdb8-d563d1ac8899	Владислав	Гнатюк	Тарасович	Допомога в оформленні посвідок, громадянства, статусу біженця або імміграційних питань.
0	t	16	2	1	4	2025-06-17 15:20:16.640413+00	47	2025-06-17 15:20:16.640524+00	01977e79-fca6-7b48-a3a4-2c362c16081c	Віталій	Яковенко	Юрійович	Консультації щодо соціальних виплат, пільг, пенсійного забезпечення та інших видів соціального захисту.
1	t	16	2	3	8	2025-06-17 15:20:17.30034+00	58	2025-06-17 15:20:17.300644+00	01977e7a-00eb-72f3-8dd8-014dcf711be4	Юлія	Савченко	Михайлівна	Консультації щодо соціальних виплат, пільг, пенсійного забезпечення та інших видів соціального захисту.
0	t	16	2	1	19	2025-06-17 15:20:16.809062+00	50	2025-06-17 15:20:16.809151+00	01977e79-fdcd-7647-9bfc-c4f208c7d607	Владислав	Коваль	Богданович	Консультації щодо соціальних виплат, пільг, пенсійного забезпечення та інших видів соціального захисту.
1	t	16	2	3	2	2025-06-17 15:20:16.699034+00	48	2025-06-17 15:20:16.699159+00	01977e79-fd05-7a4c-9854-1f283f24c417	Катерина	Гнатюк	Тарасівна	Правовий супровід у справах про розлучення, аліменти, опіку, спадщину та встановлення батьківства.
1	t	16	2	3	9	2025-06-17 15:20:17.047621+00	54	2025-06-17 15:20:17.047825+00	01977e79-ff63-73de-9441-b73d75415356	Катерина	Лисенко	Олегівна	Правова допомога у вирішенні житлових спорів, оформленні прав власності, оренди або виселення.
1	t	16	2	1	7	2025-06-17 15:20:16.871921+00	51	2025-06-17 15:20:16.872045+00	01977e79-fe37-75a1-8b40-2fc606a56218	Наталія	Бондар	Максимівна	Супровід справ щодо дій або бездіяльності органів влади, допомога у відстоюванні прав в адміністративних судах.
1	t	16	2	2	1	2025-06-17 15:20:17.183001+00	56	2025-06-17 15:20:17.183246+00	01977e7a-0026-79ac-a236-50d54f042a62	Людмила	Шевченко	Максимівна	Правова допомога у вирішенні житлових спорів, оформленні прав власності, оренди або виселення.
0	t	16	2	2	10	2025-06-17 15:20:17.362501+00	59	2025-06-17 15:20:17.362597+00	01977e7a-014a-74b6-81f5-7e8e435a3d75	Сергій	Поліщук	Ігорович	Супровід справ щодо дій або бездіяльності органів влади, допомога у відстоюванні прав в адміністративних судах.
1	t	16	2	1	16	2025-06-17 15:20:17.236889+00	57	2025-06-17 15:20:17.237004+00	01977e7a-008b-7cb4-b539-be89d4f12cfc	Ірина	Сидоренко	Сергіївна	Правова допомога у вирішенні житлових спорів, оформленні прав власності, оренди або виселення.
1	t	16	2	2	14	2025-06-17 15:20:16.266654+00	41	2025-06-17 15:20:16.266962+00	01977e79-fa48-76ee-b5ac-b933c85acc84	Людмила	Коваль	Віталіївна	Допомога в оформленні посвідок, громадянства, статусу біженця або імміграційних питань.
0	t	16	2	3	14	2025-06-17 15:20:16.523196+00	45	2025-06-17 15:20:16.523566+00	01977e79-fbdd-7845-982f-a684fb6c9078	Максим	Литвин	Михайлович	Консультації щодо соціальних виплат, пільг, пенсійного забезпечення та інших видів соціального захисту.
0	t	16	2	3	4	2025-06-17 15:20:16.342331+00	42	2025-06-17 15:20:16.342467+00	01977e79-faa9-7b86-a332-e10ef4ed001b	Егор	Поліщук	Максимович	Допомога в оформленні посвідок, громадянства, статусу біженця або імміграційних питань.
0	t	16	2	2	3	2025-06-17 16:54:36.452699+00	62	2025-06-17 16:54:36.4578+00	01977ecf-4f42-72b5-8a44-701a5fdb4a9e	Дмитро	Ільйов	Андрійович	спеціалізуюся на майнових спорах
0	t	16	2	2	14	2025-06-17 15:20:16.935801+00	52	2025-06-22 23:52:44.416373+00	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	Марина	Шевченко	Юріївна	Правова допомога у вирішенні житлових спорів, оформленні прав власності, оренди або виселення.
0	t	16	2	3	20	2025-06-17 15:20:17.119416+00	55	2025-06-29 01:36:08.010718+00	01977e79-ffca-7f6d-8b09-70a69d42bf8c	Олександр	Романючка	Арсенович	Врегулювання майнових спорів, захист прав на рухоме та нерухоме майно.
1	t	16	2	3	1	2025-07-01 19:07:59.749282+00	152	2025-07-01 19:07:59.757687+00	0197c761-f334-7314-be66-7ed27933b7be	Шватемал	Оксна	Андріївна	міграція з Гватемали
1	t	16	2	2	11	2025-06-17 15:20:16.583121+00	46	2025-07-07 02:17:46.429076+00	01977e79-fc3d-7e26-94be-b6531796eb62	Юлія	Романюк	Павлівна	Консультації щодо соціальних виплат, пільг та інших видів соціального захисту.
\.


--
-- Data for Name: profile_status; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.profile_status (id, status) FROM stdin;
1	INCOMPLETE
2	COMPLETE
\.


--
-- Data for Name: schedule_progreses; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.schedule_progreses (filled_appointment_duration, filled_first_work_day, user_id) FROM stdin;
t	t	01977e79-f272-7f27-bed4-a81b480f17fd
t	t	01977e79-f2d6-7075-a662-c4d24ed8bb3e
t	t	01977e79-f336-722a-8a36-233ae9fe22f7
t	t	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
t	t	01977e79-f3fe-7b11-a9c7-e104137309cc
t	t	01977e79-f469-7573-ba3a-604cd272778f
t	t	01977e79-f4d4-765d-8949-0cea4be46b5c
t	t	01977e79-f542-7b27-95ab-c64f22f444ee
t	t	01977e79-f59f-7958-879a-20e6a5dbfbae
t	t	01977e79-f603-7282-bc16-c98712d0e44d
t	t	01977e79-f664-7879-b2ec-fe94caaacc28
t	t	01977e79-f6c6-7707-aedb-fe3468f41140
t	t	01977e79-f727-734f-abdb-bcd9c7e3ec00
t	t	01977e79-f788-7b5f-84df-0e1fea0599f3
t	t	01977e79-f7e9-7b37-ac67-06a8797bd7d4
t	t	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
t	t	01977e79-f8b4-7c00-8caa-745ef40934a6
t	t	01977e79-f90c-7eff-9b23-192773bf98bb
t	t	01977e79-f975-78ec-81e9-6e13cd6d568a
t	t	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
t	t	01977e79-fa48-76ee-b5ac-b933c85acc84
t	t	01977e79-faa9-7b86-a332-e10ef4ed001b
t	t	01977e79-fb15-743c-9d5a-b00ac2953735
t	t	01977e79-fb74-7d65-8dd8-fb25557c7426
t	t	01977e79-fbdd-7845-982f-a684fb6c9078
t	t	01977e79-fc3d-7e26-94be-b6531796eb62
t	t	01977e79-fca6-7b48-a3a4-2c362c16081c
t	t	01977e79-fd05-7a4c-9854-1f283f24c417
t	t	01977e79-fd6e-737b-bdb8-d563d1ac8899
t	t	01977e79-fdcd-7647-9bfc-c4f208c7d607
t	t	01977e79-fe37-75a1-8b40-2fc606a56218
t	t	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
t	t	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
t	t	01977e79-ff63-73de-9441-b73d75415356
t	t	01977e79-ffca-7f6d-8b09-70a69d42bf8c
t	t	01977e7a-0026-79ac-a236-50d54f042a62
t	t	01977e7a-008b-7cb4-b539-be89d4f12cfc
t	t	01977e7a-00eb-72f3-8dd8-014dcf711be4
t	t	01977e7a-014a-74b6-81f5-7e8e435a3d75
t	t	01977e7a-01ab-77da-bcb4-15df70386907
t	t	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
t	t	0197c761-f334-7314-be66-7ed27933b7be
\.


--
-- Data for Name: services; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.services (id, authority_id, created_at, service_name, password, updated_at, is_locked) FROM stdin;
0197b7ef-b53d-7543-a742-19266c865d38	8	2025-06-28 19:07:09.602325+00	schedule-task-service	{bcrypt}$2a$10$vdimWKCh5l7Qcw/IDQ/YCu6MAHcuEHy374SeDU.NV3FpYUGYHNgOK	2025-06-28 19:07:09.602325+00	f
aa772379-dbeb-45ce-ab67-b34a249e09c8	7	2025-06-28 19:07:09.602325+00	admin	{bcrypt}$2a$10$EVIgZBQgxl5Ig4itVFW7S.GUAPRGmesOVIBcfd9.lD3H7xjOFj04G	2025-06-28 19:07:09.602325+00	f
0197d1af-bbf7-7f40-ae45-af4e5d5b7148	9	2025-07-03 19:07:24.742494+00	prometheus	{bcrypt}$2a$10$o6rfcZpd.WUXTBbRxGr7mOLzqHaf4MdmlBBFjDSQcdErCaCnOQGT2	2025-07-03 19:07:24.742494+00	f
\.


--
-- Data for Name: task_journal; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.task_journal (batch_size, page_number, task_status_code, task_type_code, end_t, execution_time, id, start_t) FROM stdin;
\.


--
-- Data for Name: task_types; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.task_types (id, type) FROM stdin;
1	DELETE_INTERVAL
2	MARK_APPOINTMENT_SKIPPED
3	NOTIFY_BEFORE_APPOINTMENT
\.


--
-- Data for Name: user_authority; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_authority (authrity_id, user_id) FROM stdin;
4	01977e79-e89a-74ae-8914-3607235689dd
4	01977e79-ea6e-77ae-b3d1-3eec79e27af2
4	01977e79-eada-7090-8882-1256bfe48b6c
4	01977e79-eb5c-7d9d-9999-16de2fa2b5ca
4	01977e79-ebea-7cdd-a03a-ad8365e0b5b6
4	01977e79-ec66-7a5c-a3fa-859bde202bcd
4	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda
4	01977e79-eda2-7ae5-8b09-e1073fda1348
4	01977e79-ee12-7598-b6d1-f711a70583a5
4	01977e79-ee76-703d-a9b1-6501c51b721a
4	01977e79-eee1-75ec-9162-a1f466f494e2
4	01977e79-ef42-7285-bf99-a45e727741b8
4	01977e79-efa8-720f-a2d9-9538e1f05127
4	01977e79-f007-77be-ab68-b61fa9a91a6c
4	01977e79-f074-779b-9674-3b07f474ce2c
4	01977e79-f0db-7638-8703-6f6a9ce99f22
4	01977e79-f146-7970-be87-46500bbd6779
4	01977e79-f1a4-7811-840e-404568a4376e
4	01977e79-f210-7ff9-a57a-ff1746221754
5	01977e79-f272-7f27-bed4-a81b480f17fd
5	01977e79-f2d6-7075-a662-c4d24ed8bb3e
5	01977e79-f336-722a-8a36-233ae9fe22f7
5	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
5	01977e79-f3fe-7b11-a9c7-e104137309cc
5	01977e79-f469-7573-ba3a-604cd272778f
5	01977e79-f542-7b27-95ab-c64f22f444ee
5	01977e79-f59f-7958-879a-20e6a5dbfbae
5	01977e79-f603-7282-bc16-c98712d0e44d
5	01977e79-f664-7879-b2ec-fe94caaacc28
5	01977e79-f6c6-7707-aedb-fe3468f41140
5	01977e79-f727-734f-abdb-bcd9c7e3ec00
5	01977e79-f788-7b5f-84df-0e1fea0599f3
5	01977e79-f7e9-7b37-ac67-06a8797bd7d4
5	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
5	01977e79-f8b4-7c00-8caa-745ef40934a6
5	01977e79-f90c-7eff-9b23-192773bf98bb
5	01977e79-f975-78ec-81e9-6e13cd6d568a
5	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
6	01977e79-fa48-76ee-b5ac-b933c85acc84
6	01977e79-faa9-7b86-a332-e10ef4ed001b
6	01977e79-fb15-743c-9d5a-b00ac2953735
6	01977e79-fb74-7d65-8dd8-fb25557c7426
6	01977e79-fbdd-7845-982f-a684fb6c9078
6	01977e79-fc3d-7e26-94be-b6531796eb62
6	01977e79-fca6-7b48-a3a4-2c362c16081c
6	01977e79-fd05-7a4c-9854-1f283f24c417
6	01977e79-fd6e-737b-bdb8-d563d1ac8899
6	01977e79-fdcd-7647-9bfc-c4f208c7d607
6	01977e79-fe37-75a1-8b40-2fc606a56218
6	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
6	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
6	01977e79-ff63-73de-9441-b73d75415356
6	01977e79-ffca-7f6d-8b09-70a69d42bf8c
6	01977e7a-0026-79ac-a236-50d54f042a62
6	01977e7a-008b-7cb4-b539-be89d4f12cfc
6	01977e7a-00eb-72f3-8dd8-014dcf711be4
6	01977e7a-014a-74b6-81f5-7e8e435a3d75
6	01977e7a-01ab-77da-bcb4-15df70386907
7	aa772379-dbeb-45ce-ab67-b34a249e09c8
4	01977e7c-e1ea-7674-a453-c35ee7829b40
6	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
4	01977e79-ed42-78d3-87ff-6fcc0b3d6de5
2	01977e79-ed42-78d3-87ff-6fcc0b3d6de5
6	0197c761-f334-7314-be66-7ed27933b7be
\.


--
-- Data for Name: user_details; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_details (created_at, id, updated_at, user_id, about_myself, address) FROM stdin;
2025-06-17 15:20:14.614292+00	1	2025-06-17 15:20:14.614292+00	01977e79-e89a-74ae-8914-3607235689dd	\N	\N
2025-06-17 15:20:14.685761+00	2	2025-06-17 15:20:14.685761+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	\N	\N
2025-06-17 15:20:14.699163+00	3	2025-06-17 15:20:14.699163+00	01977e79-eada-7090-8882-1256bfe48b6c	\N	\N
2025-06-17 15:20:14.71123+00	4	2025-06-17 15:20:14.71123+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	\N	\N
2025-06-17 15:20:14.723464+00	5	2025-06-17 15:20:14.723464+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	\N	\N
2025-06-17 15:20:14.734739+00	6	2025-06-17 15:20:14.734739+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	\N	\N
2025-06-17 15:20:14.756387+00	7	2025-06-17 15:20:14.756387+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	\N	\N
2025-06-17 15:20:14.766446+00	8	2025-06-17 15:20:14.766446+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	\N	\N
2025-06-17 15:20:14.778113+00	9	2025-06-17 15:20:14.778113+00	01977e79-eda2-7ae5-8b09-e1073fda1348	\N	\N
2025-06-17 15:20:14.787606+00	10	2025-06-17 15:20:14.787606+00	01977e79-ee12-7598-b6d1-f711a70583a5	\N	\N
2025-06-17 15:20:14.79721+00	11	2025-06-17 15:20:14.79721+00	01977e79-ee76-703d-a9b1-6501c51b721a	\N	\N
2025-06-17 15:20:14.807938+00	12	2025-06-17 15:20:14.807938+00	01977e79-eee1-75ec-9162-a1f466f494e2	\N	\N
2025-06-17 15:20:14.818346+00	13	2025-06-17 15:20:14.818346+00	01977e79-ef42-7285-bf99-a45e727741b8	\N	\N
2025-06-17 15:20:14.828424+00	14	2025-06-17 15:20:14.828424+00	01977e79-efa8-720f-a2d9-9538e1f05127	\N	\N
2025-06-17 15:20:14.840941+00	15	2025-06-17 15:20:14.840941+00	01977e79-f007-77be-ab68-b61fa9a91a6c	\N	\N
2025-06-17 15:20:14.854209+00	16	2025-06-17 15:20:14.854209+00	01977e79-f074-779b-9674-3b07f474ce2c	\N	\N
2025-06-17 15:20:14.866395+00	17	2025-06-17 15:20:14.866395+00	01977e79-f0db-7638-8703-6f6a9ce99f22	\N	\N
2025-06-17 15:20:14.879103+00	18	2025-06-17 15:20:14.879103+00	01977e79-f146-7970-be87-46500bbd6779	\N	\N
2025-06-17 15:20:14.892285+00	19	2025-06-17 15:20:14.892285+00	01977e79-f1a4-7811-840e-404568a4376e	\N	\N
2025-06-17 15:20:14.90561+00	20	2025-06-17 15:20:14.90561+00	01977e79-f210-7ff9-a57a-ff1746221754	\N	\N
2025-06-17 15:20:14.918314+00	21	2025-06-17 15:20:14.972783+00	01977e79-f272-7f27-bed4-a81b480f17fd	Широкий спектр послуг	вул. Лесі Українки 5
2025-06-17 15:20:15.06672+00	22	2025-06-17 15:20:15.090975+00	01977e79-f2d6-7075-a662-c4d24ed8bb3e	Індивідуальний підхід	вул. Франка 21
2025-06-17 15:20:15.148444+00	23	2025-06-17 15:20:15.164203+00	01977e79-f336-722a-8a36-233ae9fe22f7	Уважний до пацієнтів	вул. Грушевського 33
2025-06-17 15:20:15.204648+00	24	2025-06-17 15:20:15.221777+00	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	Індивідуальний підхід	вул. Лесі Українки 5
2025-06-17 15:20:15.276688+00	25	2025-06-17 15:20:15.296203+00	01977e79-f3fe-7b11-a9c7-e104137309cc	Працюю з дітьми та дорослими	вул. Лесі Українки 5
2025-06-17 15:20:15.344636+00	26	2025-06-17 15:20:15.364356+00	01977e79-f469-7573-ba3a-604cd272778f	Уважний до пацієнтів	вул. Лесі Українки 5
2025-06-17 15:20:15.473057+00	28	2025-06-17 15:20:15.491995+00	01977e79-f542-7b27-95ab-c64f22f444ee	Індивідуальний підхід	вул. Хрещатик 12
2025-06-17 15:20:15.539975+00	29	2025-06-17 15:20:15.561396+00	01977e79-f59f-7958-879a-20e6a5dbfbae	Працюю з дітьми та дорослими	вул. Хрещатик 12
2025-06-17 15:20:15.60001+00	30	2025-06-17 15:20:15.617183+00	01977e79-f603-7282-bc16-c98712d0e44d	Широкий спектр послуг	вул. Франка 21
2025-06-17 15:20:15.663955+00	31	2025-06-17 15:20:15.683301+00	01977e79-f664-7879-b2ec-fe94caaacc28	Індивідуальний підхід	вул. Лесі Українки 5
2025-06-17 15:20:15.724498+00	32	2025-06-17 15:20:15.740806+00	01977e79-f6c6-7707-aedb-fe3468f41140	Досвідчений спеціаліст	вул. Франка 21
2025-06-17 15:20:15.783032+00	33	2025-06-17 15:20:15.796855+00	01977e79-f727-734f-abdb-bcd9c7e3ec00	Широкий спектр послуг	вул. Хрещатик 12
2025-06-17 15:20:15.829039+00	34	2025-06-17 15:20:15.847268+00	01977e79-f788-7b5f-84df-0e1fea0599f3	Індивідуальний підхід	вул. Грушевського 33
2025-06-17 15:20:15.895555+00	35	2025-06-17 15:20:15.916683+00	01977e79-f7e9-7b37-ac67-06a8797bd7d4	Широкий спектр послуг	вул. Бандери 1
2025-06-17 15:20:15.965133+00	36	2025-06-17 15:20:15.980426+00	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	Працюю з дітьми та дорослими	вул. Франка 21
2025-06-17 15:20:16.018735+00	37	2025-06-17 15:20:16.037277+00	01977e79-f8b4-7c00-8caa-745ef40934a6	Широкий спектр послуг	вул. Франка 21
2025-06-17 15:20:16.088756+00	38	2025-06-17 15:20:16.105271+00	01977e79-f90c-7eff-9b23-192773bf98bb	Досвідчений спеціаліст	вул. Франка 21
2025-06-17 15:20:16.147876+00	39	2025-06-17 15:20:16.163418+00	01977e79-f975-78ec-81e9-6e13cd6d568a	Працюю з дітьми та дорослими	вул. Грушевського 33
2025-06-17 15:20:16.19945+00	40	2025-06-17 15:20:16.214751+00	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	Широкий спектр послуг	вул. Грушевського 33
2025-06-17 15:20:16.257348+00	41	2025-06-17 15:20:16.28526+00	01977e79-fa48-76ee-b5ac-b933c85acc84	Широкий спектр послуг	вул. Хрещатик 12
2025-06-17 15:20:16.334924+00	42	2025-06-17 15:20:16.356401+00	01977e79-faa9-7b86-a332-e10ef4ed001b	Широкий спектр послуг	вул. Франка 21
2025-06-17 15:20:16.398818+00	43	2025-06-17 15:20:16.414563+00	01977e79-fb15-743c-9d5a-b00ac2953735	Широкий спектр послуг	вул. Хрещатик 12
2025-06-17 15:20:16.457039+00	44	2025-06-17 15:20:16.477359+00	01977e79-fb74-7d65-8dd8-fb25557c7426	Уважний до пацієнтів	вул. Франка 21
2025-06-17 15:20:16.516301+00	45	2025-06-17 15:20:16.536864+00	01977e79-fbdd-7845-982f-a684fb6c9078	Досвідчений спеціаліст	вул. Хрещатик 12
2025-06-17 15:20:16.57819+00	46	2025-06-17 15:20:16.593523+00	01977e79-fc3d-7e26-94be-b6531796eb62	Працюю з дітьми та дорослими	вул. Франка 21
2025-06-17 15:20:16.635128+00	47	2025-06-17 15:20:16.653295+00	01977e79-fca6-7b48-a3a4-2c362c16081c	Досвідчений спеціаліст	вул. Бандери 1
2025-06-17 15:20:16.692593+00	48	2025-06-17 15:20:16.713383+00	01977e79-fd05-7a4c-9854-1f283f24c417	Уважний до пацієнтів	вул. Франка 21
2025-06-17 15:20:16.750915+00	49	2025-06-17 15:20:16.766705+00	01977e79-fd6e-737b-bdb8-d563d1ac8899	Індивідуальний підхід	вул. Лесі Українки 5
2025-06-17 15:20:16.8053+00	50	2025-06-17 15:20:16.819293+00	01977e79-fdcd-7647-9bfc-c4f208c7d607	Уважний до пацієнтів	вул. Грушевського 33
2025-06-17 15:20:16.867347+00	51	2025-06-17 15:20:16.885059+00	01977e79-fe37-75a1-8b40-2fc606a56218	Досвідчений спеціаліст	вул. Грушевського 33
2025-06-17 15:20:16.92756+00	52	2025-06-17 15:20:16.948804+00	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	Працюю з дітьми та дорослими	вул. Хрещатик 12
2025-06-17 15:20:16.986803+00	53	2025-06-17 15:20:17.001655+00	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	Широкий спектр послуг	вул. Грушевського 33
2025-06-17 15:20:17.039198+00	54	2025-06-17 15:20:17.06277+00	01977e79-ff63-73de-9441-b73d75415356	Індивідуальний підхід	вул. Франка 21
2025-06-17 15:20:17.111792+00	55	2025-06-17 15:20:17.134492+00	01977e79-ffca-7f6d-8b09-70a69d42bf8c	Працюю з дітьми та дорослими	вул. Бандери 1
2025-06-17 15:20:17.177773+00	56	2025-06-17 15:20:17.193675+00	01977e7a-0026-79ac-a236-50d54f042a62	Широкий спектр послуг	вул. Бандери 1
2025-06-17 15:20:17.229352+00	57	2025-06-17 15:20:17.248677+00	01977e7a-008b-7cb4-b539-be89d4f12cfc	Широкий спектр послуг	вул. Хрещатик 12
2025-06-17 15:20:17.29537+00	58	2025-06-17 15:20:17.314377+00	01977e7a-00eb-72f3-8dd8-014dcf711be4	Досвідчений спеціаліст	вул. Бандери 1
2025-06-17 15:20:17.358803+00	59	2025-06-17 15:20:17.373327+00	01977e7a-014a-74b6-81f5-7e8e435a3d75	Індивідуальний підхід	вул. Грушевського 33
2025-06-17 15:20:17.410279+00	60	2025-06-17 15:20:17.425338+00	01977e7a-01ab-77da-bcb4-15df70386907	Широкий спектр послуг	вул. Бандери 1
2025-06-17 15:24:30.319013+00	61	2025-06-17 15:24:30.319013+00	01977e7c-e1ea-7674-a453-c35ee7829b40	\N	\N
2025-06-17 16:54:36.445342+00	62	2025-06-17 16:55:43.199044+00	01977ecf-4f42-72b5-8a44-701a5fdb4a9e	безоплатна допомога для ВПО	вул Хрещатик 
2025-07-01 19:07:59.729324+00	152	2025-07-01 19:10:48.011103+00	0197c761-f334-7314-be66-7ed27933b7be	крута спеціалістка	немає
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.users (is_expired, is_locked, created_at, email_id, updated_at, id, email, password) FROM stdin;
f	f	2025-06-17 15:20:08.53793+00	1	2025-06-17 15:20:08.53793+00	01977e79-e89a-74ae-8914-3607235689dd	yuliia.koval846@gmail.com	{bcrypt}$2a$10$TfIi2mr8E7VU2cRAhp5cIO54/ZkPbAaP9uMPmHATVMRs59iQ2zMEy
f	f	2025-06-17 15:20:08.656928+00	2	2025-06-17 15:20:08.656928+00	01977e79-ea6e-77ae-b3d1-3eec79e27af2	marina.koval396@gmail.com	{bcrypt}$2a$10$fe/3T1Q2IIMyLNwdNzQJBOdGGvh5HY1PUuQI0Nkw88FJe/TwivAqS
f	f	2025-06-17 15:20:08.784313+00	3	2025-06-17 15:20:08.784313+00	01977e79-eada-7090-8882-1256bfe48b6c	andrii.kryva192@gmail.com	{bcrypt}$2a$10$SjTRNzNsPkZs55htDO9BleSZC11cX2V7MsG9rnban2unSz1UzRTUu
f	f	2025-06-17 15:20:08.929613+00	4	2025-06-17 15:20:08.929613+00	01977e79-eb5c-7d9d-9999-16de2fa2b5ca	dmytro.bondar314@gmail.com	{bcrypt}$2a$10$kYguI7hl3OHdVyoVfef2Hes/LDyWTLnzVQyFW5hG5M0Dw0ExOqTCW
f	f	2025-06-17 15:20:09.05381+00	5	2025-06-17 15:20:09.05381+00	01977e79-ebea-7cdd-a03a-ad8365e0b5b6	iryna.melnyk771@gmail.com	{bcrypt}$2a$10$hA6e9DzdbH//NIx75HXpFemo89Ya3QTGdJVpTmiYkedgOPdSgPDsy
f	f	2025-06-17 15:20:09.165437+00	6	2025-06-17 15:20:09.165437+00	01977e79-ec66-7a5c-a3fa-859bde202bcd	oksana.kovale452@gmail.com	{bcrypt}$2a$10$xX1GLbszHC1BBKEAM2XYmOfPE1AN8pIAadTmeu0lDhXHwgP5rzK0.
f	f	2025-06-17 15:20:09.276952+00	7	2025-06-17 15:20:09.276952+00	01977e79-ecd6-7e7e-ae29-ecc27d3b3fda	kateryna.shevchenko489@gmail.com	{bcrypt}$2a$10$5djNBDi2jOjdhAPk0ptN6uNLqE9W/Ed1HiXfqO8SxsAqkTngZ34cW
f	f	2025-06-17 15:20:09.48146+00	9	2025-06-17 15:20:09.48146+00	01977e79-eda2-7ae5-8b09-e1073fda1348	iryna.melnyk834@gmail.com	{bcrypt}$2a$10$Z44asRkbUrRA6MS.zO0rD.TJBn9.lzqqAGNstjXBkkYaBsIpsjKoS
f	f	2025-06-17 15:20:09.582932+00	10	2025-06-17 15:20:09.582932+00	01977e79-ee12-7598-b6d1-f711a70583a5	dmytro.tkachuk209@gmail.com	{bcrypt}$2a$10$mZRNsagzwSwJ2Kt1EZPjtuwrR3CaegkK/E2necUFi2oEha3sdY6ba
f	f	2025-06-17 15:20:09.690511+00	11	2025-06-17 15:20:09.690511+00	01977e79-ee76-703d-a9b1-6501c51b721a	kateryna.petrynko663@gmail.com	{bcrypt}$2a$10$dbjsY8K4TmFPW16uwe/cQeZU9.TbtfS8Qmm3Cy4QLJo/OgxY4Ns1u
f	f	2025-06-17 15:20:09.785972+00	12	2025-06-17 15:20:09.785972+00	01977e79-eee1-75ec-9162-a1f466f494e2	oksana.hnatiuk985@gmail.com	{bcrypt}$2a$10$d586KAlnxOjGJhfC9a94yuKJTnOGek.R54wgc6x5OG2eoEalT7MIO
f	f	2025-06-17 15:20:09.890688+00	13	2025-06-17 15:20:09.890688+00	01977e79-ef42-7285-bf99-a45e727741b8	marina.kryva154@gmail.com	{bcrypt}$2a$10$7IoHeO6u8oz3NZqDdRFWR.A58eXCEP/MNg591nZmXRi39Eqk9bDly
f	f	2025-06-17 15:20:09.985571+00	14	2025-06-17 15:20:09.985571+00	01977e79-efa8-720f-a2d9-9538e1f05127	yuliia.tkachuk623@gmail.com	{bcrypt}$2a$10$oL6F3yHw4asHKz1TCjqusuIrlc80ag4UzPSibMH4jbYFCbP5UulG6
f	f	2025-06-17 15:20:10.093671+00	15	2025-06-17 15:20:10.093671+00	01977e79-f007-77be-ab68-b61fa9a91a6c	andrii.melnyk958@gmail.com	{bcrypt}$2a$10$MmI6y8u5ZIulzuwh9O4sA.lOu4FGFAN4V94TA956O8OhyY4zisLk.
f	f	2025-06-17 15:20:10.194321+00	16	2025-06-17 15:20:10.194321+00	01977e79-f074-779b-9674-3b07f474ce2c	dmytro.bondar582@gmail.com	{bcrypt}$2a$10$9YSygKb90A3kslUMMxJ9n.h0.pj8/jgwrgjTP5L69zuRNGks78kya
f	f	2025-06-17 15:20:10.302912+00	17	2025-06-17 15:20:10.302912+00	01977e79-f0db-7638-8703-6f6a9ce99f22	iryna.litvyn120@gmail.com	{bcrypt}$2a$10$6vkpEK0mFVpjX7KyS2wOBuQiu.r0M3c1dZkWimSbGQkIjqZdF2DvW
f	f	2025-06-17 15:20:10.396951+00	18	2025-06-17 15:20:10.396951+00	01977e79-f146-7970-be87-46500bbd6779	olenna.koval942@gmail.com	{bcrypt}$2a$10$FzBQuyqHPww0i0aT3waov.o0mkK7k/c8SenMlCG2BAGIJLbYMawN6
f	f	2025-06-17 15:20:10.505094+00	19	2025-06-17 15:20:10.505094+00	01977e79-f1a4-7811-840e-404568a4376e	vladyslav.hnatiuk397@gmail.com	{bcrypt}$2a$10$CuNB6uEIfO7vLcyHkRg1KOvk.Cg96sPDSixwmjVuOu.SLw3fYDNYe
f	f	2025-06-17 15:20:10.599036+00	20	2025-06-17 15:20:10.599036+00	01977e79-f210-7ff9-a57a-ff1746221754	kateryna.shevchenko875@gmail.com	{bcrypt}$2a$10$fejON1CNA4i6LYm2D3EPku27Rvl0JfzDOGaAt/A/uISJUqQOdIX.C
f	f	2025-06-17 15:20:10.703698+00	21	2025-06-17 15:20:10.703698+00	01977e79-f272-7f27-bed4-a81b480f17fd	marina.tkachuk712@gmail.com	{bcrypt}$2a$10$rNxtkZvdAVXBc3h0E8Nr9.2eloz/jlwY1HwnzDEGCTOwDHkszqEiC
f	f	2025-06-17 15:20:10.799917+00	22	2025-06-17 15:20:10.799917+00	01977e79-f2d6-7075-a662-c4d24ed8bb3e	andrii.shevchenko378@gmail.com	{bcrypt}$2a$10$Y3YWpWkeaRJ8JBCczoO8LeHHNOlahDLM3drHc3jb.waT1XI.M3TLS
f	f	2025-06-17 15:20:10.906116+00	23	2025-06-17 15:20:10.906116+00	01977e79-f336-722a-8a36-233ae9fe22f7	oksana.kryva502@gmail.com	{bcrypt}$2a$10$edvogmLqDxb5sa99fJwTSO5BQy8KYtHLFCbvP2oF5SLMA5huQ85OS
f	f	2025-06-17 15:20:11.000058+00	24	2025-06-17 15:20:11.000058+00	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec	olenna.petrynko438@gmail.com	{bcrypt}$2a$10$q8A2hOv9jm/TjQJHMfSiWOjmxhFC0HFurDAW8f8Qmyfw/0ybv/Bbq
f	f	2025-06-17 15:20:11.108015+00	25	2025-06-17 15:20:11.108015+00	01977e79-f3fe-7b11-a9c7-e104137309cc	dmytro.ilyov761@gmail.com	{bcrypt}$2a$10$JAKRvoE5gZInWEi6CYnwBemQGOVfb4SajdLZWyZMB3W8Ik3K6VTjO
f	f	2025-06-17 15:20:11.213443+00	26	2025-06-17 15:20:11.213443+00	01977e79-f469-7573-ba3a-604cd272778f	vladyslav.koval188@gmail.com	{bcrypt}$2a$10$V5lvfPepxAORqJO/8.xNruWYn.dJAJMsxAcGEHwKhb8Q4P5tpFI4G
f	f	2025-06-17 15:20:11.415011+00	28	2025-06-17 15:20:11.415011+00	01977e79-f542-7b27-95ab-c64f22f444ee	iryna.shevchenko333@gmail.com	{bcrypt}$2a$10$gibhBFnD13y.8XQZtAND/esqby41Owd1PWh.6NHHemc8x4wadREFy
f	f	2025-06-17 15:20:11.516883+00	29	2025-06-17 15:20:11.516883+00	01977e79-f59f-7958-879a-20e6a5dbfbae	kateryna.kryva609@gmail.com	{bcrypt}$2a$10$9zib0teW6I2GxbdA/eAHzeyDo6uDIuEyBeAnWjmixWjGuWjTuEfHi
f	f	2025-06-17 15:20:11.614321+00	30	2025-06-17 15:20:11.614321+00	01977e79-f603-7282-bc16-c98712d0e44d	marina.bondar205@gmail.com	{bcrypt}$2a$10$qMyQ9YXwEFOvH0E4kuNMpe6NfgM98WUt7V.3p5r6iVxSXvEeW6xnG
f	f	2025-06-17 15:20:11.710869+00	31	2025-06-17 15:20:11.710869+00	01977e79-f664-7879-b2ec-fe94caaacc28	andrii.hnatiuk990@gmail.com	{bcrypt}$2a$10$nEsjRKo5KLBdw0M2HsN2Au61zSpUrSWpi3A.W0ryoYiLFDvr2siOm
f	f	2025-06-17 15:20:11.80906+00	32	2025-06-17 15:20:11.80906+00	01977e79-f6c6-7707-aedb-fe3468f41140	oksana.tkachuk431@gmail.com	{bcrypt}$2a$10$JmaP6BC9cSkSMllPnMbjQ.mUy6pl0bCajTt5zC5tLDtw0MoVvqMBW
f	f	2025-06-17 15:20:11.90458+00	33	2025-06-17 15:20:11.90458+00	01977e79-f727-734f-abdb-bcd9c7e3ec00	dmytro.melnyk782@gmail.com	{bcrypt}$2a$10$HrAwyZr8nwtjVVfOjbhopuebeMQ8j290Y2i2F0P2MxZUG5f9PLykG
f	f	2025-06-17 15:20:12.003973+00	34	2025-06-17 15:20:12.003973+00	01977e79-f788-7b5f-84df-0e1fea0599f3	kateryna.bondar213@gmail.com	{bcrypt}$2a$10$cBiHar.rLx1U3E/TlLN9t.ilYFsZcy33JeY5M./n2eyX4klxN0hWW
f	f	2025-06-17 15:20:12.096997+00	35	2025-06-17 15:20:12.096997+00	01977e79-f7e9-7b37-ac67-06a8797bd7d4	marina.litvyn365@gmail.com	{bcrypt}$2a$10$md.02Jwj3AvMiicw2PTwI.qBQ5ATbxitnNDgMCefBcM.Kz9tsxqX6
f	f	2025-06-17 15:20:12.207278+00	36	2025-06-17 15:20:12.207278+00	01977e79-f849-7f1a-a0c1-70bc3d7dae0c	yuliia.petrynko754@gmail.com	{bcrypt}$2a$10$4fF7jqzc21l0mezXYHrSCOgVnXGXlNWIBg40N2HTDj0Ae008sLAZm
f	f	2025-06-17 15:20:12.293493+00	37	2025-06-17 15:20:12.293493+00	01977e79-f8b4-7c00-8caa-745ef40934a6	olenna.kryva288@gmail.com	{bcrypt}$2a$10$kPYLIjKTFR6dubBR8YPziOMzdnQnXJtiimj90yK8A0Ttq/tK3b8ca
f	f	2025-06-17 15:20:12.398687+00	38	2025-06-17 15:20:12.398687+00	01977e79-f90c-7eff-9b23-192773bf98bb	vladyslav.shevchenko126@gmail.com	{bcrypt}$2a$10$ZjC/IWykn1lLycOVReFj0.72jleJ0d8WwQHTv0vWjLXvGAdLDVN.i
f	f	2025-06-17 15:20:12.496809+00	39	2025-06-17 15:20:12.496809+00	01977e79-f975-78ec-81e9-6e13cd6d568a	iryna.koval687@gmail.com	{bcrypt}$2a$10$Y63lDNgD08Wq9wBiMiz1wO2klCYr.RLVbKnQTqhRHg3uDki89AYcK
f	f	2025-06-17 15:20:12.611279+00	40	2025-06-17 15:20:12.611279+00	01977e79-f9d8-7a2d-ae01-5476b8a5ac95	andrii.tkachuk851@gmail.com	{bcrypt}$2a$10$Iia2OR7ENAEBytxx58eVpOjxcYyjHaef64L.Q/J58yI4b5XulGEdi
f	f	2025-06-17 15:20:12.704809+00	41	2025-06-17 15:20:12.704809+00	01977e79-fa48-76ee-b5ac-b933c85acc84	dmytro.kryva329@gmail.com	{bcrypt}$2a$10$8pwAMBiuAePly7P4JSw5IOy38n975dtZCgcOu7IS38Ds4GDd2a3/O
f	f	2025-06-17 15:20:12.815059+00	42	2025-06-17 15:20:12.815059+00	01977e79-faa9-7b86-a332-e10ef4ed001b	oksana.litvyn674@gmail.com	{bcrypt}$2a$10$VRG.8A3WKOlJfbhWrX9quuZZFb9Mkxk5gs9tMrPNmphdW/8bHJ/MG
f	f	2025-06-17 15:20:12.908632+00	43	2025-06-17 15:20:12.908632+00	01977e79-fb15-743c-9d5a-b00ac2953735	kateryna.hnatiuk598@gmail.com	{bcrypt}$2a$10$2iQk/aCzUxwidEwANJslcucJvPbi5ap0mq4cwHMpKTzllCoTIRLsK
f	f	2025-06-17 15:20:13.016046+00	44	2025-06-17 15:20:13.016046+00	01977e79-fb74-7d65-8dd8-fb25557c7426	marina.melnyk120@gmail.com	{bcrypt}$2a$10$MjaJ4uypF9udHyTxVzoPiO.RdGG5jgOVeSLBvj/novnlO3orwBjSG
f	f	2025-06-17 15:20:13.110127+00	45	2025-06-17 15:20:13.110127+00	01977e79-fbdd-7845-982f-a684fb6c9078	olenna.shevchenko743@gmail.com	{bcrypt}$2a$10$zGfpY2mYZEj2LnPEcp2ov.LE.quSbPLecn1BbNrXBE3gv6lVy/QnO
f	f	2025-06-17 15:20:13.216844+00	46	2025-06-17 15:20:13.216844+00	01977e79-fc3d-7e26-94be-b6531796eb62	iryna.bondar401@gmail.com	{bcrypt}$2a$10$D5yUREknM2iKoa44Oo3Sj.LtID4WaT6BNjbiAxH8ULkXd/yDCmbBC
f	f	2025-06-17 15:20:13.310272+00	47	2025-06-17 15:20:13.310272+00	01977e79-fca6-7b48-a3a4-2c362c16081c	yuliia.kryva523@gmail.com	{bcrypt}$2a$10$UlIhYmL/8uHxYqp.EJEkcuj5IFydf251N8dDdqow5drdUrw8Gh6P2
f	f	2025-06-17 15:20:13.415249+00	48	2025-06-17 15:20:13.415249+00	01977e79-fd05-7a4c-9854-1f283f24c417	vladyslav.petrynko444@gmail.com	{bcrypt}$2a$10$yWLw5Cq5nanp3fygL2YtyuTRC3RBarJLH2QI0ZlI3Esv3bOwxU2fa
f	f	2025-06-17 15:20:13.510395+00	49	2025-06-17 15:20:13.510395+00	01977e79-fd6e-737b-bdb8-d563d1ac8899	andrii.koval991@gmail.com	{bcrypt}$2a$10$KbNIE17kbGx4D7cmd4zUUulsIUELOv384uFlqt6zqKwUXQNwwwgDe
f	f	2025-06-17 15:20:13.616183+00	50	2025-06-17 15:20:13.616183+00	01977e79-fdcd-7647-9bfc-c4f208c7d607	kateryna.tkachuk385@gmail.com	{bcrypt}$2a$10$fYU319EtawOSKGA6TiUY2eKXSYVmVO9ioNApWKaRpFUBJ.QhetZ02
f	f	2025-06-17 15:20:13.714017+00	51	2025-06-17 15:20:13.714017+00	01977e79-fe37-75a1-8b40-2fc606a56218	marina.shevchenko567@gmail.com	{bcrypt}$2a$10$y86mlYih1FTV4CJDyufPBO/qiAe83y2185xX6N66ctY2B.zLybud2
f	f	2025-06-17 15:20:13.820611+00	52	2025-06-17 15:20:13.820611+00	01977e79-fe99-7c06-9eaf-c6db0f1ddd60	iryna.tkachuk229@gmail.com	{bcrypt}$2a$10$I/u4LyrKet50uj8twFg0du/wgTKB7cGw0yxhliKDEuvQiIJ.48ys6
f	f	2025-06-17 15:20:13.916672+00	53	2025-06-17 15:20:13.916672+00	01977e79-ff04-74d9-87bc-c1de6d4a2c4c	olenna.melnyk300@gmail.com	{bcrypt}$2a$10$R.M4Jv9qZSwk6TRyeZ2j3.8bzJ8dJB2h5PN3uB6EZdqhtwCUDDM92
f	f	2025-06-17 15:20:14.020131+00	54	2025-06-17 15:20:14.020131+00	01977e79-ff63-73de-9441-b73d75415356	dmytro.koval967@gmail.com	{bcrypt}$2a$10$cswSF3p7QrXUzXMxM/krn.ERQ1aL5oZctElkk12gV1Qcs2Y7/Z3S2
f	f	2025-06-17 15:20:14.112572+00	55	2025-06-17 15:20:14.112572+00	01977e79-ffca-7f6d-8b09-70a69d42bf8c	oksana.petrynko212@gmail.com	{bcrypt}$2a$10$vdVeMfdJrvI0qA4xFNcxDOxrdrzfVYqcqORYeFW0INdt.yjpyGCR.
f	f	2025-06-17 15:20:14.213775+00	56	2025-06-17 15:20:14.213775+00	01977e7a-0026-79ac-a236-50d54f042a62	yuliia.hnatiuk688@gmail.com	{bcrypt}$2a$10$S525lHkSj0yz2fIUCXmarub4OIjnlW5UXE6xVxhu4boT1qQTS9zG.
f	f	2025-06-17 15:20:14.308081+00	57	2025-06-17 15:20:14.308081+00	01977e7a-008b-7cb4-b539-be89d4f12cfc	andrii.bondar322@gmail.com	{bcrypt}$2a$10$AZk3CnXwhvaIcn8KCHXpy.3RKt7cNtEy.z6XqVdrDuJrJl1LPmUl.
f	f	2025-06-17 15:20:14.403327+00	58	2025-06-17 15:20:14.403327+00	01977e7a-00eb-72f3-8dd8-014dcf711be4	vladyslav.melnyk708@gmail.com	{bcrypt}$2a$10$8IC.jYMQ/TJplxyPrYN4RuRGEZZfvBPvMIxhooGJGkZ7syVMHS3dy
f	f	2025-06-17 15:20:14.501083+00	59	2025-06-17 15:20:14.501083+00	01977e7a-014a-74b6-81f5-7e8e435a3d75	iryna.kryva544@gmail.com	{bcrypt}$2a$10$GCqiACgOWjHJ0cGkzlEdJO/T/sd9tGXVpaCDmzLw5LcyKIHXeWhtW
f	f	2025-06-17 15:20:14.598496+00	60	2025-06-17 15:20:14.598496+00	01977e7a-01ab-77da-bcb4-15df70386907	kateryna.koval190@gmail.com	{bcrypt}$2a$10$JIedVpgTIt4etJ8qOf52PuEnQ5.sqKBnZc/Xyz1dC2LHSZePFBfWm
f	f	2025-06-17 15:21:11.904311+00	666	2025-06-17 15:21:11.904311+00	aa772379-dbeb-45ce-ab67-b34a249e09c8	dmitriy.iliyov@gmail.com	{bcrypt}$2a$10$EVIgZBQgxl5Ig4itVFW7S.GUAPRGmesOVIBcfd9.lD3H7xjOFj04G
f	f	2025-06-17 15:23:38.036243+00	121	2025-06-17 15:23:38.036243+00	01977e7c-e1ea-7674-a453-c35ee7829b40	t30095758@gmail.com	{bcrypt}$2a$10$wr9SttYM8cto3G2qF80/ouNydVrGae.UbApSCgoHqEZ31WpnsElUq
f	f	2025-06-17 16:53:36.362948+00	123	2025-06-17 16:53:36.362948+00	01977ecf-4f42-72b5-8a44-701a5fdb4a9e	testvolunteer94@gmail.com	{bcrypt}$2a$10$P1w9PzatPsDaagvk.gdLBeLSe9.U4zhWXLeoR/BPfPkca3A5DsXXy
f	f	2025-06-17 15:20:09.368567+00	8	2025-06-29 20:36:33.269616+00	01977e79-ed42-78d3-87ff-6fcc0b3d6de5	dmitriy.iliyov32@gmail.com	{bcrypt}$2a$10$DdLxijZGm7XVgJW0aftgUuyflFnAL.GDuYBmjm0sU84G23aq9GI26
f	f	2025-07-01 19:06:30.700868+00	162	2025-07-01 19:06:30.700868+00	0197c761-f334-7314-be66-7ed27933b7be	s2kr49@gmail.com	{bcrypt}$2a$10$5e.GTL4b/z6Ycvby6GsJdOSneW6bXzzYaqm1thug8mJCp.dNXoF72
\.


--
-- Data for Name: work_intervals; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.work_intervals (date, end_t, start_t, id, owner_id) FROM stdin;
2025-07-10	09:30:00	09:00:00	2	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-14	09:30:00	09:00:00	5	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	12:30:00	12:00:00	6	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	12:00:00	11:30:00	7	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	17:30:00	17:00:00	9	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-04	16:00:00	15:30:00	10	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-05	13:30:00	13:00:00	11	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	08:30:00	08:00:00	12	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	08:30:00	08:00:00	15	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	19:00:00	18:30:00	17	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-14	13:30:00	13:00:00	18	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	21:30:00	21:00:00	20	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	18:00:00	17:30:00	22	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	17:30:00	17:00:00	24	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-26	00:00:00	23:30:00	25	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	20:30:00	20:00:00	27	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-26	19:00:00	18:30:00	29	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	20:30:00	20:00:00	30	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	09:30:00	09:00:00	33	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-26	20:30:00	20:00:00	34	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-26	17:30:00	17:00:00	36	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-04	18:30:00	18:00:00	37	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-14	08:30:00	08:00:00	38	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	11:00:00	10:30:00	41	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-05	18:30:00	18:00:00	42	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	16:00:00	15:30:00	44	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	08:30:00	08:00:00	45	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-04	08:30:00	08:00:00	48	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-04	12:30:00	12:00:00	51	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	15:30:00	15:00:00	53	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	19:00:00	18:30:00	55	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	19:00:00	18:30:00	56	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-11	19:00:00	18:30:00	57	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	22:30:00	22:00:00	58	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	13:00:00	12:30:00	60	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-05	11:00:00	10:30:00	63	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	14:00:00	13:30:00	64	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	11:30:00	11:00:00	68	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	15:30:00	15:00:00	69	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	15:30:00	15:00:00	70	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-26	23:00:00	22:30:00	74	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	18:30:00	18:00:00	76	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	14:30:00	14:00:00	77	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	14:30:00	14:00:00	78	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	11:00:00	10:30:00	79	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	11:30:00	11:00:00	80	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	11:30:00	11:00:00	81	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-04	11:30:00	11:00:00	82	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-26	22:00:00	21:30:00	84	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	10:00:00	09:30:00	85	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	17:00:00	16:30:00	87	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	17:00:00	16:30:00	90	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	22:30:00	22:00:00	91	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	21:30:00	21:00:00	94	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	18:00:00	17:30:00	96	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	21:30:00	21:00:00	97	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	10:30:00	10:00:00	99	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	13:30:00	13:00:00	103	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-10	16:30:00	16:00:00	107	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-01	12:00:00	11:30:00	6938	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	20:30:00	20:00:00	109	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	19:30:00	19:00:00	113	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-14	16:30:00	16:00:00	114	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	19:30:00	19:00:00	115	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	15:00:00	14:30:00	118	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	13:30:00	13:00:00	119	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	16:30:00	16:00:00	120	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-08	20:30:00	20:00:00	121	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-14	22:00:00	21:30:00	123	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-30	10:00:00	09:30:00	124	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-28	10:00:00	09:30:00	126	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-02	12:00:00	11:30:00	127	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-24	12:30:00	12:00:00	130	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	09:30:00	09:00:00	132	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	16:30:00	16:00:00	133	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-04	09:30:00	09:00:00	134	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	16:00:00	15:30:00	137	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	17:30:00	17:00:00	138	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	21:30:00	21:00:00	139	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	12:00:00	11:30:00	140	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	18:00:00	17:30:00	142	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	13:30:00	13:00:00	143	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	17:30:00	17:00:00	145	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	00:30:00	00:00:00	146	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	16:00:00	15:30:00	149	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	16:00:00	15:30:00	152	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	08:30:00	08:00:00	153	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	08:30:00	08:00:00	154	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	08:30:00	08:00:00	156	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	08:30:00	08:00:00	159	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	18:00:00	17:30:00	161	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	13:30:00	13:00:00	163	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	12:00:00	11:30:00	164	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	12:30:00	12:00:00	167	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-04	13:00:00	12:30:00	168	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	12:30:00	12:00:00	169	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	17:30:00	17:00:00	170	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	21:00:00	20:30:00	171	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	20:30:00	20:00:00	172	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	19:00:00	18:30:00	173	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	09:30:00	09:00:00	175	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	09:30:00	09:00:00	177	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	21:30:00	21:00:00	178	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	20:30:00	20:00:00	180	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	19:00:00	18:30:00	181	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-08	18:30:00	18:00:00	182	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	17:30:00	17:00:00	185	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	18:30:00	18:00:00	186	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	16:00:00	15:30:00	187	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	11:00:00	10:30:00	188	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	08:30:00	08:00:00	189	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	11:00:00	10:30:00	190	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	16:00:00	15:30:00	192	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	08:30:00	08:00:00	193	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	17:30:00	17:00:00	198	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-03	08:30:00	08:00:00	199	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	15:30:00	15:00:00	203	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-05	12:30:00	12:00:00	205	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	15:30:00	15:00:00	206	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	12:30:00	12:00:00	207	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	23:00:00	22:30:00	211	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	16:00:00	15:30:00	214	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	08:30:00	08:00:00	215	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	11:30:00	11:00:00	216	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	08:30:00	08:00:00	217	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	13:00:00	12:30:00	218	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	13:00:00	12:30:00	219	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-25	11:00:00	10:15:00	6939	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
2025-07-02	18:30:00	18:00:00	226	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	18:30:00	18:00:00	229	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	11:30:00	11:00:00	232	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	13:00:00	12:30:00	233	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	11:00:00	10:30:00	234	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	19:30:00	19:00:00	235	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	19:30:00	19:00:00	236	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-04	15:30:00	15:00:00	237	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-03	15:30:00	15:00:00	238	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-08	19:30:00	19:00:00	239	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-04	19:30:00	19:00:00	240	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	17:00:00	16:30:00	242	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	23:00:00	22:30:00	243	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	15:00:00	14:30:00	244	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	14:30:00	14:00:00	245	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	18:30:00	18:00:00	246	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	14:30:00	14:00:00	250	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	17:00:00	16:30:00	251	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	14:00:00	13:30:00	252	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	10:00:00	09:30:00	258	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	10:00:00	09:30:00	259	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	11:30:00	11:00:00	262	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	17:00:00	16:30:00	265	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	11:00:00	10:30:00	266	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	14:00:00	13:30:00	268	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	11:00:00	10:30:00	269	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	22:30:00	22:00:00	270	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	14:30:00	14:00:00	272	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	19:30:00	19:00:00	273	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	22:00:00	21:30:00	275	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	14:30:00	14:00:00	276	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	21:30:00	21:00:00	277	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-04	10:30:00	10:00:00	280	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	20:00:00	19:30:00	283	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	12:00:00	11:30:00	284	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	18:00:00	17:30:00	285	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-08	12:00:00	11:30:00	286	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	09:30:00	09:00:00	288	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-03	16:30:00	16:00:00	290	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	20:30:00	20:00:00	293	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-05	15:00:00	14:30:00	296	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	15:00:00	14:30:00	297	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	19:30:00	19:00:00	298	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	20:00:00	19:30:00	300	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-11	20:30:00	20:00:00	301	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	15:00:00	14:30:00	302	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-01	13:30:00	13:00:00	303	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-05	20:30:00	20:00:00	304	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	13:30:00	13:00:00	305	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-02	16:30:00	16:00:00	306	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-09	20:30:00	20:00:00	307	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	20:00:00	19:30:00	308	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-07-07	20:30:00	20:00:00	309	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-28	10:00:00	09:30:00	311	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-27	10:00:00	09:30:00	312	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-24	10:00:00	09:30:00	314	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-23	16:30:00	16:00:00	318	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-09	09:30:00	09:00:00	320	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	21:00:00	20:30:00	327	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	16:30:00	16:00:00	330	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	12:00:00	11:30:00	331	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-07	17:30:00	17:00:00	332	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-09	13:30:00	13:00:00	333	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	00:00:00	23:30:00	336	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	08:30:00	08:00:00	339	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	08:30:00	08:00:00	345	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	12:00:00	11:30:00	346	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	13:30:00	13:00:00	347	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	12:30:00	12:00:00	351	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	20:30:00	20:00:00	355	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	09:30:00	09:00:00	358	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	18:00:00	17:30:00	361	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	20:00:00	19:30:00	362	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	19:00:00	18:30:00	363	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	17:30:00	17:00:00	364	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	08:30:00	08:00:00	367	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	14:00:00	13:30:00	368	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	11:00:00	10:30:00	369	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	17:30:00	17:00:00	373	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-06	08:30:00	08:00:00	375	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-09	12:30:00	12:00:00	380	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	15:30:00	15:00:00	382	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	15:30:00	15:00:00	383	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	19:00:00	18:30:00	391	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	16:00:00	15:30:00	392	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	08:30:00	08:00:00	393	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	11:00:00	10:30:00	394	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	13:00:00	12:30:00	395	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-09	11:00:00	10:30:00	402	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	17:00:00	16:30:00	411	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	18:30:00	18:00:00	413	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	14:30:00	14:00:00	414	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	22:30:00	22:00:00	415	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	14:30:00	14:00:00	417	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	11:00:00	10:30:00	422	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-07	10:00:00	09:30:00	423	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	11:00:00	10:30:00	424	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	19:30:00	19:00:00	427	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-09	14:30:00	14:00:00	429	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	13:30:00	13:00:00	430	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-07	12:00:00	11:30:00	439	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	21:30:00	21:00:00	440	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-09	16:30:00	16:00:00	443	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-23	09:30:00	09:00:00	444	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	16:30:00	16:00:00	447	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-02	15:00:00	14:30:00	449	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-14	00:30:00	00:00:00	450	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-27	10:00:00	09:30:00	453	01977e79-f336-722a-8a36-233ae9fe22f7
2025-06-24	12:30:00	12:00:00	457	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	12:30:00	12:00:00	461	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	21:30:00	21:00:00	462	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	18:00:00	17:30:00	463	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	18:00:00	17:30:00	467	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	12:30:00	12:00:00	470	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	12:30:00	12:00:00	471	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-10	13:00:00	12:30:00	473	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	20:30:00	20:00:00	474	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	20:30:00	20:00:00	476	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	21:30:00	21:00:00	477	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	18:00:00	17:30:00	478	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	18:00:00	17:30:00	480	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	20:30:00	20:00:00	481	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	17:30:00	17:00:00	482	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	17:30:00	17:00:00	485	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-15	23:00:00	22:30:00	489	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	15:30:00	15:00:00	490	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-12	12:30:00	12:00:00	491	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	23:00:00	22:30:00	493	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	23:00:00	22:30:00	494	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	22:30:00	22:00:00	495	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	13:00:00	12:30:00	498	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	21:00:00	20:30:00	499	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	15:30:00	15:00:00	502	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	13:00:00	12:30:00	504	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	15:00:00	14:30:00	505	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	15:00:00	14:30:00	507	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	14:00:00	13:30:00	509	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	11:30:00	11:00:00	512	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-10	10:00:00	09:30:00	513	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	11:30:00	11:00:00	516	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-12	11:30:00	11:00:00	517	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-04	10:00:00	09:30:00	518	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	14:00:00	13:30:00	519	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	20:00:00	19:30:00	523	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-12	10:30:00	10:00:00	524	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-15	21:30:00	21:00:00	525	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	10:00:00	09:30:00	526	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-07	16:30:00	16:00:00	527	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	10:00:00	09:30:00	528	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-12	15:00:00	14:30:00	529	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	15:00:00	14:30:00	530	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-03	16:30:00	16:00:00	531	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-04	16:30:00	16:00:00	532	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-10	15:00:00	14:30:00	534	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-07	15:00:00	14:30:00	536	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	20:00:00	19:30:00	538	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	20:00:00	19:30:00	539	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	15:00:00	14:30:00	540	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	16:30:00	16:00:00	541	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	16:30:00	16:00:00	542	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	10:00:00	09:30:00	544	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	16:30:00	16:00:00	546	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	16:30:00	16:00:00	553	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	16:30:00	16:00:00	555	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	12:00:00	11:30:00	556	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	00:30:00	00:00:00	557	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	16:00:00	15:30:00	559	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	08:30:00	08:00:00	560	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-03	13:30:00	13:00:00	562	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	08:30:00	08:00:00	563	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	19:00:00	18:30:00	564	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	19:00:00	18:30:00	566	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	08:30:00	08:00:00	567	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	08:30:00	08:00:00	568	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-15	13:30:00	13:00:00	569	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	08:30:00	08:00:00	570	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	12:00:00	11:30:00	572	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	13:30:00	13:00:00	574	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	12:00:00	11:30:00	576	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	19:00:00	18:30:00	579	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	19:00:00	18:30:00	580	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	00:30:00	00:00:00	581	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	00:30:00	00:00:00	582	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	19:00:00	18:30:00	583	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	16:00:00	15:30:00	584	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	14:00:00	13:30:00	588	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	16:00:00	15:30:00	589	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	11:00:00	10:30:00	590	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	11:00:00	10:30:00	591	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	08:30:00	08:00:00	592	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	18:30:00	18:00:00	593	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-03	11:00:00	10:30:00	595	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-10	08:30:00	08:00:00	596	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	08:30:00	08:00:00	607	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	08:30:00	08:00:00	608	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-15	11:00:00	10:30:00	609	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-07	11:00:00	10:30:00	613	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-10	11:00:00	10:30:00	616	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	15:30:00	15:00:00	618	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-07	19:30:00	19:00:00	619	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	19:30:00	19:00:00	621	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	19:30:00	19:00:00	625	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	14:30:00	14:00:00	626	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	18:30:00	18:00:00	627	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	14:30:00	14:00:00	628	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	18:30:00	18:00:00	632	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-07	23:30:00	23:00:00	633	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	17:00:00	16:30:00	634	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	11:00:00	10:30:00	635	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	22:00:00	21:30:00	636	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	22:00:00	21:30:00	637	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	22:00:00	21:30:00	638	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	11:00:00	10:30:00	639	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	11:00:00	10:30:00	640	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	11:00:00	10:30:00	641	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	11:00:00	10:30:00	642	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	22:00:00	21:30:00	645	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	13:30:00	13:00:00	647	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-03	18:00:00	17:30:00	649	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-04	12:00:00	11:30:00	653	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	13:30:00	13:00:00	656	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	09:30:00	09:00:00	658	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-24	09:30:00	09:00:00	659	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-02	20:30:00	20:00:00	660	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	09:30:00	09:00:00	665	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	19:30:00	19:00:00	666	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-27	09:30:00	09:00:00	667	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-12	20:30:00	20:00:00	668	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-15	20:30:00	20:00:00	669	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-01	13:30:00	13:00:00	670	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-26	23:30:00	23:00:00	674	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-10	17:00:00	16:30:00	675	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	22:00:00	21:30:00	677	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-06	17:00:00	16:30:00	678	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-07-11	17:00:00	16:30:00	680	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-30	23:30:00	23:00:00	682	01977e79-f3a0-77ba-8e1c-a76a4a61a0ec
2025-06-23	12:30:00	12:00:00	684	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-14	09:30:00	09:00:00	689	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-09	17:30:00	17:00:00	693	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	13:30:00	13:00:00	694	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	18:00:00	17:30:00	695	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	08:30:00	08:00:00	701	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-11	00:00:00	23:30:00	703	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	08:30:00	08:00:00	705	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	21:00:00	20:30:00	711	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	19:00:00	18:30:00	715	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	20:30:00	20:00:00	722	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-04	18:30:00	18:00:00	723	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-09	08:30:00	08:00:00	728	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	08:30:00	08:00:00	729	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-06	08:30:00	08:00:00	731	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-04	08:30:00	08:00:00	733	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-09	12:30:00	12:00:00	738	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-04	12:30:00	12:00:00	740	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	15:30:00	15:00:00	741	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	15:30:00	15:00:00	743	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-14	12:30:00	12:00:00	746	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-09	19:00:00	18:30:00	747	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	21:00:00	20:30:00	754	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	11:30:00	11:00:00	755	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	13:00:00	12:30:00	762	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	19:30:00	19:00:00	767	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-09	15:30:00	15:00:00	768	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-13	19:30:00	19:00:00	770	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	14:30:00	14:00:00	771	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	18:30:00	18:00:00	772	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	11:30:00	11:00:00	774	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	17:00:00	16:30:00	775	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	17:00:00	16:30:00	776	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-09	10:00:00	09:30:00	782	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	11:00:00	10:30:00	785	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	22:00:00	21:30:00	786	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-06	10:00:00	09:30:00	787	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	14:00:00	13:30:00	790	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	22:30:00	22:00:00	794	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	18:00:00	17:30:00	795	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-13	12:00:00	11:30:00	797	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-04	21:30:00	21:00:00	799	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	19:30:00	19:00:00	811	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	09:30:00	09:00:00	812	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	15:00:00	14:30:00	817	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	20:00:00	19:30:00	818	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-07	17:00:00	16:30:00	821	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-23	23:30:00	23:00:00	822	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-07-13	22:00:00	21:30:00	825	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-25	10:00:00	09:30:00	828	01977e79-f3fe-7b11-a9c7-e104137309cc
2025-06-26	12:30:00	12:00:00	833	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	12:30:00	12:00:00	834	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	21:30:00	21:00:00	840	01977e79-f469-7573-ba3a-604cd272778f
2025-07-03	17:30:00	17:00:00	842	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	13:00:00	12:30:00	843	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	21:30:00	21:00:00	847	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	21:00:00	20:30:00	849	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	12:30:00	12:00:00	851	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	17:30:00	17:00:00	852	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	00:00:00	23:30:00	853	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	18:00:00	17:30:00	858	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	20:30:00	20:00:00	861	01977e79-f469-7573-ba3a-604cd272778f
2025-07-11	20:00:00	19:30:00	862	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	17:30:00	17:00:00	863	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	17:30:00	17:00:00	864	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	17:30:00	17:00:00	868	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	17:30:00	17:00:00	869	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	13:00:00	12:30:00	878	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	15:00:00	14:30:00	880	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	15:00:00	14:30:00	882	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	10:30:00	10:00:00	883	01977e79-f469-7573-ba3a-604cd272778f
2025-07-09	11:30:00	11:00:00	884	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	14:00:00	13:30:00	890	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	22:30:00	22:00:00	896	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	20:00:00	19:30:00	901	01977e79-f469-7573-ba3a-604cd272778f
2025-07-11	10:30:00	10:00:00	902	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	21:30:00	21:00:00	903	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	10:00:00	09:30:00	904	01977e79-f469-7573-ba3a-604cd272778f
2025-07-05	15:00:00	14:30:00	908	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	20:00:00	19:30:00	912	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	15:00:00	14:30:00	913	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	15:00:00	14:30:00	914	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	16:30:00	16:00:00	916	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	20:00:00	19:30:00	917	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	10:00:00	09:30:00	918	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	10:00:00	09:30:00	919	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	10:00:00	09:30:00	920	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	10:30:00	10:00:00	921	01977e79-f469-7573-ba3a-604cd272778f
2025-07-04	09:30:00	09:00:00	928	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	16:30:00	16:00:00	932	01977e79-f469-7573-ba3a-604cd272778f
2025-07-11	09:30:00	09:00:00	933	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	12:00:00	11:30:00	934	01977e79-f469-7573-ba3a-604cd272778f
2025-07-05	13:30:00	13:00:00	936	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	08:30:00	08:00:00	938	01977e79-f469-7573-ba3a-604cd272778f
2025-07-09	16:00:00	15:30:00	939	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	08:30:00	08:00:00	941	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	08:30:00	08:00:00	942	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	19:00:00	18:30:00	943	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	08:30:00	08:00:00	944	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	13:30:00	13:00:00	947	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	19:00:00	18:30:00	951	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	19:00:00	18:30:00	952	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	09:30:00	09:00:00	955	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	19:00:00	18:30:00	956	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	14:00:00	13:30:00	958	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	08:30:00	08:00:00	959	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	11:00:00	10:30:00	960	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	14:00:00	13:30:00	961	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	16:00:00	15:30:00	963	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	16:00:00	15:30:00	965	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	16:00:00	15:30:00	967	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	19:00:00	18:30:00	973	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	16:00:00	15:30:00	974	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	08:30:00	08:00:00	975	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	08:30:00	08:00:00	976	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	11:00:00	10:30:00	977	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	11:30:00	11:00:00	978	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	11:30:00	11:00:00	982	01977e79-f469-7573-ba3a-604cd272778f
2025-07-03	15:30:00	15:00:00	984	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	15:30:00	15:00:00	986	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	17:00:00	16:30:00	988	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	23:00:00	22:30:00	989	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	14:30:00	14:00:00	990	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	18:30:00	18:00:00	994	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	14:30:00	14:00:00	995	01977e79-f469-7573-ba3a-604cd272778f
2025-06-25	11:00:00	10:30:00	998	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	11:00:00	10:30:00	999	01977e79-f469-7573-ba3a-604cd272778f
2025-07-01	19:30:00	19:00:00	1003	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	13:30:00	13:00:00	1005	01977e79-f469-7573-ba3a-604cd272778f
2025-07-09	18:00:00	17:30:00	1006	01977e79-f469-7573-ba3a-604cd272778f
2025-07-04	18:00:00	17:30:00	1008	01977e79-f469-7573-ba3a-604cd272778f
2025-07-05	18:00:00	17:30:00	1009	01977e79-f469-7573-ba3a-604cd272778f
2025-07-03	12:00:00	11:30:00	1012	01977e79-f469-7573-ba3a-604cd272778f
2025-07-11	14:30:00	14:00:00	1018	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	13:30:00	13:00:00	1019	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	18:00:00	17:30:00	1020	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	19:30:00	19:00:00	1021	01977e79-f469-7573-ba3a-604cd272778f
2025-06-28	09:30:00	09:00:00	1022	01977e79-f469-7573-ba3a-604cd272778f
2025-07-14	20:30:00	20:00:00	1023	01977e79-f469-7573-ba3a-604cd272778f
2025-06-26	23:30:00	23:00:00	1027	01977e79-f469-7573-ba3a-604cd272778f
2025-07-02	12:00:00	11:30:00	1029	01977e79-f469-7573-ba3a-604cd272778f
2025-07-09	22:00:00	21:30:00	1030	01977e79-f469-7573-ba3a-604cd272778f
2025-06-27	12:30:00	12:00:00	1252	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	21:30:00	21:00:00	1253	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	21:30:00	21:00:00	1259	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	18:00:00	17:30:00	1260	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-13	17:30:00	17:00:00	1261	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	18:00:00	17:30:00	1262	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	21:00:00	20:30:00	1264	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	13:00:00	12:30:00	1266	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	21:00:00	20:30:00	1268	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	00:00:00	23:30:00	1269	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	21:00:00	20:30:00	1271	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	20:30:00	20:00:00	1272	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	20:00:00	19:30:00	1274	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	17:30:00	17:00:00	1278	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	15:30:00	15:00:00	1282	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	15:30:00	15:00:00	1283	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	15:30:00	15:00:00	1284	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-05	23:00:00	22:30:00	1285	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	15:30:00	15:00:00	1286	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-14	12:30:00	12:00:00	1287	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	13:00:00	12:30:00	1289	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	13:00:00	12:30:00	1290	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	13:00:00	12:30:00	1294	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	13:00:00	12:30:00	1295	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	10:30:00	10:00:00	1296	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	10:30:00	10:00:00	1298	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	14:00:00	13:30:00	1299	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	11:30:00	11:00:00	1301	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-13	10:00:00	09:30:00	1303	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-05	10:00:00	09:30:00	1306	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	14:00:00	13:30:00	1307	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	10:00:00	09:30:00	1308	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	22:30:00	22:00:00	1309	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-08	10:30:00	10:00:00	1312	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	10:30:00	10:00:00	1314	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	16:30:00	16:00:00	1316	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	15:00:00	14:30:00	1317	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	20:00:00	19:30:00	1320	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	20:00:00	19:30:00	1321	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	20:00:00	19:30:00	1322	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	20:00:00	19:30:00	1323	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	10:00:00	09:30:00	1324	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	10:00:00	09:30:00	1325	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	10:00:00	09:30:00	1327	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-08	09:30:00	09:00:00	1330	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	09:30:00	09:00:00	1333	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	16:30:00	16:00:00	1334	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	16:30:00	16:00:00	1335	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	12:00:00	11:30:00	1336	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-05	16:00:00	15:30:00	1337	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-08	13:30:00	13:00:00	1338	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	12:00:00	11:30:00	1339	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	08:30:00	08:00:00	1341	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	08:30:00	08:00:00	1342	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	08:30:00	08:00:00	1343	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	08:30:00	08:00:00	1344	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	16:00:00	15:30:00	1345	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	08:30:00	08:00:00	1346	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	08:30:00	08:00:00	1349	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-13	13:30:00	13:00:00	1351	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-14	13:30:00	13:00:00	1353	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	19:00:00	18:30:00	1356	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	19:00:00	18:30:00	1357	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	19:00:00	18:30:00	1358	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	08:30:00	08:00:00	1359	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	18:30:00	18:00:00	1361	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	16:00:00	15:30:00	1362	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	18:30:00	18:00:00	1365	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-07	08:30:00	08:00:00	1366	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	16:00:00	15:30:00	1367	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-13	18:30:00	18:00:00	1371	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-05	19:00:00	18:30:00	1373	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	11:30:00	11:00:00	1374	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	11:30:00	11:00:00	1378	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-15	14:00:00	13:30:00	1379	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	11:30:00	11:00:00	1382	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-29	11:30:00	11:00:00	1383	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	15:30:00	15:00:00	1384	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-08	19:30:00	19:00:00	1385	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-08	15:30:00	15:00:00	1386	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	23:00:00	22:30:00	1390	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-14	19:30:00	19:00:00	1391	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-12	19:30:00	19:00:00	1392	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	14:30:00	14:00:00	1393	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	18:30:00	18:00:00	1394	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	18:30:00	18:00:00	1396	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	18:30:00	18:00:00	1397	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	14:30:00	14:00:00	1398	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	14:30:00	14:00:00	1399	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	14:30:00	14:00:00	1400	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	17:00:00	16:30:00	1401	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	11:00:00	10:30:00	1403	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	22:00:00	21:30:00	1404	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	22:00:00	21:30:00	1406	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	17:00:00	16:30:00	1407	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	17:00:00	16:30:00	1408	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-03	14:30:00	14:00:00	1418	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-13	14:30:00	14:00:00	1422	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-08	12:00:00	11:30:00	1423	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-14	14:30:00	14:00:00	1425	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-27	13:30:00	13:00:00	1426	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	13:30:00	13:00:00	1427	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-26	09:30:00	09:00:00	1430	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	19:30:00	19:00:00	1431	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-30	09:30:00	09:00:00	1435	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-28	09:30:00	09:00:00	1436	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-14	22:00:00	21:30:00	1441	01977e79-f542-7b27-95ab-c64f22f444ee
2025-07-14	17:00:00	16:30:00	1444	01977e79-f542-7b27-95ab-c64f22f444ee
2025-06-23	12:30:00	12:00:00	1446	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-08	09:30:00	09:00:00	1448	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	16:30:00	16:00:00	1449	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	16:30:00	16:00:00	1450	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	09:30:00	09:00:00	1451	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-14	09:30:00	09:00:00	1452	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	18:00:00	17:30:00	1454	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-08	17:30:00	17:00:00	1457	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	21:30:00	21:00:00	1458	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	18:00:00	17:30:00	1459	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	12:00:00	11:30:00	1460	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	08:30:00	08:00:00	1461	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	08:30:00	08:00:00	1464	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	08:30:00	08:00:00	1467	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	08:30:00	08:00:00	1468	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	08:30:00	08:00:00	1469	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-12	16:00:00	15:30:00	1473	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-12	17:30:00	17:00:00	1475	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	21:30:00	21:00:00	1478	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	12:00:00	11:30:00	1479	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-14	17:30:00	17:00:00	1481	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	12:00:00	11:30:00	1483	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	21:00:00	20:30:00	1486	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	21:00:00	20:30:00	1487	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	20:30:00	20:00:00	1488	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	19:00:00	18:30:00	1489	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-10	20:00:00	19:30:00	1490	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-13	20:00:00	19:30:00	1495	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	20:30:00	20:00:00	1496	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	16:00:00	15:30:00	1497	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	17:30:00	17:00:00	1500	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-13	08:30:00	08:00:00	1503	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-12	08:30:00	08:00:00	1506	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-10	08:30:00	08:00:00	1508	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	16:00:00	15:30:00	1509	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-13	18:30:00	18:00:00	1513	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	17:30:00	17:00:00	1514	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-10	12:30:00	12:00:00	1516	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-09	12:30:00	12:00:00	1517	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	15:30:00	15:00:00	1518	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	15:30:00	15:00:00	1519	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-08	19:00:00	18:30:00	1521	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-13	11:00:00	10:30:00	1526	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	13:00:00	12:30:00	1527	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-14	11:00:00	10:30:00	1528	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	13:00:00	12:30:00	1529	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	11:00:00	10:30:00	1530	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-13	21:00:00	20:30:00	1533	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-08	11:00:00	10:30:00	1534	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	11:30:00	11:00:00	1535	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	23:00:00	22:30:00	1539	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	15:00:00	14:30:00	1546	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	14:30:00	14:00:00	1548	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	14:30:00	14:00:00	1549	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	10:30:00	10:00:00	1550	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	18:30:00	18:00:00	1554	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	10:30:00	10:00:00	1555	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	14:30:00	14:00:00	1556	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	18:30:00	18:00:00	1557	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	17:00:00	16:30:00	1558	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-09	11:30:00	11:00:00	1559	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	11:00:00	10:30:00	1560	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	14:00:00	13:30:00	1561	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-13	10:00:00	09:30:00	1562	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	23:30:00	23:00:00	1565	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	11:00:00	10:30:00	1567	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	22:00:00	21:30:00	1568	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	17:00:00	16:30:00	1570	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	11:00:00	10:30:00	1571	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	22:30:00	22:00:00	1576	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-09	14:30:00	14:00:00	1577	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	13:30:00	13:00:00	1578	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-10	14:30:00	14:00:00	1579	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-12	12:00:00	11:30:00	1580	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	13:30:00	13:00:00	1581	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	13:30:00	13:00:00	1582	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-14	12:00:00	11:30:00	1585	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	12:00:00	11:30:00	1586	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	18:00:00	17:30:00	1588	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-14	14:30:00	14:00:00	1591	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	09:30:00	09:00:00	1592	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-26	19:30:00	19:00:00	1594	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	19:30:00	19:00:00	1595	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-12	15:00:00	14:30:00	1596	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	16:30:00	16:00:00	1600	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	09:30:00	09:00:00	1602	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-30	19:30:00	19:00:00	1603	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	09:30:00	09:00:00	1604	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-15	20:30:00	20:00:00	1609	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-28	20:00:00	19:30:00	1613	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-07-09	22:00:00	21:30:00	1617	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-25	10:00:00	09:30:00	1618	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-23	10:00:00	09:30:00	1620	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-29	12:30:00	12:00:00	1626	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-05	00:00:00	23:30:00	1627	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	18:00:00	17:30:00	1628	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-03	17:30:00	17:00:00	1629	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-04	17:30:00	17:00:00	1630	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	18:00:00	17:30:00	1634	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-11	17:30:00	17:00:00	1635	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	13:00:00	12:30:00	1637	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	12:30:00	12:00:00	1642	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	17:30:00	17:00:00	1643	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-04	13:00:00	12:30:00	1644	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	21:00:00	20:30:00	1645	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	21:00:00	20:30:00	1647	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	20:30:00	20:00:00	1652	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	17:30:00	17:00:00	1655	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	12:30:00	12:00:00	1656	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	15:30:00	15:00:00	1657	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	15:30:00	15:00:00	1658	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-15	12:30:00	12:00:00	1659	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	15:30:00	15:00:00	1661	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-11	12:30:00	12:00:00	1662	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	22:30:00	22:00:00	1663	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	13:00:00	12:30:00	1668	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-03	21:00:00	20:30:00	1669	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	13:00:00	12:30:00	1670	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	13:00:00	12:30:00	1672	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	15:00:00	14:30:00	1673	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	10:30:00	10:00:00	1675	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	11:30:00	11:00:00	1676	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	14:00:00	13:30:00	1677	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	14:00:00	13:30:00	1678	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-05	11:30:00	11:00:00	1683	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-15	11:30:00	11:00:00	1686	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-11	11:30:00	11:00:00	1688	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	14:00:00	13:30:00	1691	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	22:30:00	22:00:00	1692	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-10	21:30:00	21:00:00	1694	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-11	21:30:00	21:00:00	1695	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	20:00:00	19:30:00	1699	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	21:30:00	21:00:00	1700	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-13	21:30:00	21:00:00	1701	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	15:00:00	14:30:00	1702	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-11	15:00:00	14:30:00	1703	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-15	16:30:00	16:00:00	1705	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-11	16:30:00	16:00:00	1706	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	15:00:00	14:30:00	1710	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	20:00:00	19:30:00	1711	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	10:00:00	09:30:00	1712	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	10:00:00	09:30:00	1714	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	16:30:00	16:00:00	1721	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	09:30:00	09:00:00	1722	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-13	09:30:00	09:00:00	1723	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	16:30:00	16:00:00	1724	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	16:00:00	15:30:00	1725	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	12:00:00	11:30:00	1726	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	08:30:00	08:00:00	1729	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	08:30:00	08:00:00	1731	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	19:00:00	18:30:00	1733	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	08:30:00	08:00:00	1734	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	16:00:00	15:30:00	1735	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	08:30:00	08:00:00	1737	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	13:30:00	13:00:00	1739	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-05	01:30:00	01:00:00	1742	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	09:30:00	09:00:00	1744	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	18:30:00	18:00:00	1746	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-04	18:30:00	18:00:00	1748	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-13	08:30:00	08:00:00	1749	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	16:00:00	15:30:00	1751	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	08:30:00	08:00:00	1752	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	11:00:00	10:30:00	1753	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	08:30:00	08:00:00	1755	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	08:30:00	08:00:00	1758	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-04	08:30:00	08:00:00	1761	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-10	19:00:00	18:30:00	1763	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-05	19:00:00	18:30:00	1764	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	16:00:00	15:30:00	1768	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	08:30:00	08:00:00	1770	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	11:30:00	11:00:00	1771	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	11:00:00	10:30:00	1773	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	11:30:00	11:00:00	1774	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-10	11:00:00	10:30:00	1776	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-04	15:30:00	15:00:00	1777	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-13	15:30:00	15:00:00	1779	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	15:30:00	15:00:00	1780	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	19:30:00	19:00:00	1783	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	18:30:00	18:00:00	1785	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	14:30:00	14:00:00	1786	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	18:30:00	18:00:00	1788	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	23:30:00	23:00:00	1789	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	17:00:00	16:30:00	1790	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	11:00:00	10:30:00	1791	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	17:00:00	16:30:00	1792	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	22:00:00	21:30:00	1793	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-10	14:30:00	14:00:00	1797	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-10	18:00:00	17:30:00	1800	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	12:00:00	11:30:00	1801	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-04	12:00:00	11:30:00	1807	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	18:00:00	17:30:00	1809	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	14:30:00	14:00:00	1811	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-14	18:00:00	17:30:00	1813	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	19:30:00	19:00:00	1814	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-24	19:30:00	19:00:00	1815	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	09:30:00	09:00:00	1817	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	19:30:00	19:00:00	1818	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-27	09:30:00	09:00:00	1819	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	20:30:00	20:00:00	1821	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-02	13:30:00	13:00:00	1823	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-07	17:00:00	16:30:00	1825	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-10	17:00:00	16:30:00	1826	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-12	17:00:00	16:30:00	1830	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-29	23:30:00	23:00:00	1831	01977e79-f603-7282-bc16-c98712d0e44d
2025-06-26	12:30:00	12:00:00	1837	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	16:30:00	16:00:00	1838	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	09:30:00	09:00:00	1839	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-04	09:30:00	09:00:00	1840	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	09:30:00	09:00:00	1842	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	12:30:00	12:00:00	1844	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	09:30:00	09:00:00	1845	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-24	21:30:00	21:00:00	1846	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	00:00:00	23:30:00	1848	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	18:00:00	17:30:00	1849	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-10	16:00:00	15:30:00	1850	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	08:30:00	08:00:00	1851	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	19:00:00	18:30:00	1852	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	19:00:00	18:30:00	1853	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	08:30:00	08:00:00	1854	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	17:30:00	17:00:00	1857	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	13:00:00	12:30:00	1859	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	12:30:00	12:00:00	1862	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	17:30:00	17:00:00	1863	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	12:30:00	12:00:00	1864	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	19:00:00	18:30:00	1865	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	20:30:00	20:00:00	1866	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-24	19:00:00	18:30:00	1867	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	20:00:00	19:30:00	1868	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	00:30:00	00:00:00	1869	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	09:30:00	09:00:00	1871	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	18:00:00	17:30:00	1872	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	20:30:00	20:00:00	1873	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-24	20:30:00	20:00:00	1874	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	19:00:00	18:30:00	1875	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-04	18:30:00	18:00:00	1878	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	14:00:00	13:30:00	1879	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	14:00:00	13:30:00	1880	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	11:00:00	10:30:00	1881	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	11:00:00	10:30:00	1882	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	08:30:00	08:00:00	1883	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	18:30:00	18:00:00	1884	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	16:00:00	15:30:00	1888	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	08:30:00	08:00:00	1889	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	17:30:00	17:00:00	1890	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-04	08:30:00	08:00:00	1892	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	23:00:00	22:30:00	1896	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	22:30:00	22:00:00	1898	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	19:00:00	18:30:00	1900	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	08:30:00	08:00:00	1901	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	08:30:00	08:00:00	1902	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	14:00:00	13:30:00	1907	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-04	14:00:00	13:30:00	1908	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	15:30:00	15:00:00	1909	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	15:30:00	15:00:00	1910	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-10	11:00:00	10:30:00	1911	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	11:00:00	10:30:00	1912	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	23:00:00	22:30:00	1913	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	15:30:00	15:00:00	1914	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	15:00:00	14:30:00	1917	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	17:00:00	16:30:00	1920	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	15:00:00	14:30:00	1922	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	11:00:00	10:30:00	1927	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	22:00:00	21:30:00	1929	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	14:00:00	13:30:00	1931	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	11:00:00	10:30:00	1932	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	22:00:00	21:30:00	1933	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	12:00:00	11:30:00	1934	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	14:30:00	14:00:00	1940	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	18:00:00	17:30:00	1941	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	10:30:00	10:00:00	1942	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-15	10:30:00	10:00:00	1944	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	13:30:00	13:00:00	1946	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	10:00:00	09:30:00	1947	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	15:00:00	14:30:00	1949	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	20:30:00	20:00:00	1950	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	16:30:00	16:00:00	1951	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-01	20:30:00	20:00:00	1952	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-11	20:30:00	20:00:00	1957	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-02	16:30:00	16:00:00	1958	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	20:30:00	20:00:00	1959	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-04	17:00:00	16:30:00	1961	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-28	10:00:00	09:30:00	1963	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-26	10:00:00	09:30:00	1964	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-05	22:00:00	21:30:00	1966	01977e79-f664-7879-b2ec-fe94caaacc28
2025-07-03	22:00:00	21:30:00	1967	01977e79-f664-7879-b2ec-fe94caaacc28
2025-06-23	12:30:00	12:00:00	1969	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	18:00:00	17:30:00	1973	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	17:30:00	17:00:00	1979	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-13	17:30:00	17:00:00	1981	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	21:00:00	20:30:00	1982	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	13:00:00	12:30:00	1983	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	21:00:00	20:30:00	1984	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	12:30:00	12:00:00	1985	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	13:00:00	12:30:00	1986	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-07	20:00:00	19:30:00	1987	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	21:30:00	21:00:00	1989	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	17:30:00	17:00:00	1993	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	12:30:00	12:00:00	1994	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	15:30:00	15:00:00	1997	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	12:30:00	12:00:00	1999	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-13	12:30:00	12:00:00	2001	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	22:30:00	22:00:00	2003	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-07	22:30:00	22:00:00	2006	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	13:00:00	12:30:00	2007	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-07	21:00:00	20:30:00	2008	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	21:00:00	20:30:00	2011	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	13:00:00	12:30:00	2012	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	13:00:00	12:30:00	2013	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	15:00:00	14:30:00	2017	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	10:30:00	10:00:00	2018	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	10:30:00	10:00:00	2019	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-03	11:30:00	11:00:00	2022	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	14:00:00	13:30:00	2028	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	22:30:00	22:00:00	2031	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	20:00:00	19:30:00	2033	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	10:30:00	10:00:00	2034	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-13	15:00:00	14:30:00	2035	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	15:00:00	14:30:00	2038	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	16:30:00	16:00:00	2040	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-03	15:00:00	14:30:00	2042	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	15:00:00	14:30:00	2043	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	20:00:00	19:30:00	2045	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	20:00:00	19:30:00	2047	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	10:00:00	09:30:00	2048	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	16:30:00	16:00:00	2051	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	09:30:00	09:00:00	2053	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-03	09:30:00	09:00:00	2059	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	09:30:00	09:00:00	2060	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	09:30:00	09:00:00	2061	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	13:30:00	13:00:00	2063	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	12:00:00	11:30:00	2064	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	08:30:00	08:00:00	2066	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	08:30:00	08:00:00	2067	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	08:30:00	08:00:00	2068	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	16:00:00	15:30:00	2069	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	08:30:00	08:00:00	2070	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	12:00:00	11:30:00	2071	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	12:00:00	11:30:00	2073	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	09:30:00	09:00:00	2078	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	19:00:00	18:30:00	2079	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-03	18:30:00	18:00:00	2083	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	08:30:00	08:00:00	2084	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	11:00:00	10:30:00	2085	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	16:00:00	15:30:00	2086	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	08:30:00	08:00:00	2087	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	16:00:00	15:30:00	2088	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	08:30:00	08:00:00	2090	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-06	08:30:00	08:00:00	2091	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	19:00:00	18:30:00	2095	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	19:00:00	18:30:00	2096	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	16:00:00	15:30:00	2098	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	11:30:00	11:00:00	2099	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	08:30:00	08:00:00	2101	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-13	11:00:00	10:30:00	2102	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	11:00:00	10:30:00	2103	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-15	14:00:00	13:30:00	2105	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-06	11:00:00	10:30:00	2107	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-07	11:00:00	10:30:00	2108	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	18:30:00	18:00:00	2109	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	14:00:00	13:30:00	2110	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	11:00:00	10:30:00	2112	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	15:30:00	15:00:00	2115	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-07	15:30:00	15:00:00	2116	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	17:00:00	16:30:00	2118	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	23:00:00	22:30:00	2121	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	14:30:00	14:00:00	2122	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	18:30:00	18:00:00	2123	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	18:30:00	18:00:00	2124	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	14:30:00	14:00:00	2126	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	17:00:00	16:30:00	2127	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-30	11:00:00	10:30:00	2128	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	17:00:00	16:30:00	2129	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	22:00:00	21:30:00	2132	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	11:00:00	10:30:00	2133	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	14:30:00	14:00:00	2135	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	18:00:00	17:30:00	2137	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	13:30:00	13:00:00	2138	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	14:30:00	14:00:00	2142	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-06	18:00:00	17:30:00	2143	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-06	12:00:00	11:30:00	2145	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	12:00:00	11:30:00	2147	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	18:00:00	17:30:00	2148	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-26	09:30:00	09:00:00	2149	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	19:30:00	19:00:00	2150	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-25	09:30:00	09:00:00	2151	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	19:30:00	19:00:00	2152	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	09:30:00	09:00:00	2153	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-13	20:30:00	20:00:00	2157	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-01	13:30:00	13:00:00	2158	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-09	20:30:00	20:00:00	2159	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	23:30:00	23:00:00	2160	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-07	17:00:00	16:30:00	2161	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-08	17:00:00	16:30:00	2162	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-14	17:00:00	16:30:00	2163	01977e79-f6c6-7707-aedb-fe3468f41140
2025-06-23	16:30:00	16:00:00	2165	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	12:30:00	12:00:00	2166	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	12:30:00	12:00:00	2167	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-04	09:30:00	09:00:00	2168	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	18:00:00	17:30:00	2172	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	21:30:00	21:00:00	2173	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	12:00:00	11:30:00	2174	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	12:00:00	11:30:00	2175	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	08:30:00	08:00:00	2177	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	21:30:00	21:00:00	2179	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	08:30:00	08:00:00	2180	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	08:30:00	08:00:00	2181	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	19:00:00	18:30:00	2184	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	08:30:00	08:00:00	2185	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-12	17:30:00	17:00:00	2187	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-11	13:30:00	13:00:00	2188	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-12	13:00:00	12:30:00	2189	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-15	13:00:00	12:30:00	2190	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	17:30:00	17:00:00	2191	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	19:00:00	18:30:00	2193	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-15	20:00:00	19:30:00	2194	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	19:00:00	18:30:00	2195	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	20:30:00	20:00:00	2196	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	11:00:00	10:30:00	2199	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	16:00:00	15:30:00	2200	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-11	08:30:00	08:00:00	2201	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	16:00:00	15:30:00	2203	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	16:00:00	15:30:00	2204	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	17:30:00	17:00:00	2205	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	17:30:00	17:00:00	2207	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-04	19:00:00	18:30:00	2210	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-15	19:00:00	18:30:00	2211	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	11:30:00	11:00:00	2213	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	08:30:00	08:00:00	2215	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	13:00:00	12:30:00	2216	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-12	14:00:00	13:30:00	2217	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	15:00:00	14:30:00	2221	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	15:00:00	14:30:00	2226	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	14:30:00	14:00:00	2227	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	18:30:00	18:00:00	2228	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	18:30:00	18:00:00	2229	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	10:30:00	10:00:00	2230	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	14:30:00	14:00:00	2231	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	10:30:00	10:00:00	2232	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	17:00:00	16:30:00	2233	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-04	11:30:00	11:00:00	2235	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	11:00:00	10:30:00	2240	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	22:30:00	22:00:00	2241	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	22:30:00	22:00:00	2242	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	13:30:00	13:00:00	2243	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-12	12:00:00	11:30:00	2244	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	13:30:00	13:00:00	2245	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-04	10:30:00	10:00:00	2246	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-11	10:30:00	10:00:00	2249	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	13:30:00	13:00:00	2251	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	10:00:00	09:30:00	2252	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	09:30:00	09:00:00	2253	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	20:30:00	20:00:00	2254	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-15	15:00:00	14:30:00	2256	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	09:30:00	09:00:00	2257	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	09:30:00	09:00:00	2259	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-25	20:00:00	19:30:00	2260	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	15:00:00	14:30:00	2263	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	13:30:00	13:00:00	2264	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	16:30:00	16:00:00	2265	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-30	20:00:00	19:30:00	2266	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-28	20:00:00	19:30:00	2267	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-04	17:00:00	16:30:00	2268	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-02	12:00:00	11:30:00	2270	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-11	22:00:00	21:30:00	2271	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-23	10:00:00	09:30:00	2272	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-07-11	17:00:00	16:30:00	2273	01977e79-f727-734f-abdb-bcd9c7e3ec00
2025-06-24	12:30:00	12:00:00	2277	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-15	09:30:00	09:00:00	2284	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	16:30:00	16:00:00	2286	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-11	09:30:00	09:00:00	2287	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	17:30:00	17:00:00	2288	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	21:30:00	21:00:00	2290	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	12:00:00	11:30:00	2291	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-09	17:30:00	17:00:00	2292	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	08:30:00	08:00:00	2294	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	08:30:00	08:00:00	2296	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-12	16:00:00	15:30:00	2299	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	08:30:00	08:00:00	2300	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-11	17:30:00	17:00:00	2301	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-12	17:30:00	17:00:00	2302	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	12:00:00	11:30:00	2304	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-11	13:00:00	12:30:00	2308	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	20:30:00	20:00:00	2318	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	19:00:00	18:30:00	2319	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	17:30:00	17:00:00	2320	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-15	08:30:00	08:00:00	2322	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	16:00:00	15:30:00	2326	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-11	08:30:00	08:00:00	2327	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	16:00:00	15:30:00	2329	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-09	08:30:00	08:00:00	2330	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	08:30:00	08:00:00	2332	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-08	08:30:00	08:00:00	2333	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	17:30:00	17:00:00	2335	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-08	12:30:00	12:00:00	2337	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	12:30:00	12:00:00	2338	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	15:30:00	15:00:00	2341	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-08	23:00:00	22:30:00	2342	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	19:00:00	18:30:00	2344	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-12	11:00:00	10:30:00	2351	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	13:00:00	12:30:00	2354	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-15	14:00:00	13:30:00	2356	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-08	11:00:00	10:30:00	2360	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-08	15:30:00	15:00:00	2364	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	15:30:00	15:00:00	2365	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	23:00:00	22:30:00	2371	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	15:00:00	14:30:00	2372	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	15:00:00	14:30:00	2373	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	18:30:00	18:00:00	2375	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	10:30:00	10:00:00	2376	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	18:30:00	18:00:00	2377	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	14:30:00	14:00:00	2378	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	11:00:00	10:30:00	2379	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	17:00:00	16:30:00	2380	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-09	11:30:00	11:00:00	2381	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-12	10:00:00	09:30:00	2382	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	10:00:00	09:30:00	2388	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	11:00:00	10:30:00	2389	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	14:00:00	13:30:00	2392	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	13:30:00	13:00:00	2395	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-12	12:00:00	11:30:00	2398	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-11	21:30:00	21:00:00	2401	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-11	10:30:00	10:00:00	2406	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-12	14:30:00	14:00:00	2408	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	16:30:00	16:00:00	2409	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	09:30:00	09:00:00	2410	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	19:30:00	19:00:00	2412	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-09	15:00:00	14:30:00	2416	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-28	09:30:00	09:00:00	2418	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-25	20:00:00	19:30:00	2419	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-09	20:30:00	20:00:00	2424	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-08	20:30:00	20:00:00	2425	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-07	20:30:00	20:00:00	2426	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-07-09	22:00:00	21:30:00	2431	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	10:00:00	09:30:00	2432	01977e79-f788-7b5f-84df-0e1fea0599f3
2025-06-24	12:30:00	12:00:00	2435	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	21:00:00	20:30:00	2437	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	18:00:00	17:30:00	2441	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	00:00:00	23:30:00	2442	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-15	17:30:00	17:00:00	2447	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	21:30:00	21:00:00	2449	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	21:00:00	20:30:00	2451	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	21:00:00	20:30:00	2452	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	17:30:00	17:00:00	2456	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	12:30:00	12:00:00	2457	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	13:00:00	12:30:00	2458	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	20:30:00	20:00:00	2462	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	17:30:00	17:00:00	2463	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	17:30:00	17:00:00	2466	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	15:30:00	15:00:00	2470	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	15:30:00	15:00:00	2472	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-15	12:30:00	12:00:00	2473	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-03	23:00:00	22:30:00	2474	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	13:00:00	12:30:00	2484	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	15:00:00	14:30:00	2488	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	22:30:00	22:00:00	2490	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	11:30:00	11:00:00	2491	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-13	10:00:00	09:30:00	2492	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	14:00:00	13:30:00	2497	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	22:30:00	22:00:00	2499	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	22:30:00	22:00:00	2500	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-08	10:30:00	10:00:00	2501	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	10:30:00	10:00:00	2502	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-07	16:30:00	16:00:00	2504	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	10:00:00	09:30:00	2505	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	16:30:00	16:00:00	2506	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-05	15:00:00	14:30:00	2511	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	20:00:00	19:30:00	2516	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	20:00:00	19:30:00	2517	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	20:00:00	19:30:00	2518	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	15:00:00	14:30:00	2519	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	16:30:00	16:00:00	2522	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	20:00:00	19:30:00	2523	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	10:00:00	09:30:00	2524	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	10:00:00	09:30:00	2525	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	10:00:00	09:30:00	2526	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	10:00:00	09:30:00	2527	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	16:30:00	16:00:00	2532	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	12:00:00	11:30:00	2539	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-08	13:30:00	13:00:00	2541	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	08:30:00	08:00:00	2543	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-08	16:00:00	15:30:00	2544	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	08:30:00	08:00:00	2546	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	08:30:00	08:00:00	2547	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-15	13:30:00	13:00:00	2551	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-13	16:00:00	15:30:00	2552	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	12:00:00	11:30:00	2561	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	19:00:00	18:30:00	2564	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	19:00:00	18:30:00	2568	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-07	18:30:00	18:00:00	2570	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-15	08:30:00	08:00:00	2571	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	14:00:00	13:30:00	2572	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	11:00:00	10:30:00	2574	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	16:00:00	15:30:00	2576	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	16:00:00	15:30:00	2580	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-05	08:30:00	08:00:00	2582	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-13	18:30:00	18:00:00	2584	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-05	19:00:00	18:30:00	2586	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-15	19:00:00	18:30:00	2587	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	08:30:00	08:00:00	2590	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	11:30:00	11:00:00	2591	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-13	11:00:00	10:30:00	2592	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	11:30:00	11:00:00	2593	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-05	11:00:00	10:30:00	2595	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	18:30:00	18:00:00	2597	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	14:00:00	13:30:00	2599	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	19:30:00	19:00:00	2600	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	23:00:00	22:30:00	2606	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	18:30:00	18:00:00	2607	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	14:30:00	14:00:00	2608	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	18:30:00	18:00:00	2609	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	18:30:00	18:00:00	2611	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	14:30:00	14:00:00	2614	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	11:00:00	10:30:00	2615	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-23	17:00:00	16:30:00	2616	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	17:00:00	16:30:00	2617	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	17:00:00	16:30:00	2618	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	22:00:00	21:30:00	2622	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	11:00:00	10:30:00	2623	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	11:00:00	10:30:00	2624	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-01	19:30:00	19:00:00	2626	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-25	13:30:00	13:00:00	2627	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	13:30:00	13:00:00	2628	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-08	18:00:00	17:30:00	2629	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-09	18:00:00	17:30:00	2630	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	13:30:00	13:00:00	2631	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-05	12:00:00	11:30:00	2636	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-28	13:30:00	13:00:00	2640	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	09:30:00	09:00:00	2641	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-24	19:30:00	19:00:00	2642	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-08	20:30:00	20:00:00	2648	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-07	20:30:00	20:00:00	2649	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-08	17:00:00	16:30:00	2650	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-27	12:30:00	12:00:00	2658	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	12:30:00	12:00:00	2660	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	12:30:00	12:00:00	2661	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	16:30:00	16:00:00	2662	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-08	17:30:00	17:00:00	2663	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-06	16:00:00	15:30:00	2664	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	21:30:00	21:00:00	2665	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	21:30:00	21:00:00	2666	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	16:00:00	15:30:00	2667	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	08:30:00	08:00:00	2668	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	13:00:00	12:30:00	2669	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	08:30:00	08:00:00	2670	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	08:30:00	08:00:00	2671	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	19:00:00	18:30:00	2673	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	19:00:00	18:30:00	2674	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-13	16:00:00	15:30:00	2677	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-12	16:00:00	15:30:00	2678	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	18:00:00	17:30:00	2682	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	21:30:00	21:00:00	2683	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	13:00:00	12:30:00	2684	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	00:00:00	23:30:00	2687	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	17:30:00	17:00:00	2688	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	21:00:00	20:30:00	2689	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	20:30:00	20:00:00	2691	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-05	20:00:00	19:30:00	2693	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	09:30:00	09:00:00	2694	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	21:30:00	21:00:00	2695	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	18:00:00	17:30:00	2698	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	19:00:00	18:30:00	2700	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-12	20:00:00	19:30:00	2701	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	16:00:00	15:30:00	2705	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-14	08:30:00	08:00:00	2706	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	11:00:00	10:30:00	2707	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	08:30:00	08:00:00	2708	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-12	08:30:00	08:00:00	2710	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	16:00:00	15:30:00	2712	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-08	08:30:00	08:00:00	2714	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	17:30:00	17:00:00	2717	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	17:30:00	17:00:00	2721	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-08	12:30:00	12:00:00	2722	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	23:00:00	22:30:00	2723	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	15:30:00	15:00:00	2724	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-14	12:30:00	12:00:00	2727	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	23:00:00	22:30:00	2731	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-14	19:00:00	18:30:00	2732	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-08	22:30:00	22:00:00	2733	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	16:00:00	15:30:00	2734	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	08:30:00	08:00:00	2735	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	08:30:00	08:00:00	2736	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-14	11:00:00	10:30:00	2737	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	15:30:00	15:00:00	2743	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	21:00:00	20:30:00	2744	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	15:00:00	14:30:00	2747	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	15:00:00	14:30:00	2748	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	23:00:00	22:30:00	2750	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-06	19:30:00	19:00:00	2751	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-14	15:30:00	15:00:00	2753	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	18:30:00	18:00:00	2755	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	18:30:00	18:00:00	2756	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	14:30:00	14:00:00	2757	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	14:00:00	13:30:00	2758	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	11:00:00	10:30:00	2760	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	14:00:00	13:30:00	2761	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	11:00:00	10:30:00	2762	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	17:00:00	16:30:00	2764	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	10:00:00	09:30:00	2767	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-06	11:30:00	11:00:00	2768	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-06	23:30:00	23:00:00	2769	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	11:30:00	11:00:00	2771	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-06	10:00:00	09:30:00	2772	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	17:00:00	16:30:00	2776	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	11:00:00	10:30:00	2778	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	22:30:00	22:00:00	2779	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	22:30:00	22:00:00	2780	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	14:30:00	14:00:00	2781	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	14:30:00	14:00:00	2782	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-07	18:00:00	17:30:00	2785	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	13:30:00	13:00:00	2792	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-07	16:30:00	16:00:00	2795	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-08	16:30:00	16:00:00	2797	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	10:00:00	09:30:00	2799	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	20:30:00	20:00:00	2800	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-05	16:30:00	16:00:00	2801	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	19:30:00	19:00:00	2804	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-30	09:30:00	09:00:00	2805	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	09:30:00	09:00:00	2806	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	09:30:00	09:00:00	2809	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	20:00:00	19:30:00	2811	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	13:30:00	13:00:00	2813	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	16:30:00	16:00:00	2814	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-27	20:00:00	19:30:00	2815	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	23:30:00	23:00:00	2817	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-01	12:00:00	11:30:00	2818	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	22:00:00	21:30:00	2819	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	12:00:00	11:30:00	2820	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-02	10:30:00	10:00:00	2823	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-11	17:00:00	16:30:00	2824	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-07-06	22:00:00	21:30:00	2825	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-29	23:30:00	23:00:00	2827	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-26	12:30:00	12:00:00	2829	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	21:30:00	21:00:00	2833	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	13:00:00	12:30:00	2836	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	17:30:00	17:00:00	2839	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	13:00:00	12:30:00	2840	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	13:00:00	12:30:00	2841	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	20:00:00	19:30:00	2842	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	20:30:00	20:00:00	2845	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	17:30:00	17:00:00	2846	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	17:30:00	17:00:00	2847	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	23:00:00	22:30:00	2855	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	15:30:00	15:00:00	2858	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	22:30:00	22:00:00	2860	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	13:00:00	12:30:00	2862	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	13:00:00	12:30:00	2864	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	15:30:00	15:00:00	2865	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	13:00:00	12:30:00	2866	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	15:00:00	14:30:00	2868	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	10:30:00	10:00:00	2869	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	10:30:00	10:00:00	2871	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	14:00:00	13:30:00	2872	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-13	10:00:00	09:30:00	2873	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	14:00:00	13:30:00	2878	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	22:30:00	22:00:00	2881	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	10:30:00	10:00:00	2882	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-05	16:30:00	16:00:00	2886	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	15:00:00	14:30:00	2887	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	20:00:00	19:30:00	2889	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	20:00:00	19:30:00	2890	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	16:30:00	16:00:00	2892	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	10:00:00	09:30:00	2893	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	10:00:00	09:30:00	2895	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	10:30:00	10:00:00	2897	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	10:00:00	09:30:00	2898	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	09:30:00	09:00:00	2901	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	09:30:00	09:00:00	2902	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	09:30:00	09:00:00	2905	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	12:00:00	11:30:00	2910	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	13:30:00	13:00:00	2912	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	08:30:00	08:00:00	2913	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	08:30:00	08:00:00	2915	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-14	16:00:00	15:30:00	2917	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	08:30:00	08:00:00	2918	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	08:30:00	08:00:00	2920	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	08:30:00	08:00:00	2923	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	12:00:00	11:30:00	2926	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-14	13:30:00	13:00:00	2927	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	12:00:00	11:30:00	2928	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	09:30:00	09:00:00	2933	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	18:30:00	18:00:00	2934	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	16:00:00	15:30:00	2935	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-15	08:30:00	08:00:00	2936	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	18:30:00	18:00:00	2938	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	16:00:00	15:30:00	2941	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	14:00:00	13:30:00	2942	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	16:00:00	15:30:00	2943	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	11:00:00	10:30:00	2944	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	16:00:00	15:30:00	2945	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	08:30:00	08:00:00	2946	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-15	18:30:00	18:00:00	2947	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	08:30:00	08:00:00	2948	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-05	08:30:00	08:00:00	2949	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	08:30:00	08:00:00	2952	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	11:30:00	11:00:00	2953	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-05	19:00:00	18:30:00	2954	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	19:00:00	18:30:00	2955	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	08:30:00	08:00:00	2957	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-13	11:00:00	10:30:00	2958	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	11:30:00	11:00:00	2959	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-15	11:00:00	10:30:00	2960	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	18:30:00	18:00:00	2964	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	11:00:00	10:30:00	2966	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	14:00:00	13:30:00	2967	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	15:30:00	15:00:00	2971	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	19:30:00	19:00:00	2974	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	18:30:00	18:00:00	2983	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	14:30:00	14:00:00	2984	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	14:30:00	14:00:00	2985	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	18:30:00	18:00:00	2986	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	18:30:00	18:00:00	2987	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	14:30:00	14:00:00	2988	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	18:30:00	18:00:00	2989	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	18:30:00	18:00:00	2993	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	17:00:00	16:30:00	2994	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	23:30:00	23:00:00	2995	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	17:00:00	16:30:00	2996	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-23	11:00:00	10:30:00	3001	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	17:00:00	16:30:00	3003	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	11:00:00	10:30:00	3004	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	19:30:00	19:00:00	3009	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	14:30:00	14:00:00	3010	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	13:30:00	13:00:00	3011	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	13:30:00	13:00:00	3014	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	18:00:00	17:30:00	3015	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-05	14:30:00	14:00:00	3016	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	18:00:00	17:30:00	3017	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	14:30:00	14:00:00	3019	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-05	18:00:00	17:30:00	3020	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-13	14:30:00	14:00:00	3026	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	12:00:00	11:30:00	3029	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-14	18:00:00	17:30:00	3031	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-25	19:30:00	19:00:00	3032	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-26	19:30:00	19:00:00	3033	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-24	09:30:00	09:00:00	3034	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-02	20:30:00	20:00:00	3036	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	09:30:00	09:00:00	3041	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-06-28	19:30:00	19:00:00	3042	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-14	20:30:00	20:00:00	3043	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	20:30:00	20:00:00	3045	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-04	00:30:00	00:00:00	3046	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-09	20:30:00	20:00:00	3047	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-08	17:00:00	16:30:00	3048	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-03	17:00:00	16:30:00	3051	01977e79-f8b4-7c00-8caa-745ef40934a6
2025-07-14	09:30:00	09:00:00	3064	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	12:30:00	12:00:00	3065	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-11	09:30:00	09:00:00	3066	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-07	17:30:00	17:00:00	3067	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	17:30:00	17:00:00	3068	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-08	13:30:00	13:00:00	3069	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	17:30:00	17:00:00	3072	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	08:30:00	08:00:00	3073	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	21:30:00	21:00:00	3076	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-13	00:00:00	23:30:00	3077	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	08:30:00	08:00:00	3079	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-14	16:00:00	15:30:00	3080	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	08:30:00	08:00:00	3081	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-12	17:30:00	17:00:00	3086	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	12:00:00	11:30:00	3089	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-12	13:30:00	13:00:00	3090	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	18:00:00	17:30:00	3092	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	20:00:00	19:30:00	3098	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	19:00:00	18:30:00	3099	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	20:00:00	19:30:00	3102	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-14	20:00:00	19:30:00	3105	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-12	20:00:00	19:30:00	3106	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	20:30:00	20:00:00	3107	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	16:00:00	15:30:00	3108	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	17:30:00	17:00:00	3109	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-07	18:30:00	18:00:00	3111	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-13	08:30:00	08:00:00	3113	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-14	08:30:00	08:00:00	3114	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-11	08:30:00	08:00:00	3116	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	08:30:00	08:00:00	3120	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	16:00:00	15:30:00	3121	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-11	18:30:00	18:00:00	3124	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	08:30:00	08:00:00	3126	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-13	18:30:00	18:00:00	3127	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	15:30:00	15:00:00	3134	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-15	12:30:00	12:00:00	3135	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-13	22:30:00	22:00:00	3140	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	19:00:00	18:30:00	3141	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	22:30:00	22:00:00	3142	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-12	19:00:00	18:30:00	3143	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	11:30:00	11:00:00	3145	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-14	11:00:00	10:30:00	3148	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	11:00:00	10:30:00	3150	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	13:00:00	12:30:00	3153	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	14:00:00	13:30:00	3154	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	13:00:00	12:30:00	3155	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	11:30:00	11:00:00	3158	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	15:00:00	14:30:00	3159	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	15:30:00	15:00:00	3160	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-08	19:30:00	19:00:00	3161	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-07	15:30:00	15:00:00	3162	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-13	15:30:00	15:00:00	3163	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-11	19:30:00	19:00:00	3166	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	15:00:00	14:30:00	3167	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	18:30:00	18:00:00	3168	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	10:30:00	10:00:00	3171	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-08	11:30:00	11:00:00	3173	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	14:00:00	13:30:00	3174	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	17:00:00	16:30:00	3175	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	11:30:00	11:00:00	3176	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-15	10:00:00	09:30:00	3177	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-13	10:00:00	09:30:00	3179	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	10:00:00	09:30:00	3185	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-08	10:00:00	09:30:00	3186	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	14:00:00	13:30:00	3190	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	11:00:00	10:30:00	3191	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	14:00:00	13:30:00	3192	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	10:00:00	09:30:00	3193	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	17:00:00	16:30:00	3195	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-08	18:00:00	17:30:00	3199	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-12	12:00:00	11:30:00	3200	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-15	18:00:00	17:30:00	3204	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-14	21:30:00	21:00:00	3206	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	09:30:00	09:00:00	3209	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	16:30:00	16:00:00	3210	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-11	15:00:00	14:30:00	3211	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-04	16:30:00	16:00:00	3213	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-30	09:30:00	09:00:00	3215	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-09	15:00:00	14:30:00	3216	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-25	20:00:00	19:30:00	3217	01977e79-f90c-7eff-9b23-192773bf98bb
2025-07-15	17:00:00	16:30:00	3225	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-23	10:00:00	09:30:00	3227	01977e79-f90c-7eff-9b23-192773bf98bb
2025-06-24	16:30:00	16:00:00	3234	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	16:30:00	16:00:00	3236	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	12:30:00	12:00:00	3238	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	12:30:00	12:00:00	3242	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	16:30:00	16:00:00	3243	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	12:30:00	12:00:00	3244	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-07	16:00:00	15:30:00	3245	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	18:00:00	17:30:00	3246	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	18:00:00	17:30:00	3247	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	08:30:00	08:00:00	3248	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-05	17:30:00	17:00:00	3250	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	08:30:00	08:00:00	3252	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	08:30:00	08:00:00	3255	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	08:30:00	08:00:00	3258	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	21:30:00	21:00:00	3260	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	12:00:00	11:30:00	3262	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	18:00:00	17:30:00	3264	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-15	13:00:00	12:30:00	3266	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	21:00:00	20:30:00	3268	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	19:00:00	18:30:00	3269	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	19:00:00	18:30:00	3273	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	20:30:00	20:00:00	3274	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	19:00:00	18:30:00	3275	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-05	18:30:00	18:00:00	3278	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-15	18:30:00	18:00:00	3279	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-08	08:30:00	08:00:00	3281	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	16:00:00	15:30:00	3282	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	17:30:00	17:00:00	3284	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	15:30:00	15:00:00	3289	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-05	12:30:00	12:00:00	3290	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-08	23:00:00	22:30:00	3292	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-08	19:00:00	18:30:00	3293	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	11:30:00	11:00:00	3295	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-15	11:00:00	10:30:00	3296	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-15	21:00:00	20:30:00	3301	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-07	11:00:00	10:30:00	3302	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-08	11:00:00	10:30:00	3303	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-07	14:00:00	13:30:00	3304	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	23:00:00	22:30:00	3306	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-05	15:30:00	15:00:00	3307	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	15:00:00	14:30:00	3308	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	15:00:00	14:30:00	3309	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-07	19:30:00	19:00:00	3310	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	15:00:00	14:30:00	3314	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	10:30:00	10:00:00	3315	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	14:30:00	14:00:00	3316	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	11:00:00	10:30:00	3323	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	14:00:00	13:30:00	3324	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	11:00:00	10:30:00	3325	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	14:00:00	13:30:00	3326	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	22:00:00	21:30:00	3330	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-07	10:00:00	09:30:00	3331	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	22:30:00	22:00:00	3333	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	13:30:00	13:00:00	3338	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	13:30:00	13:00:00	3341	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-15	14:30:00	14:00:00	3345	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	19:30:00	19:00:00	3346	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-24	09:30:00	09:00:00	3348	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-05	16:30:00	16:00:00	3351	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-08	15:00:00	14:30:00	3352	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-07	15:00:00	14:30:00	3353	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	20:00:00	19:30:00	3356	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-08	20:30:00	20:00:00	3357	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	20:00:00	19:30:00	3358	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	10:00:00	09:30:00	3360	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-28	10:00:00	09:30:00	3361	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-15	17:00:00	16:30:00	3362	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-25	10:00:00	09:30:00	3363	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-29	23:30:00	23:00:00	3365	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-06-26	12:30:00	12:00:00	3369	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	16:30:00	16:00:00	3370	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	16:30:00	16:00:00	3371	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	12:30:00	12:00:00	3372	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	21:00:00	20:30:00	3374	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	09:30:00	09:00:00	3375	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	12:30:00	12:00:00	3376	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	16:30:00	16:00:00	3377	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	16:30:00	16:00:00	3378	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	09:30:00	09:00:00	3379	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	12:30:00	12:00:00	3381	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	18:00:00	17:30:00	3383	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	21:30:00	21:00:00	3385	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	18:00:00	17:30:00	3386	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	17:30:00	17:00:00	3388	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-08	13:30:00	13:00:00	3389	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	16:00:00	15:30:00	3390	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	08:30:00	08:00:00	3391	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	16:00:00	15:30:00	3392	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	13:30:00	13:00:00	3393	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	08:30:00	08:00:00	3395	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	08:30:00	08:00:00	3396	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	08:30:00	08:00:00	3397	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	19:00:00	18:30:00	3398	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	17:30:00	17:00:00	3401	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	00:00:00	23:30:00	3402	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	21:30:00	21:00:00	3403	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-12	17:30:00	17:00:00	3404	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	18:00:00	17:30:00	3405	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	18:00:00	17:30:00	3406	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	17:30:00	17:00:00	3408	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	21:00:00	20:30:00	3409	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	13:00:00	12:30:00	3411	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	21:00:00	20:30:00	3412	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	00:00:00	23:30:00	3414	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	00:00:00	23:30:00	3415	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	13:00:00	12:30:00	3416	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	21:00:00	20:30:00	3417	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	19:00:00	18:30:00	3418	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-07	20:00:00	19:30:00	3419	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	19:00:00	18:30:00	3420	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-08	20:00:00	19:30:00	3421	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	09:30:00	09:00:00	3422	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	18:00:00	17:30:00	3424	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	19:00:00	18:30:00	3425	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-12	20:00:00	19:30:00	3427	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	19:00:00	18:30:00	3428	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	17:30:00	17:00:00	3429	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	08:30:00	08:00:00	3430	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	08:30:00	08:00:00	3431	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	11:00:00	10:30:00	3432	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	16:00:00	15:30:00	3434	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	08:30:00	08:00:00	3435	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-08	08:30:00	08:00:00	3437	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	18:30:00	18:00:00	3438	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	18:30:00	18:00:00	3439	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	08:30:00	08:00:00	3440	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-08	12:30:00	12:00:00	3441	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	15:30:00	15:00:00	3442	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	23:00:00	22:30:00	3444	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	15:30:00	15:00:00	3445	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-12	22:30:00	22:00:00	3449	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	16:00:00	15:30:00	3454	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-08	22:30:00	22:00:00	3456	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-12	19:00:00	18:30:00	3457	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	08:30:00	08:00:00	3459	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	14:00:00	13:30:00	3461	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	11:00:00	10:30:00	3465	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-11	11:00:00	10:30:00	3467	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	23:00:00	22:30:00	3469	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	15:00:00	14:30:00	3470	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	19:30:00	19:00:00	3471	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	15:30:00	15:00:00	3472	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	17:00:00	16:30:00	3473	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	15:00:00	14:30:00	3474	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	19:30:00	19:00:00	3475	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	15:00:00	14:30:00	3476	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	14:30:00	14:00:00	3477	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	14:00:00	13:30:00	3479	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	11:00:00	10:30:00	3480	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	17:00:00	16:30:00	3481	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	11:00:00	10:30:00	3483	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	11:30:00	11:00:00	3484	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	14:00:00	13:30:00	3485	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-12	10:00:00	09:30:00	3486	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	10:00:00	09:30:00	3487	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-08	10:00:00	09:30:00	3488	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	11:00:00	10:30:00	3489	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	11:00:00	10:30:00	3490	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	22:30:00	22:00:00	3493	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	14:30:00	14:00:00	3495	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	22:30:00	22:00:00	3496	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	22:30:00	22:00:00	3497	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	13:30:00	13:00:00	3498	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	13:30:00	13:00:00	3502	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	21:30:00	21:00:00	3504	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	18:00:00	17:30:00	3506	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	20:00:00	19:30:00	3507	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-12	21:30:00	21:00:00	3508	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-07	12:00:00	11:30:00	3509	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	10:30:00	10:00:00	3511	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	09:30:00	09:00:00	3512	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	19:30:00	19:00:00	3513	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-25	09:30:00	09:00:00	3515	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	16:30:00	16:00:00	3517	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	15:00:00	14:30:00	3518	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	09:30:00	09:00:00	3519	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-26	20:00:00	19:30:00	3525	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-24	20:00:00	19:30:00	3526	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	13:30:00	13:00:00	3527	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	20:00:00	19:30:00	3528	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-09	20:30:00	20:00:00	3529	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	20:00:00	19:30:00	3530	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-14	22:00:00	21:30:00	3531	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-28	10:00:00	09:30:00	3532	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-02	12:00:00	11:30:00	3533	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-07	22:00:00	21:30:00	3536	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-07-04	22:00:00	21:30:00	3537	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-29	23:30:00	23:00:00	3538	01977e79-f9d8-7a2d-ae01-5476b8a5ac95
2025-06-23	12:30:00	12:00:00	3540	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	12:30:00	12:00:00	3541	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	21:00:00	20:30:00	3542	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	12:30:00	12:00:00	3545	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	18:00:00	17:30:00	3547	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	21:30:00	21:00:00	3549	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	13:00:00	12:30:00	3550	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	21:30:00	21:00:00	3552	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-14	17:30:00	17:00:00	3557	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	17:30:00	17:00:00	3560	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	21:00:00	20:30:00	3561	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	20:00:00	19:30:00	3563	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	20:30:00	20:00:00	3566	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	17:30:00	17:00:00	3567	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	17:30:00	17:00:00	3569	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-11	23:00:00	22:30:00	3572	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	12:30:00	12:00:00	3573	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	15:30:00	15:00:00	3575	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	12:30:00	12:00:00	3576	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	15:30:00	15:00:00	3577	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-04	23:00:00	22:30:00	3578	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	23:00:00	22:30:00	3580	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	15:30:00	15:00:00	3582	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	13:00:00	12:30:00	3585	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	15:00:00	14:30:00	3589	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	10:30:00	10:00:00	3590	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	14:00:00	13:30:00	3594	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	11:30:00	11:00:00	3595	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	11:30:00	11:00:00	3597	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-12	11:30:00	11:00:00	3599	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	14:00:00	13:30:00	3601	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	14:00:00	13:30:00	3603	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	22:30:00	22:00:00	3605	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	20:00:00	19:30:00	3607	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-07	16:30:00	16:00:00	3608	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	15:00:00	14:30:00	3611	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-14	16:30:00	16:00:00	3613	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	20:00:00	19:30:00	3617	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	20:00:00	19:30:00	3618	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	10:00:00	09:30:00	3619	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	10:30:00	10:00:00	3622	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	10:00:00	09:30:00	3623	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	09:30:00	09:00:00	3626	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	16:30:00	16:00:00	3630	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	16:30:00	16:00:00	3633	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	12:00:00	11:30:00	3636	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	13:30:00	13:00:00	3637	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-11	16:00:00	15:30:00	3638	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	16:00:00	15:30:00	3639	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	08:30:00	08:00:00	3640	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	08:30:00	08:00:00	3641	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	08:30:00	08:00:00	3644	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	08:30:00	08:00:00	3645	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	19:00:00	18:30:00	3656	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	19:00:00	18:30:00	3657	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	09:30:00	09:00:00	3659	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	19:00:00	18:30:00	3660	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-12	08:30:00	08:00:00	3668	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-10	08:30:00	08:00:00	3670	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-07	08:30:00	08:00:00	3671	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	16:00:00	15:30:00	3672	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	08:30:00	08:00:00	3673	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-18	16:00:00	15:30:00	3676	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-17	16:00:00	15:30:00	3677	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	16:00:00	15:30:00	3681	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-12	19:00:00	18:30:00	3682	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	11:30:00	11:00:00	3683	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	08:30:00	08:00:00	3684	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	11:30:00	11:00:00	3687	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	14:00:00	13:30:00	3688	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	18:30:00	18:00:00	3690	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	23:00:00	22:30:00	3694	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-04	19:30:00	19:00:00	3698	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-07	15:30:00	15:00:00	3699	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-17	14:30:00	14:00:00	3700	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-14	15:30:00	15:00:00	3701	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-21	14:30:00	14:00:00	3703	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-22	14:30:00	14:00:00	3704	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-18	23:00:00	22:30:00	3705	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	18:30:00	18:00:00	3706	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	14:30:00	14:00:00	3707	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	11:00:00	10:30:00	3708	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	17:00:00	16:30:00	3709	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	22:00:00	21:30:00	3710	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-22	17:00:00	16:30:00	3711	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	22:00:00	21:30:00	3712	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	17:00:00	16:30:00	3714	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-27	11:00:00	10:30:00	3715	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-17	22:00:00	21:30:00	3716	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-16	22:00:00	21:30:00	3717	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-16	11:00:00	10:30:00	3718	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-17	11:00:00	10:30:00	3719	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-18	11:00:00	10:30:00	3720	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	14:30:00	14:00:00	3721	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	22:00:00	21:30:00	3722	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	18:00:00	17:30:00	3725	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-16	19:30:00	19:00:00	3728	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-11	14:30:00	14:00:00	3729	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-26	09:30:00	09:00:00	3730	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-22	09:30:00	09:00:00	3731	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-22	19:30:00	19:00:00	3732	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-17	13:30:00	13:00:00	3733	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-18	13:30:00	13:00:00	3734	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	09:30:00	09:00:00	3735	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-28	19:30:00	19:00:00	3736	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-11	20:30:00	20:00:00	3739	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-16	23:30:00	23:00:00	3740	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-23	23:30:00	23:00:00	3742	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	17:00:00	16:30:00	3745	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-01	12:00:00	11:30:00	3746	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-07-05	22:00:00	21:30:00	3748	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-12	13:00:00	12:30:00	3750	01977e79-fa48-76ee-b5ac-b933c85acc84
2025-06-24	12:30:00	12:00:00	3751	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	12:30:00	12:00:00	3752	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	21:30:00	21:00:00	3753	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	21:30:00	21:00:00	3754	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	12:30:00	12:00:00	3755	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-08	17:30:00	17:00:00	3757	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	18:00:00	17:30:00	3758	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	21:30:00	21:00:00	3759	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	18:00:00	17:30:00	3760	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	21:30:00	21:00:00	3761	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	13:00:00	12:30:00	3762	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	18:00:00	17:30:00	3763	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	18:00:00	17:30:00	3764	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	17:30:00	17:00:00	3765	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	21:30:00	21:00:00	3766	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	18:00:00	17:30:00	3767	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	18:00:00	17:30:00	3769	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-14	13:00:00	12:30:00	3771	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	00:00:00	23:30:00	3772	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	00:00:00	23:30:00	3773	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	21:00:00	20:30:00	3774	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-07	20:00:00	19:30:00	3776	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	20:30:00	20:00:00	3778	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	20:30:00	20:00:00	3779	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	20:30:00	20:00:00	3780	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	23:00:00	22:30:00	3782	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	15:30:00	15:00:00	3784	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	15:30:00	15:00:00	3786	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	15:30:00	15:00:00	3787	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	15:30:00	15:00:00	3788	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	17:30:00	17:00:00	3790	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	12:30:00	12:00:00	3792	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-12	22:30:00	22:00:00	3793	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	13:00:00	12:30:00	3794	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	13:00:00	12:30:00	3796	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	13:00:00	12:30:00	3797	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	13:00:00	12:30:00	3798	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-14	21:00:00	20:30:00	3799	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-12	21:00:00	20:30:00	3800	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	15:00:00	14:30:00	3801	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	15:00:00	14:30:00	3802	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	15:00:00	14:30:00	3803	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	10:30:00	10:00:00	3804	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	10:30:00	10:00:00	3805	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	14:00:00	13:30:00	3806	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-03	11:30:00	11:00:00	3809	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	10:00:00	09:30:00	3811	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	14:00:00	13:30:00	3812	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	14:00:00	13:30:00	3813	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	14:00:00	13:30:00	3815	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-14	11:30:00	11:00:00	3816	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	22:30:00	22:00:00	3817	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	22:30:00	22:00:00	3818	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	22:30:00	22:00:00	3819	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-10	10:30:00	10:00:00	3820	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-13	10:30:00	10:00:00	3822	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-07	16:30:00	16:00:00	3823	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-14	15:00:00	14:30:00	3824	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	10:00:00	09:30:00	3825	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-10	16:30:00	16:00:00	3827	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	16:30:00	16:00:00	3829	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-13	16:30:00	16:00:00	3830	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	20:00:00	19:30:00	3833	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	20:00:00	19:30:00	3834	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	10:00:00	09:30:00	3835	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	10:00:00	09:30:00	3836	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	20:00:00	19:30:00	3837	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	10:00:00	09:30:00	3838	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	10:00:00	09:30:00	3839	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	08:30:00	08:00:00	3841	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	16:30:00	16:00:00	3842	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-06	09:30:00	09:00:00	3844	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	16:30:00	16:00:00	3845	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	16:30:00	16:00:00	3846	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	12:00:00	11:30:00	3847	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-13	09:30:00	09:00:00	3849	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	16:30:00	16:00:00	3850	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-10	13:30:00	13:00:00	3851	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	12:00:00	11:30:00	3852	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	08:30:00	08:00:00	3854	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	08:30:00	08:00:00	3855	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-08	16:00:00	15:30:00	3857	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	16:00:00	15:30:00	3858	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	12:00:00	11:30:00	3859	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	08:30:00	08:00:00	3860	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	13:30:00	13:00:00	3861	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	12:00:00	11:30:00	3862	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-12	16:00:00	15:30:00	3863	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	08:30:00	08:00:00	3864	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	08:30:00	08:00:00	3865	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	08:30:00	08:00:00	3866	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	12:00:00	11:30:00	3868	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	08:30:00	08:00:00	3869	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	19:00:00	18:30:00	3871	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	19:00:00	18:30:00	3872	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	16:00:00	15:30:00	3874	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	08:30:00	08:00:00	3879	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	11:00:00	10:30:00	3880	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-08	08:30:00	08:00:00	3885	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	16:00:00	15:30:00	3888	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	16:00:00	15:30:00	3889	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	16:00:00	15:30:00	3894	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	08:30:00	08:00:00	3898	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	11:30:00	11:00:00	3899	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	14:00:00	13:30:00	3900	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	11:00:00	10:30:00	3901	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	11:30:00	11:00:00	3902	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	11:30:00	11:00:00	3903	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	18:30:00	18:00:00	3904	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-07	14:00:00	13:30:00	3909	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	11:00:00	10:30:00	3910	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	11:30:00	11:00:00	3911	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-10	15:30:00	15:00:00	3913	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-03	19:30:00	19:00:00	3915	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	14:30:00	14:00:00	3917	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	19:30:00	19:00:00	3919	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	23:00:00	22:30:00	3920	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	17:00:00	16:30:00	3921	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	18:30:00	18:00:00	3922	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-12	19:30:00	19:00:00	3925	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	18:30:00	18:00:00	3926	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	14:30:00	14:00:00	3927	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	18:30:00	18:00:00	3928	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	14:30:00	14:00:00	3929	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	14:30:00	14:00:00	3930	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	11:00:00	10:30:00	3931	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	17:00:00	16:30:00	3932	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	17:00:00	16:30:00	3933	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	17:00:00	16:30:00	3934	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	11:00:00	10:30:00	3935	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	22:00:00	21:30:00	3936	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	22:00:00	21:30:00	3937	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	17:00:00	16:30:00	3938	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	11:00:00	10:30:00	3940	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	14:30:00	14:00:00	3942	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	19:30:00	19:00:00	3943	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-10	14:30:00	14:00:00	3944	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-13	12:00:00	11:30:00	3945	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	13:30:00	13:00:00	3947	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-19	13:30:00	13:00:00	3949	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	09:30:00	09:00:00	3951	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	14:30:00	14:00:00	3952	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-06	12:00:00	11:30:00	3953	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	18:00:00	17:30:00	3954	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-30	13:30:00	13:00:00	3955	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-12	18:00:00	17:30:00	3956	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-14	18:00:00	17:30:00	3957	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-10	12:00:00	11:30:00	3959	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	19:30:00	19:00:00	3960	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-25	09:30:00	09:00:00	3961	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	09:30:00	09:00:00	3962	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	19:30:00	19:00:00	3963	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	20:30:00	20:00:00	3964	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-21	09:30:00	09:00:00	3965	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-22	19:30:00	19:00:00	3966	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-20	19:30:00	19:00:00	3967	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-17	13:30:00	13:00:00	3969	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-29	19:30:00	19:00:00	3970	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	20:30:00	20:00:00	3971	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-03	20:30:00	20:00:00	3973	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-15	22:00:00	21:30:00	3974	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-11	22:00:00	21:30:00	3977	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-02	12:00:00	11:30:00	3978	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-06	17:00:00	16:30:00	3979	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-12	17:00:00	16:30:00	3980	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-07-03	22:00:00	21:30:00	3981	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-15	15:50:00	15:20:00	3982	01977e79-faa9-7b86-a332-e10ef4ed001b
2025-06-24	12:30:00	12:00:00	3984	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	12:30:00	12:00:00	3985	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	16:30:00	16:00:00	3986	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	16:30:00	16:00:00	3987	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	08:30:00	08:00:00	3988	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	12:30:00	12:00:00	3989	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	16:30:00	16:00:00	3990	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	09:30:00	09:00:00	3991	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	16:30:00	16:00:00	3993	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	12:30:00	12:00:00	3994	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-10	13:30:00	13:00:00	3995	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	16:00:00	15:30:00	3997	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	18:00:00	17:30:00	3998	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-11	16:00:00	15:30:00	3999	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	21:30:00	21:00:00	4001	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	17:30:00	17:00:00	4003	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-03	13:30:00	13:00:00	4004	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	08:30:00	08:00:00	4005	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	18:00:00	17:30:00	4006	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	12:30:00	12:00:00	4007	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	08:30:00	08:00:00	4008	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	19:00:00	18:30:00	4009	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	08:30:00	08:00:00	4010	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	08:30:00	08:00:00	4011	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	18:00:00	17:30:00	4014	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	08:30:00	08:00:00	4015	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-11	13:30:00	13:00:00	4016	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	21:00:00	20:30:00	4017	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	21:00:00	20:30:00	4018	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	21:00:00	20:30:00	4019	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	17:30:00	17:00:00	4020	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	13:00:00	12:30:00	4021	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	12:30:00	12:00:00	4022	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	00:00:00	23:30:00	4023	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	19:00:00	18:30:00	4024	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-10	20:00:00	19:30:00	4025	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	20:30:00	20:00:00	4026	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	19:00:00	18:30:00	4027	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	20:30:00	20:00:00	4028	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	21:30:00	21:00:00	4029	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-03	21:30:00	21:00:00	4030	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-11	20:00:00	19:30:00	4031	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	19:00:00	18:30:00	4032	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	16:00:00	15:30:00	4033	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	17:30:00	17:00:00	4034	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	17:30:00	17:00:00	4035	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-10	18:30:00	18:00:00	4036	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	14:00:00	13:30:00	4037	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	17:30:00	17:00:00	4040	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-06	18:30:00	18:00:00	4041	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-11	08:30:00	08:00:00	4042	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	18:30:00	18:00:00	4043	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-10	08:30:00	08:00:00	4044	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	08:30:00	08:00:00	4049	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-10	12:30:00	12:00:00	4050	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	15:30:00	15:00:00	4052	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	12:30:00	12:00:00	4053	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	15:30:00	15:00:00	4054	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	11:30:00	11:00:00	4055	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-11	12:30:00	12:00:00	4056	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	16:00:00	15:30:00	4058	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	23:00:00	22:30:00	4059	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	11:30:00	11:00:00	4060	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-03	16:00:00	15:30:00	4061	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	08:30:00	08:00:00	4063	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	11:30:00	11:00:00	4068	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	14:00:00	13:30:00	4069	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	13:00:00	12:30:00	4070	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	15:30:00	15:00:00	4073	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	19:30:00	19:00:00	4074	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	18:30:00	18:00:00	4078	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	15:00:00	14:30:00	4079	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	15:00:00	14:30:00	4080	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	23:00:00	22:30:00	4081	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	15:00:00	14:30:00	4082	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	18:30:00	18:00:00	4083	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	18:30:00	18:00:00	4084	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	14:30:00	14:00:00	4085	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	15:00:00	14:30:00	4086	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	10:30:00	10:00:00	4087	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	14:30:00	14:00:00	4088	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	11:00:00	10:30:00	4089	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-10	11:30:00	11:00:00	4090	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	11:30:00	11:00:00	4093	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	14:00:00	13:30:00	4096	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	14:00:00	13:30:00	4097	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	22:00:00	21:30:00	4098	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	11:00:00	10:30:00	4099	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	22:00:00	21:30:00	4100	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-06	10:00:00	09:30:00	4101	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-11	11:30:00	11:00:00	4102	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	11:00:00	10:30:00	4103	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	17:00:00	16:30:00	4105	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	11:00:00	10:30:00	4106	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-03	10:00:00	09:30:00	4107	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	14:00:00	13:30:00	4108	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	17:00:00	16:30:00	4109	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	22:30:00	22:00:00	4110	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	13:30:00	13:00:00	4111	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	13:30:00	13:00:00	4112	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	21:30:00	21:00:00	4114	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	10:30:00	10:00:00	4115	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	20:00:00	19:30:00	4117	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	12:00:00	11:30:00	4118	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	13:30:00	13:00:00	4122	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	10:00:00	09:30:00	4125	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	19:30:00	19:00:00	4126	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-23	09:30:00	09:00:00	4127	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	09:30:00	09:00:00	4128	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-22	19:30:00	19:00:00	4129	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-05	15:00:00	14:30:00	4132	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-04	15:00:00	14:30:00	4133	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	13:30:00	13:00:00	4134	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-03	15:00:00	14:30:00	4135	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	20:00:00	19:30:00	4138	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	10:00:00	09:30:00	4140	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	20:00:00	19:30:00	4141	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-21	20:00:00	19:30:00	4142	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-01	16:30:00	16:00:00	4144	01977e79-fb15-743c-9d5a-b00ac2953735
2025-07-03	17:00:00	16:30:00	4147	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-28	10:00:00	09:30:00	4148	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-25	10:00:00	09:30:00	4150	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-24	10:00:00	09:30:00	4151	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-16	20:00:00	19:30:00	4152	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-18	19:00:00	18:30:00	4155	01977e79-fb15-743c-9d5a-b00ac2953735
2025-06-17	08:30:00	08:00:00	4156	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	12:30:00	12:00:00	4157	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	16:30:00	16:00:00	4158	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	12:30:00	12:00:00	4159	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-19	16:30:00	16:00:00	4160	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	21:30:00	21:00:00	4162	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	16:30:00	16:00:00	4164	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	21:30:00	21:00:00	4167	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	21:30:00	21:00:00	4169	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-19	18:00:00	17:30:00	4170	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	08:30:00	08:00:00	4171	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	08:30:00	08:00:00	4172	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	21:30:00	21:00:00	4173	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	08:30:00	08:00:00	4175	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	08:30:00	08:00:00	4176	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-14	16:00:00	15:30:00	4177	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	16:30:00	16:00:00	4178	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	12:00:00	11:30:00	4179	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	12:30:00	12:00:00	4180	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	21:30:00	21:00:00	4181	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	08:30:00	08:00:00	4182	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-19	21:00:00	20:30:00	4186	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	19:00:00	18:30:00	4188	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	19:00:00	18:30:00	4189	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	20:30:00	20:00:00	4190	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	20:30:00	20:00:00	4191	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	20:30:00	20:00:00	4192	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	20:30:00	20:00:00	4193	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	17:30:00	17:00:00	4195	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	17:30:00	17:00:00	4196	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	16:00:00	15:30:00	4198	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-09	08:30:00	08:00:00	4200	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	17:30:00	17:00:00	4201	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	15:30:00	15:00:00	4202	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-09	12:30:00	12:00:00	4204	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	15:30:00	15:00:00	4206	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	17:30:00	17:00:00	4207	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	13:00:00	12:30:00	4213	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	13:00:00	12:30:00	4215	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	11:30:00	11:00:00	4216	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	13:00:00	12:30:00	4220	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	11:30:00	11:00:00	4221	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-09	14:00:00	13:30:00	4222	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	11:30:00	11:00:00	4224	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	23:00:00	22:30:00	4225	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	15:00:00	14:30:00	4226	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-19	23:00:00	22:30:00	4227	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	18:30:00	18:00:00	4228	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	15:00:00	14:30:00	4230	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	15:00:00	14:30:00	4231	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	15:00:00	14:30:00	4234	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	18:30:00	18:00:00	4235	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	14:30:00	14:00:00	4236	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	18:30:00	18:00:00	4237	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	18:30:00	18:00:00	4238	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	14:00:00	13:30:00	4239	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-09	23:30:00	23:00:00	4240	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	17:00:00	16:30:00	4241	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	14:00:00	13:30:00	4242	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	11:00:00	10:30:00	4245	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	14:00:00	13:30:00	4247	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	11:00:00	10:30:00	4248	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	17:00:00	16:30:00	4249	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-19	22:00:00	21:30:00	4250	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	22:30:00	22:00:00	4251	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	11:00:00	10:30:00	4252	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	22:30:00	22:00:00	4254	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	13:30:00	13:00:00	4255	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	09:30:00	09:00:00	4256	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-14	14:30:00	14:00:00	4259	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	19:30:00	19:00:00	4263	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-19	19:30:00	19:00:00	4264	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	13:30:00	13:00:00	4266	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-09	15:00:00	14:30:00	4268	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	19:30:00	19:00:00	4269	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	20:00:00	19:30:00	4273	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-29	10:00:00	09:30:00	4274	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-27	10:00:00	09:30:00	4275	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-26	10:00:00	09:30:00	4276	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	20:00:00	19:30:00	4277	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-07-09	22:00:00	21:30:00	4278	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-25	10:00:00	09:30:00	4279	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-22	10:00:00	09:30:00	4280	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-12	13:00:00	12:30:00	4283	01977e79-fb74-7d65-8dd8-fb25557c7426
2025-06-17	08:30:00	08:00:00	4284	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	12:30:00	12:00:00	4285	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	16:30:00	16:00:00	4286	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	08:30:00	08:00:00	4288	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	09:30:00	09:00:00	4289	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	12:30:00	12:00:00	4290	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	12:00:00	11:30:00	4293	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	21:00:00	20:30:00	4294	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	21:00:00	20:30:00	4295	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	16:00:00	15:30:00	4297	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	16:00:00	15:30:00	4298	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	13:00:00	12:30:00	4300	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-05	13:30:00	13:00:00	4301	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	00:00:00	23:30:00	4302	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	19:00:00	18:30:00	4306	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	16:30:00	16:00:00	4307	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	16:30:00	16:00:00	4308	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	08:30:00	08:00:00	4309	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	20:30:00	20:00:00	4312	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	21:00:00	20:30:00	4313	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	19:00:00	18:30:00	4314	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	20:00:00	19:30:00	4318	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	19:00:00	18:30:00	4319	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	18:00:00	17:30:00	4320	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	18:00:00	17:30:00	4321	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	17:30:00	17:00:00	4323	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	16:00:00	15:30:00	4324	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-13	08:30:00	08:00:00	4326	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	11:00:00	10:30:00	4327	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	17:30:00	17:00:00	4328	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	11:00:00	10:30:00	4329	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-05	18:30:00	18:00:00	4330	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	08:30:00	08:00:00	4332	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-05	08:30:00	08:00:00	4333	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	08:30:00	08:00:00	4334	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	15:30:00	15:00:00	4337	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-05	12:30:00	12:00:00	4338	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	17:30:00	17:00:00	4339	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	17:30:00	17:00:00	4340	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-13	12:30:00	12:00:00	4341	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-09	19:00:00	18:30:00	4342	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	13:00:00	12:30:00	4343	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	19:00:00	18:30:00	4344	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	13:00:00	12:30:00	4345	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	11:30:00	11:00:00	4346	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	16:00:00	15:30:00	4347	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	08:30:00	08:00:00	4348	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	08:30:00	08:00:00	4349	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-05	21:00:00	20:30:00	4351	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	11:30:00	11:00:00	4352	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-05	11:00:00	10:30:00	4353	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-09	15:30:00	15:00:00	4357	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	17:00:00	16:30:00	4358	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	10:30:00	10:00:00	4359	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	18:30:00	18:00:00	4360	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	18:30:00	18:00:00	4362	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	22:30:00	22:00:00	4363	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	22:30:00	22:00:00	4364	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	15:00:00	14:30:00	4365	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	15:00:00	14:30:00	4367	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	14:30:00	14:00:00	4368	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	11:30:00	11:00:00	4369	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	23:30:00	23:00:00	4370	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	11:00:00	10:30:00	4374	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	14:00:00	13:30:00	4376	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	14:00:00	13:30:00	4377	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	14:00:00	13:30:00	4378	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	14:30:00	14:00:00	4379	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	19:30:00	19:00:00	4380	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	10:30:00	10:00:00	4381	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-09	18:00:00	17:30:00	4383	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	13:30:00	13:00:00	4389	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	20:00:00	19:30:00	4390	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-17	09:30:00	09:00:00	4391	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	12:00:00	11:30:00	4392	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	10:00:00	09:30:00	4395	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	10:00:00	09:30:00	4396	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-13	15:00:00	14:30:00	4397	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	09:30:00	09:00:00	4398	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-24	19:30:00	19:00:00	4399	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-04	15:00:00	14:30:00	4403	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-13	16:30:00	16:00:00	4404	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-16	10:00:00	09:30:00	4405	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	15:00:00	14:30:00	4406	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-20	20:00:00	19:30:00	4407	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	13:30:00	13:00:00	4408	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	16:30:00	16:00:00	4409	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	17:00:00	16:30:00	4411	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-01	12:00:00	11:30:00	4413	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-02	12:00:00	11:30:00	4415	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-06	17:00:00	16:30:00	4416	01977e79-fbdd-7845-982f-a684fb6c9078
2025-07-07	22:00:00	21:30:00	4417	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-09	10:00:00	09:30:00	4418	01977e79-fbdd-7845-982f-a684fb6c9078
2025-06-25	12:30:00	12:00:00	4419	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	21:30:00	21:00:00	4420	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	12:30:00	12:00:00	4421	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	12:30:00	12:00:00	4422	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	12:30:00	12:00:00	4423	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	18:00:00	17:30:00	4425	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	13:00:00	12:30:00	4426	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	13:00:00	12:30:00	4427	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	12:30:00	12:00:00	4428	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	12:30:00	12:00:00	4430	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	18:00:00	17:30:00	4431	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	18:00:00	17:30:00	4432	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	00:00:00	23:30:00	4433	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	13:00:00	12:30:00	4435	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	17:30:00	17:00:00	4436	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	21:00:00	20:30:00	4437	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-10	13:00:00	12:30:00	4438	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	20:30:00	20:00:00	4440	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	20:00:00	19:30:00	4441	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	20:00:00	19:30:00	4442	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	21:30:00	21:00:00	4443	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	21:30:00	21:00:00	4444	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-15	20:00:00	19:30:00	4445	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	18:00:00	17:30:00	4446	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	21:00:00	20:30:00	4447	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	17:30:00	17:00:00	4448	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	17:30:00	17:00:00	4449	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	17:30:00	17:00:00	4450	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	17:30:00	17:00:00	4451	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	15:30:00	15:00:00	4453	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	15:30:00	15:00:00	4455	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	17:30:00	17:00:00	4456	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-15	12:30:00	12:00:00	4457	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	17:30:00	17:00:00	4458	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	21:00:00	20:30:00	4461	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	21:00:00	20:30:00	4462	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	13:00:00	12:30:00	4464	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	13:00:00	12:30:00	4465	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	15:30:00	15:00:00	4466	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	15:00:00	14:30:00	4467	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	15:00:00	14:30:00	4468	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	15:00:00	14:30:00	4469	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	10:30:00	10:00:00	4470	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	10:30:00	10:00:00	4471	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	15:00:00	14:30:00	4472	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	14:00:00	13:30:00	4474	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-10	10:00:00	09:30:00	4478	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	14:00:00	13:30:00	4480	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	10:00:00	09:30:00	4481	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	14:00:00	13:30:00	4483	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	21:30:00	21:00:00	4485	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	20:00:00	19:30:00	4488	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	10:00:00	09:30:00	4490	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	15:00:00	14:30:00	4492	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	10:00:00	09:30:00	4494	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	10:00:00	09:30:00	4495	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	15:00:00	14:30:00	4496	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	16:30:00	16:00:00	4497	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	20:00:00	19:30:00	4498	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	10:00:00	09:30:00	4499	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	10:00:00	09:30:00	4500	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	10:00:00	09:30:00	4501	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	10:00:00	09:30:00	4502	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	20:00:00	19:30:00	4503	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	10:30:00	10:00:00	4504	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	20:00:00	19:30:00	4505	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	08:30:00	08:00:00	4506	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	16:30:00	16:00:00	4507	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	08:30:00	08:00:00	4508	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	09:30:00	09:00:00	4509	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	16:30:00	16:00:00	4512	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	01:00:00	00:30:00	4513	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	16:30:00	16:00:00	4514	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-06	16:00:00	15:30:00	4515	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	12:00:00	11:30:00	4516	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	16:00:00	15:30:00	4517	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	08:30:00	08:00:00	4519	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	08:30:00	08:00:00	4520	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-06	13:30:00	13:00:00	4521	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	08:30:00	08:00:00	4523	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	08:30:00	08:00:00	4524	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	08:30:00	08:00:00	4525	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	12:00:00	11:30:00	4526	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	19:00:00	18:30:00	4528	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	08:30:00	08:00:00	4529	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	16:30:00	16:00:00	4530	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	08:30:00	08:00:00	4532	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	19:00:00	18:30:00	4533	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	09:30:00	09:00:00	4534	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	19:00:00	18:30:00	4535	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	19:00:00	18:30:00	4536	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	14:00:00	13:30:00	4540	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	16:00:00	15:30:00	4541	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	18:30:00	18:00:00	4542	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	16:00:00	15:30:00	4543	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	08:30:00	08:00:00	4545	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	16:00:00	15:30:00	4546	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	08:30:00	08:00:00	4547	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-03	08:30:00	08:00:00	4549	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	08:30:00	08:00:00	4550	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	11:30:00	11:00:00	4551	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	16:00:00	15:30:00	4552	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	19:00:00	18:30:00	4554	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-15	19:00:00	18:30:00	4555	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-03	16:00:00	15:30:00	4556	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	16:00:00	15:30:00	4557	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	08:30:00	08:00:00	4558	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	08:30:00	08:00:00	4559	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	11:30:00	11:00:00	4560	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	11:30:00	11:00:00	4561	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	11:00:00	10:30:00	4562	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	18:30:00	18:00:00	4563	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	11:30:00	11:00:00	4564	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	11:30:00	11:00:00	4565	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	14:00:00	13:30:00	4567	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	11:30:00	11:00:00	4568	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	15:30:00	15:00:00	4569	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	19:30:00	19:00:00	4570	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-03	19:30:00	19:00:00	4571	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	14:30:00	14:00:00	4572	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	18:30:00	18:00:00	4573	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	18:30:00	18:00:00	4574	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	14:30:00	14:00:00	4575	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	18:30:00	18:00:00	4576	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	18:30:00	18:00:00	4577	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	23:00:00	22:30:00	4578	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	14:30:00	14:00:00	4579	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	14:30:00	14:00:00	4580	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	18:30:00	18:00:00	4581	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	23:30:00	23:00:00	4582	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	11:00:00	10:30:00	4583	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	17:00:00	16:30:00	4584	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	22:00:00	21:30:00	4586	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	17:00:00	16:30:00	4587	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	22:00:00	21:30:00	4588	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	11:00:00	10:30:00	4589	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	11:00:00	10:30:00	4590	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	11:00:00	10:30:00	4591	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	14:30:00	14:00:00	4592	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	13:30:00	13:00:00	4593	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	18:00:00	17:30:00	4594	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-05	12:00:00	11:30:00	4601	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	13:30:00	13:00:00	4603	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-30	13:30:00	13:00:00	4604	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-28	13:30:00	13:00:00	4606	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-26	09:30:00	09:00:00	4607	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-25	09:30:00	09:00:00	4608	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	20:30:00	20:00:00	4609	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	09:30:00	09:00:00	4610	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-17	13:30:00	13:00:00	4611	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-16	13:30:00	13:00:00	4612	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-29	19:30:00	19:00:00	4613	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-27	19:30:00	19:00:00	4614	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-10	17:00:00	16:30:00	4616	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-04	17:00:00	16:30:00	4618	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-01	12:00:00	11:30:00	4620	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-07	22:00:00	21:30:00	4622	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-03	22:00:00	21:30:00	4624	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-08	08:50:00	08:20:00	4625	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-24	12:30:00	12:00:00	4626	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	08:30:00	08:00:00	4627	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	12:30:00	12:00:00	4629	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	12:30:00	12:00:00	4630	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	16:30:00	16:00:00	4631	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	17:30:00	17:00:00	4632	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	12:00:00	11:30:00	4633	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	08:30:00	08:00:00	4635	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	08:30:00	08:00:00	4636	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	16:00:00	15:30:00	4637	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	08:30:00	08:00:00	4638	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-15	13:30:00	13:00:00	4640	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	12:30:00	12:00:00	4641	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	08:30:00	08:00:00	4642	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	08:30:00	08:00:00	4643	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	18:00:00	17:30:00	4644	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	18:00:00	17:30:00	4646	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	21:30:00	21:00:00	4647	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	18:00:00	17:30:00	4648	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-19	21:00:00	20:30:00	4649	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	13:00:00	12:30:00	4650	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	20:30:00	20:00:00	4652	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	20:30:00	20:00:00	4653	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	20:30:00	20:00:00	4654	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	19:00:00	18:30:00	4655	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	19:00:00	18:30:00	4656	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	20:30:00	20:00:00	4657	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	16:00:00	15:30:00	4659	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	17:30:00	17:00:00	4661	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	16:00:00	15:30:00	4662	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	08:30:00	08:00:00	4663	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	12:30:00	12:00:00	4665	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	23:00:00	22:30:00	4666	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	15:30:00	15:00:00	4667	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-15	12:30:00	12:00:00	4668	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	15:30:00	15:00:00	4669	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	15:30:00	15:00:00	4670	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	16:00:00	15:30:00	4672	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	19:00:00	18:30:00	4673	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	13:00:00	12:30:00	4676	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	13:00:00	12:30:00	4677	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	11:30:00	11:00:00	4679	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	11:00:00	10:30:00	4683	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	11:30:00	11:00:00	4684	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	11:30:00	11:00:00	4685	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	23:00:00	22:30:00	4686	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	19:30:00	19:00:00	4687	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	15:00:00	14:30:00	4688	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	18:30:00	18:00:00	4689	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	15:00:00	14:30:00	4691	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	18:30:00	18:00:00	4692	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	15:00:00	14:30:00	4693	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-19	18:30:00	18:00:00	4694	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	10:30:00	10:00:00	4695	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	14:00:00	13:30:00	4696	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	17:00:00	16:30:00	4697	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	11:00:00	10:30:00	4698	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	14:00:00	13:30:00	4699	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-15	10:00:00	09:30:00	4700	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	14:00:00	13:30:00	4701	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	22:00:00	21:30:00	4702	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-15	11:30:00	11:00:00	4703	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	14:00:00	13:30:00	4704	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	22:00:00	21:30:00	4706	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	11:00:00	10:30:00	4708	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	17:00:00	16:30:00	4709	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	17:00:00	16:30:00	4710	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	10:00:00	09:30:00	4711	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	14:00:00	13:30:00	4712	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	14:00:00	13:30:00	4713	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	11:00:00	10:30:00	4714	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	17:00:00	16:30:00	4715	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	21:30:00	21:00:00	4716	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	19:30:00	19:00:00	4719	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	19:30:00	19:00:00	4721	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-19	19:30:00	19:00:00	4722	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-29	09:30:00	09:00:00	4726	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	19:30:00	19:00:00	4727	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	09:30:00	09:00:00	4728	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-28	19:30:00	19:00:00	4729	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-18	10:00:00	09:30:00	4730	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	20:00:00	19:30:00	4731	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-30	10:00:00	09:30:00	4733	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-24	10:00:00	09:30:00	4736	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-21	10:00:00	09:30:00	4737	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-17	17:40:00	17:10:00	4739	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-17	08:30:00	08:00:00	4740	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	12:30:00	12:00:00	4742	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-20	16:30:00	16:00:00	4744	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	18:00:00	17:30:00	4747	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	12:30:00	12:00:00	4748	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	21:30:00	21:00:00	4749	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	21:30:00	21:00:00	4750	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-20	21:30:00	21:00:00	4752	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-10	16:00:00	15:30:00	4753	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	08:30:00	08:00:00	4754	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	08:30:00	08:00:00	4755	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	08:30:00	08:00:00	4756	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	12:30:00	12:00:00	4758	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	12:00:00	11:30:00	4759	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	20:30:00	20:00:00	4761	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-03	13:00:00	12:30:00	4762	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-10	13:00:00	12:30:00	4763	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	00:00:00	23:30:00	4764	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	21:00:00	20:30:00	4765	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	19:00:00	18:30:00	4766	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	19:00:00	18:30:00	4767	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-20	19:00:00	18:30:00	4769	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	17:30:00	17:00:00	4770	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	17:30:00	17:00:00	4771	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	16:00:00	15:30:00	4772	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-03	18:30:00	18:00:00	4773	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	16:00:00	15:30:00	4774	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-20	17:30:00	17:00:00	4775	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	16:00:00	15:30:00	4776	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-03	11:00:00	10:30:00	4777	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-12	08:30:00	08:00:00	4778	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-10	08:30:00	08:00:00	4779	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	17:30:00	17:00:00	4780	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	15:30:00	15:00:00	4782	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-04	08:30:00	08:00:00	4784	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-03	16:00:00	15:30:00	4792	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-12	11:00:00	10:30:00	4793	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-12	14:00:00	13:30:00	4794	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-04	11:00:00	10:30:00	4796	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	11:30:00	11:00:00	4801	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	13:00:00	12:30:00	4802	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	23:00:00	22:30:00	4803	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	15:00:00	14:30:00	4804	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	14:30:00	14:00:00	4806	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	15:00:00	14:30:00	4808	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	23:00:00	22:30:00	4809	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	23:00:00	22:30:00	4810	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	14:30:00	14:00:00	4812	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	18:30:00	18:00:00	4814	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-10	11:30:00	11:00:00	4815	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	22:00:00	21:30:00	4817	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	14:00:00	13:30:00	4820	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	11:00:00	10:30:00	4821	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	11:00:00	10:30:00	4823	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	11:00:00	10:30:00	4824	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	17:00:00	16:30:00	4825	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-10	18:00:00	17:30:00	4827	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-04	18:00:00	17:30:00	4828	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	19:30:00	19:00:00	4832	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	13:30:00	13:00:00	4834	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	09:30:00	09:00:00	4835	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	13:30:00	13:00:00	4839	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	19:30:00	19:00:00	4841	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	20:00:00	19:30:00	4842	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-26	20:00:00	19:30:00	4843	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-17	10:00:00	09:30:00	4844	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-20	20:00:00	19:30:00	4846	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-10	20:30:00	20:00:00	4848	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-03	17:00:00	16:30:00	4850	01977e79-fd05-7a4c-9854-1f283f24c417
2025-07-12	22:00:00	21:30:00	4852	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-27	10:00:00	09:30:00	4853	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-25	10:00:00	09:30:00	4854	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-13	14:10:00	13:40:00	4856	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-22	12:30:00	12:00:00	4857	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-04	17:30:00	17:00:00	4858	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	18:00:00	17:30:00	4859	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	21:30:00	21:00:00	4860	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	18:00:00	17:30:00	4861	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	12:30:00	12:00:00	4862	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	12:30:00	12:00:00	4863	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	18:00:00	17:30:00	4864	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	18:00:00	17:30:00	4865	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	21:00:00	20:30:00	4866	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	21:00:00	20:30:00	4868	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	21:00:00	20:30:00	4871	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	20:30:00	20:00:00	4877	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	20:30:00	20:00:00	4878	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-11	20:00:00	19:30:00	4880	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	17:30:00	17:00:00	4881	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	17:30:00	17:00:00	4882	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	15:30:00	15:00:00	4883	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	15:30:00	15:00:00	4885	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	15:30:00	15:00:00	4886	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-09	12:30:00	12:00:00	4887	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	12:30:00	12:00:00	4888	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	15:30:00	15:00:00	4890	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	15:30:00	15:00:00	4891	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-05	23:00:00	22:30:00	4892	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	15:30:00	15:00:00	4893	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-14	12:30:00	12:00:00	4894	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-13	12:30:00	12:00:00	4895	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	22:30:00	22:00:00	4896	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	13:00:00	12:30:00	4899	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	13:00:00	12:30:00	4900	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	13:00:00	12:30:00	4902	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	13:00:00	12:30:00	4904	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	10:30:00	10:00:00	4906	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	15:00:00	14:30:00	4907	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	10:30:00	10:00:00	4908	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	10:30:00	10:00:00	4909	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	10:30:00	10:00:00	4910	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	14:00:00	13:30:00	4911	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	11:30:00	11:00:00	4913	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	14:00:00	13:30:00	4915	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-10	10:00:00	09:30:00	4916	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-09	10:00:00	09:30:00	4917	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-04	10:00:00	09:30:00	4920	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	14:00:00	13:30:00	4921	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	22:30:00	22:00:00	4923	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-09	21:30:00	21:00:00	4924	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	10:30:00	10:00:00	4925	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-14	15:00:00	14:30:00	4928	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	20:00:00	19:30:00	4930	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	16:30:00	16:00:00	4931	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	08:30:00	08:00:00	4932	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	08:30:00	08:00:00	4933	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-05	09:30:00	09:00:00	4935	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	16:30:00	16:00:00	4936	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	09:30:00	09:00:00	4937	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	16:30:00	16:00:00	4938	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	12:00:00	11:30:00	4940	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	12:00:00	11:30:00	4941	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-06	13:30:00	13:00:00	4946	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	08:30:00	08:00:00	4947	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	08:30:00	08:00:00	4948	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-14	16:00:00	15:30:00	4950	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	12:00:00	11:30:00	4951	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	08:30:00	08:00:00	4952	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-13	16:00:00	15:30:00	4953	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	08:30:00	08:00:00	4954	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	12:00:00	11:30:00	4955	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-13	13:30:00	13:00:00	4956	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	08:30:00	08:00:00	4957	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	19:00:00	18:30:00	4959	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	19:00:00	18:30:00	4960	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	19:00:00	18:30:00	4961	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-10	18:30:00	18:00:00	4963	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-04	18:30:00	18:00:00	4965	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-11	08:30:00	08:00:00	4969	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-05	18:30:00	18:00:00	4970	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-09	08:30:00	08:00:00	4971	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-10	08:30:00	08:00:00	4972	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-08	08:30:00	08:00:00	4973	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-11	18:30:00	18:00:00	4976	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	08:30:00	08:00:00	4978	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	16:00:00	15:30:00	4980	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	16:00:00	15:30:00	4981	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	11:30:00	11:00:00	4983	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-13	19:00:00	18:30:00	4984	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-06	11:00:00	10:30:00	4989	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	14:00:00	13:30:00	4990	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-08	11:00:00	10:30:00	4991	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-09	14:00:00	13:30:00	4992	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-09	11:00:00	10:30:00	4993	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-05	15:30:00	15:00:00	4996	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-08	19:30:00	19:00:00	4998	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	15:30:00	15:00:00	4999	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	18:30:00	18:00:00	5003	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	14:30:00	14:00:00	5004	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	23:00:00	22:30:00	5005	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	23:00:00	22:30:00	5006	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	14:30:00	14:00:00	5007	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	18:30:00	18:00:00	5008	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	18:30:00	18:00:00	5010	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	14:30:00	14:00:00	5011	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	14:30:00	14:00:00	5012	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	11:00:00	10:30:00	5013	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	17:00:00	16:30:00	5014	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	11:00:00	10:30:00	5015	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	22:00:00	21:30:00	5016	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	17:00:00	16:30:00	5018	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	22:00:00	21:30:00	5019	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-19	22:00:00	21:30:00	5020	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	11:00:00	10:30:00	5021	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	17:00:00	16:30:00	5023	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	17:00:00	16:30:00	5024	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-11	12:00:00	11:30:00	5025	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-08	14:30:00	14:00:00	5027	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	13:30:00	13:00:00	5030	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	19:30:00	19:00:00	5033	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	09:30:00	09:00:00	5035	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-05	12:00:00	11:30:00	5036	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	09:30:00	09:00:00	5037	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-11	14:30:00	14:00:00	5039	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-13	18:00:00	17:30:00	5040	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-10	12:00:00	11:30:00	5041	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	09:30:00	09:00:00	5042	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	19:30:00	19:00:00	5043	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-22	09:30:00	09:00:00	5044	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-21	09:30:00	09:00:00	5045	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-17	13:30:00	13:00:00	5047	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-16	13:30:00	13:00:00	5048	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	09:30:00	09:00:00	5049	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	19:30:00	19:00:00	5050	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-27	09:30:00	09:00:00	5051	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-13	20:30:00	20:00:00	5052	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-06	20:30:00	20:00:00	5053	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-08	20:30:00	20:00:00	5054	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-08	17:00:00	16:30:00	5055	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-03	17:00:00	16:30:00	5058	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-05	17:00:00	16:30:00	5059	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-07-11	17:00:00	16:30:00	5062	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-28	23:30:00	23:00:00	5063	01977e79-fd6e-737b-bdb8-d563d1ac8899
2025-06-23	12:30:00	12:00:00	5067	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	12:30:00	12:00:00	5069	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	18:00:00	17:30:00	5070	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	12:30:00	12:00:00	5071	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	21:30:00	21:00:00	5075	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-20	21:30:00	21:00:00	5076	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	13:00:00	12:30:00	5077	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	21:30:00	21:00:00	5078	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	18:00:00	17:30:00	5079	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	21:30:00	21:00:00	5080	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-05	17:30:00	17:00:00	5081	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	12:30:00	12:00:00	5082	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	18:00:00	17:30:00	5083	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	00:00:00	23:30:00	5084	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	21:00:00	20:30:00	5087	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	20:30:00	20:00:00	5088	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-06	13:00:00	12:30:00	5090	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	20:30:00	20:00:00	5091	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	20:30:00	20:00:00	5092	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	21:00:00	20:30:00	5094	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	18:00:00	17:30:00	5095	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	20:30:00	20:00:00	5096	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	17:30:00	17:00:00	5097	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	17:30:00	17:00:00	5098	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	15:30:00	15:00:00	5099	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	17:30:00	17:00:00	5100	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	17:30:00	17:00:00	5101	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	15:30:00	15:00:00	5102	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	12:30:00	12:00:00	5105	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	15:30:00	15:00:00	5106	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	22:30:00	22:00:00	5107	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	13:00:00	12:30:00	5108	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	23:00:00	22:30:00	5109	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-05	22:30:00	22:00:00	5110	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	22:30:00	22:00:00	5111	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	13:00:00	12:30:00	5113	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-05	21:00:00	20:30:00	5114	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	13:00:00	12:30:00	5115	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	15:30:00	15:00:00	5116	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	15:00:00	14:30:00	5117	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	15:00:00	14:30:00	5118	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	15:00:00	14:30:00	5119	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	15:00:00	14:30:00	5120	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	15:00:00	14:30:00	5121	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	10:30:00	10:00:00	5122	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	10:30:00	10:00:00	5123	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	14:00:00	13:30:00	5124	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	14:00:00	13:30:00	5126	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	14:00:00	13:30:00	5130	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	22:30:00	22:00:00	5134	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	22:30:00	22:00:00	5135	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	22:30:00	22:00:00	5136	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	22:30:00	22:00:00	5137	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	21:30:00	21:00:00	5140	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	10:30:00	10:00:00	5141	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	21:30:00	21:00:00	5142	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	15:00:00	14:30:00	5143	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	01:00:00	00:30:00	5145	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	16:30:00	16:00:00	5146	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-10	15:00:00	14:30:00	5149	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	15:00:00	14:30:00	5150	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	16:30:00	16:00:00	5151	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	10:00:00	09:30:00	5153	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	20:00:00	19:30:00	5154	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	20:00:00	19:30:00	5155	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	20:00:00	19:30:00	5156	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-20	20:00:00	19:30:00	5157	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	10:00:00	09:30:00	5158	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	10:00:00	09:30:00	5159	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	20:00:00	19:30:00	5160	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	10:30:00	10:00:00	5161	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	10:00:00	09:30:00	5162	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	10:00:00	09:30:00	5163	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	10:00:00	09:30:00	5164	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	08:30:00	08:00:00	5166	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	08:30:00	08:00:00	5168	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	09:30:00	09:00:00	5169	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	16:30:00	16:00:00	5170	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	16:30:00	16:00:00	5171	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	09:30:00	09:00:00	5172	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	12:00:00	11:30:00	5173	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	16:30:00	16:00:00	5175	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-06	16:00:00	15:30:00	5177	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	12:00:00	11:30:00	5179	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-05	16:00:00	15:30:00	5180	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	08:30:00	08:00:00	5182	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	08:30:00	08:00:00	5184	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	16:00:00	15:30:00	5185	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	08:30:00	08:00:00	5187	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	08:30:00	08:00:00	5188	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	19:00:00	18:30:00	5189	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	12:00:00	11:30:00	5190	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	08:30:00	08:00:00	5191	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	08:30:00	08:00:00	5192	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	08:30:00	08:00:00	5193	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	19:00:00	18:30:00	5195	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	19:00:00	18:30:00	5196	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	19:00:00	18:30:00	5197	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	09:30:00	09:00:00	5198	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	19:00:00	18:30:00	5200	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	19:00:00	18:30:00	5201	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	16:00:00	15:30:00	5202	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-20	16:00:00	15:30:00	5205	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	18:30:00	18:00:00	5206	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	08:30:00	08:00:00	5207	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	16:00:00	15:30:00	5208	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	08:30:00	08:00:00	5211	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	16:00:00	15:30:00	5214	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-05	08:30:00	08:00:00	5215	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-06	08:30:00	08:00:00	5216	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	08:30:00	08:00:00	5217	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	11:30:00	11:00:00	5218	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	16:00:00	15:30:00	5219	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	14:00:00	13:30:00	5222	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	11:30:00	11:00:00	5223	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	08:30:00	08:00:00	5224	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	11:00:00	10:30:00	5225	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-14	14:00:00	13:30:00	5226	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	11:30:00	11:00:00	5227	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-14	11:00:00	10:30:00	5228	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	11:30:00	11:00:00	5229	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-08	11:00:00	10:30:00	5231	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	11:30:00	11:00:00	5233	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-10	19:30:00	19:00:00	5235	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-20	23:00:00	22:30:00	5240	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	14:30:00	14:00:00	5241	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	18:30:00	18:00:00	5242	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	17:00:00	16:30:00	5243	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	14:30:00	14:00:00	5245	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	14:30:00	14:00:00	5246	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	18:30:00	18:00:00	5247	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-20	18:30:00	18:00:00	5248	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	18:30:00	18:00:00	5249	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-20	17:00:00	16:30:00	5251	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	22:00:00	21:30:00	5252	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-22	17:00:00	16:30:00	5253	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	11:00:00	10:30:00	5255	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	17:00:00	16:30:00	5256	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	11:00:00	10:30:00	5257	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	11:00:00	10:30:00	5258	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	11:00:00	10:30:00	5259	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	14:30:00	14:00:00	5260	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	17:00:00	16:30:00	5261	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	17:00:00	16:30:00	5262	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	22:00:00	21:30:00	5263	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	12:00:00	11:30:00	5264	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	13:30:00	13:00:00	5265	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-21	13:30:00	13:00:00	5267	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	18:00:00	17:30:00	5269	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-18	09:30:00	09:00:00	5271	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	12:00:00	11:30:00	5272	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	13:30:00	13:00:00	5273	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-14	18:00:00	17:30:00	5275	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-25	19:30:00	19:00:00	5276	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-01	20:30:00	20:00:00	5277	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-17	13:30:00	13:00:00	5278	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	09:30:00	09:00:00	5279	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-28	09:30:00	09:00:00	5280	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-13	20:30:00	20:00:00	5282	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-04	20:30:00	20:00:00	5283	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-23	23:30:00	23:00:00	5285	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-07-09	17:00:00	16:30:00	5286	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-27	23:30:00	23:00:00	5289	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-29	23:30:00	23:00:00	5290	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-14	14:50:00	14:20:00	5292	01977e79-fdcd-7647-9bfc-c4f208c7d607
2025-06-24	12:30:00	12:00:00	5293	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	16:30:00	16:00:00	5294	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	12:30:00	12:00:00	5296	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	16:30:00	16:00:00	5297	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-15	09:30:00	09:00:00	5299	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	16:30:00	16:00:00	5300	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	16:30:00	16:00:00	5301	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-11	09:30:00	09:00:00	5302	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	12:00:00	11:30:00	5303	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-08	17:30:00	17:00:00	5305	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-10	17:30:00	17:00:00	5306	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	08:30:00	08:00:00	5308	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-10	16:00:00	15:30:00	5309	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	08:30:00	08:00:00	5310	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	08:30:00	08:00:00	5312	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-15	13:30:00	13:00:00	5315	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	08:30:00	08:00:00	5316	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	18:00:00	17:30:00	5317	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-11	13:30:00	13:00:00	5320	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	18:00:00	17:30:00	5321	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	21:00:00	20:30:00	5322	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	00:00:00	23:30:00	5323	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	21:00:00	20:30:00	5324	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	21:00:00	20:30:00	5325	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	00:00:00	23:30:00	5326	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	19:00:00	18:30:00	5327	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-10	20:00:00	19:30:00	5328	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	20:30:00	20:00:00	5329	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	19:00:00	18:30:00	5330	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	20:30:00	20:00:00	5331	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-08	18:30:00	18:00:00	5332	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	17:30:00	17:00:00	5333	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	17:30:00	17:00:00	5334	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	17:30:00	17:00:00	5337	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	16:00:00	15:30:00	5339	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-09	08:30:00	08:00:00	5342	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-15	18:30:00	18:00:00	5343	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-08	08:30:00	08:00:00	5345	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-10	12:30:00	12:00:00	5350	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	12:30:00	12:00:00	5351	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	15:30:00	15:00:00	5352	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	15:30:00	15:00:00	5353	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	15:30:00	15:00:00	5355	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	22:30:00	22:00:00	5357	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	13:00:00	12:30:00	5359	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	11:30:00	11:00:00	5361	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	13:00:00	12:30:00	5362	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-15	11:00:00	10:30:00	5363	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	21:00:00	20:30:00	5365	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	14:00:00	13:30:00	5366	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-15	21:00:00	20:30:00	5367	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-10	14:00:00	13:30:00	5369	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-08	14:00:00	13:30:00	5372	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	11:30:00	11:00:00	5373	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	23:00:00	22:30:00	5374	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	19:30:00	19:00:00	5376	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	15:00:00	14:30:00	5378	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	23:00:00	22:30:00	5379	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	15:00:00	14:30:00	5380	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	10:30:00	10:00:00	5382	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	18:30:00	18:00:00	5383	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	14:30:00	14:00:00	5384	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	14:30:00	14:00:00	5385	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	22:00:00	21:30:00	5386	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	22:00:00	21:30:00	5387	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	22:00:00	21:30:00	5391	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	11:00:00	10:30:00	5392	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	11:00:00	10:30:00	5394	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	14:00:00	13:30:00	5395	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	22:30:00	22:00:00	5396	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	13:30:00	13:00:00	5397	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	09:30:00	09:00:00	5404	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-24	09:30:00	09:00:00	5405	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-19	19:30:00	19:00:00	5406	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	15:00:00	14:30:00	5408	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	19:30:00	19:00:00	5410	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	19:30:00	19:00:00	5413	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-27	09:30:00	09:00:00	5414	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-25	20:00:00	19:30:00	5415	01977e79-fe37-75a1-8b40-2fc606a56218
2025-07-03	17:00:00	16:30:00	5419	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-30	10:00:00	09:30:00	5420	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-10	11:00:00	10:30:00	5424	01977e79-fe37-75a1-8b40-2fc606a56218
2025-06-23	16:30:00	16:00:00	5425	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	09:30:00	09:00:00	5426	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	08:30:00	08:00:00	5427	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	18:00:00	17:30:00	5429	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-14	09:30:00	09:00:00	5430	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-13	09:30:00	09:00:00	5431	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	12:30:00	12:00:00	5432	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	18:00:00	17:30:00	5434	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	12:00:00	11:30:00	5435	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-06	16:00:00	15:30:00	5436	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	12:00:00	11:30:00	5437	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	21:30:00	21:00:00	5438	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	08:30:00	08:00:00	5439	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	00:00:00	23:30:00	5440	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	00:00:00	23:30:00	5441	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	08:30:00	08:00:00	5443	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	08:30:00	08:00:00	5444	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-14	16:00:00	15:30:00	5445	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	16:30:00	16:00:00	5446	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	12:00:00	11:30:00	5448	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	12:00:00	11:30:00	5449	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	12:00:00	11:30:00	5450	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	18:00:00	17:30:00	5451	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	18:00:00	17:30:00	5454	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	08:30:00	08:00:00	5455	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	18:00:00	17:30:00	5456	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	21:00:00	20:30:00	5459	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	21:00:00	20:30:00	5460	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	17:30:00	17:00:00	5461	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	20:30:00	20:00:00	5462	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	20:30:00	20:00:00	5463	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	20:30:00	20:00:00	5464	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	09:30:00	09:00:00	5465	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	09:30:00	09:00:00	5466	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	21:30:00	21:00:00	5467	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	20:30:00	20:00:00	5468	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	21:30:00	21:00:00	5469	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	19:00:00	18:30:00	5470	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	19:00:00	18:30:00	5473	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	16:00:00	15:30:00	5474	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	16:00:00	15:30:00	5475	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-13	08:30:00	08:00:00	5476	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	11:00:00	10:30:00	5479	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	08:30:00	08:00:00	5480	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	08:30:00	08:00:00	5481	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	12:30:00	12:00:00	5486	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	15:30:00	15:00:00	5490	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	15:30:00	15:00:00	5491	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	15:30:00	15:00:00	5492	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	15:30:00	15:00:00	5493	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-13	12:30:00	12:00:00	5494	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	13:00:00	12:30:00	5496	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	23:00:00	22:30:00	5497	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	11:30:00	11:00:00	5498	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	22:30:00	22:00:00	5499	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	08:30:00	08:00:00	5501	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	08:30:00	08:00:00	5502	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	13:00:00	12:30:00	5503	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	13:00:00	12:30:00	5504	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	18:30:00	18:00:00	5505	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	18:30:00	18:00:00	5506	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	15:30:00	15:00:00	5507	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	11:30:00	11:00:00	5508	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	14:00:00	13:30:00	5512	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	19:30:00	19:00:00	5513	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	15:30:00	15:00:00	5515	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-13	15:30:00	15:00:00	5516	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	10:30:00	10:00:00	5517	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	17:00:00	16:30:00	5519	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-13	19:30:00	19:00:00	5520	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	10:30:00	10:00:00	5521	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	14:30:00	14:00:00	5522	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	22:30:00	22:00:00	5523	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	14:30:00	14:00:00	5524	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	14:30:00	14:00:00	5525	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	15:00:00	14:30:00	5526	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	18:30:00	18:00:00	5527	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	18:30:00	18:00:00	5528	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	14:30:00	14:00:00	5529	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	14:30:00	14:00:00	5530	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	14:30:00	14:00:00	5531	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	11:00:00	10:30:00	5532	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	11:00:00	10:30:00	5533	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	23:30:00	23:00:00	5534	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	17:00:00	16:30:00	5535	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-06	11:30:00	11:00:00	5536	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	17:00:00	16:30:00	5537	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	11:00:00	10:30:00	5538	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	11:00:00	10:30:00	5539	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	17:00:00	16:30:00	5542	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	17:00:00	16:30:00	5545	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	14:00:00	13:30:00	5546	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	22:30:00	22:00:00	5547	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	14:30:00	14:00:00	5548	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	18:00:00	17:30:00	5549	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	13:30:00	13:00:00	5551	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	18:00:00	17:30:00	5552	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-14	12:00:00	11:30:00	5553	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	13:30:00	13:00:00	5554	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	20:00:00	19:30:00	5558	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	20:00:00	19:30:00	5559	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	09:30:00	09:00:00	5560	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-16	19:30:00	19:00:00	5561	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	13:30:00	13:00:00	5563	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-14	21:30:00	21:00:00	5566	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-11	14:30:00	14:00:00	5567	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	19:30:00	19:00:00	5572	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-23	09:30:00	09:00:00	5574	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-21	19:30:00	19:00:00	5575	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-29	09:30:00	09:00:00	5577	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	09:30:00	09:00:00	5578	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	19:30:00	19:00:00	5579	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	20:00:00	19:30:00	5581	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	15:00:00	14:30:00	5582	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	13:30:00	13:00:00	5584	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	13:30:00	13:00:00	5585	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	16:30:00	16:00:00	5586	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	20:30:00	20:00:00	5587	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	20:30:00	20:00:00	5588	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-02	12:00:00	11:30:00	5590	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	10:00:00	09:30:00	5592	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-08	22:00:00	21:30:00	5593	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-01	10:30:00	10:00:00	5594	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-22	10:00:00	09:30:00	5596	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-13	17:00:00	16:30:00	5597	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-10	10:50:00	10:20:00	5598	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-07	09:30:00	09:00:00	5599	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	12:30:00	12:00:00	5600	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	09:30:00	09:00:00	5601	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	16:00:00	15:30:00	5605	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	12:00:00	11:30:00	5606	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-07	13:30:00	13:00:00	5607	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	08:30:00	08:00:00	5608	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	16:00:00	15:30:00	5609	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	13:30:00	13:00:00	5610	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	08:30:00	08:00:00	5611	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	19:00:00	18:30:00	5613	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	21:30:00	21:00:00	5614	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	08:30:00	08:00:00	5615	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	18:00:00	17:30:00	5616	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	21:00:00	20:30:00	5619	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	21:00:00	20:30:00	5620	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-04	13:00:00	12:30:00	5621	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	17:30:00	17:00:00	5622	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	19:00:00	18:30:00	5623	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	20:30:00	20:00:00	5624	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	20:00:00	19:30:00	5626	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	20:00:00	19:30:00	5627	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	19:00:00	18:30:00	5628	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	20:30:00	20:00:00	5629	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	19:00:00	18:30:00	5630	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	16:00:00	15:30:00	5634	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	17:30:00	17:00:00	5635	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	16:00:00	15:30:00	5636	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	17:30:00	17:00:00	5637	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	18:30:00	18:00:00	5638	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	16:00:00	15:30:00	5639	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	11:00:00	10:30:00	5640	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	08:30:00	08:00:00	5641	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	08:30:00	08:00:00	5644	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	08:30:00	08:00:00	5646	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-04	08:30:00	08:00:00	5647	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	12:30:00	12:00:00	5649	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	15:30:00	15:00:00	5651	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	12:30:00	12:00:00	5652	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	22:30:00	22:00:00	5656	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	13:00:00	12:30:00	5658	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	08:30:00	08:00:00	5659	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	21:00:00	20:30:00	5661	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	11:30:00	11:00:00	5662	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	14:00:00	13:30:00	5663	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	11:00:00	10:30:00	5664	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	13:00:00	12:30:00	5665	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	11:00:00	10:30:00	5666	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-07	11:00:00	10:30:00	5667	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	15:30:00	15:00:00	5668	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	21:00:00	20:30:00	5669	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-04	15:30:00	15:00:00	5672	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	15:00:00	14:30:00	5673	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-12	15:30:00	15:00:00	5674	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	15:00:00	14:30:00	5675	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	15:00:00	14:30:00	5676	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	18:30:00	18:00:00	5677	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	14:30:00	14:00:00	5678	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	11:00:00	10:30:00	5679	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	23:30:00	23:00:00	5681	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	17:00:00	16:30:00	5683	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	22:00:00	21:30:00	5685	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	14:00:00	13:30:00	5686	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	11:30:00	11:00:00	5688	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	11:00:00	10:30:00	5689	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	17:00:00	16:30:00	5691	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	22:00:00	21:30:00	5692	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	22:30:00	22:00:00	5693	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	22:30:00	22:00:00	5694	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	14:30:00	14:00:00	5695	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	13:30:00	13:00:00	5696	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-07	14:30:00	14:00:00	5698	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	21:30:00	21:00:00	5699	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	14:30:00	14:00:00	5700	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-04	18:00:00	17:30:00	5701	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	18:00:00	17:30:00	5702	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-04	14:30:00	14:00:00	5703	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	20:00:00	19:30:00	5706	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	18:00:00	17:30:00	5707	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-12	14:30:00	14:00:00	5709	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	10:00:00	09:30:00	5710	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	09:30:00	09:00:00	5711	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	15:00:00	14:30:00	5712	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	19:30:00	19:00:00	5713	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	20:00:00	19:30:00	5715	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	13:30:00	13:00:00	5716	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	16:30:00	16:00:00	5718	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-04	17:00:00	16:30:00	5719	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-29	10:00:00	09:30:00	5720	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-20	23:30:00	23:00:00	5721	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-05	17:00:00	16:30:00	5722	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-06	17:00:00	16:30:00	5723	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-02	12:00:00	11:30:00	5724	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-11	17:00:00	16:30:00	5727	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-22	10:00:00	09:30:00	5728	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-07-12	17:00:00	16:30:00	5729	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-10	10:40:00	10:10:00	5730	01977e79-ff04-74d9-87bc-c1de6d4a2c4c
2025-06-25	16:30:00	16:00:00	5731	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	08:30:00	08:00:00	5732	01977e79-ff63-73de-9441-b73d75415356
2025-07-06	09:30:00	09:00:00	5733	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	09:30:00	09:00:00	5734	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	16:30:00	16:00:00	5735	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	18:00:00	17:30:00	5736	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	12:30:00	12:00:00	5737	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	13:30:00	13:00:00	5739	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	21:30:00	21:00:00	5740	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	16:00:00	15:30:00	5741	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	00:30:00	00:00:00	5742	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	13:30:00	13:00:00	5744	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	13:00:00	12:30:00	5745	01977e79-ff63-73de-9441-b73d75415356
2025-06-20	18:00:00	17:30:00	5746	01977e79-ff63-73de-9441-b73d75415356
2025-07-06	13:30:00	13:00:00	5747	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	08:30:00	08:00:00	5748	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	08:30:00	08:00:00	5749	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	12:00:00	11:30:00	5752	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	00:00:00	23:30:00	5753	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	21:30:00	21:00:00	5754	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	08:30:00	08:00:00	5756	01977e79-ff63-73de-9441-b73d75415356
2025-07-12	13:00:00	12:30:00	5758	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	21:00:00	20:30:00	5759	01977e79-ff63-73de-9441-b73d75415356
2025-06-20	21:00:00	20:30:00	5760	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	00:00:00	23:30:00	5762	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	19:00:00	18:30:00	5763	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	21:30:00	21:00:00	5765	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	18:00:00	17:30:00	5766	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	21:00:00	20:30:00	5767	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	17:30:00	17:00:00	5769	01977e79-ff63-73de-9441-b73d75415356
2025-07-13	08:30:00	08:00:00	5771	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	17:30:00	17:00:00	5775	01977e79-ff63-73de-9441-b73d75415356
2025-07-12	08:30:00	08:00:00	5776	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	08:30:00	08:00:00	5777	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	16:00:00	15:30:00	5778	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	08:30:00	08:00:00	5779	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	17:30:00	17:00:00	5780	01977e79-ff63-73de-9441-b73d75415356
2025-07-03	08:30:00	08:00:00	5782	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	15:30:00	15:00:00	5783	01977e79-ff63-73de-9441-b73d75415356
2025-07-13	18:30:00	18:00:00	5784	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	15:30:00	15:00:00	5785	01977e79-ff63-73de-9441-b73d75415356
2025-07-13	23:00:00	22:30:00	5787	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	15:30:00	15:00:00	5788	01977e79-ff63-73de-9441-b73d75415356
2025-07-06	12:30:00	12:00:00	5789	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	11:30:00	11:00:00	5790	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	13:00:00	12:30:00	5792	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	23:00:00	22:30:00	5793	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	11:30:00	11:00:00	5794	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	16:00:00	15:30:00	5796	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	21:00:00	20:30:00	5797	01977e79-ff63-73de-9441-b73d75415356
2025-07-12	11:00:00	10:30:00	5798	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	08:30:00	08:00:00	5799	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	11:30:00	11:00:00	5801	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	13:00:00	12:30:00	5802	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	13:00:00	12:30:00	5804	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	19:30:00	19:00:00	5806	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	23:00:00	22:30:00	5807	01977e79-ff63-73de-9441-b73d75415356
2025-07-06	15:30:00	15:00:00	5808	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	15:00:00	14:30:00	5809	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	19:30:00	19:00:00	5810	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	15:30:00	15:00:00	5811	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	17:00:00	16:30:00	5813	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	10:30:00	10:00:00	5815	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	18:30:00	18:00:00	5816	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	23:00:00	22:30:00	5817	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	18:30:00	18:00:00	5818	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	18:30:00	18:00:00	5820	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	11:00:00	10:30:00	5821	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	14:00:00	13:30:00	5823	01977e79-ff63-73de-9441-b73d75415356
2025-07-03	11:30:00	11:00:00	5824	01977e79-ff63-73de-9441-b73d75415356
2025-06-20	17:00:00	16:30:00	5825	01977e79-ff63-73de-9441-b73d75415356
2025-07-12	10:00:00	09:30:00	5826	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	14:00:00	13:30:00	5830	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	11:00:00	10:30:00	5831	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	14:00:00	13:30:00	5832	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	22:00:00	21:30:00	5834	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	14:00:00	13:30:00	5835	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	14:30:00	14:00:00	5836	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	19:30:00	19:00:00	5837	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	17:00:00	16:30:00	5838	01977e79-ff63-73de-9441-b73d75415356
2025-07-13	12:00:00	11:30:00	5842	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	12:00:00	11:30:00	5848	01977e79-ff63-73de-9441-b73d75415356
2025-07-13	14:30:00	14:00:00	5849	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	09:30:00	09:00:00	5853	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	10:00:00	09:30:00	5854	01977e79-ff63-73de-9441-b73d75415356
2025-07-09	16:30:00	16:00:00	5855	01977e79-ff63-73de-9441-b73d75415356
2025-07-03	16:30:00	16:00:00	5856	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	19:30:00	19:00:00	5857	01977e79-ff63-73de-9441-b73d75415356
2025-07-01	20:30:00	20:00:00	5858	01977e79-ff63-73de-9441-b73d75415356
2025-06-20	19:30:00	19:00:00	5860	01977e79-ff63-73de-9441-b73d75415356
2025-07-12	16:30:00	16:00:00	5863	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	09:30:00	09:00:00	5864	01977e79-ff63-73de-9441-b73d75415356
2025-06-25	20:00:00	19:30:00	5865	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	10:00:00	09:30:00	5866	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	20:30:00	20:00:00	5868	01977e79-ff63-73de-9441-b73d75415356
2025-06-28	20:00:00	19:30:00	5869	01977e79-ff63-73de-9441-b73d75415356
2025-07-05	17:00:00	16:30:00	5870	01977e79-ff63-73de-9441-b73d75415356
2025-07-06	17:00:00	16:30:00	5872	01977e79-ff63-73de-9441-b73d75415356
2025-06-16	20:00:00	19:30:00	5873	01977e79-ff63-73de-9441-b73d75415356
2025-06-22	10:00:00	09:30:00	5874	01977e79-ff63-73de-9441-b73d75415356
2025-06-14	15:20:00	14:50:00	5876	01977e79-ff63-73de-9441-b73d75415356
2025-06-18	08:30:00	08:00:00	5877	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-08	09:30:00	09:00:00	5878	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	08:30:00	08:00:00	5879	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-05	09:30:00	09:00:00	5882	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	21:00:00	20:30:00	5883	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	18:00:00	17:30:00	5884	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	16:30:00	16:00:00	5885	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	12:30:00	12:00:00	5886	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	18:00:00	17:30:00	5888	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	13:00:00	12:30:00	5889	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-05	13:30:00	13:00:00	5890	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	13:00:00	12:30:00	5891	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	17:30:00	17:00:00	5892	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	08:30:00	08:00:00	5894	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-08	16:00:00	15:30:00	5895	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	08:30:00	08:00:00	5896	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	12:30:00	12:00:00	5897	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	21:00:00	20:30:00	5901	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	20:30:00	20:00:00	5903	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	13:00:00	12:30:00	5904	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	13:00:00	12:30:00	5905	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	20:30:00	20:00:00	5907	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	20:00:00	19:30:00	5908	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	09:30:00	09:00:00	5910	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	09:30:00	09:00:00	5911	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	18:00:00	17:30:00	5913	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	19:00:00	18:30:00	5914	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	14:00:00	13:30:00	5916	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	14:00:00	13:30:00	5918	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	11:00:00	10:30:00	5919	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	16:00:00	15:30:00	5920	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	17:30:00	17:00:00	5924	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	15:30:00	15:00:00	5925	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	08:30:00	08:00:00	5926	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	17:30:00	17:00:00	5929	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	11:30:00	11:00:00	5930	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	16:00:00	15:30:00	5931	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	19:00:00	18:30:00	5932	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	13:00:00	12:30:00	5934	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	19:00:00	18:30:00	5935	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	11:30:00	11:00:00	5936	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	11:30:00	11:00:00	5937	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	16:00:00	15:30:00	5939	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	08:30:00	08:00:00	5940	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	08:30:00	08:00:00	5941	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	11:30:00	11:00:00	5943	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	21:00:00	20:30:00	5945	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-05	11:00:00	10:30:00	5946	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	18:30:00	18:00:00	5947	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	15:30:00	15:00:00	5949	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	13:00:00	12:30:00	5950	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	11:30:00	11:00:00	5952	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-08	19:30:00	19:00:00	5954	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	15:00:00	14:30:00	5955	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	15:30:00	15:00:00	5956	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	18:30:00	18:00:00	5958	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	14:30:00	14:00:00	5959	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	10:30:00	10:00:00	5960	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	17:00:00	16:30:00	5961	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	17:00:00	16:30:00	5962	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	14:30:00	14:00:00	5964	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	15:00:00	14:30:00	5965	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	17:00:00	16:30:00	5966	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	11:30:00	11:00:00	5967	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	11:30:00	11:00:00	5968	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	23:30:00	23:00:00	5969	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	22:00:00	21:30:00	5971	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	22:00:00	21:30:00	5976	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	14:00:00	13:30:00	5977	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	19:30:00	19:00:00	5978	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	19:30:00	19:00:00	5979	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	17:00:00	16:30:00	5980	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	22:30:00	22:00:00	5981	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	22:00:00	21:30:00	5982	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	18:00:00	17:30:00	5986	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	19:30:00	19:00:00	5990	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-05	12:00:00	11:30:00	5991	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	09:30:00	09:00:00	5992	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-16	19:30:00	19:00:00	5993	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-13	21:30:00	21:00:00	5996	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	13:30:00	13:00:00	5997	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	19:30:00	19:00:00	5998	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	01:00:00	00:30:00	5999	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	23:30:00	23:00:00	6002	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	10:00:00	09:30:00	6004	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-13	20:30:00	20:00:00	6005	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	15:00:00	14:30:00	6006	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-18	23:30:00	23:00:00	6008	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	20:30:00	20:00:00	6009	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-10	17:00:00	16:30:00	6011	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-01	12:00:00	11:30:00	6012	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-05	17:00:00	16:30:00	6013	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	10:00:00	09:30:00	6014	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-25	10:00:00	09:30:00	6015	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-02	10:30:00	10:00:00	6016	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-27	23:30:00	23:00:00	6017	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-04	22:00:00	21:30:00	6018	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-07-13	17:00:00	16:30:00	6020	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-15	15:50:00	15:20:00	6021	01977e79-ffca-7f6d-8b09-70a69d42bf8c
2025-06-23	16:30:00	16:00:00	6022	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	08:30:00	08:00:00	6023	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	12:30:00	12:00:00	6025	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	16:30:00	16:00:00	6026	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	12:00:00	11:30:00	6028	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	09:30:00	09:00:00	6029	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-14	09:30:00	09:00:00	6030	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	09:30:00	09:00:00	6031	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	18:00:00	17:30:00	6032	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	21:30:00	21:00:00	6034	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	18:00:00	17:30:00	6035	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	21:30:00	21:00:00	6036	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	08:30:00	08:00:00	6039	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	08:30:00	08:00:00	6041	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	16:00:00	15:30:00	6042	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	08:30:00	08:00:00	6043	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	12:00:00	11:30:00	6044	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	12:00:00	11:30:00	6045	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	08:30:00	08:00:00	6047	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-14	17:30:00	17:00:00	6048	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	21:00:00	20:30:00	6050	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	20:30:00	20:00:00	6051	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-09	13:00:00	12:30:00	6052	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-08	13:00:00	12:30:00	6054	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-09	20:00:00	19:30:00	6055	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	19:00:00	18:30:00	6056	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	20:00:00	19:30:00	6057	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	16:00:00	15:30:00	6058	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-09	18:30:00	18:00:00	6059	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-20	16:00:00	15:30:00	6060	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	08:30:00	08:00:00	6061	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-20	17:30:00	17:00:00	6063	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	16:00:00	15:30:00	6064	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	11:00:00	10:30:00	6065	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-09	08:30:00	08:00:00	6066	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	12:30:00	12:00:00	6070	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-14	12:30:00	12:00:00	6072	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	16:00:00	15:30:00	6073	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	12:30:00	12:00:00	6074	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	13:00:00	12:30:00	6076	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-14	14:00:00	13:30:00	6081	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	11:30:00	11:00:00	6082	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	21:00:00	20:30:00	6084	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-08	11:00:00	10:30:00	6086	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	21:00:00	20:30:00	6087	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	13:00:00	12:30:00	6089	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-08	19:30:00	19:00:00	6090	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	15:30:00	15:00:00	6091	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	19:30:00	19:00:00	6093	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	15:00:00	14:30:00	6096	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	14:30:00	14:00:00	6097	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-20	15:00:00	14:30:00	6098	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	18:30:00	18:00:00	6099	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	15:00:00	14:30:00	6100	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	15:00:00	14:30:00	6101	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	18:30:00	18:00:00	6102	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	10:30:00	10:00:00	6103	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	10:30:00	10:00:00	6104	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	14:30:00	14:00:00	6105	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-20	18:30:00	18:00:00	6106	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	18:30:00	18:00:00	6107	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	10:30:00	10:00:00	6108	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	23:00:00	22:30:00	6109	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	17:00:00	16:30:00	6110	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	17:00:00	16:30:00	6113	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-09	10:00:00	09:30:00	6115	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	14:00:00	13:30:00	6116	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	11:00:00	10:30:00	6117	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-14	11:30:00	11:00:00	6118	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	22:00:00	21:30:00	6119	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	11:00:00	10:30:00	6120	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	17:00:00	16:30:00	6121	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-09	14:30:00	14:00:00	6122	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	13:30:00	13:00:00	6126	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	13:30:00	13:00:00	6128	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	19:30:00	19:00:00	6131	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-17	09:30:00	09:00:00	6132	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	15:00:00	14:30:00	6138	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	09:30:00	09:00:00	6140	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	19:30:00	19:00:00	6141	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-23	09:30:00	09:00:00	6142	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	09:30:00	09:00:00	6143	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-03	16:30:00	16:00:00	6144	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-08	15:00:00	14:30:00	6145	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-14	20:30:00	20:00:00	6147	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	20:00:00	19:30:00	6148	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	20:00:00	19:30:00	6149	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-22	20:00:00	19:30:00	6150	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-20	20:00:00	19:30:00	6151	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-08	17:00:00	16:30:00	6153	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	22:00:00	21:30:00	6154	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-26	10:00:00	09:30:00	6155	01977e7a-0026-79ac-a236-50d54f042a62
2025-07-13	17:00:00	16:30:00	6157	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-09	10:10:00	09:40:00	6159	01977e7a-0026-79ac-a236-50d54f042a62
2025-06-24	12:30:00	12:00:00	6160	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	12:30:00	12:00:00	6161	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	12:30:00	12:00:00	6162	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	18:00:00	17:30:00	6163	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	18:00:00	17:30:00	6164	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	21:30:00	21:00:00	6165	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	18:00:00	17:30:00	6166	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	12:30:00	12:00:00	6167	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	21:30:00	21:00:00	6168	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	18:00:00	17:30:00	6171	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	13:00:00	12:30:00	6172	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	12:30:00	12:00:00	6174	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	18:00:00	17:30:00	6175	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	21:30:00	21:00:00	6177	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	21:00:00	20:30:00	6179	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	21:00:00	20:30:00	6180	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	20:30:00	20:00:00	6181	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	20:30:00	20:00:00	6182	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	20:30:00	20:00:00	6185	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	18:00:00	17:30:00	6186	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	15:30:00	15:00:00	6187	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	17:30:00	17:00:00	6188	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-09	12:30:00	12:00:00	6190	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	15:30:00	15:00:00	6191	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	15:30:00	15:00:00	6194	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	15:30:00	15:00:00	6195	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	13:00:00	12:30:00	6196	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-09	21:00:00	20:30:00	6198	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	13:00:00	12:30:00	6199	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	21:00:00	20:30:00	6200	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-05	21:00:00	20:30:00	6201	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	21:00:00	20:30:00	6202	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	15:00:00	14:30:00	6203	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	10:30:00	10:00:00	6204	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	10:30:00	10:00:00	6205	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	15:00:00	14:30:00	6206	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	15:00:00	14:30:00	6208	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-09	11:30:00	11:00:00	6209	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	11:30:00	11:00:00	6211	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	14:00:00	13:30:00	6212	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	22:30:00	22:00:00	6214	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	10:00:00	09:30:00	6215	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	10:00:00	09:30:00	6217	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-09	10:00:00	09:30:00	6218	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-05	10:00:00	09:30:00	6219	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-04	10:00:00	09:30:00	6220	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	14:00:00	13:30:00	6222	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	15:00:00	14:30:00	6223	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	16:30:00	16:00:00	6224	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-15	16:30:00	16:00:00	6227	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-05	15:00:00	14:30:00	6228	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-09	15:00:00	14:30:00	6231	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	16:30:00	16:00:00	6232	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	20:00:00	19:30:00	6233	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	10:00:00	09:30:00	6234	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	20:00:00	19:30:00	6235	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	20:00:00	19:30:00	6236	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	10:00:00	09:30:00	6237	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	10:00:00	09:30:00	6238	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	10:00:00	09:30:00	6239	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	10:00:00	09:30:00	6240	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	08:30:00	08:00:00	6241	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	08:30:00	08:00:00	6242	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	08:30:00	08:00:00	6243	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	16:30:00	16:00:00	6244	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	12:00:00	11:30:00	6245	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	16:30:00	16:00:00	6246	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	12:00:00	11:30:00	6247	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	13:30:00	13:00:00	6249	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-05	13:30:00	13:00:00	6251	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	08:30:00	08:00:00	6252	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	08:30:00	08:00:00	6253	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	08:30:00	08:00:00	6256	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	08:30:00	08:00:00	6257	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	19:00:00	18:30:00	6258	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	08:30:00	08:00:00	6259	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-15	13:30:00	13:00:00	6260	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	08:30:00	08:00:00	6261	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	12:00:00	11:30:00	6262	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	19:00:00	18:30:00	6264	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	19:00:00	18:30:00	6265	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	09:30:00	09:00:00	6266	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	19:00:00	18:30:00	6267	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	16:00:00	15:30:00	6268	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-09	18:30:00	18:00:00	6270	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	11:00:00	10:30:00	6273	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	16:00:00	15:30:00	6274	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	16:00:00	15:30:00	6275	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-05	18:30:00	18:00:00	6276	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	08:30:00	08:00:00	6278	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	16:00:00	15:30:00	6279	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-03	08:30:00	08:00:00	6281	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-04	08:30:00	08:00:00	6282	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	16:00:00	15:30:00	6283	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	16:00:00	15:30:00	6284	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	11:30:00	11:00:00	6286	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-15	19:00:00	18:30:00	6287	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	11:30:00	11:00:00	6288	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-03	16:00:00	15:30:00	6289	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	16:00:00	15:30:00	6290	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	08:30:00	08:00:00	6291	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	11:30:00	11:00:00	6293	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-04	11:00:00	10:30:00	6297	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	11:30:00	11:00:00	6299	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	19:30:00	19:00:00	6300	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	15:30:00	15:00:00	6301	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-04	19:30:00	19:00:00	6303	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	14:30:00	14:00:00	6304	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	17:00:00	16:30:00	6305	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	19:30:00	19:00:00	6306	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	18:30:00	18:00:00	6307	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	18:30:00	18:00:00	6308	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	14:30:00	14:00:00	6309	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	14:30:00	14:00:00	6310	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	18:30:00	18:00:00	6311	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	14:30:00	14:00:00	6312	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	18:30:00	18:00:00	6313	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	14:30:00	14:00:00	6314	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	14:30:00	14:00:00	6315	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	18:30:00	18:00:00	6316	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	17:00:00	16:30:00	6317	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	11:00:00	10:30:00	6318	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	17:00:00	16:30:00	6319	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	17:00:00	16:30:00	6320	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	11:00:00	10:30:00	6321	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	11:00:00	10:30:00	6322	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	23:30:00	23:00:00	6323	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	11:00:00	10:30:00	6324	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-30	17:00:00	16:30:00	6325	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	11:00:00	10:30:00	6326	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	14:30:00	14:00:00	6327	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	17:00:00	16:30:00	6328	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	17:00:00	16:30:00	6329	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	17:00:00	16:30:00	6330	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	13:30:00	13:00:00	6331	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	13:30:00	13:00:00	6333	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	12:00:00	11:30:00	6334	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-24	13:30:00	13:00:00	6335	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-10	18:00:00	17:30:00	6336	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	13:30:00	13:00:00	6338	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	09:30:00	09:00:00	6339	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-15	18:00:00	17:30:00	6340	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	09:30:00	09:00:00	6341	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-18	19:30:00	19:00:00	6342	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-27	13:30:00	13:00:00	6343	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-13	18:00:00	17:30:00	6344	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-25	09:30:00	09:00:00	6345	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	19:30:00	19:00:00	6346	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-19	19:30:00	19:00:00	6347	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-17	13:30:00	13:00:00	6349	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-16	13:30:00	13:00:00	6350	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	09:30:00	09:00:00	6351	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-28	19:30:00	19:00:00	6352	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-15	20:30:00	20:00:00	6353	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-07-01	12:00:00	11:30:00	6357	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-08	08:50:00	08:20:00	6358	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	12:30:00	12:00:00	6359	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	12:30:00	12:00:00	6360	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	12:30:00	12:00:00	6361	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	18:00:00	17:30:00	6362	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	21:30:00	21:00:00	6363	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	12:30:00	12:00:00	6364	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	18:00:00	17:30:00	6365	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	21:30:00	21:00:00	6368	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	21:30:00	21:00:00	6369	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	12:30:00	12:00:00	6371	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	18:00:00	17:30:00	6372	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	18:00:00	17:30:00	6373	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	21:00:00	20:30:00	6374	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	13:00:00	12:30:00	6375	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	00:00:00	23:30:00	6376	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	21:00:00	20:30:00	6377	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-15	13:00:00	12:30:00	6378	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	00:00:00	23:30:00	6379	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	21:00:00	20:30:00	6380	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-08	13:00:00	12:30:00	6382	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	20:30:00	20:00:00	6383	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-06	20:00:00	19:30:00	6385	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	20:30:00	20:00:00	6386	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	21:00:00	20:30:00	6387	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	21:00:00	20:30:00	6388	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	20:30:00	20:00:00	6390	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	17:30:00	17:00:00	6391	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	17:30:00	17:00:00	6392	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	17:30:00	17:00:00	6393	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	12:30:00	12:00:00	6394	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	15:30:00	15:00:00	6395	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	15:30:00	15:00:00	6397	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	23:00:00	22:30:00	6398	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	17:30:00	17:00:00	6399	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	13:00:00	12:30:00	6401	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	13:00:00	12:30:00	6402	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	13:00:00	12:30:00	6403	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-06	21:00:00	20:30:00	6404	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	15:00:00	14:30:00	6406	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	15:00:00	14:30:00	6407	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	10:30:00	10:00:00	6408	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	10:30:00	10:00:00	6409	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	14:00:00	13:30:00	6410	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	22:30:00	22:00:00	6411	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	22:30:00	22:00:00	6412	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-15	11:30:00	11:00:00	6415	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	14:00:00	13:30:00	6416	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-11	11:30:00	11:00:00	6417	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-05	10:00:00	09:30:00	6418	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	22:30:00	22:00:00	6419	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-15	10:30:00	10:00:00	6422	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-05	15:00:00	14:30:00	6427	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	20:00:00	19:30:00	6431	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	10:00:00	09:30:00	6432	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	10:00:00	09:30:00	6433	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	20:00:00	19:30:00	6434	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	10:00:00	09:30:00	6435	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	20:00:00	19:30:00	6436	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	20:00:00	19:30:00	6437	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	10:00:00	09:30:00	6438	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	08:30:00	08:00:00	6441	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	08:30:00	08:00:00	6442	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	08:30:00	08:00:00	6444	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	12:00:00	11:30:00	6446	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	16:30:00	16:00:00	6448	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	12:00:00	11:30:00	6451	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-05	16:00:00	15:30:00	6452	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	08:30:00	08:00:00	6454	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	08:30:00	08:00:00	6456	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	08:30:00	08:00:00	6459	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-15	16:00:00	15:30:00	6460	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	08:30:00	08:00:00	6461	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	16:30:00	16:00:00	6462	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	16:00:00	15:30:00	6463	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	08:30:00	08:00:00	6464	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	08:30:00	08:00:00	6465	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	12:00:00	11:30:00	6467	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	19:00:00	18:30:00	6468	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	19:00:00	18:30:00	6469	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	19:00:00	18:30:00	6470	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-15	08:30:00	08:00:00	6472	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	18:30:00	18:00:00	6473	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	08:30:00	08:00:00	6474	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	16:00:00	15:30:00	6476	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-05	18:30:00	18:00:00	6477	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-10	08:30:00	08:00:00	6478	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	16:00:00	15:30:00	6479	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	16:00:00	15:30:00	6480	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-06	08:30:00	08:00:00	6483	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	18:30:00	18:00:00	6484	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	08:30:00	08:00:00	6485	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	16:00:00	15:30:00	6486	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	16:00:00	15:30:00	6487	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	16:00:00	15:30:00	6488	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-10	19:00:00	18:30:00	6489	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-06	19:00:00	18:30:00	6490	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	11:30:00	11:00:00	6491	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-11	19:00:00	18:30:00	6492	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-11	14:00:00	13:30:00	6494	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-15	14:00:00	13:30:00	6496	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	11:30:00	11:00:00	6497	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	11:00:00	10:30:00	6498	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-06	11:00:00	10:30:00	6499	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-10	14:00:00	13:30:00	6501	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	11:30:00	11:00:00	6503	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	11:30:00	11:00:00	6504	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	23:00:00	22:30:00	6506	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-08	19:30:00	19:00:00	6507	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	19:30:00	19:00:00	6509	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	14:30:00	14:00:00	6510	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	14:30:00	14:00:00	6511	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	18:30:00	18:00:00	6513	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	19:30:00	19:00:00	6515	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	14:30:00	14:00:00	6516	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	18:30:00	18:00:00	6517	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	14:30:00	14:00:00	6518	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	23:00:00	22:30:00	6519	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	18:30:00	18:00:00	6520	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	14:30:00	14:00:00	6521	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	18:30:00	18:00:00	6522	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	14:30:00	14:00:00	6523	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	14:30:00	14:00:00	6524	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	18:30:00	18:00:00	6525	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	17:00:00	16:30:00	6526	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	17:00:00	16:30:00	6527	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	22:00:00	21:30:00	6528	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	22:00:00	21:30:00	6529	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	22:00:00	21:30:00	6530	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	11:00:00	10:30:00	6531	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	17:00:00	16:30:00	6532	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	11:00:00	10:30:00	6533	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	17:00:00	16:30:00	6534	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	22:00:00	21:30:00	6535	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	11:00:00	10:30:00	6536	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	11:00:00	10:30:00	6537	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-18	17:00:00	16:30:00	6539	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	13:30:00	13:00:00	6540	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	13:30:00	13:00:00	6541	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	12:00:00	11:30:00	6542	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	13:30:00	13:00:00	6543	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	09:30:00	09:00:00	6545	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	19:30:00	19:00:00	6546	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-06	12:00:00	11:30:00	6547	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-25	09:30:00	09:00:00	6551	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	19:30:00	19:00:00	6552	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-23	09:30:00	09:00:00	6553	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-22	19:30:00	19:00:00	6554	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-19	19:30:00	19:00:00	6555	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-17	13:30:00	13:00:00	6556	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-16	13:30:00	13:00:00	6557	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-30	19:30:00	19:00:00	6558	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	09:30:00	09:00:00	6559	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-28	09:30:00	09:00:00	6560	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-14	20:30:00	20:00:00	6561	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-08	17:00:00	16:30:00	6564	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-05	17:00:00	16:30:00	6566	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-29	23:30:00	23:00:00	6568	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-07-04	22:00:00	21:30:00	6569	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-15	15:50:00	15:20:00	6571	01977e7a-00eb-72f3-8dd8-014dcf711be4
2025-06-24	16:30:00	16:00:00	6572	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	08:30:00	08:00:00	6573	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	08:30:00	08:00:00	6574	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	16:30:00	16:00:00	6575	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	16:30:00	16:00:00	6578	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	12:30:00	12:00:00	6579	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	18:00:00	17:30:00	6580	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	21:30:00	21:00:00	6581	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	12:30:00	12:00:00	6583	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	16:30:00	16:00:00	6584	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	12:00:00	11:30:00	6585	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	21:30:00	21:00:00	6586	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	21:30:00	21:00:00	6588	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	18:00:00	17:30:00	6589	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	16:00:00	15:30:00	6590	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	21:30:00	21:00:00	6591	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	18:00:00	17:30:00	6592	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-05	13:30:00	13:00:00	6593	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	08:30:00	08:00:00	6594	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	08:30:00	08:00:00	6596	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	13:30:00	13:00:00	6597	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	08:30:00	08:00:00	6598	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	12:00:00	11:30:00	6599	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	08:30:00	08:00:00	6601	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	16:30:00	16:00:00	6602	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	08:30:00	08:00:00	6604	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	08:30:00	08:00:00	6605	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	12:00:00	11:30:00	6608	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	08:30:00	08:00:00	6609	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-13	13:00:00	12:30:00	6610	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	20:30:00	20:00:00	6611	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	19:00:00	18:30:00	6612	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	00:00:00	23:30:00	6613	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	21:00:00	20:30:00	6615	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	19:00:00	18:30:00	6616	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	20:30:00	20:00:00	6617	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	19:00:00	18:30:00	6618	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	19:00:00	18:30:00	6620	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	20:00:00	19:30:00	6621	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	20:30:00	20:00:00	6622	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	20:30:00	20:00:00	6623	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	21:00:00	20:30:00	6624	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	20:30:00	20:00:00	6625	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	17:30:00	17:00:00	6628	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	16:00:00	15:30:00	6629	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	18:30:00	18:00:00	6631	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	17:30:00	17:00:00	6632	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	16:00:00	15:30:00	6633	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	17:30:00	17:00:00	6634	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-05	08:30:00	08:00:00	6638	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	15:30:00	15:00:00	6639	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-13	18:30:00	18:00:00	6640	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	17:30:00	17:00:00	6641	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	08:30:00	08:00:00	6642	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	15:30:00	15:00:00	6645	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	23:00:00	22:30:00	6646	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	15:30:00	15:00:00	6647	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	15:30:00	15:00:00	6648	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	17:30:00	17:00:00	6649	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	16:00:00	15:30:00	6650	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-05	19:00:00	18:30:00	6651	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	13:00:00	12:30:00	6652	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	13:00:00	12:30:00	6653	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	11:30:00	11:00:00	6654	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	11:30:00	11:00:00	6656	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-13	14:00:00	13:30:00	6657	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	11:30:00	11:00:00	6658	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	13:00:00	12:30:00	6659	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-15	11:00:00	10:30:00	6661	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	13:00:00	12:30:00	6663	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-05	21:00:00	20:30:00	6664	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	11:30:00	11:00:00	6665	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	11:00:00	10:30:00	6666	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	13:00:00	12:30:00	6667	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	13:00:00	12:30:00	6668	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	13:00:00	12:30:00	6671	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-08	11:00:00	10:30:00	6672	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-09	11:00:00	10:30:00	6673	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	11:30:00	11:00:00	6674	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	13:00:00	12:30:00	6675	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	11:30:00	11:00:00	6677	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-09	19:30:00	19:00:00	6678	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	15:00:00	14:30:00	6679	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-13	15:30:00	15:00:00	6683	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	10:30:00	10:00:00	6684	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	14:30:00	14:00:00	6685	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	15:00:00	14:30:00	6686	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	18:30:00	18:00:00	6687	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	15:00:00	14:30:00	6688	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	14:30:00	14:00:00	6689	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	14:30:00	14:00:00	6690	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	14:30:00	14:00:00	6691	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	14:30:00	14:00:00	6692	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	18:30:00	18:00:00	6693	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	18:30:00	18:00:00	6694	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	18:30:00	18:00:00	6695	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	14:30:00	14:00:00	6696	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	18:30:00	18:00:00	6697	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	14:00:00	13:30:00	6699	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	22:30:00	22:00:00	6700	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	14:00:00	13:30:00	6701	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-09	10:00:00	09:30:00	6702	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	11:00:00	10:30:00	6703	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	14:00:00	13:30:00	6704	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	17:00:00	16:30:00	6707	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	11:00:00	10:30:00	6708	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	17:00:00	16:30:00	6709	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	11:00:00	10:30:00	6710	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	22:30:00	22:00:00	6711	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	11:00:00	10:30:00	6712	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	22:30:00	22:00:00	6713	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	22:30:00	22:00:00	6714	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-09	18:00:00	17:30:00	6716	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-13	12:00:00	11:30:00	6717	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-15	12:00:00	11:30:00	6719	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-05	18:00:00	17:30:00	6722	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	12:00:00	11:30:00	6724	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	19:30:00	19:00:00	6725	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	09:30:00	09:00:00	6726	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	13:30:00	13:00:00	6730	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	01:00:00	00:30:00	6732	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	09:30:00	09:00:00	6733	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-08	16:30:00	16:00:00	6734	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	19:30:00	19:00:00	6736	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-21	09:30:00	09:00:00	6737	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	19:30:00	19:00:00	6738	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-05	16:30:00	16:00:00	6739	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-09	15:00:00	14:30:00	6742	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	19:30:00	19:00:00	6743	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	19:30:00	19:00:00	6745	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-27	09:30:00	09:00:00	6746	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-25	20:00:00	19:30:00	6747	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-18	10:00:00	09:30:00	6748	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-15	20:30:00	20:00:00	6749	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-04	00:30:00	00:00:00	6750	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	20:00:00	19:30:00	6752	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-30	10:00:00	09:30:00	6754	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-28	10:00:00	09:30:00	6755	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-07-15	17:00:00	16:30:00	6756	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-24	10:00:00	09:30:00	6758	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-16	20:00:00	19:30:00	6759	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-20	10:00:00	09:30:00	6760	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-19	10:00:00	09:30:00	6761	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-08	09:10:00	08:40:00	6762	01977e7a-014a-74b6-81f5-7e8e435a3d75
2025-06-23	16:30:00	16:00:00	6763	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	12:30:00	12:00:00	6764	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	12:30:00	12:00:00	6765	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	08:30:00	08:00:00	6766	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	12:00:00	11:30:00	6768	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	09:30:00	09:00:00	6770	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	18:00:00	17:30:00	6771	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-14	09:30:00	09:00:00	6772	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	09:30:00	09:00:00	6773	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	12:30:00	12:00:00	6774	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	16:30:00	16:00:00	6775	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	12:30:00	12:00:00	6776	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	18:00:00	17:30:00	6777	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	21:30:00	21:00:00	6778	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	18:00:00	17:30:00	6779	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	00:00:00	23:30:00	6780	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	18:00:00	17:30:00	6781	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	17:30:00	17:00:00	6782	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	08:30:00	08:00:00	6783	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	08:30:00	08:00:00	6784	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-06	13:30:00	13:00:00	6785	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	13:30:00	13:00:00	6786	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	08:30:00	08:00:00	6787	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	17:30:00	17:00:00	6788	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	08:30:00	08:00:00	6790	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	08:30:00	08:00:00	6791	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	13:30:00	13:00:00	6792	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	08:30:00	08:00:00	6793	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	13:30:00	13:00:00	6794	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	17:30:00	17:00:00	6796	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	18:00:00	17:30:00	6798	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	12:00:00	11:30:00	6799	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	00:00:00	23:30:00	6800	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	19:00:00	18:30:00	6802	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	20:30:00	20:00:00	6803	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	20:30:00	20:00:00	6804	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	19:00:00	18:30:00	6805	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	19:00:00	18:30:00	6806	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	19:00:00	18:30:00	6808	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	20:30:00	20:00:00	6809	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	21:00:00	20:30:00	6810	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	20:30:00	20:00:00	6811	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	19:00:00	18:30:00	6812	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	17:30:00	17:00:00	6813	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	08:30:00	08:00:00	6814	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	16:00:00	15:30:00	6815	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	08:30:00	08:00:00	6816	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	17:30:00	17:00:00	6817	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-14	08:30:00	08:00:00	6818	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	16:00:00	15:30:00	6819	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	11:00:00	10:30:00	6820	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	16:00:00	15:30:00	6821	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	18:30:00	18:00:00	6822	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	16:00:00	15:30:00	6824	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-06	08:30:00	08:00:00	6825	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	08:30:00	08:00:00	6826	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-07	12:30:00	12:00:00	6828	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	23:00:00	22:30:00	6829	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	15:30:00	15:00:00	6830	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	12:30:00	12:00:00	6831	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-06	23:00:00	22:30:00	6832	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	16:00:00	15:30:00	6834	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	19:00:00	18:30:00	6837	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	13:00:00	12:30:00	6838	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	22:30:00	22:00:00	6839	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	16:00:00	15:30:00	6841	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	11:30:00	11:00:00	6843	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	11:30:00	11:00:00	6845	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	11:30:00	11:00:00	6846	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	11:00:00	10:30:00	6847	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	21:00:00	20:30:00	6848	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	13:00:00	12:30:00	6849	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	13:00:00	12:30:00	6851	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-07	14:00:00	13:30:00	6852	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	11:30:00	11:00:00	6853	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	11:30:00	11:00:00	6854	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	15:00:00	14:30:00	6855	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	15:00:00	14:30:00	6856	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-04	19:30:00	19:00:00	6859	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-07	15:30:00	15:00:00	6860	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	10:30:00	10:00:00	6861	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	19:30:00	19:00:00	6862	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	14:30:00	14:00:00	6863	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	15:00:00	14:30:00	6864	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	15:00:00	14:30:00	6865	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	15:00:00	14:30:00	6866	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	10:30:00	10:00:00	6867	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	14:30:00	14:00:00	6868	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	10:30:00	10:00:00	6869	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	14:30:00	14:00:00	6870	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	10:30:00	10:00:00	6871	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	18:30:00	18:00:00	6872	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	14:00:00	13:30:00	6874	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	14:00:00	13:30:00	6875	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	17:00:00	16:30:00	6876	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	17:00:00	16:30:00	6877	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	10:00:00	09:30:00	6878	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	22:00:00	21:30:00	6879	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-04	11:30:00	11:00:00	6880	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	22:00:00	21:30:00	6881	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	22:30:00	22:00:00	6882	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	22:00:00	21:30:00	6884	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	14:00:00	13:30:00	6886	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	14:00:00	13:30:00	6887	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	22:00:00	21:30:00	6888	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	17:00:00	16:30:00	6889	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	17:00:00	16:30:00	6891	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	13:30:00	13:00:00	6893	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	13:30:00	13:00:00	6894	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-04	10:30:00	10:00:00	6895	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-04	18:00:00	17:30:00	6896	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-06	21:30:00	21:00:00	6897	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-06	10:30:00	10:00:00	6899	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-03	12:00:00	11:30:00	6901	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	09:30:00	09:00:00	6902	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-16	19:30:00	19:00:00	6903	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-14	10:30:00	10:00:00	6905	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	09:30:00	09:00:00	6908	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-14	15:00:00	14:30:00	6909	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	19:30:00	19:00:00	6911	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-23	09:30:00	09:00:00	6912	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	16:30:00	16:00:00	6915	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-30	09:30:00	09:00:00	6917	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	19:30:00	19:00:00	6918	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	16:30:00	16:00:00	6919	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-14	16:30:00	16:00:00	6920	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-14	20:30:00	20:00:00	6921	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	20:30:00	20:00:00	6922	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	20:00:00	19:30:00	6923	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-15	20:30:00	20:00:00	6925	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-06	20:30:00	20:00:00	6926	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-07	17:00:00	16:30:00	6929	01977e7a-01ab-77da-bcb4-15df70386907
2025-07-13	22:00:00	21:30:00	6930	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-29	10:00:00	09:30:00	6932	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-25	10:00:00	09:30:00	6933	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-24	10:00:00	09:30:00	6934	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-11	11:50:00	11:20:00	6936	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-20	12:00:00	11:30:00	6952	01977e7a-01ab-77da-bcb4-15df70386907
2025-06-26	08:30:00	08:00:00	6953	01977e79-f272-7f27-bed4-a81b480f17fd
2025-06-19	14:00:00	13:30:00	7002	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	14:00:00	13:30:00	7003	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	11:00:00	10:30:00	7004	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-19	10:00:00	09:30:00	7052	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-09	12:00:00	11:30:00	7053	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	17:00:00	16:30:00	7054	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-07-04	16:00:00	15:30:00	7102	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-19	15:00:00	14:30:00	7103	01977e79-fca6-7b48-a3a4-2c362c16081c
2025-06-19	08:30:00	08:00:00	7104	01977e79-f2d6-7075-a662-c4d24ed8bb3e
2025-06-26	14:00:00	13:30:00	7152	01977e79-f849-7f1a-a0c1-70bc3d7dae0c
2025-06-19	10:00:00	09:30:00	7153	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-03	09:30:00	09:00:00	7154	01977e79-fc3d-7e26-94be-b6531796eb62
2025-06-19	08:30:00	08:00:00	7155	01977e7a-008b-7cb4-b539-be89d4f12cfc
2025-06-23	08:30:00	08:00:00	7203	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-28	17:00:00	16:30:00	7204	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-09	16:00:00	15:30:00	7205	01977e79-f59f-7958-879a-20e6a5dbfbae
2025-06-24	14:00:00	13:30:00	7207	01977e79-fd05-7a4c-9854-1f283f24c417
2025-06-24	08:30:00	08:00:00	7252	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-06-26	14:30:00	14:00:00	7253	01977e79-f7e9-7b37-ac67-06a8797bd7d4
2025-07-06	13:30:00	13:00:00	7304	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	15:00:00	14:30:00	7306	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-06-27	19:30:00	19:00:00	7307	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-05	10:30:00	10:00:00	7308	01977e79-fe99-7c06-9eaf-c6db0f1ddd60
2025-07-04	09:30:00	09:00:00	7352	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-03	11:30:00	11:00:00	7402	01977e79-f603-7282-bc16-c98712d0e44d
2025-07-03	12:30:00	12:00:00	7403	01977e79-f6c6-7707-aedb-fe3468f41140
2025-07-04	12:00:00	11:30:00	7404	01977e79-f336-722a-8a36-233ae9fe22f7
2025-07-03	10:30:00	10:00:00	7405	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-02	08:30:00	08:00:00	7452	01977e79-f272-7f27-bed4-a81b480f17fd
2025-07-09	08:45:00	08:00:00	7502	01977ecf-4f42-72b5-8a44-701a5fdb4a9e
2025-06-19	11:30:00	11:00:00	7902	01977e79-f469-7573-ba3a-604cd272778f
2025-06-19	10:00:00	09:30:00	7952	01977e79-f975-78ec-81e9-6e13cd6d568a
2025-07-04	14:30:00	14:00:00	7953	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-25	12:30:00	11:45:00	8002	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-25	13:15:00	12:30:00	8003	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-25	11:45:00	11:00:00	8004	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-15	16:00:00	15:30:00	8007	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-03	13:00:00	12:30:00	8052	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-25	11:00:00	10:15:00	8102	01977e79-fc3d-7e26-94be-b6531796eb62
2025-07-09	08:30:00	08:00:00	8152	01977e79-f469-7573-ba3a-604cd272778f
\.


--
-- Name: a_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.a_seq', 8, true);


--
-- Name: app_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.app_seq', 2401, true);


--
-- Name: appointment_duration_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.appointment_duration_seq', 51, true);


--
-- Name: cont_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.cont_seq', 221, true);


--
-- Name: cont_t_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.cont_t_seq', 2, true);


--
-- Name: continue_flags_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.continue_flags_id_seq', 3, true);


--
-- Name: detail_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.detail_seq', 201, true);


--
-- Name: doc_spec_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.doc_spec_seq', 41, true);


--
-- Name: jur_spec_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.jur_spec_seq', 21, true);


--
-- Name: jur_type_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.jur_type_seq', 21, true);


--
-- Name: profile_status_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.profile_status_id_seq', 2, true);


--
-- Name: task_journal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.task_journal_id_seq', 1, false);


--
-- Name: task_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.task_types_id_seq', 3, true);


--
-- Name: w_i_seq; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.w_i_seq', 8251, true);


--
-- Name: appointment_durations appointment_durations_owner_id_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointment_durations
    ADD CONSTRAINT appointment_durations_owner_id_key UNIQUE (owner_id);


--
-- Name: appointment_durations appointment_durations_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointment_durations
    ADD CONSTRAINT appointment_durations_pkey PRIMARY KEY (id);


--
-- Name: appointments appointments_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);


--
-- Name: appointments appointments_status_check; Type: CHECK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE public.appointments
    ADD CONSTRAINT appointments_status_check CHECK (((status >= 0) AND (status <= 3))) NOT VALID;


--
-- Name: authorities authorities_authority_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT authorities_authority_key UNIQUE (authority);


--
-- Name: authorities authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT authorities_pkey PRIMARY KEY (id);


--
-- Name: avatars avatars_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.avatars
    ADD CONSTRAINT avatars_pkey PRIMARY KEY (id);


--
-- Name: contact_progreses contact_progreses_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.contact_progreses
    ADD CONSTRAINT contact_progreses_pkey PRIMARY KEY (user_id);


--
-- Name: contact_types contact_types_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.contact_types
    ADD CONSTRAINT contact_types_pkey PRIMARY KEY (id);


--
-- Name: contact_types contact_types_type_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.contact_types
    ADD CONSTRAINT contact_types_type_key UNIQUE (type);


--
-- Name: contacts contacts_contact_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_contact_key UNIQUE (contact);


--
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- Name: continue_flags continue_flags_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.continue_flags
    ADD CONSTRAINT continue_flags_pkey PRIMARY KEY (id);


--
-- Name: continue_flags continue_flags_task_type_code_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.continue_flags
    ADD CONSTRAINT continue_flags_task_type_code_key UNIQUE (task_type_code);


--
-- Name: customers customers_detail_id_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_detail_id_key UNIQUE (detail_id);


--
-- Name: customers customers_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pkey PRIMARY KEY (id);


--
-- Name: deactivated_auth_token deactivated_auth_token_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.deactivated_auth_token
    ADD CONSTRAINT deactivated_auth_token_pkey PRIMARY KEY (id);


--
-- Name: doctor_specializations doctor_specializations_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_specializations
    ADD CONSTRAINT doctor_specializations_pkey PRIMARY KEY (id);


--
-- Name: doctor_specializations doctor_specializations_specialization_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_specializations
    ADD CONSTRAINT doctor_specializations_specialization_key UNIQUE (specialization);


--
-- Name: doctors doctors_detail_id_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_detail_id_key UNIQUE (detail_id);


--
-- Name: doctors doctors_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (id);


--
-- Name: jurist_specializations jurist_specializations_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurist_specializations
    ADD CONSTRAINT jurist_specializations_pkey PRIMARY KEY (id);


--
-- Name: jurist_specializations jurist_specializations_specialization_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurist_specializations
    ADD CONSTRAINT jurist_specializations_specialization_key UNIQUE (specialization);


--
-- Name: jurist_types jurist_types_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurist_types
    ADD CONSTRAINT jurist_types_pkey PRIMARY KEY (id);


--
-- Name: jurist_types jurist_types_type_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurist_types
    ADD CONSTRAINT jurist_types_type_key UNIQUE (type);


--
-- Name: jurists jurists_detail_id_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurists
    ADD CONSTRAINT jurists_detail_id_key UNIQUE (detail_id);


--
-- Name: jurists jurists_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurists
    ADD CONSTRAINT jurists_pkey PRIMARY KEY (id);


--
-- Name: profile_status profile_status_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.profile_status
    ADD CONSTRAINT profile_status_pkey PRIMARY KEY (id);


--
-- Name: profile_status profile_status_status_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.profile_status
    ADD CONSTRAINT profile_status_status_key UNIQUE (status);


--
-- Name: schedule_progreses schedule_progreses_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.schedule_progreses
    ADD CONSTRAINT schedule_progreses_pkey PRIMARY KEY (user_id);


--
-- Name: services services_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);


--
-- Name: task_journal task_journal_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.task_journal
    ADD CONSTRAINT task_journal_pkey PRIMARY KEY (id);


--
-- Name: task_types task_types_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.task_types
    ADD CONSTRAINT task_types_pkey PRIMARY KEY (id);


--
-- Name: user_details user_details_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_details
    ADD CONSTRAINT user_details_pkey PRIMARY KEY (id);


--
-- Name: user_details user_details_user_id_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_details
    ADD CONSTRAINT user_details_user_id_key UNIQUE (user_id);


--
-- Name: users users_email_id_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_id_key UNIQUE (email_id);


--
-- Name: users users_email_key; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: work_intervals work_intervals_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.work_intervals
    ADD CONSTRAINT work_intervals_pkey PRIMARY KEY (id);


--
-- Name: idx_appointments_cid_date_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_cid_date_status ON public.appointments USING btree (customer_id, date, status);


--
-- Name: idx_appointments_date_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_date_status ON public.appointments USING btree (date, status);


--
-- Name: idx_appointments_status_cid; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_status_cid ON public.appointments USING btree (status, customer_id);


--
-- Name: idx_appointments_status_vid; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_status_vid ON public.appointments USING btree (status, volunteer_id);


--
-- Name: idx_appointments_vid_cid; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_vid_cid ON public.appointments USING btree (volunteer_id, customer_id);


--
-- Name: idx_appointments_vid_date; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_vid_date ON public.appointments USING btree (volunteer_id, date);


--
-- Name: idx_appointments_vid_date_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_appointments_vid_date_status ON public.appointments USING btree (volunteer_id, date, status);


--
-- Name: idx_avatars_user_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_avatars_user_id ON public.avatars USING btree (user_id);


--
-- Name: idx_contacts_contact; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_contacts_contact ON public.contacts USING btree (contact);


--
-- Name: idx_contacts_owner_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_contacts_owner_id ON public.contacts USING btree (owner_id);


--
-- Name: idx_customers_lower_first_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_customers_lower_first_name ON public.customers USING btree (lower((first_name)::text));


--
-- Name: idx_customers_lower_last_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_customers_lower_last_name ON public.customers USING btree (lower((last_name)::text));


--
-- Name: idx_customers_lower_second_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_customers_lower_second_name ON public.customers USING btree (lower((second_name)::text));


--
-- Name: idx_doctor_specialization_relations_doctor_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctor_specialization_relations_doctor_id ON public.doctor_specialization_relations USING btree (doctor_id);


--
-- Name: idx_doctor_specialization_relations_specialization_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctor_specialization_relations_specialization_id ON public.doctor_specialization_relations USING btree (specialization_id);


--
-- Name: idx_doctors_approved_profile_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctors_approved_profile_status ON public.doctors USING btree (is_approved, profile_status_id);


--
-- Name: idx_doctors_gender_approved_profile_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctors_gender_approved_profile_status ON public.doctors USING btree (gender, is_approved, profile_status_id);


--
-- Name: idx_doctors_lower_first_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctors_lower_first_name ON public.doctors USING btree (lower((first_name)::text));


--
-- Name: idx_doctors_lower_last_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctors_lower_last_name ON public.doctors USING btree (lower((last_name)::text));


--
-- Name: idx_doctors_lower_second_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_doctors_lower_second_name ON public.doctors USING btree (lower((second_name)::text));


--
-- Name: idx_intervals_oid_date; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_intervals_oid_date ON public.work_intervals USING btree (owner_id, date);


--
-- Name: idx_intervals_oid_date_start_t; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_intervals_oid_date_start_t ON public.work_intervals USING btree (owner_id, date, start_t);


--
-- Name: idx_jurist_specialization_relations_doctor_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurist_specialization_relations_doctor_id ON public.jurist_specialization_relations USING btree (jurist_id);


--
-- Name: idx_jurist_specialization_relations_specialization_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurist_specialization_relations_specialization_id ON public.jurist_specialization_relations USING btree (specialization_id);


--
-- Name: idx_jurists_approved_profile_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurists_approved_profile_status ON public.jurists USING btree (is_approved, profile_status_id);


--
-- Name: idx_jurists_gender_approved_profile_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurists_gender_approved_profile_status ON public.jurists USING btree (gender, is_approved, profile_status_id);


--
-- Name: idx_jurists_lower_first_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurists_lower_first_name ON public.jurists USING btree (lower((first_name)::text));


--
-- Name: idx_jurists_lower_last_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurists_lower_last_name ON public.jurists USING btree (lower((last_name)::text));


--
-- Name: idx_jurists_lower_second_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurists_lower_second_name ON public.jurists USING btree (lower((second_name)::text));


--
-- Name: idx_jurists_type_id_approved_profile_status; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_jurists_type_id_approved_profile_status ON public.jurists USING btree (type_id, is_approved, profile_status_id);


--
-- Name: idx_users_email; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_users_email ON public.users USING btree (email);


--
-- Name: doctors fk1thussfqouxmwshmlk7i6809p; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT fk1thussfqouxmwshmlk7i6809p FOREIGN KEY (detail_id) REFERENCES public.user_details(id);


--
-- Name: user_authority fk2gr3e45mhpge4rwjtqyex0rwu; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fk2gr3e45mhpge4rwjtqyex0rwu FOREIGN KEY (authrity_id) REFERENCES public.authorities(id);


--
-- Name: customers fk4q9baxjcfu7wqvyg8tq2yxwjv; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT fk4q9baxjcfu7wqvyg8tq2yxwjv FOREIGN KEY (profile_status_id) REFERENCES public.profile_status(id);


--
-- Name: jurist_specialization_relations fkf5rbup3i9q2gv25ji9bvjrnyu; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurist_specialization_relations
    ADD CONSTRAINT fkf5rbup3i9q2gv25ji9bvjrnyu FOREIGN KEY (jurist_id) REFERENCES public.jurists(id);


--
-- Name: contacts fkg2u9rjhg5ampj8fomxj1q9y8x; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT fkg2u9rjhg5ampj8fomxj1q9y8x FOREIGN KEY (type_id) REFERENCES public.contact_types(id);


--
-- Name: jurists fkgnuubt453pr2bd7056ikkt284; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurists
    ADD CONSTRAINT fkgnuubt453pr2bd7056ikkt284 FOREIGN KEY (detail_id) REFERENCES public.user_details(id);


--
-- Name: user_authority fkhi46vu7680y1hwvmnnuh4cybx; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fkhi46vu7680y1hwvmnnuh4cybx FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: jurist_specialization_relations fkjmaisrpd8a3b7538i2yfohm6j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurist_specialization_relations
    ADD CONSTRAINT fkjmaisrpd8a3b7538i2yfohm6j FOREIGN KEY (specialization_id) REFERENCES public.jurist_specializations(id);


--
-- Name: jurists fkqfv2x37vvbhls9a7tjeow17fn; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurists
    ADD CONSTRAINT fkqfv2x37vvbhls9a7tjeow17fn FOREIGN KEY (profile_status_id) REFERENCES public.profile_status(id);


--
-- Name: doctor_specialization_relations fkqvgyv7xdyps12v2wt34x8256p; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_specialization_relations
    ADD CONSTRAINT fkqvgyv7xdyps12v2wt34x8256p FOREIGN KEY (specialization_id) REFERENCES public.doctor_specializations(id);


--
-- Name: doctor_specialization_relations fkrr6e7gnxhlnjtjn1iwi21v0qi; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctor_specialization_relations
    ADD CONSTRAINT fkrr6e7gnxhlnjtjn1iwi21v0qi FOREIGN KEY (doctor_id) REFERENCES public.doctors(id);


--
-- Name: jurists fkscrbcoft4hpoeb33gw39hctwk; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.jurists
    ADD CONSTRAINT fkscrbcoft4hpoeb33gw39hctwk FOREIGN KEY (type_id) REFERENCES public.jurist_types(id);


--
-- Name: doctors fkt1f006hmqymvmkjwj7cjxbgr8; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT fkt1f006hmqymvmkjwj7cjxbgr8 FOREIGN KEY (profile_status_id) REFERENCES public.profile_status(id);


--
-- Name: customers fktjdeeb5ywj8i2e40jcqw56leh; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT fktjdeeb5ywj8i2e40jcqw56leh FOREIGN KEY (detail_id) REFERENCES public.user_details(id);


--
-- PostgreSQL database dump complete
--

