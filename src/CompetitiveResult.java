public class CompetitiveResult extends Result{
    String nameOfCompetition;
    CompetitiveResult(String mail, String disciplin, double time, String date, String nameOfCompetition){
        super(mail,disciplin,time,date);
        this.nameOfCompetition = nameOfCompetition;
    }
    String toFile(){
        return  "Konkurrence " + mail + " " + disciplin + " " + time + " " + date + " " + nameOfCompetition;
    }

}
