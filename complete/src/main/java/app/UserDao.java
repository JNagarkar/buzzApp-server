package app;

import app.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jaydatta on 3/18/17.
 */

@Service
public class UserDao implements IUserDao {

    public UserDao() {
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<User> getAdjacentUsers(List<User> allUsers, Double latitude, Double longitude, Integer radius) {
        return null;
    }

    @Override
    public List<User> getRespondedUsers() {
        return null;
    }
}
