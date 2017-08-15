ALTER TABLE consent_template ADD COLUMN version BIGINT;
ALTER TABLE consent_template ADD COLUMN active BIT;

update consent_template set version = 1;
update consent_template set active = 1;