package detectapp;

import detectapp.model.TestFile;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "src//main//resources//testfile.txt";

        TestSmellDetector testSmellDetector = new TestSmellDetector();

        TestFile testFile = new TestFile(filePath);

        testFile = testSmellDetector.detectSmells(testFile);

        System.out.println("end.");
    }
}
