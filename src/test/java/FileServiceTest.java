import model.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.hibernate.HibernateFileRepositoryImpl;
import service.FileService;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    HibernateFileRepositoryImpl hibernateFileRepositoryMock;

    @InjectMocks
    FileService fileServiceMock;

    @Test
    public void saveShouldReturnSameFile(){
        File fileFromMock = new File();
        Mockito.when(hibernateFileRepositoryMock.save(fileFromMock)).thenReturn(fileFromMock);
        File file = fileServiceMock.save(fileFromMock);
        Assertions.assertEquals(fileFromMock, file);
    }

    @Test
    public void updateShouldReturnSameFile() {
        File fileFromMock = new File();
        Mockito.when(hibernateFileRepositoryMock.update(fileFromMock)).thenReturn(fileFromMock);
        File file = fileServiceMock.update(fileFromMock);
        Assertions.assertEquals(fileFromMock, file);
    }

    @Test
    public void getAllShouldReturnListOfFiles() {
        List<File> filesFromMock = new ArrayList<>();
        Mockito.when(hibernateFileRepositoryMock.getAll()).thenReturn(filesFromMock);
        List<File> files = fileServiceMock.getAll();
        Assertions.assertEquals(filesFromMock, files);
    }

    @Test
    public void getByIdShouldReturnFile() {
        File fileFromMock = new File();
        Integer i = 1;
        Mockito.when(hibernateFileRepositoryMock.getById(i)).thenReturn(fileFromMock);
        File file = fileServiceMock.getById(i);
        Assertions.assertEquals(fileFromMock, file);
    }

    @Test
    public void getByIdShouldReturnNull() {
        Integer i = 1;
        Mockito.when(hibernateFileRepositoryMock.getById(i)).thenReturn(null);
        File file = fileServiceMock.getById(i);
        Assertions.assertNull(file);
    }

    @Test
    public void deleteShouldReturnTrue() {
        Integer i = 1;
        Mockito.when(hibernateFileRepositoryMock.deleteById(i)).thenReturn(true);
        boolean b = fileServiceMock.deleteById(i);
        Assertions.assertTrue(b);
    }

    @Test
    public void deleteShouldReturnFalse() {
        Integer i = 1;
        Mockito.when(hibernateFileRepositoryMock.deleteById(i)).thenReturn(false);
        boolean b = fileServiceMock.deleteById(i);
        Assertions.assertFalse(b);
    }
}
