public class UserInterface {
    private Controller controller;

    UserInterface(Controller controller) {
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
                    //controller.createMember();
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
                    //Eventuelt gemme det man på nuværende tidspunkt har lavet
                    break;
                default:
                    System.out.println("Dit valg er ikke gyldigt.");

            }
        }
    }
}
