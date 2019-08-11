package detectapp.utils;

//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;


import detectapp.model.FilePair;
import detectapp.model.Type;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private static String testFolderPath;
    private static String productionFolderPath;
    private static List<FilePair> filePairs;
    private static List<String> smellsTypes;
    private static Map<String, List<String>> keyWords;
    private static String outputPath;
    private static Type type;
    private static boolean detailedInfo;

    public static List<String> getSmellsTypes() {
        return smellsTypes;
    }

    public static void setSmellsTypes(List<String> smellsTypes) {
        Config.smellsTypes = smellsTypes;
    }

    public static void getConfig(String configPath) {
        filePairs = new ArrayList<>();
        smellsTypes = new ArrayList<>();
        keyWords = new HashMap<>();

        JSONObject obj = null;
        if ((obj = readConfigFile(configPath)) != null)
            loadConfigFile(obj);
    }

    private static JSONObject readConfigFile(String configPath) {
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

    private static void loadConfigFile(JSONObject obj){
        testFolderPath = obj.getJSONObject("source").getString("test-folder");
        productionFolderPath = obj.getJSONObject("source").getString("production-folder");

        JSONArray jsonArray = obj.getJSONObject("detailed-source").getJSONArray("file-pairs");
        for (int i = 0; i < jsonArray.length(); i++ ){
            filePairs.add(new FilePair(jsonArray.getJSONObject(i).getString("test-file"),
                    jsonArray.getJSONObject(i).getString("production-file")));
        }

        jsonArray = obj.getJSONArray("smells-types");
        for (int i = 0; i < jsonArray.length(); i++){
            smellsTypes.add(jsonArray.get(i).toString());
        }

        jsonArray = obj.getJSONObject("app-settings").getJSONObject("key-words").getJSONArray("assertion");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.get(i).toString());
        }
        keyWords.put("assertion", list);

        jsonArray = obj.getJSONObject("app-settings").getJSONObject("key-words").getJSONArray("mystery-types");
        list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            list.add(jsonArray.get(i).toString());
        }
        keyWords.put("mystery-types", list);

        outputPath = obj.getJSONObject("output").getString("path");
        type = obj.getJSONObject("output").getString("type").equals("json") ? Type.json : Type.xml;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static List<String> getAssertionTypes() {
        return keyWords.get("assertion");
    }

    public static  List<String> getMysteryTypes() {
        return keyWords.get("mystery-types");
    }

    public static String getTestFolderPath() {
        return testFolderPath;
    }

    public static String getProductionFolderPath() {
        return productionFolderPath;
    }

    public static List<FilePair> getFilePairs() {
        return filePairs;
    }
}
