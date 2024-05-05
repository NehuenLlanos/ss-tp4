package ar.edu.itba.ss.tp4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import ar.edu.itba.ss.tp4.mission.MarsMission;
import ar.edu.itba.ss.tp4.mission.Object;
import ar.edu.itba.ss.tp4.utils.PlanetaryVariables;

public class MarsMissionMain {
    public static void main(String[] args) {
        List<String> data = null;
        try (Stream<String> stream = Files.lines(Paths.get("input.txt"))) {
            data = stream.toList();
        } catch (Exception e) {
            System.err.println("No input file found");
            System.exit(1);
        }

        if (data.size() != 17) {
            throw new IllegalStateException();
        }

        final double sunMass =  Double.parseDouble(data.get(0)); // Sun mass
        final double sunRadius = Double.parseDouble(data.get(1)); // Sun radius
        final double earthMass =  Double.parseDouble(data.get(2)); // Earth mass
        final double earthRadius = Double.parseDouble(data.get(3)); // Earth radius
        final double earthX = Double.parseDouble(data.get(4)); // Earth x
        final double earthY = Double.parseDouble(data.get(5)); // Earth y
        final double earthVelocityX = Double.parseDouble(data.get(6)); // Earth Velocity x
        final double earthVelocityY = Double.parseDouble(data.get(7)); // Earth Velocity y
        final double marsMass =  Double.parseDouble(data.get(8)); // Mars mass
        final double marsRadius = Double.parseDouble(data.get(9)); // Mars radius
        final double marsX = Double.parseDouble(data.get(10)); // Mars x
        final double marsY = Double.parseDouble(data.get(11)); // Mars y
        final double marsVelocityX = Double.parseDouble(data.get(12)); // Mars Velocity x
        final double marsVelocityY = Double.parseDouble(data.get(13)); // Mars Velocity y
        final double spaceshipMass = Double.parseDouble(data.get(14)); // Spaceship mass
        final double spaceshipRelativePosition = Double.parseDouble(data.get(15)); // Spaceship position relative to Earth
        final double spaceshipTangentialRelativeVelocity = Double.parseDouble(data.get(16)); // Spaceship tangential velocity relative to Earth

        // To run the simulation with a specific delta t and departure time
//        final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
//        final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
//        final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
//
//        final MarsMission mission = new MarsMission(sun, earth, mars, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
//
//        mission.simulate(1000, 0, String.format("mars_mission.txt"));

        // To run the simulation with different delta t
//        List.of(10, 20, 30, 40, 50).parallelStream().forEach(dt -> {
//            final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
//            final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
//            final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
//
//            final MarsMission mission = new MarsMission(sun, earth, mars, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
//
//            mission.simulate(dt, 0, String.format("mars_mission_%d.txt", dt));
//        });
    }
}
