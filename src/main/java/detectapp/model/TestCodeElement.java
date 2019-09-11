package detectapp.model;

import com.github.javaparser.ast.expr.AnnotationExpr;

import java.util.List;

public interface TestCodeElement {

    String getName();

    boolean isSmell();

    void setSmell(boolean smell);

    List<AnnotationExpr> getAnnotations();

    int getStatementsCount();

    int getLoc();
}

