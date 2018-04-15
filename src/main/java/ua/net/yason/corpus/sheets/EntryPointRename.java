package ua.net.yason.corpus.sheets;

import ua.net.yason.corpus.sheets.factory.CorpusModelFactory;
import ua.net.yason.corpus.meta.model.CorpusModel;
import com.google.api.services.sheets.v4.Sheets;
import com.ibm.icu.text.Transliterator;
import org.apache.commons.io.FileUtils;
import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import ua.net.yason.corpus.meta.model.TextModel;

/**
 * 
 * @author yason
 */
public class EntryPointRename {

    private static final String SRC_TEXTS_PATH = "d:\\Shvedova\\Texts";
    
    private static String DST_TEXTS_PATH = "d:\\Shvedova\\RenamedTexts";
    
    public static void main(String[] args) throws IOException {
        
        String sheetId = "1cAHbrF6-aQrZiz0K8N0wEzRV_FJxO2fBXa8mAD0luW8";
        Sheets service = GoogleSheetsApi.getSheetsService();
        CorpusModelFactory loader = new CorpusModelFactory();
        CorpusModel corpusMeta = loader.create(service, sheetId);

        String id = "Any-Latin; nfd; [:nonspacing mark:] remove; nfc";
        Transliterator normalizer = Transliterator.getInstance(id);
        Charset charset = Charset.forName("UTF-8");
        
        for (TextModel text : corpusMeta.getTexts()) {
            String originPath = text.getPath();
            String encodedPath = normalizer.transliterate(originPath);
            encodedPath = encodedPath.substring(0, encodedPath.length() - 4);
            encodedPath = encodedPath.trim().replaceAll("[–\\.\\s\\-…]", "_");
            encodedPath = encodedPath.trim().replaceAll("_+", "_");
            encodedPath = encodedPath.replaceAll("[\\[\\]’“«»\\?„”'\\,\\ʹʺ!\\(\\)]", "");
            encodedPath = encodedPath.replaceAll("[^\\p{ASCII}]", "");
            encodedPath = charset.decode(charset.encode(encodedPath)).toString();
            encodedPath += ".txt";
            String firstChar = encodedPath.substring(0, 1).toUpperCase();
            encodedPath = firstChar + "\\" + encodedPath;
            System.out.println(encodedPath);
            File srcFile = new File(SRC_TEXTS_PATH + "\\" + originPath);
            File destFile = new File(DST_TEXTS_PATH + "\\" + encodedPath);
            destFile.getParentFile().mkdirs();
            if (!destFile.exists()){
            FileUtils.copyFile(srcFile, destFile);
            }
        }
    }
}
