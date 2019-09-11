package detectapp.model;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public abstract class TestSmell extends VoidVisitorAdapter<Void> {

    public abstract String getName();

    public abstract List<TestCodeElement> getTestCodeElements();

    public int calcLoc(MethodDeclaration method) {
        if (method.getBody().isPresent()) {
            if (method.getBody().get().getBegin().isPresent() && method.getBody().get().getEnd().isPresent())
                return method.getBody().get().getEnd().get().line - method.getBody().get().getBegin().get().line;
        }
        return 0;
    }
}
