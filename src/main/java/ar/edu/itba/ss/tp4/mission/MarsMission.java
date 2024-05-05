package ar.edu.itba.ss.tp4.mission;

import ar.edu.itba.ss.tp4.utils.Constants;
import ar.edu.itba.ss.tp4.utils.PlanetaryVariables;
import ar.edu.itba.ss.tp4.integrator.GearPredictor;
import ar.edu.itba.ss.tp4.utils.StateVariables;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

public class MarsMission {
    private final Object sun;
    private final Object earth;
    private final Object mars;
    private final double spaceshipMass;
    private final double spaceshipRelativePosition;
    private final double spaceshipTangentialRelativeVelocity;

    private final static long MAX_MISSION_SECONDS = 60 * 60 * 24 * 365 * 2;
    private final static double MIN_DISTANCE_TO_MARS = 130;

    public MarsMission(Object sun, Object earth, Object mars, double spaceshipMass, double spaceshipRelativePosition, double spaceshipTangentialRelativeVelocity) {
        this.sun = sun;
        this.earth = earth;
        this.mars = mars;
        this.spaceshipMass = spaceshipMass;
        this.spaceshipRelativePosition = spaceshipRelativePosition;
        this.spaceshipTangentialRelativeVelocity = spaceshipTangentialRelativeVelocity;
    }

