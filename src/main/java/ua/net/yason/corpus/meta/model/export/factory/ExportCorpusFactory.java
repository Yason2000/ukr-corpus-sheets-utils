package ua.net.yason.corpus.meta.model.export.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ua.net.yason.corpus.meta.model.CorpusModel;
import ua.net.yason.corpus.meta.model.export.ExportCodeNameModel;
import ua.net.yason.corpus.meta.model.export.ExportCorpusModel;
import ua.net.yason.corpus.meta.model.export.ExportTextModel;

/**
 *
 * @author yason
 */
public class ExportCorpusFactory {

    private final ExportTextFactory exportTextFactory = new ExportTextFactory();

    public ExportCorpusModel create(final CorpusModel model) {
        ExportCorpusModel result = new ExportCorpusModel();
        List<ExportTextModel> texts = model.getTexts().stream()
                .map(text -> {return exportTextFactory.create(model, text);})
                .collect(Collectors.toList());
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
