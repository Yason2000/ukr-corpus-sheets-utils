package ua.net.yason.corpus.meta.model.export;

import java.io.Serializable;

/**
 *
 * @author yason
 */
public class ExportCodeNameModel implements Serializable {

    private static final long serialVersionUID = -8629355138287614461L;
    
    private String name;
    
    private String code;

    public ExportCodeNameModel() {
    }

    public ExportCodeNameModel(String name, String code) {
        this.name = name;
        this.code = code;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