    public void simulate(double dt, long departureSeconds, String filename) {
        Object spaceship = null;

        BiFunction<Double, Double, Double> earthAccelerationX = (rx, vx) ->
                Constants.G *
                        (- sun.getMass() * Math.cos(Math.atan2(earth.getY(), rx)) / (Math.pow(rx - sun.getX(), 2) + Math.pow(earth.getY() - sun.getY(), 2))
                                + mars.getMass() * Math.cos(Math.atan2(earth.getY() - mars.getY(), rx - mars.getX())) / (Math.pow(rx - mars.getX(), 2) + Math.pow(earth.getY() - mars.getY(), 2))
                        );
        BiFunction<Double, Double, Double> earthAccelerationY = (ry, vy) ->
                Constants.G *
                        (- sun.getMass() * Math.sin(Math.atan2(ry, earth.getX())) / (Math.pow(earth.getX() - sun.getX(), 2) + Math.pow(ry - sun.getY(), 2))
                                + mars.getMass() * Math.sin(Math.atan2(ry - mars.getY(), earth.getX() - mars.getX())) / (Math.pow(earth.getX() - mars.getX(), 2) + Math.pow(ry - mars.getY(), 2))
                        );
        BiFunction<Double, Double, Double> marsAccelerationX = (rx, vx) ->
                Constants.G *
                        (- sun.getMass() * Math.cos(Math.atan2(mars.getY(), rx)) / (Math.pow(rx - sun.getX(), 2) + Math.pow(mars.getY() - sun.getY(), 2))
                                - earth.getMass() * Math.cos(Math.atan2(mars.getY() - earth.getY(), rx - earth.getX())) / (Math.pow(rx - earth.getX(), 2) + Math.pow(mars.getY() - earth.getY(), 2))
                        );
        BiFunction<Double, Double, Double> marsAccelerationY = (ry, vy) ->
                Constants.G *
                        (- sun.getMass() * Math.sin(Math.atan2(ry, mars.getX())) / (Math.pow(mars.getX() - sun.getX(), 2) + Math.pow(ry - sun.getY(), 2))
                                - earth.getMass() * Math.sin(Math.atan2(ry - earth.getY(), mars.getX() - earth.getX())) / (Math.pow(mars.getX() - earth.getX(), 2) + Math.pow(ry - earth.getY(), 2))
                        );

        final GearPredictor earthXPredictor = new GearPredictor(dt, earthAccelerationX, earth.getX(), earth.getVx(), earthAccelerationX.apply(earth.getX(), 0.0), 0, 0, 0);
        final Iterator<StateVariables> earthXIterator = earthXPredictor.iterator();
        final GearPredictor earthYPredictor = new GearPredictor(dt, earthAccelerationY, earth.getY(), earth.getVy(), earthAccelerationY.apply(earth.getY(), 0.0), 0, 0, 0);
        final Iterator<StateVariables> earthYIterator = earthYPredictor.iterator();
        final GearPredictor marsXPredictor = new GearPredictor(dt, marsAccelerationX, mars.getX(), mars.getVx(), marsAccelerationX.apply(mars.getX(), 0.0), 0, 0, 0);
        final Iterator<StateVariables> marsXIterator = marsXPredictor.iterator();
        final GearPredictor marsYPredictor = new GearPredictor(dt, marsAccelerationY, mars.getY(), mars.getVy(), marsAccelerationY.apply(mars.getY(), 0.0), 0, 0, 0);
        final Iterator<StateVariables> marsYIterator = marsYPredictor.iterator();
        Iterator<StateVariables> spaceshipXIterator = null;
        Iterator<StateVariables> spaceshipYIterator = null;

        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filename),
                StandardOpenOption.WRITE,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        )) {
            for (long i = 0; i < departureSeconds + MAX_MISSION_SECONDS; i += (long) dt) {
                if (i == departureSeconds) {
                    spaceship = Object.createSpaceshipFromEarth(earth, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
                    final Object finalSpaceship = spaceship;
                    earthAccelerationX = (rx, vx) ->
                            Constants.G *
                                    (- sun.getMass() * Math.cos(Math.atan2(earth.getY(), rx)) / (Math.pow(rx - sun.getX(), 2) + Math.pow(earth.getY() - sun.getY(), 2))
                                            + mars.getMass() * Math.cos(Math.atan2(earth.getY() - mars.getY(), rx - mars.getX())) / (Math.pow(rx - mars.getX(), 2) + Math.pow(earth.getY() - mars.getY(), 2))
                                            + finalSpaceship.getMass() * Math.cos(Math.atan2(earth.getY() - finalSpaceship.getY(), rx - finalSpaceship.getX())) / (Math.pow(rx - finalSpaceship.getX(), 2) + Math.pow(earth.getY() - finalSpaceship.getY(), 2))
                                    );
                    earthAccelerationY = (ry, vy) ->
                            Constants.G *
                                    (- sun.getMass() * Math.sin(Math.atan2(ry, earth.getX())) / (Math.pow(earth.getX() - sun.getX(), 2) + Math.pow(ry - sun.getY(), 2))
                                            + mars.getMass() * Math.sin(Math.atan2(ry - mars.getY(), earth.getX() - mars.getX())) / (Math.pow(earth.getX() - mars.getX(), 2) + Math.pow(ry - mars.getY(), 2))
                                            + finalSpaceship.getMass() * Math.sin(Math.atan2(ry - finalSpaceship.getY(), earth.getX() - finalSpaceship.getX())) / (Math.pow(earth.getX() - finalSpaceship.getX(), 2) + Math.pow(ry - finalSpaceship.getY(), 2))
                                    );
                    marsAccelerationX = (rx, vx) ->
                            Constants.G *
                                    (- sun.getMass() * Math.cos(Math.atan2(mars.getY(), rx)) / (Math.pow(rx - sun.getX(), 2) + Math.pow(mars.getY() - sun.getY(), 2))
                                            - earth.getMass() * Math.cos(Math.atan2(mars.getY() - earth.getY(), rx - earth.getX())) / (Math.pow(rx - earth.getX(), 2) + Math.pow(mars.getY() - earth.getY(), 2))
                                            - finalSpaceship.getMass() * Math.cos(Math.atan2(mars.getY() - finalSpaceship.getY(), rx - finalSpaceship.getX())) / (Math.pow(rx - finalSpaceship.getX(), 2) + Math.pow(mars.getY() - finalSpaceship.getY(), 2))
                                    );
                    marsAccelerationY = (ry, vy) ->
                            Constants.G *
                                    (- sun.getMass() * Math.sin(Math.atan2(ry, mars.getX())) / (Math.pow(mars.getX() - sun.getX(), 2) + Math.pow(ry - sun.getY(), 2))
                                            - earth.getMass() * Math.sin(Math.atan2(ry - earth.getY(), mars.getX() - earth.getX())) / (Math.pow(mars.getX() - earth.getX(), 2) + Math.pow(ry - earth.getY(), 2))
                                            - finalSpaceship.getMass() * Math.sin(Math.atan2(ry - finalSpaceship.getY(), mars.getX() - finalSpaceship.getX())) / (Math.pow(mars.getX() - finalSpaceship.getX(), 2) + Math.pow(ry - finalSpaceship.getY(), 2))
                                    );

                    BiFunction<Double, Double, Double> spaceshipAccelerationX = (rx, vx) ->
                            Constants.G *
                                    (- sun.getMass() * Math.cos(Math.atan2(finalSpaceship.getY(), rx)) / (Math.pow(rx - sun.getX(), 2) + Math.pow(finalSpaceship.getY() - sun.getY(), 2))
                                            - earth.getMass() * Math.cos(Math.atan2(finalSpaceship.getY() - earth.getY(), rx - earth.getX())) / (Math.pow(rx - earth.getX(), 2) + Math.pow(finalSpaceship.getY() - earth.getY(), 2))
                                            + mars.getMass() * Math.cos(Math.atan2(finalSpaceship.getY() - mars.getY(), rx - mars.getX())) / (Math.pow(rx - mars.getX(), 2) + Math.pow(finalSpaceship.getY() - mars.getY(), 2))
                                    );
                    BiFunction<Double, Double, Double> spaceshipAccelerationY = (ry, vy) ->
                            Constants.G *
                                    (- sun.getMass() * Math.sin(Math.atan2(ry, finalSpaceship.getX())) / (Math.pow(finalSpaceship.getX() - sun.getX(), 2) + Math.pow(ry - sun.getY(), 2))
                                            - earth.getMass() * Math.sin(Math.atan2(ry - earth.getY(), finalSpaceship.getX() - earth.getX())) / (Math.pow(finalSpaceship.getX() - earth.getX(), 2) + Math.pow(ry - earth.getY(), 2))
                                            + mars.getMass() * Math.sin(Math.atan2(ry - mars.getY(), finalSpaceship.getX() - mars.getX())) / (Math.pow(finalSpaceship.getX() - mars.getX(), 2) + Math.pow(ry - mars.getY(), 2))
                                    );

                    earthXPredictor.setAcceleration(earthAccelerationX);
                    earthYPredictor.setAcceleration(earthAccelerationY);
                    marsXPredictor.setAcceleration(marsAccelerationX);
                    marsYPredictor.setAcceleration(marsAccelerationY);
                    spaceshipXIterator = new GearPredictor(dt, spaceshipAccelerationX, spaceship.getX(), spaceship.getVx(), spaceshipAccelerationX.apply(spaceship.getX(), 0.0), 0, 0, 0).iterator();
                    spaceshipYIterator = new GearPredictor(dt, spaceshipAccelerationY, spaceship.getY(), spaceship.getVy(), spaceshipAccelerationY.apply(spaceship.getY(), 0.0), 0, 0, 0).iterator();
                }
                StateVariables earthXState = earthXIterator.next();
                StateVariables earthYState = earthYIterator.next();
                StateVariables marsXState = marsXIterator.next();
                StateVariables marsYState = marsYIterator.next();
                StateVariables spaceshipXState = spaceship == null ? null : spaceshipXIterator.next();
                StateVariables spaceshipYState = spaceship == null ? null : spaceshipYIterator.next();

                earth.set(earthXState, earthYState);
                mars.set(marsXState, marsYState);
                if (spaceship != null) {
                    spaceship.set(spaceshipXState, spaceshipYState);
                }

//                if (i % 100 == 0) {
                    writer.write(new PlanetaryVariables(earthXState, earthYState, marsXState, marsYState, spaceshipXState, spaceshipYState).toString());
                    writer.newLine();
//                }

                if (spaceship != null && spaceship.distanceTo(mars) < mars.getRadius() + MIN_DISTANCE_TO_MARS) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write files.");
        }
    }
}
