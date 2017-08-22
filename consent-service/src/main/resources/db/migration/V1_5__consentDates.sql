ALTER TABLE consent ADD COLUMN creation_date DATETIME;
ALTER TABLE consent ADD COLUMN revocation_date DATETIME;
update consent set creation_date = current_timestamp;