package dto;

import model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class EventDto {
    private Integer id;
    private UserDto user;
    private FileDto file;

    public EventDto(Integer id, UserDto userDto, FileDto fileDto) {
        this.id = id;
        this.user = userDto;
        this.file = fileDto;
    }

    public static EventDto getEventDtoFromEntity(Event event){
        return new EventDto(event.getId(), UserDto.getUserDtoFromEntity(event.getUser()), FileDto.getFileDtoFromEntity(event.getFile()));
    }

    public static List<EventDto> getEventDtoListFromEntity(List<Event> events){
        return events.stream().map(EventDto::getEventDtoFromEntity).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDto getUserDto() {
        return user;
    }

    public void setUserDto(UserDto userDto) {
        this.user = userDto;
    }

    public FileDto getFileDto() {
        return file;
    }

    public void setFileDto(FileDto fileDto) {
        this.file = fileDto;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", user=" + user +
                ", file=" + file +
                '}';
    }
}
