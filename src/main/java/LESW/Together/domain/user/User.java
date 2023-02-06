package LESW.Together.domain.user;

import lombok.Getter;

@Getter
public class User {
    private String userId;
    private String password;
    private String username;

    public User(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
