package cz.cvut.fel.omo.event;

import cz.cvut.fel.omo.entity.people.HouseholdMember;

import java.time.LocalDateTime;

/**
 * Represents a domain event occurring in the smart home simulation.
 * <p>
 * Event encapsulates information about:
 * <ul>
 *     <li>event type</li>
 *     <li>source that generated the event</li>
 *     <li>target object affected by the event</li>
 *     <li>handling state and responsible handler</li>
 * </ul>
 *
 * Events are created by {@link EventManager} and dispatched to
 * {@link EventHandler}s that are able to process them.
 *
 * This class is part of the event-driven architecture and supports
 * the Mediator pattern implemented by {@link EventManager}.
 */
public class Event {

    /**
     * Indicates whether the event has been successfully handled.
     */
    private boolean handled;

    /**
     * Handler that processed this event.
     * <p>
     * May be {@code null} if the event was not handled.
     */
    private EventHandler handledBy;

    /**
     * Source that generated the event (e.g. sensor, person, device).
     */
    private final EventSource source;

    /**
     * Target object affected by the event.
     * <p>
     * Can be a device, person, or any domain object relevant to the event.
     */
    private final Object target;

    /**
     * Type of the event.
     */
    private final EventType eventType;

    /**
     * Creates a new event with given source, target, and type.
     * <p>
     * Newly created events are initially unhandled.
     *
     * @param source    source that generated the event
     * @param target    object affected by the event
     * @param eventType type of the event
     */
    public Event(EventSource source, Object target, EventType eventType) {
        this.source = source;
        this.target = target;
        this.eventType = eventType;
        this.handled = false;
        this.handledBy = null;
    }

    /**
     * Returns the type of the event.
     *
     * @return event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns the handler that processed this event.
     *
     * @return event handler or {@code null} if the event was not handled
     */
    public EventHandler getHandledBy() {
        return handledBy;
    }

    /**
     * Sets the handler that processed this event.
     *
     * @param handledBy handler responsible for processing the event
     */
    public void setHandledBy(EventHandler handledBy) {
        this.handledBy = handledBy;
    }

    /**
     * Indicates whether the event has been handled.
     *
     * @return {@code true} if handled, {@code false} otherwise
     */
    public boolean isHandled() {
        return handled;
    }

    /**
     * Sets the handled state of the event.
     *
     * @param handled {@code true} if the event was handled
     */
    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    /**
     * Returns the source that generated the event.
     *
     * @return event source
     */
    public EventSource getSource() {
        return source;
    }

    /**
     * Returns the target object affected by the event.
     *
     * @return event target
     */
    public Object getTarget() {
        return target;
    }
}
