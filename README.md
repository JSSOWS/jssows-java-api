# jssows-java-api 

###Motivation

I looked for something similar on the web but did not find any **very very simple** standard sub protocol for calling services over websocket and getting a response. So I wrote it. Putting it there so it can be useful for others.

This API has been defined to allow different java implementation and websocket

###Protocol

* Communication using Json String
* Service defined by a name

*Format:*
>     {
>       "service" : "<service name of the service to call>", 
>       "data"    : "<the data to give to the service>"
>     }

or to get a response on a given service name called callback:

*Format:* 
>     {
>       "service"  : "<service name of the service to call>",
>       "data"     : "<the data to give to the service>",
>       "callback" : "service name of the service to call to send the response"
>     }

### Implementations
 1. [javascript](https://github.com/JSSOWS/jssows-js) (client) - (should be usable for serversocket also but not tested)
 2. [java](https://github.com/JSSOWS/jssows-javq) (server) - Using org.java_websocket implementation  (work on Android)


### Donate!

Help me continue to contribute to the JSSOWS projects.
Donate.
<form action="https://www.paypal.com/cgi-bin/webscr" method="post" target="_top">
<input type="hidden" name="cmd" value="_s-xclick">
<input type="hidden" name="encrypted" value="-----BEGIN PKCS7-----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-----END PKCS7-----
">
<input type="image" src="https://www.paypalobjects.com/fr_FR/FR/i/btn/btn_donateCC_LG.gif" border="0" name="submit" alt="PayPal, le réflexe sécurité pour payer en ligne">
<img alt="" border="0" src="https://www.paypalobjects.com/fr_FR/i/scr/pixel.gif" width="1" height="1">
</form>