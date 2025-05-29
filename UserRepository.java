import java.util.*;

public class UserRepository {
    private Map<String, User> userMap = new HashMap<>();

    public boolean addUser(User user) {
        if (userMap.containsKey(user.getUsername())) return false;
        userMap.put(user.getUsername(), user);
        return true;
    }

    public User getUser(String username) {
        return userMap.get(username);
    }

    public boolean removeUser(String username) {
        return userMap.remove(username) != null;
    }
}
