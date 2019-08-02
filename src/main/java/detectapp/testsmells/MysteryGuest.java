package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import detectapp.model.TestCodeElement;
import detectapp.model.TestMethod;
import detectapp.model.TestSmell;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MysteryGuest extends TestSmell {

    private String name = "Mystery Guest";
    private List<TestCodeElement> testCodeElements;

    public MysteryGuest() {
        testCodeElements = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return testCodeElements;
    }

    private List<String> mysteryTypes = new ArrayList<>(Arrays.asList(
            "File",
            "FileInputStream",
            "SQLiteOpenHelper",
            "SQLiteDatabase",
            "Cursor",
            "Context",
            "HttpClient",
            "HttpResponse",
            "HttpPost",
            "HttpGet",
            "SoapObject"
    ));

    private boolean hasSmell = false;

    public boolean isHasSmell() {
        return hasSmell;
    }

    public void setHasSmell(boolean hasSmell) {
        this.hasSmell = hasSmell;
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        TestMethod testMethod = new TestMethod(n.getNameAsString());
        super.visit(n, arg);

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
        if (n.getAnnotations().isNonEmpty()){
            for (AnnotationExpr annotationExpr : n.getAnnotations()){
                if (annotationExpr.getNameAsString().equals("Mock")){
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

