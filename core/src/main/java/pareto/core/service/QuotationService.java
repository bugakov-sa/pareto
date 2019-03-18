package pareto.core.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import pareto.core.entity.Quotation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class QuotationService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public QuotationService(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(List<Quotation> quotations) {
        Map<String, ?>[] insertQuotationParams = new Map[quotations.size()];
        for(int i =0;i<quotations.size();i++){
            Quotation quotation = quotations.get(i);
            insertQuotationParams[i] = Map.of(
                    "product_id", quotation.getProductId(),
                    "time", quotation.getTime(),
                    "open", quotation.getOpen(),
                    "close", quotation.getClose(),
                    "min", quotation.getMin(),
                    "max", quotation.getMax()
            );
        }
        namedParameterJdbcTemplate.batchUpdate(
                "insert into quotation(product_id, time, open, close, min, max) values(:product_id, :time, :open, :close, :min, :max)",
                insertQuotationParams
        );
    }

    public Iterator<List<Quotation>> getQuotationsIterator(
            List<Long> productIds, LocalDateTime fromTime, LocalDateTime toTime
    ) {
        return getQuotationsList(productIds, fromTime, toTime).iterator();
    }

    public List<List<Quotation>> getQuotationsList(
            List<Long> productIds, LocalDateTime fromTime, LocalDateTime toTime
    ) {
        List<List<Quotation>> res = new ArrayList<>();
        namedParameterJdbcTemplate.query(
                "select * from product_quotation where time >= :from_time and time < :to_time and product_id in (:product_id) order by time, product_id",
                Map.of("product_id", productIds, "from_time", fromTime, "to_time", toTime),
                rs -> {
                    LocalDateTime time = LocalDateTime.ofEpochSecond(
                            rs.getLong("time"), 0, ZoneOffset.UTC
                    );
                    if (res.isEmpty()) {
                        res.add(new ArrayList<>());
                    }
                    List<Quotation> lastQuotationList = res.get(res.size() - 1);
                    if (lastQuotationList.size() > 0 && time.isAfter(lastQuotationList.get(0).getTime())) {
                        res.add(new ArrayList<>());
                    }
                    List<Quotation> currQuotationList = res.get(res.size() - 1);
                    currQuotationList.add(buildQuotation(rs));
                });

        return res;
    }

    private Quotation buildQuotation(ResultSet rs) throws SQLException {
        long productId = rs.getLong("product_id");
        LocalDateTime time = LocalDateTime.ofEpochSecond(
                rs.getLong("time"), 0, ZoneOffset.UTC
        );
        int open = rs.getInt("open");
        int close = rs.getInt("close");
        int min = rs.getInt("min");
        int max = rs.getInt("max");
        return new Quotation(productId, time, open, close, min, max);
    }
}
