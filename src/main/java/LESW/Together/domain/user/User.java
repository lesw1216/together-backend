package LESW.Together.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity(name = "users")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String password;
    private String userName;
    private String userRole;

    public User(String userId, String password, String userName, String userRole) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userRole = userRole;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", username='" + userName + '\'' +
                ", role='" + userRole + '\'' +
                '}';
    }
}
