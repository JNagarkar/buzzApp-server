package app.controller;

import app.UserDao;
import app.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by jaydatta on 3/18/17.
 */


@Controller
@RequestMapping(value = "/broadCast")
public class BroadCastController {

    public Logger logger = LogManager.getLogger(BroadCastController.class);
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/close/{radius}")
    @ResponseBody
    public List<User> getNearbyUsers(Double latitude, Double longitude, Integer radius) {
        List<User> allUsers = null;
        List<User> nearbyUsers = null;
        try {
            allUsers = userDao.getAllUsers();
            nearbyUsers = userDao.getAdjacentUsers(allUsers, latitude, longitude, radius);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return nearbyUsers;
    }

}
