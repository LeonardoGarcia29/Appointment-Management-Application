package source;
import java.time.LocalDate;

public abstract class Appointment implements Comparable<Appointment> {

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    //Alt + insert
    public Appointment(String description, LocalDate startDate, LocalDate endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    //Getters
    public String getDescription() {

        return description;
    }
    public LocalDate getEndDate() {

        return endDate;
    }
    public LocalDate getStartDate() {

        return startDate;
    }
    //Setters
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Check if the date appointment occurs on a given date
     * @param date date to be checked
     * @return true if date >= start date and <= end date
     */

    public abstract String getAppointmentType();

    public abstract boolean occursOn(LocalDate date);

    protected boolean inBetween(LocalDate date) {

        //Flip to !()
       // return (date.isAfter(startDate) && date.isBefore(endDate)) || (date.isEqual(startDate) || date.isEqual(endDate));
        return ((startDate.isEqual(date) || startDate.isBefore(date)) &&
                (endDate.isEqual(date) || endDate.isAfter(date)));
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s", description, startDate, endDate);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment that)) return false;
        return toString().equals(that.toString());
        //return Objects.equals(this.toString(), that.toString());
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int compareTo(Appointment that) {
       if(!startDate.equals(that.startDate)) {
           return startDate.compareTo(that.startDate);
       }
       else if(!endDate.equals(that.endDate)) {
           return endDate.compareTo(that.endDate);
       }
       else if (!description.equals(that.description)) {
               return description.compareTo(that.description);
       }
       return 0;
    }
}
