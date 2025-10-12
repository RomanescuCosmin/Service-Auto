CREATE SEQUENCE USERS_SEQ;
CREATE TABLE USERS (
                       ID           BIGINT PRIMARY KEY DEFAULT NEXTVAL('USERS_SEQ'),
                       NUME         VARCHAR(100) NOT NULL,
                       EMAIL        VARCHAR(255) NOT NULL UNIQUE,
                       PASSWORD     VARCHAR(255) NOT NULL,
                       IS_ENABLED   BOOLEAN DEFAULT TRUE,
                       IS_DELETED   BOOLEAN DEFAULT FALSE,
                       reset_token VARCHAR(100),
                       token_expiry TIMESTAMP,
                       VERSION      INTEGER DEFAULT 0
);

CREATE SEQUENCE ROLE_SEQ;
CREATE TABLE ROLE (
                      ID    BIGINT PRIMARY KEY DEFAULT NEXTVAL('ROLE_SEQ'),
                      NAME  VARCHAR(100) NOT NULL UNIQUE
);

CREATE SEQUENCE USERS_ROLES_SEQ;
CREATE TABLE USERS_ROLES (
                             ID BIGINT PRIMARY KEY DEFAULT NEXTVAL('USERS_ROLES_SEQ')
                             USER_ID  BIGINT NOT NULL,
                             ROLE_ID  BIGINT NOT NULL,
                             CONSTRAINT USERS_ROLES_X_USERS_FK FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
                             CONSTRAINT USERS_ROLES_X_ROLE_FK FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID)
);



CREATE SEQUENCE CONTACT_SEQ;
CREATE TABLE CONTACT (
        id BIGINT PRIMARY KEY NOT NULL DEFAULT NEXTVAL('CONTACT_SEQ'),
        fk_users BIGINT   NOT NULL,
        nume VARCHAR(30) NOT NULL,
        telefon INTEGER NOT NULL,
        email VARCHAR(200) NOT NULL,
        serie_sasiu VARCHAR(200),
        descriere varchar(8000),
        CONSTRAINT users_fk FOREIGN KEY (fk_users) REFERENCES  USERS(ID)
);