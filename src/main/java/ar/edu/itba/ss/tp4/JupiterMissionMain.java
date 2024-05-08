package ar.edu.itba.ss.tp4;

import ar.edu.itba.ss.tp4.mission.JupiterMission;
import ar.edu.itba.ss.tp4.mission.MarsMission;
import ar.edu.itba.ss.tp4.mission.Object;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JupiterMissionMain {
    public static void run(String[] args) {
        List<String> data = null;
        try (Stream<String> stream = Files.lines(Paths.get("jupiter_input.txt"))) {
            data = stream.toList();
        } catch (Exception e) {
            System.err.println("No input file found");
            System.exit(1);
        }

        if (data.size() != 23) {
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
        final double jupiterMass =  Double.parseDouble(data.get(14)); // Jupiter mass
        final double jupiterRadius = Double.parseDouble(data.get(15)); // Jupiter radius
        final double jupiterX = Double.parseDouble(data.get(16)); // Jupiter x
        final double jupiterY = Double.parseDouble(data.get(17)); // Jupiter y
        final double jupiterVelocityX = Double.parseDouble(data.get(18)); // Jupiter Velocity x
        final double jupiterVelocityY = Double.parseDouble(data.get(19)); // Jupiter Velocity y
        final double spaceshipMass = Double.parseDouble(data.get(20)); // Spaceship mass
        final double spaceshipRelativePosition = Double.parseDouble(data.get(21)); // Spaceship position relative to Earth
        final double spaceshipTangentialRelativeVelocity = Double.parseDouble(data.get(22)); // Spaceship tangential velocity relative to Earth

        // To run the simulation with a specific delta t and departure time
//        final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
//        final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
//        final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
//        final Object jupiter = new Object(jupiterRadius, jupiterMass, jupiterX, jupiterY, jupiterVelocityX, jupiterVelocityY);
//
//        final JupiterMission mission = new JupiterMission(sun, earth, mars, jupiter, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
//
//        mission.simulate(1000, 0, "jupiter_mission_1000.txt");

        // To run the simulation for different departure days
//        List<Integer> departures = new ArrayList<>();
//        for (int i = 0; i < 60 * 60 * 24 * 365 * 5; i += 60 * 60 * 24) {
//            departures.add(i);
//        }
//        departures.parallelStream().forEach(departure -> {
//            final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
//            final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
//            final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
//            final Object jupiter = new Object(jupiterRadius, jupiterMass, jupiterX, jupiterY, jupiterVelocityX, jupiterVelocityY);
//
//            final JupiterMission mission = new JupiterMission(sun, earth, mars, jupiter, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
//
//            mission.simulate(60, departure, String.format("jupiter_departures/jupiter_mission_departure_%d.txt", departure));
//        });

        // To run the simulation for different departure hours
//        List<Integer> departures = new ArrayList<>();
//        for (int i = 895 * 24 * 60 * 60; i < 897 * 24 * 60 * 60; i += 60 * 60) {
//            departures.add(i);
//        }
//        departures.parallelStream().forEach(departure -> {
//            final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
//            final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
//            final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
//            final Object jupiter = new Object(jupiterRadius, jupiterMass, jupiterX, jupiterY, jupiterVelocityX, jupiterVelocityY);
//
//            final JupiterMission mission = new JupiterMission(sun, earth, mars, jupiter, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
//
//            mission.simulate(60, departure, String.format("jupiter_departures_day_895_per_hour_until_day_897/jupiter_mission_departure_%d.txt", departure));
//        });

        // To run the simulation for different departure minutes
//        List<Integer> departures = new ArrayList<>();
//        for (int i = 896 * 24 * 60 * 60 + 60 * 60; i < 896 * 24 * 60 * 60 + 3 * 60 * 60; i += 60) {
//            departures.add(i);
//        }
//        departures.parallelStream().forEach(departure -> {
//            final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
//            final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
//            final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
//            final Object jupiter = new Object(jupiterRadius, jupiterMass, jupiterX, jupiterY, jupiterVelocityX, jupiterVelocityY);
//
//            final JupiterMission mission = new JupiterMission(sun, earth, mars, jupiter, spaceshipMass, spaceshipRelativePosition, spaceshipTangentialRelativeVelocity);
//
//            mission.simulate(60, departure, String.format("jupiter_departures_day_896_hour_1_per_minute_until_day_896_hour_3/jupiter_mission_departure_%d.txt", departure));
//        });

        // Simulation for the 896th day at 1:52 with different velocities
        List<Double> spaceshipVelocities = new ArrayList<>();
        for (double i = 7.9; i < 8.1; i += 0.01) {
            spaceshipVelocities.add(i);
        }
        spaceshipVelocities.parallelStream().forEach(velocity -> {
            final Object sun = new Object(sunRadius, sunMass, 0, 0, 0, 0);
            final Object earth = new Object(earthRadius, earthMass, earthX, earthY, earthVelocityX, earthVelocityY);
            final Object mars = new Object(marsRadius, marsMass, marsX, marsY, marsVelocityX, marsVelocityY);
            final Object jupiter = new Object(jupiterRadius, jupiterMass, jupiterX, jupiterY, jupiterVelocityX, jupiterVelocityY);

            final JupiterMission mission = new JupiterMission(sun, earth, mars, jupiter, spaceshipMass, spaceshipRelativePosition, velocity + 7.12);

            final int departureSeconds = 896 * 24 * 60 * 60 + 60 * 60 + 52 * 60;
            mission.simulate(
                    60,
                    departureSeconds,
                    String.format("jupiter_departures_day_896_hour_1_minute_52_changing_velocities/jupiter_mission_velocity_%s.txt", String.format("%.2f", velocity)));
        });
    }
}
