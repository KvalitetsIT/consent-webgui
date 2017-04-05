FROM ianblenke/simplesamlphp:1.13.2

RUN  apt-get update && apt-get install gettext -y

ADD entrypoint.sh /
RUN chmod +x /entrypoint.sh

# Templates
RUN mkdir /templates
ADD apache2.conf /templates/

#
RUN cp -r /var/www/html/ /var/consent/
RUN mkdir /var/consent/cert
# Simplesamlphp setup - idp
#ADD authsources.php /var/consent/config-templates/
ADD saml20-idp-hosted.php /var/consent/metadata-templates/

ENTRYPOINT ["/entrypoint.sh"]