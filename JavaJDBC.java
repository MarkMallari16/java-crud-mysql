package javajdbc;

import java.sql.*;
import java.util.Scanner;

public class JavaJDBC {

    public static void displayChoice() {
        System.out.println("=========== Basic Employee Management System ===========");
        System.out.println("1. Display All Employees");
        System.out.println("2. Insert New Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Exit");
        System.out.print("Select Choice: ");
    }

    public static void main(String[] args) {
        String jdbURL = "jdbc:mysql://localhost:3306/db_employee_system";
        String username = "root";
        String pass = "";

        Scanner scan = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(jdbURL, username, pass)) {
            while (true) {
                displayChoice();
                int choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        try (Statement stmt = conn.createStatement()) {
                            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
                            System.out.println("\nList of Employees");
                            while (rs.next()) {
                                System.out.println("Employee ID: " + rs.getInt("id"));
                                System.out.println("Employee First Name: " + rs.getString("first_name"));
                                System.out.println("Employee Last Name: " + rs.getString("last_name"));
                                System.out.println("Employee Age: " + rs.getInt("age"));
                                System.out.println();

                            }
                        }
                        break;
                    case 2:
                        System.out.print("Enter Employee First Name: ");
                        String firstName = scan.nextLine();

                        System.out.print("Enter Employee Last Name: ");
                        String lastName = scan.nextLine();

                        System.out.print("Enter Employee Age: ");
                        int age = scan.nextInt();

                        String insertSQL = "INSERT INTO Employees (first_name,last_name,age) " + "VALUES(?,?,?)";

                        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                            pstmt.setString(1, firstName);
                            pstmt.setString(2, lastName);
                            pstmt.setInt(3, age);

                            int rowsInserted = pstmt.executeUpdate();

                            if (rowsInserted > 0) {
                                System.out.println("A new employee was inserted successfully!");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Soon...");
                        break;
                    case 4:
                        System.out.print("Enter Employee ID to delete: ");
                        int employeeIDToDelete = scan.nextInt();

                        String deleteSQL = "DELETE FROM employees WHERE id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                            pstmt.setInt(1, employeeIDToDelete);

                            int rowsDeleted = pstmt.executeUpdate();

                            if (rowsDeleted > 0) {
                                System.out.println("Employee with ID " + employeeIDToDelete + " was deleted successfully!");
                            } else {
                                System.out.println("No employee found with ID " + employeeIDToDelete);

                            }
                        }

                        break;
                    case 5:
                        System.out.println("Exiting the program. Goodbye!");
                        return;
                    default:
                        System.out.println("Unkown choice try again.");
                }

            }

        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
        } finally {
            scan.close();
        }
    }

}
