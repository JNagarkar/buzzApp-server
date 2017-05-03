package app.helper;

/**
 * Created by jaydatta on 4/15/17.
 */
public class EventFetcher {

    public String latitude;
    public String longitude;
    public String radius;
    public String userID;

    public EventFetcher() {

    }

    public EventFetcher(String latitude, String longitude, String radius, String userID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.userID = userID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "EventFetcher{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", radius='" + radius + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
