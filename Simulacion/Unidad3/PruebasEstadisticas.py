from random import random
from tabulate import tabulate
import matplotlib.pyplot as plt

chi5 = [
    3.8415,
    5.9915,
    7.8147,
    9.4877,
    11.0705,
    12.5916,
    14.0671,
    15.5073,
    16.9190,
    18.3070,
]
chi10 = [
    2.7055,
    4.6052,
    6.2514,
    7.7794,
    9.2363,
    10.6446,
    12.0170,
    13.3616,
    14.6837,
    15.9872,
]

kolmo5 = [
    0.22743,
    0.22425,
    0.22119,
    0.21826,
    0.21544,
    0.21273,
    0.21012,
    0.20760,
    0.20517,
    0.20283,
    0.20056,
    0.19837,
    0.19625,
    0.19420,
    0.19221,
    0.19028,
    0.18841,
]
kolmo10 = [
    0.21472,
    0.20185,
    0.19910,
    0.19646,
    0.19392,
    0.19148,
    0.18913,
    0.18687,
    0.18468,
    0.18257,
    0.18051,
    0.17856,
    0.17665,
    0.17481,
    0.17301,
    0.17128,
    0.16959,
]


def preguntarFallo():
    while True:
        global fallo
        print("\n¿Que porcentaje de fallo quieres aplicar?")
        print("1. 5%")
        print("2. 10%")
        opcionFallo = int(input("Opcion: "))
        if opcionFallo == 1:
            fallo = 0.05
            break
        elif opcionFallo == 2:
            fallo = 0.1
            break
        else:
            print("Opcion no valida")


# ------------------------------------------------------------------------
# Prueba de ChiCuadrada
def buscarIntervalo(intervalo, k):
    contador = 0
    for i in range(n):
        if aleat[i] > intervalo / k and aleat[i] <= (intervalo + 1) / k:
            contador += 1
    return contador


def graficarChi(intervalos, observado, esperado):
    plt.xticks(range(len(intervalos)), intervalos)
    plt.xlabel("Rangos (i)")
    plt.ylabel("Observado (O)")
    plt.title("Chi Cuadrada")
    plt.bar(range(len(observado)), observado)
    plt.axhline(y=esperado, color="r", linestyle="-")
    plt.show()


def ChiCuadrada():
    datos = []
    intervalos = []
    observado = []
    sumaO = 0
    sumaE = 0
    sumaO_E = 0
    Chi = 0
    k = round(n ** (1 / 2))
    for i in range(k):
        intervalos.append(round((i + 1) / k, 5))
        O = buscarIntervalo(i, k)
        observado.append(O)
        sumaO += O
        E = n / k
        sumaE += E
        O_E = O - E
        sumaO_E += O_E
        chi = O_E**2 / E
        Chi += chi
        datos.append([intervalos[i], O, E, O_E, chi])
    datos.append(["Totales", sumaO, sumaE, "Σ", Chi])
    print(
        tabulate(
            datos, headers=["i", "O", "E", "(O-E)", "(O-E)²/E"], tablefmt="rounded_grid"
        )
    )
    chiTabla = chi5[k - 2] if fallo == 0.05 else chi10[k - 2]
    if Chi <= chiTabla:
        print(f"{Chi} ≤ {chiTabla}")
        print("Los numeros ESTAN UNIFORMEMENTE DISTRIBUIDOS")
    else:
        print(f"{Chi} > {chiTabla}")
        print("Los numeros NO ESTAN UNIFORMEMENTE DISTRIBUIDOS")
    graficarChi(intervalos, observado, k)


# -----------------------------------------------------------------------------
# Prueba de Kolmogorov
def graficarKolmo(arr1, arr2, mayor):
    plt.plot(range(len(arr1)), arr1, color="blue", label="Ui")
    plt.plot(range(len(arr2)), arr2, color="orange", label="i/N")
    x_points = [mayor, mayor]
    y_points = [min(arr1[mayor], arr2[mayor]), max(arr1[mayor], arr2[mayor])]
    plt.plot(x_points, y_points, color="r", label="D")
    plt.legend()
    plt.show()


