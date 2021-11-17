public class CompetitiveSwimmer extends Member{
    private double butterfly, frontcrawl, backstroke, breaststroke;
    public CompetitiveSwimmer(String firstName, String middleName, String lastName, int yearOfBirth,
                              String phoneNumber, String email, boolean isActive, double butterfly,
                              double frontcrawl, double backstroke, double breaststroke){
        super(firstName, middleName, lastName, yearOfBirth, phoneNumber, email, isActive);
        this.butterfly = butterfly;
        this.frontcrawl = frontcrawl;
        this.backstroke = backstroke;
        this.breaststroke = breaststroke;
    }
    public String toString() {
        return super.toString();  //+  de forskellige tider


    }
}
