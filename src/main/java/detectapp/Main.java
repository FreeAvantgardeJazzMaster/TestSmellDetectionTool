package detectapp;

import detectapp.model.Config;
import detectapp.model.TestFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        Config config = new Config("src//main//config//config");

        String folderPath = "src//main//resources//test";
        List<TestFile> testFiles = new ArrayList<>();
        TestSmellDetector testSmellDetector = new TestSmellDetector();

        File rootFolder = new File(folderPath);

        if (rootFolder.exists() && rootFolder.isDirectory()) {
            for (File file : rootFolder.listFiles()) {
                if (!file.isDirectory()){
                    TestFile testFile = new TestFile(file.getPath());
                    testFiles.add(testFile);
                }
            }
        }

        testFiles = testSmellDetector.detectSmells(testFiles);

        System.out.println("end.");
    }
}
