public class Main {
    public static void main(String[] args) {
        var rootName = UserInterface.GetRootName();
        var rootDirectory = Operator.GetRootPath(rootName);
        if (rootDirectory.isEmpty()) {
            return;
        }
        var files = Operator.GetFiles(rootDirectory.get());
        if (files.isEmpty()) {
            return;
        }
        var textFiles = files.get();
        java.util.Collections.sort(textFiles, new PathComparator());
        System.out.println("\nText files sorted by name:");
        for (var textFile: textFiles) {
            System.out.println(textFile.getFileName());
        }
        Operator.Concatenate(textFiles, rootDirectory.get());
        System.out.println("\nCheck " + rootName + " for result.txt file.");
    }
}