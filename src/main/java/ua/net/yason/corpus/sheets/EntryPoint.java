package ua.net.yason.corpus.sheets;

import ua.net.yason.corpus.sheets.factory.CorpusModelFactory;
import ua.net.yason.corpus.meta.model.CorpusModel;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import ua.net.yason.corpus.meta.BasicStatistics;
import ua.net.yason.corpus.meta.SimpleStatistics;
import ua.net.yason.corpus.meta.model.export.ExportCorpusModel;
import ua.net.yason.corpus.meta.model.export.factory.ExportCorpusFactory;
import ua.net.yason.corpus.utils.XmlUtils;

/**
 * 
 * @author yason
 */
public class EntryPoint {

    public static void main(String[] args) throws IOException {
        String sheetId = Options.UKR_CORPUS_METADATA_SHEETS_ID;
        Sheets service = GoogleSheetsApi.getSheetsService();
        CorpusModelFactory loader = new CorpusModelFactory();
        CorpusModel corpusMeta = loader.create(service, sheetId);
        BasicStatistics basicStatistics = new BasicStatistics(corpusMeta);
        basicStatistics.printSummary(System.out);
        ExportCorpusFactory exportFactory = new ExportCorpusFactory();
        ExportCorpusModel exportModel = exportFactory.create(corpusMeta);
        XmlUtils.saveAsXml(Options.UKR_CORPUS_METADATA_OUTPUT_XML_FILENAME, ExportCorpusModel.class, exportModel);
        SimpleStatistics simpleStatistics = new SimpleStatistics(corpusMeta, exportModel, Options.UKR_CORPUS_TEXTS_ROOT_PATH);
        simpleStatistics.printSummary(System.out);
    }
}
