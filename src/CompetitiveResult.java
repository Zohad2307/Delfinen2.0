public class CompetitiveResult extends Result{
    String nameOfCompetition;
    boolean isCompetitiveResult = true;
    CompetitiveResult(String mail, String disciplin, int time, String date, String nameOfCompetition){
        super(mail,disciplin,time,date);
        this.nameOfCompetition = nameOfCompetition;
    }
    String toFile(){
        return  isCompetitiveResult + " " + mail + " " + disciplin + " " + time + " " + date + " " + nameOfCompetition;
    }

}
