package app;

import java.util.List;

/**
 * Created by jaydatta on 3/18/17.
 */
public interface IUserDao {

    public List<User> getAllUsers();
    public List<User> getAdjacentUsers(List<User> allUsers,Double latitude,Double longitude, Integer radius);
    public List<User> getRespondedUsers();

}
