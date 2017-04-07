#! /bin/bash

# Copy key and certificates
cp /cert/${IDP_CERTIFICATE} /var/simplesamlphp/cert/certificate.crt
cp /cert/${IDP_PRIVATE_KEY} /var/simplesamlphp/cert/certificate.pem

# Copy the templates
cp -r /var/simplesamlphp/config-templates/* /var/simplesamlphp/config/
cp -r /var/simplesamlphp/metadata-templates/* /var/simplesamlphp/metadata/

# Copy metadata
cp -r /metadata/* /var/simplesamlphp/metadata/

# Configure the IDP according to environment variables
grep -rl auth.adminpassword /var/simplesamlphp/config/config.php | xargs sed -i "s/123/${IDP_ADMIN_PASSWORD}/g"
grep -rl technicalcontact_email /var/simplesamlphp/config/config.php | xargs sed -i "s/na@example.org/${IDP_TECHNICAL_EMAIL}/g"
grep -rl secretsalt /var/simplesamlphp/config/config.php | xargs sed -i "s/'secretsalt' => 'defaultsecretsalt',/'secretsalt' => '${IDP_SECRET_SALT}',/g"
grep -rl enable.saml20-idp /var/simplesamlphp/config/config.php | xargs sed -i "s/'enable.saml20-idp' => false,/'enable.saml20-idp' => true,/g"
grep -rl tempdir /var/simplesamlphp/config/config.php | xargs sed -i "s/'tempdir' => '\/tmp\/simplesaml',/'tempdir' => '\/tmp\/simplesaml-idp',/g"
grep -rl baseurlpath /var/simplesamlphp/config/config.php | xargs sed -i "s/'baseurlpath' => 'simplesaml\/',/'baseurlpath' => '${IDP_PROTOCOL}:\/\/${IDP_HOSTNAME}\/${IDP_CONTEXTPATH}\/',/g"
grep -rl logging.processname /var/simplesamlphp/config/config.php | xargs sed -i "s/'logging.processname' => 'simplesamlphp',/'logging.processname' => 'simplesamlphp-idp',/g"
grep -rl logging.logfile /var/simplesamlphp/config/config.php | xargs sed -i "s/'logging.logfile' => 'simplesamlphp.log',/'logging.logfile' => 'simplesamlphp-idp.log',/g"
grep -rl session.cookie.path /var/simplesamlphp/config/config.php | xargs sed -i "s/'session.cookie.path' => '\/',/'session.cookie.path' => '\/${IDP_CONTEXTPATH}\/',/g"
grep -rl auth /var/simplesamlphp/metadata/saml20-idp-hosted.php | xargs sed -i "s/example-userpass/default-sp/g"

# Configure apache
envsubst < /templates/apache2.conf > /etc/apache2/apache2.conf
sed -i "s|SOURCE_IDP_URL|$SOURCE_IDP_URL|g" /var/simplesamlphp/config/authsources.php
sed -i "s|__CONSENT_SERVICE_URL__|$CONSENT_SERVICE_URL|g" /var/simplesamlphp/config/config.php
sed -i "s|__USER_ID_ATTR__|$USER_ID_ATTR|g" /var/simplesamlphp/config/config.php

# check that is set SOURCE_IDP_URL


apache2 -DFOREGROUND