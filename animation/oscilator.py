import csv
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


with open(f"../verlet.txt") as verlet_file:
    verlet = list(csv.reader(verlet_file, delimiter=" "))
    time = [float(x[0]) for x in verlet]
    position_verlet = [float(x[1]) for x in verlet]

    plt.rcParams.update({'font.size': 20})
    fig, ax = plt.subplots()
    lines = []
    lines.append(ax.plot(time, position_verlet, label="Verlet")[0])
    lines.append(ax.plot(time, get_analytic_solution(time), label="Real")[0])

    ax.set_xlabel("Tiempo $(s)$", fontdict={"weight": "bold"})
    ax.set_ylabel("Posición $(m)$", fontdict={"weight": "bold"})
    ax.legend(handles=lines)

    # Display the animation
    plt.show()
