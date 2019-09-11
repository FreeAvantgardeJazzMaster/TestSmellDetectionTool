package detectapp.testsmells;


import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;

import detectapp.model.TestClass;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class ObjectCreationOutsideSetUp extends TestSmell {

    private String name = "Object Creation Outside SetUp";
    private TestMethod testMethod;
    private boolean smell = false;
    private List<TestCodeElement> testCodeElements;

    public ObjectCreationOutsideSetUp() {
        testCodeElements = new ArrayList<>();
    }

    public boolean isSmell() {
        return smell;
    }

    public void setSmell(boolean smell) {
        this.smell = smell;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        for (AnnotationExpr annotations : method.getAnnotations()) {
            if (!annotations.getNameAsString().toLowerCase().contains("before")){
                TestMethod testMethod = new TestMethod(method.getNameAsString());
                testMethod.setAnnotations(method.getAnnotations());
                testMethod.setStatementsCount(method.getBody().isPresent() ? method.getBody().get().getStatements().size() : 0);
                testMethod.setLoc(calcLoc(method));
                setSmell(false);
                super.visit(method, arg);
                if (isSmell())
                    testMethod.setSmell(true);
                testCodeElements.add(testMethod);
            }
        }
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        super.visit(n, arg);
        setSmell(true);
    }
}
