import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JobView extends JFrame {
    private JTextField jobClassIDField;
    private JTextField jobTitleField;
    private JTextField hourlyWageField;
    private final JComboBox<String> operationComboBox;
    private final JPanel panel = new JPanel();

    public JobView() {
        setTitle("Job View");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        panel.setLayout(new GridLayout(4, 2));

        JLabel operationLabel = new JLabel("Select Operation:");
        String[] operations = {"Add Job Class", "Update Job Class"};
        operationComboBox = new JComboBox<>(operations);
        panel.add(operationLabel);
        panel.add(operationComboBox);

        operationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOperation = (String) operationComboBox.getSelectedItem();
                assert selectedOperation != null;
                if (selectedOperation.equals("Add Job Class")) {
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

        JLabel jobClassIDLabel = new JLabel("Job Class ID:");
        jobClassIDField = new JTextField();
        panel.add(jobClassIDLabel);
        panel.add(jobClassIDField);

        JLabel jobTitleLabel = new JLabel("Job Title:");
        jobTitleField = new JTextField();
        panel.add(jobTitleLabel);
        panel.add(jobTitleField);

        JLabel hourlyWageLabel = new JLabel("Hourly Wage:");
        hourlyWageField = new JTextField();
        panel.add(hourlyWageLabel);
        panel.add(hourlyWageField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add job class
                int jobClassID = Integer.parseInt(jobClassIDField.getText());
                String jobTitle = jobTitleField.getText();
                double hourlyWage = Double.parseDouble(hourlyWageField.getText());
                Job job = new Job(jobClassID, jobTitle, hourlyWage);
                DataAccess.addJobClass(job);
            }
        });
        panel.add(addButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private void updateFields() {
        panel.removeAll(); // Clear previous components
        panel.setLayout(new GridLayout(4, 2));

        JLabel jobClassIDLabel = new JLabel("Job Class ID:");
        jobClassIDField = new JTextField();
        panel.add(jobClassIDLabel);
        panel.add(jobClassIDField);

        JLabel jobTitleLabel = new JLabel("Job Title:");
        jobTitleField = new JTextField();
        panel.add(jobTitleLabel);
        panel.add(jobTitleField);

        JLabel hourlyWageLabel = new JLabel("Hourly Wage:");
        hourlyWageField = new JTextField();
        panel.add(hourlyWageLabel);
        panel.add(hourlyWageField);

        JButton updateButton = getjButton();
        panel.add(updateButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the component
    }

    private JButton getjButton() {
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update job class
                int jobClassID = Integer.parseInt(jobClassIDField.getText());
                String jobTitle = jobTitleField.getText();
                double hourlyWage = Double.parseDouble(hourlyWageField.getText());
                Job job = new Job(jobClassID, jobTitle, hourlyWage);
                DataAccess.updateJobClass(job);
            }
        });
        return updateButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JobView jobView = new JobView();
            jobView.setVisible(true);
        });
    }
}
