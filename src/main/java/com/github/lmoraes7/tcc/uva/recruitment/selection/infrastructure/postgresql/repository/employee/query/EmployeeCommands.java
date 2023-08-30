package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.employee.query;

public enum EmployeeCommands {
    SAVE("insert into employees (id, name, cpf, email, date_of_birth, phones, adresses, username, password) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

    public final String sql;

    EmployeeCommands(String sql) {
        this.sql = sql;
    }
}
