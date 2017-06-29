ALTER TABLE consent_template ADD COLUMN notification_subject VARCHAR(64);

update consent_template set notification_subject = app_id;