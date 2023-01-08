import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Operator {

    /**
     *
     * @param rootName строка содержащая некоторый абсолютный путь
     * @return null если путь некорректный и Path в ином случае
     */
    public static Optional<Path> GetRootPath(String rootName) {
        Path rootDirectory = null;
        try {
            rootDirectory = Paths.get(rootName);
        } catch (InvalidPathException e) {
            System.out.println("No such directory exists!");
        }
        return Optional.ofNullable(rootDirectory);
    }

    /**
     *
     * @param directory абсолютный путь до файла, который нужно прочитать
     * @return null если файл не удалось прочитать и List<String> с содержимым в ином случае
     */
    public static Optional<List<String>> ReadFile(Path directory) {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(directory);
        } catch (IOException | SecurityException e) {
            System.out.println("Failed to read file " + directory);
        }
        return Optional.ofNullable(lines);
    }

    /**
     *
     * @param files абсолютные пути до файлов, которые необходимо соединить
     * @param rootDirectory путь по которому будет находиться результирующий файл
     */
    public static void Concatenate(List<Path> files, Path rootDirectory) {
        Path resultPath = rootDirectory.resolve("result.txt");
        File resultFile;
        try {
            resultFile = resultPath.toFile();
            resultFile.createNewFile();
        } catch (IOException | SecurityException e) {
            System.out.println("An error occurred during creating the result file.");
            return;
        }

        try {
            FileWriter writer = new FileWriter(resultFile);
            for (var file: files) {
                var lines = ReadFile(file);
                if (lines.isEmpty()) {
                    continue;
                }
                for (var line: lines.get()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred during writing to the result file.");
        }
    }

    private static List<Path> GetFilesRecursively(Path directory) throws IOException {
        List<Path> textFiles = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path path: stream) {
                if (Files.isDirectory(path)) {
                    textFiles.addAll(GetFilesRecursively(path));
                } else if (Files.isRegularFile(path)) {
                    var type = Files.probeContentType(path);
                    if (type != null && type.equals("text/plain")) {
                        textFiles.add(path);
                    }
                }
            }
        } catch (DirectoryIteratorException e) {
            System.out.println("Failed to get a list of files!");
        }
        return textFiles;
    }

    /**
     *
     * @param rootDirectory путь в котором ведется поиск текстовых файлов
     * @return null, если вознкла ошибка или список текстовых файлов в ином случае
     */
    public static Optional<List<Path>> GetFiles(Path rootDirectory) {
        List<Path> textFiles = null;
        try {
            textFiles = GetFilesRecursively(rootDirectory);
        } catch (IOException e) {
            System.out.println("Wrong root directory!");
        }
        return Optional.ofNullable(textFiles);
    }
}
