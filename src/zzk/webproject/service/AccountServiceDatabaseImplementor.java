/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzk.webproject.service;

import zzk.webproject.dao.db.Persistence;

public class AccountServiceDatabaseImplementor extends AccountServiceImplementor {

    @Override
    public boolean register(String username, String password) {
        return Persistence.doUpdate("insert into tb_account (username,password) values (?,?)", username, password) > 0;
    }

    @Override
    public boolean login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteAccount(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
