<?php

session_cache_limiter('nocache');

$globalConfig = SimpleSAML_Configuration::getInstance();

if (!array_key_exists('StateId', $_REQUEST)) {
    throw new SimpleSAML_Error_BadRequest(
        'Missing required StateId query parameter.'
    );
}

$id = $_REQUEST['StateId'];

// sanitize the input
$sid = SimpleSAML_Utilities::parseStateID($id);
if (!is_null($sid['url'])) {
	SimpleSAML_Utilities::checkURLAllowed($sid['url']);
}

$state = SimpleSAML_Auth_State::loadState($id, 'consent:request');

header('Content-Type: '.$state['consent:template.mimetype']);
echo base64_decode($state['consent:template.content']);


