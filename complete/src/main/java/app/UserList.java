package app;

import app.model.User;

import java.util.ArrayList;

/**
 * Created by jaydatta on 4/17/17.
 */
public class UserList {

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public UserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public UserList(){

    }

    public ArrayList<User> userList;
}
