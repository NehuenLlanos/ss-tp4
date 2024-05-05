import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
FILENAME = "mars_mission"
# RUNS = [60, 500, 1000, 3600, 10800, 21600, 43200, 86400]
# RUNS = [60, 120, 180, 240, 300, 360, 420, 480, 540, 600, 660, 720, 780, 840, 900]
RUNS = [10, 20, 30, 40, 50, 60]
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


files = [open(os.path.join(os.path.dirname(__file__), "..", f"{FILENAME}_{x}.txt")) for x in RUNS]
plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()
lines = []

for file in files:
    data = list(csv.reader(file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    deltaT = data[1][0] - data[0][0]
    xs = []
    ys = []
    for instant in data:
        xs.append(instant[0])
        energy = instant_energy(
            (instant[1], instant[2], instant[7], instant[8]),
            (instant[3], instant[4], instant[9], instant[10]),
            (instant[5], instant[6], instant[11], instant[12])
        )
        ys.append(energy)
    print(f"Delta t = {round(deltaT)} - Delta E = {ys[-1] - ys[0]}")
    line, = ax.plot(xs, ys, label=f"$\\Delta t = {round(deltaT)} s$")
    lines.append(line)
    file.close()

ax.set_xlabel("Tiempo $\\left( s \\right)$", fontdict={"weight": "bold"})
ax.set_ylabel("Energía $\\left( \\frac{kg \\cdot km^2}{s^2} \\right)$", fontdict={"weight": "bold"})
ax.legend(handles=lines)
plt.show()
