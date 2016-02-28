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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONObject;

/**
 *
 * @author Frank Afriat
 */
public class JsonStandardWebSocketServerServicesDelegator<W, S> implements JsonStandardWebSocketServerServices<W, S> {

    protected WebSocketServerServices<W> webSocketServerAdapter;
    
    protected Map<W,S> session = new HashMap<>();
    protected Map<String, JsonHandler<W, S>> bindings = new HashMap<>();
    protected JsonHandler<W,S> unknownServiceHandler;
    protected AtomicInteger callback = new AtomicInteger(0);    

    
    public JsonStandardWebSocketServerServicesDelegator(WebSocketServerServices<W> webSocketServerAdapter) {
        this.webSocketServerAdapter = webSocketServerAdapter;
    }
    
    public void onMessage(W conn, String message) {
        //this.sendToAll( message );
        //System.out.println(conn + ": " + message);
        JSONObject jSONObject = new JSONObject(message);
        String service = jSONObject.getString("service");
        JsonHandler<W,S> handler = bindings.get(service);
        if (handler != null) {
            JSONObject data = jSONObject.getJSONObject("data");
            String callbackId = jSONObject.optString("callback");
            boolean shouldRemove = handler.handle(data, this, conn, callbackId);
            if (shouldRemove) {
                unbind(service);
            }
        }
        else {
            if (unknownServiceHandler != null) {
                unknownServiceHandler.handle(jSONObject, this, conn, null);
            }
            else {
                //System.out.println("Unknown Service: [" + service + "]");
            }
        }
    }    

    @Override
    public void bind(String service, JsonHandler<W, S> handler) {
        bindings.put(service, handler);
    }    

    @Override
    public void unbind(String service) {
        bindings.remove(service);
    }

    @Override
    public void bindSession(W conn, S s) {
        session.put(conn, s);
    }

    @Override
    public void unbindSession(W conn) {
        session.remove(conn);
    }
    
    @Override
    public S getSession(W conn) {
        return session.get(conn);
    }

    @Override
    public void send(W conn, String service, JSONObject data, JsonHandler<W,S> handler) {
        JSONObject toSend = new JSONObject();
        toSend.put("service", service);
        toSend.put("data", data);
        if (handler != null) {
            String callbackId = "callback" + callback.incrementAndGet();
            bind(callbackId, handler);
            toSend.put("callback", callbackId);
        }
        webSocketServerAdapter.send(conn, toSend.toString());
    }

    
    @Override
    public void send(W conn, String service, JSONObject data) {
      send(conn, service, data, null);
    }
    
    @Override
    public void sendToAll(String service, JSONObject data, JsonHandler<W,S> handler) {
        Collection<W> connections = webSocketServerAdapter.getConnections();
        synchronized (connections) {
            for (W conn : connections) {
                send(conn, service, data, handler);
            }
        }
    }
    
    @Override
    public void sendToAll(String service, JSONObject data) {
        sendToAll(service, data, null);
    }

    @Override
    public void setUnknownServiceHandler(JsonHandler<W,S> unknownServiceHandler) {
        this.unknownServiceHandler = unknownServiceHandler;
    }
        
}
