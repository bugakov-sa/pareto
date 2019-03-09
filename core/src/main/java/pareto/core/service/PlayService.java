package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.PlayReport;

import java.util.Collections;
import java.util.List;

@Service
public class PlayService {

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
