package persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserFileDAO implements UserDAO {
    final Map<String, User> userMap = new HashMap<>();
    private final ObjectMapper objectMapper;
    private final String filename;
    ArrayList<User> userList = new ArrayList<>();

    public UserFileDAO(@Value("${dao.users}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    @Override
    public User createUser(User user) throws IOException {
        synchronized (userMap) {
            if (userMap.containsKey(user.getUsername())) {
                throw new FileAlreadyExistsException("Username already exists");
            }
            User newUser = new User();
            newUser.setFullName(user.getFullName());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
            userMap.put(newUser.getUsername(), newUser);
            save();
            return newUser;
        }
    }

    @Override
    public User findByUsername(String username) throws IOException {
        return null;
    }

    @Override
    public User updateUser(User updateUser) throws IOException {
        synchronized (userMap) {
            if (!userMap.containsKey(updateUser.getUsername())) {
                throw new FileNotFoundException("User does not exist.");
            }
            User currentUser = userMap.get(updateUser.getUsername()) ;
            if (updateUser.getFullName() != null) currentUser.setFullName(updateUser.getFullName());
            if (updateUser.getUsername() != null) currentUser.setUsername(updateUser.getUsername());
            if (updateUser.getPassword() != null) currentUser.setPassword(updateUser.getPassword());

            userMap.put(currentUser.getUsername(), currentUser);
            save();
            return currentUser;
        }
    }

    @Override
    public boolean isAdmin(String username, String password) throws IOException {
        return username.toLowerCase().contains("admin name");
    }

    @Override
    public boolean isUser(String username, String password) throws IOException {
        return !this.isAdmin(username, password);
    }

    @Override
    public ArrayList<User> getUsers(String userInfo) throws IOException {
        ArrayList<User> userArrayList = new ArrayList<>();
        if (userInfo == null) {
            userArrayList.addAll(userMap.values());
        }
        else {
            for (User user : userMap.values()) {
                if (user.toString().contains(userInfo)) {
                    userArrayList.add(user);
                }
            }
        }
        return userArrayList;
    }

    @Override
    public ArrayList<User> getUsers() throws IOException {
        return getUsers(null);
    }
    private boolean save() throws IOException {
        userList = getUsers();
        objectMapper.writeValue(new File(filename), userList);
        return true;
    }
    private void load() throws IOException {
        User[] users = objectMapper.readValue(new File(filename), User[].class);
        for (User user : users) {
            userMap.put(user.getUsername(), user);
        }
    }
}
