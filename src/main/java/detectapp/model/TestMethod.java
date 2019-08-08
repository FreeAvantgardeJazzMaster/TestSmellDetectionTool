package detectapp.model;

import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.List;

public class TestMethod implements TestCodeElement{

    private String name;
    private boolean smell;
    private List<AnnotationExpr> annotations;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSmell() {
        return smell;
    }

    @Override
    public void setSmell(boolean smell) {
        this.smell = smell;
    }

    public TestMethod(String name) {
        this.name = name;
        this.smell = false;
    }

    public List<AnnotationExpr> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationExpr> annotations) {
        this.annotations = annotations;
    }
}
