FROM adoptopenjdk/openjdk13-openj9:x86_64-alpine-jre-13.0.2_8_openj9-0.18.0
RUN apk update && \
    apk upgrade && \
    apk add --update gettext tzdata

COPY entrypoint.sh /
RUN chmod +x entrypoint.sh
COPY configtemplates  /configtemplates
RUN mkdir /app
ADD /maven/consent-service.jar /app/consent-service.jar
WORKDIR /app
ENTRYPOINT [ "/entrypoint.sh" ]