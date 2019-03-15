package detectapp.model;

import java.util.ArrayList;
import java.util.List;

public class TestFile {
    String fileName;
    String filePath;
    List<TestSmell> testSmells;

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<TestSmell> getTestSmells() {
        return testSmells;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setTestSmells(List<TestSmell> testSmells) {
        this.testSmells = testSmells;
    }

    public TestFile(String filePath) {
        this.filePath = filePath;
        this.fileName = extractFileName();
        this.testSmells = new ArrayList<>();
    }

    private String extractFileName(){
        fileName = filePath.substring(filePath.lastIndexOf("//"));
        return fileName;
    }
}
