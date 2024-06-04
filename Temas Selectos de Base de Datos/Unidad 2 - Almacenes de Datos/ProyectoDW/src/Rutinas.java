import java.util.Random;

public class Rutinas {

    private final static double TIPO_CAMBIO_EURO_DOLAR = 1.08,TIPO_CAMBIO_WON_DOLAR = 0.00074,
    TIPO_CAMBIO_YEN_DOLAR = 0.0066,TIPO_CAMBIO_RENMINBI_DOLAR = 0.14;

    private static Random r = new Random();
    private static int contador = -1;

    public static String PonCeros(String texto, int largo) {
        while (texto.length() < largo)
            texto = "0"+texto;
        return texto;
    }
    public static String corregirFechaImportaciones(String fecha){
        String[] separacion = fecha.split("/");
        separacion[0]=PonCeros(separacion[0],2);
        separacion[1] = PonCeros(separacion[1],2);
        if(separacion[1].equals("02") && (separacion[0].equals("31") || separacion[0].equals("30") || separacion[0].equals("29"))) {
            separacion[0] = (Integer.parseInt(separacion[2]) % 4 == 0 ? "29" : "28");
        }else if(separacion[1].equals("04") || separacion[1].equals("06") || separacion[1].equals("09") || separacion[1].equals("11")){
            separacion[0] = (separacion[0].equals("31") ? "30" : separacion[0]);
        }
        return separacion[2]+separacion[1]+separacion[0];
    }

    public static double convertirImporte(double importe,String origen){
        switch (origen){
            case "Spain":
                return importe*TIPO_CAMBIO_EURO_DOLAR;
            case "South Korea":
                return importe*TIPO_CAMBIO_WON_DOLAR;
            case "Germany":
                return importe*TIPO_CAMBIO_EURO_DOLAR;
            case "Japan":
                return importe*TIPO_CAMBIO_YEN_DOLAR;
            case "Italy":
                return importe*TIPO_CAMBIO_EURO_DOLAR;
            case "China":
                return importe*TIPO_CAMBIO_RENMINBI_DOLAR;
        }
        return importe;
    }

    public static String traducirPais(String pais){
        switch (pais){
            case "Spain":
                return "Espana";
            case "South Korea":
                return "Corea del Sur";
            case "Germany":
                return "Alemania";
            case "Japan":
                return "Japon";
            case "Italy":
                return "Italia";
            case "China":
                return "China";
        }
        return pais;
    }

    public static String traducirTransporte(String transporte){
        switch (transporte){
            case "Road":
                return "Carretera";
            case "Sea":
                return "Mar";
            case "Air":
                return "Cielo";
            case "Rail":
                return "Rieles";
        }
        return transporte;
    }

    public static String obtenerPaisOrigen(String marca){
        if(marca.equals("Dina") || marca.equals("VW"))
            return "Japon";
        return "USA";
    }

    public static String obtenerMedioTransporte(int anio,String estado){
        if(anio == 2021){
            if(estado.equals("BCS") || estado.equals("BC") || estado.equals("SON") || estado.equals("CHI") || estado.equals("COA")
            || estado.equals("NL") || estado.equals("TAM") || estado.equals("SIN") || estado.equals("SINALOA") || estado.equals("DUR")
            || estado.equals("DUR") || estado.equals("DURANGO") || estado.equals("ZAC") || estado.equals("SLP")){
                return "Carretera";
            }
            return "Rieles";
        }
        if(anio == 2022){
            int rand = r.nextInt(4);
            if(rand == 0) return "Carretera";
            if(rand == 1) return "Mar";
            if(rand == 2) return "Cielo";
            return "Rieles";
        }
        if(anio == 2023){
            contador = (contador+1)%4;
            if(contador == 0) return "Carretera";
            if(contador == 1) return "Mar";
            if(contador == 2) return "Cielo";
            return "Rieles";
        }
        return "";
    }

    public static String corregirFechaVentas(String fecha) {
        String[] separacion = fecha.split("/");
        String mesCorregido;
        switch (separacion[1]) {
            case "ene":
                mesCorregido = "01";
                break;
            case "feb":
                mesCorregido = "02";
                if (separacion[0].equals("31") || separacion[0].equals("30") || separacion[0].equals("29")) {
                    separacion[0] = (Integer.parseInt(separacion[2]) % 4 == 0 ? "29" : "28");
                }
                break;
            case "mar":
                mesCorregido = "03";
                break;
            case "abr":
                mesCorregido = "04";
                separacion[0] = (separacion[0].equals("31") ? "30" : separacion[0]);
                break;
            case "may":
                mesCorregido = "05";
                break;
            case "jun":
                mesCorregido = "06";
                separacion[0] = (separacion[0].equals("31") ? "30" : separacion[0]);
                break;
            case "jul":
                mesCorregido = "07";
                break;
            case "ago":
                mesCorregido = "08";
                break;
            case "sep":
                mesCorregido = "09";
                separacion[0] = (separacion[0].equals("31") ? "30" : separacion[0]);
                break;
            case "oct":
                mesCorregido = "10";
                break;
            case "nov":
                mesCorregido = "11";
                separacion[0] = (separacion[0].equals("31") ? "30" : separacion[0]);
                break;
            case "dic":
                mesCorregido = "12";
                break;
            default:
                mesCorregido = "01";
                break;
        }
        return "20" + separacion[2] + mesCorregido + separacion[0];
    }

    public static String limpiarCampo(String campo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campo.length(); i++) {
            if (campo.charAt(i) == '.')
                break;
            if (campo.charAt(i) != ' ' && campo.charAt(i) != '$')
                sb.append(campo.charAt(i));
        }
        return sb.toString();
    }

}
