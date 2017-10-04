package ua.net.yason.corpus.meta.model;


import java.util.List;

/**
 *
 * @author yason
 */
public class TextModel {
    
    private String path;
    
    private List<String> authors;
    
    private String name;
    
    private String genre;

    private String language;
    
    private String date;

    private List<String> translators;

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
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(List<String> authors) {
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
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre the genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
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
    public List<String> getTranslators() {
        return translators;
    }

    /**
     * @param translators the translators to set
     */
    public void setTranslators(List<String> translators) {
        this.translators = translators;
    }

    @Override
    public String toString() {
        return "TextModel{" + "path=" + path + 
                ", authors=" + authors + 
                ", name=" + name + 
                ", genre=" + genre + 
                ", language=" + language + 
                ", date=" + date + 
                ", translators=" + translators + '}';
    }
}
