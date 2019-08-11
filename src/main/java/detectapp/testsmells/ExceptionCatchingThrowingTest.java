package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ThrowStmt;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class ExceptionCatchingThrowingTest extends TestSmell {

    private String name = "Exception Catching Throwing Test";

    private boolean hasException = false;

    public ExceptionCatchingThrowingTest() {
        testCodeElements = new ArrayList<>();
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
        hasException = false;
        TestMethod testMethod = new TestMethod(method.getNameAsString());
        testMethod.setAnnotations(method.getAnnotations());
        super.visit(method, arg);

        if (method.getThrownExceptions().size() > 0)
            hasException = true;

        if (hasException)
            testMethod.setSmell(true);

        testCodeElements.add(testMethod);
    }

    @Override
    public void visit(ThrowStmt n, Void arg) {
        super.visit(n, arg);
        hasException = true;
    }

    @Override
    public void visit(CatchClause n, Void arg) {
        super.visit(n, arg);
        hasException = true;
    }

}
