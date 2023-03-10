package LESW.Together.domain.user.repository.jdbcTemplate;

import LESW.Together.domain.user.User;
import LESW.Together.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
public class UserJdbcTemplateRepository implements UserRepository {

    private final NamedParameterJdbcTemplate template;

    @Autowired
    public UserJdbcTemplateRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User createUser(User user) {
        String sql = "insert into users (user_id, password, user_name, user_role) values " +
                "(:userId, :password, :username, :userRole)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();


//        MapSqlParameterSource params = new MapSqlParameterSource()
//                .addValue("id", user.getId())
//                .addValue("userId", user.getUserId())
//                .addValue("password", user.getPassword())
//                .addValue("username", user.getUsername())
//                .addValue("role", user.getRole());
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);

        template.update(sql, params, keyHolder);
        long key = Objects.requireNonNull(keyHolder.getKey()).longValue();
        user.setId(key);
        log.info("user.setId={}", user.getId());
        return user;
    }

    @Override
    public Optional<User> readUser(Long id) {
        String sql = "select id, user_id, password, user_name, user_role from users where id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);

        User findUserById = template.queryForObject(sql, params, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserId(rs.getString("user_id"));
            user.setPassword(rs.getString("password"));
            user.setUserName(rs.getString("user_name"));
            user.setUserRole(rs.getString("user_role"));
            return user;
        });

        return Optional.ofNullable(findUserById);
    }

    // ???????????? ???????????? ?????? ?????? ?????? ??????
    // ?????? ????????? ?????? ?????? ?????? ??????
    @Override
    public List<User> readAllUser() {
        return null;
    }

    @Override
    public void updateUser(Long id, User updateUser) {
        String sql = "update users set id=:id, user_id=:userId, password=:password" +
                ", user_name=:username, user_role=:userRole where id=:id";

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(updateUser);
        int update = template.update(sql, parameterSource);
        log.info("????????? ?????? ??????=[{}]", update);
    }

    @Override
    public void deleteUser(Long id) {
        String sql = "delete from users where id=:id";
        template.update(sql, new MapSqlParameterSource("id", id));
    }


    public Optional<User> findUserByUserId(String userId) {
        log.info("userId={}", userId);
        String sql = "select id, user_id, password, user_name, user_role from users where user_id=:userId";

        User findUserByUserId = template.queryForObject(sql, new MapSqlParameterSource("userId", userId), (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserId(rs.getString("user_id"));
            user.setPassword(rs.getString("password"));
            user.setUserName(rs.getString("user_name"));
            user.setUserRole(rs.getString("user_role"));
            return user;
        });

        return Optional.of(Objects.requireNonNull(findUserByUserId));
    }
}
