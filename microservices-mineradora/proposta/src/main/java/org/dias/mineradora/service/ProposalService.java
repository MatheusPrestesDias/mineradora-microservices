package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.dias.mineradora.dto.ProposalDetailsDTO;

@ApplicationScoped
public interface ProposalService {

    ProposalDetailsDTO findFullProposal(Long id);

    void createProposal(ProposalDetailsDTO proposalDetailsDTO);
    void removeProposal(Long id);
}
