package ua.net.yason.corpus.sheets.factory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ua.net.yason.corpus.meta.model.TextModel;
import ua.net.yason.corpus.sheets.GoogleSheetsApi;
import static ua.net.yason.corpus.sheets.GoogleSheetsApi.getRange;
import static ua.net.yason.corpus.sheets.GoogleSheetsApi.getStringValue;

/**
 *
 * @author yason
 */
public class TextModelFactory {

    private String textsRange = "Texts!A2:M6500";

    private final int TEXT_PATH_COLUMN = 0;
    
    private final int TEXT_AUTHOR1_COLUMN = 2;
    private final int TEXT_AUTHOR2_COLUMN = 3;
    private final int TEXT_AUTHOR3_COLUMN = 4;
    private final int TEXT_AUTHOR4_COLUMN = 5;
    
    private final int TEXT_NAME_COLUMN = 6;
    private final int TEXT_GENRE_COLUMN = 7;
    private final int TEXT_LANGUAGE_COLUMN = 8;
    private final int TEXT_DATE_COLUMN = 9;
    
    private final int TEXT_TRANSLATOR1_COLUMN = 10;
    private final int TEXT_TRANSLATOR2_COLUMN = 11;
    private final int TEXT_TRANSLATOR3_COLUMN = 12;

    private List<TextModel> getTexts(ValueRange valueRange){
        List<List<Object>> values = valueRange.getValues();
        List<TextModel> result = new ArrayList<>(values.size());        
        for (List<Object> value : values) {
            TextModel text = new TextModel();
            text.setPath(getStringValue(value, TEXT_PATH_COLUMN));
            
            text.setAuthors(GoogleSheetsApi.getList(value,
                    TEXT_AUTHOR1_COLUMN, TEXT_AUTHOR2_COLUMN,
                    TEXT_AUTHOR3_COLUMN, TEXT_AUTHOR4_COLUMN));
            
            text.setName(getStringValue(value, TEXT_NAME_COLUMN));
            text.setGenre(getStringValue(value, TEXT_GENRE_COLUMN));
            text.setLanguage(getStringValue(value, TEXT_LANGUAGE_COLUMN));
            text.setDate(getStringValue(value, TEXT_DATE_COLUMN));
            
            text.setTranslators(GoogleSheetsApi.getList(value, 
                    TEXT_TRANSLATOR1_COLUMN, TEXT_TRANSLATOR2_COLUMN,
                    TEXT_TRANSLATOR3_COLUMN));
            result.add(text);
        }
        return result;
    }

    public List<TextModel> create(Sheets service, String sheetId) throws IOException {
        return getTexts(getRange(service, sheetId, getTextsRange()));
    }

    public String getTextsRange() {
        return textsRange;
    }    
}
