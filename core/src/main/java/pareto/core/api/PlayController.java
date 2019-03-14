package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.NewPlayDto;
import pareto.core.api.dto.PlayDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.Play;
import pareto.core.service.PlayService;

import java.util.List;
import java.util.Optional;

@RestController
public class PlayController {

    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    @PostMapping("/play")
    public PlayDto createPlay(@RequestBody NewPlayDto dto) {
        Play play = playService.createPlay(dto.getRobotId(), dto.getContextId());
        return MappingUtil.map(play);
    }


    @GetMapping("/play")
    public List<PlayDto> getAllPlays() {
        List<Play> plays = playService.getAllPlays();
        return MappingUtil.mapPlays(plays);
    }

    @GetMapping("/play/{id}")
    public PlayDto getPlay(@PathVariable long id) {
        Optional<Play> play = playService.getPlay(id);
        return play.map(MappingUtil::map).orElse(null);
    }
}
