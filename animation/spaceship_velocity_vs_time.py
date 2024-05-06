import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
FILENAMES = ["mars_mission_departure_14858220"]
DEPARTURE_DAYS = ["Dia 171, 23:17"]
##########################################


def velocity(vx, vy):
    return np.sqrt(vx**2 + vy**2)


files = [open(os.path.join(os.path.dirname(__file__), "..", "departures_day_171_hour_22_per_minute_until_day_172_hour_00", f"{x}.txt")) for x in FILENAMES]
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

    lines.append(ax.scatter(xs, ys, label=f"Partida: {departure_day}"))
    print(f"Tiempo de vuelo: {data[-1][0] - data[0][0]} s")
    file.close()

ax.set_xlabel("Tiempo $\\left( s \\right)$", fontdict={"weight": "bold"})
ax.set_ylabel("Velocidad de la nave $\\left( km/s \\right)$", fontdict={"weight": "bold"})
ax.ticklabel_format(axis="x", style="sci", useMathText=True)
ax.legend(handles=lines)
plt.show()
