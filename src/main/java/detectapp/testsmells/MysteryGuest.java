package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import detectapp.utils.Config;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.util.ArrayList;
import java.util.List;

public class MysteryGuest extends TestSmell {

    private String name = "Mystery Guest";
    private List<TestCodeElement> testCodeElements;
    private List<String> mysteryTypes;
    private boolean hasSmell = false;

    public MysteryGuest() {
        testCodeElements = new ArrayList<>();
        mysteryTypes = Config.getMysteryTypes();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    private boolean isHasSmell() {
        return hasSmell;
    }

    private void setHasSmell(boolean hasSmell) {
        this.hasSmell = hasSmell;
    }

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        TestMethod testMethod = new TestMethod(method.getNameAsString());
        testMethod.setAnnotations(method.getAnnotations());
        super.visit(method, arg);

        if (isHasSmell()) {
            testMethod.setSmell(true);
        }

        testCodeElements.add(testMethod);
        setHasSmell(false);

    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        super.visit(n, arg);
        boolean omit = false;
        if (n.getAnnotations().isNonEmpty()) {
            for (AnnotationExpr annotationExpr : n.getAnnotations()) {
                if (annotationExpr.getNameAsString().equals("Mock")) {
                    omit = true;
                }
            }
        }
        if (!omit) {
            for (String mysteryType : mysteryTypes) {
                if (n.getVariable(0).getType().asString().equals(mysteryType)) {
                    setHasSmell(true);
                }
            }
        }
    }
}

