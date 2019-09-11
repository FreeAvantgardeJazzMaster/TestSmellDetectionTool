package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;
import detectapp.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class MissingAssertion extends TestSmell {
    private String name = "Missing Assertion";
    private List<TestCodeElement> testCodeElements;
    private Integer assertCount = 0;
    private List<String> assertionsTypes;

    public MissingAssertion() {
        this.testCodeElements = new ArrayList<>();
        this.assertionsTypes = Config.getAssertionTypes();
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        TestMethod testMethod = new TestMethod(method.getNameAsString());
        testMethod.setAnnotations(method.getAnnotations());
        testMethod.setStatementsCount(method.getBody().isPresent() ? method.getBody().get().getStatements().size() : 0);
        testMethod.setLoc(calcLoc(method));
        assertCount = 0;
        super.visit(method, arg);

        if (assertCount == 0 && method.getBody().get().getStatements().size() != 0) {
            testMethod.setSmell(true);
        }
        testCodeElements.add(testMethod);
    }

    @Override
    public void visit(MethodCallExpr method, Void arg) {
        super.visit(method, arg);
        for (String assertionString : assertionsTypes)
            if (method.getNameAsString().toLowerCase().contains(assertionString.toLowerCase())) {
                assertCount++;
                break;
            }
    }
}
