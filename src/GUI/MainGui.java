package GUI;

import source.Appointment;
import source.AppointmentManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainGui {
    //Main menu Panel
    private JPanel MainPanel;

    //Menu name label
    private JLabel menu;

    //Main menu buttons
    private JButton AddAppointment;
    private JButton ViewAppointments;
    private JButton ManageAppointment;

    //Spacer between menu buttons
    private JLabel Spacer1;
    private JLabel Spacer2;

    //Manager
    private AppointmentManager manager;

    public MainGui() {
        //Application Styl
        visuals();

        //Initialized the data manager, will hold all appointment data
        manager = new AppointmentManager();

        //Action Listener for the 3 buttons in main menu
        AddAppointment.addActionListener(this::addAppointment);
        ViewAppointments.addActionListener(this::viewAppointments);
        ManageAppointment.addActionListener(this::manageAppointment);
    }

    private void addAppointment(ActionEvent e) {
        //Initialized and set up dialog window
        AddAppointment dialog = new AddAppointment(false, null);
        dialog.pack();
        dialog.setVisible(true);

        //Get appointment user wants to add
        Appointment appointment = dialog.getAppointment();

        //Attempt to add appointment to data manager
        //If attempt fails, appropriate message will be display
        if(e.getSource().equals(AddAppointment) && appointment != null) {
            try{ manager.add(appointment);
            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    private void viewAppointments(ActionEvent e) {
        //Initialized and set up dialog window
        //Pass data manager to dialog to display appointment data
        ViewAppointment dialog = new ViewAppointment(manager);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void manageAppointment(ActionEvent e){
        //Initialized and set up dialog window
        //Pass data manager to dialog so user can manage appointment data
        ManageAppointment dialog = new ManageAppointment(manager);
        dialog.pack();
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainGui");
        frame.setContentPane(new MainGui().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    private void visuals() {
        //Styling of the main panel and its components

        //Color of main Panel
        MainPanel.setBackground(Color.decode("#114538"));

        //Color of buttons
        ViewAppointments.setBackground(Color.decode("#5e8d83"));
        AddAppointment.setBackground(Color.decode("#5e8d83"));
        ManageAppointment.setBackground(Color.decode("#5e8d83"));

        //Color of buttons lettering
        ViewAppointments.setForeground(Color.black);
        AddAppointment.setForeground(Color.black);
        ManageAppointment.setForeground(Color.black);

        //Size of buttons
        ViewAppointments.setPreferredSize(new Dimension(175, 40));
        AddAppointment.setPreferredSize(new Dimension(175, 40));
        ManageAppointment.setPreferredSize(new Dimension(175, 40));

        //Set border and its color for buttons
        ViewAppointments.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        AddAppointment.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        ManageAppointment.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
    }
}

