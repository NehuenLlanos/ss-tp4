package ar.edu.itba.ss.tp4.utils;

public record PlanetaryVariables(
        StateVariables earthX,
        StateVariables earthY,
        StateVariables marsX,
        StateVariables marsY,
        StateVariables spaceshipX,
        StateVariables spaceshipY
) {
    @Override
    public String toString() {
        return earthX.time() + " " + earthX.position() + " " + earthY.position() + " " +
                marsX.position() + " " + marsY.position() + " " + String.format("%f", spaceshipX != null ? spaceshipX.position() : 0.0) + " " +
                String.format("%f", spaceshipY != null ? spaceshipY.position() : 0.0)
                + " " + earthX.velocity() + " " + earthY.velocity() + " " + marsX.velocity() + " " + marsY.velocity()
                + " " + String.format("%f", spaceshipX != null ? spaceshipX.velocity() : 0.0) + " " + String.format("%f", spaceshipY != null ? spaceshipY.velocity() : 0.0);
    }
}
