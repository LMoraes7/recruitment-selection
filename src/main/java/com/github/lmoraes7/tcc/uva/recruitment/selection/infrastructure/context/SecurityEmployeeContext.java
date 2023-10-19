package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.context;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.model.Employee;

public class SecurityEmployeeContext {
    private static ThreadLocal<Employee> employeeContext = new ThreadLocal<>();

    public static void setContext(final Employee employee) {
        employeeContext.set(employee);
    }

    public static Employee getContext() {
        return employeeContext.get();
    }

    public static void clearContext() {
        employeeContext.remove();
    }

}
