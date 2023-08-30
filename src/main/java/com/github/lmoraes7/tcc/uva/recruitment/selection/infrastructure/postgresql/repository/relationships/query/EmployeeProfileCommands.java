package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.relationships.query;

public enum EmployeeProfileCommands {
    SAVE("insert into employees_profiles (id_employee, id_profile) values (?, ?)");

    public final String sql;

    EmployeeProfileCommands(String sql) {
        this.sql = sql;
    }
}
