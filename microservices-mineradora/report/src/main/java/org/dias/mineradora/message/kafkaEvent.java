package org.dias.mineradora.message;

import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.dias.mineradora.dto.ProposalDTO;
import org.dias.mineradora.dto.QuotationDTO;
import org.dias.mineradora.service.OpportunityService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class kafkaEvent {

    private final Logger LOG = LoggerFactory.getLogger(kafkaEvent.class);

    @Inject
    OpportunityService opportunityService;

    @Incoming("proposal")
    @Transactional
    public void receiveProposal(ProposalDTO proposalDTO) {
        LOG.info("Recebendo proposta do Tópico Kafka: {}", proposalDTO);
        opportunityService.buildOpportunity(proposalDTO);
    }

    @Incoming("quotation")
    @Blocking
    public void receiveQuotation(QuotationDTO quotationDTO) {
        LOG.info("Recebendo Cotação de moeda do Tópico Kafka: {}", quotationDTO);
        opportunityService.saveQuotation(quotationDTO);
    }
}
