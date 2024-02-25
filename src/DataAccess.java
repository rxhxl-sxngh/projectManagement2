import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
}

