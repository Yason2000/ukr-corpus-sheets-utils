package ua.net.yason.corpus.meta.model.export.factory;

import java.util.ArrayList;
import java.util.List;
import ua.net.yason.corpus.meta.model.CorpusModel;
import ua.net.yason.corpus.meta.model.TextModel;
import ua.net.yason.corpus.meta.model.export.ExportAuthorModel;
import ua.net.yason.corpus.meta.model.export.ExportTextModel;

import static ua.net.yason.corpus.meta.model.export.factory.ExportCorpusFactory.fillCodeName;

/**
 *
 * @author yason
 */
class ExportTextFactory {
    
    private final ExportAuthorFactory exportAuthorFactory = new ExportAuthorFactory();

    public ExportTextModel create(CorpusModel model, TextModel textModel) {
        ExportTextModel result = new ExportTextModel();

        copyFields(result, textModel);

        result.setGenre(fillCodeName(model.getGenres(), textModel.getGenre()));
        result.setLanguage(fillCodeName(model.getLanguages(), textModel.getLanguage()));
        
        result.setAuthors(getAuthors(model, textModel.getAuthors()));
        result.setTranslators(getAuthors(model, textModel.getTranslators()));
        
        return result;
    }

    private List<ExportAuthorModel> getAuthors(CorpusModel model, List<String> authors) {
        List<ExportAuthorModel> result = new ArrayList<>();
        for (String author : authors) {
            result.add(exportAuthorFactory.create(model, author));
        }
        return result;
    }
    
    private void copyFields(ExportTextModel dest, TextModel src) {
        dest.setPath(src.getPath());
        dest.setName(src.getName());
        dest.setDate(src.getDate());
        
        dest.setPublicationCity(src.getPublicationCity());
        dest.setPublisher(src.getPublisher());
        dest.setPublicationYear(src.getPublicationYear());
        dest.setPublication(src.getPublication());
        dest.setUri(src.getUri());
    }
}
