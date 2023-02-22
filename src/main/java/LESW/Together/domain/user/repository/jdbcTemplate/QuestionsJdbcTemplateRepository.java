package LESW.Together.domain.user.repository.jdbcTemplate;

import LESW.Together.domain.user.Question;
import LESW.Together.domain.user.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Slf4j
@Repository
public class QuestionsJdbcTemplateRepository implements QuestionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public QuestionsJdbcTemplateRepository(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Question createQuestion(Question question) {
        String sql = "insert into questions (id, key_num, key_value) values (:userIndexId, :keyNum, :keyValue)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("userIndexId", question.getId())
                .addValue("keyNum", question.getKeyNum())
                .addValue("keyValue", question.getKeyValue());

        namedParameterJdbcTemplate.update(sql, params);
        return question;
    }

    @Override
    public Optional<Question> readQuestion(Long userIndexId) {
        String sql = "select id, key_num, key_value from questions where id=:userIndexId";
        MapSqlParameterSource param = new MapSqlParameterSource("userIndexId", userIndexId);
        Question findQuestionById = namedParameterJdbcTemplate.queryForObject(sql, param, Question.class);
        return Optional.ofNullable(findQuestionById);
    }
}
