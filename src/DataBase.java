import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    final File fileUserInfo = new File("src/Userinfo.txt");
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
        System.out.println(members);
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
    public void loadFiles(){
        try{
           Scanner fileReader = new Scanner(fileUserInfo);
           while (fileReader.hasNext()){
               createMember(fileReader.next(),fileReader.next(),fileReader.next(),
                       fileReader.nextInt(),fileReader.next(),fileReader.next(),fileReader.nextBoolean(),fileReader.nextBoolean());
           }
        }catch(Exception e){
            System.out.println("Filen findes ikke");
        }
    }
}
