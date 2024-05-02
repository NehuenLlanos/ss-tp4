package ar.edu.itba.ss.tp4.integrator;

import ar.edu.itba.ss.tp4.utils.StateVariables;
import ar.edu.itba.ss.tp4.utils.Utils;

import java.util.Iterator;
import java.util.function.BiFunction;

public class GearPredictor implements IntegratorMethod {
    private final double dt;
    private BiFunction<Double, Double, Double> acceleration;
    private final double initialR;
    private final double initialR1;
    private final double initialR2;
    private final double initialR3;
    private final double initialR4;
    private final double initialR5;

    public GearPredictor(
            double dt,
            BiFunction<Double, Double, Double> acceleration,
            double initialR,
            double initialR1,
            double initialR2,
            double initialR3,
            double initialR4,
            double initialR5
    ) {
        this.dt = dt;
        this.acceleration = acceleration;
        this.initialR = initialR;
        this.initialR1 = initialR1;
        this.initialR2 = initialR2;
        this.initialR3 = initialR3;
        this.initialR4 = initialR4;
        this.initialR5 = initialR5;
    }

    public void setAcceleration(BiFunction<Double, Double, Double> acceleration) {
        this.acceleration = acceleration;
    }

    @Override
    public Iterator<StateVariables> iterator() {
        return new Iterator<>() {
            private double t = 0;
            private double r = initialR;
            private double r1 = initialR1;
            private double r2 = initialR2;
            private double r3 = initialR3;
            private double r4 = initialR4;
            private double r5 = initialR5;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public StateVariables next() {
                final StateVariables returnValue = new StateVariables(t, r, r1);

                final double predictedR  = r  + r1 * dt + r2 * Math.pow(dt, 2) / Utils.factorial(2) + r3 * Math.pow(dt, 3) / Utils.factorial(3) + r4 * Math.pow(dt, 4) / Utils.factorial(4) + r5 * Math.pow(dt, 5) / Utils.factorial(5);
                final double predictedR1 = r1 + r2 * dt + r3 * Math.pow(dt, 2) / Utils.factorial(2) + r4 * Math.pow(dt, 3) / Utils.factorial(3) + r5 * Math.pow(dt, 4) / Utils.factorial(4);
                final double predictedR2 = r2 + r3 * dt + r4 * Math.pow(dt, 2) / Utils.factorial(2) + r5 * Math.pow(dt, 3) / Utils.factorial(3);
                final double predictedR3 = r3 + r4 * dt + r5 * Math.pow(dt, 2) / Utils.factorial(2);
                final double predictedR4 = r4 + r5 * dt;
                final double predictedR5 = r5;

                final double delta = (acceleration.apply(predictedR, predictedR1) - predictedR2) * Math.pow(dt, 2) / Utils.factorial(2);

                t += dt;
                r = predictedR + 3.0 / 16.0 * delta * Utils.factorial(0) / Math.pow(dt, 0);
                r1 = predictedR1 + 251.0 / 360.0 * delta * Utils.factorial(1) / Math.pow(dt, 1);
                r2 = predictedR2 + 1.0 * delta * Utils.factorial(2) / Math.pow(dt, 2);
                r3 = predictedR3 + 11.0 / 18.0 * delta * Utils.factorial(3) / Math.pow(dt, 3);
                r4 = predictedR4 + 1.0 / 6.0 * delta * Utils.factorial(4) / Math.pow(dt, 4);
                r5 = predictedR5 + 1.0 / 60.0 * delta * Utils.factorial(5) / Math.pow(dt, 5);

                return returnValue;
            }
        };
    }
}
