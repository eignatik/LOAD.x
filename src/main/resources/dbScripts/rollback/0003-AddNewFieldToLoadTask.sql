BEGIN TRANSACTION;
DROP INDEX LOAD_TASK_id_uindex;
CREATE TABLE LOAD_TASK2773
(
  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,
  baseUrl varchar(255) NOT NULL,
  loadingTime integer NOT NULL,
  parallelThreshold integer NOT NULL
);
CREATE UNIQUE INDEX LOAD_TASK_id_uindex ON LOAD_TASK2773 (id);
INSERT INTO LOAD_TASK2773(id, baseUrl, loadingTime, parallelThreshold) SELECT id, baseUrl, loadingTime, parallelThreshold FROM LOAD_TASK;
DROP TABLE LOAD_TASK;
ALTER TABLE LOAD_TASK2773 RENAME TO LOAD_TASK;

DELETE FROM SCHEMA where scriptNumber = '0003';

COMMIT;