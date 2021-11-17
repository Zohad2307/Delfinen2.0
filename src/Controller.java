public class Controller {
    DataBase db = new DataBase();

    public Controller() {

    }
    public void start() {
        
    }

    public Member createMember(String firstName, String middleName, String lastName, int yearOfBirth, String phone, String email, boolean isActive, boolean isCompetitiveSwimmer) {
        return db.createMember(firstName,middleName,lastName,yearOfBirth,phone,email,isActive,isCompetitiveSwimmer);
    }

}
