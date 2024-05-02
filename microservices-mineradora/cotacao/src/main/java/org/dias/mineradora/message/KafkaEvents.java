package org.dias.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.dias.mineradora.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ApplicationScoped
public class KafkaEvents {

    private final Logger log = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotationDTO) {
        log.info("Enviando Cotação para o Tópico Kafka: {}", quotationDTO);
        quotationRequestEmitter.send(quotationDTO).toCompletableFuture().join();
    }
}
