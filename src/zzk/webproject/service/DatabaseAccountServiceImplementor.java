/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzk.webproject.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import zzk.webproject.dao.db.Persistents;
import zzk.webproject.pojo.Account;
import zzk.webproject.util.OnlineUserUtil;

public class DatabaseAccountServiceImplementor extends AccountServiceImplementor {

    @Override
    public boolean register(String username, String password) {
        return Persistents.doUpdate("insert into tb_account (username,password) values (?,?)", username, password) > 0;
    }

    @Override
    public boolean login(String username, String password) {
        Account account = Persistents.doQuery("select username,password from tb_account where username=? and password=?", (ResultSet t) -> {
            Account current = null;
            try {
                while (t.next()) {
                    current = new Account();
                    String username1 = t.getString("username");
                    String password1 = t.getString("password");
                    current.setUsername(username1);
                    current.setPassword(password1);
                }
            }catch (SQLException ex) {
                Logger.getLogger(DatabaseAccountServiceImplementor.class.getName()).log(Level.SEVERE, null, ex);
            }
            return current;
        }, username, password);
        return Objects.nonNull(account);
    }
    @Override
    public boolean deleteAccount(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
