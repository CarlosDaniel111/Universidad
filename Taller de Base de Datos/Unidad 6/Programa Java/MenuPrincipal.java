import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPrincipal extends JFrame implements ActionListener {

    private JTextField txtServidor,txtBD,txtIS;
    private JLabel lblError;
    private JPasswordField txtContra;
    private JButton btnConectar,btnConsultar, btnCaptura;
    private ConexionBD bd;

    public MenuPrincipal(ConexionBD bd){
        super("Menu");
        this.bd = bd;
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        //Etiquetas
        JLabel lblServidor = new JLabel("Servidor",JLabel.RIGHT);
        lblServidor.setBounds(0,10,150,20);
        add(lblServidor);

        JLabel lblBD = new JLabel("Base de Datos",JLabel.RIGHT);
        lblBD.setBounds(0,40,150,20);
        add(lblBD);

        JLabel lblIS = new JLabel("Inicio de Sesión",JLabel.RIGHT);
        lblIS.setBounds(0,70,150,20);
        add(lblIS);

        JLabel lblContra = new JLabel("Contraseña",JLabel.RIGHT);
        lblContra.setBounds(0,100,150,20);
        add(lblContra);

        lblError = new JLabel("Algún parametro es erroneo.");
        lblError.setBounds(10,180,200,20);
        lblError.setForeground(Color.red);
        lblError.setVisible(false);
        add(lblError);

        //TextField
        txtServidor = new JTextField("Laptop");
        txtServidor.setBounds(160,10,200,20);
        add(txtServidor);

        txtBD = new JTextField("Ventas");
        txtBD.setBounds(160,40,200,20);
        add(txtBD);

        txtIS = new JTextField("sa");
        txtIS.setBounds(160,70,200,20);
        add(txtIS);

        txtContra = new JPasswordField();
        txtContra.setBounds(160,100,200,20);
        add(txtContra);

        // Botones
        btnConectar = new JButton("Conectar");
        btnConectar.setBounds(155,140,90,25);
        btnConectar.addActionListener(this);
        add(btnConectar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(105,180,90,25);
        btnConsultar.addActionListener(this);
        btnConsultar.setVisible(false);
        add(btnConsultar);

        btnCaptura = new JButton("Captura");
        btnCaptura.setBounds(205,180,90,25);
        btnCaptura.addActionListener(this);
        btnCaptura.setVisible(false);
        add(btnCaptura);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnConectar){
            Cursor cursorEspera = new Cursor(Cursor.WAIT_CURSOR);
            setCursor(cursorEspera);
            boolean res = bd.conectarBD(txtServidor.getText(),txtBD.getText(),txtIS.getText(), txtContra.getText());
            Cursor cursorNormal = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(cursorNormal);
            lblError.setVisible(!res);
            btnConsultar.setVisible(res);
            btnCaptura.setVisible(res);

            txtServidor.setEnabled(!res);
            txtIS.setEnabled(!res);
            txtBD.setEnabled(!res);
            txtContra.setEnabled(!res);
            btnConectar.setEnabled(!res);
            return;
        }
        if(e.getSource() == btnConsultar){
            new Consulta(bd);
            return;
        }
    }
}
