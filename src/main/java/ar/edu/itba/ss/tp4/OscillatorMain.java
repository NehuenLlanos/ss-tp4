package ar.edu.itba.ss.tp4;

import ar.edu.itba.ss.tp4.integrator.*;
import ar.edu.itba.ss.tp4.utils.Constants;
import ar.edu.itba.ss.tp4.utils.StateVariables;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.function.BiFunction;

public class OscillatorMain {
    public static void run(String[] args) {
        BiFunction<Double, Double, Double> acceleration = (r,v) -> (-1 * Constants.K * r - 1 * Constants.GAMMA * v) / Constants.M;
        double r0 = 1.0;
        double v0 = - 1 * Constants.A * Constants.GAMMA / (2 * Constants.M);

        for (int i = 0, j = 6; j >= 2; j--, i++) {
            double dt = Math.pow(10, -j);
            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(String.format("verlet_%d.txt", i)),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )
            ) {
                IntegratorMethod originalVerlet = new NewVerlet(
                        dt,
                        acceleration,
                        r0,
                        v0
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

            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(String.format("original_verlet_%d.txt", i)),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )
            ) {
                IntegratorMethod verlet = new OriginalVerlet(
                        dt,
                        acceleration,
                        r0,
                        v0
                );

                Iterator<StateVariables> it = verlet.iterator();
                StateVariables vars;
                do {
                    vars = it.next();
                    writer.write(vars.time() + " " + vars.position() + " " + vars.velocity());
                    writer.newLine();
                } while (vars.time() <= 5);
            } catch (IOException e) {
                throw new RuntimeException("Could not write files.");
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(String.format("beeman_%d.txt", i)),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )
            ) {
                IntegratorMethod beeman = new Beeman(
                        dt,
                        acceleration,
                        r0,
                        v0
                );

                Iterator<StateVariables> it = beeman.iterator();
                StateVariables vars;
                do {
                    vars = it.next();
                    writer.write(vars.time() + " " + vars.position() + " " + vars.velocity());
                    writer.newLine();
                } while (vars.time() <= 5);
            } catch (IOException e) {
                throw new RuntimeException("Could not write files.");
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(String.format("gear_predictor_%d.txt", i)),
                    StandardOpenOption.WRITE,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )
            ) {
                double initialR2 = acceleration.apply(r0, v0);
                double initialR3 = acceleration.apply(v0, initialR2);
                double initialR4 = acceleration.apply(initialR2, initialR3);
                double initialR5 = acceleration.apply(initialR3, initialR4);

                IntegratorMethod gearPredictor = new GearPredictor(
                        dt,
                        acceleration,
                        r0,
                        v0,
                        initialR2,
                        initialR3,
                        initialR4,
                        initialR5
                );

                Iterator<StateVariables> it = gearPredictor.iterator();
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
}
