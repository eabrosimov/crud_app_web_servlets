package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Event;
import model.File;
import model.User;
import service.EventService;
import service.FileService;
import service.UserService;
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

@WebServlet("/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {
    EventService eventService = new EventService();
    UserService userService = new UserService();
    FileService fileService = new FileService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Map<String, String> jsonElements = objectMapper.readValue(reader, new TypeReference<>() {});
        String userId = jsonElements.get("userId");
        String fileId = jsonElements.get("fileId");
        if (req.getPathInfo() == null && NumberValidator.isInteger(userId) && NumberValidator.isInteger(fileId)) {
            User user = userService.getById(Integer.parseInt(userId));
            File file = fileService.getById(Integer.parseInt(fileId));
            if (user != null && file != null) {
                Event event = new Event(user, file);
                eventService.save(event);
                writer.println(objectMapper.writeValueAsString(event));
                return;
            }
        }
        resp.setStatus(404);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        String paramId = req.getPathInfo();
        if (paramId != null) {
            if (NumberValidator.isInteger(paramId.substring(1))) {
                int id = Integer.parseInt(paramId.substring(1));
                Event event = eventService.getById(id);
                if (event != null) {
                    writer.println(objectMapper.writeValueAsString(event));
                    return;
                }
            }
            resp.setStatus(404);
        } else {
            List<Event> events = eventService.getAll();
            writer.println(objectMapper.writeValueAsString(events));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Map<String, String> jsonElements = objectMapper.readValue(reader, new TypeReference<>() {});
        String eventId = jsonElements.get("eventId");
        String userId = jsonElements.get("userId");
        String fileId = jsonElements.get("fileId");
        if (req.getPathInfo() == null && NumberValidator.isInteger(eventId)) {
            Event event = eventService.getById(Integer.parseInt(eventId));
            if (event != null && NumberValidator.isInteger(userId)) {
                User user = userService.getById(Integer.parseInt(userId));
                if (user != null) {
                    event.setUser(user);
                } else {
                    resp.setStatus(404);
                    return;
                }
            }
            if (event != null && NumberValidator.isInteger(fileId)) {
                File file = fileService.getById(Integer.parseInt(fileId));
                if (file != null) {
                    event.setFile(file);
                } else {
                    resp.setStatus(404);
                    return;
                }
            }
            event = eventService.update(event);
            if (event != null) {
                writer.println(objectMapper.writeValueAsString(event));
                return;
            }
        }
        resp.setStatus(404);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String paramId = req.getPathInfo();
        if (paramId != null && NumberValidator.isInteger(paramId.substring(1))) {
            int id = Integer.parseInt(paramId.substring(1));
            if (eventService.deleteById(id)) {
                return;
            }
        }
        resp.setStatus(404);
    }
}
