package LESW.Together.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserDto {

    @NotBlank
    private String userId;

    @NotBlank
    private String password;


    public UserDto() {
    }

    public UserDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
