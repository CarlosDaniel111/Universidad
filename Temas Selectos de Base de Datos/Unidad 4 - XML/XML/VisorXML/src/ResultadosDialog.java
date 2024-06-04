import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import org.w3c.dom.*;

public class ResultadosDialog extends JDialog {

    private JTable tablaTipos, tablaMovimientos, tablaHabilidades, tablaPokemon;
    private DefaultTableModel modeloTipos, modeloMovimientos, modeloHabilidades, modeloPokemon;
    private Document doc;

    public ResultadosDialog(JFrame parent, Document doc) {
        super(parent, "Resultados", true);
        this.doc = doc;

        HazInterfaz();
    }

    private void HazInterfaz() {
        setSize(1024, 800);
        setLocationRelativeTo(null);
        JLabel label = new JLabel("Resultados", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.NORTH);

        JPanel panelTablas = new JPanel();
        panelTablas.setLayout(new GridLayout(2, 2, 10, 10));

        modeloTipos = new DefaultTableModel(new String[] { "ID", "Nombre", "Inmunidad", "Resistencia", "Debilidad" },
                0);
        tablaTipos = new JTable(modeloTipos);

        modeloMovimientos = new DefaultTableModel(
                new String[] { "ID", "Categoria", "Nombre", "Tipo", "PP", "Potencia", "Precision", "Efecto" }, 0);
        tablaMovimientos = new JTable(modeloMovimientos);

        modeloHabilidades = new DefaultTableModel(new String[] { "ID", "Nombre", "Efecto" }, 0);
        tablaHabilidades = new JTable(modeloHabilidades);

        modeloPokemon = new DefaultTableModel(
                new String[] { "ID", "Nombre", "Altura", "Peso", "Tipo 1", "Tipo 2", "Habilidades", "Estadisticas",
                        "Movimientos" },
                0);
        tablaPokemon = new JTable(modeloPokemon);

        panelTablas.add(new JScrollPane(tablaTipos));
        panelTablas.add(new JScrollPane(tablaMovimientos));
        panelTablas.add(new JScrollPane(tablaHabilidades));
        panelTablas.add(new JScrollPane(tablaPokemon));

        add(panelTablas, BorderLayout.CENTER);
        LlenarTablas();
        setVisible(true);
    }

    private void LlenarTablas() {
        doc.getDocumentElement().normalize();

        // Tabla Tipos
        NodeList tipoList = doc.getElementsByTagName("Tipo");

        for (int i = 0; i < tipoList.getLength(); i++) {
            Node tipoNode = tipoList.item(i);
            if (tipoNode.getNodeType() == Node.ELEMENT_NODE) {
                Element tipoElement = (Element) tipoNode;

                String id = tipoElement.getAttribute("id");
                String nombreTipo = getContenido(tipoElement, "NombreTipo");
                String inmunidad = getContenido(tipoElement, "Inmunidad");

                String resistencias = getTextoHijos(tipoElement, "Resistencia", "TipoResistente");
                String debilidades = getTextoHijos(tipoElement, "Debilidad", "TipoDebil");

                modeloTipos.addRow(new String[] { id, nombreTipo, inmunidad, resistencias, debilidades });
            }
        }

        // Tabla Movimientos
        NodeList movimientoList = doc.getElementsByTagName("Movimiento");

        for (int i = 0; i < movimientoList.getLength(); i++) {
            Node movimientoNode = movimientoList.item(i);
            if (movimientoNode.getNodeType() == Node.ELEMENT_NODE) {
                Element movimientoElement = (Element) movimientoNode;

                String id = movimientoElement.getAttribute("id");
                String categoria = movimientoElement.getAttribute("Categoria");
                String nombreMovimiento = getContenido(movimientoElement, "NombreMovimiento");
                String tipoMovimiento = getContenido(movimientoElement, "TipoMovimiento");
                String pp = getContenido(movimientoElement, "PP");
                String potencia = getContenido(movimientoElement, "Potencia");
                String precision = getContenido(movimientoElement, "Precision");
                String efectoMovimiento = getContenido(movimientoElement, "EfectoMovimiento");

                modeloMovimientos
                        .addRow(new String[] { id, categoria, nombreMovimiento, tipoMovimiento, pp, potencia,
                                precision, efectoMovimiento });
            }
        }

        // Tabla Habilidades
        NodeList habilidadList = doc.getElementsByTagName("Habilidad");

        for (int i = 0; i < habilidadList.getLength(); i++) {
            Node habilidadNode = habilidadList.item(i);
            if (habilidadNode.getNodeType() == Node.ELEMENT_NODE) {
                Element habilidadElement = (Element) habilidadNode;

                String id = habilidadElement.getAttribute("id");
                String nombreHabilidad = getContenido(habilidadElement, "NombreHabilidad");
                String efectoHabilidad = getContenido(habilidadElement, "EfectoHabilidad");

                modeloHabilidades.addRow(new String[] { id, nombreHabilidad, efectoHabilidad });
            }
        }

        // Tabla Pokemon
        NodeList pokemonList = doc.getElementsByTagName("Pokemon");

        for (int i = 0; i < pokemonList.getLength(); i++) {
            Node pokemonNode = pokemonList.item(i);
            if (pokemonNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pokemonElement = (Element) pokemonNode;

                String id = pokemonElement.getAttribute("id");
                String nombre = getContenido(pokemonElement, "NombrePokemon");
                String altura = pokemonElement.getAttribute("altura");
                String peso = pokemonElement.getAttribute("peso");
                String tipo1 = getContenido(pokemonElement, "Tipo1");
                String tipo2 = getContenido(pokemonElement, "Tipo2");

                String habilidades = getHabilidades(pokemonElement);
                String estadisticas = getEstadisticas(pokemonElement);
                String movimientos = getTextoHijos(pokemonElement, "MovimientosPokemon", "MovimientoPokemon");

                // Agregar fila a la tabla
                modeloPokemon.addRow(new String[] { id, nombre, altura, peso, tipo1, tipo2, habilidades,
                        estadisticas, movimientos });
            }
        }
    }

