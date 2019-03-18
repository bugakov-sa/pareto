package pareto.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pareto.core.api.dto.QuotationDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.Quotation;
import pareto.core.service.QuotationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping("/quotation")
    public void save(List<QuotationDto> quotations) {
        quotationService.save(MappingUtil.mapQuotationDtos(quotations));
    }

    @GetMapping("/quotation")
    public List<QuotationDto> getQuotations(@RequestParam long productId) {
        List<Quotation> quotations = quotationService.getQuotationsList(List.of(productId), LocalDateTime.MIN, LocalDateTime.MAX)
                .stream()
                .flatMap((Function<List<Quotation>, Stream<Quotation>>) quotations1 -> quotations1.stream())
                .collect(Collectors.toList());
        return MappingUtil.mapQuotations(quotations);
    }
}
