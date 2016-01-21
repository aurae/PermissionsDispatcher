package permissions.dispatcher.processor.base;

import com.google.testing.compile.JavaFileObjects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

import javax.tools.JavaFileObject;

public final class Test {

    private final JavaFileObject actualJava;
    private final JavaFileObject expectJava;

    private Test(String className, @NotNull URL actual, @Nullable URL expect) {
        // Read in the resources
        this.actualJava = JavaFileObjects.forSourceLines(className, TestSuite.readResource(actual));
        this.expectJava = expect != null ? JavaFileObjects.forSourceLines(className + "PermissionsDispatcher", TestSuite.readResource(expect)) : null;
    }

    public final JavaFileObject actual() {
        return actualJava;
    }

    public final JavaFileObject expect() {
        return expectJava;
    }

    /* Static */

    public static Test of(String className, @NotNull URL actual, @Nullable URL expect) {
        return new Test(className, actual, expect);
    }
}
