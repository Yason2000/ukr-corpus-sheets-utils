package ua.net.yason.corpus.meta.model.export;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yason
 */
@XmlRootElement(name = "corpus")
public class ExportCorpusModel implements Serializable {

    private static final long serialVersionUID = -5370556685359849713L;
    
    private List<ExportTextModel> texts;

    public ExportCorpusModel() {
    }

    public void setTexts(List<ExportTextModel> texts) {
        this.texts = texts;
    }

    @XmlElement(name = "text")    
    public List<ExportTextModel> getTexts() {
        return texts;
    }   
}
