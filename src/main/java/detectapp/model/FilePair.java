package detectapp.model;

public class FilePair {

    private String testFileName;
    private String productionFileName;

    public FilePair(String testFileName, String productionFileName) {
        this.testFileName = testFileName;
        this.productionFileName = productionFileName;
    }
}
