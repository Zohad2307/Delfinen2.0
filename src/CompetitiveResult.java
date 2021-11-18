public class CompetitiveResult extends Result{
    String nameOfCompetition;
    CompetitiveResult(String name, double time, String date, String nameOfCompetition){
        super(name,time,date);
        this.nameOfCompetition = nameOfCompetition;
    }

}
