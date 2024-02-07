import random


def MediosCuadrados(numero):
    listaNumeros = [numero]
    while True:
        cuadrado = str(numero**2)
        cuadrado = ("0" * (8 - len(cuadrado))) + cuadrado
        numero = int(cuadrado[2:6])
        if numero == 0 or numero in listaNumeros:
            listaNumeros.append(numero)
            break
        listaNumeros.append(numero)
    for index, numero in enumerate(listaNumeros):
        print(f"{index + 1}. {numero:04}")


def Newman(numero):
    listaNumeros = []
    while True:
        cuadrado = str(numero**2)
        if len(cuadrado) % 2 == 0:
            cuadrado = "0" + cuadrado
        tam = len(cuadrado)
        numero = int(cuadrado[int(tam / 2 - 2) : int(tam / 2 + 3)])
        if numero == 0 or numero in listaNumeros:
            listaNumeros.append(numero)
            break
        listaNumeros.append(numero)
    for index, numero in enumerate(listaNumeros):
        print(f"{index + 1}. {numero:05}")


while True:
    print("\nMENU")
    print("1. Metodo de Medios Cuadrados")
    print("2. Metodo de Newman")
    print("3. Salir")
    metodo = int(input("Selecciona un metodo: "))

    if metodo == 3:
        break
    elif metodo != 1 and metodo != 2:
        continue

    while True:
        print("\nComo generar la semilla?")
        print("1. Semilla por teclado")
        print("2. Semilla por funcion random")
        opcion = int(input("Seleccione una opcion: "))
        if opcion in (1, 2):
            break

    semilla = 0

    if opcion == 1:
        while True:
            semilla = int(input("Ingresa la semilla: "))
            if metodo == 1 and semilla >= 1000 and semilla < 10000:
                break
            elif metodo == 2 and semilla >= 1000000000 and semilla < 10000000000:
                break
    else:
        if metodo == 1:
            semilla = random.randint(1000, 9999)
        else:
            semilla = random.randint(1000000000, 9999999999)

    print(f"Semilla: {semilla}")
    if metodo == 1:
        MediosCuadrados(semilla)
    else:
        Newman(semilla)
