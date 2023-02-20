package LESW.Together.domain.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldErrorMessageOfSignupUser {
    private String username;
    private String password;
    private String userId;
    private String keyNum;
    private String keyValue;

    @Override
    public String toString() {
        return "FieldErrorMessageOfSignupUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", keyNum='" + keyNum + '\'' +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
