package ROBOT.Logger;

public class MyLogger {

    private static Level logLevel = Level.INFO;

    public static Level getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(Level logLevel) {
        MyLogger.logLevel = logLevel;
    }

    public static <T> void debug(T log) {
        log(Level.DEBUG, log);
    }

    public static <T> void info(T log) {
        log(Level.INFO, log);
    }

    public static <T> void warning(T log) {
        log(Level.WARNING, log);
    }

    public static <T> void error(T log) {
        log(Level.ERROR, log);
    }

    public static <T> void critical(T log) {
        log(Level.CRITICAL, log);
    }

    private static <T> void log(Level lvl, T log) {
        if (logLevel.required(lvl))
            System.out.println(log);
    }

    public enum Level {
        NONE(0), CRITICAL(1), ERROR(2), WARNING(3), INFO(4), DEBUG(5);
        private final int level;

        Level(int lvl) {
            level = lvl;
        }

        public int getLevel() {
            return level;
        }

        public boolean required(Level lvl) {
            return lvl.getLevel() <= getLevel();
        }
    }
}
