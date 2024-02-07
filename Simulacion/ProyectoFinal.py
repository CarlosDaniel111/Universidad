from random import random
from tabulate import tabulate


def camionesIniciales(numRandom):
    if numRandom < 0.5:
        return 0
    if numRandom < 0.75:
        return 1
    if numRandom < 0.9:
        return 2
    return 3


def tiempoLlegada(numRandom):
    if numRandom < 0.02:
        return 20
    if numRandom < 0.1:
        return 25
    if numRandom < 0.22:
        return 30
    if numRandom < 0.47:
        return 35
    if numRandom < 0.67:
        return 40
    if numRandom < 0.82:
        return 45
    if numRandom < 0.92:
        return 50
    if numRandom < 0.97:
        return 55
    return 60


def tiempoServicio(numRandom, equipo):
    if equipo == 3:
        if numRandom < 0.05:
            return 20
        if numRandom < 0.15:
            return 25
        if numRandom < 0.35:
            return 30
        if numRandom < 0.60:
            return 35
        if numRandom < 0.72:
            return 40
        if numRandom < 0.82:
            return 45
        if numRandom < 0.90:
            return 50
        if numRandom < 0.96:
            return 55
        return 60
    elif equipo == 4:
        if numRandom < 0.05:
            return 15
        if numRandom < 0.2:
            return 20
        if numRandom < 0.4:
            return 25
        if numRandom < 0.6:
            return 30
        if numRandom < 0.75:
            return 35
        if numRandom < 0.87:
            return 40
        if numRandom < 0.95:
            return 45
        if numRandom < 0.99:
            return 50
        return 55
    elif equipo == 5:
        if numRandom < 0.1:
            return 10
        if numRandom < 0.28:
            return 15
        if numRandom < 0.5:
            return 20
        if numRandom < 0.68:
            return 25
        if numRandom < 0.78:
            return 30
        if numRandom < 0.86:
            return 35
        if numRandom < 0.92:
            return 40
        if numRandom < 0.97:
            return 45
        return 50
    if numRandom < 0.12:
        return 5
    if numRandom < 0.27:
        return 10
    if numRandom < 0.53:
        return 15
    if numRandom < 0.68:
        return 20
    if numRandom < 0.80:
        return 25
    if numRandom < 0.88:
        return 30
    if numRandom < 0.94:
        return 35
    if numRandom < 0.98:
        return 40
    return 45


def formatoHora(minutos):
    hora = minutos // 60
    minutos = minutos % 60

    if hora > 12:
        hora %= 12

    return str(hora).zfill(2) + ":" + str(minutos).zfill(2)


turnos = 60
datos3 = []
datos4 = []
datos5 = []
datos6 = []
datosPromedio = [[0] * 6, [0] * 6, [0] * 6, [0] * 6]
datosPromedio[0][0] = 3
datosPromedio[0][1] = 600
datosPromedio[1][0] = 4
datosPromedio[1][1] = 800
datosPromedio[2][0] = 5
datosPromedio[2][1] = 1000
datosPromedio[3][0] = 6
datosPromedio[3][1] = 1200

conta = 0

