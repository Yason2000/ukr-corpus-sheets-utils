package ua.net.yason.corpus.meta.model;

import java.util.List;
import java.util.Map;

/**
 *
 * @author yason
 */
public class CorpusModel {

    private final Map<String, String> regions;

    private final Map<String, String> genres;

    private final Map<String, String> languages;

    private final List<AuthorModel> authors;

    private final List<TextModel> texts;

    public CorpusModel(Map<String, String> regions, Map<String, String> genres,
            Map<String, String> languages, List<AuthorModel> authors, List<TextModel> texts) {
        this.regions = regions;
        this.genres = genres;
        this.languages = languages;
        this.authors = authors;
        this.texts = texts;
    }

    public List<TextModel> getTexts() {
        return texts;
    }

    public List<AuthorModel> getAuthors() {
        return authors;
    }

    public Map<String, String> getGenres() {
        return genres;
    }

    public Map<String, String> getLanguages() {
        return languages;
    }

    public Map<String, String> getRegions() {
        return regions;
    }

    @Override
    public String toString() {
        return "UkrCorpusMeta{" + "regions=" + regions
                + ", genres=" + genres
                + ", languages=" + languages
                + ", authors=" + authors
                + ", texts=" + texts + '}';
    }

    public AuthorModel findAuthor(String author) {
        AuthorModel result = null;
        for (AuthorModel authorModel : authors) {
            if (authorModel.getNames().contains(author)) {
                result = authorModel;
                break;
            }
        }
        return result;
    }
}
