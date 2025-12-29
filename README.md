# FinTrack - Finance Tracker Application

This provides a clear guide on how to set up and run the FinTrack application on a new machine.

## Prerequisites

Before running this project, ensure the following software is installed on your computer:

1.  **Java Development Kit (JDK) 21**
    *   Download from: [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/#java21) or [OpenJDK 21](https://jdk.java.net/21/).
    *   **Important**: After installation, verify that the `JAVA_HOME` environment variable is set correctly and points to your JDK 21 installation.
    *   Verify by running `java -version` in your terminal. It should output version 21.

2.  **Apache Maven**
    *   Download from: [Maven Downloads](https://maven.apache.org/download.cgi).
    *   Follow the specific installation instructions for your OS.
    *   Verify by running `mvn -version`.

3.  **Internet Connection**
    *   Required to download Maven dependencies and to connect to the cloud MongoDB database. (Put your own MongoDB URI in FinTrack\target\classes\config.properties and FinTrack\src\main\resources\config.properties)

---

## Installation & Setup

1.  **Unzip the Project**
    *   Extract the `FinTrack.zip` file to a location of your choice.

2.  **Navigate to Project Directory**
    *   Open your terminal (Command Prompt, PowerShell, or Terminal).
    *   Change directory (`cd`) into the project folder. Ensure you are in the folder containing `pom.xml`.
    *   Example: `cd path/to/FinTrack`

3.  **Build the Project**
    *   Run the following command to download all dependencies and build the application:
        ```bash
        mvn clean install
        ```
    *   *Note: This may take a minute or two the first time as it downloads libraries.*

## Running the Application

To start the application, run:

```bash
mvn javafx:run
```

The application window should appear.

## Configuration (Database)

This project is configured to connect to a **MongoDB Atlas (Cloud)** database.

*   **Config File**: Located at `src/main/resources/config.properties`.
*   **Current State**: It is pre-configured with a connection URI. **Do not execute a local MongoDB instance** unless you change this setting to point to `localhost`.
*   **Troubleshooting**: If the app fails to connect, ensure you have an active internet connection and that your network does not block MongoDB connections (port 27017).

## Troubleshooting

*   **"mvn is not recognized..."**: Ensure Maven is added to your system's PATH.
*   **"release version 21 not supported"**: You are likely running an older version of Java. Run `java -version` to check. You strictly need JDK 21.
*   **JavaFX errors**: Try running `mvn clean javafx:run` to force a clean run.
