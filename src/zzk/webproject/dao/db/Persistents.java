package zzk.webproject.dao.db;

import zzk.webproject.pojo.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Persistents {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/webproject";
    private static final String DB_DRIVER_NAME = "org.mysql.driver.Driver";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";

    private static final ThreadLocal<Connection> DB_CONNECTION_THREADLOCAL = new ThreadLocal<>();

    private Persistents() {

    }

    public static int doUpdate(String preparedStatementSql, Object... parameters) {
        Connection connection = get();
        int update = -1;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(preparedStatementSql);
            for (int index = 0; parameters.length > 0 && index < parameters.length; index++) {
                preparedStatement.setObject(index, parameters[index]);
            }
            update = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            erasure();
        }
        return update;
    }

    public static <POJO> List<POJO> doQuery(Class<POJO> mapToClazz, Function<ResultSet, POJO> singleRSFunction, String prepareStatementSql, Object... parameters) {
        List<POJO> pojoList = new ArrayList<>();
        Connection connection = get();
        ResultSet resultSet = null;
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(prepareStatementSql);
            for (int index = 0; parameters.length > 0 && index < parameters.length; index++) {
                prepareStatement.setObject(index, parameters[index]);
            }
            resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                POJO singlePOJO = singleRSFunction.apply(resultSet);
                pojoList.add(singlePOJO);
            }
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
            erasure();
        }
        return pojoList;
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

    public static void erasure() {
        Connection connection = DB_CONNECTION_THREADLOCAL.get();
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB_CONNECTION_THREADLOCAL.remove();
        }
    }

    public static void main(String[] args) {
        List<Account> accounts = doQuery(Account.class, resultSet -> new Account(), "select * from tb_account");
    }
}
