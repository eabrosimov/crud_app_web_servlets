package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Event;
import model.File;
import model.User;
import service.EventService;
import service.FileService;
import service.UserService;
import utility.NameValidator;
import utility.NumberValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {
    FileService fileService = new FileService();
    UserService userService = new UserService();
    EventService eventService = new EventService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Map<String, String> jsonElements = objectMapper.readValue(reader, new TypeReference<>() {});
        String userId = req.getHeader("id");
        String fileName = jsonElements.get("name");
        String filePath = jsonElements.get("filePath");
        if (req.getPathInfo() == null && NameValidator.isValidFileName(fileName)
                && NameValidator.isValidFilePath(filePath) && NumberValidator.isInteger(userId)) {
            User user = userService.getById(Integer.parseInt(userId));
            File file = new File(fileName, filePath);
            if (user != null && fileService.save(file) != null) {
                Event event = new Event(user, file);
                eventService.save(event);
                writer.println(objectMapper.writeValueAsString(file));
                return;
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
                    writer.println(objectMapper.writeValueAsString(file));
                    return;
                }
            }
            resp.setStatus(404);
        } else {
            List<File> files = fileService.getAll();
            writer.println(objectMapper.writeValueAsString(files));
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
        String filePath = jsonElements.get("filePath");
        if (req.getPathInfo() == null && NumberValidator.isInteger(id)) {
            File file = fileService.getById(Integer.parseInt(id));
            if (file != null && name != null) {
                if (NameValidator.isValidFileName(name)) {
                    file.setName(name);
                } else {
                    resp.setStatus(404);
                    return;
                }
            }
            if (file != null && filePath != null) {
                if (NameValidator.isValidFilePath(filePath)) {
                    file.setFilePath(filePath);
                } else {
                    resp.setStatus(404);
                    return;
                }
            }
            file = fileService.update(file);
            if (file != null) {
                writer.println(objectMapper.writeValueAsString(file));
                return;
            }
        }
        resp.setStatus(404);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && NumberValidator.isInteger(pathInfo.substring(1))) {
            int id = Integer.parseInt(pathInfo.substring(1));
            if (fileService.deleteById(id)) {
                return;
            }
        }
        resp.setStatus(404);
    }
}
