package domino;

/*
 * INSTITUTO TECNOLOGICO DE CULIACAN
 * ING. EN SISTEMAS COMPUTACIONALES
 * TOPICOS AVANZADOS DE PROGRAMACIÓN 09-10
 * DOMINO
 * ALUMNO: CARLOS DANIEL BELTRÁN MEDINA
 * DOCENTE: DR. CLEMENTE GARCIA GERARDO
 */

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
public class Rutinas {
	public static ImageIcon AjustarImagen(String ico,int Ancho,int Alto){
		ImageIcon tmpIconAux = new ImageIcon(ico);
		//Escalar Imagen
		ImageIcon tmpIcon = new ImageIcon(tmpIconAux.getImage().getScaledInstance(Ancho,
				Alto, Image.SCALE_SMOOTH));//SCALE_DEFAULT
		return tmpIcon;
	}
	
	public static ImageIcon rotarImagen(String ico,int grados,int ancho,int alto){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(ico));
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		double rads = Math.toRadians(grados);
	    double sin = Math.abs(Math.sin(rads));
	    double cos = Math.abs(Math.cos(rads));
	    int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
	    int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
	    BufferedImage rotatedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
	    AffineTransform at = new AffineTransform();
	    at.translate(w / 2, h / 2);
	    at.rotate(rads,0, 0);
	    at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
	    AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
	    rotateOp.filter(image,rotatedImage);
		Image scaledImage = rotatedImage.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(scaledImage);
	}

}
