package ua.net.yason.corpus.meta;

import ua.net.yason.corpus.meta.model.CorpusModel;
import java.io.PrintStream;

/**
 *
 * @author yason
 */
public class BasicStatistics {

    private final CorpusModel corpusModel;

    public BasicStatistics(CorpusModel corpusModel) {
        this.corpusModel = corpusModel;
    }
    
    public PrintStream printSummary(PrintStream cout) {
        cout.println("Regions count:"+ corpusModel.getRegions().size());
        cout.println("Genres count:"+ corpusModel.getGenres().size());
        cout.println("Languages count:"+ corpusModel.getLanguages().size());
        cout.println("Authors count:"+ corpusModel.getAuthors().size());
        cout.println("Texts count:"+ corpusModel.getTexts().size());
        return cout;
    }
}
