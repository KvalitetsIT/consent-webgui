FROM rabbitmq:3.5.5-management

RUN mv docker-entrypoint.sh   /old-docker-entrypoint.sh
COPY docker-entrypoint.sh /
RUN chmod +x docker-entrypoint.sh
COPY definitions.json /etc/rabbitmq/

