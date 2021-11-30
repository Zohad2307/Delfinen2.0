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
    private final String filenameResults = "src/Results.txt";
    private final String filenameUserInfo = "src/Userinfo.txt";
    private final File fileUserInfo = new File(filenameUserInfo);
    private final File fileResults = new File(filenameResults);
    private ArrayList<Member> members = new ArrayList<>();
    private ArrayList<Result> results = new ArrayList<>();

    // opretter et member-objekt ud fra kriteriet om man er konkurrencesvømmer
    // eller ej og tilføjer det til members-listen
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

    // Gemmer alle member-informationerne i en tekstfil. Sender en custom exception til UserInterface, hvor den bliver
    // skrevet ud.
    private void saveUserinfo() throws CSVFileWriteException {
        try {
            PrintStream printStream = new PrintStream(fileUserInfo);
            for (Member member : members) {
                printStream.println(member.toFile());
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileWriteException("Kunne ikke skrive til filen: " + filenameUserInfo, e);
        }
    }

    // Gemmer alle result-informationerne i en tekstfil. Sender en custom exception til UserInterface, hvor den bliver
    // skrevet ud.
    public void saveResult() throws CSVFileWriteException {
        try {
            PrintStream printStream = new PrintStream(fileResults);
            for (Result result : results) {
                printStream.println(result.toFile());
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileWriteException("Kunne ikke skrive til filen: " + filenameResults, e);
        }
    }

    public void loadFiles() {
        loadUserInfo();
        loadResults();
    }

    /* henter alle result-informationerne fra en tekstfil og opretter results-objekter,
       som derefter bliver tilføjet til results-listen.
       Sender en custom exception til UserInterface, hvor den bliver skrevet ud.
     */
    public void loadResults() throws CSVFileReadException {
        try {
            Scanner fileReader = new Scanner(fileResults);
            fileReader.useDelimiter(";");
            Result result;
            while (fileReader.hasNext()) {
                if (fileReader.nextBoolean()) {
                    result = new CompetitiveResult(fileReader.next(), fileReader.next(), fileReader.nextInt(),
                            fileReader.next(), fileReader.next());
                    fileReader.nextLine();
                } else {
                    result = new Result(fileReader.next(), fileReader.next(), fileReader.nextInt(), fileReader.next());
                    fileReader.nextLine();
                }
                results.add(result);
            }
        } catch (FileNotFoundException e) {
            throw new CSVFileReadException("Kunne ikke læse filen: " + filenameResults, e);
        }
    }

    /* henter alle member-informationerne fra en tekstfil og opretter member-objekter,
       som derefter bliver tilføjet til members-listen.
       Sender en custom exception til UserInterface, hvor den bliver skrevet ud.
     */
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
            throw new CSVFileReadException("Kunne ikke læse filen: " + filenameUserInfo, e);
        }
    }

    // Laver en ny ArrayList, smider alle competitiveSwimmers i. Smider det i et nyt array og returnerer det.
    public Member[] getCompetitiveSwimmers() {
        ArrayList<Member> competitiveMembers = new ArrayList<>();
        for (Member member : members) {
            if (member instanceof CompetitiveSwimmer) {
                competitiveMembers.add(member);
            }
        }
        // Laver et Array med samme størrelse som det array man kalder metoden på,
        // og smider elementerne over i det nye array
        Member[] competitiveSwimmers = competitiveMembers.toArray(new Member[0]);
        return competitiveSwimmers;
    }
    // Registrerer et resultat
    public Result registerResult(int personNumber, int disciplin, String date, int time,
                                 String tournament, boolean isCompetitiveResult) {
        String disc;
        // Tjekker hvilken disciplin der skal registreres en tid i
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
        // Laver et Member array indeholdende CompetitiveSwimmers
        Member[] competitiveSwimmers = getCompetitiveSwimmers();

        // Looper igennem members-listen. Hvis Id = personNumber, så sætter den mail til memberets mail.
        String mail = null;
        for (Member member : competitiveSwimmers) {
            if (member.getId() == personNumber) {
                mail = member.getEmail();
            }
        }
        if (mail == null) {
            return null;
        }
        // Opretter et result-objekt afhængig af information, der er blevet sendt med.
        if (isCompetitiveResult) {
            result = new CompetitiveResult(mail, disc, time, date, tournament);
        } else {
            result = new Result(mail, disc, time, date);
        }
        // Hvis der ikke allerede er et resultat, så tilføjer den bare det registrede resultat.
        // Hvis der bliver fundet et resultat, sammenligner den tiderne og beholder det bedste resultat.
        // returnerer til sidst resultatet, så det kan bliver skrevet ud i UserInterfacet.
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
    // Leder efter et resultat ud fra valgte mail og disciplin og returnerer til sidste resultat eller null
    public Result findResult(String mail, String discipline) {
        Result foundResult = null;
        for (Result result : results) {
            if (mail.equalsIgnoreCase(result.getMail()) && discipline.equalsIgnoreCase(result.getDiscipline())) {
                foundResult = result;
            }
        }
        return foundResult;
    }
    // Returnerer member eller null afhængig af den valgte mail
    public Member findMember(String email) {
        Member foundMember = null;
        for (Member member : members) {
            if (email.equalsIgnoreCase(member.getEmail())) {
                foundMember = member;
            }
        }
        return foundMember;
    }

    //TODO Hvor vil det være bedst at afgøre hvilken disciplin det er? I UI eller Database?

    // Returnerer en liste med topsvømmere inde for den valgte disciplin
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
        // looper igennem results-listen. Hvis disciplin og valgte aldersgruppe matcher, bliver de smidt i en ny liste.
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
        // Sorterer resultaterne vha. compareTo metoden i members, som sammenligner tid.
        Collections.sort(swimmerResults);
        // Laver en ny ArrayList som indeholder strings
        ArrayList<String> swimmerInformation = new ArrayList<>();

        // Sammensætter strings med nogle forskellige informationer og tilføjer dem til listen ovenover.
        for (Result result : swimmerResults) {
            String fullName = findMember(result.getMail()).getFullName();
            String information = fullName + " Tid: " + result.getTime();
            swimmerInformation.add(information);
        }
        // Erklærer et array som bliver initialiseret nedenunder.
        String[] topFiveSwimmers;

        if (swimmerInformation.size() > 5) {
            // Fjerner alle elementer fra og med index 5 og op
            swimmerInformation.subList(5, swimmerInformation.size()).clear();
        }
        // Smider elementerne over i et String Array

        return swimmerInformation.toArray(new String[0]);
    }

    // TODO Spørg hvorfor det er bedst at sende et Array i stedet for f.eks. et objekt eller en ArrayList.
    // Looper igennem members og tilføjer alle der members med hasPaid = false til en ny liste og returnerer den.
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
    // Lægger alle members kontigentbetalinger sammen og returnerer summen.
    public int getExpectedPayments() {
        int payment = 0;
        for (Member member : members) {
            payment = payment + member.getPayment();
        }
        return payment;
    }
}

