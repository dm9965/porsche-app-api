package persistence;

import model.User;

import java.io.IOException;
import java.util.ArrayList;

public interface UserDAO {
    User createUser (User user) throws IOException;
    User findByUsername(String username) throws IOException;
    User updateUser(User user) throws IOException;
    boolean isAdmin(String username, String password) throws IOException;
    boolean isUser(String username, String password) throws IOException;

    ArrayList<User> getUsers(String userInfo) throws IOException;
    ArrayList<User> getUsers() throws IOException;
}
