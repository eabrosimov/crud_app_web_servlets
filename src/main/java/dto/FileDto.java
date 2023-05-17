package dto;

import model.File;

import java.util.List;
import java.util.stream.Collectors;

public class FileDto {
    private Integer id;
    private String name;
    private String filePath;

    public FileDto(Integer id, String name, String filePath) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
    }

    public static FileDto getFileDtoFromEntity(File file){
        return new FileDto(file.getId(), file.getName(), file.getFilePath());
    }

    public static List<FileDto> getFileDtoListFromEntity(List<File> files){
        return files.stream().map(FileDto::getFileDtoFromEntity).collect(Collectors.toList());
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
