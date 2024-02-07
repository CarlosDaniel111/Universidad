import random
import matplotlib.pyplot as plt
import matplotlib.image as mpimg


def convertir(a):
    return f"C:/Users/beltr_2lu48tj/Documents/TEC/Quinto Semestre/Simulacion/MisProyectos/Unidad2/cartas/{a[0]}_{a[1]}.png"


def obtenerCarta():
    aleat = random.random()
    if aleat <= 0.25:
        color = 0
    elif aleat <= 0.5:
        color = 1
    elif aleat <= 0.75:
        color = 2
    elif aleat <= 1:
        color = 3

    aleat2 = random.random()
    for i in range(1, 14):
        if aleat2 <= i / 13:
            num = i
            break

    if cartaUsada[color * 13 + num] != 0:
        return obtenerCarta()

    cartaUsada[color * 13 + num] = 1
    return [color, num]


fig, ax = plt.subplots()
fig.set_size_inches(10, 8)
plt.title(f"Jugador 1: 0\nJugador 2: 0\nJugador 3: 0\nJugador 4: 0")


ganados = [0] * 4
veces = int(input("¿Cuantas simulaciones quieres? "))
for i in range(veces):
    ax.axis("off")
    cartaUsada = [0] * 54
    jugadores = [[], [], [], []]
    # Dar cartas
    for j in range(8):
        jugadores[j % 4].append(obtenerCarta())

    # Quemar carta
    cartaQuemada = obtenerCarta()
    centro = []
    for j in range(3):
        centro.append(obtenerCarta())

    cartaQuemada2 = obtenerCarta()
    centro.append(obtenerCarta())
    cartaQuemada3 = obtenerCarta()
    centro.append(obtenerCarta())

    mayorJuego = [-1, -1]
    ganador = -1

    # Checar juegos
    for j in range(4):
        # for carta in jugadores[j]:
        #   print(carta)
        # for carta in centro:
        #   print(carta)
        # print("------")

        tienePoker = False
        tieneFull = False
        tieneColor = False
        tieneEscalera = False
        tieneTercia = False
        tienePares = False

        cntNum = [0] * 14
        cntColor = [0] * 4
        cartaAlta = -1
        for carta in jugadores[j]:
            cntNum[carta[1]] += 1
            cntColor[carta[0]] += 1
            if carta[1] == 1:
                cartaAlta = 15
            cartaAlta = max(cartaAlta, carta[1])
        for carta in centro:
            cntNum[carta[1]] += 1
            cntColor[carta[0]] += 1
            if carta[1] == 1:
                cartaAlta = 15
            cartaAlta = max(cartaAlta, carta[1])

        pares = []

        if cntNum[1] == 4:
            tienePoker = True
            poker = 15
        elif cntNum[1] == 3:
            tieneTercia = True
            tercia = 15
        elif cntNum[1] == 2:
            tienePares = True
            pares.append(15)
        tercia = -1
        for k in range(13, 1, -1):
            if cntNum[k] == 4:
                tienePoker = True
                poker = k
            elif cntNum[k] == 3:
                tieneTercia = True
                tercia = max(tercia, k)
            elif cntNum[k] == 2:
                tienePares = True
                pares.append(k)

        # Checar color
        for k in range(4):
            if cntColor[k] == 5:
                tieneColor = True

        # Checar Full
        if tieneTercia and tienePares:
            tieneFull = True

        # Checar escalera
        for k in range(13, 4, -1):
            if (
                cntNum[k] != 0
                and cntNum[k - 1] != 0
                and cntNum[k - 2] != 0
                and cntNum[k - 3] != 0
                and cntNum[k - 4] != 0
            ):
                tieneEscalera = True
                escalera = [k, k - 4]
                break

        if tienePoker:
            juego = [7, poker]
            # print("tiene poker")
        elif tieneFull:
            juego = [6, tercia]
            # print("tiene full")
        elif tieneColor:
            juego = [5, 0]
            # print("tiene color")
        elif tieneEscalera:
            juego = [4, escalera]
            # print("tiene escalera")
        elif tieneTercia:
            juego = [3, tercia]
            # print("tiene tercia")
        elif tienePares and len(pares) >= 2:
            juego = [2, pares[0], pares[1], cartaAlta]
            # print("tiene pares dobles")
        elif tienePares:
            juego = [1, pares[0], cartaAlta]
            # print("tiene par")
        else:
            juego = [0, cartaAlta]
            # print("tiene carta alta")

        if juego > mayorJuego:
            mayorJuego = juego
            ganador = j
    ganados[ganador] += 1
    # print(ganador + 1)

    ax.set_xlim(0, 15)
    ax.set_ylim(0, 10)

    cnt = 0
    for carta in centro:
        img = mpimg.imread(convertir(carta))
        ax.imshow(img, extent=(5 + (cnt) + 0.3, 6 + cnt, 4, 5.6))
        cnt += 1
    cnt = 0
    for carta in jugadores[0]:
        img = mpimg.imread(convertir(carta))
        ax.imshow(img, extent=(6.5 + (cnt) + 0.3, 7.5 + cnt, 7, 8.6))
        cnt += 1
    cnt = 0
    for carta in jugadores[1]:
        img = mpimg.imread(convertir(carta))
        ax.imshow(img, extent=(12 + (cnt) + 0.3, 13 + cnt, 4, 5.6))
        cnt += 1
    cnt = 0
    for carta in jugadores[2]:
        img = mpimg.imread(convertir(carta))
        ax.imshow(img, extent=(6.5 + (cnt) + 0.3, 7.5 + cnt, 1, 2.6))
        cnt += 1
    cnt = 0
    for carta in jugadores[3]:
        img = mpimg.imread(convertir(carta))
        ax.imshow(img, extent=(0 + (cnt) + 0.3, 1 + cnt, 4, 5.6))
        cnt += 1

    # QUEMADAS
    img = mpimg.imread(convertir(cartaQuemada))
    ax.imshow(img, extent=(0.3, 1, 7, 8.6))
    img = mpimg.imread(convertir(cartaQuemada2))
    ax.imshow(img, extent=(1.3, 2, 7, 8.6))
    img = mpimg.imread(convertir(cartaQuemada3))
    ax.imshow(img, extent=(2.3, 3, 7, 8.6))

    ax.text(0.65, 7.3, "X", fontsize=50, ha="center", color="red")
    ax.text(1.65, 7.3, "X", fontsize=50, ha="center", color="red")
    ax.text(2.65, 7.3, "X", fontsize=50, ha="center", color="red")

    ax.text(7.7, 9, "JUGADOR 1", fontsize=12, ha="center")
    ax.text(13.1, 6, "JUGADOR 2", fontsize=12, ha="center")
    ax.text(7.7, 0.3, "JUGADOR 3", fontsize=12, ha="center")
    ax.text(1.1, 6, "JUGADOR 4", fontsize=12, ha="center")
    ax.text(
        7.5, 6, f"Gano Jugador {ganador+1}", fontsize=12, ha="center", color="green"
    )
    plt.title(
        f"PUNTUAJE:\nJugador 1: {ganados[0]}\nJugador 2: {ganados[1]}\nJugador 3: {ganados[2]}\nJugador 4: {ganados[3]}"
    )

    plt.pause(0.5)
    if i is not veces - 1:
        plt.cla()


probabilidad = ganados[2] / veces * 100
plt.title(
    f"PUNTUAJE:\nJugador 1: {ganados[0]}\nJugador 2: {ganados[1]}\nJugador 3: {ganados[2]}\nJugador 4: {ganados[3]}\nLa probabilidad de que gane el jugador 3 es de: {probabilidad}%"
)
print(f"La probabilidad de que gane el jugador 3 es de: {probabilidad}%")
plt.show()