def Kolmogorov():
    datos = []
    aleatOrden = list(aleat)
    aleatOrden.sort()
    i_n = []
    DiMayor = 0
    posMayor = 0
    for i in range(n):
        i_n.append((i + 1) / n)
        di = abs(aleatOrden[i] - i_n[i])
        if di > DiMayor:
            DiMayor = di
            posMayor = i + 1
        datos.append([i + 1, aleatOrden[i], i_n[i], di])
    print(
        tabulate(
            datos,
            headers=["i", "Ui", "i/N", "Di"],
            tablefmt="rounded_grid",
            floatfmt=".5f",
        )
    )
    if n > 50:
        if fallo == 0.05:
            kolmoTabla = 1.36 / (n**0.5)
        else:
            kolmoTabla = 1.22 / (n**0.5)
    else:
        kolmoTabla = kolmo5[n - 34] if fallo == 0.05 else kolmo10[n - 34]
    if DiMayor <= kolmoTabla:
        print(f"{DiMayor} ≤ {kolmoTabla}")
        print("Los numeros ESTAN UNIFORMEMENTE DISTRIBUIDOS")
    else:
        print(f"{DiMayor} > {kolmoTabla}")
        print("Los numeros NO ESTAN UNIFORMEMENTE DISTRIBUIDOS")
    graficarKolmo(aleatOrden, i_n, posMayor - 1)


# -----------------------------------------------------------------------------
# Series
def graficarSeries(puntos):
    plt.xlabel("x")
    plt.ylabel("y")
    puntosX = []
    puntosY = []
    for i in range(n):
        puntosX.append(puntos[i][1])
        puntosY.append(puntos[i][2])
    plt.scatter(puntosX, puntosY, marker="o")
    plt.grid(True)
    plt.show()


def Series():
    # Imprimir tabla de pares
    tablaAleat = []
    for i in range(n):
        tablaAleat.append([i + 1, aleat[i], aleat[i + 1 if i != n - 1 else n - 1]])
    print(
        tabulate(
            tablaAleat,
            headers=["n", "U1", "U2"],
            tablefmt="rounded_grid",
            floatfmt=".5f",
        )
    )

    observado = [[0] * 6 for _ in range(5)]
    esperado = [[0] * 6 for _ in range(5)]
    tabla = [[0] * 6 for _ in range(5)]

    # Calcular observado
    lineaX = [0, 0.2, 0.4, 0.6, 0.8, 1]
    lineaY = [1, 0.8, 0.6, 0.4, 0.2, 0]
    for i in range(5):
        observado[i][0] = lineaY[i]
        for j in range(5):
            contador = 0
            for k in range(n):
                if (
                    tablaAleat[k][1] <= lineaX[j + 1]
                    and tablaAleat[k][1] > lineaX[j]
                    and tablaAleat[k][2] <= lineaY[i]
                    and tablaAleat[k][2] > lineaY[i + 1]
                ):
                    contador += 1
            observado[i][j + 1] = contador

    # Calcular esperado
    for i in range(5):
        observado[i][0] = lineaY[i]
        for j in range(5):
            esperado[i][j + 1] = n / 25

    # Calcular tabla
    sumatoria = 0
    for i in range(5):
        tabla[i][0] = lineaY[i]
        for j in range(5):
            calculo = ((observado[i][j + 1] - esperado[i][j + 1]) ** 2) / esperado[i][
                j + 1
            ]
            sumatoria += calculo
            tabla[i][j + 1] = calculo

    # Impresiones
    print("Tabla de Observado")
    print(
        tabulate(
            observado,
            headers=["", "0.2", "0.4", "0.6", "0.8", "1"],
            tablefmt="rounded_grid",
        )
    )
    print("Tabla de Esperado")
    print(
        tabulate(
            esperado,
            headers=["", "0.2", "0.4", "0.6", "0.8", "1"],
            tablefmt="rounded_grid",
        )
    )
    print("Tabla de Chi Cuadrada")
    print(
        tabulate(
            tabla,
            headers=["", "0.2", "0.4", "0.6", "0.8", "1"],
            tablefmt="rounded_grid",
        )
    )

    chiTabla = 36.4150 if fallo == 0.05 else 33.1962
    if sumatoria <= chiTabla:
        print(f"{sumatoria} ≤ {chiTabla}")
        print("Los numeros SI SON INDEPENDIENTES")
    else:
        print(f"{sumatoria} > {chiTabla}")
        print("Los numeros NO SON INDEPENDIENTES")

    graficarSeries(tablaAleat)


