package app;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Proxy(lazy = false)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String name;

    private String email;

	private Double latitude;

	public Date getLatestUpdated() {
		return latestUpdated;
	}

	public void setLatestUpdated(Date latestUpdated) {
		this.latestUpdated = latestUpdated;
	}

	private Date latestUpdated;




	public User(String name, String email, String contactNumber, Double latitude, Double longitude , Integer expectedTime, Integer radius, Date latestUpdated) {

		Calendar calendar = Calendar.getInstance();
		this.name = name;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.contactNumber = contactNumber;
		this.expectedTime = expectedTime;
		this.radius = radius;
		this.latestUpdated = calendar.getTime();
	}




	public User(){

	}


	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	private Double longitude;

	private String contactNumber;

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

	private Integer expectedTime;

	private Integer radius;




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
				", longitude=" + longitude +
				", contactNumber='" + contactNumber + '\'' +
				", expectedTime=" + expectedTime +
				", radius=" + radius +
				'}';
	}
}

