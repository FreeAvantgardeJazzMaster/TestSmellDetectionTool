package detectapp.utils;

import com.github.javaparser.ast.expr.AnnotationExpr;
import detectapp.model.TestCodeElement;
import detectapp.model.TestFile;
import detectapp.model.TestSmell;
import detectapp.model.Type;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

import static java.util.stream.Collectors.toList;

public class OutputParser {
    private String outputPath;
    private Type format;
    private List<TestFile> testFiles;

    public OutputParser(List<TestFile> testfiles, String outputPath, Type format) {
        this.outputPath = outputPath;
        this.format = format;
        this.testFiles = testfiles;
    }

    public void buildOutputFile(){
        JSONObject jsonObject = parseTestFiles2(testFiles);
        try {
            File file = new File(outputPath + "//result.json");
            FileUtils.writeStringToFile(file, jsonObject.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject parseTestFiles(List<TestFile> testFiles) {
        JSONObject testFilesJsonObject = new JSONObject();
        for (TestFile testFile : testFiles) {
            testFile = filterTestFile(testFile);

            JSONObject testSmellJsonObject = new JSONObject();
            for (TestSmell testSmell : testFile.getTestSmells()) {
                JSONArray ja = new JSONArray();
                for (TestCodeElement testCodeElement : testSmell.getTestCodeElements()) {
                    ja.put(testCodeElement.getName());
                }
                testSmellJsonObject.put(testSmell.getName(), ja);
            }

            testFilesJsonObject.put(testFile.getFileName(), testSmellJsonObject);
        }

        return testFilesJsonObject;
    }

    public JSONObject parseTestFiles2(List<TestFile> testFiles) {
        JSONObject testFilesJsonObject = new JSONObject();
        for (TestFile testFile : testFiles) {
            testFile = filterTestFile(testFile);
            JSONObject testCodeElementsJsonObject = new JSONObject();

            Map<String, List<TestSmell>> testCodeElementListMap = new HashMap<>();
            Map<String, List<AnnotationExpr>> annotations = new HashMap<>();

            for (TestSmell testSmell : testFile.getTestSmells()){
                for (TestCodeElement testCodeElement : testSmell.getTestCodeElements()) {
                    if (testCodeElementListMap.containsKey(testCodeElement.getName())) {
                        testCodeElementListMap.get(testCodeElement.getName()).add(testSmell);
                    }
                    else {
                        List<TestSmell> testSmells = new ArrayList<>();
                        testCodeElementListMap.put(testCodeElement.getName(), new ArrayList<TestSmell>(Arrays.asList(testSmell)));
                    }

                    if (!annotations.containsKey(testCodeElement.getName())){
                        if (testCodeElement.getAnnotations() != null)
                            annotations.put(testCodeElement.getName(), testCodeElement.getAnnotations());
                        else
                            annotations.put(testCodeElement.getName(), new ArrayList<>());
                    }

                }
            }

            for (Map.Entry<String, List<TestSmell>> entry : testCodeElementListMap.entrySet()){
                JSONObject testCodeElementJO = new JSONObject();
                JSONArray ja = new JSONArray();
                for (TestSmell testSmell: entry.getValue())
                    ja.put(testSmell.getName());
                testCodeElementJO.put("test-smells", ja);

                ja = new JSONArray();
                for (AnnotationExpr annotationExpr : annotations.get(entry.getKey()))
                    ja.put(annotationExpr.getName());
                testCodeElementJO.put("annotations", ja);

                testCodeElementsJsonObject.put(entry.getKey(), testCodeElementJO);
            }
            testFilesJsonObject.put(testFile.getFileName(), testCodeElementsJsonObject);
        }
        return testFilesJsonObject;
    }

    public TestFile filterTestFile(TestFile testFile){
        for (TestSmell testSmell : testFile.getTestSmells()){
            testSmell.getTestCodeElements().removeIf(e -> !e.isSmell());
        }
        return testFile;
    }
}