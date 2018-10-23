/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzk.webproject.service;

/**
 *
 * @author uestc
 */
public class FacilitatedTestingAccountServiceImplementor extends MemoryAccountServiceImplementor {

    @Override
    public synchronized boolean login(String username, String password) {
        if(notExistUsername(username)){
            super.register(username, password);
        }
        return super.login(username, password); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
