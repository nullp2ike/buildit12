--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: plant_hire_request; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE plant_hire_request (
    id bigint NOT NULL,
    end_date timestamp without time zone NOT NULL,
    plant_id integer NOT NULL,
    start_date timestamp without time zone NOT NULL,
    status integer,
    total_cost numeric(19,2) NOT NULL,
    version integer,
    site bigint NOT NULL,
    site_engineer bigint NOT NULL,
    supplier bigint
);


ALTER TABLE public.plant_hire_request OWNER TO postgres;

--
-- Name: site; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE site (
    id bigint NOT NULL,
    name character varying(255),
    version integer
);


ALTER TABLE public.site OWNER TO postgres;

--
-- Name: site_engineer; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE site_engineer (
    id bigint NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    version integer
);


ALTER TABLE public.site_engineer OWNER TO postgres;

--
-- Name: supplier; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE supplier (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    version integer
);


ALTER TABLE public.supplier OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 16, true);


--
-- Data for Name: plant_hire_request; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY plant_hire_request (id, end_date, plant_id, start_date, status, total_cost, version, site, site_engineer, supplier) FROM stdin;
4	2013-10-20 01:11:11.3	1	2013-10-20 01:11:11.3	1	3.00	1	2	1	3
8	2013-10-20 01:11:11.53	1	2013-10-20 01:11:11.53	0	100.00	0	6	5	7
12	2013-10-20 01:11:11.662	1	2013-10-20 01:11:11.662	3	100.00	1	10	9	11
16	2013-10-20 01:11:11.78	1	2013-10-20 01:11:11.78	0	3.00	0	14	13	15
\.


--
-- Data for Name: site; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY site (id, name, version) FROM stdin;
2	Modify	0
6	Check Status	0
10	Cancel	0
14	Create	0
\.


--
-- Data for Name: site_engineer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY site_engineer (id, first_name, last_name, version) FROM stdin;
1	FirstName2	LastName2	0
5	FirstName4	LastName4	0
9	FirstName3	LastName3	0
13	FirstName1	LastName1	0
\.


--
-- Data for Name: supplier; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY supplier (id, name, version) FROM stdin;
3	Supplier2	0
7	Supplier4	0
11	Supplier3	0
15	Supplier1	0
\.


--
-- Name: plant_hire_request_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY plant_hire_request
    ADD CONSTRAINT plant_hire_request_pkey PRIMARY KEY (id);


--
-- Name: site_engineer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY site_engineer
    ADD CONSTRAINT site_engineer_pkey PRIMARY KEY (id);


--
-- Name: site_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY site
    ADD CONSTRAINT site_pkey PRIMARY KEY (id);


--
-- Name: supplier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY supplier
    ADD CONSTRAINT supplier_pkey PRIMARY KEY (id);


--
-- Name: fk_ch3xs9as8fbft3p7nye26hi4g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plant_hire_request
    ADD CONSTRAINT fk_ch3xs9as8fbft3p7nye26hi4g FOREIGN KEY (site) REFERENCES site(id);


--
-- Name: fk_ixs5q75pcuwncfstfqhdryndn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plant_hire_request
    ADD CONSTRAINT fk_ixs5q75pcuwncfstfqhdryndn FOREIGN KEY (site_engineer) REFERENCES site_engineer(id);


--
-- Name: fk_q820u1kawfciw9k5kd9kuthlj; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY plant_hire_request
    ADD CONSTRAINT fk_q820u1kawfciw9k5kd9kuthlj FOREIGN KEY (supplier) REFERENCES supplier(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

