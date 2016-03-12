--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.3
-- Dumped by pg_dump version 9.3.3
-- Started on 2016-03-12 17:23:53

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 184 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2022 (class 0 OID 0)
-- Dependencies: 184
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 182 (class 1259 OID 24716)
-- Name: option; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE option (
    id bigint NOT NULL,
    content character varying NOT NULL,
    isright boolean DEFAULT false NOT NULL,
    questionid bigint
);


ALTER TABLE public.option OWNER TO postgres;

--
-- TOC entry 173 (class 1259 OID 16864)
-- Name: option_id_cnt; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE option_id_cnt
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.option_id_cnt OWNER TO postgres;

--
-- TOC entry 181 (class 1259 OID 24714)
-- Name: option_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE option_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.option_id_seq OWNER TO postgres;

--
-- TOC entry 2023 (class 0 OID 0)
-- Dependencies: 181
-- Name: option_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE option_id_seq OWNED BY option.id;


--
-- TOC entry 175 (class 1259 OID 24671)
-- Name: poll; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE poll (
    id bigint NOT NULL,
    name character varying NOT NULL,
    description character varying
);


ALTER TABLE public.poll OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 16819)
-- Name: poll_id_cnt; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE poll_id_cnt
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.poll_id_cnt OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 24669)
-- Name: poll_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE poll_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.poll_id_seq OWNER TO postgres;

--
-- TOC entry 2024 (class 0 OID 0)
-- Dependencies: 174
-- Name: poll_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE poll_id_seq OWNED BY poll.id;


--
-- TOC entry 178 (class 1259 OID 24695)
-- Name: poll_respondent; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE poll_respondent (
    pollid bigint NOT NULL,
    respondentid bigint NOT NULL
);


ALTER TABLE public.poll_respondent OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 24702)
-- Name: question; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE question (
    id bigint NOT NULL,
    name character varying NOT NULL,
    pollid bigint
);


ALTER TABLE public.question OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 16850)
-- Name: question_id_cnt; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE question_id_cnt
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.question_id_cnt OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 24700)
-- Name: question_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE question_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.question_id_seq OWNER TO postgres;

--
-- TOC entry 2025 (class 0 OID 0)
-- Dependencies: 179
-- Name: question_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE question_id_seq OWNED BY question.id;


--
-- TOC entry 177 (class 1259 OID 24684)
-- Name: respondent; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE respondent (
    id bigint NOT NULL,
    name character varying NOT NULL,
    email character varying NOT NULL
);


ALTER TABLE public.respondent OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 16832)
-- Name: respondent_id_cnt; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE respondent_id_cnt
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.respondent_id_cnt OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 24682)
-- Name: respondent_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE respondent_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.respondent_id_seq OWNER TO postgres;

--
-- TOC entry 2026 (class 0 OID 0)
-- Dependencies: 176
-- Name: respondent_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE respondent_id_seq OWNED BY respondent.id;


--
-- TOC entry 183 (class 1259 OID 24729)
-- Name: respondent_option; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE respondent_option (
    respondentid bigint NOT NULL,
    optionid bigint NOT NULL
);


ALTER TABLE public.respondent_option OWNER TO postgres;

--
-- TOC entry 1864 (class 2604 OID 24719)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY option ALTER COLUMN id SET DEFAULT nextval('option_id_seq'::regclass);


--
-- TOC entry 1861 (class 2604 OID 24674)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll ALTER COLUMN id SET DEFAULT nextval('poll_id_seq'::regclass);


--
-- TOC entry 1863 (class 2604 OID 24705)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question ALTER COLUMN id SET DEFAULT nextval('question_id_seq'::regclass);


--
-- TOC entry 1862 (class 2604 OID 24687)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY respondent ALTER COLUMN id SET DEFAULT nextval('respondent_id_seq'::regclass);


