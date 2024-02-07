import random
from tabulate import tabulate
import math

anios = int(input("¿Cuantos años se desean simular? "))

datos = []
costeFinal = 0

for anio in range(1, anios + 1):
    costeAnual = 0
    for dia in range(1, 261):
        pesoTotal = 0
        for tina in range(1, 6):
            alea = random.random()
            if alea < 0.5:
                peso = 190 + math.sqrt(800 * alea)
            elif alea == 0.5:
                peso = 210
            else:
                peso = 230 - math.sqrt(800 * (1 - alea))

            pesoTotal += peso

            if tina == 1:
                datos.append(
                    [anio if dia == 1 else "", dia, tina, alea, peso, pesoTotal, "", ""]
                )
            else:
                datos.append(
                    [
                        "",
                        "",
                        tina,
                        alea,
                        peso,
                        pesoTotal,
                        "Si" if pesoTotal > 1000 else "No",
                        "+$200" if pesoTotal > 1000 else "",
                    ]
                )
        if pesoTotal > 1000:
            costeAnual += 200

    costeFinal += costeAnual

print(
    tabulate(
        datos,
        headers=[
            "Año",
            "Dia",
            "Tina",
            "#aleaR",
            "Peso Simulado",
            "Peso Sim. Acumulado",
            "Excede capacidad?",
            "Si si, acumula $200",
        ],
        tablefmt="rounded_grid",
    )
)
costeRentando = costeFinal / anios
costeComprando = 50_000
print(f"Coste promedio anual rentando camion: ${costeRentando}")
print(f"Coste promedio anual comprando camion: ${costeComprando}")
if costeRentando <= costeComprando:
    print("Es mejor rentar el camion")
else:
    print("Es mejor comprar el camion")
