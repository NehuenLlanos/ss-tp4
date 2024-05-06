import csv
import os
import matplotlib.pyplot as plt
import matplotlib.patches as ptchs
from PIL import Image
from matplotlib.animation import FuncAnimation, FFMpegWriter

########### CONSTANT VARIABLES ###########
FILENAME = "jupiter_mission_1000"
SUN_RADIUS = 6.96340 * 10**7
EARTH_RADIUS = 6.371 * 10**6.5
MARS_RADIUS = 3.3895 * 10**6.5
JUPITER_RADIUS = 6.9911 * 10**6.9
SPACESHIP_RADIUS = 2000 * 10**4
AXIS_LIMIT = 8 * 10**8
##########################################


with open(os.path.join(os.path.dirname(__file__), "..", f"{FILENAME}.txt")) as data_file:
    data = list(csv.reader(data_file, delimiter=" "))
    for i in range(len(data)):
        for j in range(len(data[i])):
            data[i][j] = float(data[i][j])

    fig, ax = plt.subplots()
    sun_texture = Image.open(os.path.join(os.path.dirname(__file__), "textures", "sun.jpeg"))
    earth_texture = Image.open(os.path.join(os.path.dirname(__file__), "textures", "earth.jpeg"))
    mars_texture = Image.open(os.path.join(os.path.dirname(__file__), "textures", "mars.jpeg"))
    jupiter_texture = Image.open(os.path.join(os.path.dirname(__file__), "textures", "jupiter.jpeg"))
    spaceship_texture = Image.open(os.path.join(os.path.dirname(__file__), "textures", "spaceship.png"))
    space_texture = Image.open(os.path.join(os.path.dirname(__file__), "textures", "space.jpeg"))

    def update(i):
        ax.clear()
        ax.get_xaxis().set_visible(False)
        ax.get_yaxis().set_visible(False)
        ax.set_xlim(-AXIS_LIMIT, AXIS_LIMIT)
        ax.set_ylim(-AXIS_LIMIT, AXIS_LIMIT)
        ax.set_aspect('equal', adjustable='box')

        # Plot each object
        spaceship_patch = ptchs.Circle((data[i][7], data[i][8]), SPACESHIP_RADIUS, transform=ax.transData)
        sun_patch = ptchs.Circle((0, 0), SUN_RADIUS, transform=ax.transData)
        earth_patch = ptchs.Circle((data[i][1], data[i][2]), EARTH_RADIUS, transform=ax.transData)
        mars_patch = ptchs.Circle((data[i][3], data[i][4]), MARS_RADIUS, transform=ax.transData)
        jupiter_patch = ptchs.Circle((data[i][5], data[i][6]), JUPITER_RADIUS, transform=ax.transData)

        ax.imshow(
            space_texture,
            extent=(-AXIS_LIMIT, AXIS_LIMIT,
                    -AXIS_LIMIT, AXIS_LIMIT)
        )
        ax.imshow(
            spaceship_texture,
            extent=(data[i][7] - SPACESHIP_RADIUS, data[i][7] + SPACESHIP_RADIUS,
                    data[i][8] - SPACESHIP_RADIUS, data[i][8] + SPACESHIP_RADIUS)
        ).set_clip_path(spaceship_patch)
        ax.imshow(
            sun_texture,
            extent=(-SUN_RADIUS, SUN_RADIUS,
                    -SUN_RADIUS, SUN_RADIUS)
        ).set_clip_path(sun_patch)
        ax.imshow(
            earth_texture,
            extent=(data[i][1] - EARTH_RADIUS, data[i][1] + EARTH_RADIUS,
                    data[i][2] - EARTH_RADIUS, data[i][2] + EARTH_RADIUS)
        ).set_clip_path(earth_patch)
        ax.imshow(
            mars_texture,
            extent=(data[i][3] - MARS_RADIUS, data[i][3] + MARS_RADIUS,
                    data[i][4] - MARS_RADIUS, data[i][4] + MARS_RADIUS)
        ).set_clip_path(mars_patch)
        ax.imshow(
            jupiter_texture,
            extent=(data[i][5] - JUPITER_RADIUS, data[i][5] + JUPITER_RADIUS,
                    data[i][6] - JUPITER_RADIUS, data[i][6] + JUPITER_RADIUS)
        ).set_clip_path(jupiter_patch)

        return ax

    # Create the animation
    ani = FuncAnimation(fig, update, frames=len(data), blit=False, interval=1)

    # Display the animation
    plt.show()
    # Save the animation
    # ani.save("../animation.mp4", writer=FFMpegWriter(fps=30))
