package ar.edu.itba.ss.tp4;

import ar.edu.itba.ss.tp4.integrator.IntegratorMethod;
import ar.edu.itba.ss.tp4.integrator.OriginalVerlet;
import ar.edu.itba.ss.tp4.utils.Constants;
import ar.edu.itba.ss.tp4.utils.StateVariables;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
        BiFunction<Double, Double, Double> acceleration = (r,v) -> (-1 * Constants.K * r - 1 * Constants.GAMMA * v) / Constants.M;

        try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get("verlet.txt"),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )
        ) {
            IntegratorMethod originalVerlet = new OriginalVerlet(
                    0.001,
                    acceleration,
                    1,
                    -100 / 140.0
            );

            Iterator<StateVariables> it = originalVerlet.iterator();
            StateVariables vars;
            do {
                vars = it.next();
                writer.write(vars.time() + " " + vars.position() + " " + vars.velocity());
                writer.newLine();
            } while (vars.time() <= 5);
        } catch (IOException e) {
            throw new RuntimeException("Could not write files.");
        }
    }
}
