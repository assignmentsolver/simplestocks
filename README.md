# Simple stocks assignment

### Solution overview

- Main API defined in the `com.assignmentsolver.stocks` package.
- Simple implementation in the `com.assignmentsolver.stocks.simple` package.
- Tests are provided for the simple implementation that cover the use cases defined in the requirements. All tests can
  be run with the `mvn test` command.
- No form of UI or service interface has been developed, however it would be easy to add this:
    - It could be exposed as a service with a REST API using Spring (`@RestController` and `@RequestMapping` etc).
    - A command line shell interface could also be added using [Spring Shell](http://projects.spring.io/spring-shell/).

### Notes, constraints and assumptions

- TDD approach taken during development. Could have taken a BDD approach, but I kept the scope constrained.
- Monetary amounts were represented using `double` for simplicity. In a real-world solution, these
  should probably be at least a `BigDecimal`, or using JSR 354 (JavaMoney), Joda-Money etc.
- Trade quantities are assumed to be whole units.
- JavaDoc / doc-comments were not used, the focus was to keep the code short and concise for this demo.
- Thread safety has not been enforced for the implementation.
- The test suite is by no means complete, but it should cover most of the functionality to some extent.
