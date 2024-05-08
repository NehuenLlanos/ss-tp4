import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
VELOCITIES = np.arange(7.9, 8.100, 0.01)
# Reduced velocities
# VELOCITIES = np.arange(7.995, 8.005, 0.001)
##########################################

def distance_between(a, b):
    return np.sqrt((a[0] - b[0])**2 + (a[1] - b[1])**2)


files = [open(os.path.join(os.path.dirname(__file__), "..", "jupiter_departures_day_896_hour_1_minute_52_changing_velocities", "jupiter_mission_velocity_{:.2f}.txt".format(x))) for x in VELOCITIES]
plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()

xs = []
ys = []

# Minimum distance to Jupiter vs velocity.
# We are interested in the velocities that allows the spaceship to be less than 10^4 km away from Jupiter.
# ...............................................................................................................
# for file, velocity in zip(files, VELOCITIES):
#     data = list(csv.reader(file, delimiter=" "))
#     for i in range(len(data)):
#         for j in range(len(data[i])):
#             data[i][j] = float(data[i][j])
#
#     min_distance = np.inf
#     for instant in data:
#         jupiter_position = (instant[5], instant[6])
#         spaceship_position = (instant[7], instant[8])
#         if spaceship_position[0] == 0 and spaceship_position[1] == 0:
#             continue
#         distance = distance_between(jupiter_position, spaceship_position)
#         if distance < min_distance:
#             min_distance = distance
#     ys.append(min_distance)
#     xs.append(velocity)
#     file.close()
#
# for i in range(4):
#     if ys[i] < 10**6:
#         print(f"La velocidad inicial de la nave para llegar a marte en el menor tiempo posible es {xs[i]} km/s.")
#
# # ax.semilogy(xs, ys)
# ax.scatter(xs, ys)
# ax.set_yscale('log')
# ax.axhline(y=10**6, color="k", linestyle="--")
# ax.set_xlabel("Velocidad inicial de la nave $\\left( km/s \\right)$", fontdict={"weight": "bold"})
# ax.set_ylabel("Distancia mínima a júpiter $\\left( km \\right)$", fontdict={"weight": "bold"})

#######################################################################################################################

# Travel time vs Velocity. Parisi told us it is unnecessary to plot the travel time vs velocity.
# ...............................................................................................................
# for file, velocity in zip(files, VELOCITIES):
#     data = list(csv.reader(file, delimiter=" "))
#     for i in range(len(data)):
#         for j in range(len(data[i])):
#             data[i][j] = float(data[i][j])
#
#     xs.append(velocity)
#     ys.append(data[-1][0] - data[0][0])
#     file.close()

#######################################################################################################################

# Time vs velocity, but only for the four velocities that allows the spaceship to be less than 10^4 km away from mars.
# ...............................................................................................................
for file, velocity in zip(files, VELOCITIES):
    data = list(csv.reader(file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    min_distance = np.inf
    min_time = np.inf
    for instant in data:
        mars_position = (instant[5], instant[6])
        spaceship_position = (instant[7], instant[8])
        if spaceship_position[0] == 0 and spaceship_position[1] == 0:
            continue
        distance = distance_between(mars_position, spaceship_position)
        if distance < min_distance:
            min_distance = distance
            min_time = instant[0]
    if min_distance < 10**6:
        xs.append(velocity)
        ys.append(min_time)
    file.close()

ax.scatter(xs, ys)
ax.set_xlabel("Velocidad inicial de la nave $\\left( km/s \\right)$", fontdict={"weight": "bold"})
ax.set_ylabel("Tiempo de vuelo $\\left( s \\right)$", fontdict={"weight": "bold"})
ax.ticklabel_format(axis="y", style="sci", useMathText=True)

#######################################################################################################################

plt.show()