    private static String getTextoHijos(Element elementoPadre, String elemento, String elementoHijo) {
        NodeList listaElementos = elementoPadre.getElementsByTagName(elemento);
        if (listaElementos.getLength() > 0) {
            Element padre = (Element) listaElementos.item(0);
            NodeList listaElementosHijos = padre.getElementsByTagName(elementoHijo);

            StringBuilder texto = new StringBuilder();
            for (int i = 0; i < listaElementosHijos.getLength(); i++) {
                texto.append(listaElementosHijos.item(i).getTextContent());
                texto.append(", ");
            }
            if (texto.length() > 0) {
                texto.setLength(texto.length() - 2);
            }
            return texto.toString();
        }
        return "";
    }

    private static String getContenido(Element elementoPadre, String elemento) {
        NodeList listaElementos = elementoPadre.getElementsByTagName(elemento);
        if (listaElementos.getLength() > 0) {
            return listaElementos.item(0).getTextContent();
        }
        return "";
    }

    private static String getHabilidades(Element pokemonElemento) {
        NodeList listaHabilidades = pokemonElemento.getElementsByTagName("HabilidadPokemon");
        StringBuilder habilidades = new StringBuilder();
        for (int j = 0; j < listaHabilidades.getLength(); j++) {
            Node habilidadNode = listaHabilidades.item(j);
            if (habilidadNode.getNodeType() == Node.ELEMENT_NODE) {
                Element habilidadElement = (Element) habilidadNode;
                String habilidad = habilidadElement.getTextContent();
                habilidades.append(habilidad).append(" (").append(habilidadElement.getAttribute("esOculta")).append(")")
                        .append(", ");
            }
        }
        if (habilidades.length() > 0) {
            habilidades.setLength(habilidades.length() - 2);
        }
        return habilidades.toString();
    }

    private static String getEstadisticas(Element pokemonElement) {
        Element estadisticasElement = (Element) pokemonElement.getElementsByTagName("Estadisticas").item(0);
        String vida = getContenido(estadisticasElement, "Vida");
        String ataque = getContenido(estadisticasElement, "Ataque");
        String defensa = getContenido(estadisticasElement, "Defensa");
        String ataqueEspecial = getContenido(estadisticasElement, "AtaqueEspecial");
        String defensaEspecial = getContenido(estadisticasElement, "DefensaEspecial");
        String velocidad = getContenido(estadisticasElement, "Velocidad");

        return "Vida: " + vida + ", Ataque: " + ataque + ", Defensa: " + defensa +
                ", Ataque Especial: " + ataqueEspecial + ", Defensa Especial: " + defensaEspecial +
                ", Velocidad: " + velocidad;
    }
}
