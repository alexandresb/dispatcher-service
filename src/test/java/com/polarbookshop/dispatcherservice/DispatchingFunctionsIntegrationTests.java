package com.polarbookshop.dispatcherservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Function;

@Disabled("useful and functional when testing alone function without binding")
@FunctionalSpringBootTest//activation du contexte d'exécution des fonctions managées par Spring Cloud Function
public class DispatchingFunctionsIntegrationTests {

    @Autowired //injection du catalogue des fonctions gérées par SCF
    private FunctionCatalog catalog;

    @Test
    public void packAndLabelOrder(){
        //récupération de la fonction composée par SCF
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel =
                catalog.lookup(Function.class, "pack|label");
        Long orderId = 121L;

        StepVerifier.create(packAndLabel.apply(new OrderAcceptedMessage(orderId) //message d'entrée passée à la fonction composée
                ))//vérification que le message de sortie est bien celui attendu
                .expectNextMatches( dispatchedOrderMessage->
                    dispatchedOrderMessage.equals(new OrderDispatchedMessage(orderId))
                ).verifyComplete();

    }

}
