<?php

class SimpleSAML_Logger_LoggingHandlerSystemOut implements SimpleSAML_Logger_LoggingHandler
{
    private $stdout;
    
    private $format;
    
    private static $levelNames = array(
        SimpleSAML_Logger::EMERG   => 'ERROR',
        SimpleSAML_Logger::ALERT   => 'ERROR',
        SimpleSAML_Logger::CRIT    => 'ERROR',
        SimpleSAML_Logger::ERR     => 'ERROR',
        SimpleSAML_Logger::WARNING => 'WARN',
        SimpleSAML_Logger::NOTICE  => 'INFO',
        SimpleSAML_Logger::INFO    => 'INFO',
        SimpleSAML_Logger::DEBUG   => 'DEBUG',
    );
    

    /**
     * Build a new logging handler based on syslog.
     */
    public function __construct()
    {
    	$this->stdout = fopen('php://stdout', 'w');
    }

    /**
     * Set the format desired for the logs.
     *
     * @param string $format The format used for logs.
     */
    public function setLogFormat($format)
    {
        $this->format = $format;
    }


    /**
     * Log a message to systemout
     *
     * @param int $level The log level.
     * @param string $string The formatted message to log.
     */
    public function log($level, $string)
    {
    	$levelName = self::$levelNames[$level];
    	$headers = getallheaders();
    	
    	$corrId = $headers['correlation-id'];
    	
    	$timeformat = '%Y-%m-%dT%H:%M:%S.000+00:00';
    	
    	$formats = array('%time', '%level', '%correlationid');
        $replacements = array(strftime($timeformat), $levelName, $corrId);
        $string = str_replace($formats, $replacements, $string);
            
    	fwrite($this->stdout, $string.PHP_EOL);
    }
}
