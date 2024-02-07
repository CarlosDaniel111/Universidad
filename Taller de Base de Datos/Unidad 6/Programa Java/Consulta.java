import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class Consulta extends JFrame implements ActionListener {
    private ConexionBD bd;
    private JLabel lblMensaje;
    private JTextField txtClave,txtNombre,txtDescripcion,txtPrecio;
    private JButton btnBuscar;
    private JComboBox cbFamilia;
    private JTable tabla;
    private DefaultTableModel modelo;
    private Vector<String> familias;
    public Consulta(ConexionBD bd){
        super("Consulta");
        this.bd = bd;
        setSize(600 , 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        //Etiquetas
        JLabel lblClave = new JLabel("Clave");
        lblClave.setBounds(30,30,70,20);
        add(lblClave);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(30,65,70,20);
        add(lblNombre);

        JLabel lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setBounds(30,100,70,20);
        add(lblDescripcion);

        JLabel lblPrecio = new JLabel("Precio");
        lblPrecio.setBounds(30,135,70,20);
        add(lblPrecio);

        JLabel lblFamilia = new JLabel("Familia");
        lblFamilia.setBounds(30,170,70,20);
        add(lblFamilia);

        lblMensaje = new JLabel("Mensaje");
        lblMensaje.setBounds(30,205,70,20);
        lblMensaje.setForeground(Color.red);
        add(lblMensaje);

        // TextField
        txtClave = new JTextField();
        txtClave.setBounds(110,30,70,25);
        add(txtClave);

        txtNombre = new JTextField();
        txtNombre.setBounds(110,65,300,25);
        add(txtNombre);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(110,100,400,25);
        add(txtDescripcion);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(110,135,200,25);
        add(txtPrecio);

        familias = bd.getFamilias();
        cbFamilia = new JComboBox();
        cbFamilia.addItem("Todas");
        for(String fam:familias)
            cbFamilia.addItem(fam);
        cbFamilia.setBounds(110,170,200,25);
        add(cbFamilia);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(450,205,100,25);
        btnBuscar.addActionListener(this);
        add(btnBuscar);

        //Tabla
        String [] cols = {"Articulo ID","Nombre","Descripcion","Precio","Familia ID","Nombre Familia"};
        tabla = new JTable();
        modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(cols);
        tabla.setModel(modelo);
        actualizarTabla();
        JScrollPane sp =new JScrollPane(tabla);
        sp.setBounds(30,240,520,400);
        add(sp);


        setVisible(true);
    }

    private void actualizarTabla() {
        modelo.setRowCount(0);

        int famid = cbFamilia.getSelectedIndex();
        Articulo articulo = new Articulo(txtClave.getText(),txtNombre.getText(),txtDescripcion.getText(),txtPrecio.getText(),
                (famid == 0 ? "" : ""+famid));
        Vector<Articulo> articulos = bd.getArticulos(articulo);
        Vector<String> fila;
        for(Articulo art : articulos){
            fila = new Vector<>();
            fila.add(art.getId());
            fila.add(art.getNombre());
            fila.add(art.getDescripcion());
            fila.add(art.getPrecio());
            fila.add(art.getFamid());
            fila.add(art.getFamnombre());
            modelo.addRow(fila);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnBuscar){
            actualizarTabla();
            return;
        }
    }
}
