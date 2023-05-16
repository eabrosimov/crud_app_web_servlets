package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
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

@WebServlet("/v1/users/*")
public class UserRestControllerV1 extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Map<String, String> jsonElements = objectMapper.readValue(reader, new TypeReference<>() {});
        String name = jsonElements.get("name");
        if (req.getPathInfo() == null && NameValidator.isValidUserName(name)) {
            User user = new User(name);
            user = userService.save(user);
            if (user != null) {
                writer.println(objectMapper.writeValueAsString(user));
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
                User user = userService.getById(id);
                if (user != null) {
                    writer.println(objectMapper.writeValueAsString(user));
                    return;
                }
            }
            resp.setStatus(404);
        } else {
            List<User> users = userService.getAll();
            writer.println(objectMapper.writeValueAsString(users));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        Map<String, String> jsonElements = objectMapper.readValue(reader, new TypeReference<>() {});
        String paramId = jsonElements.get("id");
        String name = jsonElements.get("name");
        if (req.getPathInfo() == null && NumberValidator.isInteger(paramId) && NameValidator.isValidUserName(name)) {
            User user = userService.getById(Integer.parseInt(paramId));
            if (user != null) {
                user.setName(name);
                user = userService.update(user);
                if (user != null) {
                    writer.println(objectMapper.writeValueAsString(user));
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
            if (userService.deleteById(id)) {
                return;
            }
        }
        resp.setStatus(404);
    }
}
