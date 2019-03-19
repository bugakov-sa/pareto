package pareto.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pareto.core.api.dto.PlayPnlDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.PlayPnl;
import pareto.core.service.PlayPnlService;

import java.util.List;

@RestController
public class PlayPnlController {

    private final PlayPnlService playPnlService;

    public PlayPnlController(PlayPnlService playPnlService) {
        this.playPnlService = playPnlService;
    }

    @GetMapping("/play/{id}/pnl")
    public List<PlayPnlDto> getPlayPnl(@PathVariable long playId) {
        List<PlayPnl> playPnl = playPnlService.getPlayPnl(playId);
        return MappingUtil.mapPlayPnls(playPnl);
    }
}
