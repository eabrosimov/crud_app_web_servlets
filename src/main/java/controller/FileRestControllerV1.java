package controller;

import model.File;
import service.FileService;
import utility.NumberChecker;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/files")
public class FileRestControllerV1 extends HttpServlet {
    FileService fileService = new FileService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String name = req.getParameter("name");
        String filePath = req.getParameter("filePath");
        if (name == null || name.isEmpty() || name.isBlank() || name.length() > 50 ||
                filePath == null || filePath.isEmpty() || filePath.isBlank() ||
                filePath.length() > 100 || !filePath.startsWith(":/", 1)) {

            writer.println("<h1>Неверный формат</h1>");
            writer.println("Проверьте, что:<br>" +
                    "- название параметров 'name' и 'filePath' соответственно<br>" +
                    "- имя и путь не пустые<br>" +
                    "- путь начинается с 'some_disk:/'<br>" +
                    "- имя не превышает 50 символов<br>" +
                    "- путь не превышает 100 символов");
        } else {
            File file = new File();
            file.setName(name);
            file.setFilePath(filePath);
            file = fileService.save(file);
            if(file == null){
                writer.println("<h1>Неудачно</h1>");
                writer.print("Файл с таким именем и путем уже существует");
            } else {
                writer.println("<h1>Успешно</h1>");
                writer.print(file);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();

        String paramId = req.getParameter("id");
        if(paramId == null){
            List<File> files = fileService.getAll();
            for(File f: files){
                writer.println(f + "<br>");
            }
        } else {
            if (NumberChecker.isInteger(paramId)) {
                int id = Integer.parseInt(paramId);
                File file = fileService.getById(id);
                if (file == null) {
                    writer.println("<h1>Неудачно</h1>");
                    writer.print("Файл с таким id не сущесвтует");
                } else {
                    writer.print(file);
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
        String filePath = req.getParameter("filePath");
        if(NumberChecker.isInteger(paramId)) {
            int id = Integer.parseInt(paramId);
            File file = fileService.getById(id);
            if(file == null) {
                writer.println("<h1>Неудачно</h1>");
                writer.print("Файл с таким id не существует");
            } else {
                if(name != null){
                    if (name.isEmpty() || name.isBlank() || name.length() > 50) {
                        writer.println("<h1>Неверный формат имени</h1>");
                        writer.println("Проверьте, что:<br>" +
                                "- имя не пустое<br>" +
                                "- не превышет 50 символов");
                        return;
                    } else {
                        file.setName(name);
                    }
                }

                if(filePath != null){
                    if (filePath.isEmpty() || filePath.isBlank() ||
                            filePath.length() > 10 || !filePath.startsWith(":/", 1)) {
                        writer.println("<h1>Неверный формат пути</h1>");
                        writer.println("Проверьте, что:<br>" +
                                "- путь не пустой<br>" +
                                "- начинается с 'some_disk:/'<br>" +
                                "- не превышет 100 символов");
                        return;
                    } else {
                        file.setFilePath(filePath);
                    }
                }

                file = fileService.update(file);
                if(file == null) {
                    writer.println("<h1>Неудачно</h1>");
                    writer.print("Файл с таким именем и путем уже существует");
                } else {
                    writer.println("<h1>Успешно</h1>");
                    writer.print(file);
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
        if (NumberChecker.isInteger(paramId)) {
            int id = Integer.parseInt(paramId);
            boolean isDeleted = fileService.deleteById(id);
            if(isDeleted) {
                writer.println("<h1>Успешно</h1>");
                writer.println("Файл удален");
            } else {
                writer.println("<h1>Неудачно</h1>");
                writer.println("Файл с таким id не сущесвтует");
            }
        } else {
            writer.println("<h1>Неудачно</h1>");
            writer.print("Убедитесь, что:<br>" +
                    "- id содержат только цифры<br>" + "" +
                    "- название параметра 'id'");
        }
    }
}
