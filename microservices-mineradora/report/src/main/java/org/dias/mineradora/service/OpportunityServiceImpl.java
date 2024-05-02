package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.dias.mineradora.dto.OpportunityDTO;
import org.dias.mineradora.dto.ProposalDTO;
import org.dias.mineradora.dto.QuotationDTO;
import org.dias.mineradora.entity.OpportunityEntity;
import org.dias.mineradora.entity.QuotationEntity;
import org.dias.mineradora.repository.OpportunityRepository;
import org.dias.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
@Traced
public class OpportunityServiceImpl implements OpportunityService {

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    OpportunityRepository opportunityRepository;

    @Override
    public void buildOpportunity(ProposalDTO proposalDTO) {
        var lastQuotation = quotationRepository.findLastQuotation();

        var opportunity = new OpportunityEntity();
        opportunity.setDate(new Date());
        opportunity.setProposalId(proposalDTO.getProposalId());
        opportunity.setCustomer(proposalDTO.getCustomer());
        opportunity.setPriceTonne(proposalDTO.getPriceTonne());
        opportunity.setLastDollarQuotation(lastQuotation.get().getCurrencyPrice());

        opportunityRepository.persist(opportunity);
    }

    @Override
    @Transactional
    public void saveQuotation(QuotationDTO quotationDTO) {
        var quotationEntity = new QuotationEntity();
        quotationEntity.setCurrencyPrice(quotationDTO.getCurrencyPrice());
        quotationEntity.setDate(new Date());

        quotationRepository.persist(quotationEntity);
    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {
        List<OpportunityDTO> opportunityList = new ArrayList<>();

        opportunityRepository.findAll().stream().forEach(opportunity -> {
            opportunityList.add(OpportunityDTO.builder()
                    .proposalId(opportunity.getProposalId())
                    .customer(opportunity.getCustomer())
                    .priceTonne(opportunity.getPriceTonne())
                    .lastDollarQuotation(opportunity.getLastDollarQuotation())
                    .build());
        });
        return opportunityList;
    }
}
