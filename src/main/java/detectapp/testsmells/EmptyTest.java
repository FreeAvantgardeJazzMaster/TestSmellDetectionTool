package detectapp.testsmells;

import com.github.javaparser.ast.body.MethodDeclaration;
import detectapp.model.TestSmell;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class EmptyTest extends TestSmell {

    @Override
    public void visit(MethodDeclaration method, Void arg){
        System.out.println(method.getNameAsString());
    }
}
