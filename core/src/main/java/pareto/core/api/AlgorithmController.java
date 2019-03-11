package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.AlgorithmDto;
import pareto.core.api.dto.NewAlgorithmDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.Algorithm;
import pareto.core.service.AlgorithmService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AlgorithmController {

    private final AlgorithmService algorithmService;

    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    @PostMapping("/algorithm")
    public AlgorithmDto createAlgorithm(@RequestBody NewAlgorithmDto dto) {
        Algorithm algorithm = algorithmService.createAlgorithm(dto.getName(), dto.getDescription());
        return MappingUtil.map(algorithm);
    }

    @GetMapping("/algorithm/{id}")
    public AlgorithmDto getAlgorithm(@PathVariable long id) {
        Optional<Algorithm> algorithm = algorithmService.getAlgorithm(id);
        return algorithm.map(MappingUtil::map).orElse(null);
    }

    @GetMapping("/algorithm")
    public List<AlgorithmDto> getAlgorithms() {
        return algorithmService.getAlgorithms().stream().map(MappingUtil::map).collect(Collectors.toList());
    }
}
