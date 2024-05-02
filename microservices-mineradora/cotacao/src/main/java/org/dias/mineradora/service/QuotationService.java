package org.dias.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.dias.mineradora.client.CurrencyPriceClient;
import org.dias.mineradora.dto.CurrencyPriceDTO;
import org.dias.mineradora.dto.QuotationDTO;
import org.dias.mineradora.entity.QuotationEntity;
import org.dias.mineradora.message.KafkaEvents;
import org.dias.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Date;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;


    public void getCurrencyPrice() {
        var currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        if (updateCurrentInfoPrice(currencyPriceInfo)) {
            kafkaEvents.sendNewKafkaEvent(
                    QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                    .date(new Date())
                    .build()
            );
        }
    }

    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo) {
        var currentPrice = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());
        var updatePrice = false;
        var quotationList = quotationRepository.findAll().list();

        if (quotationList.isEmpty()) {
            saveQuotation(currencyPriceInfo);
            updatePrice = true;
        } else {
            var lastDollarPrice = quotationList.get(quotationList.size() - 1).getCurrencyPrice().floatValue();
            if (currentPrice.floatValue() > lastDollarPrice) {
                saveQuotation(currencyPriceInfo);
                updatePrice = true;
            }
        }
        return updatePrice;
    }

    private void saveQuotation(CurrencyPriceDTO currencyPriceInfo) {
        var quotation = new QuotationEntity();
        quotation.setDate(new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()));
        quotation.setPctChange(currencyPriceInfo.getUSDBRL().getPctChange());
        quotation.setPair("USD-BRL");
        quotationRepository.persist(quotation);
    }
}
