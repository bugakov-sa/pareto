package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.ContextDto;
import pareto.core.api.dto.NewContextDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.Context;
import pareto.core.service.ContextService;

import java.util.List;
import java.util.Optional;

@RestController
public class ContextController {

    private final ContextService contextService;

    public ContextController(ContextService contextService) {
        this.contextService = contextService;
    }

    @PostMapping("/context")
    public ContextDto createContext(@RequestBody NewContextDto newContextDto) {
        Context newContext = contextService.createContext(
                newContextDto.getName(),
                newContextDto.getDescription(),
                MappingUtil.map(newContextDto.getParams())
        );
        return MappingUtil.map(newContext);
    }

    @GetMapping("/context")
    public List<ContextDto> getAllContexts() {
        List<Context> contexts = contextService.getAllContexts();
        return MappingUtil.mapContextMetas(contexts);
    }

    @GetMapping("/context/{id}")
    public ContextDto getContext(@PathVariable long id) {
        Optional<Context> context = contextService.getContext(id);
        return context.map(MappingUtil::map).orElse(null);
    }
}
