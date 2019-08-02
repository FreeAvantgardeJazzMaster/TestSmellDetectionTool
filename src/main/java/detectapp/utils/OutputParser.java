package detectapp.utils;

import detectapp.model.TestCodeElement;
import detectapp.model.TestFile;
import detectapp.model.TestSmell;
import detectapp.model.Type;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import org.apache.commons.io.FileUtils;

public class OutputParser {
    private String outputPath;
    private Type format;
    private TestFile testFile;

    public OutputParser(TestFile testfile, String outputPath, Type format) {
        this.outputPath = outputPath;
        this.format = format;
        this.testFile = testfile;
    }

    public void buildOutputFile(){
        JSONObject jsonObject = parseTestFile(testFile);
        try {
            File file = new File(outputPath + "//result.json");
            FileUtils.writeStringToFile(file, jsonObject.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject parseTestFile(TestFile testFile) {
        //testFile = filterTestFile(testFile);
        JSONObject testSmellJsonObject = new JSONObject();
        for (TestSmell testSmell: testFile.getTestSmells()) {
            JSONArray ja = new JSONArray();
            for (TestCodeElement testCodeElement : testSmell.getTestCodeElements()){
                ja.put(testCodeElement.getName());
            }
            testSmellJsonObject.put(testSmell.getName(), ja);
        }
        JSONObject testFileJsonObject = new JSONObject();
        testFileJsonObject.put(testFile.getFileName(), testSmellJsonObject);

        return testFileJsonObject;
    }

    public TestFile filterTestFile(TestFile testFile){
        for (TestSmell testSmell : testFile.getTestSmells()){

        }
        return testFile;
    }
}


