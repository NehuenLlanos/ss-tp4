# Time-Step-Driven Simulation

## Introduction

This project is a simulation of a time-step-driven system. The system is composed of a set of components that interact with each other. The simulation is driven by a time-step mechanism, where each component is updated at each time step.
Three integration methods are implemented: Verlet, Beeman and Gear Predictor-Corrector.
The system that is simulated is a solar system with the Sun, the Earth, Mars and, optionally, Jupiter. A spaceship is launched from Earth and it is studied how the spaceship is affected by the gravitational forces of the planets and the Sun.

## Requirements

* Java 19
* Maven
* Python 3 (only if you want the animation to be rendered)

## Building the project

To build the project, `cd` to the root of the project and run the following command:

```bash
mvn clean package
```

This will compile and package a `.jar` file in the `target` directory.

## Executing the project

> [!NOTE]  
> The following instructions assume that you have built the project as described in the previous section and that the generated `.jar` file is in the current working directory.

```bash
java -jar ss-tp4-1.0-SNAPSHOT.jar {{ executable }}
```

Where `{{ executable }}` can be `oscillator`, `mars` or `jupiter`.

### `oscillator`

The program will execute the simulation of a simple harmonic oscillator with each of the three integration methods.

It will generate a series of output files named `{{ method }}-{{ run }}.txt` in the current working directory. Each run will have a different delta t, from the following list: $\{10^{-6}, 10^{-5}, 10^{-4}, 10^{-3}, 10^{-2}\}$. 

Each file will have the following structure:

```text
{{ time }} {{ position }} {{ velocity }}
...
```

### `mars`

The program will execute the simulation of the solar system with the Sun, the Earth and Mars. The spaceship will be launched from Earth and its trajectory will be studied.

The program expects to have an input file named `input.txt` in the current working directory. The input file should contain the following structure:

```text
{{ sun_mass }}
{{ sun_radius }}
{{ earth_mass }}
{{ earth_radius }}
{{ earth_x }}
{{ earth_y }}
{{ earth_vx }}
{{ earth_vy }}
{{ mars_mass }}
{{ mars_radius }}
{{ mars_x }}
{{ mars_y }}
{{ mars_vx }}
{{ mars_vy }}
{{ spaceship_mass }}
{{ spaceship_initial_distance_to_earth }}
{{ spaceship_initial_velocity }}
```

Where:

* `sun_mass` is the mass of the Sun.
* `sun_radius` is the radius of the Sun.
* `earth_mass` is the mass of the Earth.
* `earth_radius` is the radius of the Earth.
* `earth_x` is the x-coordinate of the Earth.
* `earth_y` is the y-coordinate of the Earth.
* `earth_vx` is the x-component of the velocity of the Earth.
* `earth_vy` is the y-component of the velocity of the Earth.
* `mars_mass` is the mass of Mars.
* `mars_radius` is the radius of Mars.
* `mars_x` is the x-coordinate of Mars.
* `mars_y` is the y-coordinate of Mars.
* `mars_vx` is the x-component of the velocity of Mars.
* `mars_vy` is the y-component of the velocity of Mars.
* `spaceship_mass` is the mass of the spaceship.
* `spaceship_initial_distance_to_earth` is the distance between the spaceship and Earth when it is launched.
* `spaceship_initial_velocity` is the initial velocity of the spaceship, tangent to the Earth.

This will generate an output file named `mars_mission.txt` in the current working directory, with the following structure:

```text
{{ time }} {{ earth_x }} {{ earth_y }} {{ mars_x }} {{ mars_y }} {{ spaceship_x }} {{ spaceship_y }} {{ earth_vx }} {{ earth_vy }} {{ mars_vx }} {{ mars_vy }} {{ spaceship_vx }} {{ spaceship_vy }}
...
```

### `jupiter`

The program will execute the simulation of the solar system with the Sun, the Earth, Mars and Jupiter. The spaceship will be launched from Earth and its trajectory will be studied.

The program expects to have an input file named `jupiter_input.txt` in the current working directory. The input file should contain the following structure:

```text
{{ sun_mass }}
{{ sun_radius }}
{{ earth_mass }}
{{ earth_radius }}
{{ earth_x }}
{{ earth_y }}
{{ earth_vx }}
{{ earth_vy }}
{{ mars_mass }}
{{ mars_radius }}
{{ mars_x }}
{{ mars_y }}
{{ mars_vx }}
{{ mars_vy }}
{{ jupiter_mass }}
{{ jupiter_radius }}
{{ jupiter_x }}
{{ jupiter_y }}
{{ jupiter_vx }}
{{ jupiter_vy }}
{{ spaceship_mass }}
{{ spaceship_initial_distance_to_earth }}
{{ spaceship_initial_velocity }}
```

