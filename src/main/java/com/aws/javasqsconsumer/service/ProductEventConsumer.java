package com.aws.javasqsconsumer.service;

import com.aws.javasqsconsumer.model.ProductEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Service
public class ProductEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(ProductEventConsumer.class);

    private ObjectMapper objectMapper;

    private ProductEvent productEvent;

    @Autowired
    public ProductEventConsumer(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${aws.sqs.queue.product.events.name}")
    public void receiveProductEvent(TextMessage textMessage)
        throws JMSException, IOException {
        try{

            LOG.info("Mensagem Recebida: {}", textMessage.getText());
            //LOG.info("Texto da Mensagem: {}",objectMapper.readValue(textMessage.getText().toString(), ProductEvent.class));
            ProductEvent productEvent = objectMapper.readValue(textMessage.getText(), ProductEvent.class);
            LOG.info("Classe preenchida após o Parse do Json: {}", productEvent.toString());

        } catch (Exception e){
            throw new RuntimeException("Erro na recepção da Mensagem: ", e);
        }
        //SnsMessage snsMessage = objectMapper.readValue(textMessage.getText(), SnsMessage.class);

        //Envelope envelope = objectMapper.readValue(snsMessage.getMessage(), Envelope.class);

        //ProductEvent productEvent = objectMapper.readValue((JsonParser) textMessage, ProductEvent.class);

        //LOG.info("Product Event Received - Code: {} - Description: {}", productEvent.getCode(), productEvent.getDescription());

    }
}
