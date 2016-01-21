package permissions.dispatcher.processor.base;

import com.google.testing.compile.CompileTester;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.net.URL;
import java.util.Scanner;

import permissions.dispatcher.processor.PermissionsProcessor;

import static com.google.common.truth.Truth.ASSERT;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public abstract class TestSuite {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    protected final void expectRuntimeException(final String message) {
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage(newEqualsMatcher(message));
    }

    protected final void assertJavaSource(String className, String resourcePath) {
        // Fix separators within the resource path
        resourcePath = resourcePath.replaceAll("/", File.separator) + File.separator;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL actualUrl = cl.getResource(resourcePath + "actual.txt");
        URL expectUrl = cl.getResource(resourcePath + "expect.txt");
        if (actualUrl == null) throw new IllegalArgumentException("not found: " + resourcePath + "actual.txt");
        Test test = Test.of(className, actualUrl, expectUrl);

        // Create the first part of the assertion by checking compilation
        CompileTester.SuccessfulCompilationClause clause = ASSERT.about(javaSource())
                .that(test.actual())
                .processedWith(new PermissionsProcessor())
                .compilesWithoutError();

        // If an expect file is also present, check the source code generation as well
        if (expectUrl != null) {
            clause.and().generatesSources(test.expect());
        }
    }

    protected final void assertJavaSource(BaseTest test) {
        ASSERT
                .about(javaSource())
                .that(test.actual())
                .processedWith(new PermissionsProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(test.expect());
    }

    /* Static */

    private static Matcher<String> newEqualsMatcher(String forString) {
        return new StringEquals(forString);
    }

    static String readResource(URL url) {
        StringBuilder result = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(url.toURI()))) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return result.toString();
    }
}
