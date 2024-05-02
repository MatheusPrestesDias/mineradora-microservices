package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.dias.mineradora.client.ProposalRestClient;
import org.dias.mineradora.dto.ProposalDetailsDTO;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Traced
public class ProposalServiceImpl implements ProposalService {

    @Inject
    @RestClient
    ProposalRestClient proposalRestClient;

    @Override
    public ProposalDetailsDTO getProposalDetailsById(Long id) {
        return proposalRestClient.getProposalDetailsById(id);
    }

    @Override
    public Response createProposal(ProposalDetailsDTO proposalDetailsDTO) {
        return proposalRestClient.createProposal(proposalDetailsDTO);
    }

    @Override
    public Response removeProposal(Long id) {
        return proposalRestClient.removeProposal(id);
    }
}
