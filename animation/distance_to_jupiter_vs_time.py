import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
FILENAME = "jupiter_mission_departure_26784000"
##########################################


def distance_between(a, b):
    return np.sqrt((a[0] - b[0])**2 + (a[1] - b[1])**2)


plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()
ax.ticklabel_format(axis="y", style="sci", useMathText=True)

xs = []
ys = []

file = open(os.path.join(os.path.dirname(__file__), "..", "..", "jupiter_departures", f"{FILENAME}.txt"))
data = list(csv.reader(file, delimiter=" "))
for i in range(len(data)):
    for j in range(len(data[i])):
        data[i][j] = float(data[i][j])
file.close()

for instant in data:
    jupiter_position = (instant[5], instant[6])
    spaceship_position = (instant[7], instant[8])
    distance = distance_between(jupiter_position, spaceship_position)
    xs.append(instant[0])
    ys.append(distance)


ax.scatter(xs, ys)

ax.set_xlabel("Tiempo $s$", fontdict={"weight": "bold"})
ax.set_ylabel("Distancia a Júpiter $\\left( km \\right)$", fontdict={"weight": "bold"})

plt.show()
