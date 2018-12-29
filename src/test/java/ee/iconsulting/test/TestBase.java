package ee.iconsulting.test;

import java.io.File;
import java.util.Objects;

class TestBase {
    File getResourceFile(String name) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(name)).getFile());
    }
}
