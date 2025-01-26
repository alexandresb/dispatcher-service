package com.polarbookshop.dispatcherservice;

//message de sortie notifiant une commande dispatchée
public record OrderDispatchedMessage(Long orderId) {
}
