package GUI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import source.Appointment;
import source.DailyAppointment;
import source.MonthlyAppointment;
import source.OnetimeAppointment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class AddAppointment extends JDialog {
    //Dialog Panels
    private JPanel contentPane;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;

    //Buttons
    private JButton buttonADD;
    private JButton buttonCancel;

    //Drop Down menu
    private JComboBox<String> AppointmentType;

    //Fill in box
    private JTextField Description;

    //Labels
    private JLabel LDescription;
    private JLabel LDate;
    private JLabel StartDate;
    private JLabel EndDate;
    private JLabel APTittle;

    //Date Pickers
    private DatePicker startPicker;
    private DatePicker endPicker;

    //Appointment
    Appointment appointment;

    //Booleans hold whether appointment field has been filled
    boolean typeSelected = false;
    boolean descriptionSelected = true;
    boolean startDateSelected = false;
    boolean endDateSelected = false;

    public AddAppointment(boolean Update, Appointment UpdatedAppSelected) {

        //Change Display whether dialog is called to add or update
        if(Update) {
            APTittle.setText("UPDATE APPOINTMENT");
            buttonADD.setText("UPDATE");
            startPicker.setDate(UpdatedAppSelected.getStartDate());
            endPicker.setDate(UpdatedAppSelected.getEndDate());
            AppointmentType.setSelectedItem(UpdatedAppSelected.getAppointmentType());
            Description.setText(UpdatedAppSelected.getDescription());
            typeSelected = true;
            startDateSelected = true;
            endDateSelected = true;
            if(AppointmentType.getSelectedItem() == "One Time"){
                endPicker.setEnabled(false);
            }

        }
        else{
            APTittle.setText("ADD APPOINTMENT");
            buttonADD.setText("ADD");
            //Disable OK button until all fields are completed
            buttonADD.setEnabled(false);
            //Dialog will spawn with NO appointment type selected
            AppointmentType.setSelectedItem(null);
        }

        //Application Styling
        visuals();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonADD);


        //Action Listener for drop down menu and date selectors
        AppointmentType.addActionListener(this::appointmentType);
        startPicker.addDateChangeListener(this::appointmentStartDate);
        endPicker.addDateChangeListener(this::appointmentEndDate);

        buttonADD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onADD();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(UpdatedAppSelected);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(UpdatedAppSelected);
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(UpdatedAppSelected);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void appointmentEndDate(DateChangeEvent e) {
        //<Dates Validation>

        //If start date had not been selected, no need to check for date validation on End Date
        if(startPicker.getDate() != null && endPicker.getDate() != null) {

            //Validate End Date for a Daily/Monthly Appointment
            //Appropriate message will display if, date is invalid

            if (AppointmentType.getSelectedItem() == "Daily" || AppointmentType.getSelectedItem() == "Monthly") {
                try {
                    //Check if Start Date is equal or before End Date
                    if (!(startPicker.getDate().equals(endPicker.getDate()) || startPicker.getDate().isBefore(endPicker.getDate()))) {
                        endPicker.clear();
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Date selected is before Start-Date! \n Please select a different Date!");
                }
            }

        }

        //Check if an End Date was selected
        endDateSelected = !(endPicker.getDate() == null);
        //Check if all fields are selected to enable ADD button
        ADDCheck();
    }

    private void appointmentStartDate(DateChangeEvent e) {
        //<Dates Validation>

        //If end date had not been selected, no need to check for date validation on End Date
        if(endPicker.getDate() != null && startPicker.getDate() != null) {
            //Validate Start Date for a Daily/Monthly Appointment
            //Appropriate message will display if, date is invalid
            if (AppointmentType.getSelectedItem() == "Daily" || AppointmentType.getSelectedItem() == "Monthly") {
                try {
                    //Check if Start Date is equal or before End Date
                    if (!(startPicker.getDate().equals(endPicker.getDate()) || startPicker.getDate().isBefore(endPicker.getDate()))) {
                        startPicker.clear();
                        throw new IllegalArgumentException();
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Date selected is after End-Date! \n Please select a different Date!");
                }
            }
        }

        //Check if a Start Date was selected
        startDateSelected = !(startPicker.getDate() == null);
        //Check if all fields are selected to enable ADD button
        ADDCheck();
    }

    private void appointmentType(ActionEvent e) {
        //Appointment Type was Selected
        typeSelected = true;
        //Change the Dialog according to the Appointment Type selected
        if(AppointmentType.getSelectedItem() == ("One Time")){
            endPicker.setEnabled(false);
            EndDate.setText("End & Start Date Match");
            endDateSelected = true;
            this.pack();
        } else if (AppointmentType.getSelectedItem() == "Daily") {
            endPicker.setEnabled(true);

            if(endPicker.getDate() == null) {
                endDateSelected = false;
            }
            EndDate.setText("End Date");
            this.pack();
        } else if (AppointmentType.getSelectedItem() == "Monthly") {
            endPicker.setEnabled(true);
            if(endPicker.getDate() == null) {
                endDateSelected = false;
            }
            EndDate.setText("End Date");
            this.pack();
        }

        //Check if all fields are selected to enable ADD button
        ADDCheck();
    }


    private void ADDCheck(){
        //Check if all fields are completed to allow the appointment to be added, ADD button enable
        buttonADD.setEnabled(typeSelected && descriptionSelected && startDateSelected && endDateSelected);
    }

    private void onADD() {

        //Fetch all information filled by user
        String Desc = Description.getText();
        LocalDate start = startPicker.getDate();
        LocalDate end = endPicker.getDate();

        //Place information into the appointment type selected by user
        if(AppointmentType.getSelectedItem() == ("One Time")){
            appointment = new OnetimeAppointment(Desc, start);
        } else if (AppointmentType.getSelectedItem() == "Daily") {
            appointment = new DailyAppointment(Desc, start, end);
        } else if (AppointmentType.getSelectedItem() == "Monthly") {
            appointment = new MonthlyAppointment(Desc, start, end);
        }

        dispose();
    }

    public Appointment getAppointment() {
        //Return appointment with user data to add to manager
        return appointment;
    }

    private void onCancel(Appointment UpdatedAppSelected) {
        //If user cancels appointment addition, return a null appointment
        if(buttonADD.getText() == "ADD"){
            appointment = null;
        }
        else{
            appointment = UpdatedAppSelected;
        }

        dispose();
    }

    private void visuals(){
        //Styling of the panels and its components

        //Color of panels
        contentPane.setBackground(Color.decode("#114538"));
        panel1.setBackground(Color.decode("#114538"));
        panel2.setBackground(Color.decode("#114538"));
        panel3.setBackground(Color.decode("#114538"));
        panel4.setBackground(Color.decode("#114538"));

        //Color of buttons lettering
        buttonADD.setForeground(Color.black);
        buttonCancel.setForeground(Color.black);

        //Color of buttons
        buttonADD.setBackground(Color.decode("#5e8d83"));
        buttonCancel.setBackground(Color.decode("#5e8d83"));

        //Set border and its color for buttons
        buttonADD.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        buttonCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        //Size of buttons
        buttonADD.setPreferredSize(new Dimension(70, 25));
        buttonCancel.setPreferredSize(new Dimension(70, 25));
    }
}
