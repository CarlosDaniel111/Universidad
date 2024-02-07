import math
import random
import matplotlib.pyplot as plt

fig, grafica = plt.subplots(2)
fig.set_size_inches(8, 8)

grafica[0].set_xlim(-1.1, 2.1)
grafica[0].set_ylim(0, 3)

grafica[0].plot([-1, 2], [1, 1], "r")
grafica[0].plot([-1, 2], [2, 2], "r")

agujas = int(input("¿Cuantas agujas deseas lanzar? "))
aciertos = 0
pi = 0.0

for i in range(1, agujas + 1):
    x1 = random.random()
    y1 = random.random() + 1
    angulo = random.random() * 360
    x2 = x1 + math.cos(angulo)
    y2 = y1 + math.sin(angulo)

    grafica[0].plot([x1, x2], [y1, y2])
    grafica[1].plot([0, i], [math.pi, math.pi], color="b")

    if y1 > y2:
        y1, y2 = y2, y1

    if (y1 <= 1 and y2 >= 1) or (y1 <= 2 and y2 >= 2):
        aciertos += 1

    if aciertos > 0:
        pi = 2 * i / aciertos

    grafica[1].plot(i, pi, marker=".", color="r")
    plt.title(f"{i}/{aciertos} ≈ " + "{:.8f}".format(pi) + " ≈ π")

    plt.pause(0.01)
plt.show()
