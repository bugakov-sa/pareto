package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.NewPlayDto;
import pareto.core.api.dto.PlayDto;
import pareto.core.api.dto.PlayStatusDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.PlayReport;
import pareto.core.service.PlayService;

import java.util.List;

@RestController
public class PlayController {

    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @PostMapping("/play")
    public PlayDto createPlay(NewPlayDto newPlayDto) {

        PlayReport playReport = playService.createPlay(newPlayDto.getRobotId(), newPlayDto.getContextId());
        PlayDto playDto = MappingUtil.map(playReport.getPlay());
        return playDto;
    }

    @PutMapping("/play/{id}")
    public PlayDto changePlayStatus(@PathVariable long playId, PlayStatusDto playStatusDto) {

        return null;
    }

    @GetMapping("/play/{id}")
    public PlayDto getPlay(@PathVariable long playId) {

        return null;
    }

    @GetMapping("/play")
    public List<PlayDto> getAllPlays() {
        return null;
    }
}
