package ua.net.yason.corpus.meta.model.export.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ua.net.yason.corpus.meta.model.CorpusModel;
import ua.net.yason.corpus.meta.model.TextModel;
import ua.net.yason.corpus.meta.model.export.ExportCodeNameModel;
import ua.net.yason.corpus.meta.model.export.ExportCorpusModel;
import ua.net.yason.corpus.meta.model.export.ExportTextModel;

/**
 *
 * @author yason
 */
public class ExportCorpusFactory {

    private final ExportTextFactory exportTextFactory = new ExportTextFactory();

    public ExportCorpusModel create(CorpusModel model) {
        ExportCorpusModel result = new ExportCorpusModel();
        List<ExportTextModel> texts = new ArrayList<>(model.getTexts().size());
        for (TextModel textModel : model.getTexts()) {
            texts.add(exportTextFactory.create(model, textModel));
        }
        result.setTexts(texts);
        return result;
    }

    public static ExportCodeNameModel fillCodeName(Map<String, String> map, String name) {
        ExportCodeNameModel result = null;
        if (name != null) {
            String genreCode = map.get(name);
            result = new ExportCodeNameModel(name, genreCode);
        }
        return result;
    }

    public static List<ExportCodeNameModel> fillCodeNames(Map<String, String> map, List<String> names) {
        List<ExportCodeNameModel> result = new ArrayList<>(names.size());
        for (String name : names) {
            result.add(fillCodeName(map, name));
        }
        return result;
    }
}
