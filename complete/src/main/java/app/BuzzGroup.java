package app;

import app.model.DummyEvent;
import app.model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jaydatta on 4/17/17.
 */


public class BuzzGroup {

    public User sender;
    public ArrayList<User> responderGroup;
    public int signalValidFor;
    public DummyEvent event;
    public boolean isValid;
    public Date signalInitiatedAt;
    public long signalValidUntil;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public long getSignalValidUntil() {
        return signalValidUntil;
    }

    public void setSignalValidUntil(long signalValidUntil) {
        this.signalValidUntil = signalValidUntil;
    }

    public DummyEvent getEvent() {
        return event;
    }

    public void setEvent(DummyEvent event) {
        this.event = event;
    }

    public BuzzGroup(User sender, ArrayList<User> responderGroup, int signalValidFor, Date signalInitiatedAt,DummyEvent event) {
        this.sender = sender;
        this.responderGroup = responderGroup;
        this.signalValidFor = signalValidFor;
        this.signalInitiatedAt = signalInitiatedAt;
        this.signalValidUntil = signalInitiatedAt.getTime() + signalValidFor*60*1000;
        this.isValid=true;
        this.event = event;
    }

    public void addResponder(User user){
        responderGroup.add(user);
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<User> getResponderGroup() {
        return responderGroup;
    }

    public void setResponderGroup(ArrayList<User> responderGroup) {
        this.responderGroup = responderGroup;
    }

    public int getSignalValidFor() {
        return signalValidFor;
    }

    public void setSignalValidFor(int signalValidFor) {
        this.signalValidFor = signalValidFor;
    }

    public Date getSignalInitiatedAt() {
        return signalInitiatedAt;
    }

    public void setSignalInitiatedAt(Date signalInitiatedAt) {
        this.signalInitiatedAt = signalInitiatedAt;
    }

    public BuzzGroup(){

    }
}
