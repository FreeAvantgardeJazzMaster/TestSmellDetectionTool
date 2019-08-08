package detectapp.testsmells;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class RescourceOptimism extends TestSmell {

    private String name = "Resource Optimism";

    public boolean isHasSmell() {
        return hasSmell;
    }

    public void setHasSmell(boolean hasSmell) {
        this.hasSmell = hasSmell;
    }

    private boolean hasSmell = false;

    private List<TestCodeElement> testCodeElements;

    public RescourceOptimism() {
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

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        TestMethod testMethod = new TestMethod(method.getNameAsString());
        testMethod.setAnnotations(method.getAnnotations());
        super.visit(method, arg);

        if (isHasSmell())
            testMethod.setSmell(isHasSmell());

        testCodeElements.add(testMethod);
        setHasSmell(false);
    }


    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            if (variableDeclarator.getType().asString().equals("File")) {
                setHasSmell(true);
            }
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarator n, Void arg) {
        if (n.getType().asString().equals("File")) {
            setHasSmell(true);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            if (variableDeclarator.getType().asString().equals("File")) {
                setHasSmell(true);
            }
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        if (n.getType().asString().equals("File")) {
            setHasSmell(true);
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        super.visit(n, arg);
        if (n.getNameAsString().equals("exists") ||
                n.getNameAsString().equals("isFile") ||
                n.getNameAsString().equals("notExists")) {
            setHasSmell(true);
        }
    }
}
