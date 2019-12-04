package python.middleware;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Simple marker annotation for methods called from Python
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Pythond { /* Just used as a marker */ }
