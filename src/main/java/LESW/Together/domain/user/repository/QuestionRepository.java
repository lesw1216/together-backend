package LESW.Together.domain.user.repository;

import LESW.Together.domain.user.Question;

import java.util.Optional;

public interface QuestionRepository {

    // 저장
    Question createQuestion(Question question);

    Optional<Question> readQuestion(Long userIndexId);
}
