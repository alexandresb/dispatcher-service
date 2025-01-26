package com.polarbookshop.dispatcherservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
//import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
//import org.springframework.context.annotation.Import;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//Remplacé par @EnableTestBinder
//@Import(TestChannelBinderConfiguration.class) //configuration du binder de test
@EnableTestBinder
public class FunctionStreamIntegrationTests {
    //injection de la représentation de l'input binding packlabel-in-0
    @Autowired
    private InputDestination inputDestination;
    //injection de la représentation de l'output binding packlabel-out-0
    @Autowired
    private OutputDestination outputDestination;

    @Autowired //pour mapper un message JSON avec un objet Java
    ObjectMapper objectMapper;

    @Test
    void whenOrderAcceptedThenOrderDispatched() throws IOException {
        Long orderId = 121L;
        //construction du Message d'entrée utilisé /exploitable par SCS
        Message<OrderAcceptedMessage> inputMessage = MessageBuilder
                .withPayload(new OrderAcceptedMessage(orderId))
                .build();
        //construction du message de sortie attendu
        Message<OrderDispatchedMessage> expectedOutputMessage = MessageBuilder
                .withPayload(new OrderDispatchedMessage(orderId))
                .build();
        //envoi du message dans la destination gérée par le binder de test.
        inputDestination.send(inputMessage);
        //vérification de
        assertThat(
              //  conversion en OrderDispatchedMessage du payload du massage de sortie Json reçu par la destination de sortie Rabbit
                objectMapper.readValue(
                        outputDestination.receive().getPayload(), OrderDispatchedMessage.class)
        ).isEqualTo(expectedOutputMessage.getPayload());

    }

}
