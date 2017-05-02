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

}
