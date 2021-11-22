import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DataBase {
    final File fileUserInfo = new File("src/Userinfo.txt");
    final File fileResults = new File("src/Results.txt");
    ArrayList<Member> members = new ArrayList<>();
    ArrayList<Result> results = new ArrayList<>();



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
        saveResult();
    }

    public void saveResult() {
        try {
            PrintStream printStream = new PrintStream(fileResults);
            for (Result result:results) {
                printStream.println(result.toFile());
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
        loadResults();

    }

    public Member[] getCompetitiveSwimmers() {
        ArrayList<Member> competitiveMembers = new ArrayList<>();
        for (Member member: members) {
            if(member instanceof CompetitiveSwimmer){
                competitiveMembers.add(member);
            }
        }
        Member[] competitiveSwimmers = competitiveMembers.toArray(new Member[0]);
        return competitiveSwimmers;
    }
   //public void registerCompetitiveResult(int personNumber, int disciplin, String date, int  )

    public void registerResult(int personNumber, int disciplin, String date, int time, String tournament) {
        String disc;
        if(disciplin == 1){
            disc = "Crawl";
        } else if(disciplin == 2){
            disc = "Rygcrawl";
        } else if(disciplin == 3){
            disc = "Butterfly";
        } else{
            disc = "Brystsvømning";
        }
        Result result;
        Member[] competitiveSwimmers = getCompetitiveSwimmers();
        String mail = competitiveSwimmers[personNumber-1].getEmail();
        if(tournament.equals("")){
            result = new Result(mail,disc,time,date);
        }else{
            result = new CompetitiveResult(mail,disc,time,date,tournament);
        }
        results.add(result);
    }

    public void loadResults(){
        try {
            Scanner fileReader = new Scanner(fileResults);
            Result result = null;
            while(fileReader.hasNext()){
                if(fileReader.next().equals("Træning")){
                    result = new Result(fileReader.next(),fileReader.next(),fileReader.nextDouble(),fileReader.next());
                } else if(fileReader.next().equals("Konkurrence")){
                    result = new CompetitiveResult(fileReader.next(),fileReader.next(),fileReader.nextDouble(),fileReader.next(),fileReader.next());
                }
                results.add(result);
            }
        }catch(FileNotFoundException e){
            System.out.println("Fil findes ikke");
            System.out.println("Resultat kunne ikke loades");
        }
    }
}
