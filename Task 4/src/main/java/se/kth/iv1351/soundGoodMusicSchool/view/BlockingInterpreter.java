package main.java.se.kth.iv1351.soundGoodMusicSchool.view;

import java.util.List;
import java.util.Scanner;

import main.java.se.kth.iv1351.soundGoodMusicSchool.controller.Controller;

public class BlockingInterpreter {
    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private Controller controller;
    private boolean keepReceivingCmds = false;

    public BlockingInterpreter(Controller controller) {
        this.controller = controller;
    }

    public void stop() {
        keepReceivingCmds = false;
    }

    public void handleCmds() {
        keepReceivingCmds = true;
        while (keepReceivingCmds) {
            try {
                CmdLine cmdLine = new CmdLine(readNextLine());
                switch (cmdLine.getCmd()) {
                    case HELP:
                        for (Command command : Command.values()) {
                            if (command == Command.ILLEGAL_COMMAND) {
                                continue;
                            }
                            System.out.println(command.toString().toLowerCase());
                        }
                        break;
                    case QUIT:
                        System.out.println("Quitting...");
                        keepReceivingCmds = false;
                        break;
                    case RENT:
                        controller.rentInstrument(cmdLine.getParameter(0), Integer.parseInt(cmdLine.getParameter(1)));
                        break;
                    case TERMINATE:
                        controller.terminateRental(Integer.parseInt(cmdLine.getParameter(0)));
                        break;
                    case LIST:
                        controller.listInstrument();
                        break;
                    default:
                        System.out.println("illegal command");
                }
            } catch (Exception e) {
                System.out.println("Operation failed");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
