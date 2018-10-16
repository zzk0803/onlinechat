package zzk.webproject.everythingelse;

import zzk.webproject.dao.Persistents;
import zzk.webproject.pojo.Account;

import java.sql.SQLException;

public class Calculators {
    public static void main(String[] args) {

        Account account = Persistents.<Account>doQuery(
                "select * from tb_user",
                resultSet -> {
                    try {
                        String string = resultSet.getString(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return new Account();
                },
                new Object()
        );

        int update = Persistents.doUpdate(
                "insert into tb_user (username,password) values (?,?)",
                new Object()
        );
    }
}
