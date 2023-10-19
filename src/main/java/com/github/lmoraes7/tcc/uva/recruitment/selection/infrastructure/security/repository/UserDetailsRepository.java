package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository;

import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.vo.UserWithAcessProfileWrapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.security.repository.converter.ConverterHelper.entityWrapperToUserDetails;

@Repository
public class UserDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserDetailsRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserDetails> findEmployeeByIdWithAccessProfile(final String id) {
        final var userEntities = this.jdbcTemplate.query(
                "select " +
                        "e.id as employee_id,  " +
                        "e.username as employee_username, " +
                        "f.id as function_id, " +
                        "f.name as function_name " +
                    "from employees e " +
                            "inner join employees_profiles ep " +
                                "on e.id = ep.id_employee " +
                            "inner join profiles_functions pf " +
                                "on ep.id_profile on pf.id_profile " +
                            "inner join functions f " +
                                "on pf.id_function = f.id " +
                    "where e.id = ?",
                (rs, index) -> new UserWithAcessProfileWrapper(
                        rs.getString("employee_id"),
                        rs.getString("employee_username"),
                        null,
                        rs.getString("function_id"),
                        rs.getString("function_name")
                ),
                id
        );

        if (userEntities.isEmpty())
            return Optional.empty();

        return Optional.of(entityWrapperToUserDetails(userEntities));
    }

    public Optional<UserDetails> findCandidateByIdWithAccessProfile(final String id) {
        final var userEntities = this.jdbcTemplate.query(
                "select " +
                        "c.id as candidate_id,  " +
                        "c.username as candidate_username, " +
                        "f.id as function_id, " +
                        "f.name as function_name " +
                        "from candidates c " +
                        "inner join candidates_profiles ep " +
                        "on c.id = ep.id_candidate " +
                        "inner join profiles_functions pf " +
                        "on ep.id_profile on pf.id_profile " +
                        "inner join functions f " +
                        "on pf.id_function = f.id " +
                        "where c.id = ?",
                (rs, index) -> new UserWithAcessProfileWrapper(
                        rs.getString("candidate_id"),
                        rs.getString("candidate_username"),
                        null,
                        rs.getString("function_id"),
                        rs.getString("function_name")
                ),
                id
        );

        if (userEntities.isEmpty())
            return Optional.empty();

        return Optional.of(entityWrapperToUserDetails(userEntities));
    }

    public Optional<UserDetails> findCandidateByUsername(final String username) {
        final var userEntities = this.jdbcTemplate.query(
                "select " +
                        "c.id as candidate_id,  " +
                        "c.username as candidate_username, " +
                        "c.password as candidate_password, " +
                        "f.id as function_id, " +
                        "f.name as function_name " +
                        "from candidates c " +
                        "inner join candidates_profiles ep " +
                        "on c.id = ep.id_candidate " +
                        "inner join profiles_functions pf " +
                        "on ep.id_profile on pf.id_profile " +
                        "inner join functions f " +
                        "on pf.id_function = f.id " +
                        "where c.username = ?",
                (rs, index) -> new UserWithAcessProfileWrapper(
                        rs.getString("candidate_id"),
                        rs.getString("candidate_username"),
                        rs.getString("candidate_password"),
                        rs.getString("function_id"),
                        rs.getString("function_name")
                ),
                username
        );

        if (userEntities.isEmpty())
            return Optional.empty();

        return Optional.of(entityWrapperToUserDetails(userEntities));
    }

    public Optional<UserDetails> findEmployeeByUsername(final String username) {
        final var userEntities = this.jdbcTemplate.query(
                "select " +
                        "e.id as employee_id,  " +
                        "e.username as employee_username, " +
                        "e.password as employee_password, " +
                        "f.id as function_id, " +
                        "f.name as function_name " +
                        "from employees e " +
                        "inner join employees_profiles ep " +
                        "on e.id = ep.id_employee " +
                        "inner join profiles_functions pf " +
                        "on ep.id_profile on pf.id_profile " +
                        "inner join functions f " +
                        "on pf.id_function = f.id " +
                        "where e.username = ?",
                (rs, index) -> new UserWithAcessProfileWrapper(
                        rs.getString("employee_id"),
                        rs.getString("employee_username"),
                        rs.getString("employee_password"),
                        rs.getString("function_id"),
                        rs.getString("function_name")
                ),
                username
        );

        if (userEntities.isEmpty())
            return Optional.empty();

        return Optional.of(entityWrapperToUserDetails(userEntities));
    }

}
