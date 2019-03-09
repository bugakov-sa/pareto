package pareto.core.repository;

import org.springframework.stereotype.Repository;
import pareto.core.entity.Quotation;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Repository
public class QuotationRepository {

    public Iterator<Quotation> getQuotationIterator(LocalDateTime fromTime, LocalDateTime toTime, List<String> products) {
        return null;//TODO
    }
}
