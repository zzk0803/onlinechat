package zzk.webproject.air;

import zzk.webproject.util.SimpleJsonFormatter;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class AirMessageDecoder implements Decoder.Text<AirMessage> {
//    public AirMessageDecoder() {
//    }

    @Override
    public AirMessage decode(String s) throws DecodeException {
        return SimpleJsonFormatter.fromJsonToObject(s, AirMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
