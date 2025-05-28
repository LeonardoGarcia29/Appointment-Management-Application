package GUI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import source.Appointment;
import source.AppointmentComparator;
import source.AppointmentManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class ViewAppointment extends JDialog {

    //Buttons
    private JButton buttonCancel;
    private JButton buttonClearAll;

    //Radio Buttons
    private JRadioButton DisplayAll;
    private JRadioButton SelectDisplayDate;

    //Drop down
    private JComboBox<String> SortOrder;

    //Labels
    private JLabel OrderType;
    private JLabel DisplayType;

    //Display List
    private JList<Appointment> AppointmentsList;

    //Date Selector
    private DatePicker DisplayDatePicker;

    //Panels
    private JPanel contentPane;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    //Appointment and Manager
    private Appointment[]  appointments;
    private final AppointmentManager manager;


    public ViewAppointment(AppointmentManager m) {
        //Initialized Appointment Manager
        manager = m;
        setContentPane(contentPane);
        setModal(true);

        //Application Styling
        visuals();

        //Disable display options until, a display type is selected
        SortOrder.setEnabled(false);
        DisplayDatePicker.setEnabled(false);
        SortOrder.setSelectedItem(null);
        AppointmentsList.setEnabled(false);

        //Actions Listeners
        DisplayAll.addActionListener(this::DisplayAllOptions);
        SelectDisplayDate.addActionListener(this::DisplayByDateOptions);
        SortOrder.addActionListener(this::orderTypeDisplay);
        DisplayDatePicker.addDateChangeListener(this::dataChangeDisplay);
        buttonClearAll.addActionListener(this::clearAll);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void clearAll(ActionEvent e) {
        //When clear all button is clicked, all display fields will be clear
        SortOrder.setSelectedItem(null);
        DisplayDatePicker.clear();
        Appointment[] clear = {};
        AppointmentsList.setListData(clear);
    }

    private void displayManager(){
        //Initialized comparator and a LocalDate to input for the Appointmentmanager.java->getAppointmentsOn()
        AppointmentComparator comparator = new AppointmentComparator();
        LocalDate date = DisplayDatePicker.getDate();

        //The 4 possible cases for displaying appointments
        //Each case will call getAppointmentsOn() with the appropriate date and order
        //The list in this dialog will display dates and resize accordingly
        if(SortOrder.getSelectedItem() == "Date" && date == null){
            appointments = manager.getAppointmentsOn(null,null);
            AppointmentsList.setListData(appointments);
            this.pack();
        }
        else if(SortOrder.getSelectedItem() == "Date" && date != null){
            appointments = manager.getAppointmentsOn(date,null);
            AppointmentsList.setListData(appointments);
            this.pack();
        }
        else if(SortOrder.getSelectedItem() == "Description" && date == null){
            appointments = manager.getAppointmentsOn(null, comparator);
            AppointmentsList.setListData(appointments);
            this.pack();
        }
        else if(SortOrder.getSelectedItem() == "Description" && date != null){
            appointments = manager.getAppointmentsOn(date, comparator);
            AppointmentsList.setListData(appointments);
            this.pack();
        }
    }

    private void DisplayAllOptions(ActionEvent e){
        //When "Display All" is selected,currently selected options will clear
        SortOrder.setEnabled(true);
        SortOrder.setSelectedItem(null);
        DisplayDatePicker.setEnabled(false);
        DisplayDatePicker.clear();
        AppointmentsList.setEnabled(true);
        Appointment[] clear = {};
        AppointmentsList.setListData(clear);
    }

    private void DisplayByDateOptions(ActionEvent e){
        //When "Display by Dates" is selected,currently selected options will clear
        SortOrder.setEnabled(true);
        SortOrder.setSelectedItem(null);
        DisplayDatePicker.setEnabled(true);
        DisplayDatePicker.clear();
        AppointmentsList.setEnabled(true);
        Appointment[] clear = {};
        AppointmentsList.setListData(clear);
    }

    private void dataChangeDisplay(DateChangeEvent e){
        //When the date is change, call DisplayManager() to update appointment display list accordingly
        displayManager();
    }

    private void orderTypeDisplay(ActionEvent e){
        //When the order type display is change, call DisplayManager() to update appointment display list accordingly
        displayManager();
    }

    private void onCancel() {
        //Close Dialog
        dispose();
    }

    public static void main(String[] args) {
        ViewAppointment dialog = new ViewAppointment(new AppointmentManager());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void visuals(){
        //Styling of the panels and its components

        //Color of panels
        contentPane.setBackground(Color.decode("#114538"));
        panel1.setBackground(Color.decode("#114538"));
        panel2.setBackground(Color.decode("#114538"));
        panel3.setBackground(Color.decode("#114538"));

        //Color of buttons lettering
        buttonClearAll.setForeground(Color.black);
        buttonCancel.setForeground(Color.black);

        //Color of buttons
        buttonClearAll.setBackground(Color.decode("#5e8d83"));
        buttonCancel.setBackground(Color.decode("#5e8d83"));

        //Set border and its color for buttons
        buttonClearAll.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        buttonCancel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));

        //Size of buttons
        buttonClearAll.setPreferredSize(new Dimension(75, 25));
        buttonCancel.setPreferredSize(new Dimension(70, 25));
    }
}
