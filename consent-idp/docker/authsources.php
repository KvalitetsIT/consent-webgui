<?php
$config = array(

	// This is a authentication source which handles admin authentication.
    'admin' => array(
        // The default is to use core:AdminPassword, but it can be replaced with
        // any authentication source.

        'core:AdminPassword',
    ),

    /* This is the name of this authentication source, and will be used to access it later. */
    'default-sp' => array(
        'saml:SP',
        'idp' => 'SOURCE_IDP_URL',
        'privatekey' => 'certificate.pem',
        'certificate' => 'certificate.crt',
    ),
);