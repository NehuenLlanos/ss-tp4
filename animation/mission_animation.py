import csv
import os
import matplotlib.pyplot as plt
import matplotlib.patches as ptchs
from matplotlib.animation import FuncAnimation, FFMpegWriter

########### CONSTANT VARIABLES ###########
FILENAME = "mars_mission"
SUN_RADIUS = 6.96340 * 10**7
EARTH_RADIUS = 6.371 * 10**6
MARS_RADIUS = 3.3895 * 10**7
SPACESHIP_RADIUS = 1000 * 10**3
##########################################


with open(os.path.join(os.path.dirname(__file__), "..", f"{FILENAME}.txt")) as data_file:
    data = list(csv.reader(data_file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    fig, ax = plt.subplots()

    def update(i):
        ax.clear()
        ax.get_xaxis().set_visible(False)
        ax.get_yaxis().set_visible(False)
        ax.set_xlim(-5 * 10**8, 5 * 10**8)
        ax.set_ylim(-5 * 10**8, 5 * 10**8)
        ax.set_aspect('equal', adjustable='box')

        # Plot each object
        ax.add_patch(ptchs.Circle((0, 0), SUN_RADIUS, color='y'))
        ax.add_patch(ptchs.Circle((float(data[i][1]), float(data[i][2])), EARTH_RADIUS, color='b'))
        ax.add_patch(ptchs.Circle((float(data[i][3]), float(data[i][4])), MARS_RADIUS, color='r'))
        ax.add_patch(ptchs.Circle((float(data[i][5]), float(data[i][6])), SPACESHIP_RADIUS, color='k'))

        return ax

    # Create the animation
    ani = FuncAnimation(fig, update, frames=len(data), blit=False, interval=1)

    # Display the animation
    plt.show()
    # Save the animation
    # ani.save("../animation.mp4", writer=FFMpegWriter(fps=30))