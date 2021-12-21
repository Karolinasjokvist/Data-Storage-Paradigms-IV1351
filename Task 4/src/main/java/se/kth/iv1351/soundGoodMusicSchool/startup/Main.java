package main.java.se.kth.iv1351.soundGoodMusicSchool.startup;

import main.java.se.kth.iv1351.soundGoodMusicSchool.view.BlockingInterpreter;
import main.java.se.kth.iv1351.soundGoodMusicSchool.controller.Controller;

public class Main {
    public static void main(String[] args) {
        try {
            new BlockingInterpreter(new Controller()).handleCmds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