# -----------------------------------------------------------------------------
# Huecos
def Huecos():
    res = []
    alpha = float(input("Valor de alpha: "))
    beta = float(input("valor de beta: "))
    teta = beta - alpha
    for i in range(1, n + 1):
        e = 1 if alpha <= aleat[i - 1] <= beta else 0

        output = [i, aleat[i - 1], e]
        res.append(output)

    for i, x in enumerate(res):
        if x[2] == 1:
            x.append(0)
        else:
            contador = 0
            ultimo_indice = 0
            for y in range(i, len(res)):
                if res[y][2] == 0:
                    contador += 1
                    ultimo_indice = y
                else:
                    ultimo_indice = y - 1
                    break

            for y in range(ultimo_indice, i - 1, -1):
                if len(res[y]) == 3:
                    res[y].append(contador)
    print(
        tabulate(
            headers=["n", "Ui", "∈", "i"],
            tabular_data=res,
            tablefmt="rounded_grid",
            floatfmt=".5f",
        )
    )
    res2 = []
    d = dict()
    for r in range(len(res)):
        if res[r][3] != res[r - 1][3]:
            d[res[r][3]] = d.get(res[r][3], 0) + 1

    d = sorted(d.items())

    primero = True

    for x in d:
        if len(res2) == x[0]:
            aux = len(res2)
            res2.append(x[1])
        else:
            if primero:
                aux = len(res2)
                res2.append(x[1])
                primero = False
            else:
                res2[len(res2) - 1] += x[1]

    res.clear()
    sumatoria_oi = sum(res2)
    sumatoria_ultimo = 0
    for i, oi in enumerate(res2):
        pi = ((1 - teta) ** i) * (teta) if i < len(res2) - 1 else (1 - teta) ** i
        ei = sumatoria_oi * pi
        oi_ei = oi - ei
        ultimo = oi_ei**2 / ei
        if i == len(res2) - 1 and primero == False:
            aux_i = "≥ " + str(aux)
        else:
            aux_i = i
        res.append(
            [
                aux_i,
                pi,
                oi,
                ei,
                oi_ei,
                ultimo,
            ]
        )
        sumatoria_ultimo += ultimo

    res.append(["", 1, sumatoria_oi, sumatoria_oi, "Σ=", sumatoria_ultimo])

    print(
        tabulate(
            headers=["i", "Pi", "Oi", "Ei", "Oi-Ei", "(Oi-Ei)²/Ei"],
            tabular_data=res,
            tablefmt="rounded_grid",
            floatfmt=".4f",
        )
    )
    chiTabla = chi5[aux - 1] if fallo == 0.05 else chi10[aux - 1]
    if sumatoria_ultimo <= chiTabla:
        print(f"{sumatoria_ultimo} ≤ {chiTabla}")
        print("Los numeros SI SON INDEPENDIENTES")
    else:
        print(f"{sumatoria_ultimo} > {chiTabla}")
        print("Los numeros NO SON INDEPENDIENTES")


