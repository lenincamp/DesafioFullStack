alter table role_permission drop constraint permissions_fk;
alter table role_permission add constraint permissions_fk foreign key (module_id, action_id) references actions_modules;

/*ROLE USERS*/
INSERT INTO ROLE(DESCRIPTION, NAME) VALUES ('ROLE FOR USER-ADMINS', 'ROLE_ADMIN');
INSERT INTO ROLE(DESCRIPTION, NAME) VALUES ('ROLE FOR USER-TRIADOR', 'ROLE_USER');
INSERT INTO ROLE(DESCRIPTION, NAME) VALUES ('ROLE FOR USER-FINAL', 'ROLE_USER_FINAL');
/*CREATE USER ADMIN DEFAULT*/
INSERT INTO "USER" (EMAIL, ENABLED, FIRSTNAME, LASTNAME,FULLNAME,PASSWORD,USERNAME, CREATED_AT )VALUES('default@gmail.com', 1, 'Default', 'User', 'Default User','$2a$10$s5vSKd1Y1Q2CS89mexdx6uLaPwdJ0i40Uni91UtpA95XF5/xBAAxq','default_user',current_timestamp);
INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES(1,1);
/*CREATE ACTIONS FOR MODULES*/
INSERT INTO "ACTION" (CREATED_AT, CREATED_BY, TITLE, DESCRIPTION) VALUES(CURRENT_TIMESTAMP , 1,'CC', 'CAN CREATE');
INSERT INTO "ACTION" (CREATED_AT, CREATED_BY, TITLE, DESCRIPTION) VALUES(CURRENT_TIMESTAMP , 1,'CR', 'CAN READ');
INSERT INTO "ACTION" (CREATED_AT,  CREATED_BY, UPDATE_BY, TITLE, DESCRIPTION) VALUES(CURRENT_TIMESTAMP, 1, 0, 'CU', 'CAN UPDATE');
INSERT INTO "ACTION" (CREATED_AT, CREATED_BY, UPDATE_BY, TITLE, DESCRIPTION) VALUES(CURRENT_TIMESTAMP, 1, 0, 'CR', 'CAN REMOVE');
INSERT INTO "ACTION" (CREATED_AT, CREATED_BY, UPDATE_BY, TITLE, DESCRIPTION) VALUES(CURRENT_TIMESTAMP, 1, 0, 'CO', 'CAN GIVE A OPINION');
INSERT INTO "ACTION" (CREATED_AT, CREATED_BY, UPDATE_BY, TITLE, DESCRIPTION) VALUES(CURRENT_TIMESTAMP, 1, 0, 'CE', 'CAN EXECUTE');
/*CREATE MODULES*/
INSERT INTO "MODULE" (CREATED_AT, CREATED_BY, DESCRIPTION, TITLE, URL) VALUES(CURRENT_TIMESTAMP , 1, 'MODULE FOR USERS', 'USERS', '/users');
INSERT INTO "MODULE" (CREATED_AT, CREATED_BY, DESCRIPTION, TITLE, URL) VALUES(CURRENT_TIMESTAMP , 1, 'MODULE FOR PROCESS', 'PROCESS', '/process');
INSERT INTO "MODULE" (CREATED_AT, CREATED_BY, DESCRIPTION, TITLE, URL) VALUES(CURRENT_TIMESTAMP , 1, 'MODULE FOR ASSIGMENT USER PROCESS', 'ASSIGMENT', '/assigment');
/*ASSIGMENT ACTIONS FOR MODULES*/
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(1, 1, '/users/create');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(2, 1, '/users/list');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(3, 1, '/users/update');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(4, 1, null);
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(1, 2, '/process/create');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(2, 2, '/process/list');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(5, 2, '/process/opinion');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(6, 2, '/process/opinion');
INSERT INTO ACTIONS_MODULES(ACTION_ID, MODULE_ID,URL) VALUES(2, 3, '/assigment/user');
/*ASSIGMENT ROLES MODULES ACTIONS*/
--ROLE FOR ADMIN
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 1, 1);
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 2, 1);
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 3, 1);
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 4, 1);
--/*INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 1, 2);
--INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 2, 2);
--INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 2, 3);
--INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(1, 5, 2);*/
--ROLE FOR USER OBSERVER
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(2, 1, 2);
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(2, 2, 2);
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(2, 6, 2);
--ROLE FOR USER FINAL
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(3, 2, 2);
INSERT INTO ROLE_PERMISSION(ROLE_ID, ACTION_ID, MODULE_ID) VALUES(3, 5, 2);
/*CREATE CATALOGS*/
INSERT INTO CATALOG (KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('TPP', 'TYPE PROCESS', 'TYPE OF PROCESS', 1, 1, CURRENT_TIMESTAMP );
INSERT INTO CATALOG (KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('STS', 'STATUS', 'TYPE OF PROCESS EXECUTIONS', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO CATALOG (KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('TOE', 'TYPE OBSERVATION', 'TYPE OF OBSERVATION FOR EXECUTIONS', 1, 1, CURRENT_TIMESTAMP);
/*CREATE CATALOG VALUE*/
INSERT INTO CATALOG_VALUE (CATALOG_ID, KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('TPP','OP','OPERATIONAL PROCESSES', 'OPERATIONAL PROCESS', 1, 1,CURRENT_TIMESTAMP );
INSERT INTO CATALOG_VALUE (CATALOG_ID, KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('TPP','PM','PROCESS MANAGEMENT', 'PROCESS MANAGEMENT', 1, 1,CURRENT_TIMESTAMP );
INSERT INTO CATALOG_VALUE (CATALOG_ID, KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('STS','SA','ASSIGNED', 'STATUS ASSIGNED', 1, 1,CURRENT_TIMESTAMP );
INSERT INTO CATALOG_VALUE (CATALOG_ID, KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('STS','SF','FINISHED', 'STATUS FINISHED', 1, 1,CURRENT_TIMESTAMP );
INSERT INTO CATALOG_VALUE (CATALOG_ID, KEYWORD, NAME, DESCRIPTION, ENABLED, CREATED_BY, CREATED_AT) VALUES ('TOE','OD','OBSERVATION DEFAULT', 'OBSERVATION DEFAULT', 1, 1,CURRENT_TIMESTAMP );