package detectapp.testsmells;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;
import detectapp.utils.Similarity;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EagerTest extends TestSmell {

    private final String name = "Eager Test";
    private final Double TRESHOLD_PROB = 0.5;
    private List<TestCodeElement> testCodeElements;
    private String productionFilePath;
    private LinkedList<String> methodPairs;
    private List<Double> similarities;
    private Similarity similarity;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    public EagerTest(String productionFilePath) {
        this.productionFilePath = productionFilePath;
        this.methodPairs = new LinkedList<>();
        this.testCodeElements = new ArrayList<>();
        this.similarities = new ArrayList<>();
        this.similarity = new Similarity();

    }

    private String getProductionMethodBody(MethodCallExpr methodCall) {
        ProductionFileVisitor productionFileVisitor = new ProductionFileVisitor(methodCall);
        try {
            FileInputStream fis = new FileInputStream(productionFilePath);
            CompilationUnit cu = JavaParser.parse(fis);
            cu.accept(productionFileVisitor, null);

            return productionFileVisitor.getMethodBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private Double calculateSimilarity() {
        return similarity.get(methodPairs.get(0), methodPairs.get(1));
    }

    private Double calculateProbability() {
        if (similarities.size() > 0)
            return 1 - (similarities.stream().reduce(0.0, Double::sum) / similarities.size());
        else
            return 0.0;
    }

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        TestMethod testMethod = new TestMethod(method.getNameAsString());
        testMethod.setAnnotations(method.getAnnotations());
        super.visit(method, arg);

        testMethod.setSmell(calculateProbability() > TRESHOLD_PROB);
        testCodeElements.add(testMethod);
    }

    @Override
    public void visit(MethodCallExpr method, Void arg) {
        String methodBody;
        if (methodPairs.size() < 2) {
            if ((methodBody = getProductionMethodBody(method)) != "")
                methodPairs.add(methodBody);
        } else {
            methodPairs.poll();
            if ((methodBody = getProductionMethodBody(method)) != "")
                methodPairs.add(methodBody);
        }

        if (methodPairs.size() == 2)
            similarities.add(calculateSimilarity());
    }


    private class ProductionFileVisitor extends VoidVisitorAdapter<Void> {
        private MethodCallExpr methodCall;
        private String methodBody = "";

        private String getMethodBody() {
            return methodBody;
        }


        private ProductionFileVisitor(MethodCallExpr methodCall) {
            this.methodCall = methodCall;
        }

        @Override
        public void visit(MethodDeclaration method, Void arg) {
            if (method.getName().toString().equals(methodCall.getName().toString())) {
                if (methodCall.getArguments().size() == method.getParameters().size()) {
                    methodBody = method.getBody().toString();
                }
            }
        }
    }
}
