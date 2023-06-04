package persistence;

import model.Event;

import java.io.IOException;
import java.time.LocalDate;

public interface EventDAO {
    Event createEvent(Event event) throws IOException;
    Event[] getEvents() throws IOException;
    Event getEventByDate(LocalDate date) throws IOException;
    Event updateEvent(Event eventToUpdate) throws IOException;
    Event deleteEvent(Event eventName) throws IOException;
}
