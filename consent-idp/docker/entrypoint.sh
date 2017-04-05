#! /bin/bash

# Copy key and certificates
cp /cert/${IDP_CERTIFICATE} /var/consent/cert/certificate.crt
cp /cert/${IDP_PRIVATE_KEY} /var/consent/cert/certificate.pem

# Copy the templates
cp -r /var/consent/config-templates/* /var/consent/config/
cp -r /var/consent/metadata-templates/* /var/consent/metadata/

# Copy metadata
cp -r /metadata/* /var/consent/metadata/

# Configure the IDP according to environment variables
grep -rl auth.adminpassword /var/consent/config/config.php | xargs sed -i "s/123/${IDP_ADMIN_PASSWORD}/g"
grep -rl technicalcontact_email /var/consent/config/config.php | xargs sed -i "s/na@example.org/${IDP_TECHNICAL_EMAIL}/g"
grep -rl secretsalt /var/consent/config/config.php | xargs sed -i "s/'secretsalt' => 'defaultsecretsalt',/'secretsalt' => '${IDP_SECRET_SALT}',/g"
grep -rl enable.saml20-idp /var/consent/config/config.php | xargs sed -i "s/'enable.saml20-idp' => false,/'enable.saml20-idp' => true,/g"
grep -rl tempdir /var/consent/config/config.php | xargs sed -i "s/'tempdir' => '\/tmp\/simplesaml',/'tempdir' => '\/tmp\/simplesaml-idp',/g"
grep -rl baseurlpath /var/consent/config/config.php | xargs sed -i "s/'baseurlpath' => 'simplesaml\/',/'baseurlpath' => '${IDP_PROTOCOL}:\/\/${IDP_HOSTNAME}\/${IDP_CONTEXTPATH}\/',/g"
grep -rl logging.processname /var/consent/config/config.php | xargs sed -i "s/'logging.processname' => 'simplesamlphp',/'logging.processname' => 'simplesamlphp-idp',/g"
grep -rl logging.logfile /var/consent/config/config.php | xargs sed -i "s/'logging.logfile' => 'simplesamlphp.log',/'logging.logfile' => 'simplesamlphp-idp.log',/g"
grep -rl session.cookie.path /var/consent/config/config.php | xargs sed -i "s/'session.cookie.path' => '\/',/'session.cookie.path' => '\/${IDP_CONTEXTPATH}\/',/g"
grep -rl auth /var/consent/metadata/saml20-idp-hosted.php | xargs sed -i "s/example-userpass/default-sp/g"

# Configure apache
envsubst < /templates/apache2.conf > /etc/apache2/apache2.conf

apache2 -DFOREGROUND
