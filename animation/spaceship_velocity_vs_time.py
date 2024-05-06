import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
FILENAMES = ["mars_mission_departure"]
DEPARTURE_DAYS = [12]
##########################################


def velocity(vx, vy):
    return np.sqrt(vx**2 + vy**2)


files = [open(os.path.join(os.path.dirname(__file__), "..", "departures", f"{x}.txt")) for x in FILENAMES]
plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()
lines = []

for file, departure_day in zip(files, DEPARTURE_DAYS):
    data = list(csv.reader(file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    xs = []
    ys = []
    for instant in data:
        xs.append(instant[0])
        ys.append(velocity(instant[11], instant[12]))

    lines.append(ax.scatter(xs, ys, label=f"Día de partida: {departure_day}"))
    file.close()

ax.set_xlabel("Día de partida", fontdict={"weight": "bold"})
ax.set_ylabel("Distancia a Marte $\\left( km \\right)$", fontdict={"weight": "bold"})
ax.legend(handles=lines)
plt.show()
