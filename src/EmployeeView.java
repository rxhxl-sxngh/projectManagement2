import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame {
    private JTextField employeeIDField;
    private JTextField employeeNameField;
    private JTextField jobClassIDField;
    private final JComboBox<String> operationComboBox;
    private final JPanel panel = new JPanel();

    public EmployeeView() {
        setTitle("Employee View");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        panel.setLayout(new GridLayout(4, 2));

        JLabel operationLabel = new JLabel("Select Operation:");
        String[] operations = {"Add Employee", "Update Employee"};
        operationComboBox = new JComboBox<>(operations);
        panel.add(operationLabel);
        panel.add(operationComboBox);

        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOperation = (String) operationComboBox.getSelectedItem();
                assert selectedOperation != null;
                if (selectedOperation.equals("Add Employee")) {
                    addFields();
                } else {
                    updateFields();
                }
            }
        });

        add(panel);
    }

    private void addFields() {
        panel.removeAll(); // Clear previous components
        panel.setLayout(new GridLayout(4, 2));

        JLabel employeeIDLabel = new JLabel("Employee ID:");
        employeeIDField = new JTextField();
        panel.add(employeeIDLabel);
        panel.add(employeeIDField);

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        employeeNameField = new JTextField();
        panel.add(employeeNameLabel);
        panel.add(employeeNameField);

        JLabel jobClassIDLabel = new JLabel("Job Class ID:");
        jobClassIDField = new JTextField();
        panel.add(jobClassIDLabel);
        panel.add(jobClassIDField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add employee
                int employeeID = Integer.parseInt(employeeIDField.getText());
                String employeeName = employeeNameField.getText();
                int jobClassID = Integer.parseInt(jobClassIDField.getText());
                Employee employee = new Employee(employeeID, employeeName, jobClassID);
                DataAccess.addEmployee(employee);
                // Show operation selection view
                showOperationSelectionView();
            }
        });
        panel.add(addButton);

        JButton backButton = getBackButton();
        panel.add(backButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private void updateFields() {
        panel.removeAll(); // Clear previous components
        panel.setLayout(new GridLayout(4, 2));

        JLabel employeeIDLabel = new JLabel("Employee ID:");
        employeeIDField = new JTextField();
        panel.add(employeeIDLabel);
        panel.add(employeeIDField);

        JLabel employeeNameLabel = new JLabel("Employee Name:");
        employeeNameField = new JTextField();
        panel.add(employeeNameLabel);
        panel.add(employeeNameField);

        JLabel jobClassIDLabel = new JLabel("Job Class ID:");
        jobClassIDField = new JTextField();
        panel.add(jobClassIDLabel);
        panel.add(jobClassIDField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update employee
                int employeeID = Integer.parseInt(employeeIDField.getText());
                String employeeName = employeeNameField.getText();
                int jobClassID = Integer.parseInt(jobClassIDField.getText());
                Employee employee = new Employee(employeeID, employeeName, jobClassID);
                DataAccess.updateEmployee(employee);
                // Show operation selection view
                showOperationSelectionView();
            }
        });
        panel.add(updateButton);

        JButton backButton = getBackButton();
        panel.add(backButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Go Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show operation selection view
                showOperationSelectionView();
            }
        });
        return backButton;
    }

    private void showOperationSelectionView() {
        panel.removeAll();
        JLabel operationLabel = new JLabel("Select Operation:");
        panel.add(operationLabel);
        panel.add(operationComboBox);
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeView employeeView = new EmployeeView();
            employeeView.setVisible(true);
        });
    }
}


