import java.util.*;
import java.awt.Image;
import javax.swing.*;

public class Rutinas {
	static Random r = new Random();

	static String[] VN = { "Zoila", "Daniel", "Yessenia", "Luis", "Anastacia", "Plutarco", "Alicia", "Maria", "Sofia",
			"Antonio", "Nereida", "Carolina", "Rebeca", "Javier", "Luis" };
	static String[] VA = { "Garcia", "Lopez", "Perez", "Urias", "Mendoza", "Coppel", "Diaz" };
	static boolean[] Sexo = { false, true, false, true, false, true, false, false, false, true, false, false, false,
			true, true };

	public static void Mensaje(String texto) {
		JOptionPane.showMessageDialog(null, texto, " ******** ", JOptionPane.ERROR_MESSAGE);
		// int resp = JOptionPane.showConfirmDialog(null, "�Est� seguro?");
	}

	public static String nextNombre(int Numero) {
		String Nom = "", NomTra = "";
		int Pos;
		boolean Genero = true;

		for (int i = 0; i < Numero; i++) {
			Pos = nextInt(VN.length);

			NomTra = VN[Pos] + " ";

			if (i == 0) {
				Genero = Sexo[Pos];

			}

			if (Genero != Sexo[Pos] || i > 0 && Nom.indexOf(NomTra) > -1) {
				i--;
				continue;
			}

			Nom += NomTra;

		}
		for (byte i = 0; i < 2; i++) {
			Nom += VA[nextInt(VA.length)] + " ";
		}
		return Nom.trim();
	}

	public static int nextInt(int li, int ls) {
		return r.nextInt(ls - li + 1) + li;

	}

	public static int nextInt(int numero) {
		return r.nextInt(numero);
	}

	/*
	 * public static String PonComas(long cantidad) { String texto=cantidad+""; int
	 * cont=0; char car; String res=""; for(int i=texto.length()-1; i>=0 ; i--) {
	 * car=texto.charAt(i); cont++; if(cont ==4 ) { res=","+res; cont=1; }
	 * res=car+res; } return res; }
	 */
	public static String PonComas(long cantidad) {
		String texto = cantidad + "";

		String res = "";
		int l;
		while (texto.length() > 3) {
			l = texto.length();
			res = "," + texto.substring(l - 3) + res;
			texto = texto.substring(0, l - 3);

		}
		if (texto.length() > 0)
			res = texto + res;
		return res;
	}

	public static String Color() {
		String[] color = { "Azul", "Amarillo", "Negro", "Blanco", "Rojo", "Verde", "Purpura" };
		return color[Rutinas.nextInt(0, color.length - 1)];
	}

	public static String PonBlancos(String texto, int largo) {
		while (texto.length() < largo)
			texto = texto + " ";
		return texto;

	}

	public static String PonCeros(String texto, int largo) {
		while (texto.length() < largo)
			texto = '0' + texto;
		return texto;

	}

	public static ImageIcon AjustarImagen(String ico, int Ancho, int Alto) {
		ImageIcon tmpIconAux = new ImageIcon(ico);
		// Escalar Imagen
		ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(Ancho, Alto, Image.SCALE_SMOOTH));// SCALE_DEFAULT
		return tmpIcon;
	}

	public static String corregirFecha(String fecha) {
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
		return "20" + separacion[2] + "/" + mesCorregido + "/" + separacion[0];
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
