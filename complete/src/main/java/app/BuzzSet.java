package app;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by jaydatta on 4/17/17.
 */
public class BuzzSet {

    public HashSet<BuzzGroup> getBuzzSet() {
        return buzzSet;
    }

    public void setBuzzSet(HashSet<BuzzGroup> buzzSet) {
        this.buzzSet = buzzSet;
    }

    public BuzzSet(HashSet<BuzzGroup> buzzSet) {
        this.buzzSet = buzzSet;
    }

    public HashSet<BuzzGroup> buzzSet;

    public BuzzSet(){
    this.buzzSet = new HashSet<>();
    }


}
