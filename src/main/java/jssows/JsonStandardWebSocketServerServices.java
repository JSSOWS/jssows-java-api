/*
    Copyright 2015 Frank Afriat

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package jssows;

import org.json.JSONObject;

/**
 *
 * @author Frank Afriat
 */
public interface JsonStandardWebSocketServerServices<W, S> {

    public void bindSession(W websocket, S s);
    public S getSession(W websocket);
    public void unbindSession(W websocket);

    public void send(W websocket, String service, JSONObject data, JsonHandler<W,S> handler);
    public void send(W websocket, String service, JSONObject data);
    public void sendToAll(String service, JSONObject data, JsonHandler<W,S> handler);
    public void sendToAll(String service, JSONObject data);

    public void setUnknownServiceHandler(JsonHandler<W,S> unknownServiceHandler);

    public void bind(String service, JsonHandler<W,S> handler);
    public void unbind(String service);
    
}
