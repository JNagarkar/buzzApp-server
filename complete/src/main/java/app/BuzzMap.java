package app;

import app.model.DummyEvent;
import app.model.Event;
import app.model.User;

import java.util.*;

/**
 * Created by jaydatta on 4/17/17.
 */
public class BuzzMap {

    public HashMap<User, BuzzSet> buzzMap = new HashMap<>();

    public HashMap<User, BuzzSet> getBuzzMap() {
        return buzzMap;
    }

    public void setBuzzMap(HashMap<User, BuzzSet> buzzMap) {
        this.buzzMap = buzzMap;
    }

    public BuzzMap(){

    }

    public boolean containsUser(User user){

        if(buzzMap.containsKey(user)){
            return true;
        }
        return false;
    }

    public void addBuzzGroup(Event currentEvent, User sender){

        HashSet<BuzzGroup> hset = buzzMap.get(sender).buzzSet;

        System.out.println("adding buzzgroup");
        BuzzGroup suitableGroup=null;
        /*for(BuzzGroup currentGroup: hset){
            if(currentGroup.event.equals(currentEvent)){
                suitableGroup = currentGroup;
                System.out.println("group:"+currentEvent.getName()+"  already exists");
                return ;
            }
        }*/


        DummyEvent dummyEvent = new DummyEvent();
        dummyEvent.setEventURL(currentEvent.getEventURL());
        dummyEvent.setId(Long.valueOf(currentEvent.getId()));
        dummyEvent.setStartTime(currentEvent.getStartTime());
        dummyEvent.setStartDate(currentEvent.getStartTime());
        dummyEvent.setVenue(currentEvent.getVenue());
        dummyEvent.setName(currentEvent.getName());
        dummyEvent.setImageURL(currentEvent.getEventURL());

//            User sender, ArrayList<User> responderGroup, int signalValidFor, Date responderRespondedAt,Event event
            buzzMap.get(sender).buzzSet.add(new BuzzGroup(sender,new ArrayList<>(),sender.getExpectedTime(),new Date(),dummyEvent));

    }

    public void addUserToBuzzGroup(User sender,Event currentEvent,User responder){
        HashSet<BuzzGroup> hset = buzzMap.get(sender).buzzSet;


        BuzzGroup group=null;
        for(BuzzGroup currentGroup: hset){
            if(currentGroup.event.equals(currentEvent)){
                currentGroup.addResponder(responder);
            }
        }
    }

    public void addUser(User sender) {
        buzzMap.put(sender,new BuzzSet());
    }

    public boolean containsBuzzGroup(Event currentEvent,User sender) {

        HashSet<BuzzGroup> hset = buzzMap.get(sender).buzzSet;

        BuzzGroup suitableGroup=null;
        for(BuzzGroup currentGroup: hset){
            if(currentGroup.event.equals(currentEvent)){
                return true;
            }
        }

        return false;
    }

    public void print(){

        for(Map.Entry<User,BuzzSet> entry: buzzMap.entrySet()){
            System.out.print(entry.getKey().getName()+"  ");
            for(BuzzGroup group: entry.getValue().buzzSet){
                System.out.print("Event:"+group.event.getName()+"  ");
                System.out.print(group.responderGroup.size()+"size");
                for(User user: group.responderGroup){
                    System.out.print(user.getName()+" ");
                }
            }
            System.out.println("");
        }
    }


}
