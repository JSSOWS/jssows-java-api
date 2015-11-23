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
[Donate with PayPal.](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=YPXUQJM6ACZNG&lc=FR&item_name=JSSOWS&item_number=JSSOWS&currency_code=EUR&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHosted)