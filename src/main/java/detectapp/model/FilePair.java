package detectapp.model;

public class FilePair {

    private String testFilePath;
    private String productionFilePath;

    public FilePair(String testFileName, String productionFileName) {
        this.testFilePath = testFileName;
        this.productionFilePath = productionFileName;
    }

    public String getTestFilePath() {
        return testFilePath;
    }

    public String getProductionFilePath() {
        return productionFilePath;
    }
}
