package source;

import java.util.Comparator;

public class AppointmentComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment o1, Appointment o2) {

        //Checks Description First
        int trueDescription = o1.getDescription().compareTo(o2.getDescription());
            if (trueDescription < 0) {
                return -1;
            }
             if (trueDescription > 0) {
                return 1;
            }

        int trueStartDate = o1.getStartDate().compareTo(o2.getStartDate());
            //Then Checks startDate
            if(trueStartDate < 0) {
                return -1;
            }
            if (trueStartDate > 0) {
                return 1;
            }
            //Last Check is endDAte
        int trueEndDate = o1.getEndDate().compareTo(o2.getEndDate());
            if(trueEndDate < 0) {
                return -1;
            }
            if (trueEndDate > 0) {
                return 1;
            }

            //ONLY EXECUTES IF THEY ARE EXACTLY THE SAME IMPOSSIBLEEE
            return 0;
    }

}
