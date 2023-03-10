import java.nio.file.Path;
import java.util.Comparator;

public class PathComparator implements Comparator<Path> {
    public int compare(Path path1, Path path2) {
        return path1.getFileName().compareTo(path2.getFileName());
    }
}
