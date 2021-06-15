package com.project.authentication.utils;

import com.project.authentication.constants.Constants;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerProducerUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(ConsumerProducerUtil.class);


    @Bean
    ConnectionFactory connectionFactory(){
        return new ConnectionFactory();
    }

    @Bean
    Connection connection() throws IOException, TimeoutException {
        return  connectionFactory().newConnection();
    }

    @Bean
    Channel channel() throws IOException, TimeoutException {
        Channel channel = connection().createChannel();
        channel.queueDeclare(Constants.CUSTOMER_ACCOUNT_DETAILS_QUEUE, false, false, false, null);
        return channel;
    }

    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private Connection connectioninit;
    @Autowired
    private Channel channelinit;

    public String consumeMessage() throws IOException, TimeoutException{

        GetResponse response = channel().basicGet(Constants.CUSTOMER_ACCOUNT_DETAILS_QUEUE,false);
        if(response != null){
            byte[] body = response.getBody();
            return new String(body);
        }
        else{
            return "no message was found on queue";
        }
    }

    public void produceMessage(String details) throws IOException, TimeoutException {
        channel().basicPublish("", Constants.CUSTOMER_ACCOUNT_DETAILS_QUEUE,false,null,details.getBytes());
        LOGGER.debug("!!!Message has been sent" + details);
    }
}
