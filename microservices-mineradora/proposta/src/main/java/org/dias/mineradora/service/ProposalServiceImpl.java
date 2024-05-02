package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.dias.mineradora.dto.ProposalDTO;
import org.dias.mineradora.dto.ProposalDetailsDTO;
import org.dias.mineradora.entity.ProposalEntity;
import org.dias.mineradora.message.KafkaEvents;
import org.dias.mineradora.repository.ProposalRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.Date;
import java.util.NoSuchElementException;

@ApplicationScoped
@Traced
public class ProposalServiceImpl implements ProposalService {

    @Inject
    ProposalRepository proposalRepository;

    @Inject
    KafkaEvents kafkaEvents;


    @Override
    public ProposalDetailsDTO findFullProposal(Long id) {
        var proposalEntity = proposalRepository.findById(id);
        return ProposalDetailsDTO.builder()
                .proposalId(proposalEntity.getId())
                .customer(proposalEntity.getCustomer())
                .priceTonne(proposalEntity.getPriceTonne())
                .tonnes(proposalEntity.getTonnes())
                .country(proposalEntity.getCountry())
                .proposalValidityDays(proposalEntity.getProposalValidityDays())
                .build();
    }

    @Override
    @Transactional
    public void createProposal(ProposalDetailsDTO proposalDetailsDTO) {
        var proposalDTO = buildAndSaveProposal(proposalDetailsDTO);
        kafkaEvents.sendNewKafkaEvent(proposalDTO);
    }

    @Override
    @Transactional
    public void removeProposal(Long id) {
        proposalRepository.deleteById(id);
    }

    @Transactional
    public ProposalDTO buildAndSaveProposal(ProposalDetailsDTO proposalDetailsDTO) {
        try {
            var proposalEntity = new ProposalEntity();
            proposalEntity.setCustomer(proposalDetailsDTO.getCustomer());
            proposalEntity.setPriceTonne(proposalDetailsDTO.getPriceTonne());
            proposalEntity.setTonnes(proposalDetailsDTO.getTonnes());
            proposalEntity.setCountry(proposalDetailsDTO.getCountry());
            proposalEntity.setProposalValidityDays(proposalDetailsDTO.getProposalValidityDays());
            proposalEntity.setCreated(new Date());

            proposalRepository.persist(proposalEntity);

            return ProposalDTO.builder()
                    .proposalId(
                            proposalRepository.findByCustomer(proposalEntity.getCustomer())
                                    .orElseThrow(() -> new NoSuchElementException("Nenhuma proposta encontrada para o cliente fornecido"))
                                    .getId()
                    )
                    .customer(proposalEntity.getCustomer())
                    .priceTonne(proposalEntity.getPriceTonne())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar nova proposta.");
        }
    }
}
