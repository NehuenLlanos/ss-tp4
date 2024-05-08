package ar.edu.itba.ss.tp4;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No arguments provided");
            System.exit(1);
        }

        switch (args[0]) {
            case "oscillator" -> OscillatorMain.run(args);
            case "jupiter" -> JupiterMissionMain.run(args);
            case "mars" -> MarsMissionMain.run(args);
            default -> {
                System.err.println("Invalid argument");
                System.exit(1);
            }
        }
    }
}
