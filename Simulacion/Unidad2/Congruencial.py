import random
from tabulate import tabulate


def isPrimo(x):
    lim = int(x**0.5) + 1
    for i in range(3, lim):
        if x % i == 0:
            return False
    return True


def generador(a, x, c, m):
    lista = []
    listaX = []
    n = 0

    while True:
        listaX.append(x)
        renglon = [n, x, a * x + c, f"{(a * x +c)} / {m}", (a * x + c) % m]
        x = (a * x + c) % m
        n += 1
        lista.append(renglon)
        if x in listaX:
            renglon = [n, x, a * x + c, f"{(a*x+c)} / {m}", (a * x + c) % m]
            lista.append(renglon)
            break

    print(
        tabulate(
            lista,
            headers=["n", "x", "axₙ + c", "(axₙ + c) / m", "xₙ₊₁"],
            tablefmt="rounded_grid",
        )
    )


while True:
    a = int(input("\nIngrese el valor de A: "))
    if a % 2 == 1 and a % 3 != 0 and a % 5 != 0:
        break
    else:
        print("Numero no valido")

while True:
    print("\nComo generar el valor de X?")
    print("1. Introducir el valor por teclado")
    print("2. Generar un valor random")
    opcion = int(input("Selecciona una opcion: "))
    if opcion in (1, 2):
        break
    else:
        print("Opcion no valida")

if opcion == 1:
    x = int(input("Ingresa el valor de X: "))
else:
    x = random.randint(1, 10000)

while True:
    c = int(input("\nIngrese el valor de C: "))
    if c % 200 == 21:
        break
    else:
        print("Numero no valido")

while True:
    print("\nComo generar el valor de M?")
    print("1. Generar el valor al azar")
    print("2. Introducir un valor por teclado")
    opcion = int(input("Selecciona una opcion: "))
    if opcion in (1, 2):
        break
    else:
        print("Opcion no valida")
if opcion == 1:
    while True:
        m = random.randint(10, 10000)
        if isPrimo(m):
            break
else:
    while True:
        m = int(input("Ingresa el valor de M: "))
        if isPrimo(m):
            break

generador(a, x, c, m)
