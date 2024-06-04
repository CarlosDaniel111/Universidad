import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class App extends JFrame implements ActionListener {

    private JTextArea txtXML, txtSalida;
    private JButton btnAbrir, btnBienFormado, btnValidar, btnResultados;
    private String rutaXML;
    private Document doc;

    public App() {
        super("Visor Pokedex XML");
        HazInterfaz();
        HazEscuchas();
    }

    private void HazInterfaz() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblXML = new JLabel("Visor Pokedex XML", JLabel.CENTER);
        lblXML.setFont(new Font("Arial", Font.BOLD, 24));

        add(lblXML, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());

        btnAbrir = new JButton("Abrir archivo XML");
        btnBienFormado = new JButton("Validar bien formado");
        btnValidar = new JButton("Validar XML");
        btnResultados = new JButton("Mostrar resultados");
        btnBienFormado.setEnabled(false);
        btnValidar.setEnabled(false);
        btnResultados.setEnabled(false);

        JPanel pnlBotones = new JPanel();
        pnlBotones.setLayout(new FlowLayout());
        pnlBotones.add(btnAbrir);
        pnlBotones.add(btnBienFormado);
        pnlBotones.add(btnValidar);
        pnlBotones.add(btnResultados);

        JPanel panelTxt = new JPanel();
        panelTxt.setLayout(new GridLayout(1, 1));

        txtXML = new JTextArea();
        txtXML.setEditable(false);
        txtXML.setLineWrap(true);
        txtXML.setBorder(BorderFactory.createTitledBorder("Archivo XML"));
        panelTxt.add(new JScrollPane(txtXML));

        txtSalida = new JTextArea();
        txtSalida.setEditable(false);
        txtSalida.setLineWrap(true);
        txtSalida.setBorder(BorderFactory.createTitledBorder("Salida"));
        panelTxt.add(new JScrollPane(txtSalida));

        panelCentral.add(pnlBotones, BorderLayout.NORTH);
        panelCentral.add(panelTxt, BorderLayout.CENTER);
        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    private void HazEscuchas() {
        btnAbrir.addActionListener(this);
        btnBienFormado.addActionListener(this);
        btnValidar.addActionListener(this);
        btnResultados.addActionListener(this);
    }

    private String leerArchivo(String ruta) {
        String contenido = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(ruta));
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.replace("\t", "  ");
                contenido += linea + "\n";
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnAbrir) {
            JFileChooser fc = new JFileChooser();
            int seleccion = fc.showOpenDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                txtXML.setText("");
                rutaXML = fc.getSelectedFile().getAbsolutePath();
                txtXML.setText(leerArchivo(rutaXML));
                btnBienFormado.setEnabled(true);
                btnValidar.setEnabled(false);
                btnResultados.setEnabled(false);
            }
            return;
        }
        if (evt.getSource() == btnBienFormado) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                dBuilder.parse(rutaXML);
                txtSalida.setText("El archivo esta bien formado");
                btnValidar.setEnabled(true);

            } catch (Exception e) {
                txtSalida.setText(e.getMessage());
            }
            return;
        }

        if (evt.getSource() == btnValidar) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                dbFactory.setValidating(true);
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                ErroresXML erroresXML = new ErroresXML();
                dBuilder.setErrorHandler(erroresXML);
                doc = dBuilder.parse(rutaXML);
                String errores = erroresXML.getErrores();

                if (errores.isEmpty()) {
                    txtSalida.setText("El archivo es valido");
                    btnResultados.setEnabled(true);
                } else {
                    txtSalida.setText(errores);
                }

            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
            return;
        }

        if (evt.getSource() == btnResultados) {
            new ResultadosDialog(this, doc);
            return;
        }

    }

    public static void main(String[] args) throws Exception {
        new App();
    }

}
