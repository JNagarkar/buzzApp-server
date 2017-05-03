package app.helper;

import java.util.Date;

/**
 * Created by jaydatta on 4/13/17.
 */
public class BroadCastEvent {

    public String userID;
    public Event event;
    public Date currentTime;
    public String personalMessage;

    public BroadCastEvent(String userID, Event event, Date currentTime) {
        this.userID = userID;
        this.event = event;
        this.currentTime = currentTime;
    }

    public BroadCastEvent() {

    }

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

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public String getPersonalMessage() {
        return personalMessage;
    }

    public void setPersonalMessage(String personalMessage) {
        this.personalMessage = personalMessage;
    }

}
