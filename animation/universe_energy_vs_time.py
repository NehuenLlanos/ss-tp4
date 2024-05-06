import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
FILENAME = "mars_mission"
RUNS = [0.01, 0.1, 1, 10, 100, 1000, 10000]
G = 6.67430 * 10 ** -20
SUN_MASS = 2 * 10 ** 30
EARTH_MASS = 5.972 * 10 ** 24
MARS_MASS = 6.4 * 10 ** 23
SPACESHIP_MASS = 2 * 10 ** 5
##########################################


def distance(a, b):
    return np.sqrt((a[0] - b[0])**2 + (a[1] - b[1])**2)


# element_data = (pos_x, pos_y, v_x, v_y)
def instant_energy(earth_data, mars_data, spaceship_data):
    U_sun = (- G * SUN_MASS * EARTH_MASS / distance((0, 0), earth_data)
             - G * SUN_MASS * MARS_MASS / distance((0, 0), mars_data)
             - G * SUN_MASS * SPACESHIP_MASS / distance((0, 0), spaceship_data))
    U_earth = (- G * EARTH_MASS * MARS_MASS / distance(earth_data, mars_data)
               - G * EARTH_MASS * SPACESHIP_MASS / distance(earth_data, spaceship_data))
    U_mars = - G * MARS_MASS * SPACESHIP_MASS / distance(mars_data, spaceship_data)
    U_total = U_sun + U_earth + U_mars

    K_earth = EARTH_MASS * (earth_data[2] ** 2 + earth_data[3] ** 2) / 2
    K_mars = MARS_MASS * (mars_data[2] ** 2 + mars_data[3] ** 2) / 2
    K_spaceship = SPACESHIP_MASS * (spaceship_data[2] ** 2 + spaceship_data[3] ** 2) / 2
    K_total = K_earth + K_mars + K_spaceship

    return U_total + K_total


files = [open(os.path.join(os.path.dirname(__file__), "..", "{:s}_{:.3f}.txt").format(FILENAME, x)) for x in RUNS]
plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()
ax.ticklabel_format(axis="x", style="sci", useMathText=True)

lines = []

average_energy_vs_dt_xs = []
average_energy_vs_dt_ys = []
average_energy_vs_dt_errors = []

for file, deltaT in zip(files, RUNS):
    data = list(csv.reader(file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    xs = []
    ys = []
    first_energy = 0
    did_first = False
    for instant in data:
        energy = instant_energy(
            (instant[1], instant[2], instant[7], instant[8]),
            (instant[3], instant[4], instant[9], instant[10]),
            (instant[5], instant[6], instant[11], instant[12])
        )
        if not did_first:
            did_first = True
            first_energy = energy
        else:
            xs.append(instant[0])
            ys.append(abs(100 - energy / first_energy * 100))

    # Comment the following lines to get average energy lost vs deltaT
    line, = ax.plot(xs, ys, label=f"$\\Delta t = {round(deltaT, 2)} s$")
    lines.append(line)

    average_energy_vs_dt_xs.append(deltaT)
    average_energy_vs_dt_ys.append(np.mean(ys))
    average_energy_vs_dt_errors.append(np.std(ys, ddof=1))

    file.close()

# Uncomment the following lines to get average energy lost vs deltaT
# ax.errorbar(average_energy_vs_dt_xs, average_energy_vs_dt_ys, yerr=average_energy_vs_dt_errors, fmt="o")
# ax.set_xscale("log")
# ax.set_yscale("log")
# ax.set_xlabel("$\\Delta t$ $\\left( s \\right)$", fontdict={"weight": "bold"})
# ax.set_ylabel("Promedio de la energía perdida del sistema", fontdict={"weight": "bold"})

# Comment the following lines to get average energy lost vs deltaT
ax.set_xlabel("Tiempo $\\left( s \\right)$", fontdict={"weight": "bold"})
ax.set_ylabel("Porcentaje de energía perdida del sistema", fontdict={"weight": "bold"})
ax.set_yscale("log")
ax.legend(handles=lines)

plt.show()
