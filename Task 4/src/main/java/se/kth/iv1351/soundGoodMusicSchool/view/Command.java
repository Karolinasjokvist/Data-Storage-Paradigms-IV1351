package main.java.se.kth.iv1351.soundGoodMusicSchool.view;

public enum Command {
    /**
     * The command to list all instruments.
     */
    LIST,
    /**
     * Command to rent an instrument.
     */
    RENT,
    /**
     * Command to terminate a rental of an instrument.
     */
    TERMINATE,
    /**
     * Help command to list all commands.
     */
    HELP,
    /**
     * Command to quit the program.
     */
    QUIT,
    /**
     * If command is not recognized.
     */
    ILLEGAL_COMMAND
}
