package ua.net.yason.corpus.meta.model.export.factory;

import ua.net.yason.corpus.meta.model.AuthorModel;
import ua.net.yason.corpus.meta.model.CorpusModel;
import ua.net.yason.corpus.meta.model.export.ExportAuthorModel;

import static ua.net.yason.corpus.meta.model.export.factory.ExportCorpusFactory.fillCodeNames;

/**
 *
 * @author Yason
 */
public class ExportAuthorFactory {

    public ExportAuthorModel create(CorpusModel model, String author) {
        ExportAuthorModel result = new ExportAuthorModel();
        AuthorModel authorModel = model.findAuthor(author);
        if (authorModel != null) {
            copyFields(result, authorModel);
            result.setRegions(fillCodeNames(model.getRegions(), authorModel.getRegions()));
        }
        return result;
    }
    
    private void copyFields(ExportAuthorModel dest, AuthorModel src) {
        dest.setNames(src.getNames());
        dest.setBirthday(src.getBirthday());
        dest.setImmigrationDate(src.getImmigrationDate());
    }
    
}
