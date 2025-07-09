import javax.swing.*;
import java.awt.*;
import java.util.*;

class Patient {
    int id;
    String name;
    String medicalHistory;
    String appointment;

    Patient(int id, String name) {
        this.id = id;
        this.name = name;
        this.medicalHistory = "";
        this.appointment = "";
    }

    public String toString() {
        return id + ": " + name;
    }
}

public class HospitalSystem extends JFrame {
    Map<Integer, Patient> patients = new HashMap<>();
    DefaultListModel<Patient> patientListModel = new DefaultListModel<>();
    JList<Patient> patientList = new JList<>(patientListModel);

    JTextField txtId = new JTextField(5);
    JTextField txtName = new JTextField(15);
    JTextArea txtMedicalHistory = new JTextArea(5, 20);
    JTextField txtAppointment = new JTextField(15);

    public HospitalSystem() {
        super("Hospital Information System");
        setLayout(new BorderLayout());

        // Left panel: Patient list
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JLabel("Patients:"), BorderLayout.NORTH);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        leftPanel.add(new JScrollPane(patientList), BorderLayout.CENTER);

        // Right panel: Patient info form
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.anchor = GridBagConstraints.WEST;

        c.gridx=0; c.gridy=0; rightPanel.add(new JLabel("ID:"), c);
        c.gridx=1; rightPanel.add(txtId, c);

        c.gridx=0; c.gridy=1; rightPanel.add(new JLabel("Name:"), c);
        c.gridx=1; rightPanel.add(txtName, c);

        c.gridx=0; c.gridy=2; rightPanel.add(new JLabel("Medical History:"), c);
        c.gridx=1; rightPanel.add(new JScrollPane(txtMedicalHistory), c);

        c.gridx=0; c.gridy=3; rightPanel.add(new JLabel("Appointment:"), c);
        c.gridx=1; rightPanel.add(txtAppointment, c);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add Patient");
        JButton btnUpdate = new JButton("Update Patient");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);

        // Add panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event handlers
        btnAdd.addActionListener(e -> addPatient());
        btnUpdate.addActionListener(e -> updatePatient());

        patientList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Patient p = patientList.getSelectedValue();
                if (p != null) loadPatient(p);
            }
        });

        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void addPatient() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (patients.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "ID already exists!");
                return;
            }
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!");
                return;
            }
            Patient p = new Patient(id, name);
            p.medicalHistory = txtMedicalHistory.getText();
            p.appointment = txtAppointment.getText();
            patients.put(id, p);
            patientListModel.addElement(p);
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID!");
        }
    }

    void updatePatient() {
        Patient p = patientList.getSelectedValue();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Select a patient to update!");
            return;
        }
        p.name = txtName.getText().trim();
        p.medicalHistory = txtMedicalHistory.getText();
        p.appointment = txtAppointment.getText();
        patientList.repaint();
        clearForm();
    }

    void loadPatient(Patient p) {
        txtId.setText(String.valueOf(p.id));
        txtName.setText(p.name);
        txtMedicalHistory.setText(p.medicalHistory);
        txtAppointment.setText(p.appointment);
    }

    void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtMedicalHistory.setText("");
        txtAppointment.setText("");
        patientList.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HospitalSystem());
    }
}
