package org.dias.mineradora.controller;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.dias.mineradora.dto.ProposalDetailsDTO;
import org.dias.mineradora.service.ProposalService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
@Authenticated
public class ProposalController {

    private final Logger LOG = LoggerFactory.getLogger(ProposalController.class);

    @Inject
    ProposalService proposalService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public ProposalDetailsDTO findFullProposal(@PathParam("id") Long id) {
        return proposalService.findFullProposal(id);
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createProposal(ProposalDetailsDTO proposalDetailsDTO) {
        proposalService.createProposal(proposalDetailsDTO);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response deleteProposal(@PathParam("id") Long id) {
        proposalService.removeProposal(id);
        return Response.ok().build();
    }
}
