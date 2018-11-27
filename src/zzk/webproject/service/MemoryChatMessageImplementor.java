package zzk.webproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import zzk.webproject.air.AirMessage;

public class MemoryChatMessageImplementor extends ChatMessageServiceImplementor {

    @Override
    public int save(AirMessage message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isExist(int messageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AirMessage get(int messageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AirMessage> list(Predicate<AirMessage> messageFliter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
