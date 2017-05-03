package app.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Proxy(lazy = false)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String name;

    private String email;

    private Double latitude;
    private Date latestUpdated;
    private String token;
    private Double longitude;
    private String contactNumber;
    private Integer expectedTime;
    private Integer radius;


    public User(String name, String email, String contactNumber, Double latitude, Double longitude, Integer expectedTime, Integer radius, Date latestUpdated, String token) {

        Calendar calendar = Calendar.getInstance();
        this.name = name;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
        this.expectedTime = expectedTime;
        this.radius = radius;
        this.latestUpdated = calendar.getTime();
        this.token = token;
    }

    public User() {

    }

    public Date getLatestUpdated() {
        return latestUpdated;
    }

    public void setLatestUpdated(Date latestUpdated) {
        this.latestUpdated = latestUpdated;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Integer getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Integer expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", latitude=" + latitude +
                ", latestUpdated=" + latestUpdated +
                ", token='" + token + '\'' +
                ", longitude=" + longitude +
                ", contactNumber='" + contactNumber + '\'' +
                ", expectedTime=" + expectedTime +
                ", radius=" + radius +
                '}';
    }
}

