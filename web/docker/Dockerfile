FROM ibm-semeru-runtimes:open-17-jre
RUN apt-get update && \
    apt-get install -y gettext \
                       tzdata

COPY entrypoint.sh /
RUN chmod +x entrypoint.sh

COPY config /consent
RUN mkdir /app

ADD /maven/web-exec.war /app/web.war

COPY images /app/images

WORKDIR /app

ENTRYPOINT [ "/entrypoint.sh" ]