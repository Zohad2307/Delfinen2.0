public class Main {



    public static void main(String[] args){
        Main main = new Main();
        main.run();
    }

    private void run() {
        Controller controller = new Controller();
        UserInterface ui = new UserInterface(controller);
        //controller.start();    Som kører program og loader filerne ind
        ui.run();
    }
}
