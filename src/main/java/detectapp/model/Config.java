package detectapp.model;

//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private String testFolderPath;
    private String productionFolderPath;
    private List<FilePair> filePairs;
    private List<String> smellsTypes;
    private Map<String, List<String>> keyWords;
    private boolean createStats;
    private boolean createGraph;
    private String outputPath;
    private enum Type{
        json, xml
    }
    private Type type;
    private boolean detailedInfo;

    public Config(String configPath) {
        this.filePairs = new ArrayList<>();
        this.smellsTypes = new ArrayList<>();
        this.keyWords = new HashMap<>();

        JSONObject obj = null;
        if ((obj = readConfigFile(configPath)) != null)
            loadConfigFile(obj);
    }

    public JSONObject readConfigFile(String configPath) {
        try {
            File file = new File(configPath);
            InputStream is = new FileInputStream(file);
            if (is == null) {
                throw new NullPointerException("Cannot find resource file " + configPath);
            }
            JSONTokener tokener = new JSONTokener(is);
            return new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void loadConfigFile(JSONObject obj){
        this.testFolderPath = obj.getJSONObject("source").getString("test-folder");
        this.productionFolderPath = obj.getJSONObject("source").getString("production-folder");

        JSONArray jsonArray = obj.getJSONObject("detailed-source").getJSONArray("file-pairs");
        for (int i = 0; i < jsonArray.length(); i++ ){
            this.filePairs.add(new FilePair(jsonArray.getJSONObject(i).getString("test-file"),
                    jsonArray.getJSONObject(i).getString("production-file")));
        }

        jsonArray = obj.getJSONArray("smells-types");
        for (int i = 0; i < jsonArray.length(); i++){
            this.smellsTypes.add(jsonArray.get(i).toString());
        }

        jsonArray = obj.getJSONObject("app-settings").getJSONObject("key-words").getJSONArray("assertion");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.get(i).toString());
        }
        this.keyWords.put(jsonArray.toString(), list);

        jsonArray = obj.getJSONObject("app-settings").getJSONObject("key-words").getJSONArray("mystery-types");
        list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.get(i).toString());
        }
        this.keyWords.put(jsonArray.toString(), list);

        this.createStats = obj.getJSONObject("app-settings").getJSONObject("add-functions").getBoolean("create-stats");
        this.createGraph = obj.getJSONObject("app-settings").getJSONObject("add-functions").getBoolean("create-graph");

        this.outputPath = obj.getJSONObject("output").getString("path");
        this.type = obj.getJSONObject("output").getString("type").equals("json") ? Type.json : Type.xml;
    }
}
