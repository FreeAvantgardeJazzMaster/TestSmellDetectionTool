package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import detectapp.model.Config;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class DuplicatedAssertion extends TestSmell{
    private String name = "Duplicated Assertion";
    private List<TestCodeElement> testCodeElements;
    private Integer assertCount = 0;
    private List<String> assertionsTypes;

    public DuplicatedAssertion() {
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
    public void visit(MethodDeclaration method, Void arg){
        TestMethod testMethod = new TestMethod(method.getNameAsString());
        testMethod.setAnnotations(method.getAnnotations());
        assertCount = 0;
        super.visit(method, arg);

        if (assertCount > 1){
            testMethod.setSmell(true);
        }
        testCodeElements.add(testMethod);
    }

    @Override
    public void visit(MethodCallExpr method, Void arg){
        super.visit(method, arg);
        for (String assertionString : assertionsTypes)
            if(method.getNameAsString().toLowerCase().contains(assertionString.toLowerCase()))
                assertCount++;
    }
}
