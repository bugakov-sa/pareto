package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.ContextParam;
import pareto.core.repository.ContextRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContextService {

    private final ContextRepository contextRepository;

    public ContextService(ContextRepository contextRepository) {
        this.contextRepository = contextRepository;
    }

    public Context createContext(String name, String description, List<ContextParam> params) {
        Context context = new Context();
        context.setName(name);
        context.setDescription(description);
        contextRepository.save(context);
        params = new ArrayList<>(params);
        params.forEach(param -> param.setContextId(context.getId()));
        context.setParams(params);
        contextRepository.save(context);
        return context;
    }

    public List<Context> getAllContexts() {
        return contextRepository.findAll();
    }

    public Optional<Context> getContext(long id) {
        return contextRepository.findById(id);
    }
}
