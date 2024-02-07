TITLE BURBLE SORT EN ENSAMBLADOR, VERSION 2

SAVE_REGS MACRO REGS
    IRP D,<REGS>
        PUSH D
    ENDM
ENDM

RESTORE_REGS MACRO REGS
    IRP D,<REGS>
    POP D
    ENDM
ENDM

.MODEL SMALL
.STACK 100H
.DATA
    ARRAY DB 10,9,8,7,6,5,4,3,2,1
    ARRAY2 DB 105, 104, 103, 102, 101
.code
main proc
    MOV AX, @DATA
    MOV DS, AX
    ;IMPLEMENTACION DE BUBLE SORT
    
    ;ORDENA EL PASO EN BX, LA DIRECCION DE INICIO DE UN ARREGLO Y EN CX EL TAMA?O
    ;ES UN ARREGLO DE BYTES
    LEA bx, array
    mov cx,10
    CALL ORDENA
    
    LEA BX, ARRAY2
    MOV CX,5
    CALL ORDENA
    
    MOV AH, 4CH
    INT 21H
MAIN ENDP

ORDENA PROC

    SAVE_REGS <AX,CX,SI,DI,DX>
    
    XOR SI,SI
    ;SE REALIZA SIZE -1 VECES
    DEC CX
CICLO1:
    ;DI ES MI INDICE J, SE HACE IGUAL A I+1
    MOV DI,SI
    INC DI
    ;CX, CONTROLAR2 AMBOS CICLOS, POR ESO LO METO A LA PILA
    PUSH CX
CICLO2:
    MOV AL,[BX][SI]
    CMP AL,[BX][DI]
    ;SI ES MENOR O IGUAL, YA ESTA ORDENADO
    JLE NEXT
INTERCAMBIA:
    MOV AL,[BX][SI]
    XCHG AL,[BX][DI]
    MOV [BX][SI],AL
NEXT:
    INC DI
    LOOP CICLO2
    POP CX
    INC SI
    LOOP CICLO1
    
    RESTORE_REGS <DX,DI,SI,CX,AX>
    RET
ORDENA ENDP
    END MAIN
    