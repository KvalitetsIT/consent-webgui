CREATE TABLE consent (
    id         	        BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    citizen_id          VARCHAR(64),
    answer			    BIT,
    consent_template_id BIGINT
);

CREATE TABLE consent_template (
    id         	        BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    app_id     		    VARCHAR(64),
    mime_type           VARCHAR(64),
    content			    MEDIUMTEXT
);
