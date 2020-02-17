package com.github.bewaremypower.util;

public class ExceptionUtil {
    /**
     * #{@link #handle(Exception, String)}, with no `prefix`
     */
    public static void handle(Exception e) {
        handle(e, null);
    }

    /**
     * Handles the exception by printing the description to stdout.
     * @param e Exception to handle
     * @param prefix The prefix of description, followed by ": " and the description of `e` if not null
     */
    public static void handle(Exception e, String prefix) {
        if (e != null) {
            if (prefix == null || prefix.isEmpty()) {
                System.out.println(e.getClass() + ": " + e.getMessage());
            } else {
                System.out.println(prefix + " | " + e.getClass() + ": " + e.getMessage());
            }
        }
    }
}
