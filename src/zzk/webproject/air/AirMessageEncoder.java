package zzk.webproject.air;

import zzk.webproject.util.SimpleJsonFormatter;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class AirMessageEncoder implements Encoder.Text<AirMessage> {
//    public AirMessageEncoder() {
//    }

    @Override
    public String encode(AirMessage airMessage) throws EncodeException {
        return SimpleJsonFormatter.toJsonString(airMessage);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {

    }
}
