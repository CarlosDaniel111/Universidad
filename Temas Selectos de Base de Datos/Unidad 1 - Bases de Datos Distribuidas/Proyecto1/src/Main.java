public class Main {
    public static void main(String[] args) {
        BaseDeDatos bd = new BaseDeDatos("localhost", "EMPRESA2", "sa", "sa");
        Vista vista = new Vista();
        Controlador controlador = new Controlador(vista, bd);
        vista.setEscuchador(controlador);
    }
}