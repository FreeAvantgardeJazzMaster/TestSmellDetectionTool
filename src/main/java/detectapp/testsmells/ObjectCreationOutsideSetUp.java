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
    private List<TestCodeElement> testCodeElements;

    public ObjectCreationOutsideSetUp() {
        testCodeElements = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    /*
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        testClass = new TestClass(n.getNameAsString());
        super.visit(n, arg);
        testCodeElements.add(testClass);
    }
    */

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        for (AnnotationExpr annotations : method.getAnnotations()) {
            if (!annotations.getNameAsString().toLowerCase().contains("before")){
                testMethod = new TestMethod(method.getNameAsString());
                super.visit(method, arg);
                testCodeElements.add(testMethod);
            }
        }
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        super.visit(n, arg);
        testMethod.setSmell(true);
    }
}
