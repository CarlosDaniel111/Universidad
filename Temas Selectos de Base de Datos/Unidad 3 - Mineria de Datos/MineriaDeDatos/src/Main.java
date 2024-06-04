import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    private JButton btnInicioArff,btnInicioSql,btnGenerarArff,btnGenerarSql;
    private JTextField txtEjemplos;
    ARFF arff;
    BaseDeDatos baseDeDatos;
    public Main(){
        super("Generador de Ejemplos");
        arff = new ARFF("Archivos/creditos.arff");
        baseDeDatos = new BaseDeDatos("localhost","MineriaDeDatos","sa","sa");
        //baseDeDatos = new BaseDeDatos("localhost","EmpresaOLTP","sa","sa");
        HazInterfaz();
        HazEscuchas();

    }

    private void HazEscuchas() {
        btnInicioArff.addActionListener(this);
        btnInicioSql.addActionListener(this);
        btnGenerarArff.addActionListener(this);
        btnGenerarSql.addActionListener(this);
    }

    private void HazInterfaz() {
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JLabel lblTitulo = new JLabel("Generador de Ejemplos",JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial",Font.BOLD,20));
        add(lblTitulo,BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2,20,20));
        btnInicioArff = new JButton("Crear archivo ARFF");
        btnInicioSql = new JButton("Crear tabla SQL");
        panel.add(btnInicioArff);
        panel.add(btnInicioSql);

        panel.add(new JLabel("Cantidad de ejemplos: ",JLabel.CENTER));
        txtEjemplos = new JTextField();
        panel.add(txtEjemplos);

        btnGenerarArff = new JButton("Insertar ejemplos en ARFF");
        btnGenerarSql = new JButton("Insertar ejemplos en SQL");

        panel.add(btnGenerarArff);
        panel.add(btnGenerarSql);

        add(panel);

        setVisible(true);

    }


    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnInicioArff){
            if(arff.iniciarArchivo()){
                JOptionPane.showMessageDialog(null,"Se ha creado el archivo ARFF");
            }else{
                JOptionPane.showMessageDialog(null,"Ocurrio un error");
            }
            return;
        }

        if(e.getSource() == btnGenerarArff){
            if(arff.insertarEjemplos(Integer.parseInt(txtEjemplos.getText()))){
                JOptionPane.showMessageDialog(null,"Se insertaron los ejemplos al archivo ARFF");
            }else{
                JOptionPane.showMessageDialog(null,"Ocurrio un error");
            }
            return;
        }

        if(e.getSource() == btnInicioSql){
            if(baseDeDatos.crearTabla()){
                JOptionPane.showMessageDialog(null,"Se ha creado la tabla SQL con exito");
            }else{
                JOptionPane.showMessageDialog(null,"Ocurrio un error");
            }
        }

        if(e.getSource() == btnGenerarSql){
            if(baseDeDatos.insertarTuplas(Integer.parseInt(txtEjemplos.getText()))){
                JOptionPane.showMessageDialog(null,"Se insertaron las tuplas a la tabla SQL");
            }else{
                JOptionPane.showMessageDialog(null,"Ocurrio un error");
            }
            return;
        }
    }
}