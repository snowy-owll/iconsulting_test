## How to build
Run `mvn package` to build an executable jar.
The jar will be in `target` directory.

  - 'assignment.jar' includes all dependencies.
  - 'assignment-external-dependencies.jar' does not includes dependencies. They are located in `target/lib` folder.

## How to run
Run `java -jar target/assignment.jar -f test-files.xml -s file-278959595.xml`

For get help run `java -jar target/assignment.jar -h`

## How to test
Run `mvn test`
