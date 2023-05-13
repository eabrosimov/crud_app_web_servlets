package controller;

import model.User;
import service.UserService;
import utility.NumberChecker;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users")
public class UserRestControllerV1 extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String name = req.getParameter("name");
        if(name == null){
            writer.println("<h1>Неверный формат</h1>");
            writer.println("Имя параметра должно быть 'name'");
        }
        String clearedName = name.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
        if (clearedName.isEmpty() || clearedName.isBlank() || clearedName.length() > 25) {
            writer.println("<h1>Неверный формат</h1>");
            writer.println("Проверьте, что:<br>" +
                    "- имя не пустое<br>" +
                    "- не содержит недопустимых символов<br>" +
                    "- не превышет 25 символов");
        } else {
            User user = new User();
            user.setName(clearedName);
            user = userService.save(user);
            if(user == null) {
                writer.println("<h1>Неудачно</h1>");
                writer.print("Пользователь с таким именем уже существует");
            } else {
                writer.println("<h1>Успешно</h1>");
                writer.print(user);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        if(paramId == null){
            List<User> users = userService.getAll();
            for(User u: users) {
                writer.println(u + "<br>");
            }
        } else {
            if(NumberChecker.isInteger(paramId)) {
                int id = Integer.parseInt(paramId);
                User user = userService.getById(id);
                if(user == null) {
                    writer.println("<h1>Неудачно</h1>");
                    writer.print("Пользователь с таким id не сущесвтует");
                } else {
                    writer.print(user);
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        String name = req.getParameter("name");
        if(NumberChecker.isInteger(paramId)) {
            int id = Integer.parseInt(paramId);
            User user = userService.getById(id);
            if(user == null) {
                writer.println("<h1>Неудачно</h1>");
                writer.print("Пользователь с таким id не существует");
            } else {
                String clearedName = name.replaceAll("[^a-zA-Zа-яёА-ЯЁ]", "");
                if (clearedName.isEmpty() || clearedName.isBlank() || clearedName.length() > 25) {
                    writer.println("<h1>Неверный формат</h1>");
                    writer.println("Проверьте, что:<br>" +
                            "- имя не пустое<br>" +
                            "- не содержит недопустимых символов<br>" +
                            "- не превышет 25 символов");
                } else {
                    user.setName(clearedName);
                    user = userService.update(user);
                    if(user == null) {
                        writer.println("<h1>Неудачно</h1>");
                        writer.print("Пользователь с таким именем уже существует");
                    } else {
                        writer.println("<h1>Успешно</h1>");
                        writer.print(user);
                    }
                }
            }
        } else {
            writer.println("<h1>Неудачно</h1>");
            writer.print("id должен содержать только цифры");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        if(NumberChecker.isInteger(paramId)) {
            int id = Integer.parseInt(paramId);
            boolean isDeleted = userService.deleteById(id);
            if(isDeleted) {
                writer.println("<h1>Успешно</h1>");
                writer.println("Пользователь удален");
            } else {
                writer.println("<h1>Неудачно</h1>");
                writer.println("Пользователь с таким id не сущесвтует");
            }
        } else {
            writer.println("<h1>Неудачно</h1>");
            writer.print("Убедитесь, что:<br>" +
                    "- id содержат только цифры<br>" + "" +
                    "- название параметра 'id'");
        }
    }
}
