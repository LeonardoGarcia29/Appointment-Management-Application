package source;

import java.time.LocalDate;
import java.util.*;

public class AppointmentManager {
    Set<Appointment> appointments;

    public AppointmentManager() {
        appointments = new HashSet<>();
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void add(Appointment appointment) {
        if (!appointments.add(appointment)) {
            throw new IllegalArgumentException("Appointment exists!");
        }
    }

    public void delete(Appointment appointment) {
        if (!appointments.remove(appointment)) {
            throw new IllegalArgumentException("Select an appointment to delete!");
        }

    }

    public void update(Appointment current, Appointment modified) {
        if (modified == null) {
            throw new IllegalArgumentException("Select an appointment to update!");
        }
        delete(current);
        add(modified);
    }

    public Appointment[] getAppointmentsOn(LocalDate date, Comparator<Appointment> order) {

        if (appointments.isEmpty()) {
            return new Appointment[0];
        }

        Appointment[] AppointmentOrganized = new Appointment[appointments.size()];

        PriorityQueue<Appointment> temp = new PriorityQueue<>(appointments.size(), order);
        List<Appointment> appointmentList = new ArrayList<>();

        if (date != null) {
            for (Appointment a : appointments) {
                if (a.occursOn(date)) {
                    appointmentList.add(a);
                }
            }
            if (order != null) {
                Collections.sort(appointmentList, order);
            }

        }
        else {
            for (Appointment a : appointments) {
                appointmentList.add(a);
            }
            if (order != null) {
                Collections.sort(appointmentList, order);
            }
        }

        for (Appointment a : appointmentList) {
            temp.add(a);
        }

        AppointmentOrganized = temp.toArray(new Appointment[temp.size()]);
        return AppointmentOrganized;
    }
}

