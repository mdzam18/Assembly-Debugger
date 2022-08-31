package src.DapClasses;

import src.DapClasses.Event.Event;

public class TerminatedEvent extends Event {
    public TerminatedEvent() {
        super.setEvent("terminated");
    }
    private Object body;


    public Object getBody() {
        return body;
    }


    public void setBody(Object body) {
        this.body = body;
    }
}
