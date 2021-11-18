import java.io.PrintStream;
import java.util.ArrayList;

public class DataBase {
    final String fileUserInfo = "src/Userinfo.txt";
    ArrayList<Member> members = new ArrayList<>();




    public Member createMember(String firstName, String middleName, String lastName,
                               int yearOfBirth, String phone, String email, boolean isActive, boolean isCompetitiveSwimmer) {
        Member member;
        if(isCompetitiveSwimmer) {
            member = new CompetitiveSwimmer(firstName, middleName, lastName, yearOfBirth, phone, email, isActive);
        } else{
            member = new CasualSwimmer(firstName, middleName, lastName, yearOfBirth, phone, email, isActive);
        }
        members.add(member);
        return member;
    }

    public void saveFiles() {
        try {
            PrintStream printStream = new PrintStream(fileUserInfo);
            for (Member member:members) {
                printStream.println(member.toFile());
            }
        }
        catch(Exception e){
            System.out.println("Denne fil findes ikke!!");
        }
    }
}
