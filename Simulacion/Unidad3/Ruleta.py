import random
from tabulate import tabulate


def calc_color(x: float):
    if x < 0.4545:
        return "Rojo"
    elif x < 0.9090:
        return "Negro"
    else:
        return "Verde"


simulaciones = int(input("¿Cuantas simulaciones quieres? "))
datosJ1 = []
datosJ2 = []
exitoJ1 = 0
exitoJ2 = 0

for i in range(simulaciones):
    sigueJ1 = True
    sigueJ2 = True
    apuestaJ1 = 200
    apuestaJ2 = 200
    it = 0
    verdeJ1 = False
    verdeJ2 = False
    apuesta = 1
    while sigueJ1 or sigueJ2:
        it += 1
        alea = random.random()
        color = calc_color(alea)

        # Jugador 1
        if sigueJ1:
            antes = apuestaJ1
            if color == "Verde":
                verdeJ1 = True
                gano = "Nulo"
            elif color == "Rojo":
                if verdeJ1 is False:
                    apuestaJ1 += 1
                gano = "Si"
                verdeJ1 = False
            else:
                apuestaJ1 -= 1
                gano = "No"
                verdeJ1 = False

            if apuestaJ1 >= 1000:
                exitoJ1 += 1
                sigueJ1 = False
            if apuestaJ1 <= 0:
                sigueJ1 = False

            datosJ1.append(
                [
                    it,
                    antes,
                    "$1",
                    alea,
                    color,
                    gano,
                    apuestaJ1,
                    "Si" if apuestaJ1 >= 1000 else "No",
                    "++" if apuestaJ1 >= 1000 else "",
                ]
            )

        # Jugador 2
        if sigueJ2:
            antes = apuestaJ2
            ant = apuesta
            if color == "Verde":
                verdeJ2 = True
                gano = "Nulo"
            elif color == "Rojo":
                if verdeJ2 is False:
                    apuestaJ2 += apuesta
                    apuesta = 1
                gano = "Si"
                verdeJ2 = False
            else:
                apuestaJ2 -= apuesta
                apuesta *= 2
                apuesta = min(apuesta, apuestaJ2)
                if apuesta > 1000:
                    apuesta = 1
                gano = "No"
                verdeJ2 = False

            if apuestaJ2 >= 1000:
                exitoJ2 += 1
                sigueJ2 = False
            if apuestaJ2 <= 0:
                sigueJ2 = False

            datosJ2.append(
                [
                    it,
                    antes,
                    "$" + str(ant),
                    alea,
                    color,
                    gano,
                    apuestaJ2,
                    "Si" if apuestaJ2 >= 1000 else "No",
                    "++" if apuestaJ2 >= 1000 else "",
                ]
            )

print("JUGADOR 1")
print(
    tabulate(
        datosJ1,
        headers=[
            "#iteracion",
            "$ antes de girar",
            "Apuesta",
            "#alea",
            "Color?",
            "Gano?",
            "$ despues de girar",
            "Se llego a USD$1000",
            "Exito++",
        ],
        tablefmt="rounded_grid",
    )
)
print("JUGADOR 2")
print(
    tabulate(
        datosJ2,
        headers=[
            "#iteracion",
            "$ antes de girar",
            "Apuesta",
            "#alea",
            "Color?",
            "Gano?",
            "$ despues de girar",
            "Se llego a USD$1000",
            "Exito++",
        ],
        tablefmt="rounded_grid",
    )
)

print(f"El jugador 1 tuvo exito en {exitoJ1} juegos de {simulaciones} simulaciones")
print(f"El jugador 2 tuvo exito en {exitoJ2} juegos de {simulaciones} simulaciones")
if exitoJ1 == exitoJ2:
    print("Las dos estrategias son buenas")
elif exitoJ1 > exitoJ2:
    print("La estrategia del Jugador 1 es la mejor")
else:
    print("La estrategia del Jugador 2 es la mejor")
