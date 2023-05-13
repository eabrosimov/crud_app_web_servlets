package controller;

import model.Event;
import model.File;
import model.User;
import service.EventService;
import service.FileService;
import service.UserService;
import utility.NumberChecker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/events")
public class EventRestControllerV1 extends HttpServlet {
    EventService eventService = new EventService();
    UserService userService = new UserService();
    FileService fileService = new FileService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        if (NumberChecker.isInteger(userId) && NumberChecker.isInteger(fileId)) {
            int uId = Integer.parseInt(userId);
            int fId = Integer.parseInt(fileId);
            Event event = new Event();
            User user = userService.getById(uId);
            File file = fileService.getById(fId);
            if(user != null && file != null){
                event.setUser(user);
                event.setFile(file);
                event = eventService.save(event);
                writer.println("<h1>Успешно</h1>");
                writer.print(event);
            } else {
                writer.println("<h1>Неудачно</h1>");
                writer.print("Такой пользователь или файл не существует");
            }

        } else {
            writer.println("<h1>Неудачно</h1>");
            writer.print("Убедитесь, что:<br>" +
                    "userId и fileId содержат только цифры<br>" + "" +
                    "- название параметров 'userId' и 'fileId' соответственно");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        if(paramId == null){
            List<Event> events = eventService.getAll();
            for(Event e: events){
                writer.println(e + "<br>");
            }
        } else {
            if (NumberChecker.isInteger(paramId)) {
                int id = Integer.parseInt(paramId);
                Event event = eventService.getById(id);
                if (event == null) {
                    writer.println("<h1>Неудачно</h1>");
                    writer.print("Событие с таким id не сущесвтует");
                } else {
                    writer.print(event);
                }
            } else {
                writer.println("<h1>Неудачно</h1>");
                writer.print("Убедитесь, что:<br>" +
                        "- id содержат только цифры<br>" + "" +
                        "- название параметра 'id'");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        String userId = req.getParameter("userId");
        String fileId = req.getParameter("fileId");
        if (NumberChecker.isInteger(paramId)) {
            int id = Integer.parseInt(paramId);
            Event event = eventService.getById(id);
            if(event == null) {
                writer.println("<h1>Неудачно</h1>");
                writer.print("Файл с таким id не существует");
            } else {
                if(userId != null) {
                    if(NumberChecker.isInteger(userId)){
                        int uId = Integer.parseInt(userId);
                        User user = userService.getById(uId);
                        if(user == null){
                            writer.println("<h1>Неудачно</h1>");
                            writer.print("Пользователь с таким id не существует");
                            return;
                        } else {
                            event.setUser(user);
                        }
                    }
                }
                if(fileId != null) {
                    if(NumberChecker.isInteger(fileId)){
                        int fId = Integer.parseInt(fileId);
                        File file = fileService.getById(fId);
                        if(file == null){
                            writer.println("<h1>Неудачно</h1>");
                            writer.print("Файл с таким id не существует");
                            return;
                        } else {
                            event.setFile(file);
                        }
                    }
                }
                event = eventService.update(event);
                writer.println("<h1>Успешно</h1>");
                writer.print(event);
            }
        } else {
            writer.println("<h1>Неудачно</h1>");
            writer.print("id должен содержать только цифры");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        if(NumberChecker.isInteger(paramId)) {
            int id = Integer.parseInt(paramId);
            boolean isDeleted = eventService.deleteById(id);
            if(isDeleted) {
                writer.println("<h1>Успешно</h1>");
                writer.println("Событие удалено");
            } else {
                writer.println("<h1>Неудачно</h1>");
                writer.println("Событие с таким id не сущесвтует");
            }
        } else {
            writer.println("<h1>Неудачно</h1>");
            writer.print("Убедитесь, что:<br>" +
                    "- id содержат только цифры<br>" + "" +
                    "- название параметра 'id'");
        }
    }
}
