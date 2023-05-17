package service;

import model.User;
import repository.UserRepository;
import repository.hibernate.HibernateUserRepositoryImpl;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
    }
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User getById(Integer integer) {
        return userRepository.getById(integer);
    }

    public boolean deleteById(Integer integer) {
        return userRepository.deleteById(integer);
    }
}
