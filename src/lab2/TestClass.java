package lab2;

public class TestClass {

    public String publicNotAnnotated(String str) {
        return "[publicNotAnnotated] str = " + str;
    }

    @RepeatCount(5)
    public String publicAnnotated(int val) {
        return "[publicAnnotated] val = " + val;
    }

    @RepeatCount(3)
    protected String protectedAnnotated(String str, int val) {
        return "[protectedAnnotated] str = " + str + ", val = " + val;
    }

    protected String protectedNotAnnotated(double val) {
        return "[protectedNotAnnotated] val = " + val;
    }

    @RepeatCount(4)
    private String privateAnnotated(boolean bool) {
        return "[privateAnnotated] bool = " + bool;
    }

    private String privateNotAnnotated(String str) {
        return "[privateNotAnnotated] str = " + str;
    }
}
