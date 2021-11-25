public class CompetitiveResult extends Result {
    private String nameOfCompetition;
    private boolean isCompetitiveResult = true;

    CompetitiveResult(String mail, String disciplin, int time, String date, String nameOfCompetition) {
        super(mail, disciplin, time, date);
        this.nameOfCompetition = nameOfCompetition;
    }

    String toFile() {
        return isCompetitiveResult + ";" + getMail() + ";" + getDiscipline() + ";" + getTime() +
                ";" + getDate() + ";" + nameOfCompetition + ";";
    }

}
