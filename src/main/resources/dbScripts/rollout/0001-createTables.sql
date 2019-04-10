BEGIN TRANSACTION;

CREATE TABLE LOAD_TASK
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    baseUrl varchar(255) NOT NULL,
    loadingTime integer NOT NULL,
    parallelThreshold integer NOT NULL
);
CREATE UNIQUE INDEX LOAD_TASK_id_uindex ON LOAD_TASK (id);

CREATE TABLE LOAD_REQUEST
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    type varchar(6) NOT NULL,
    url varchar NOT NULL,
    timeout integer
);
CREATE UNIQUE INDEX LOAD_REQUEST_id_uindex ON LOAD_REQUEST (id);

CREATE TABLE TASK_REQUESTS
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    loadTaskid integer NOT NULL,
    loadRequestId integer NOT NULL
);
CREATE UNIQUE INDEX TASK_REQUESTS_id_uindex ON TASK_REQUESTS (id);

CREATE TABLE LOADING_EXECUTION
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    loadingTaskId integer NOT NULL,
    startTime date,
    endTime date
);
CREATE UNIQUE INDEX LOADING_EXECUTION_id_uindex ON LOADING_EXECUTION (id);

CREATE TABLE EXECUTION_DETAILS
(
    id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
    executionId integer NOT NULL,
    startTime date,
    endTime date,
    requestId integer NOT NULL,
    timeElapsed integer,
    responseCode integer,
    loadingStatus varchar
);
CREATE UNIQUE INDEX EXECUTION_DETAILS_id_uindex ON EXECUTION_DETAILS (id);

INSERT INTO SCHEMA (scriptNumber) values ('0001');

COMMIT;