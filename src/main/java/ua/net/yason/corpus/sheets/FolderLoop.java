package ua.net.yason.corpus.sheets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mozilla.universalchardet.ReaderFactory;
import org.mozilla.universalchardet.UniversalDetector;

/**
 *
 * @author Yason
 */
public class FolderLoop {

    //private static final String TEXTS_PATH = "d:\\Shvedova\\Texts";
    //private static final String TEXTS_PATH = "d:\\GoogleDrive\\Шведова\\Ukr-corp\\.new-names";
    private static final String TEXTS_PATH = "d:\\GoogleDrive\\Шведова\\Ukr-corp\\Texts";

    private static final String TEXTS_EXTENSION = ".txt";
    
    private static final String TOKENS_PATH = "d:\\Shvedova\\Tokens\\";
    
    private static final String TOKENS_EXTENSION = ".xtag";

    private static final String TAG_SCRIPT = "c:\\Libraries\\brown-uk\\nlp_uk\\src\\main\\groovy\\org\\nlp_uk\\tools\\TagText.groovy";
    
    private static final String CORRECT_CHARSET = "UTF-8";
    
    public static void main(String[] args) {
        List<File> textFiles = getTextsFiles();
        //List<File> wrongFiles = detectWrongEncoding(textFiles);
        //convertEncoding(wrongFiles);
        tagFiles(textFiles);        
    }
    
    private static String getDestFileName(File textFile)
    {
        String fileName = textFile.getName();
        String folderName = TOKENS_PATH + textFile.getParentFile().getName();
        new File(folderName).mkdirs();
        return folderName + "\\" + fileName.substring(0, fileName.length() - TEXTS_EXTENSION.length()) + TOKENS_EXTENSION;
    }
    
    private static File convertEncoding(File file) {
        String fileName = file.getAbsolutePath();
        File tmpFile = new File(fileName + ".tmp");
        try (Reader reader = ReaderFactory.createReaderFromFile(file)) {
            BufferedReader in = new BufferedReader(reader);
            String line;
            System.out.println("Converting file " + fileName);
            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), CORRECT_CHARSET))) {
                while ((line = in.readLine()) != null) {
                    out.write(line);
                    out.write("\r\n");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error convert encoding of file " + fileName + ": " + ex.getMessage());
        }
        return tmpFile;
    }
    
    private static void convertEncoding(List<File> files) {
        for (File file : files) {
            File result = convertEncoding(file);
            if (result != null) {
                if (!swapFileNamesAndRemove(file, result)){
                    System.err.println("Error rename file after convert to UTF-8: " + result.getAbsolutePath());
                }
            }
        }
    }
    
    private static boolean swapFileNamesAndRemove(File src, File dest) {
        File bakFile = new File(src.getAbsolutePath() + ".bak");
        if (src.renameTo(bakFile)) {
            if (dest.renameTo(src)) {
                return bakFile.delete();
            } else {
                bakFile.renameTo(src);
            }
        }
        return false;
    }
    
    private static List<File> detectWrongEncoding(List<File> textFiles) {
        List<File> wrongFiles = new ArrayList<>(textFiles.size());
        textFiles.stream().forEach((textFile) -> {
            String encoding = null;
            try {
                encoding = UniversalDetector.detectCharset(textFile);
            } catch (IOException ex) {
                System.err.println("Error detect charset: " + ex.getMessage());
            }
            String sourceFileName = textFile.getAbsolutePath();
            if (encoding != null && !CORRECT_CHARSET.equals(encoding)) {
                System.out.println("ENCODING of " + sourceFileName + " is " + encoding);
                wrongFiles.add(textFile);
            }
        });
        System.out.println("Files with wrong encoding " + wrongFiles.size());
        return wrongFiles;
    }
    
    private static void tagFiles(List<File> textFiles) {
        int size = textFiles.size();
        textFiles.stream().parallel().forEach((textFile) -> {
            String sourceFileName = textFile.getAbsolutePath();
            String destFileName = getDestFileName(textFile);
            tagFile(sourceFileName, destFileName, size);
        });
    }

    private static final AtomicInteger count = new AtomicInteger();
    
    private static void tagFile(String sourceFileName, String destFileName, int size) {
        String[] cmd = {"cmd", "/c", "groovy", TAG_SCRIPT, "-x", "-i", sourceFileName, "-o", destFileName};
        try {
            System.out.println("PROCESS(" + count.incrementAndGet() + "/" + size + "):" + sourceFileName + " TO " + destFileName);
            
//            for (String string : cmd) {
//                System.out.print(string);
//                System.out.print(" ");
//            }
//            System.out.println();
            
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException ex) {
            System.err.println("Error call tokenizer: " + ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(FolderLoop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static List<File> getTextsFiles() {
        List<File> result = new ArrayList<>();
        Path wholeTextsDir = FileSystems.getDefault().getPath(TEXTS_PATH);
        try (DirectoryStream<Path> authorsStream = Files.newDirectoryStream(wholeTextsDir)) {
            for (Path authorDir : authorsStream) {
                File authorDirFile = authorDir.toFile();
                if (authorDirFile.isDirectory()) {
                    try (DirectoryStream<Path> textsStream = Files.newDirectoryStream(authorDir)) {
                        for (Path textsDir : textsStream) {
                            File textFile = textsDir.toFile();
                            if (!textFile.isDirectory() && textFile.getName().endsWith(TEXTS_EXTENSION)) {
                                result.add(textFile);
                            }
                        }
                    } catch (IOException ex) {
                        System.err.println("Error get texts files list: " + ex.getMessage());
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Error get author folders list: " + ex.getMessage());
        }
        return result;
    }
}
