package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.stmt.*;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class Complexity extends TestSmell {
    private String name = "Complexity";

    private int statementCount = 0;

    public Complexity() {
        this.testCodeElements = new ArrayList<>();
    }

    private int getStatementCount() {
        return statementCount;
    }

    private void setStatementCount(int statementCount) {
        this.statementCount = statementCount;
    }

    private void incrementCount() {
        statementCount++;
    }

    private List<TestCodeElement> testCodeElements;

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

        if (getStatementCount() > 0) {
            testMethod.setSmell(true);
        }
        testCodeElements.add(testMethod);
        setStatementCount(0);
    }

    @Override
    public void visit(IfStmt n, Void arg) {
        super.visit(n, arg);
        incrementCount();
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {
        super.visit(n, arg);
        incrementCount();
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        super.visit(n, arg);
        incrementCount();
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        super.visit(n, arg);
        incrementCount();
    }

    @Override
    public void visit(ForeachStmt n, Void arg) {
        super.visit(n, arg);
        incrementCount();
    }

    @Override
    public void visit(ConditionalExpr n, Void arg) {
        super.visit(n, arg);
        incrementCount();
    }


}