--
-- TOC entry 2013 (class 0 OID 24716)
-- Dependencies: 182
-- Data for Name: option; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO option VALUES (19, 'Soft rock', false, 9);
INSERT INTO option VALUES (20, 'Sympho-power metal', true, 9);
INSERT INTO option VALUES (21, 'Melodic death metal', false, 9);
INSERT INTO option VALUES (22, 'Post grunge', true, 10);
INSERT INTO option VALUES (23, 'Punk rock', false, 10);
INSERT INTO option VALUES (24, 'Thrash metal', false, 10);
INSERT INTO option VALUES (25, 'Power metal', false, 11);
INSERT INTO option VALUES (26, 'Postpunk revival', false, 11);
INSERT INTO option VALUES (27, 'Pop punk / skate punk', true, 11);
INSERT INTO option VALUES (28, 'Surf rock', false, 11);
INSERT INTO option VALUES (29, 'Pop rock', false, 12);
INSERT INTO option VALUES (30, 'Alternative', true, 12);
INSERT INTO option VALUES (31, 'Punk rock', false, 12);
INSERT INTO option VALUES (43, 'should', false, 14);
INSERT INTO option VALUES (1, 'Paris', false, 2);
INSERT INTO option VALUES (2, 'London', true, 2);
INSERT INTO option VALUES (3, 'Voronezh', false, 2);
INSERT INTO option VALUES (35, 'might', false, 14);
INSERT INTO option VALUES (36, 'must', true, 14);
INSERT INTO option VALUES (42, 'can''t', false, 14);


--
-- TOC entry 2027 (class 0 OID 0)
-- Dependencies: 173
-- Name: option_id_cnt; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('option_id_cnt', 1, false);


--
-- TOC entry 2028 (class 0 OID 0)
-- Dependencies: 181
-- Name: option_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('option_id_seq', 43, true);


--
-- TOC entry 2006 (class 0 OID 24671)
-- Dependencies: 175
-- Data for Name: poll; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO poll VALUES (12, 'Intermediate English', 'Тест, позволяющий проверить, владеете ли вы английским языком на уровне Intermediate');
INSERT INTO poll VALUES (22, 'Жанры рока', 'Знаете ли вы жанры следующих исполнителей?');


--
-- TOC entry 2029 (class 0 OID 0)
-- Dependencies: 170
-- Name: poll_id_cnt; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('poll_id_cnt', 8, true);


--
-- TOC entry 2030 (class 0 OID 0)
-- Dependencies: 174
-- Name: poll_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('poll_id_seq', 23, true);


--
-- TOC entry 2009 (class 0 OID 24695)
-- Dependencies: 178
-- Data for Name: poll_respondent; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO poll_respondent VALUES (12, 63);
INSERT INTO poll_respondent VALUES (12, 64);


--
-- TOC entry 2011 (class 0 OID 24702)
-- Dependencies: 180
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO question VALUES (2, 'What city is the capital of Great Britain?', 12);
INSERT INTO question VALUES (14, 'She has worked for a month without a day off — she ________be exhausted', 12);
INSERT INTO question VALUES (9, 'Nightwish', 22);
INSERT INTO question VALUES (10, 'Breaking Benjamin', 22);
INSERT INTO question VALUES (11, 'Sum 41', 22);
INSERT INTO question VALUES (12, 'The Pretty Reckless', 22);


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 172
-- Name: question_id_cnt; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('question_id_cnt', 1, false);


--
-- TOC entry 2032 (class 0 OID 0)
-- Dependencies: 179
-- Name: question_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('question_id_seq', 19, true);


--
-- TOC entry 2008 (class 0 OID 24684)
-- Dependencies: 177
-- Data for Name: respondent; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO respondent VALUES (62, 'Mr.Ebola', 'ebola@ebola.net');
INSERT INTO respondent VALUES (63, 'Deep Blues', 'qwqr@ao.no');
INSERT INTO respondent VALUES (64, 'The Greatest User In The World', 'greatest@gmail.com');
INSERT INTO respondent VALUES (65, 'Aquarius', 'kop@ags.net');


--
-- TOC entry 2033 (class 0 OID 0)
-- Dependencies: 171
-- Name: respondent_id_cnt; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('respondent_id_cnt', 1, false);


