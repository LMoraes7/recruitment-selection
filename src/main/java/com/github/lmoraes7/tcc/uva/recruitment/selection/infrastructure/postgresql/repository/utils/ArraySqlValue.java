package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.postgresql.repository.utils;

import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.support.SqlValue;

import java.sql.Array;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public final class ArraySqlValue<T> implements SqlValue {

    public static <T> ArraySqlValue<T> create(final T[] array, final String dbTypeName) {
        return new ArraySqlValue<>(array, dbTypeName);
    }

    public static <T> ArraySqlValue<T> create(final T[] array) {
        return new ArraySqlValue<>(array, determineDbTypeName(array));
    }

    private static <T> String determineDbTypeName(final T[] array) {
        final int index = StatementCreatorUtils.javaTypeToSqlParameterType(array.getClass().componentType());
        return JDBCType.valueOf(index).getName().toLowerCase(Locale.US);
    }

    private T[] array;
    private String dbTypeName;

    private ArraySqlValue(final T[] array, final String dbTypeName) {
        this.array = array;
        this.dbTypeName = dbTypeName;
    }

    public T[] getArray() {
        return array;
    }

    @Override
    public void setValue(final PreparedStatement ps, final int paramIndex) throws SQLException {
        final Array arrayOf = ps.getConnection().createArrayOf(this.dbTypeName, this.array);
        ps.setArray(paramIndex, arrayOf);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ArraySqlValue<?> that = (ArraySqlValue<?>) object;
        return Arrays.equals(array, that.array) && Objects.equals(dbTypeName, that.dbTypeName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dbTypeName);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

}
