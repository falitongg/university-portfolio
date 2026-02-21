package cz.cvut.fel.omo.event;

public interface EventSource {

    public String getSourceName();

    public void eventOccuired();
}
