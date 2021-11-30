import java.time.Year;

public abstract class Member {
    private static int idCounter = 1;

    private String firstName;
    private String middleName;
    private String lastName;
    private int yearOfBirth;
    private String phoneNumber;
    private String email;
    private boolean isActive;
    private boolean isCompetitive = false;
    private boolean hasPaid;
    private int id;

    public Member(String firstName, String middleName, String lastName, int yearOfBirth,
                  String phoneNumber, String email, boolean isActive, boolean hasPaid) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = isActive;
        this.id = idCounter++;
        this.hasPaid = hasPaid;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
    public String toString() {
        //TODO ret i det så der ikke er en if
        if(middleName.equals("")) {
            return "#" + id + " " + firstName + " " + lastName + ", født i år: " + yearOfBirth +
                    ", telefonnummer: " + phoneNumber + ", email: " + email;
        }else {
            return "#" + id + " " + firstName + " " + middleName + " " + lastName + ", født i år: " + yearOfBirth +
                    ", telefonnummer: " + phoneNumber + ", email: " + email;
        }
    }
    public String toFile(){
        return firstName + ";" + middleName + ";" + lastName + ";" + yearOfBirth +
                ";" + phoneNumber + ";" + email + ";" + isActive + ";" + isCompetitive + ";" + hasPaid + ";";
    }
    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public void setIsCompetitive(boolean isCompetitive) {
        this.isCompetitive = isCompetitive;
    }
    public boolean getHasPaid(){
        return hasPaid;
    }
    public String toPayment(){
        final String TEXT_RED = "\u001B[31m";
        final String TEXT_RESET = "\u001B[0m";
        return toString() + " "+ TEXT_RED + getPayment() + "kr." + TEXT_RESET;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public int getPayment() {
        int payment = 0;
        int currentYear = Year.now().getValue();
        if(isActive == false){
            payment = 500;
        }else if(currentYear - yearOfBirth < 18){
            payment = 1000;
        }else if(currentYear - yearOfBirth >= 18 && currentYear - yearOfBirth < 60){
            payment = 1600;
        }else if(currentYear - yearOfBirth >= 60){
            payment = 1200;
        }
        return payment;
    }


}



