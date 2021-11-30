import java.util.ArrayList;

public class Controller {
    DataBase db = new DataBase();

    public Controller() {

    }
    public void start() {
        db.loadFiles();
    }

    public Member createMember(String firstName, String middleName, String lastName, int yearOfBirth, String phone,
                               String email, boolean isActive, boolean isCompetitiveSwimmer, boolean hasPayed) {
        return db.createMember(firstName,middleName,lastName,yearOfBirth,phone,email,isActive,isCompetitiveSwimmer, hasPayed);
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

    public String[] getTopFive(int swimmingDiscipline, boolean isJuniorSwimmer) {
       return db.showTopFive(swimmingDiscipline, isJuniorSwimmer);

    }

    public ArrayList<String> showMembersInDebt() {
        return db.showMembersInDebt();
    }

    public int getExpectedPayments() {
        return db.getExpectedPayments();
    }
}
