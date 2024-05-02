package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.dias.mineradora.dto.ProposalDetailsDTO;

@ApplicationScoped
public interface ProposalService {

    ProposalDetailsDTO getProposalDetailsById(Long id);

    Response createProposal(ProposalDetailsDTO proposalDetailsDTO);
    Response removeProposal(Long id);
}
