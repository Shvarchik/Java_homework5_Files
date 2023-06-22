import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardCopyOption.COPY_ATTRIBUTES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DirectoryWork {

          public static void main(String[] args) {
            printTree (new File("."), "", true);
            try {
                copyFiles(new File("."));
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }

        /**
         * @param file
         * @param indent
         * @param isLast
         */
        public static void printTree (File file, String indent, boolean isLast) {
            System.out.print(indent); // рисуем отступ
            if (isLast) {
                System.out.print("└─");
                indent += "  ";
            } else {
                System.out.print("├─");
                indent += "│ ";
            }
            System.out.println(file.getName());

            File[] files = file.listFiles();
            if (files == null)
                return;

            List<File> filesOnly = new ArrayList<>(); // список простых файлов
            int counter = 0;
            for (File fileItem : files) {
                if (fileItem.isDirectory()) {
                    printTree(fileItem, indent, counter == files.length - 1);
                    counter++;
                } else {
                    filesOnly.add(fileItem);
                }
            }
            if (!filesOnly.isEmpty()) {
                for (File fileItem : filesOnly) {
                    printTree (fileItem, indent, counter == files.length - 1);
                    counter++;
                }
            }
        }

        public static void copyFiles (File file) throws IOException {
            String path = file.getName() + "//backup";
            try {
                Files.createDirectory(Paths.get(path));
            } catch (FileAlreadyExistsException e){
                System.out.println("Директория уже существует");
            }
            File [] files = file.listFiles();
            for (int i = 0; i<files.length; i++){
                if (files[i].isFile()) {
                    Path copy_from = files[i].toPath();
                    Path copy_to = Paths.get(path, files[i].getName());
                    Files.copy(copy_from, copy_to, REPLACE_EXISTING, COPY_ATTRIBUTES);
                }
            }
        }
    }


