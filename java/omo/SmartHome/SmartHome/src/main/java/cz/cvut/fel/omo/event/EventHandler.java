package cz.cvut.fel.omo.event;

public interface EventHandler {
    boolean canHandle(Event event);
    boolean isAvailable();
    int getPriority(Event event);
    void handle(Event event);
    String getName();
}
