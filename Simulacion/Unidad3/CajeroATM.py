import math
import random
from tabulate import tabulate

simulaciones = int(input("¿Cuantas simulaciones quieres? "))
datos = []
tiempoEsperaTotal = 0
for i in range(simulaciones):
    momentoLlegada = 0
    tiempoTermino = 0
    for cliente in range(1, 101):
        alea1 = random.random()
        tiempoLlegada = -(math.log(1 - alea1) / 30) * 60
        momentoLlegada += tiempoLlegada
        tiempoInicia = max(momentoLlegada, tiempoTermino)
        tiempoEspera = tiempoInicia - momentoLlegada

        alea2 = random.random()
        if alea2 <= 0.25:
            operacion = "Consulta de Saldo"
            duracion = 80
        elif alea2 <= 0.5:
            operacion = "Otros"
            duracion = 50
        elif alea2 <= 0.85:
            operacion = "Retiros"
            duracion = 120
        else:
            operacion = "Transferencia"
            duracion = 60

        tiempoTermino = tiempoInicia + (duracion / 60)
        tiempoEsperaTotal += tiempoEspera
        datos.append(
            [
                cliente,
                alea1,
                tiempoLlegada,
                momentoLlegada,
                tiempoInicia,
                tiempoEspera,
                alea2,
                operacion,
                str(duracion) + " seg",
                tiempoTermino,
            ]
        )

print(
    tabulate(
        datos,
        headers=[
            "Usuario",
            "#alea1",
            "T. / llegada",
            "Mom. llegada",
            "T. Ini serv",
            "T. espera",
            "#alea2",
            "Operacion",
            "T. Operacion",
            "T. termina",
        ],
        tablefmt="rounded_grid",
    )
)

print(f"El tiempo total de espera fue {tiempoEsperaTotal}")
