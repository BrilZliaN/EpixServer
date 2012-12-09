package org.Epixcrafted.EpixServer.engine.player;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class SessionList {

    private final ConcurrentMap<Session, Boolean> sessions = new ConcurrentHashMap<Session, Boolean>();
    
    public void add(Session session) {
        sessions.put(session, true);
    }

    public void remove(Session session) {
        sessions.remove(session);
    }

    public void update() {
        for (Session session : sessions.keySet()) {
            session.update();
        }
    }
    
    public ArrayList<Session> getSessionList() {
    	return new ArrayList<Session>(sessions.keySet());
    }
}
