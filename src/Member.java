public abstract class Member {
    private static int idCounter = 1;

    private String firstName;
    private String middleName;
    private String lastName;
    private int yearOfBirth;
    private String phoneNumber;
    private String email;

    private int id;

    public Member(String firstName, String middleName, String lastName, int yearOfBirth, String phoneNumber, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.yearOfBirth = yearOfBirth;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = idCounter++;
    }

    public int getId() {
        return id;
    }
    public String toString() {
        return "#" + id + " " + firstName + " " + middleName + " " + lastName + " year of birth: " + yearOfBirth +
                " phone: " + phoneNumber + " email: " + email;
    }
}
