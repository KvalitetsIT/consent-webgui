#! /bin/ash
if [ "$CONTAINER_TIMEZONE" = "" ]
then
   echo "Using default timezone"
else
	
	TZFILE="/usr/share/zoneinfo/$CONTAINER_TIMEZONE"
	if [ ! -e "$TZFILE" ]
	then 
    	echo "requested timezone $CONTAINER_TIMEZONE doesn't exist"
	else
		cp /usr/share/zoneinfo/$CONTAINER_TIMEZONE /etc/localtime
		echo "$CONTAINER_TIMEZONE" > /etc/timezone
		echo "using timezone $CONTAINER_TIMEZONE"
	fi
fi

if [[ -z $LOG_LEVEL ]]; then
  echo "Default LOG_LEVEL = INFO"
  export LOG_LEVEL=INFO
fi

if [[ -z $CORRELATION_ID ]]; then
  echo "Default CORRELATION_ID = correlation-id"
  export CORRELATION_ID=correlation-id
fi

if [[ -z $SERVICE_ID ]]; then
  echo "Default SERVICE_ID = consent-service"
  export SERVICE_ID=consent-service
fi


envsubst < /configtemplates/application.properties > /app/application.properties
envsubst < /configtemplates/logback.xml > /app/logback-spring.xml

java $JVM_OPTS -jar consent-service.jar