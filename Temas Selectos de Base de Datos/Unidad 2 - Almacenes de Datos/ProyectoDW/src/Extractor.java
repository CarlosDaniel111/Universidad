import java.io.BufferedReader;
import java.io.FileReader;
public class Extractor {
    private BufferedReader bf;
    private String linea;

    public Extractor(String direccionArchivo) {
        try {
            bf = new BufferedReader(new FileReader(direccionArchivo));
            linea = bf.readLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean quedanTuplas() {
        return linea != null;
    }

    public String[] nextTupla() {
        String[] tupla = linea.split(",");
        try {
            linea = bf.readLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return tupla;
    }
}
