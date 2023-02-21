package LESW.Together.domain.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Question {

    private Long id;
    private int keyNum;
    private String keyValue;

    public Question(Long userIndexId, int keyNum, String keyValue) {
        this.id = userIndexId;
        this.keyNum = keyNum;
        this.keyValue = keyValue;
    }

    public Question() {
    }

    @Override
    public String toString() {
        return "Question{" +
                "userId='" + id + '\'' +
                ", keyNum=" + keyNum +
                ", keyValue='" + keyValue + '\'' +
                '}';
    }
}
