package service;

import model.File;
import repository.FileRepository;
import repository.hibernate.HibernateFileRepositoryImpl;

import java.util.List;

public class FileService {
    private final FileRepository fileRepository;

    public FileService() {
        fileRepository = new HibernateFileRepositoryImpl();
    }

    public File save(File file) {
        return fileRepository.save(file);
    }

    public File update(File file) {
        return fileRepository.update(file);
    }

    public List<File> getAll() {
        return fileRepository.getAll();
    }

    public File getById(Integer integer) {
        return fileRepository.getById(integer);
    }

    public boolean deleteById(Integer integer) {
        return fileRepository.deleteById(integer);
    }
}
