BEGIN TRANSACTION;

ALTER TABLE LOAD_TASK ADD basePort integer NULL;

COMMIT;