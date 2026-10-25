package annotations;

public class Sample {

    @MyAnnotation(3)
    public void greet(String name) {
        System.out.println("greet: " + name);
    }

    public void ping(int code) {
        System.out.println("ping: " + code);
    }

    @MyAnnotation(2)
    protected void log(String message) {
        System.out.println("log: " + message);
    }

    @MyAnnotation(3)
    protected void add(int left, int right) {
        System.out.println("add: " + left + " + " + right + " = " + (left + right));
    }

    protected void skipMe(double value) {
        System.out.println("skipMe: " + value);
    }

    @MyAnnotation(2)
    private void flag(boolean ok, char mark) {
        System.out.println("flag: " + ok + ", " + mark);
    }

    @MyAnnotation(4)
    private void info(Item item, String title) {
        System.out.println("info: " + title + " -> " + item);
    }

    private void hidden(long id) {
        System.out.println("hidden: " + id);
    }

    public static class Item {
        private final int id;
        private final String name;

        public Item(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Item(" + id + ", " + name + ")";
        }
    }
}
