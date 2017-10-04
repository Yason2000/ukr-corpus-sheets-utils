package ua.net.yason.corpus.sheets;

import ua.net.yason.corpus.sheets.factory.CorpusModelFactory;
import ua.net.yason.corpus.meta.model.CorpusModel;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import ua.net.yason.corpus.meta.BasicStatistics;
import ua.net.yason.corpus.meta.model.export.ExportCorpusModel;
import ua.net.yason.corpus.meta.model.export.factory.ExportCorpusFactory;
import ua.net.yason.corpus.utils.XmlUtils;

/**
 * 
 * @author yason
 */
public class EntryPoint {

    public static void main(String[] args) throws IOException {
        String sheetId = "1cAHbrF6-aQrZiz0K8N0wEzRV_FJxO2fBXa8mAD0luW8";
        Sheets service = GoogleSheetsApi.getSheetsService();
        CorpusModelFactory loader = new CorpusModelFactory();
        CorpusModel corpusMeta = loader.create(service, sheetId);
        BasicStatistics basicStatistics = new BasicStatistics(corpusMeta);
        basicStatistics.printSummary(System.out);
        ExportCorpusFactory exportFactory = new ExportCorpusFactory();
        ExportCorpusModel exportModel = exportFactory.create(corpusMeta);
        XmlUtils.saveAsXml("ukrCorpusMeta.xml", ExportCorpusModel.class, exportModel);
    }
}
