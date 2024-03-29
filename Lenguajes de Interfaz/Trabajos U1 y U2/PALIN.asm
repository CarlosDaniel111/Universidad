; INTEGRANTES:
; AVILES BRAVO CESAR AMADO
; BELTRAN MEDINA CARLOS DANIEL
; BELTRAN ONTIVEROS KAREN VALERIA
; GARCIA SANCHEZ SERGIO JESUS
; VALENZUELA BERRELLEZA CESAR JESUS

TITLE LEE HASTA 50 CHARS Y DETERMINA SI ES PALINDROMO
.MODEL SMALL
.STACK 100H
.DATA
    MSG1 DB 'DAME HASTA 50 CHARS: $'
    MSG2 DB 10,13,'SI ES PALINDROMO$'
    MSG3 DB 10,13,'NO ES PALINDROMO$'
    CADENA DB 50 DUP('$')
.CODE
MAIN PROC
    MOV AX,@DATA
    MOV DS,AX
    
    ;DESPLEGAR MENSAJE A USUARIO
    LEA DX,MSG1
    MOV AH,9
    INT 21H
    
    ;INICIALIZAR CX, PARA LA CUENTA DE CUANTOS CARACTERES LEYO Y SI AL INICIO DEL STRING
    XOR CX,CX
    XOR SI,SI
CICLO:
    ;LEER 1 CHAR
    MOV AH,1
    INT 21H
    
    ;VERIFICAR SI FUE ENTER
    CMP AL,13
    JE SIGUE
    
    CMP AL,'a'
    JL MAYUSCULA
    CMP AL,'z'
    JG CICLO
MINUSCULA:
    SUB AL,32
    JMP GUARDA
MAYUSCULA:
    CMP AL,'A'
    JL NUMERO
    CMP AL,'Z'
    JLE GUARDA
NUMERO:
    CMP AL,'0'
    JL CICLO
    CMP AL,'9'
    JG CICLO
GUARDA:
    ;ALMACENAR EL CARACTER
    MOV CADENA[SI],AL
    ;INCREMENTAR NUMERO DE  CHARS
    INC CX
    ;AVANZAR EL APUNTADOR
    INC SI
    CMP CX,50
    JE SIGUE
    JMP CICLO
SIGUE:
    ;UN APUNTADOR ESTARA AL FINAL EN CX - 1
    MOV SI,CX
    DEC SI
    ;EL OTRO APUNTADOR ESTARA AL PRINCIPIO
    XOR DI,DI
    ;HACER LA MITAD DE COMPARACIONES
    SHR CX,1
COMPARA:
    MOV AL,CADENA[SI]
    CMP AL,CADENA[DI]
    JNE NO_PALINDROME
    DEC SI
    INC DI
    LOOP COMPARA
SI_PALINDROME:
    LEA DX,MSG2
    MOV AH,09
    INT 21H
    JMP FIN
NO_PALINDROME:
    LEA DX,MSG3
    MOV AH,9
    INT 21H
FIN:
    MOV AH,4CH
    INT 21H
MAIN ENDP
    END MAIN