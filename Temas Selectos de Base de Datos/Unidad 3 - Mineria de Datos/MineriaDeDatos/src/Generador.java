import java.util.Random;

public class Generador {
    static Random rand = new Random();
    public static Ejemplo generarEjemplo(){
        // Calcular nivel de renta
        double r = rand.nextDouble();
        String nivelRenta;
        if(r<1.0/3.0){
            nivelRenta = "alto";
        }else if(r<2.0/3.0) {
            nivelRenta = "medio";
        }else{
            nivelRenta = "bajo";
        }

        // Calcular patrimonio
        String patrimonio;
        r = rand.nextDouble();

        if(nivelRenta.equals("alto")){
            if(r<0.8){
                patrimonio = "alto";
            }else{
                patrimonio = "medio";
            }
        }else if(nivelRenta.equals("medio")){
            if(r<0.2){
                patrimonio = "alto";
            }else if(r<0.8){
                patrimonio = "medio";
            }else{
                patrimonio = "bajo";
            }
        }else{
            if(r<0.9){
                patrimonio = "bajo";
            }else{
                patrimonio = "medio";
            }
        }

        //Calcular tamano de credito
        int tamanoCredito = 0;
        if(patrimonio.equals("alto")){
            tamanoCredito = 50000 + 1000*rand.nextInt(0,51);
        }else if(patrimonio.equals("medio")){
            tamanoCredito = 30000 + 1000*rand.nextInt(0,51);
        }else{
            tamanoCredito = 1000 + 1000*rand.nextInt(0,51);
        }

        //Calcular edad
        int edad = rand.nextInt(20,61);

        //Calcular numero de hijos
        int hijos;
        if(nivelRenta.equals("bajo") && patrimonio.equals("bajo")){
            hijos = rand.nextInt(4,7);
        }else{
            hijos = rand.nextInt(3);
        }

        //Calcular nivel estudios
        r = rand.nextDouble();
        String nivelEstudios;

        if(patrimonio.equals("alto")){
            if(r<0.6){
                nivelEstudios = "posgrado";
            }else if(r<0.95){
                nivelEstudios = "licenciatura";
            }else{
                nivelEstudios = "ninguno";
            }
        }else if(patrimonio.equals("medio")){
            if(r<0.2){
                nivelEstudios = "posgrado";
            }else if(r<0.8){
                nivelEstudios = "licenciatura";
            }else{
                nivelEstudios = "ninguno";
            }
        }else{
            if(r<0.6){
                nivelEstudios = "ninguno";
            }else if(r<0.95){
                nivelEstudios = "licenciatura";
            }else{
                nivelEstudios = "posgrado";
            }
        }

        //Calcular funcionario
        String funcionario;
        r = rand.nextDouble();
        if(nivelEstudios.equals("licenciatura") || nivelEstudios.equals("posgrado")){
            if(r<0.8){
                funcionario = "si";
            }else{
                funcionario = "no";
            }
        }else{
            if(r<0.5){
                funcionario = "si";
            }else{
                funcionario = "no";
            }
        }

        //Calcular autorizo
        String autorizo;
        r = rand.nextDouble();
        if(patrimonio.equals("alto")){
            if(funcionario.equals("si")) autorizo = r<0.9 ? "si" : "no";
            else{
                if(tamanoCredito <= 80000) autorizo = r<0.9 ? "si" : "no";
                else{
                    if(edad < 40 && hijos<=1) autorizo = r < 0.8 ? "si" : "no";
                    else autorizo = r < 0.25 ? "si" : "no";
                }
            }
        }else if(patrimonio.equals("medio")){
            if(funcionario.equals("si")) autorizo = r < 0.75 ? "si" : "no";
            else{
                if(tamanoCredito <= 50000) autorizo = r < 0.75 ? "si" : "no";
                else{
                    if(edad < 30 && hijos==0) autorizo = r < 0.6 ? "si" : "no";
                    else autorizo = r < 0.25 ? "si" : "no";
                }
            }
        }else{
            if(nivelRenta.equals("bajo")) autorizo = r < 0.15 ? "si" : "no";
            else{
                if(funcionario.equals("si")) autorizo = r < 0.65 ? "si" : "no";
                else{
                    if(tamanoCredito <= 20000) autorizo= r< 0.6 ? "si" : "no";
                    else if(edad<30) autorizo =  r < 0.5 ? "si" : "no";
                    else autorizo = r < 0.25 ? "si" : "no";
                }
            }

        }

        return new Ejemplo(nivelRenta,patrimonio,tamanoCredito,edad,hijos,funcionario,nivelEstudios,autorizo);
    }
}
