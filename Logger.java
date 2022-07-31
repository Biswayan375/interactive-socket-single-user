public class Logger {
    private String source = "Logger";

    public Logger(String source) { this.source = source; }

    public void log(Exception e) {
        System.out.println("\n[ " + this.source + " ]: An exception has occurred\n" + e + "\n");
    }
    public void log(String message) {
        System.out.println("[" + this.source + "]: " + message);
    }
}
