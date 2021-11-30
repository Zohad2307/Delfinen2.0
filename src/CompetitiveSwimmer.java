public class CompetitiveSwimmer extends Member {

    public CompetitiveSwimmer(String firstName, String middleName, String lastName, int yearOfBirth,
                              String phoneNumber, String email, boolean isActive, boolean hasPayed) {
        super(firstName, middleName, lastName, yearOfBirth, phoneNumber, email, isActive, hasPayed);
        setIsCompetitive(true);
    }
}