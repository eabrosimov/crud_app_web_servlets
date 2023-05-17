package service;

import model.Event;
import repository.EventRepository;
import repository.hibernate.HibernateEventRepositoryImpl;

import java.util.List;

public class EventService {
    private final EventRepository eventRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event update(Event event) {
        return eventRepository.update(event);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    public Event getById(Integer integer) {
        return eventRepository.getById(integer);
    }

    public boolean deleteById(Integer integer) {
        return eventRepository.deleteById(integer);
    }
}
