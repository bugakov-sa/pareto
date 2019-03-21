package pareto.core.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pareto.core.api.dto.EventDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.service.EventService;

import java.util.List;

@RestController
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/play/{id}/event")
    public List<EventDto> getPlayEvents(@PathVariable long playId) {
        return MappingUtil.mapEvents(eventService.getPlayEvents(playId));
    }
}
