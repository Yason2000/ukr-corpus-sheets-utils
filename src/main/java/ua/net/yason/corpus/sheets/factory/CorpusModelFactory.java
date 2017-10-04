package ua.net.yason.corpus.sheets.factory;


import ua.net.yason.corpus.meta.model.CorpusModel;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import static ua.net.yason.corpus.sheets.GoogleSheetsApi.getMap;
import static ua.net.yason.corpus.sheets.GoogleSheetsApi.getRange;


/**
 *
 * @author yason
 */
public class CorpusModelFactory {
    
    private String genresRange = "Genres!B1:C50";

    private String regionsRange = "Regions!B1:C100";

    private String languagesRange = "Languages!B1:C100";
    
    private final AuthorModelFactory authorModelFactory = new AuthorModelFactory();
    
    private final TextModelFactory textModelFactory = new TextModelFactory();

    public CorpusModel create(Sheets service, String sheetId) throws IOException {
        return new CorpusModel(
                getMap(getRange(service, sheetId, getRegionsRange())),
                getMap(getRange(service, sheetId, getGenresRange())),
                getMap(getRange(service, sheetId, getLanguagesRange())),
                authorModelFactory.create(service, sheetId),
                textModelFactory.create(service, sheetId));
    }

    private String getRegionsRange() {
        return regionsRange;
    }

    private String getLanguagesRange() {
        return languagesRange;
    }

    private String getGenresRange() {
        return genresRange;
    }
}