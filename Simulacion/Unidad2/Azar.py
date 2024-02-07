import random
import matplotlib.pyplot as plt
from tabulate import tabulate


def moneda():
    lanzamientos = int(input("¿Cuantas veces deseas lanzar la moneda? "))
    arr = []
    sello = aguila = 0
    for i in range(1, lanzamientos + 1):
        numran = random.random()

        if numran <= 0.5:
            evento = "Aguila"
            aguila += 1
        elif numran <= 1:
            evento = "Sello"
            sello += 1
        arr.append([i, numran, evento])

    print(tabulate(arr, headers=["No.", "#aleagen", "Evento"], tablefmt="rounded_grid"))
    plt.pie([aguila, sello], labels=["Aguila", "Sello"], autopct="%.2f%%")
    plt.show()


def dado():
    lanzamientos = int(input("¿Cuantas veces deseas lanzar el dado? "))
    arr = []
    dado = [0, 0, 0, 0, 0, 0]
    for i in range(1, lanzamientos + 1):
        numran = random.random()
        if numran <= 1 / 6:
            evento = "Uno"
            dado[0] += 1
        elif numran <= 2 / 6:
            evento = "Dos"
            dado[1] += 1
        elif numran <= 3 / 6:
            evento = "Tres"
            dado[2] += 1
        elif numran <= 4 / 6:
            evento = "Cuatro"
            dado[3] += 1
        elif numran <= 5 / 6:
            evento = "Cinco"
            dado[4] += 1
        elif numran <= 6 / 6:
            evento = "Seis"
            dado[5] += 1
        arr.append([i, numran, evento])

    print(tabulate(arr, headers=["No.", "#aleagen", "Evento"], tablefmt="rounded_grid"))
    plt.pie(
        dado, labels=["Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis"], autopct="%.2f%%"
    )
    plt.show()


def ruleta():
    lados = int(input("¿De cuantos lados la ruleta? "))
    lanzamientos = int(input("¿Cuantas veces deseas girar la ruleta? "))
    arr = []
    ruleta = [0] * lados
    for i in range(1, lanzamientos + 1):
        numran = random.random()
        for j in range(1, lados + 1):
            if numran <= j / lados:
                evento = f"Lado {j}"
                ruleta[j - 1] += 1
                break
        arr.append([i, numran, evento])
    labels = []
    for i in range(1, lados + 1):
        labels.append(f"Lado {i}")
    print(tabulate(arr, headers=["No.", "#aleagen", "Evento"], tablefmt="rounded_grid"))
    plt.pie(ruleta, labels=labels, autopct="%.2f%%")
    plt.show()


while True:
    print("---MENU---")
    print("1. Moneda")
    print("2. Dado")
    print("3. Ruleta")
    print("4. Salir")
    opcion = int(input("Selecciona un juego de azar: "))
    if opcion == 4:
        break
    if opcion not in (1, 2, 3):
        continue
    if opcion == 1:
        moneda()
    elif opcion == 2:
        dado()
    else:
        ruleta()
