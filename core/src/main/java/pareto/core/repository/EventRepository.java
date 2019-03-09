package pareto.core.repository;

import pareto.core.entity.Event;

import java.util.List;

public interface EventRepository {

    void save(List<Event> events);
}
