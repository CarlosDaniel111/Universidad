public class Ejemplo {
    private String nivelRenta,patrimonio,funcionario,nivelEstudios,autorizo;
    private int tamanoCredito,edad,hijos;

    public Ejemplo(String nivelRenta, String patrimonio, int tamanoCredito, int edad, int hijos, String funcionario, String nivelEstudios, String autorizo) {
        this.nivelRenta = nivelRenta;
        this.patrimonio = patrimonio;
        this.funcionario = funcionario;
        this.nivelEstudios = nivelEstudios;
        this.autorizo = autorizo;
        this.tamanoCredito = tamanoCredito;
        this.edad = edad;
        this.hijos = hijos;
    }

    public String getNivelRenta() {
        return nivelRenta;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public String getNivelEstudios() {
        return nivelEstudios;
    }

    public String getAutorizo() {
        return autorizo;
    }

    public int getTamanoCredito() {
        return tamanoCredito;
    }

    public int getEdad() {
        return edad;
    }

    public int getHijos() {
        return hijos;
    }

    @Override
    public String toString() {
        return  nivelRenta + ',' + patrimonio + ',' + tamanoCredito + ','+edad +','+hijos+','+funcionario+','+nivelEstudios+','+autorizo;
    }
}
