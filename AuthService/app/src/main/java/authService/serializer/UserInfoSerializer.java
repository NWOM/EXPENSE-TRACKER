package authService.serializer;

import authService.model.UserInfoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDTO>
{

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String arg0, UserInfoDTO arg1) {
        byte[] retVal=null;
        ObjectMapper objectMapper=new ObjectMapper();
        try{
            retVal= objectMapper.writeValueAsString(arg1).getBytes();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return retVal;
    }
    @Override
    public void close() {
    }
}
/**
 * This class is a custom serializer for Kafka that converts a 'UserInfoEvent'
 * object into a byte array which kafka can then use to transmit the data
 */