package ua.net.yason.corpus.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.GoogleUtils;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * see https://developers.google.com/sheets/api/quickstart/java
 */
public class GoogleSheetsApi {
    
    /** Application name. */
    private static final String APPLICATION_NAME =
        "ukr-corpus-sheets-utils";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/ukr-corpus-sheets-utils");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (IOException | GeneralSecurityException t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    static HttpTransport newProxyTransport() throws GeneralSecurityException, IOException {
        NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
        builder.trustCertificates(GoogleUtils.getCertificateTrustStore());
        builder.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.org", 3128)));
        return builder.build();
    }
    
    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            EntryPoint.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    public static ValueRange getRange(Sheets service, String sheetId, String range) throws IOException {
        return service.spreadsheets().values()
                .get(sheetId, range)
                .execute();
    }

    public static Map<String, String> getMap(ValueRange valueRange) {
        Map<String, String> result = new TreeMap<>();
        List<List<Object>> values = valueRange.getValues();
        for (List<Object> value : values) {
            String key = getStringValue(value, 0, true, false);
            if (key != null) {
                result.put(key, getStringValue(value, 1, true, false));
            }
        }
        return result;
    }
  
    public static List<String> getList(List<Object> value, int... indexes) {
        List<String> result = new ArrayList<>();
        for (int index : indexes) {
            String str = getStringValue(value, index, true, true);
            if (str != null) {
                result.add(str);
            }
        }
        return result;
    }
    
    public static String getStringValue(List<Object> value, int index) {
        return getStringValue(value, index, true, true);
    }
    
    public static String getStringValue(List<Object> value, int index, boolean trim, boolean ignoreEmpty) {
        String result = null;
        if (value != null && value.size() > index) {
            Object row = value.get(index);
            if (row != null) {
                String str = row.toString();
                if (trim) {
                    str = str.trim();
                }
                if (!ignoreEmpty || !str.isEmpty()){
                    result = str;
                }
            }
        }
        return result;
    }
}
