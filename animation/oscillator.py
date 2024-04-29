import csv
import os
import matplotlib.pyplot as plt
import numpy as np

########### CONSTANT VARIABLES ###########
A = 1
GAMMA = 100
M = 70
K = 10**4
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


with (open(os.path.join(os.path.dirname(__file__), "..", "verlet.txt")) as verlet_file,
      open(os.path.join(os.path.dirname(__file__), "..", "beeman.txt")) as beeman_file,
      open(os.path.join(os.path.dirname(__file__), "..", "gear_predictor.txt")) as gear_predictor_file):
    verlet = list(csv.reader(verlet_file, delimiter=" "))
    beeman = list(csv.reader(beeman_file, delimiter=" "))
    gear_predictor = list(csv.reader(gear_predictor_file, delimiter=" "))

    time = [float(x[0]) for x in verlet]
    position_real = get_analytic_solution(time)
    position_verlet = [float(x[1]) for x in verlet]
    position_beeman = [float(x[1]) for x in beeman]
    position_gear_predictor = [float(x[1]) for x in gear_predictor]

    print("ECM Verlet: ", calculate_ecm(position_real, position_verlet))
    print("ECM Beeman: ", calculate_ecm(position_real, position_beeman))
    print("ECM Gear Predictor: ", calculate_ecm(position_real, position_gear_predictor))

    plt.rcParams.update({'font.size': 20})
    fig, ax = plt.subplots()
    lines = []
    lines.append(ax.plot(time, position_verlet, label="Verlet")[0])
    lines.append(ax.plot(time, position_beeman, label="Beeman")[0])
    lines.append(ax.plot(time, position_gear_predictor, label="Gear Predictor")[0])
    lines.append(ax.plot(time, position_real, label="Real", linestyle="dotted")[0])

    ax.set_xlabel("Tiempo $(s)$", fontdict={"weight": "bold"})
    ax.set_ylabel("Posición $(m)$", fontdict={"weight": "bold"})
    ax.legend(handles=lines)

    # Display the animation
    plt.show()
