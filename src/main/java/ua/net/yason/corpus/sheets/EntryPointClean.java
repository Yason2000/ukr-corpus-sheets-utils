package ua.net.yason.corpus.sheets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Yason
 */
public class EntryPointClean {

    public static String srcRootPath = "d:\\Shvedova\\Texts";
    public static String cleanTextPath = "d:\\Shvedova\\Scripts\\.foreign\\nlp_uk\\src\\main\\groovy\\org\\nlp_uk\\other\\CleanText.groovy";

    public static void main(String[] args) throws IOException {
        Files.list(Paths.get(srcRootPath))
                .parallel()
                .filter(Files::isDirectory)
                .forEach(EntryPointClean::removeGood);
    }

    public static void removeGood(Path path) {
        Path goodPath = path.resolve("good");
        goodPath.toFile().delete();
    }
    
    public static void moveGood(Path path) {
        //System.out.println(path);
        Path goodPath = path.resolve("good");
        try {
            Set<String> oldName = Files.list(path)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toFile().getName().endsWith(".txt"))
                    .map(p -> p.getFileName().toFile().getName())
                    .collect(Collectors.toSet());
            if(!oldName.isEmpty()){
                System.out.println(path);
            }
            
            Files.list(goodPath)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toFile().getName().endsWith(".txt"))
                    .forEach(good -> {
                        File file = good.getFileName().toFile();
                        Path newFile = path.resolve(file.getName());
                        try {
                                    /*
                            if (!file.renameTo(newFile)){
                            System.out.println(newFile);
                            }*/
                            Files.move(good, newFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ex) {
                            Logger.getLogger(EntryPointClean.class.getName()).log(Level.SEVERE, "Can't move file " + good, ex);
                        }
                    });
        } catch (IOException ex) {
            Logger.getLogger(EntryPointClean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void cleanDirectory(Path path) {
        System.out.println(path);
        Path destPath = initDestination(path);
        execCleanText(path.toString());
    }

    private static Path initDestination(Path path) {
        Path destPath = path.resolve("good");
        try {
            Files.deleteIfExists(destPath);
            Files.createDirectory(destPath);
        } catch (IOException ex) {
            Logger.getLogger(EntryPointClean.class.getName())
                    .log(Level.WARNING, "Can't init destination directory", ex);
        }
        return destPath;
    }

    private static void execCleanText(String sourceDir) {
        String[] cmd = {"cmd", "/c", "groovy", cleanTextPath, sourceDir, "-wc", "5"};
        try {

            Process proc = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            
            String s;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException ex) {
            System.err.println("Error call clean text script: " + ex.getMessage());
        } /*catch (InterruptedException ex) {
            Logger.getLogger(EntryPointClean.class.getName())
                    .log(Level.WARNING, "Can't exec clean text script", ex);
        }*/
    }
}
