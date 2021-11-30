import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Array;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DataBase {
    final File fileUserInfo = new File("src/Userinfo.txt");
    final File fileResults = new File("src/Results.txt");
    ArrayList<Member> members = new ArrayList<>();
    ArrayList<Result> results = new ArrayList<>();


    public Member createMember(String firstName, String middleName, String lastName,
                               int yearOfBirth, String phone, String email, boolean isActive,
                               boolean isCompetitiveSwimmer, boolean hasPayed) {
        Member member;
        if (isCompetitiveSwimmer) {
            member = new CompetitiveSwimmer(firstName, middleName, lastName, yearOfBirth, phone, email, isActive, hasPayed);
        } else {
            member = new CasualSwimmer(firstName, middleName, lastName, yearOfBirth, phone, email, isActive, hasPayed);
        }
        members.add(member);
        return member;
    }

    public void saveFiles() {
        saveUserinfo();
        saveResult();
    }

    private void saveUserinfo() throws CSVFileWriteException {
        try {
            PrintStream printStream = new PrintStream(fileUserInfo);
            for (Member member : members) {
                printStream.println(member.toFile());
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileWriteException("Kunne ikke skrive til filen", e);
        }
    }


    public void saveResult() throws CSVFileWriteException {
        try {
            PrintStream printStream = new PrintStream(fileResults);
            for (Result result : results) {
                printStream.println(result.toFile());
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileWriteException("Kunne ikke skrive til filen", e);
        }
    }

    public void loadFiles() {
        loadUserInfo();
        loadResults();
    }

    private void loadUserInfo() throws CSVFileReadException {
        try {
            Scanner fileReader = new Scanner(fileUserInfo);
            fileReader.useDelimiter(";");
            while (fileReader.hasNext()) {
                createMember(fileReader.next(), fileReader.next(), fileReader.next(),
                        fileReader.nextInt(), fileReader.next(), fileReader.next(),
                        fileReader.nextBoolean(), fileReader.nextBoolean(), fileReader.nextBoolean());
                fileReader.nextLine();
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileReadException("Kunne ikke læse filen", e);
        }
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
    // TODO Måske overloade metoden så den bliver kaldt alt efter, hvilket indput man sender
    public Result registerResult(int personNumber, int disciplin, String date, int time, String tournament, boolean isCompetitiveResult) {
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
        String mail = null;
        for (Member member : competitiveSwimmers) {
            if (member.getId() == personNumber) {
                mail = member.getEmail();
            }
        }
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
            } else {
                result = null;
            }
        }
        return result;
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

    public void loadResults() throws CSVFileReadException {
        try {
            Scanner fileReader = new Scanner(fileResults);
            fileReader.useDelimiter(";");
            Result result;
            while (fileReader.hasNext()) {
                if (fileReader.nextBoolean()) {
                    result = new CompetitiveResult(fileReader.next(), fileReader.next(), fileReader.nextInt(), fileReader.next(), fileReader.next());
                    fileReader.nextLine();
                } else {
                    result = new Result(fileReader.next(), fileReader.next(), fileReader.nextInt(), fileReader.next());
                    fileReader.nextLine();
                }
                results.add(result);
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileReadException("Kunne ikke læse filen", e);
        }
    }
    //FIXME Rykke Switch over i User-interface,
    // - så man kan printe disciplinen ud sammen med listen af konkurrencesvømmere.

    public String[] showTopFive(int swimmingDiscipline, boolean isJuniorSwimmer) {
        ArrayList<Result> swimmerResults = new ArrayList<>();
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
        int currentYear = Year.now().getValue();
        if (isJuniorSwimmer) {
            for (Result result : results) {
                if (result.getDiscipline().equalsIgnoreCase(discpline)
                        && currentYear - findMember(result.getMail()).getYearOfBirth() < 18) {
                    swimmerResults.add(result);
                }
            }
        } else {
            for (Result result : results) {
                if (result.getDiscipline().equalsIgnoreCase(discpline)
                        && currentYear - findMember(result.getMail()).getYearOfBirth() >= 18) {
                    swimmerResults.add(result);
                }
            }
        }
        // Sorterer efter tid
        Collections.sort(swimmerResults);

        ArrayList<String> swimmerInformation = new ArrayList<>();

        for (Result result : swimmerResults) {
            String fullName = findMember(result.getMail()).getFullName();
            String information = fullName + " Tid: " + result.getTime();
            swimmerInformation.add(information);
        }
        // Erklærer et array som bliver initialiseret nedenunder.
        String[] topFiveSwimmers;

        if (swimmerInformation.size() > 5) {
            // Fjerner alle elementer fra index og med index 5 og op
            swimmerInformation.subList(5, swimmerInformation.size()).clear();
        }
        topFiveSwimmers = new String[swimmerInformation.size()];
        // Smider elementerne over i et String Array
        swimmerInformation.toArray(topFiveSwimmers);

        return topFiveSwimmers;
    }

    public ArrayList<String> showMembersInDebt() {
        ArrayList<String> membersInDebt = new ArrayList<>();
        String line;
        for (Member member : members) {
            if (member.getHasPaid() == false) {
                line = member.toPayment();
                membersInDebt.add(line);
            }
        }
        return membersInDebt;
    }

    public int getExpectedPayments() {
        int payment = 0;
        for (Member member : members) {
            payment = payment + member.getPayment();
        }
        return payment;
    }
}

