public class Application {



    public static void main(String[] args){
        Application application = new Application();
        application.run();
    }

    private void run() {
        Controller controller = new Controller();
        UserInterface ui = new UserInterface(controller);
        //controller.start(); Som kører program og loader filerne ind
        controller.start();
        ui.run();


    }
} 
