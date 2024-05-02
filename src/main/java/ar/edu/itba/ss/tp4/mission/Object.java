package ar.edu.itba.ss.tp4.mission;

import ar.edu.itba.ss.tp4.utils.StateVariables;

public class Object {
    private double radius;
    private double mass;
    private double x;
    private double y;
    private double vx;
    private double vy;

    public Object(double radius, double mass, double x, double y, double vx, double vy) {
        this.radius = radius;
        this.mass = mass;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void set(StateVariables stateX, StateVariables stateY) {
        this.setX(stateX.position());
        this.setVx(stateX.velocity());
        this.setY(stateY.position());
        this.setVy(stateY.velocity());
    }

    public static Object createSpaceshipFromEarth(Object earth, double mass, double relativePosition, double relativeTangentialVelocity) {
        double theta = Math.atan2(earth.getY(), earth.getX());
        double spaceshipX = earth.getX() + earth.getRadius() + relativePosition * Math.cos(theta);
        double spaceshipY = earth.getY() + earth.getRadius() + relativePosition * Math.sin(theta);
        double earthTangentialVelocity = earth.getVy() / Math.cos(theta);
        double spaceshipVx = (relativeTangentialVelocity + earthTangentialVelocity) * Math.sin(theta);
        double spaceshipVy = (relativeTangentialVelocity + earthTangentialVelocity) * Math.cos(theta);
        return new Object(0, mass, spaceshipX, spaceshipY, spaceshipVx, spaceshipVy);
    }

    public double distanceTo(Object other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
}
