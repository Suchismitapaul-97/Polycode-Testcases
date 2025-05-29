package app;

import java.util.HashMap;
import java.util.Map;

public class UserManagementApp {

    // User Model
    public static class User {
        private String username;
        private String password;
        private String email;

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getEmail() { return email; }
    }

    // Custom Exception: UserAlreadyExists
    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    // Custom Exception: InvalidCredentials
    public static class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    // Service Layer
    public static class UserService {
        private Map<String, User> userDatabase = new HashMap<>();

        public void registerUser(String username, String password, String email) throws UserAlreadyExistsException {
            if (userDatabase.containsKey(username)) {
                throw new UserAlreadyExistsException("User already exists!");
            }
            userDatabase.put(username, new User(username, password, email));
        }

        public User login(String username, String password) throws InvalidCredentialsException {
            User user = userDatabase.get(username);
            if (user == null || !user.getPassword().equals(password)) {
                throw new InvalidCredentialsException("Invalid username or password!");
            }
            return user;
        }

        public User getUserDetails(String username) {
            return userDatabase.get(username);
        }

        public void clearDatabase() {
            userDatabase.clear();
        }
    }
}
