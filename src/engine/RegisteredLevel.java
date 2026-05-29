package engine;

import java.lang.annotation.*;

/**
 * Custom annotation to register game levels automatically and position their hub door.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisteredLevel {
    String name();
    String color() default ""; // Hex color (e.g. "#228B22") or empty for auto-generated color
    int doorX() default -1;
    int doorY() default -1;
}
