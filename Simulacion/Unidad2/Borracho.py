import random
import matplotlib.pyplot as plt
from tabulate import tabulate

veces = int(input("¿Cuantas veces deseas realizar la simulacion? "))

fig, ax = plt.subplots()
ax.set_aspect(1)
c_exito = 0
for i in range(1, veces + 1):
    x, y = 0, 0
    datos = []
    plt.plot([-10, 10], [0, 0], color="black")
    plt.plot([0, 0], [-10, 10], color="black")

    for j in range(1, 11):
        alea = random.random()
        if alea <= 0.25:
            marca = "^"
            y += 1
        elif alea <= 0.5:
            marca = "v"
            y -= 1
        elif alea <= 0.75:
            marca = ">"
            x += 1
        elif alea <= 1:
            marca = "<"
            x -= 1

        localizacion = f"({x},{y})"
        exito = ""
        if j == 10:
            if abs(x) + abs(y) >= 2:
                exito = "Si"
                c_exito += 1
            else:
                exito = "No"

        intento = i if j == 1 else ""
        datos.append([intento, j, alea, localizacion, exito])

        plt.plot(x, y, "blue", marker=marca)
        plt.title(localizacion)
        plt.pause(0.25)
    print(
        tabulate(
            datos,
            headers=[
                "N",
                "# de cuadras recorridas",
                "#aleagen",
                "Localizacion (x,y)",
                "Exito?",
            ],
            tablefmt="rounded_grid",
        )
    )
    plt.plot(x, y, "r", marker="o")
    plt.pause(1)
    if i is not veces:
        plt.cla()
probabilidad = c_exito / veces * 100
print(
    f"La probabilidad de que el borracho quede a 2 cuadras o mas del inicio es de {probabilidad}%"
)
plt.show()
