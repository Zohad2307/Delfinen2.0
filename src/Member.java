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

    private int id;

    public Member(String firstName, String middleName, String lastName, int yearOfBirth, String phoneNumber, String email, boolean isActive) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.isActive = isActive;
        this.id = idCounter++;
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
                ";" + phoneNumber + ";" + email + ";" + isActive + ";" + isCompetitive + ";";
    }
    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }

    public void setIsCompetitive(boolean isCompetitive) {
        this.isCompetitive = isCompetitive;
    }


    // public int/payment getPayment() {} noget med dato og så videre
}

