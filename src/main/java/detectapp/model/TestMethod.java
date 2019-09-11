package detectapp.model;

import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.List;

public class TestMethod implements TestCodeElement {

    private String name;
    private boolean smell;
    private List<AnnotationExpr> annotations;
    private int statementsCount;
    private int loc;

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

    @Override
    public List<AnnotationExpr> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationExpr> annotations) {
        this.annotations = annotations;
    }

    public int getStatementsCount() {
        return statementsCount;
    }

    public void setStatementsCount(int statementsCount) {
        this.statementsCount = statementsCount;
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }
}
