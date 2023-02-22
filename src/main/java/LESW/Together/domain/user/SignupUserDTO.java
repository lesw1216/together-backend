package LESW.Together.domain.user;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 회원가입시 객체를 받을 DTO
 */
@Getter
public class SignupUserDTO {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력주세요.")
    private String username;

    @NotNull(message = "질문을 선택해주세요.")
    @Range(min=0, max = 3)
    private Integer keyNum;
    @NotBlank(message = "질문의 답을 입력해주세요.")
    private String keyValue;

    public SignupUserDTO() {
    }

    public SignupUserDTO(String userId, String password, String username, Integer keyNum, String keyValue) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.keyNum = keyNum;
        this.keyValue = keyValue;
    }

    @Override
    public String toString() {
        return "SignUpUserDTO{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", keyNum=" + keyNum +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
