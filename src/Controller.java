import java.util.ArrayList;

public class Controller {
    DataBase db = new DataBase();

    public Controller() {

    }
    public void start() {
        db.loadFiles();
    }

    public Member createMember(String firstName, String middleName, String lastName, int yearOfBirth, String phone,
                               String email, boolean isActive, boolean isCompetitiveSwimmer) {
        return db.createMember(firstName,middleName,lastName,yearOfBirth,phone,email,isActive,isCompetitiveSwimmer);
    }

    public void saveFiles() {
        db.saveFiles();
    }

    public Member[] getCompetitiveSwimmers() {
        return db.getCompetitiveSwimmers();
    }

    public Result registerResult(int personNumber, int disciplin, String date, int time, String tournament, boolean isCompetitiveResult) {
        return db.registerResult(personNumber, disciplin, date, time, tournament, isCompetitiveResult);
    }

    public String[] getTopFive(int swimmingDiscipline) {
       return db.getTopFive(swimmingDiscipline);


    }
}
