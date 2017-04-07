FROM openjdk:8u111-jre-alpine
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