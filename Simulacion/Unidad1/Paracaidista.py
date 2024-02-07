import math

import matplotlib.pyplot as grafica
from tabulate import tabulate


def main():
    m = float(input("Peso del paracaidista? "))
    c = float(input("Coeficiente de Resistencia del aire? "))

    tiempo, velocidad, datos = [], [], []

    t = 0
    velocidadActual = 0
    while True:
        velocidadAnterior = velocidadActual
        velocidadActual = calcular_velocidad(m, c, t)

        tiempo.append(t)
        velocidad.append(velocidadActual)
        datos.append([t, velocidadActual])

        t += 1
        if velocidadActual == velocidadAnterior and velocidadActual != 0:
            break

    print(
        tabulate(
            datos,
            headers=["Tiempo (s)", "Velocidad"],
            tablefmt="rounded_grid",
            floatfmt=".15f",
        )
    )

    grafica.plot(tiempo, velocidad)
    grafica.title("Grafica de Paracaidista")
    grafica.xlabel("Tiempo (s)")
    grafica.ylabel("Velocidad")
    grafica.show()


def calcular_velocidad(m, c, t):
    g = 9.81
    return (g * m / c) * (1 - math.e ** (-(c / m) * t))


if __name__ == "__main__":
    main()
