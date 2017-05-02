package app;

import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by jaydatta on 4/20/17.
 */
@Entity // This tells Hibernate to make a table out of this class
@Proxy(lazy = false)
public class UserPersonalization {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    public long id;
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String userID;
    public String category;

    public UserPersonalization(String userID, String category) {
        this.userID = userID;
        this.category = category;
    }

    public UserPersonalization(){

    }
}
