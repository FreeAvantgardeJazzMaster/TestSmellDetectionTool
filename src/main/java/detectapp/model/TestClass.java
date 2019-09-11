package detectapp.model;

import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.List;

public class TestClass implements TestCodeElement {

    private String name;
    private boolean smell;

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

    @Override
    public List<AnnotationExpr> getAnnotations() {
        return null;
    }

    @Override
    public int getStatementsCount() {
        return 0;
    }

    @Override
    public int getLoc() {return 0;}

    public TestClass(String name) {
        this.name = name;
        this.smell = false;
    }
}
