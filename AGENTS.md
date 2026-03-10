# Agent Guidelines for RuoYi Project

## Build and Test Commands

### Maven Commands
- **Build entire project**: `mvn clean package`
- **Skip tests during build**: `mvn clean package -DskipTests`
- **Run tests**: `mvn test`
- **Run single test**: `mvn test -Dtest=ClassName#methodName`
- **Run all tests in package**: `mvn test -Dtest=com.ruoyi.*`
- **Run test with specific pattern**: `mvn test -Dtest=**/*Test.java`
- **Compile**: `mvn clean compile`
- **Clean build**: `mvn clean`

### Run Application
- **Run with Maven**: `mvn spring-boot:run`
- **Package executable JAR**: `mvn clean package`
- **Run JAR**: `java -jar ruoyi-admin/target/ruoyi-admin.jar`

### Project Structure
- Base package: `com.ruoyi`
- Admin module: `ruoyi-admin` (web entry point)
- Common module: `ruoyi-common` (utilities)
- Framework module: `ruoyi-framework` (core framework)
- System module: `ruoyi-system` (system management)
- Bricks module: `ruoyi-bricks` (custom business module)
- Quartz module: `ruoyi-quartz` (scheduled tasks)
- Generator module: `ruoyi-generator` (code generation)

## Code Style Guidelines

### Package Structure
- Follow `com.ruoyi.{module}.xxx` convention
- Controller packages: `com.ruoyi.web.controller.{controllerType}`
- Service packages: `com.ruoyi.{module}.service.{serviceName}`
- Mapper packages: `com.ruoyi.{module}.mapper.{mapperName}`
- Domain packages: `com.ruoyi.{module}.domain.{entityName}`
- Utils packages: `com.ruoyi.common.utils.{utilityName}`

### Class Naming
- Classes: PascalCase (e.g., `CommonController`, `AjaxResult`)
- Methods: camelCase (e.g., `fileDownload`, `uploadFile`)
- Constants: UPPER_SNAKE_CASE (e.g., `FILE_DELIMITER`)
- Boolean fields: `is` prefix (e.g., `isDelete`, `isEnabled`)

### Imports
- Group imports by source: standard, third-party, internal
- Static imports: limited to frequently used constants
- Avoid unused imports
- No wildcard imports (use explicit imports)

### Formatting
- Indentation: 4 spaces (no tabs)
- Line length: max 120 characters
- Blank lines: 1-2 between methods, 1 between classes
- Opening braces: on same line as class/method signatures
- Closing braces: on new line

### Annotation Usage
- Controllers: `@RestController` + `@RequestMapping`
- REST endpoints: Use `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- Validation: Use `@Valid`, `@Validated`
- API documentation: Use Swagger annotations
- Security: Use `@PreAuthorize`, `@Anonymous` for public endpoints

### Error Handling
- Wrap business logic in try-catch blocks
- Use `Logger.error()` for caught exceptions with context
- Return `AjaxResult.error(message)` on exceptions
- Throw `ServiceException` for business validation errors
- Use `try-with-resources` for IO operations
- Never swallow exceptions without logging

### Logger Usage
```java
// Standard Slf4j pattern
private static final Logger log = LoggerFactory.getLogger(ClassName.class);

// Logging best practices
log.debug("Debug message with parameter {}", param);
log.info("Info message");
log.warn("Warning message: {}", warning);
log.error("Error occurred", exception); // Include exception for debugging
```

### Response Format
- Always return `AjaxResult` objects
- Success response: `AjaxResult.success(data)`
- Error response: `AjaxResult.error(message)`
- Include HTTP status codes where appropriate

### Database Access
- Use MyBatis with XML mappers: `com.ruoyi.{module}.mapper`
- Query methods: `mapper.selectByCondition()`
- Use PageHelper for pagination: `PageHelper.startPage()`
- Never use raw SQL without logging

### Configuration Files
- Main config: `application.yml`
- Profile-specific: `application-{profile}.yml`
- Mapper XML: `src/main/resources/mapper/**/*Mapper.xml`
- Load profiles: `spring.profiles.active=dev`

### Testing
- Test classes: `{ClassName}Test.java` in `src/test/java`
- Use JUnit 5 for testing
- Mock dependencies with MockMVC or Mock
- Test both success and error cases
- Mock static resources in controllers

### Comments and Documentation
- Class-level Javadoc: @author, @description
- Method-level Javadoc: @param, @return, @throws
- Keep comments concise and relevant
- Use Chinese for business logic comments (project convention)
- Use English for technical documentation

### Security Practices
- Always validate user input
- Use prepared statements for queries
- Implement proper authorization with `@PreAuthorize`
- Sanitize file uploads
- Use HTTPS in production
- Validate JWT tokens on protected endpoints

### Performance Considerations
- Use lazy loading for relationships
- Add proper indexing on database columns
- Limit query result sets
- Cache frequently accessed data with Redis
- Use connection pooling (Druid)
- Monitor slow queries

### Code Review Checklist
- All imports are used and properly organized
- No hard-coded credentials or secrets
- Error handling is comprehensive
- Logging is appropriate for each level
- Method names clearly describe purpose
- Class naming follows conventions
- No magic numbers or strings
- Code is readable and maintainable
- Tests are written for new functionality
- Documentation is up to date

### Maven Dependency Management
- Use dependencyManagement in parent POM
- Specify version properties
- Avoid transitive dependencies
- Use latest stable versions from Spring Boot
- Check for security vulnerabilities regularly
