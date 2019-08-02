package detectapp;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import detectapp.model.TestFile;
import detectapp.model.TestMethod;
import detectapp.model.TestProductionFile;
import detectapp.model.TestSmell;
import detectapp.testsmells.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TestSmellDetector {

    private List<TestSmell> testSmells;

    public TestSmellDetector() { }

    private void initSmells(String productionFilePath){
        testSmells = new ArrayList<>();
        //testSmells.add(new EmptyTest());
        testSmells.add(new DuplicatedAssertion());
        //testSmells.add(new ObjectCreationOutsideSetUp());
        //testSmells.add(new ExceptionCatchingThrowingTest());
        //testSmells.add(new RescourceOptimism());
        //testSmells.add(new Complexity());
        //testSmells.add(new SleepyTest());
        //testSmells.add(new MysteryGuest());
        testSmells.add(new EagerTest(productionFilePath));
    }

    public TestFile detectSmells(TestFile testFile, TestProductionFile testProductionFile) throws FileNotFoundException{
        initSmells(testProductionFile.getFilePath());
        FileInputStream fis = new FileInputStream(testFile.getFilePath());
        JavaParser javaParser = new JavaParser();
        CompilationUnit cu = javaParser.parse(fis);

        for (TestSmell testSmell : testSmells) {
            try {
                cu.accept(testSmell, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            testFile.addTestSmell(testSmell);
        }
        return testFile;
    }
}
