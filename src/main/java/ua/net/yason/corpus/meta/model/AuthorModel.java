package ua.net.yason.corpus.meta.model;


import java.util.List;

/**
 *
 * @author yason
 */
public class AuthorModel {
            
    private List<String> names;

    private String birthday;

    private String immigrationDate;

    private List<String> regions;

    public String getBirthday() {
        return birthday;
    }

    public String getImmigrationDate() {
        return immigrationDate;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getNames() {
        return names;
    }

    public List<String> getRegions() {
        return regions;
    }

    @Override
    public String toString() {
        return "AuthorMeta{" + "names=" + names +
                ", birthday=" + birthday +
                ", immigrationDate=" + immigrationDate + 
                ", regions=" + regions + '}';
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setImmigrationDate(String immigrationDate) {
        this.immigrationDate = immigrationDate;
    }
}
