package detectapp;

import detectapp.model.*;
import detectapp.utils.OutputParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        Config config = new Config("src//main//config//config");

        String folderPath = "src//main//resources//test";
        List<TestFile> testFiles = new ArrayList<>();
        List<TestProductionFile> testProductionFiles = new ArrayList<>();
        List<TestFile> analyzedFiles = new ArrayList<>();
        TestSmellDetector testSmellDetector = new TestSmellDetector();

        File rootFolder = new File(folderPath);

        if (rootFolder.exists() && rootFolder.isDirectory()) {
            for (File file : rootFolder.listFiles()) {
                if (!file.isDirectory()){
                    testFiles.add(new TestFile(file.getPath()));
                    testProductionFiles.add(new TestProductionFile("src//main//resources//production//Car.txt"));
                }
            }
        }
        int count = 0;
        for (TestFile testFile : testFiles) {
            analyzedFiles.add(testSmellDetector.detectSmells(testFile, testProductionFiles.get(count)));
            count += 1;
        }



        System.out.println("end.");
        //OutputParser outputParser = new OutputParser(testFiles.get(0), config.getOutputPath(), Type.json);
        //outputParser.buildOutputFile();
    }
}
