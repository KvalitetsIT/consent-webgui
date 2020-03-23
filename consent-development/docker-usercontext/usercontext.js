var http = require('http');

var server = http.createServer(function(req, res) {
  res.setHeader('Content-Type', 'application/json');
  res.writeHead(200);
  res.end('{ "UserAttributes": { "urn:dk:gov:saml:cprNumberIdentification": [ "0102031AB2" ],"urn:dk:gov:saml:municipality": [ "Esbjerg" ], "urn:dk:kit:borger:displayName": [ "mogensen" ], "uid": [ "mogensen" ] } , "SessionAttributes": { "urn:dk:gov:saml:cprNumberIdentification": "0102031AB2", "urn:dk:kit:borger:displayName":  "mogensen" , "uid":  "mogensen"  }  }');
});
server.listen(9200);
