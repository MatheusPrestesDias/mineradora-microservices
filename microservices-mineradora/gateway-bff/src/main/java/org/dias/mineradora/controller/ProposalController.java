package org.dias.mineradora.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.dias.mineradora.dto.ProposalDetailsDTO;
import org.dias.mineradora.service.ProposalService;

@Path("/api/trade")
public class ProposalController {

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public Response getProposalDetailsById(@PathParam("id") Long id) {

        try {
            return Response.ok(proposalService.getProposalDetailsById(id),
                    MediaType.APPLICATION_JSON).build();
        } catch (ServerErrorException error) {
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createNewProposal(ProposalDetailsDTO proposalDetails){

        var proposalRequestStatus = proposalService.createProposal(proposalDetails).getStatus();

        if(proposalRequestStatus > 199 || proposalRequestStatus < 205){
            return Response.ok().build();
        } else {
            return Response.status(proposalRequestStatus).build();
        }
    }

    @DELETE
    @Path("/remove/{id}")
    @RolesAllowed("manager")
    public Response removeProposal(@PathParam("id") Long id){

        int proposalRequestStatus = proposalService.removeProposal(id).getStatus();

        if(proposalRequestStatus > 199 || proposalRequestStatus < 205){
            return Response.ok().build();
        } else {
            return Response.status(proposalRequestStatus).build();
        }
    }


}
