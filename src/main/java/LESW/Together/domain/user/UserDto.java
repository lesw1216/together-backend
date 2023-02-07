package LESW.Together.domain.user;

import lombok.Getter;

@Getter
public class UserDto {

    private String userId;
    private String password;
    private String username;

    public UserDto() {
    }

    public UserDto(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }
}
