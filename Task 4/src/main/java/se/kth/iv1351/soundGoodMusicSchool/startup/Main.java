package main.java.se.kth.iv1351.soundGoodMusicSchool.startup;

import main.java.se.kth.iv1351.soundGoodMusicSchool.view.View;
import main.java.se.kth.iv1351.soundGoodMusicSchool.controller.Controller;
import main.java.se.kth.iv1351.soundGoodMusicSchool.integration.*;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new View(controller);
        view.start();
    }
}
