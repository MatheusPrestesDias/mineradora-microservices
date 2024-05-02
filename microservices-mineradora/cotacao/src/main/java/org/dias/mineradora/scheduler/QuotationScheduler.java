package org.dias.mineradora.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.dias.mineradora.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {

    @Inject
    QuotationService quotationService;

    private final Logger log = LoggerFactory.getLogger(QuotationScheduler.class);


    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    public void schedule() {
        log.info("Iniciando o Scheduler");
        quotationService.getCurrencyPrice();
    }
}
