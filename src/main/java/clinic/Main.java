package clinic;

import clinic.ui.TextUI;

public class Main {

    public static void main(String[] args) {
        //System.out.println("teste1");

        try {
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar: "+e.getMessage());
        }

    }
}
