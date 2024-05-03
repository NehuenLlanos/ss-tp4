import csv
import os
import matplotlib.pyplot as plt
import numpy as np

########### CONSTANT VARIABLES ###########
A = 1
GAMMA = 100
M = 70
K = 10**4
RUNS = range(5)
##########################################


def get_analytic_solution(times):
    positions = []
    for t in times:
        positions.append(A * np.exp(-GAMMA * t / (2 * M)) * np.cos(np.sqrt(K / M - GAMMA**2 / (4 * M**2)) * t))
    return positions


def calculate_ecm(real, approx):
    differences = []
    for r, a in zip(real, approx):
        differences.append((r - a)**2)
    return np.mean(differences)


verlets = [open(os.path.join(os.path.dirname(__file__), "..", f"verlet_{i}.txt")) for i in RUNS]
beemans = [open(os.path.join(os.path.dirname(__file__), "..", f"beeman_{i}.txt")) for i in RUNS]
gear_predictors = [open(os.path.join(os.path.dirname(__file__), "..", f"gear_predictor_{i}.txt")) for i in RUNS]
og_verlet = [open(os.path.join(os.path.dirname(__file__), "..", f"original_verlet_{i}.txt")) for i in RUNS]

xs = []
ecm_verlet = []
ecm_beeman = []
ecm_gear_predictor = []
ecm_og_verlet = []

for verlet_file, beeman_file, gear_predictor_file, og_verlet_file in zip(verlets, beemans, gear_predictors, og_verlet):
    verlet = list(csv.reader(verlet_file, delimiter=" "))
    beeman = list(csv.reader(beeman_file, delimiter=" "))
    gear_predictor = list(csv.reader(gear_predictor_file, delimiter=" "))
    og_verlet = list(csv.reader(og_verlet_file, delimiter=" "))

    time = [float(x[0]) for x in verlet]
    position_real = get_analytic_solution(time)
    position_verlet = [float(x[1]) for x in verlet]
    position_beeman = [float(x[1]) for x in beeman]
    position_gear_predictor = [float(x[1]) for x in gear_predictor]
    position_og_verlet = [float(x[1]) for x in og_verlet]

    ecm_verlet.append(calculate_ecm(position_real, position_verlet))
    ecm_beeman.append(calculate_ecm(position_real, position_beeman))
    ecm_gear_predictor.append(calculate_ecm(position_real, position_gear_predictor))
    ecm_og_verlet.append(calculate_ecm(position_real, position_og_verlet))

    xs.append(time[1] - time[0])

plt.rcParams.update({'font.size': 20})
fig, ax = plt.subplots()
lines = []
lines.append(ax.scatter(xs, ecm_verlet, label="Verlet"))
lines.append(ax.scatter(xs, ecm_beeman, label="Beeman"))
lines.append(ax.scatter(xs, ecm_gear_predictor, label="Gear Predictor"))
lines.append(ax.scatter(xs, ecm_og_verlet, label="Original Verlet"))

ax.set_xlabel("$\\Delta t \\  \\left( s \\right)$", fontdict={"weight": "bold"})
ax.set_ylabel("$ECM$", fontdict={"weight": "bold"})
ax.legend(handles=lines)

plt.xscale("log")
plt.yscale("log")
# Display the animation
plt.show()
