package com.polarbookshop.dispatcherservice;

/*message entrant notifiant l'acceptation de la commande*/
public record OrderAcceptedMessage(Long orderId) {}
