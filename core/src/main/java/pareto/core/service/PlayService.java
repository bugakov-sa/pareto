package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.PlayReport;
import pareto.core.repository.PlayRepository;

import java.util.Collections;
import java.util.List;

@Service
public class PlayService {

    private final PlayRepository playRepository;

    public PlayService(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public PlayReport createPlay(long robotId, long contextId) {
        return null;
    }

    public List<PlayReport> gellPlayReports() {
        return Collections.emptyList();
    }

    public PlayReport getPlayReport(long playId) {
        return null;
    }
}
