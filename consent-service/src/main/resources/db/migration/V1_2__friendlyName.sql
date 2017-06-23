ALTER TABLE consent_template ADD COLUMN friendly_name VARCHAR(64);

update consent_template set friendly_name = app_id;