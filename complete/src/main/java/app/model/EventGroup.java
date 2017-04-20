package app.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by jaydatta on 4/17/17.
 */
@Entity // This tells Hibernate to make a table out of this class
@Proxy(lazy = false)
public class EventGroup {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    public long id;
    public String senderName;
    public String responderName;

    public String senderToken;
    public String responderToken;

    public String eventID;

    public String eventName;
    public Date responderRespondedAt;
    public long signalValidUntil;
    public int signalValidFor;

    public String responderContactNumber;

    public String getResponderContactNumber() {
        return responderContactNumber;
    }

    public void setResponderContactNumber(String responderContactNumber) {
        this.responderContactNumber = responderContactNumber;
    }

    public String getResponderContactEmail() {
        return responderContactEmail;
    }

    public void setResponderContactEmail(String responderContactEmail) {
        this.responderContactEmail = responderContactEmail;
    }

    public String responderContactEmail;

    public long getBroadCastTime() {
        return broadCastTime;
    }

    public void setBroadCastTime(long broadCastTime) {
        this.broadCastTime = broadCastTime;
    }

    public long broadCastTime;

    public EventGroup(String senderName, String responderName, String senderToken, String responderToken, String eventID, String eventName, Date responderRespondedAt, int signalValidFor, Long broadcastTime,String responderContactNumber,String responderContactEmail) {
        this.senderName = senderName;
        this.responderName = responderName;
        this.senderToken = senderToken;
        this.responderToken = responderToken;
        this.eventID = eventID;
        this.eventName = eventName;
        this.responderRespondedAt = responderRespondedAt;
        this.signalValidUntil = responderRespondedAt.getTime() + signalValidFor*60*1000;
        this.signalValidFor = signalValidFor;
        this.broadCastTime = broadcastTime;
        this.responderContactNumber = responderContactNumber;
        this.responderContactEmail = responderContactEmail;
    }
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getResponderName() {
        return responderName;
    }

    public void setResponderName(String responderName) {
        this.responderName = responderName;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getResponderToken() {
        return responderToken;
    }

    public void setResponderToken(String responderToken) {
        this.responderToken = responderToken;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getResponderRespondedAt() {
        return responderRespondedAt;
    }

    public void setResponderRespondedAt(Date responderRespondedAt) {
        this.responderRespondedAt = responderRespondedAt;
    }

    public long getSignalValidUntil() {
        return signalValidUntil;
    }

    public void setSignalValidUntil(long signalValidUntil) {
        this.signalValidUntil = signalValidUntil;
    }

    public int getSignalValidFor() {
        return signalValidFor;
    }

    public void setSignalValidFor(int signalValidFor) {
        this.signalValidFor = signalValidFor;
    }

    public EventGroup(){

    }

}
