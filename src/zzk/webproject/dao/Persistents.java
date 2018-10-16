package zzk.webproject.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Persistents {
    private static final String DB_URL = "jdbc:mysql://localhost:5060/webproject";
    private static final String DB_DRIVER_NAME = "org.mysql.driver.Driver";
    private static final String DB_USERNAME = "zhangzhike";
    private static final String DB_PASSWORD = "123456";

    private static final ThreadLocal<Connection> DB_CONNECTION_THREADLOCAL = new ThreadLocal<>();

    private Persistents() {

    }

    public static int doUpdate(String sql, Object... parameters) {
        Connection connection = get();
        int update = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int index = 0; parameters.length > 0 && index < parameters.length; index++) {
                preparedStatement.setObject(index, parameters[index]);
            }
            update = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            erasure(connection);
        }
        return update;
    }

    public static <POJO> POJO doQuery(String sql, Function<ResultSet, POJO> resultSetFunction, Object... parameters) {
        Connection connection = get();
        ResultSet resultSet = null;
        POJO result = null;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            for (int index = 0; parameters.length > 0 && index < parameters.length; index++) {
                prepareStatement.setObject(index, parameters[index]);
            }
            resultSet = prepareStatement.executeQuery();
            result = resultSetFunction.apply(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(resultSet)) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            erasure(connection);
        }
        return result;
    }

    public static Connection get() {
        Connection connection = DB_CONNECTION_THREADLOCAL.get();
        if (connection == null) {
            try {
                Class.forName(DB_DRIVER_NAME);
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                DB_CONNECTION_THREADLOCAL.set(connection);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void erasure(Connection connection) {
        if (connection == DB_CONNECTION_THREADLOCAL.get()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB_CONNECTION_THREADLOCAL.remove();
        }
    }
}
