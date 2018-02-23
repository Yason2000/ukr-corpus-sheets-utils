package ua.net.yason.corpus.meta.model.export;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author yason
 */
public class ExportAuthorModel implements Serializable {

    private static final long serialVersionUID = 2323421040811224140L;
    
    private List<String> names;

    private String birthday;

    private String immigrationDate;

    private List<ExportCodeNameModel> regions;

    public ExportAuthorModel() {
    }

    /**
     * @return the names
     */
    @XmlElement(name = "name")
    public List<String> getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(List<String> names) {
        this.names = names;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the immigrationDate
     */
    public String getImmigrationDate() {
        return immigrationDate;
    }

    /**
     * @param immigrationDate the immigrationDate to set
     */
    public void setImmigrationDate(String immigrationDate) {
        this.immigrationDate = immigrationDate;
    }

    /**
     * @return the regions
     */
    @XmlElement(name = "region")
    public List<ExportCodeNameModel> getRegions() {
        return regions;
    }

    /**
     * @param regions the regions to set
     */
    public void setRegions(List<ExportCodeNameModel> regions) {
        this.regions = regions;
    }

    @Override
    public String toString() {
        return "ExportAuthorModel{" + "names=" + names + ", birthday=" + birthday + ", immigrationDate=" + immigrationDate + ", regions=" + regions + '}';
    }
}
