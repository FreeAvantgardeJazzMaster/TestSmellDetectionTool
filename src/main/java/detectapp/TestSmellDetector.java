package detectapp;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import detectapp.model.TestFile;
import detectapp.model.TestSmell;
import detectapp.testsmells.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TestSmellDetector {

    private List<TestSmell> testSmells;

    public TestSmellDetector() {
        initSmells();
    }

    private void initSmells(){
        testSmells = new ArrayList<>();
        testSmells.add(new EmptyTest());
        testSmells.add(new DuplicatedAssertion());
        testSmells.add(new ObjectCreationOutsideSetUp());
        testSmells.add(new ExceptionCatchingThrowingTest());
        testSmells.add(new RescourceOptimism());
        testSmells.add(new Complexity());
    }

    public TestFile detectSmells(TestFile testFile) throws FileNotFoundException{
        FileInputStream fis = new FileInputStream(testFile.getFilePath());
        CompilationUnit cu = JavaParser.parse(fis);

        for(TestSmell testSmell : testSmells){
            try{
                cu.accept(testSmell, null);
            }catch(Exception e){
                e.printStackTrace();
            }
         testFile.addTestSmell(testSmell);
        }
        return testFile;
    }
}
