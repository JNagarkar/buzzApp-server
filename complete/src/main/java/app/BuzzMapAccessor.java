package app;

/**
 * Created by jaydatta on 4/17/17.
 */
public class BuzzMapAccessor {
    public  static BuzzMapAccessor ourInstance = new BuzzMapAccessor();

    public static BuzzMapAccessor getInstance() {
        return ourInstance;
    }

    public static BuzzMap map = new BuzzMap();

    public BuzzMap getMap(){
        return map;
    }

    private BuzzMapAccessor() {

    }
}
