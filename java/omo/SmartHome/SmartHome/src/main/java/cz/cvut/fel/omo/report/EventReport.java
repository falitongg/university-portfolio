package cz.cvut.fel.omo.report;

import cz.cvut.fel.omo.event.Event;
import cz.cvut.fel.omo.event.EventManager;
import cz.cvut.fel.omo.event.EventSource;
import cz.cvut.fel.omo.event.EventType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Report showing events that occurred during simulation.
 * Groups events by type, source, and handler.
 */
public class EventReport extends Report {

    private final EventManager eventManager;

    /**
     * Creates event report.
     * @param eventManager Event manager containing all recorded events
     */
    public EventReport(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Generates event report.
     * @return Formatted report showing events grouped by type, source, and handler
     */
    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EVENT REPORT ===\n\n");

        Map<EventType, List<Event>> byType = groupByType();

        for (EventType type : byType.keySet()) {
            sb.append("EventType: ").append(type).append("\n");

            Map<String, List<Event>> bySource =
                    groupBySource(byType.get(type));

            for (String source : bySource.keySet()) {
                sb.append("  Source: ").append(source).append("\n");

                Map<String, List<Event>> byHandler =
                        groupByHandler(bySource.get(source));

                for (String handler : byHandler.keySet()) {
                    sb.append("    Handled by: ")
                            .append(handler)
                            .append(" (")
                            .append(byHandler.get(handler).size())
                            .append("×)\n");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Groups events by event type.
     * @return Map of event type to list of events
     */
    private Map<EventType, List<Event>> groupByType() {
        Map<EventType, List<Event>> map = new EnumMap<>(EventType.class);
        for (Event e : eventManager) {
            map.computeIfAbsent(e.getEventType(), k -> new ArrayList<>())
                    .add(e);
        }
        return map;
    }

    /**
     * Groups events by source name.
     * @param events List of events to group
     * @return Map of source name to list of events
     */
    private Map<String, List<Event>> groupBySource(List<Event> events) {
        return events.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getSource().getSourceName()
                ));
    }

    /**
     * Groups events by handler name.
     * @param events List of events to group
     * @return Map of handler name to list of events (or "UNHANDLED" for unhandled events)
     */
    private Map<String, List<Event>> groupByHandler(List<Event> events) {
        return events.stream()
                .collect(Collectors.groupingBy(e -> {
                    if (!e.isHandled() || e.getHandledBy() == null) {
                        return "UNHANDLED";
                    }
                    return e.getHandledBy().getName();
                }));
    }
}
