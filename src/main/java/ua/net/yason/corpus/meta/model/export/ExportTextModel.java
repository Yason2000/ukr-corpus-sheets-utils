package ua.net.yason.corpus.meta.model.export;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author yason
 */
public class ExportTextModel implements Serializable {

    private static final long serialVersionUID = -4602106404941588749L;
    
    private String path;
    
    private List<ExportAuthorModel> authors;
    
    private String name;
    
    private ExportCodeNameModel genre;

    private ExportCodeNameModel language;
    
    private String date;

    private List<ExportAuthorModel> translators;

    public ExportTextModel() {
    }
    
    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the authors
     */
    @XmlElement(name = "author")
    public List<ExportAuthorModel> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(List<ExportAuthorModel> authors) {
        this.authors = authors;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the genre
     */
    public ExportCodeNameModel getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(ExportCodeNameModel genre) {
        this.genre = genre;
    }

    /**
     * @return the language
     */
    public ExportCodeNameModel getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(ExportCodeNameModel language) {
        this.language = language;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the translators
     */
    @XmlElement(name = "translator")
    public List<ExportAuthorModel> getTranslators() {
        return translators;
    }

    /**
     * @param translators the translators to set
     */
    public void setTranslators(List<ExportAuthorModel> translators) {
        this.translators = translators;
    }
}
