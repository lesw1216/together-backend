package LESW.Together.domain.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;


    public UserDto() {
    }

    public UserDto(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
    }
}
