public class Result implements Comparable<Result> {
    private String mail;
    private String disciplin;
    private int time;
    private String date;
    private boolean isCompetitiveResult = false;

    Result(String mail, String disciplin, int time, String date) {
        this.mail = mail;
        this.disciplin = disciplin;
        this.time = time;
        this.date = date;
    }

    String toFile() {
        return isCompetitiveResult + " " + mail + " " + disciplin + " " + time + " " + date;
    }
    public int getTime() {
        return time;
    }

    public String getMail() {
        return mail;
    }

    public String getDiscipline() {
        return disciplin;
    }

    public String getDate() {
        return date;
    }

    public boolean isCompetitiveResult() {
        return isCompetitiveResult;
    }

    @Override
    public int compareTo(Result o) {
        int number;
        if (this.time > o.getTime()){
            number = 1;
        }
        else if (this.time < o.getTime()) {
            number = -1;
        }
        else {
            number = 0;
        }
        return number;
    }
}
