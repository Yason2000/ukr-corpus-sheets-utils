package ua.net.yason.corpus.sheets.factory;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ua.net.yason.corpus.meta.model.AuthorModel;
import ua.net.yason.corpus.sheets.GoogleSheetsApi;
import static ua.net.yason.corpus.sheets.GoogleSheetsApi.getRange;
import static ua.net.yason.corpus.sheets.GoogleSheetsApi.getStringValue;

/**
 *
 * @author yason
 */
public class AuthorModelFactory {

    private String authorsRange = "Authors!A2:J4000";

    private final int AUTHOR_NAME1_COLUMN = 0;
    private final int AUTHOR_BIRTHDAY_COLUMN = 1;
    private final int AUTHOR_IMIGRATION_COLUMN = 2;
    private final int AUTHOR_NAME2_COLUMN = 3;
    private final int AUTHOR_NAME3_COLUMN = 4;
    private final int AUTHOR_REGION1_COLUMN = 5;
    private final int AUTHOR_REGION2_COLUMN = 6;
    private final int AUTHOR_REGION3_COLUMN = 7;
    private final int AUTHOR_REGION4_COLUMN = 8;
    private final int AUTHOR_REGION5_COLUMN = 9;

    private List<AuthorModel> getAuthors(ValueRange valueRange){
        List<List<Object>> values = valueRange.getValues();
        List<AuthorModel> result = new ArrayList<>(values.size());        
        for (List<Object> value : values) {
            AuthorModel author = new AuthorModel();
            author.setNames(GoogleSheetsApi.getList(value,
                    AUTHOR_NAME1_COLUMN, AUTHOR_NAME2_COLUMN, AUTHOR_NAME3_COLUMN));
            
            author.setBirthday(getStringValue(value, AUTHOR_BIRTHDAY_COLUMN));
            author.setImmigrationDate(getStringValue(value, AUTHOR_IMIGRATION_COLUMN));

            author.setRegions(GoogleSheetsApi.getList(value, 
                    AUTHOR_REGION1_COLUMN, AUTHOR_REGION2_COLUMN,
                    AUTHOR_REGION3_COLUMN, AUTHOR_REGION4_COLUMN,
                    AUTHOR_REGION5_COLUMN));
            result.add(author);
        }
        return result;
    }

    public List<AuthorModel> create(Sheets service, String sheetId) throws IOException {
        return getAuthors(getRange(service, sheetId, getAuthorsRange()));
    }

    public String getAuthorsRange() {
        return authorsRange;
    }
}
