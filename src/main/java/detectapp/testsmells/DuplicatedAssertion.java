package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class DuplicatedAssertion extends TestSmell{
    private String name = "Duplicated Assertion";
    private List<TestCodeElement> testCodeElements;
    private Integer assertCount = 0;

    public DuplicatedAssertion() {
        this.testCodeElements = new ArrayList<>();
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
        System.out.println(method.getNameAsString());
        assertCount = 0;
        super.visit(method, arg);

        if (assertCount > 1){
            System.out.println("Assert count: " + assertCount);
            testMethod.setSmell(true);
        }
        testCodeElements.add(testMethod);
    }

    @Override
    public void visit(MethodCallExpr method, Void arg){
        super.visit(method, arg);
        if(method.getNameAsString().toLowerCase().contains("assert")){
            //System.out.println(method.getNameAsString());
            assertCount++;
        }
    }
}
