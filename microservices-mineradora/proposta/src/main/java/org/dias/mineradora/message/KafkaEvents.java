package org.dias.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.dias.mineradora.dto.ProposalDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("proposal")
    Emitter<ProposalDTO> proposalRequestEmitter;

    public void sendNewKafkaEvent(ProposalDTO proposalDTO) {
        LOG.info("Enviando proposta para o Tópico Kafka: {}", proposalDTO);
        proposalRequestEmitter.send(proposalDTO).toCompletableFuture().join();
    }
}
