package ua.net.yason.corpus.sheets;

import ua.net.yason.corpus.sheets.factory.CorpusModelFactory;
import ua.net.yason.corpus.meta.model.CorpusModel;
import com.google.api.services.sheets.v4.Sheets;
import java.io.File;

import java.io.IOException;

/**
 * 
 * @author yason
 */
public class EntryPointTag {

    private static final String SRC_TEXTS_PATH = "d:\\GoogleDrive\\Шведова\\Ukr-corp\\Texts\\";

    private static final String TOKENS_PATH = "d:\\Shvedova\\Tokens\\";
    
    private static final String TEXTS_EXTENSION = ".txt";
    
    private static final String TOKENS_EXTENSION = ".xtag";

    private static String getDestFileName(File textFile)
    {
        String fileName = textFile.getAbsolutePath();
        
        fileName = fileName.replace(SRC_TEXTS_PATH, TOKENS_PATH);
        fileName = fileName.replace(TEXTS_EXTENSION, TOKENS_EXTENSION);
        
        return fileName;
    }
    
    public static void main(String[] args) throws IOException {
        
        String sheetId = "1cAHbrF6-aQrZiz0K8N0wEzRV_FJxO2fBXa8mAD0luW8";
        Sheets service = GoogleSheetsApi.getSheetsService();
        CorpusModelFactory loader = new CorpusModelFactory();
        CorpusModel corpusMeta = loader.create(service, sheetId);

        corpusMeta.getTexts().stream().parallel().forEach( text -> {
            File srcFile = new File(SRC_TEXTS_PATH + text.getPath());
            File destFile = new File(getDestFileName(srcFile));
            destFile.getParentFile().mkdirs();
            FolderLoop.tagFile(srcFile.getAbsolutePath(), 
                    destFile.getAbsolutePath(),
                    corpusMeta.getTexts().size());
        });
    }
}
