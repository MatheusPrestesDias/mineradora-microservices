package org.dias.mineradora.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.dias.mineradora.entity.QuotationEntity;

import java.util.Optional;

@ApplicationScoped
public class QuotationRepository implements PanacheRepository<QuotationEntity> {

    public Optional<QuotationEntity> findLastQuotation() {
        return find("ORDER BY date DESC").firstResultOptional();
    }
}
