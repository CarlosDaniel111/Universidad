import random
import math
import matplotlib.pyplot as plt

fig, grafica = plt.subplots(2)
fig.set_size_inches(8, 8)

grafica[0].set_aspect(1)
grafica[0].set_xlim(-1.1, 1.1)
grafica[0].set_ylim(-1.1, 1.1)

grafica[0].add_artist(plt.Circle((0, 0), 1, fill=True, color="r"))
grafica[0].add_artist(plt.Circle((0, 0), 0.75, fill=True, color="w"))
grafica[0].add_artist(plt.Circle((0, 0), 0.50, fill=True, color="r"))
grafica[0].add_artist(plt.Circle((0, 0), 0.25, fill=True, color="w"))
grafica[0].add_artist(plt.Circle((0, 0), 0.10, fill=True, color="r"))


dardos = int(input("¿Cuantos dardos deseas lanzar? "))
aciertos = 0

for i in range(1, dardos + 1):
    x = random.random() * 2 - 1
    y = random.random() * 2 - 1

    grafica[0].plot(x, y, marker=".")
    grafica[1].plot([0, i], [math.pi, math.pi], color="b")

    if (x * x + y * y) <= 1:
        aciertos += 1

    pi = aciertos / i * 4
    grafica[1].plot(i, pi, marker=".", color="r")
    plt.title(f"{aciertos}/{i} * 4 ≈ " + "{:.8f}".format(pi) + " ≈ π")
    # plt.pause(0.01)
plt.show()
