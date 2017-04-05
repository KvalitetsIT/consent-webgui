FROM ianblenke/simplesamlphp:1.13.2

RUN  apt-get update && apt-get install gettext -y

ADD entrypoint.sh /
RUN chmod +x /entrypoint.sh

# Templates
RUN mkdir /templates
ADD apache2.conf /templates/

RUN cp -r /var/www/html/ /var/simplesamlphp/
RUN mkdir /var/simplesamlphp/cert
ADD config.php /var/simplesamlphp/config-templates/

# Simplesamlphp setup - idp
ADD authsources.php /var/simplesamlphp/config-templates/
ADD saml20-idp-hosted.php /var/simplesamlphp/metadata-templates/

RUN touch /var/simplesamlphp/modules/consent/enable

ENTRYPOINT ["/entrypoint.sh"]