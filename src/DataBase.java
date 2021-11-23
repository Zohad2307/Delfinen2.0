import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class DataBase {
    final File fileUserInfo = new File("src/Userinfo.txt");
    final File fileResults = new File("src/Results.txt");
    ArrayList<Member> members = new ArrayList<>();
    ArrayList<Result> results = new ArrayList<>();


    public Member createMember(String firstName, String middleName, String lastName,
                               int yearOfBirth, String phone, String email, boolean isActive,
                               boolean isCompetitiveSwimmer) {
        Member member;
        if (isCompetitiveSwimmer) {
            member = new CompetitiveSwimmer(firstName, middleName, lastName, yearOfBirth, phone, email, isActive);
        } else {
            member = new CasualSwimmer(firstName, middleName, lastName, yearOfBirth, phone, email, isActive);
        }
        members.add(member);
        return member;
    }

    public void saveFiles() {
        try {
            PrintStream printStream = new PrintStream(fileUserInfo);
            for (Member member : members) {
                printStream.println(member.toFile());
            }
        } catch (Exception e) {
            System.out.println("Denne fil findes ikke!!");
        }
        saveResult();
    }

    public void saveResult() {
        try {
            PrintStream printStream = new PrintStream(fileResults);
            for (Result result : results) {
                printStream.println(result.toFile());
            }
        } catch (Exception e) {
            System.out.println("Denne fil findes ikke!!");
        }
    }

    public void loadFiles() {
        try {
            Scanner fileReader = new Scanner(fileUserInfo);
            while (fileReader.hasNext()) {
                createMember(fileReader.next(), fileReader.next(), fileReader.next(),
                        fileReader.nextInt(), fileReader.next(), fileReader.next(), fileReader.nextBoolean(), fileReader.nextBoolean());
            }
        } catch (Exception e) {
            System.out.println("Filen findes ikke");
            System.out.println("Personer kunne ikke loades");
        }
        loadResults();

    }

    public Member[] getCompetitiveSwimmers() {
        ArrayList<Member> competitiveMembers = new ArrayList<>();
        for (Member member : members) {
            if (member instanceof CompetitiveSwimmer) {
                competitiveMembers.add(member);
            }
        }
        // TODO Skal finde ud af, hvad der foregår her og skrive det som en kommentar
        Member[] competitiveSwimmers = competitiveMembers.toArray(new Member[0]);
        return competitiveSwimmers;
    }

    //public void registerCompetitiveResult(int personNumber, int disciplin, String date, int  )
    // TODO returnerer noget så man kan printe ud, at der allerede var en bedre tid og tiden man indtastede ikke blev tilføjet.
    public void registerResult(int personNumber, int disciplin, String date, int time, String tournament, boolean isCompetitiveResult) {
        String disc;
        if (disciplin == 1) {
            disc = "Crawl";
        } else if (disciplin == 2) {
            disc = "Rygcrawl";
        } else if (disciplin == 3) {
            disc = "Butterfly";
        } else {
            disc = "Brystsvømning";
        }
        Result result;
        Member[] competitiveSwimmers = getCompetitiveSwimmers();
        String mail = competitiveSwimmers[personNumber - 1].getEmail();
        if (isCompetitiveResult) {
            result = new CompetitiveResult(mail, disc, time, date, tournament);
        } else {
            result = new Result(mail, disc, time, date);
        }
        Result foundResult = findResult(mail, disc);
        if (foundResult == null) {
            results.add(result);
        } else {
            if (result.compareTo(foundResult) < 0 || result.compareTo(foundResult) == 0) {
                results.add(result);
                results.remove(foundResult);
            }
        }
    }

    public Result findResult(String mail, String discipline) {
        Result foundResult = null;
        for (Result result : results) {
            if (mail.equalsIgnoreCase(result.getMail()) && discipline.equalsIgnoreCase(result.getDiscipline())) {
                foundResult = result;
            }
        }
        return foundResult;
    }

    public Member findMember(String mail) {
        Member foundMember = null;
        for (Member member : members) {
            if (mail.equalsIgnoreCase(member.getEmail())) {
                foundMember = member;
            }

        }
        return foundMember;

    }

    public void loadResults() {
        try {
            Scanner fileReader = new Scanner(fileResults);
            Result result;
            while (fileReader.hasNext()) {
                if (fileReader.nextBoolean()) {
                    result = new CompetitiveResult(fileReader.next(), fileReader.next(), fileReader.nextInt(), fileReader.next(), fileReader.next());
                } else {
                    result = new Result(fileReader.next(), fileReader.next(), fileReader.nextInt(), fileReader.next());
                }
                results.add(result);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fil findes ikke");
            System.out.println("Resultat kunne ikke loades");
        }
    }
        // TODO Sørge for at det kun er top 5, der kommer med i listen.
    public ArrayList<String> getTopFive(int swimmingDiscipline) {
        ArrayList<Result> competitiveSwimmers = new ArrayList<>();
        String discpline = null;
        switch (swimmingDiscipline) {
            case 1:
                discpline = "Crawl";
                break;
            case 2:
                discpline = "Rygcrawl";
                break;
            case 3:
                discpline = "Butterfly";
                break;
            case 4:
                discpline = "Brystsvømning";
                break;

        }
        for (Result result : results) {
            if (result.getDiscipline().equalsIgnoreCase(discpline)) {
                competitiveSwimmers.add(result);
            }
        }
        Collections.sort(competitiveSwimmers);
        ArrayList<String> swimmerInformation = new ArrayList<>();
        for (int i = 0; i < competitiveSwimmers.size(); i++) {
            String fullName = findMember(competitiveSwimmers.get(i).getMail()).getFullName();
            String information = fullName + " " + "Tid: " + competitiveSwimmers.get(i).getTime();
            swimmerInformation.add(information);
        }
        return swimmerInformation;

        // tage alle resultater ud fra en svømmedisciplin
        // sorterer dem og så tage top 5 og smide i en nyt array og returnerer arrayet

    }
}

