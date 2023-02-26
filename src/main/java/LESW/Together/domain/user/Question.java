package LESW.Together.domain.user;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private Long id;
    private int keyNum;
    private String keyValue;

    @Override
    public String toString() {
        return "Question{" +
                "userId='" + id + '\'' +
                ", keyNum=" + keyNum +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
