package ua.net.yason.corpus.meta;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import ua.net.yason.corpus.meta.model.CorpusModel;
import ua.net.yason.corpus.meta.model.export.ExportAuthorModel;
import ua.net.yason.corpus.meta.model.export.ExportCodeNameModel;
import ua.net.yason.corpus.meta.model.export.ExportCorpusModel;
import ua.net.yason.corpus.meta.model.export.ExportTextModel;
import ua.net.yason.corpus.sheets.EntryPointRename;

/**
 *
 * @author yason
 */
public class SimpleStatistics {

    private final ExportCorpusModel corpus;

    private final CorpusModel corpusMeta;
    
    private long totalBytes;

    private final Map<String, RegionStatistics> regionsStat = new HashMap<>();

    class RegionStatistics {

        private final String name;
        private final AtomicInteger textCount = new AtomicInteger();
        
        private long bytes;

        public RegionStatistics(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getTextCount() {
            return textCount.get();
        }

        public long getBytes() {
            return bytes;
        }
        
        public void addText(long lengh) {
            textCount.incrementAndGet();
            bytes += lengh;
        }
    }

    public SimpleStatistics(CorpusModel corpusMeta, ExportCorpusModel corpus) {
        this.corpus = corpus;
        this.corpusMeta = corpusMeta;
    }

    private void init() {
        this.corpusMeta.getRegions().forEach((code, name) -> {
            regionsStat.put(name, new RegionStatistics(code));
        });
    }

    public PrintStream printSummary(PrintStream cout) {
        init();


        corpus.getTexts().forEach((ExportTextModel text) -> {
            List<ExportAuthorModel> authors = text.getAuthors();
            List<ExportAuthorModel> translators = text.getTranslators();
            if (translators != null && !translators.isEmpty()) {
                authors = translators;
            }
            long textSize = textSize(text.getPath());
            totalBytes += textSize;
            authors.forEach((ExportAuthorModel author) -> {
                List<ExportCodeNameModel> regions = author.getRegions();
                if (regions != null && !regions.isEmpty()) {
                    //regions.forEach((region) -> {
                        String code = regions.get(0).getCode();
                        RegionStatistics stat = regionsStat.get(code);
                        stat.addText(textSize);
                    //});
                }
            });
        });
        
        
        regionsStat.forEach((code, regionStat) -> {
            cout.println(code + ";" + regionStat.getName() + ";" + regionStat.getBytes());
        });
        
        cout.println("totalBytes: "+totalBytes);
        return cout;
    }
    
    public long textSize(String path) {
        String fileName = EntryPointRename.srcRootPath + "\\"+ path;
        File file = new File(fileName);
        if (file.exists()){
                    return file.length();
        }
        else{
                    System.out.println("ERROR " + fileName);
        }
        return 0;
    }
}
