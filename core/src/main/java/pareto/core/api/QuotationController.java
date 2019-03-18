package pareto.core.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pareto.core.api.dto.QuotationDto;
import pareto.core.service.QuotationService;

import java.util.List;

@RestController
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("quotation")
    public void save(List<QuotationDto> quotations) {
        
    }


}
