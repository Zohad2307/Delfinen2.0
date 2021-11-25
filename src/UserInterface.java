import java.util.Scanner;

public class UserInterface {
    private Controller controller;
    Scanner input = new Scanner(System.in);

    public UserInterface(Controller controller) {
        this.controller = controller;

    }

    public void run() {
        Menu menu = new Menu("Velkommen til Delfinen",
                "Vælg en af ovennævnte muligheder", new String[]{"1. Opret medlem",
                "2. Se topsvømmere", "3. Registrer resultat", "4. Se Regnskab", "5. Se menuoversigt", "9. Luk program"});

        boolean isRunning = true;

        menu.printMenu();
        while (isRunning) {
            switch (menu.readChoice()) {
                case 1:
                    createMember();
                    break;
                case 2:
                    getTopFive();
                    break;
                case 3:
                    registerResult();
                    break;
                case 4:
                    getPaymentOverview();
                    break;
                case 5:
                    menu.printMenu();
                    break;
                case 9:
                    isRunning = false;
                    controller.saveFiles();
                    //Eventuelt gemme det man på nuværende tidspunkt har lavet
                    break;
                default:
                    System.out.println("Dit valg er ikke gyldigt.");

            }
        }
    }

    private void getPaymentOverview() {
       Menu menu = new Menu("Se regnskab","Vælg en af ovennævnte:",new String[]{"1. Se forventet indbetaling pr. år","2. Se restanceoversigt"});
       menu.printMenu();
       int choice = input.nextInt();

    }

    private void getTopFive() {
        //Lav det med menu klassen
        System.out.println("Vælg svømmedisciplin\n1. Crawl\n2. Rygcrawl\n3. Butterfly\n4. Brystsvømning");
        int swimmingDiscipline = input.nextInt();

        int id = 1;
        if (controller.getTopFive(swimmingDiscipline).length == 0) {
            System.out.println("Der er ikke nogle konkurrencesvømmere i den valgte disciplin");
        }
        else {
            for (String string : controller.getTopFive(swimmingDiscipline)) {
                System.out.println(id + ". " + string);
                id++;

            }
        }
    }

    private void registerResult() {
        String tournament = "";
        boolean isCompetitiveResult;
        System.out.println("Indtast id på den person der skal have registreret en tid");
        for (Member member : controller.getCompetitiveSwimmers()) {
            System.out.println(member);
        }
        int personNumber = input.nextInt();
        //Lav det med menu klassen
        System.out.println("Hvilken svømmedisciplin skal registreres?\n1. Crawl\n2. Rygcrawl\n3. Butterfly\n4. Brystsvømning");
        int disciplin = input.nextInt();
        System.out.println("Vil du registrere en konkurrencetid eller en træningstid? k/t");
        String choice = input.next();
        if (choice.equals("k")) {
            isCompetitiveResult = true;
        } else {
            isCompetitiveResult = false;
        }
        System.out.println("Indtast den dato for hvornår tiden er svømmet");
        String date = input.next();
        System.out.println("Indtast din tid i sekunder");
        int time = input.nextInt();
        if (choice.equals("k")) {
            System.out.println("Indtast navnet på turneringen");
            tournament = input.next();
        }
        Result result = controller.registerResult(personNumber, disciplin, date, time, tournament, isCompetitiveResult);
        if (result == null) {
            System.out.println("Du har prøvet at registrere en tid som er værre end en allerede ekstisterende tid");
        } else {
            System.out.println("Du har registreret resultatet: " + result);
        }
    }

    private void createMember() {
        boolean isActive;
        boolean isCompetitiveSwimmer;
        System.out.println("Indtast fornavn: ");
        String firstName = input.nextLine();
        System.out.println("Indtast mellemnavn (Blank hvis der intet mellemnavn er): ");
        String middleName = input.nextLine();
        System.out.println("Indtast efternavn: ");
        String lastName = input.nextLine();
        System.out.println("Indtast fødselsår: ");
        int yearOfBirth = input.nextInt();
        input.nextLine();
        System.out.println("Indtast telefonnummer: ");
        String phone = input.nextLine();
        System.out.println("Indtast email: ");
        String email = input.nextLine();
        System.out.println("Er du aktiv medlem? j/n");
        String aktivEllerPassiv = input.nextLine();
        if (aktivEllerPassiv.equals("j")) {
            isActive = true;
        } else {
            isActive = false;
        }
        System.out.println("Er du konkurrencesvømmer? j/n");
        String membershipType = input.nextLine();
        if (membershipType.equalsIgnoreCase("j")) {
            isCompetitiveSwimmer = true;
        } else {
            isCompetitiveSwimmer = false;
        }
        Member member = controller.createMember(firstName, middleName, lastName, yearOfBirth, phone, email, isActive, isCompetitiveSwimmer);
        System.out.println("Har oprettet nyt medlem: " + member);
    }
}
