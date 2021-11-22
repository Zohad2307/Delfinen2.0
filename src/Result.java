public class Result {
    String mail;
    String disciplin;
    double time;
    String date;
    Result(String mail, String disciplin, double time, String date){
        this.mail = mail;
        this.disciplin = disciplin;
        this.time = time;
        this.date = date;
    }
    String toFile(){
        return  "Træning " + mail + " " + disciplin + " " + time + " " + date;
    }
}
