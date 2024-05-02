package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.dias.mineradora.client.ReportRestClient;
import org.dias.mineradora.dto.OpportunityDTO;
import org.dias.mineradora.utils.CSVHelper;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
@Traced
public class ReportServiceImpl implements ReportService{

    @Inject
    @RestClient
    ReportRestClient reportRestClient;


    @Override
    public ByteArrayInputStream generateCSVOppotunityReport() {
        List<OpportunityDTO> opportunities = reportRestClient.requestOpportunityData();
        return CSVHelper.opportunitiesToCSV(opportunities);
    }

    @Override
    public List<OpportunityDTO> getOpportunitiesData() {
        return reportRestClient.requestOpportunityData();
    }
}
