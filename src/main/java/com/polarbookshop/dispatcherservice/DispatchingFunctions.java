package com.polarbookshop.dispatcherservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchingFunctions {
    private static final Logger log = LoggerFactory.getLogger(DispatchingFunctions.class);

    /*fonction pack avec OrderAcceptedMessage en entrée et orderId de type Long en sortie.
    Implémente la logique métier d'attribution des infos d'empaquetage de la commande
    **/
    @Bean //la fonction est gérée comme un bean
    public Function<OrderAcceptedMessage, Long> pack(){
      return  orderAcceptedMessage ->{
            log.info("the order with id {} is packed.", orderAcceptedMessage.orderId());
            return orderAcceptedMessage.orderId();
            };
    }

    /*
    entrée = id commande, sortie = message OrderDispatchedMessage
    Implémente la logique métier de labellisation d'un commande
    * */
    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label(){
        return orderFlux->orderFlux.map(orderId->{
                log.info("the order with id {} is labeled.", orderId);
                return new OrderDispatchedMessage(orderId);
                }
            );

    }
}

