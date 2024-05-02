package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.dias.mineradora.dto.OpportunityDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
public interface ReportService {

    ByteArrayInputStream generateCSVOppotunityReport();

    List<OpportunityDTO> getOpportunitiesData();
}
