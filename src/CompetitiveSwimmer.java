public class CompetitiveSwimmer extends Member{
    private boolean isCompetitive = true;
    private Result trainingButterfly, trainingFrontcrawl, trainingBackstroke, trainingBreaststroke = null;
    private  CompetitiveResult competitiveButterfly, competitiveFrontcrawl, competitiveBackstroke, competitiveBreaststroke = null;
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
