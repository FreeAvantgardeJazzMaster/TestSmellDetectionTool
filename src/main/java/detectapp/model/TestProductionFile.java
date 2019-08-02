package detectapp.model;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class TestProductionFile {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public TestProductionFile(String filePath) {
        this.filePath = filePath;
    }
}