for i in range(4):
    K = 0
    for j in range(turnos):
        datos = []
        camiones = [[0 for _ in range(10)] for _ in range(30)]
        aleat = random()
        camionesIni = camionesIniciales(aleat)

        hora = 660

        contCamiones = 0

        # Calcular tiempo de llegada de los camiones
        for _ in range(camionesIni):
            camiones[contCamiones][2] = 660
            camiones[contCamiones][9] = camionesIni - contCamiones
            contCamiones += 1

        while hora <= 1170:
            aleat = random()
            camiones[contCamiones][0] = aleat
            camiones[contCamiones][1] = tiempoLlegada(aleat)
            camiones[contCamiones][2] = camiones[contCamiones][1] + hora
            hora = camiones[contCamiones][2]
            contCamiones += 1

        K += contCamiones
        contCamiones -= 1
        descanso = True
        ocioCamiones = 0

        for camion in range(contCamiones):
            # Calcular le inicio de servicio de cada camion
            aleat = random()
            if camion == 0:
                camiones[camion][3] = camiones[camion][2]
            elif camiones[camion][9] == 0:
                camiones[camion][3] = camiones[camion][2]
            else:
                camiones[camion][3] = camiones[camion - 1][6]

            if camiones[camion][3] >= 900 and descanso:
                camiones[camion][3] += 30
                descanso = False

            camiones[camion][4] = aleat
            camiones[camion][5] = tiempoServicio(aleat, i + 3)
            camiones[camion][6] = camiones[camion][5] + camiones[camion][3]

            # Calcular Ocio Personal
            camiones[camion][7] = camiones[camion][3] - 660
            if camion != 0:
                camiones[camion][7] = camiones[camion][3] - camiones[camion - 1][6]

            # Calcular Espera Camion
            camiones[camion][8] = camiones[camion][3] - camiones[camion][2]
            ocioCamiones += camiones[camion][8]

            # Calcular Longitud de la cola

            for x in range(camion + 1, contCamiones):
                if camiones[x][2] < camiones[camion][6]:
                    camiones[x][9] += 1
                else:
                    break
        # Ajustar cola
        for x in range(camionesIni):
            camiones[x][9] -= x
        # Guardar datos
        for y in range(contCamiones):
            if y < camionesIni:
                datos.append(
                    [
                        "",
                        "",
                        formatoHora(camiones[y][2]),
                        formatoHora(camiones[y][3]),
                        f"{camiones[y][4]:.5f}",
                        camiones[y][5],
                        formatoHora(camiones[y][6]),
                        camiones[y][7],
                        camiones[y][8],
                        camiones[y][9],
                    ]
                )
            else:
                datos.append(
                    [
                        f"{camiones[y][0]:.5f}",
                        camiones[y][1],
                        formatoHora(camiones[y][2]),
                        formatoHora(camiones[y][3]),
                        f"{camiones[y][4]:.5f}",
                        camiones[y][5],
                        formatoHora(camiones[y][6]),
                        camiones[y][7],
                        camiones[y][8],
                        camiones[y][9],
                    ]
                )

        # Calculo para el promedio
        horaExtra = max(camiones[contCamiones - 1][6], 1170) - 1170
        datosPromedio[i][2] += (horaExtra / 60) * 37.5 * (i + 3)
        datosPromedio[i][3] += (ocioCamiones / 60) * 100
        datosPromedio[i][4] += 4250 + ((horaExtra / 60) * 500)

        if i == 0:
            datos3.append(datos)
        elif i == 1:
            datos4.append(datos)
        elif i == 2:
            datos5.append(datos)
        else:
            datos6.append(datos)
    datosPromedio[i][2] /= 60
    datosPromedio[i][3] /= 60
    datosPromedio[i][4] /= 60
    datosPromedio[i][5] = (
        datosPromedio[i][2] + datosPromedio[i][3] + datosPromedio[i][4]
    )

    # Dar formato a la tabla
    datosPromedio[i][2] = f"{datosPromedio[i][2]:.5f}"
    datosPromedio[i][3] = f"{datosPromedio[i][3]:.5f}"
    datosPromedio[i][4] = f"{datosPromedio[i][4]:.5f}"
    datosPromedio[i][5] = f"{datosPromedio[i][5]:.5f}"


# IMPRESION DE TABLAS
print("Simulacion de 60 turnos con equipos de 3 personas")
for i in range(60):
    print("Turno " + str(i + 1))
    print(
        tabulate(
            datos3[i],
            headers=[
                "Num Aleat",
                "T / llegadas",
                "T de llegada",
                "Inic del serv",
                "Num Aleat",
                "T de servicio",
                "Term del serv",
                "Ocio del personal",
                "T de esp de camion",
                "Long de la cola",
            ],
            tablefmt="rounded_grid",
        )
    )

print("Simulacion de 60 turnos con equipos de 4 personas")
for i in range(60):
    print("Turno " + str(i + 1))
    print(
        tabulate(
            datos4[i],
            headers=[
                "Num Aleat",
                "T / llegadas",
                "T de llegada",
                "Inic del serv",
                "Num Aleat",
                "T de servicio",
                "Term del serv",
                "Ocio del personal",
                "T de esp de camion",
                "Long de la cola",
            ],
            tablefmt="rounded_grid",
        )
    )

print("Simulacion de 60 turnos con equipos de 5 personas")
for i in range(60):
    print("Turno " + str(i + 1))
    print(
        tabulate(
            datos5[i],
            headers=[
                "Num Aleat",
                "T / llegadas",
                "T de llegada",
                "Inic del serv",
                "Num Aleat",
                "T de servicio",
                "Term del serv",
                "Ocio del personal",
                "T de esp de camion",
                "Long de la cola",
            ],
            tablefmt="rounded_grid",
        )
    )

print("Simulacion de 60 turnos con equipos de 6 personas")
for i in range(60):
    print("Turno " + str(i + 1))
    print(
        tabulate(
            datos6[i],
            headers=[
                "Num Aleat",
                "T / llegadas",
                "T de llegada",
                "Inic del serv",
                "Num Aleat",
                "T de servicio",
                "Term del serv",
                "Ocio del personal",
                "T de esp de camion",
                "Long de la cola",
            ],
            tablefmt="rounded_grid",
        )
    )

print("Tabla de promedios considerando diferentes tamaños de equipo")

print(
    tabulate(
        datosPromedio,
        headers=[
            "Tamaño del equipo",
            "Salario normal",
            "Salario Extra",
            "Ocio del camion",
            "Operacion del almacen",
            "Costos Totales",
        ],
        tablefmt="rounded_grid",
    )
)
