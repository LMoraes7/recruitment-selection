package com.github.lmoraes7.tcc.uva.recruitment.selection.utils;

import com.github.lmoraes7.tcc.uva.recruitment.selection.application.generator.GeneratorIdentifier;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Candidate;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Function;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Profile;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.constants.Functionality;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.AccessCredentials;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.Address;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.PersonalData;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.vo.Phone;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.converter.ConverterHelper;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.entity.CandidateEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.candidate.query.CandidateCommands;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.entity.EmployeeEntity;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.query.EmployeeCommands;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.converter.ConverterHelper.toEntity;
import static com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.profile.query.ProfileCommands.SAVE;

public final class TestItUtils {

    public static Employee generateEmployee() {
        return new Employee(
                GeneratorIdentifier.forEmployee(),
                new PersonalData(
                        "Fernando Costa da Silva",
                        "21314335065",
                        "email@email.com.br",
                        LocalDate.now(),
                        new Phone(
                                "21",
                                "947884078"
                        ),
                        new Address(
                                "Avenida Atlântica",
                                570,
                                "APT 304",
                                "Copacabana",
                                "Rio de Janeiro",
                                "RJ",
                                "10856894"
                        )
                ),
                new AccessCredentials(
                        "email@email.com",
                        "hhKJ6hui3%8u&2$jgJhjbJugj",
                        Set.of(
                                new Profile(
                                        GeneratorIdentifier.forProfile(),
                                        "ADM_PROFILE",
                                        Set.of(
                                                new Function(
                                                        GeneratorIdentifier.forProfile(),
                                                        Functionality.FUNC_CREATE_EMPLOYEE
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Candidate generateCandidate() {
        return new Candidate(
                GeneratorIdentifier.forCandidate(),
                new PersonalData(
                        "Fernando Costa da Silva",
                        "21314335065",
                        "email@email.com.br",
                        LocalDate.now(),
                        new Phone(
                                "21",
                                "947884078"
                        ),
                        new Address(
                                "Avenida Atlântica",
                                570,
                                "APT 304",
                                "Copacabana",
                                "Rio de Janeiro",
                                "RJ",
                                "10856894"
                        )
                ),
                new AccessCredentials(
                        "email@email.com",
                        "hhKJ6hui3%8u&2$jgJhjbJugj",
                        Set.of(
                                new Profile(
                                        GeneratorIdentifier.forProfile(),
                                        "CAN_PROFILE",
                                        Set.of(
                                                new Function(
                                                        GeneratorIdentifier.forProfile(),
                                                        Functionality.FUNC_CREATE_EMPLOYEE
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static Profile generateProfileWithoutFunctions() {
        return new Profile(
                GeneratorIdentifier.forProfile(),
                "TEST_PROFILE",
                null
        );
    }

    public static Employee generateEmployee(final Set<Profile> profiles) {
        return new Employee(
                GeneratorIdentifier.forEmployee(),
                new PersonalData(
                        "Fernando Costa da Silva",
                        "21314335065",
                        "email@email.com.br",
                        LocalDate.now(),
                        new Phone(
                                "21",
                                "947884078"
                        ),
                        new Address(
                                "Avenida Atlântica",
                                570,
                                "APT 304",
                                "Copacabana",
                                "Rio de Janeiro",
                                "RJ",
                                "10856894"
                        )
                ),
                new AccessCredentials(
                        "email@email.com",
                        "hhKJ6hui3%8u&2$jgJhjbJugj",
                        profiles
                )
        );
    }

    public static void saveFunctions(
            final JdbcTemplate jdbcTemplate,
            final Collection<Function> functions
    ) {
        functions.forEach(it ->
            jdbcTemplate.update(
                    "insert into functions (id, name) values (?, ?)",
                    it.getIdentifier(),
                    it.getName().name()
            )
        );
    }

    public static void saveProfiles(
            final JdbcTemplate jdbcTemplate,
            final Collection<Profile> profiles
    ) {
        profiles.forEach(it ->
                jdbcTemplate.update(
                        SAVE.sql,
                        it.getIdentifier(),
                        it.getName()
                )
        );
    }

    public static void saveProfileAndFunctions(
            final JdbcTemplate jdbcTemplate,
            final Profile profile
    ) {
        profile.getFunctions().forEach(function ->
                jdbcTemplate.update(
                        "insert into functions (id, name) values (?, ?)",
                        function.getIdentifier(),
                        function.getName().name()
                )
        );

        jdbcTemplate.update(
                SAVE.sql,
                profile.getIdentifier(),
                profile.getName()
        );
    }

    public static void saveEmployee(
            final JdbcTemplate jdbcTemplate,
            final Employee employee
    ) {
        final EmployeeEntity employeeEntity = toEntity(employee);

        jdbcTemplate.update(
                EmployeeCommands.SAVE.sql,
                employeeEntity.getIdentifier(),
                employeeEntity.getPersonalData().getName(),
                employeeEntity.getPersonalData().getCpf(),
                employeeEntity.getPersonalData().getEmail(),
                employeeEntity.getPersonalData().getDateOfBirth(),
                employeeEntity.getPersonalData().getPhone(),
                employeeEntity.getPersonalData().getAddress(),
                employeeEntity.getAccessCredentials().getUsername(),
                employeeEntity.getAccessCredentials().getPassword()
        );
    }

    public static void saveCandidate(
            final JdbcTemplate jdbcTemplate,
            final Candidate candidate
    ) {
        final CandidateEntity candidateEntity = ConverterHelper.toEntity(candidate);

        jdbcTemplate.update(
                CandidateCommands.SAVE.sql,
                candidateEntity.getIdentifier(),
                candidateEntity.getPersonalData().getName(),
                candidateEntity.getPersonalData().getCpf(),
                candidateEntity.getPersonalData().getEmail(),
                candidateEntity.getPersonalData().getDateOfBirth(),
                candidateEntity.getPersonalData().getPhone(),
                candidateEntity.getPersonalData().getAddress(),
                candidateEntity.getAccessCredentials().getUsername(),
                candidateEntity.getAccessCredentials().getPassword()
        );
    }

}
