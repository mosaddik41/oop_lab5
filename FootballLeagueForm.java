import javax.swing.*;
import java.io.*;

public class FootballLeagueForm {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FootballLeagueForm().createUI());
    }

    private JFrame frame;
    private JTextField nameField, phoneField, emailField, addressField, dobField;
    private JComboBox<String> genderBox, degreeBox, positionBox;
    private JCheckBox interDeptBox;
    private JTextArea experienceArea;
    private JLabel pictureLabel;
    private File pictureFile = null;

    public void createUI() {
        frame = new JFrame("IITDU Football League Registration Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 700);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = createInput(panel, "Name:");
        phoneField = createInput(panel, "Phone:");
        emailField = createInput(panel, "Email:");
        addressField = createInput(panel, "Address:");

        panel.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"Select", "Male", "Female", "Other"});
        panel.add(genderBox);

        dobField = createInput(panel, "Date of Birth (YYYY-MM-DD):");

        panel.add(new JLabel("Latest Degree (IITDU):"));
        degreeBox = new JComboBox<>(new String[]{"Select", "BSc in Software Engineering", "MSc in Software Engineering"});
        panel.add(degreeBox);

        panel.add(new JLabel("Preferred Position:"));
        positionBox = new JComboBox<>(new String[]{"Select", "Goalkeeper", "Defender", "Midfielder", "Forward"});
        panel.add(positionBox);

        interDeptBox = new JCheckBox("Played in Inter-departmental Competition");
        panel.add(interDeptBox);

        panel.add(new JLabel("Football Experience:"));
        experienceArea = new JTextArea(5, 30);
        experienceArea.setLineWrap(true);
        experienceArea.setWrapStyleWord(true);
        panel.add(new JScrollPane(experienceArea));

        JButton uploadBtn = new JButton("Upload Picture");
        pictureLabel = new JLabel("No file selected");
        uploadBtn.addActionListener(e -> uploadPicture());
        panel.add(uploadBtn);
        panel.add(pictureLabel);

        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> handleSubmit());
        panel.add(submitBtn);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private JTextField createInput(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField field = new JTextField();
        panel.add(field);
        return field;
    }

    private void uploadPicture() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            pictureFile = chooser.getSelectedFile();
            pictureLabel.setText(pictureFile.getName());
        }
    }

    private void handleSubmit() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String dob = dobField.getText().trim();
        String degree = (String) degreeBox.getSelectedItem();
        String position = (String) positionBox.getSelectedItem();
        boolean playedBefore = interDeptBox.isSelected();
        String experience = experienceArea.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() ||
                gender.equals("Select") || dob.isEmpty() || degree.equals("Select") ||
                position.equals("Select") || experience.isEmpty() || pictureFile == null) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("participants.txt", true))) {
            out.println("Name: " + name);
            out.println("Phone: " + phone);
            out.println("Email: " + email);
            out.println("Address: " + address);
            out.println("Gender: " + gender);
            out.println("DOB: " + dob);
            out.println("Degree: " + degree);
            out.println("Preferred Position: " + position);
            out.println("Inter-departmental: " + (playedBefore ? "Yes" : "No"));
            out.println("Experience: " + experience);
            out.println("Picture: " + pictureFile.getAbsolutePath());
            out.println("---------------------------");

            JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