Where:

* `sun_mass` is the mass of the Sun.
* `sun_radius` is the radius of the Sun.
* `earth_mass` is the mass of the Earth.
* `earth_radius` is the radius of the Earth.
* `earth_x` is the x-coordinate of the Earth.
* `earth_y` is the y-coordinate of the Earth.
* `earth_vx` is the x-component of the velocity of the Earth.
* `earth_vy` is the y-component of the velocity of the Earth.
* `mars_mass` is the mass of Mars.
* `mars_radius` is the radius of Mars.
* `mars_x` is the x-coordinate of Mars.
* `mars_y` is the y-coordinate of Mars.
* `mars_vx` is the x-component of the velocity of Mars.
* `mars_vy` is the y-component of the velocity of Mars.
* `jupiter_mass` is the mass of Jupiter.
* `jupiter_radius` is the radius of Jupiter.
* `jupiter_x` is the x-coordinate of Jupiter.
* `jupiter_y` is the y-coordinate of Jupiter.
* `jupiter_vx` is the x-component of the velocity of Jupiter.
* `jupiter_vy` is the y-component of the velocity of Jupiter.
* `spaceship_mass` is the mass of the spaceship.
* `spaceship_initial_distance_to_earth` is the distance between the spaceship and Earth when it is launched.
* `spaceship_initial_velocity` is the initial velocity of the spaceship, tangent to the Earth.

This will generate an output file named `jupiter_mission.txt` in the current working directory, with the following structure:

```text
{{ time }} {{ earth_x }} {{ earth_y }} {{ mars_x }} {{ mars_y }} {{ jupiter_x }} {{ jupiter_y }} {{ spaceship_x }} {{ spaceship_y }} {{ earth_vx }} {{ earth_vy }} {{ mars_vx }} {{ mars_vy }} {{ jupiter_vx }} {{ jupiter_vy }} {{ spaceship_vx }} {{ spaceship_vy }}
...
```

## Visualizing the output

> [!NOTE]  
> The following instructions assume that you have executed the project as described in the previous section and that all he output files are in the current working directory.

### Installing dependencies

To visualize the output, we must run a Python script. First, we need to install the required dependencies. To do so, run the following command:

```bash
python -m venv venv
source venv/bin/activate
pip install -r animation/requirements.txt
```

### Visualize the oscillator output

To visualize the output of the oscillator, run the following command:

```bash
python animation/oscillator.py
```

This will generate a plot with the position of the oscillator as a function of time for each integration method, as well as the analytical solution.

### Visualize the integrator method error vs delta t

To visualize the error of each integration method as a function of delta t, run the following command:

```bash
python animation/oscillator_error_vs_dt.py
```

This will generate a plot with the error of each integration method as a function of delta t. The calculated error is the mean squared error between the analytical solution and the numerical solution.

### Visualize the Mars mission animation

To visualize the Mars mission animation, run the following command:

```bash
python animation/mars_animation.py
```

This will generate an animation of the solar system with the Sun, the Earth, Mars and the spaceship. The animation will show the trajectory of the spaceship as it is launched from Earth.

### Visualize the Jupiter mission animation

To visualize the Jupiter mission animation, run the following command:

```bash
python animation/jupiter_animation.py
```

This will generate an animation of the solar system with the Sun, the Earth, Mars, Jupiter and the spaceship. The animation will show the trajectory of the spaceship as it is launched from Earth.

### Visualize other plots

* Minimum distance to Mars vs launch day: `python animation/distance_to_mars_vs_departure_day.py`
* Minimum distance to Jupiter vs launch day: `python animation/distance_to_jupiter_vs_departure_day.py`
* Travel time vs initial velocity for a Mars mission: `python animation/travel_time_vs_initial_velocities.py`
* Travel time vs initial velocity for a Jupiter mission: `python animation/jupiter_travel_time_vs_initial_velocities.py`
* Spaceship velocity vs time: `python animation/spaceship_velocity_vs_time.py`
* Universe energy vs time: `python animation/universe_energy_vs_time.py`

## Final Remarks

This project was done in an academic environment, as part of the curriculum of Systems Simulation from Instituto Tecnológico de Buenos Aires (ITBA).

The project was carried out by:

* Alejo Flores Lucey
* Nehuén Gabriel Llanos