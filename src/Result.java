public class Result {
    String mail;
    String disciplin;
    int time;
    String date;
    boolean isCompetitiveResult = false;
    Result(String mail, String disciplin, int time, String date){
        this.mail = mail;
        this.disciplin = disciplin;
        this.time = time;
        this.date = date;
    }
    String toFile(){
        return isCompetitiveResult + " " + mail + " " + disciplin + " " + time + " " + date;
    }
}
