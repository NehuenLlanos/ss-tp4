import csv
import os
import matplotlib.pyplot as plt
import numpy as np


########### CONSTANT VARIABLES ###########
FILENAME = "mars_mission_departure"
##########################################


def annotate_point(x, y, text, axis):
    bbox_props = dict(boxstyle="square,pad=0.3", fc="w", ec="k", lw=0.72)
    arrow_props = dict(arrowstyle="->")
    kw = dict(
        xycoords='data',
        textcoords="axes fraction",
        arrowprops=arrow_props,
        bbox=bbox_props
    )
    axis.annotate(text, xy=(x, y), xytext=(0.5, 0.1), **kw)


def distance_between(a, b):
    return np.sqrt((a[0] - b[0])**2 + (a[1] - b[1])**2)


files = [open(os.path.join(os.path.dirname(__file__), "..", "departures", f"{FILENAME}_{x}.txt")) for x in range(0, 60 * 60 * 24 * 365 * 2, 60 * 60 * 24)]
# files = [open(os.path.join(os.path.dirname(__file__), "..", "departures_day_171_per_hour_until_day_173", f"{FILENAME}_{x}.txt")) for x in range(171 * 24 * 60 * 60, 173 * 24 * 60 * 60, 60 * 60)]
# files = [open(os.path.join(os.path.dirname(__file__), "..", "departures_day_171_hour_22_per_minute_until_day_172_hour_00", f"{FILENAME}_{x}.txt")) for x in range(171 * 24 * 60 * 60 + 22 * 60 * 60, 172 * 24 * 60 * 60, 60)]

plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()
ax.ticklabel_format(axis="y", style="sci", useMathText=True)

xs = []
ys = []
seconds = []

for index, file in enumerate(files):
    data = list(csv.reader(file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    min_distance = np.inf
    min_seconds = np.inf
    for instant in data:
        mars_position = (instant[3], instant[4])
        spaceship_position = (instant[5], instant[6])
        if spaceship_position[0] == 0 and spaceship_position[1] == 0:
            continue
        distance = distance_between(mars_position, spaceship_position)
        if distance < min_distance:
            min_distance = distance
            min_seconds = instant[0]
    xs.append(index)
    ys.append(min_distance)
    seconds.append(min_seconds)
    file.close()

print(f"El día de salida del cohete que logra la menor distancia con marte es {np.argmin(ys)} con una distancia de {np.min(ys)} km.")
# print(f"La hora de salida del cohete que logra la menor distancia con marte es {np.argmin(ys)} con una distancia de {np.min(ys)} km.")
# print(f"El minuto de salida del cohete que logra la menor distancia con marte es {np.argmin(ys)} con una distancia de {np.min(ys)} km.")
print(f"El tiempo en segundos con menor distancia respecto a marte es {seconds[np.argmin(ys)]}.")

ax.axvline(x=xs[np.argmin(ys)], color="k", linestyle="--", zorder=-10)
ax.scatter(xs, ys)
ax.scatter(xs[np.argmin(ys)], np.min(ys), color="tab:red")

ax.set_xlabel("Día de partida", fontdict={"weight": "bold"})
# ax.set_xlabel("Hora de partida a partir del día 171", fontdict={"weight": "bold"})
# ax.set_xlabel("Minuto de partida a partir del día 171 a las 22:00", fontdict={"weight": "bold"})
ax.set_ylabel("Distancia a Marte $\\left( km \\right)$", fontdict={"weight": "bold"})

annotate_point(xs[np.argmin(ys)], np.min(ys), f"Día {xs[np.argmin(ys)]}", ax)
# annotate_point(xs[np.argmin(ys)], np.min(ys), f"Hora {xs[np.argmin(ys)]}", ax)
# annotate_point(xs[np.argmin(ys)], np.min(ys), f"Minuto {xs[np.argmin(ys)]}", ax)

plt.show()
