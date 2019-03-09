package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.ContextDto;
import pareto.core.api.dto.NewContextDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.ContextMeta;
import pareto.core.repository.ContextMetaRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContextController {

    private final ContextMetaRepository contextMetaRepository;

    public ContextController(ContextMetaRepository contextMetaRepository) {
        this.contextMetaRepository = contextMetaRepository;
    }

    @PostMapping("/context")
    public ContextDto createContext(@RequestBody NewContextDto newContextDto) {
        long contextId = contextMetaRepository.saveNewContextMeta(MappingUtil.map(newContextDto));
        ContextMeta contextMeta = contextMetaRepository.getContextMeta(contextId);
        return MappingUtil.map(contextMeta);
    }

    @GetMapping("/context")
    public List<ContextDto> getAllContexts() {
        return contextMetaRepository.getContextMetas()
                .stream()
                .map(MappingUtil::map)
                .collect(Collectors.toList());
    }

    @GetMapping("/context/{id}")
    public ContextDto getContext(@PathVariable long id) {
        return MappingUtil.map(contextMetaRepository.getContextMeta(id));
    }
}
