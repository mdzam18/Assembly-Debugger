package src.DapClasses;

public class Event extends ProtocolMessage {
    public Event(){
        super.setType("event");
    }
    private String event;
    private Object body;


    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
