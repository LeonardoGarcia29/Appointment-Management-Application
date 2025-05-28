package GUI;

import source.Appointment;
import source.AppointmentComparator;
import source.AppointmentManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ManageAppointment extends JDialog {
    //Panel
    private JPanel contentPane;

    //Buttons
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonUpdate;
    private JButton buttonDelete;

    //labels
    private JLabel SelectApp;

    //Display Lists
    private JList<Appointment> DisplayList;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    //Appointment and Manager
    private Appointment[]  appointments;
    private final AppointmentManager manager;

    public ManageAppointment(AppointmentManager m) {
        //Initialized Appointment Manager
        manager = m;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Application Styling
        visuals();

        //Display ALl Current Appointments
        showList();

        //Action Listeners for buttons
        buttonUpdate.addActionListener(this::update);
        buttonDelete.addActionListener(this::delete);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        //call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        //call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void update(ActionEvent e) {

        //Get appointment selected by user to update or delete
       Appointment SelectedAppointment = DisplayList.getSelectedValue();

        //If no appointment was selected, update method will throw exception
        //Appropriate message will be display to the user
        if((SelectedAppointment == null)) {
            if(e.getSource().equals(buttonUpdate)) {
                try {
                    manager.update(null, null);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
        //If an appointment was selected by a user
        else{
            //Open appointment addition window, to update appointment information
            AddAppointment dialog = new AddAppointment(true, SelectedAppointment);
            dialog.pack();
            dialog.setVisible(true);

            //Get updated appointment
            Appointment appointmentAdd = dialog.getAppointment();

            //Call update manager to implement updated appointment
            //Appropriate message will be display if appointment cannot be updated
            if(e.getSource().equals(buttonUpdate)) {
                try {
                    manager.update(SelectedAppointment, appointmentAdd);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                //Display updated appointments list
                showList();
            }
        }
    }

    private void showList() {
        //Gets all the appointments from manager and displays all
        AppointmentComparator comparator = new AppointmentComparator();
        appointments = manager.getAppointmentsOn(null, comparator);
        DisplayList.setListData(appointments);
        this.pack();

    }

    private void delete(ActionEvent e) {
        //Get appointment to delete
        Appointment appointment = DisplayList.getSelectedValue();

        //Attempt to delete appointment
        //if appointment cannot be deleted, appropriate message will be displayed
        if(e.getSource().equals(buttonDelete)) {
            try{
                manager.delete(appointment);
            }catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            //Display updated appointment list
            showList();
        }
    }

    private void onCancel() {
        //Close window on cancel
        dispose();
    }

    public static void main(String[] args) {
        ManageAppointment dialog = new ManageAppointment(new AppointmentManager());
        dialog.pack();
        dialog.setSize(200,200);
        dialog.setVisible(true);
        System.exit(0);
    }

    private void visuals() {
        //Styling of the panels and its components

        //Color of panels
        contentPane.setBackground(Color.decode("#114538"));
        panel1.setBackground(Color.decode("#114538"));
        panel2.setBackground(Color.decode("#114538"));
        panel3.setBackground(Color.decode("#114538"));

        //Color of buttons lettering
        buttonDelete.setForeground(Color.black);
        buttonUpdate.setForeground(Color.black);
        buttonCancel.setForeground(Color.black);

        //Color of buttons
        buttonDelete.setBackground(Color.decode("#5e8d83"));
        buttonUpdate.setBackground(Color.decode("#5e8d83"));
        buttonCancel.setBackground(Color.decode("#5e8d83"));

        //Set border and its color for buttons
        buttonDelete.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        buttonUpdate.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        buttonCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        //Size of buttons
        buttonDelete.setPreferredSize(new Dimension(75, 25));
        buttonUpdate.setPreferredSize(new Dimension(75, 25));
        buttonCancel.setPreferredSize(new Dimension(70, 25));
    }
}
