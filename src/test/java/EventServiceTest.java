import model.Event;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.hibernate.HibernateEventRepositoryImpl;
import service.EventService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    HibernateEventRepositoryImpl hibernateEventRepositoryMock;

    @InjectMocks
    EventService eventServiceMock;

    @Test
    public void saveShouldReturnSameEvent(){
        Event eventFromMock = new Event();
        Mockito.when(hibernateEventRepositoryMock.save(eventFromMock)).thenReturn(eventFromMock);
        Event event = eventServiceMock.save(eventFromMock);
        Assertions.assertEquals(eventFromMock, event);
    }

    @Test
    public void updateShouldReturnSameEvent() {
        Event eventFromMock = new Event();
        Mockito.when(hibernateEventRepositoryMock.update(eventFromMock)).thenReturn(eventFromMock);
        Event event = eventServiceMock.update(eventFromMock);
        Assertions.assertEquals(eventFromMock, event);
    }

    @Test
    public void getAllShouldReturnListOfEvents() {
        List<Event> eventsFromMock = new ArrayList<>();
        Mockito.when(hibernateEventRepositoryMock.getAll()).thenReturn(eventsFromMock);
        List<Event> events = eventServiceMock.getAll();
        Assertions.assertEquals(eventsFromMock, events);
    }

    @Test
    public void getByIdShouldReturnEvent() {
        Event eventFromMock = new Event();
        Integer i = 1;
        Mockito.when(hibernateEventRepositoryMock.getById(i)).thenReturn(eventFromMock);
        Event event = eventServiceMock.getById(i);
        Assertions.assertEquals(eventFromMock, event);
    }

    @Test
    public void getByIdShouldReturnNull() {
        Integer i = 1;
        Mockito.when(hibernateEventRepositoryMock.getById(i)).thenReturn(null);
        Event event = eventServiceMock.getById(i);
        Assertions.assertNull(event);
    }

    @Test
    public void deleteShouldReturnTrue() {
        Integer i = 1;
        Mockito.when(hibernateEventRepositoryMock.deleteById(i)).thenReturn(true);
        boolean b = eventServiceMock.deleteById(i);
        Assertions.assertTrue(b);
    }

    @Test
    public void deleteShouldReturnFalse() {
        Integer i = 1;
        Mockito.when(hibernateEventRepositoryMock.deleteById(i)).thenReturn(false);
        boolean b = eventServiceMock.deleteById(i);
        Assertions.assertFalse(b);
    }
}
