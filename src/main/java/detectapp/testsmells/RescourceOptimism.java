package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class RescourceOptimism extends TestSmell {

    private String name = "Resource Optimism";

    private boolean isHasSmell() {
        return hasSmell;
    }

    private void setHasSmell(boolean hasSmell) {
        this.hasSmell = hasSmell;
    }

    private boolean hasSmell = false;

    private List<String> variables = new ArrayList<>();
    private List<String> checkedVariables = new ArrayList<>();

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

        checkLists();
        if (isHasSmell())
            testMethod.setSmell(true);

        testCodeElements.add(testMethod);

        setHasSmell(false);
        variables = new ArrayList<>();
        checkedVariables = new ArrayList<>();
    }


    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        for (VariableDeclarator variableDeclarator : n.getVariables()) {
            if (variableDeclarator.getType().asString().equals("File")) {
                if (!variables.contains(variableDeclarator.getNameAsString()))
                    variables.add(variableDeclarator.getNameAsString());
            }
        }
        super.visit(n, arg);
    }

    @Override
    public void visit(VariableDeclarator n, Void arg) {
        if (n.getType().asString().equals("File")) {
            if (!variables.contains(n.getNameAsString()))
                variables.add(n.getNameAsString());
        }
        super.visit(n, arg);
    }


    @Override
    public void visit(MethodCallExpr n, Void arg) {
        super.visit(n, arg);
        if (n.getNameAsString().equals("exists") ||
                n.getNameAsString().equals("isFile") ||
                n.getNameAsString().equals("notExists")) {
            if (n.getScope().isPresent()){
                if (n.getScope().get() instanceof NameExpr) {
                    checkedVariables.add(((NameExpr) n.getScope().get()).getNameAsString());
                }
            }

        }
    }

    private void checkLists() {
        for (String checkedVariable : checkedVariables) {
            if (variables.contains(checkedVariable))
                variables.remove(checkedVariable);
        }

        if (variables.size() > 0)
            setHasSmell(true);
    }
}
