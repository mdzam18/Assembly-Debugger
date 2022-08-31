package src.DapClasses;

import src.DapClasses.Event.Event;

public class ExitedEvent extends Event {
    public ExitedEvent() {
        super.setEvent("exited");
    }
    private Object body;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
