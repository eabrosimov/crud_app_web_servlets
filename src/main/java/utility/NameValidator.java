package utility;

public class NameValidator {
    public static boolean isValidUserName(String userName) {
        if (userName != null && !userName.isEmpty() && !userName.isBlank() && userName.length() <= 25) {
            return userName.matches("[a-zA-Z\\d]+");
        }

        return false;
    }

    public static boolean isValidFileName(String fileName) {
        if (fileName != null && !fileName.isEmpty() && !fileName.isBlank() && fileName.length() <= 50) {
            return fileName.matches("[^/\\\\:*?<>|]+");
        }

        return false;
    }

    public static boolean isValidFilePath(String filePath) {
        if (filePath != null && !filePath.isEmpty() && !filePath.isBlank() && filePath.length() <= 100) {
            return filePath.matches("[a-zA-Z]:\\\\[^/:*?<>|]+\\\\");
        }

        return false;
    }
}