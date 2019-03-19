package detectapp.model;

public class TestMethod implements TestCodeElement{

    private String name;
    private boolean smell;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isSmell() {
        return smell;
    }

    @Override
    public void setSmell(boolean smell) {
        this.smell = smell;
    }

    public TestMethod(String name) {
        this.name = name;
        this.smell = false;
    }
}
