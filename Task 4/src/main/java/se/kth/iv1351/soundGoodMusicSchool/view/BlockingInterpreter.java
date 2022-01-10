package main.java.se.kth.iv1351.soundGoodMusicSchool.view;

import java.util.List;
import java.util.Scanner;

import main.java.se.kth.iv1351.soundGoodMusicSchool.controller.Controller;
import main.java.se.kth.iv1351.soundGoodMusicSchool.model.InstrumentDTO;

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

                    // InstrumentID and StudentID
                    case RENT:
                        controller.rentInstrument(Integer.parseInt(cmdLine.getParameter(0)), Integer.parseInt(cmdLine.getParameter(1)));
                        break;
                    
                    // Rental id is the only parameter
                    case TERMINATE:
                        controller.terminateRental(Integer.parseInt(cmdLine.getParameter(0)));
                        break;
                    
                    // list instrument using type
                    case LIST:
                        List <? extends InstrumentDTO> instruments = null;
                        instruments = controller.listInstrument(cmdLine.getParameter(0));
                        System.out.println(instruments);
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
