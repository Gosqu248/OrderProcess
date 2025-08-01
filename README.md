# OrderProcess: Food Ordering Workflow

## Introduction
OrderProcess is a Java-based BPMN (Business Process Model and Notation) project demonstrating a complete food ordering workflow in a restaurant scenario. It uses the Camunda BPM engine with an embedded H2 database to model, execute, and persist process instances, showcasing tasks such as order validation, payment processing, meal preparation, and delivery coordination.

## Architecture & Components

- **Camunda Process Engine**: Embedded in a standalone Java application, persisting runtime data to the `camunda-h2-database.mv.db` files.
- **Process Definition**: The BPMN 2.0 file (`src/main/resources/order-process.bpmn`) defines the workflow with:
  1. **Start Event** – `Order Received`
  2. **User Task** – `Review Order` (e.g., manager approval)
  3. **Service Tasks** – delegates implemented in Java under `src/main/java/com/example/delegates`:
     - `ValidateOrderDelegate`
     - `ProcessPaymentDelegate`
     - `PrepareMealDelegate`
     - `ArrangeDeliveryDelegate`
  4. **Parallel Gateway** – splits preparation and delivery flows
  5. **End Event** – `Order Completed`
- **Java Delegates**: Concrete implementations of business logic, wired via Camunda’s Java Delegate API.
- **Build & Dependencies**: Managed with Maven (`pom.xml`), including:
  - `camunda-bom` for BPMN engine
  - `h2` database driver
  - SLF4J for logging

## Prerequisites

- **Java** 11 or higher
- **Maven** 3.6+
- (Optional) **Camunda Modeler** to visualize or edit the BPMN diagram

## Setup & Execution

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Gosqu248/OrderProcess.git
   cd OrderProcess
   ```

2. **Build the project**:
   ```bash
   mvn clean package
   ```

3. **Run the application** (deploys the BPMN and starts a sample process instance):
   ```bash
   java -jar target/OrderProcess-1.0-SNAPSHOT.jar
   ```

4. **Access the H2 Console** (optional, for inspecting process tables):
   - **JDBC URL**: `jdbc:h2:./camunda-h2-database`
   - **User**: `sa`
   - **Password**: *(leave blank)*

5. **Deploy & Test**:
   - The `Main` class automatically deploys the BPMN model and launches a test order.
   - Check the logs for process variables and task completions.

## Customization

- **Editing the Workflow**: Open `order-process.bpmn` in Camunda Modeler to add tasks, events, or subprocesses.
- **Implementing New Logic**: Create additional Java Delegates in `src/main/java` and reference them in the BPMN service tasks.
- **Database Configuration**: Modify H2 settings or switch to a different JDBC-compatible database by updating the `application.properties` or `pom.xml`.

## Project Structure

```
├── pom.xml                   # Maven configuration
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/example/delegates  # Business logic implementations
│   │   └── resources
│   │       └── order-process.bpmn     # BPMN workflow definition
├── camunda-h2-database.mv.db # Persisted H2 database file
└── README.md                 # Project documentation
```

## Contributing

Contributions, issues, and feature requests are welcome! Feel free to:
- Fork the repo
- Create a branch for your feature (`git checkout -b feature/my-change`)
- Commit your changes (`git commit -m 'Add some feature'`)
- Push to the branch (`git push origin feature/my-change`)
- Open a Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
