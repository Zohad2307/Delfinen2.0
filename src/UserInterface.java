import java.util.Scanner;

public class UserInterface {
    private Controller controller;
    Scanner input = new Scanner(System.in);

   public UserInterface(Controller controller) {
        this.controller = controller;

    }
    public void run(){
        Menu menu = new Menu("Velkommen til Delfinen",
                "Vælg en af ovennævnte muligheder", new String[]{"1. Opret medlem",
                "2. Se topsvømmere", "3. Registrer resultat", "4. Se Regnskab", "5. Se menuoversigt", "9. Luk program"});

        boolean isRunning = true;


        menu.printMenu();
        while(isRunning) {
            switch(menu.readChoice()) {
                case 1:
                    createMember();
                    break;
                case 2:
                    //controller.getTopFive();
                    break;
                case 3:
                    //controller.registerResult();
                    break;
                case 4:
                    //controller.getRegnskab();
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

    private void createMember() {
        boolean isActive;
        boolean isCompetitiveSwimmer;
        System.out.println("Indtast fornavn: ");
        String firstName = input.nextLine();
        System.out.println("Indtast mellemnavn: ");
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
        if(aktivEllerPassiv.equals("j")){
            isActive = true;
        }else{
            isActive = false;
        }
        System.out.println("Er du konkurrencesvømmer? j/n");
        String membershipType = input.nextLine();
        if(membershipType.equalsIgnoreCase("j")){
            isCompetitiveSwimmer = true;
        }else{
            isCompetitiveSwimmer = false;
        }
        Member member = controller.createMember(firstName,middleName,lastName,yearOfBirth,phone,email,isActive,isCompetitiveSwimmer);
        System.out.println("Har oprettet nyt medlem: " + member);
    }
}
