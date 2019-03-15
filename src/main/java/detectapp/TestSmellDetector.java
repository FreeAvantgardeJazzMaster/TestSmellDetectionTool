package detectapp;

import com.github.javaparser.ast.CompilationUnit;
import detectapp.model.TestSmell;
import detectapp.testsmells.EmptyTest;

import java.util.ArrayList;
import java.util.List;

public class TestSmellDetector {

    private List<TestSmell> testSmells;

    public TestSmellDetector() {
        initSmells();
    }

    private void initSmells(){
        testSmells = new ArrayList<>();
        testSmells.add(new EmptyTest);
    }

    public void detectSmells{
        CompilationUnit compilationUnit=null;
    }
}
