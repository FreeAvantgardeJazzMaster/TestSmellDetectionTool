package detectapp.model;

import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public abstract class TestSmell extends VoidVisitorAdapter<Void> {

    public abstract String getName();

    public abstract List<TestCodeElement> getTestCodeElements();

}
