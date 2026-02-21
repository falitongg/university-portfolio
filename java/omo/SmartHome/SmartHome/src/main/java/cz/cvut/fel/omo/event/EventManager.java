package cz.cvut.fel.omo.event;

import java.util.*;

/**
 * Central manager responsible for creating, storing and dispatching events
 * in the smart home simulation.
 * <p>
 * {@code EventManager} implements multiple design patterns:
 * <ul>
 *     <li><b>Singleton</b> – ensures a single global event manager instance</li>
 *     <li><b>Iterator</b> – allows iteration over all occurred events</li>
 * </ul>
 *
 * The manager:
 * <ul>
 *     <li>creates events via {@link #generateEvent(EventSource, Object, EventType)}</li>
 *     <li>dispatches events to suitable {@link EventHandler}s</li>
 *     <li>stores history of all occurred events for reporting</li>
 * </ul>
 *
 * Events stored in this manager are later used by reporting components
 * such as {@code EventReport}.
 */
public class EventManager implements Iterable<Event> {

    /**
     * Singleton instance of EventManager.
     */
    private static EventManager instance;

    /**
     * List of all events that occurred during the simulation.
     */
    private List<Event> happenedEvents;

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private EventManager() {
        happenedEvents = new ArrayList<>();
    }

    /**
     * Returns an iterator over all occurred events.
     * <p>
     * Enables usage of enhanced for-loop and stream processing.
     *
     * @return iterator over event history
     */
    @Override
    public Iterator<Event> iterator() {
        return happenedEvents.iterator();
    }

    /**
     * Creates a new event and stores it in the event history.
     * <p>
     * Newly created events are initially unhandled.
     *
     * @param source    source that generated the event
     * @param target    object affected by the event
     * @param eventType type of the event
     * @return created event
     */
    public Event generateEvent(EventSource source, Object target, EventType eventType) {
        Event event = new Event(source, target, eventType);
        addEvent(event);
        return event;
    }

    /**
     * Dispatches the given event to the most suitable event handler.
     * <p>
     * The selection process:
     * <ol>
     *     <li>filters handlers that can handle the event</li>
     *     <li>filters only available handlers</li>
     *     <li>selects handler with the highest priority</li>
     * </ol>
     *
     * If a handler is found, the event is processed and marked as handled.
     *
     * @param event    event to be dispatched
     * @param handlers list of available event handlers
     */
    public void dispatchEvent(Event event, List<EventHandler> handlers) {
        handlers.stream()
                .filter(h -> h.canHandle(event))
                .filter(EventHandler::isAvailable)
                .max(Comparator.comparingInt(h -> h.getPriority(event)))
                .ifPresent(handler -> {
                    handler.handle(event);
                    event.setHandledBy(handler);
                    event.setHandled(true);
                });
    }

    /**
     * Returns a list of all occurred events.
     *
     * @return list of events
     */
    public List<Event> getHappenedEvents() {
        return happenedEvents;
    }

    /**
     * Replaces the current event history.
     * <p>
     * Mainly intended for testing or simulation reset.
     *
     * @param happenedEvents new event list
     */
    public void setHappenedEvents(List<Event> happenedEvents) {
        this.happenedEvents = happenedEvents;
    }

    /**
     * Adds an event to the event history.
     *
     * @param event event to add
     */
    public void addEvent(Event event) {
        happenedEvents.add(event);
    }

    /**
     * Removes an event from the event history.
     *
     * @param event event to remove
     */
    public void removeEvent(Event event) {
        happenedEvents.remove(event);
    }

    /**
     * Returns the singleton instance of EventManager.
     *
     * @return EventManager instance
     */
    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }
}
