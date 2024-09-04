package com.sagnik.userService.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sagnik.userService.entities.UserInfoDto;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfoDto> {
    @Override
    public void configure(Map<String, ?> configs, boolean arg1) {
    }

    @Override
    public UserInfoDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper objectMapper=new ObjectMapper();
        UserInfoDto user=null;
        try{
            user=objectMapper.readValue(arg1,UserInfoDto.class);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return user;
    }


    @Override
    public void close() {
    }
}
