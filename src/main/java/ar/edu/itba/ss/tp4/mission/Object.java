package ar.edu.itba.ss.tp4.mission;

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
}
