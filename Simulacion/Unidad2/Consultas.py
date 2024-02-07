import random
import matplotlib.pyplot as plt
from tabulate import tabulate

dias = int(input("¿Cuantas veces deseas realizar la simulacion? "))
cantidad = [0] * 6
tabla = []

for i in range(1, dias + 1):
    aleatorio = random.random()
    if aleatorio <= 0.05:
        consultas = 0
    elif aleatorio <= 0.15:
        consultas = 1
    elif aleatorio <= 0.35:
        consultas = 2
    elif aleatorio <= 0.65:
        consultas = 3
    elif aleatorio <= 0.85:
        consultas = 4
    elif aleatorio <= 1:
        consultas = 5

    cantidad[consultas] += 1
    tabla.append([i, aleatorio, consultas])

print(tabulate(tabla, headers=["No.", "#Alegen", "Resultado"], tablefmt="rounded_grid"))

plt.pie(
    cantidad, labels=["Cero", "Uno", "Dos", "Tres", "Cuatro", "Cinco"], autopct="%.2f%%"
)
plt.show()
