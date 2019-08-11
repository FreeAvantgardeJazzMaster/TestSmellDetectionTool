package detectapp.model;

import java.io.File;

public class FilePair {

    private String testFilePath;
    private String testFileName;
    private String productionFilePath;

    public FilePair(String testFileName, String productionFileName) {
        this.testFilePath = testFileName;
        this.productionFilePath = productionFileName;
        this.testFileName = extractFileName(this.testFilePath);
    }

    public String getTestFilePath() {
        return testFilePath;
    }

    public String getProductionFilePath() {
        return productionFilePath;
    }

    public String getTestFileName() {
        return testFileName;
    }

    private String extractFileName(String filePath){
        File file = new File(filePath);
        testFileName = file.getPath().substring(file.getPath().lastIndexOf("\\") + 1);
        if (testFileName.lastIndexOf(".") != -1){
            testFileName = testFileName.substring(0, testFileName.lastIndexOf("."));
        }
        return testFileName;
    }
}
