package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.FileDto;
import model.Event;
import model.File;
import model.User;
import service.EventService;
import service.FileService;
import service.UserService;
import utility.NameValidator;
import utility.NumberValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@MultipartConfig
@WebServlet("/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {
    private final FileService fileService = new FileService();
    private final UserService userService = new UserService();
    private final EventService eventService = new EventService();
    private final String filePath = "D:\\Projects\\web_app\\src\\main\\resources\\storage\\";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter writer = resp.getWriter();
        String userId = req.getHeader("userId");
        if (req.getPathInfo() == null && req.getPart("file") != null && NumberValidator.isInteger(userId)) {
            String fileName = req.getPart("file").getSubmittedFileName();
            java.io.File realFile = new java.io.File(filePath + fileName);
            File file = new File(fileName, filePath);
            User user = userService.getById(Integer.parseInt(userId));
            if (user != null && realFile.createNewFile() && fileService.save(file) != null) {
                Event event = new Event(user, file);
                eventService.save(event);
                try(InputStream inputStream = req.getPart("file").getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(realFile)) {
                    fileOutputStream.write(inputStream.readAllBytes());
                }
                writer.println(new ObjectMapper().writeValueAsString(FileDto.getFileDtoFromEntity(file)));
                return;
            } else {
                realFile.delete();
            }
        }
        resp.setStatus(404);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            if (NumberValidator.isInteger(pathInfo.substring(1))) {
                int id = Integer.parseInt(pathInfo.substring(1));
                File file = fileService.getById(id);
                if (file != null) {
                    writer.println(objectMapper.writeValueAsString(FileDto.getFileDtoFromEntity(file)));
                    return;
                }
            }
            resp.setStatus(404);
        } else {
            List<File> files = fileService.getAll();
            writer.println(objectMapper.writeValueAsString(FileDto.getFileDtoListFromEntity(files)));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Map<String, String> jsonElements = objectMapper.readValue(reader, new TypeReference<>() {});
        String id = jsonElements.get("id");
        String name = jsonElements.get("name");
        if (req.getPathInfo() == null && NumberValidator.isInteger(id) && NameValidator.isValidFileName(name)) {
            File file = fileService.getById(Integer.parseInt(id));
            if (file != null) {
                java.io.File realFile = new java.io.File(filePath + file.getName());
                file.setName(name);
                file = fileService.update(file);
                if (file != null) {
                    realFile.renameTo(new java.io.File(filePath + file.getName()));
                    writer.println(objectMapper.writeValueAsString(FileDto.getFileDtoFromEntity(file)));
                    return;
                }
            }
        }

        resp.setStatus(404);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && NumberValidator.isInteger(pathInfo.substring(1))) {
            int id = Integer.parseInt(pathInfo.substring(1));
            File file = fileService.getById(id);
            if(file != null) {
                java.io.File realFile = new java.io.File(filePath + file.getName());
                fileService.deleteById(id);
                realFile.delete();
                return;
            }
        }
        resp.setStatus(404);
    }
}
