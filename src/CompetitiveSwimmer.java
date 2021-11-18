public class CompetitiveSwimmer extends Member{
    private double butterfly, frontcrawl, backstroke, breaststroke;
    public CompetitiveSwimmer(String firstName, String middleName, String lastName, int yearOfBirth,
                              String phoneNumber, String email, boolean isActive){
        super(firstName, middleName, lastName, yearOfBirth, phoneNumber, email, isActive);
        setIsCompetitive(true);
    }
    public String toString() {
        return super.toString();  //+  de forskellige tider
    }

    @Override
    public String toFile() {
        return super.toFile();
    }
}
