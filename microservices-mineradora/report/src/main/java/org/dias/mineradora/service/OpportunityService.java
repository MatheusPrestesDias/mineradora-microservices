package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.dias.mineradora.dto.OpportunityDTO;
import org.dias.mineradora.dto.ProposalDTO;
import org.dias.mineradora.dto.QuotationDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
public interface OpportunityService {

    void buildOpportunity(ProposalDTO proposalDTO);

    void saveQuotation(QuotationDTO quotationDTO);

    List<OpportunityDTO> generateOpportunityData();
}