# -----------------------------------------------------------------------------
# Poker
def Poker():
    datos = []
    eventos = [0, 0, 0, 0, 0, 0, 0]

    for i in range(1, n + 1):
        n_str = "{:f}".format(aleat[i - 1])

        n_str += "0" * (7 - len(n_str))

        h = [int(n_str[2]), int(n_str[3]), int(n_str[4]), int(n_str[5]), int(n_str[6])]
        h.sort()

        pares = 0
        tercia = 0
        poker = 0
        quintilla = 0
        for x in range(5):
            if h[x] == -1:
                continue

            contador = 0

            for y in range(x + 1, 5):
                if h[x] == h[y]:
                    contador += 1
                    h[y] = -1
                else:
                    break

            if contador == 1:
                pares += 1
            elif contador == 2:
                tercia += 1
            elif contador == 3:
                poker += 1
            elif contador == 4:
                quintilla += 1

            h[x] = -1

        fila = [i, aleat[i - 1]]

        if pares == tercia == poker == quintilla == 0:
            fila.append("Pachuca")
            eventos[0] += 1
        elif pares == 1 and tercia == poker == quintilla == 0:
            fila.append("1 Par")
            eventos[1] += 1
        elif pares == 2:
            fila.append("2 Pares")
            eventos[2] += 1
        elif tercia == 1 and pares == 0:
            fila.append("Tercia")
            eventos[3] += 1
        elif tercia == 1 and pares == 1:
            fila.append("Full")
            eventos[4] += 1
        elif poker == 1:
            fila.append("Poker")
            eventos[5] += 1
        elif quintilla == 1:
            fila.append("Quintilla")
            eventos[6] += 1

        datos.append(fila)

    # Imprimir primera tabla
    print(
        tabulate(
            datos,
            headers=["n", "ri", "evento"],
            tablefmt="rounded_grid",
            floatfmt=".5f",
        )
    )
    chiPachuca = (eventos[0] - 0.3024 * n) ** 2 / (0.3024 * n)
    chiPar = (eventos[1] - 0.5040 * n) ** 2 / (0.5040 * n)
    chiTercia = (eventos[3] - 0.0720 * n) ** 2 / (0.0720 * n)
    chiDosPares = (eventos[2] - 0.1080 * n) ** 2 / (0.1080 * n)
    chiFull = (eventos[4] - 0.0090 * n) ** 2 / (0.0090 * n)
    chiPoker = (eventos[5] - 0.0045 * n) ** 2 / (0.0045 * n)
    chiQuintilla = (eventos[6] - 0.0001 * n) ** 2 / (0.0001 * n)
    sumatoria = (
        chiPachuca
        + chiPar
        + chiTercia
        + chiDosPares
        + chiFull
        + chiPoker
        + chiQuintilla
    )
    datos2 = [
        ["Pachuca", eventos[0], "0.3024", (0.3024 * n), chiPachuca],
        ["Un Par", eventos[1], "0.5040", (0.5040 * n), chiPar],
        ["Tercia", eventos[3], "0.0720", (0.0720 * n), chiTercia],
        ["Dos Pares", eventos[2], "0.1080", (0.1080 * n), chiDosPares],
        ["Full", eventos[4], "0.0090", (0.0090 * n), chiFull],
        ["Poker", eventos[5], "0.0045", (0.0045 * n), chiPoker],
        ["Quintilla", eventos[6], "0.0001", (0.0001 * n), chiQuintilla],
        ["Total de la muestra", n, "", "", sumatoria],
    ]
    print(
        tabulate(
            datos2,
            headers=["Evento", "FO", "PE", "FE", "(FOi - FEi)² / FEi"],
            tablefmt="rounded_grid",
            floatfmt=".4f",
        )
    )
    chiTabla = 12.5916 if fallo == 0.05 else 10.6446
    if sumatoria <= chiTabla:
        print(f"{sumatoria} ≤ {chiTabla}")
        print("Los numeros SI SON INDEPENDIENTES")
    else:
        print(f"{sumatoria} > {chiTabla}")
        print("Los numeros NO SON INDEPENDIENTES")


# -----------------------------------------------------------------------------
# MAIN

# Preguntar cantidad de numeros
while True:
    n = int(input("¿Cuantos numeros quieres generar? "))
    if n < 34 or n > 100:
        print("Total de numeros no permitidos debe ser ser entre 34-100")
        continue
    break

# Generar numeros aleatorios
aleat = []
for i in range(n):
    aleat.append(round(random(), 5))

# Imprimir numeros
print("Numeros Aleatorios")
for i in range(n):
    print(f"{i+1}. {'{:.5f}'.format(aleat[i-1])}")

# Preguntar prueba
while True:
    print("\nMenu")
    print("1. Chi Cuadrada")
    print("2. Kolmogorov")
    print("3. Serie")
    print("4. Huecos")
    print("5. Poker")
    print("6. Salir")
    opcion = int(input("¿Que prueba quieres aplicar? "))
    if opcion == 1:
        preguntarFallo()
        ChiCuadrada()
    elif opcion == 2:
        preguntarFallo()
        Kolmogorov()
    elif opcion == 3:
        preguntarFallo()
        Series()
    elif opcion == 4:
        preguntarFallo()
        Huecos()
    elif opcion == 5:
        preguntarFallo()
        Poker()
    elif opcion == 6:
        break
    else:
        print("Opcion no valida")
