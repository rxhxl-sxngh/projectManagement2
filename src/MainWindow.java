import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Main Window");
        setSize(1000, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton openJobViewButton = new JButton("Open Job View");
        openJobViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JobView jobView = new JobView(); // Open Job View
                jobView.setVisible(true);
            }
        });

        JButton openEmployeeViewButton = new JButton("Open Employee View");
        openEmployeeViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeView employeeView = new EmployeeView(); // Open Employee View
                employeeView.setVisible(true);
            }
        });

        JPanel panel = new JPanel();
        panel.add(openJobViewButton);
        panel.add(openEmployeeViewButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}



