package detectapp.testsmells;

import com.github.javaparser.ast.expr.MethodCallExpr;
import detectapp.model.TestCodeElement;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class ExceptionCatchingThrowingTest extends TestSmell {

    private String name = "Exception Catching Throwing Test";

    public ExceptionCatchingThrowingTest() {
        testCodeElements = new ArrayList<>();
    }

    private List<TestCodeElement> testCodeElements;
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    @Override
    public void visit(MethodCallExpr method, Void arg){

    }
}
