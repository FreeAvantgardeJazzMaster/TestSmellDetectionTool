package detectapp.testsmells;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import detectapp.model.TestClass;
import detectapp.model.TestCodeElement;
import detectapp.model.TestSmell;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ObjectCreationOutsideSetUp extends TestSmell {

    private TestClass testClass;
    boolean constructorAllowed=false;
    private List<TestCodeElement> testCodeElements;

    public ObjectCreationOutsideSetUp() {
        testCodeElements = new ArrayList<>();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public List<TestCodeElement> getTestCodeElements() {
        return null;
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        testClass = new TestClass(n.getNameAsString());
        super.visit(n, arg);
        testCodeElements.add(testClass);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        for (AnnotationExpr annotations : n.getAnnotations()){
            if(!annotations.getNameAsString().toLowerCase().contains("before"))
                super.visit(n, arg);
        }
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        super.visit(n, arg);
        testClass.setSmell(true);
    }

    /*
    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        super.visit(n, arg);
        for (VariableDeclarator var : n.getVariables()) {
            if (var.getInitializer().get().toString().toLowerCase().contains("new")){
                testClass.setSmell(true);
            }
        }
    }
    */
}
