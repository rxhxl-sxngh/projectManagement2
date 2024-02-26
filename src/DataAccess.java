import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
public class DataAccess {
    static final String DB_URL = "jdbc:mysql://localhost:3306/company";
    static final String USER = "root";
    static final String PASS = "Soco2003user#";

    public static void addJobClass(Job job) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO JobClass (JobClassID, JobClassName, HourlyRate) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, job.getJobClassID());
                preparedStatement.setString(2, job.getJobTitle());
                preparedStatement.setDouble(3, job.getHourlyWage());
                preparedStatement.executeUpdate();
                System.out.println("Job Class added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add Job Class.");
        }
    }

    public static void addEmployee(Employee employee) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO Employee (ID, Name, JobClassID) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employee.getEmployeeID());
                preparedStatement.setString(2, employee.getEmployeeName());
                preparedStatement.setInt(3, employee.getJobClassID());
                preparedStatement.executeUpdate();
                System.out.println("Employee added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add Employee.");
        }
    }

    public static Job getJobClassByID(int jobClassID) {
        Job job = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM JobClass WHERE JobClassID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, jobClassID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        job = new Job(resultSet.getInt("JobClassID"),
                                resultSet.getString("JobClassName"),
                                resultSet.getDouble("HourlyRate"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }

    public static Employee getEmployeeByID(int employeeID) {
        Employee employee = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM Employee WHERE ID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        employee = new Employee(resultSet.getInt("ID"),
                                resultSet.getString("Name"),
                                resultSet.getInt("JobClassID"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public static List<Job> getAllJobClasses() {
        List<Job> jobList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM JobClass";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int jobClassID = resultSet.getInt("JobClassID");
                    String jobTitle = resultSet.getString("JobClassName");
                    double hourlyWage = resultSet.getDouble("HourlyRate");
                    Job job = new Job(jobClassID, jobTitle, hourlyWage);
                    jobList.add(job);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM Employee";
            try (Statement statement = conn.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        Employee employee = new Employee(resultSet.getInt("ID"),
                                resultSet.getString("Name"),
                                resultSet.getInt("JobClassID"));
                        employees.add(employee);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void updateJobClass(Job job) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE JobClass SET JobClassName = ?, HourlyRate = ? WHERE JobClassID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, job.getJobTitle());
                preparedStatement.setDouble(2, job.getHourlyWage());
                preparedStatement.setInt(3, job.getJobClassID());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 1) {
                    System.out.println("Job Class updated successfully.");
                } else {
                    System.out.println("Job Class not found or failed to update.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update Job Class.");
        }
    }

    public static void updateEmployee(Employee employee) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE Employee SET Name = ?, JobClassID = ? WHERE ID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, employee.getEmployeeName());
                preparedStatement.setInt(2, employee.getJobClassID());
                preparedStatement.setInt(3, employee.getEmployeeID());
                preparedStatement.executeUpdate();
                System.out.println("Employee updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update Employee.");
        }
    }

    public static void deleteJobClass(int jobClassID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM JobClass WHERE JobClassID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, jobClassID);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 1) {
                    System.out.println("Job Class deleted successfully.");
                } else {
                    System.out.println("Job Class not found or failed to delete.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(int employeeID) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "DELETE FROM Employee WHERE ID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeID);
                preparedStatement.executeUpdate();
                System.out.println("Employee deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to delete Employee.");
        }
    }

    public static void addProject(Project project) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "INSERT INTO project (ProjectID, ProjectName, Leader, TotalChargeForProject) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, project.getProjectID());
                preparedStatement.setString(2, project.getProjectName());
                preparedStatement.setInt(3, project.getLeaderID());
                preparedStatement.setDouble(4, project.getTotalChargeForProject());
                preparedStatement.executeUpdate();
                addProjectAssignments(conn, project.getProjectID(), project.getEmployeeHoursBilled());
                System.out.println("Project added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add Project.");
        }
    }

    private static void addProjectAssignments(Connection conn, int projectID, Map<Integer, Double> employeeHoursBilled) throws SQLException {
        String sql = "INSERT INTO projectassignment (EmployeeID, ProjectID, HoursBilled, TotalChargeFromEmployee) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            for (Map.Entry<Integer, Double> entry : employeeHoursBilled.entrySet()) {
                preparedStatement.setInt(1, entry.getKey());
                preparedStatement.setInt(2, projectID);
                preparedStatement.setDouble(3, entry.getValue());
                // You may need to calculate TotalChargeFromEmployee based on HoursBilled and hourly rate.
                preparedStatement.setDouble(4, calculateTotalCharge(entry.getValue(), getHourlyRateByEmployeeID(entry.getKey())));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private static double calculateTotalCharge(double hoursBilled, double hourlyRate) {
        // Implement your calculation logic here
        return hoursBilled * hourlyRate;
    }

    public static double getHourlyRateByEmployeeID(int employeeID) throws SQLException {
        double hourlyRate = 0.0;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT j.HourlyRate FROM JobClass j JOIN Employee e ON j.JobClassID = e.JobClassID WHERE e.ID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        hourlyRate = resultSet.getDouble("HourlyRate");
                    }
                }
            }
        }
        return hourlyRate;
    }


    public static Project getProjectByID(int projectID) {
        Project project = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM project WHERE ProjectID = ?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, projectID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        project = new Project(resultSet.getInt("ProjectID"),
                                resultSet.getString("ProjectName"),
                                resultSet.getInt("Leader"),
                                resultSet.getDouble("TotalChargeForProject"));
                        project.setEmployeeHoursBilled(getProjectAssignments(conn, projectID));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public static List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT * FROM project";
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    int projectID = resultSet.getInt("ProjectID");
                    String projectName = resultSet.getString("ProjectName");
                    int leaderID = resultSet.getInt("Leader");
                    double totalChargeForProject = resultSet.getDouble("TotalChargeForProject");
                    Project project = new Project(projectID, projectName, leaderID, totalChargeForProject);
                    project.setEmployeeHoursBilled(getProjectAssignments(conn, projectID));
                    projects.add(project);
                }
            }
        }
        return projects;
    }

    private static Map<Integer, Double> getProjectAssignments(Connection conn, int projectID) throws SQLException {
        Map<Integer, Double> assignments = new HashMap<>();
        String sql = "SELECT * FROM projectassignment WHERE ProjectID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, projectID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    assignments.put(resultSet.getInt("EmployeeID"), resultSet.getDouble("HoursBilled"));
                }
            }
        }
        return assignments;
    }

    public static void addOrUpdateProjectAssignment(int projectId, int employeeId, double hoursBilled) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // Check if the project assignment already exists for the given project and employee
            String checkSql = "SELECT * FROM projectassignment WHERE ProjectID = ? AND EmployeeID = ?";
            try (PreparedStatement checkStatement = conn.prepareStatement(checkSql)) {
                checkStatement.setInt(1, projectId);
                checkStatement.setInt(2, employeeId);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // If the project assignment exists, update the hours billed
                        updateProjectAssignment(conn, projectId, employeeId, hoursBilled);
                    } else {
                        // If the project assignment doesn't exist, insert a new record
                        insertProjectAssignment(conn, projectId, employeeId, hoursBilled);
                    }
                    // Update total charge for the project after adding or updating the assignment
                    updateTotalChargeForProject(conn, projectId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to add or update project assignment.");
        }
    }

    private static void insertProjectAssignment(Connection conn, int projectId, int employeeId, double hoursBilled) throws SQLException {
        String insertSql = "INSERT INTO projectassignment (ProjectID, EmployeeID, HoursBilled, TotalChargeFromEmployee) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(insertSql)) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setDouble(3, hoursBilled);
            // Calculate the total charge based on hours billed and hourly rate
            double hourlyRate = getHourlyRateByEmployeeID(employeeId);
            double totalCharge = hoursBilled * hourlyRate;
            preparedStatement.setDouble(4, totalCharge);
            preparedStatement.executeUpdate();
            System.out.println("Project assignment added successfully.");
        }
    }

    private static void updateProjectAssignment(Connection conn, int projectId, int employeeId, double hoursBilled) throws SQLException {
        String updateSql = "UPDATE projectassignment SET HoursBilled = ?, TotalChargeFromEmployee = ? WHERE ProjectID = ? AND EmployeeID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateSql)) {
            preparedStatement.setDouble(1, hoursBilled);
            // Calculate the total charge based on hours billed and hourly rate
            double hourlyRate = getHourlyRateByEmployeeID(employeeId);
            double totalCharge = hoursBilled * hourlyRate;
            preparedStatement.setDouble(2, totalCharge);
            preparedStatement.setInt(3, projectId);
            preparedStatement.setInt(4, employeeId);
            preparedStatement.executeUpdate();
            System.out.println("Project assignment updated successfully.");
        }
    }

    private static void updateTotalChargeForProject(Connection conn, int projectId) throws SQLException {
        String updateTotalChargeSql = "UPDATE project SET TotalChargeForProject = (SELECT SUM(TotalChargeFromEmployee) FROM projectassignment WHERE ProjectID = ?) WHERE ProjectID = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(updateTotalChargeSql)) {
            preparedStatement.setInt(1, projectId);
            preparedStatement.setInt(2, projectId);
            preparedStatement.executeUpdate();
            System.out.println("Total charge for project updated successfully.");
        }
    }

    public static void updateProject(Project project) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE project SET projectName = ?, leader = ? WHERE projectID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, project.getProjectName());
                stmt.setInt(2, project.getLeaderID());
                stmt.setInt(3, project.getProjectID());
                stmt.executeUpdate();
                System.out.println("Project updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update project.");
        }
    }

}