--
-- TOC entry 2034 (class 0 OID 0)
-- Dependencies: 176
-- Name: respondent_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('respondent_id_seq', 65, true);


--
-- TOC entry 2014 (class 0 OID 24729)
-- Dependencies: 183
-- Data for Name: respondent_option; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO respondent_option VALUES (63, 3);
INSERT INTO respondent_option VALUES (63, 35);
INSERT INTO respondent_option VALUES (64, 2);
INSERT INTO respondent_option VALUES (64, 35);


--
-- TOC entry 1878 (class 2606 OID 24713)
-- Name: id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT id UNIQUE (id);


--
-- TOC entry 1883 (class 2606 OID 24728)
-- Name: option_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY option
    ADD CONSTRAINT option_id UNIQUE (id);


--
-- TOC entry 1885 (class 2606 OID 24726)
-- Name: pk_option_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY option
    ADD CONSTRAINT pk_option_id PRIMARY KEY (id);


--
-- TOC entry 1867 (class 2606 OID 24679)
-- Name: pk_poll_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT pk_poll_id PRIMARY KEY (id);


--
-- TOC entry 1875 (class 2606 OID 24699)
-- Name: pk_poll_respondent; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY poll_respondent
    ADD CONSTRAINT pk_poll_respondent PRIMARY KEY (pollid, respondentid);


--
-- TOC entry 1880 (class 2606 OID 24711)
-- Name: pk_question_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY question
    ADD CONSTRAINT pk_question_id PRIMARY KEY (id);


--
-- TOC entry 1871 (class 2606 OID 24692)
-- Name: pk_respondent_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY respondent
    ADD CONSTRAINT pk_respondent_id PRIMARY KEY (id);


--
-- TOC entry 1887 (class 2606 OID 24733)
-- Name: pk_respondent_option; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY respondent_option
    ADD CONSTRAINT pk_respondent_option PRIMARY KEY (respondentid, optionid);


--
-- TOC entry 1869 (class 2606 OID 24681)
-- Name: poll_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY poll
    ADD CONSTRAINT poll_id UNIQUE (id);


--
-- TOC entry 1873 (class 2606 OID 24694)
-- Name: respondent_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY respondent
    ADD CONSTRAINT respondent_id UNIQUE (id);


--
-- TOC entry 1876 (class 1259 OID 24709)
-- Name: IX_Relationship4; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "IX_Relationship4" ON question USING btree (pollid);


--
-- TOC entry 1881 (class 1259 OID 24724)
-- Name: IX_Relationship5; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX "IX_Relationship5" ON option USING btree (questionid);


--
-- TOC entry 1893 (class 2606 OID 24759)
-- Name: fk_option_respondent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY respondent_option
    ADD CONSTRAINT fk_option_respondent FOREIGN KEY (optionid) REFERENCES option(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1890 (class 2606 OID 24744)
-- Name: fk_poll_question; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY question
    ADD CONSTRAINT fk_poll_question FOREIGN KEY (pollid) REFERENCES poll(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1888 (class 2606 OID 24734)
-- Name: fk_poll_respondent; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll_respondent
    ADD CONSTRAINT fk_poll_respondent FOREIGN KEY (pollid) REFERENCES poll(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1891 (class 2606 OID 24749)
-- Name: fk_question_option; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY option
    ADD CONSTRAINT fk_question_option FOREIGN KEY (questionid) REFERENCES question(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1892 (class 2606 OID 24754)
-- Name: fk_respondent_option; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY respondent_option
    ADD CONSTRAINT fk_respondent_option FOREIGN KEY (respondentid) REFERENCES respondent(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 1889 (class 2606 OID 24739)
-- Name: fk_respondent_poll; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY poll_respondent
    ADD CONSTRAINT fk_respondent_poll FOREIGN KEY (respondentid) REFERENCES respondent(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2021 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2016-03-12 17:23:53

--
-- PostgreSQL database dump complete
--