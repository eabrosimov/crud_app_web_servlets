import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.hibernate.HibernateUserRepositoryImpl;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    HibernateUserRepositoryImpl hibernateUserRepositoryMock;

    @InjectMocks
    UserService userServiceMock;

    @Test
    public void saveShouldReturnSameUser(){
        User userFromMock = new User();
        Mockito.when(hibernateUserRepositoryMock.save(userFromMock)).thenReturn(userFromMock);
        User user = userServiceMock.save(userFromMock);
        Assertions.assertEquals(userFromMock, user);
    }

    @Test
    public void updateShouldReturnSameUser() {
        User userFromMock = new User();
        Mockito.when(hibernateUserRepositoryMock.update(userFromMock)).thenReturn(userFromMock);
        User user = userServiceMock.update(userFromMock);
        Assertions.assertEquals(userFromMock, user);
    }

    @Test
    public void getAllShouldReturnListOfUsers() {
        List<User> usersFromMock = new ArrayList<>();
        Mockito.when(hibernateUserRepositoryMock.getAll()).thenReturn(usersFromMock);
        List<User> users = userServiceMock.getAll();
        Assertions.assertEquals(usersFromMock, users);
    }

    @Test
    public void getByIdShouldReturnUser() {
        User userFromMock = new User();
        Integer i = 1;
        Mockito.when(hibernateUserRepositoryMock.getById(i)).thenReturn(userFromMock);
        User user = userServiceMock.getById(i);
        Assertions.assertEquals(userFromMock, user);
    }

    @Test
    public void getByIdShouldReturnNull() {
        Integer i = 1;
        Mockito.when(hibernateUserRepositoryMock.getById(i)).thenReturn(null);
        User user = userServiceMock.getById(i);
        Assertions.assertNull(user);
    }

    @Test
    public void deleteShouldReturnTrue() {
        Integer i = 1;
        Mockito.when(hibernateUserRepositoryMock.deleteById(i)).thenReturn(true);
        boolean b = userServiceMock.deleteById(i);
        Assertions.assertTrue(b);
    }

    @Test
    public void deleteShouldReturnFalse() {
        Integer i = 1;
        Mockito.when(hibernateUserRepositoryMock.deleteById(i)).thenReturn(false);
        boolean b = userServiceMock.deleteById(i);
        Assertions.assertFalse(b);
    }
}
