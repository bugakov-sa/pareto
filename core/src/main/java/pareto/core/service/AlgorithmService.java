package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.Algorithm;
import pareto.core.repository.AlgorithmRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;

    public AlgorithmService(AlgorithmRepository algorithmRepository) {
        this.algorithmRepository = algorithmRepository;
    }

    public Algorithm createAlgorithm(String name, String description) {
        Algorithm algorithm = new Algorithm();
        algorithm.setName(name);
        algorithm.setDescription(description);
        algorithmRepository.save(algorithm);
        return algorithm;
    }

    public List<Algorithm> getAlgorithms() {
        return algorithmRepository.findAll();
    }

    public Optional<Algorithm> getAlgorithm(long id) {
        return algorithmRepository.findById(id);
    }
}
