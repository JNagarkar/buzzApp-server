package app;

/**
 * Created by jaydatta on 4/13/17.
 */
public class BroadCastEvent {

    public String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Event event;

    public BroadCastEvent(String userID, Event event) {
        this.userID = userID;
        this.event = event;
    }

    public BroadCastEvent(){

    }

}
