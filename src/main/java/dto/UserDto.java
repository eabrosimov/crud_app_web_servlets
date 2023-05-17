package dto;

import model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    private Integer id;
    private String name;

    public UserDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserDto getUserDtoFromEntity(User user){
        return new UserDto(user.getId(), user.getName());
    }

    public static List<UserDto> getUserDtoListFromEntity(List<User> users){
        return users.stream().map(UserDto::getUserDtoFromEntity).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
