package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Event;
import service.EventService;
import utility.NumberValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {
    EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter writer = resp.getWriter();
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            if (NumberValidator.isInteger(pathInfo.substring(1))) {
                int id = Integer.parseInt(pathInfo.substring(1));
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && NumberValidator.isInteger(pathInfo.substring(1))) {
            int id = Integer.parseInt(pathInfo.substring(1));
            if (eventService.deleteById(id)) {
                return;
            }
        }
        resp.setStatus(404);
    }
}
