import java.io.*;

public class ARFF {
    private String ruta;
    public ARFF(String ruta){
        this.ruta = ruta;
        try{
            File fichero = new File(ruta);
            if(!fichero.exists()) {
                fichero.createNewFile();
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public boolean iniciarArchivo(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));
            bw.write("");
            bw.append("@relation creditos\n\n");
            bw.append("@attribute nivelRenta {alto,medio,bajo}\n");
            bw.append("@attribute patrimonio {alto,medio,bajo}\n");
            bw.append("@attribute tamanoCredito REAL\n");
            bw.append("@attribute edadPeticionario REAL\n");
            bw.append("@attribute numeroHijos REAL\n");
            bw.append("@attribute funcionario {si,no}\n");
            bw.append("@attribute nivelEstudios {ninguno,licenciatura,posgrado}\n");
            bw.append("@attribute autorizo {si,no}\n\n");
            bw.append("@data\n");
            bw.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean insertarEjemplos(int cantidad){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(ruta,true));
            for(int i=0;i<cantidad;i++){
                Ejemplo ejemplo = Generador.generarEjemplo();
                bw.append(ejemplo + "\n");
            }
            bw.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
}
