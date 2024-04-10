import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class Captura extends JFrame implements ActionListener, ItemListener, MouseListener {
    private ConexionBD bd;
    private JRadioButton radNuevo,radModificar;
    private JButton btnLimpiar,btnGrabar, btnBorrar;
    private JTextField txtClave,txtNombre,txtDescripcion,txtPrecio;
    private JComboBox cbFamilia;
    private Vector<String> familias;
    private JTable tabla;
    private DefaultTableModel modelo;

    public Captura(ConexionBD bd){
        super("Captura");
        this.bd = bd;
        setSize(600 , 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        //Radios
        radNuevo = new JRadioButton("Nuevo");
        radNuevo.setBounds(100,20,90,20);
        radNuevo.setSelected(true);
        radNuevo.addItemListener(this);
        radModificar = new JRadioButton("Modificar");
        radModificar.setBounds(200,20,90,20);
        radModificar.addItemListener(this);
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(radNuevo);
        grupo.add(radModificar);
        add(radNuevo);
        add(radModificar);

        //Etiquetas
        JLabel lblClave = new JLabel("Clave");
        lblClave.setBounds(30,50,70,20);
        add(lblClave);

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(30,85,70,20);
        add(lblNombre);

        JLabel lblDescripcion = new JLabel("Descripción");
        lblDescripcion.setBounds(30,120,70,20);
        add(lblDescripcion);

        JLabel lblPrecio = new JLabel("Precio");
        lblPrecio.setBounds(30,155,70,20);
        add(lblPrecio);

        JLabel lblFamilia = new JLabel("Familia");
        lblFamilia.setBounds(30,190,70,20);
        add(lblFamilia);

        //TextField
        txtClave = new JTextField();
        txtClave.setBounds(110,50,70,25);
        txtClave.setEnabled(false);
        add(txtClave);

        txtNombre = new JTextField();
        txtNombre.setBounds(110,85,300,25);
        add(txtNombre);

        txtDescripcion = new JTextField();
        txtDescripcion.setBounds(110,120,400,25);
        add(txtDescripcion);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(110,155,200,25);
        add(txtPrecio);

        //ComboBox
        familias = bd.getFamilias();
        cbFamilia = new JComboBox();
        cbFamilia.addItem("Seleccionar");
        for(String fam:familias)
            cbFamilia.addItem(fam);
        cbFamilia.setBounds(110,190,200,25);
        add(cbFamilia);

        //Botones
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(310,20,80,20);
        btnLimpiar.addActionListener(this);
        add(btnLimpiar);

        btnGrabar = new JButton("Grabar");
        btnGrabar.setBounds(410,20,80,20);
        btnGrabar.addActionListener(this);
        add(btnGrabar);

        btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(450,225,100,25);
        btnBorrar.addActionListener(this);
        add(btnBorrar);

        //Tabla
        String [] cols = {"Articulo ID","Nombre","Descripcion","Precio","Familia ID","Nombre Familia"};
        tabla = new JTable();
        modelo = new DefaultTableModel(){ @Override public boolean isCellEditable(int row, int column) { return false; } };
        modelo.setColumnIdentifiers(cols);
        tabla.setModel(modelo);
        tabla.addMouseListener(this);
        traerDatos();
        JScrollPane sp =new JScrollPane(tabla);
        sp.setBounds(30,260,520,380);
        add(sp);

        setVisible(true);
    }

    private void traerDatos() {
        modelo.setRowCount(0);
        Vector<Articulo> articulos = bd.getAllArticulos();
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

    private boolean validarCampos() {
        if(radModificar.isSelected() && tabla.getSelectedRow() ==-1){
            JOptionPane.showMessageDialog(null, "No haz seleccionado algo a modificar");
            return false;
        }

        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo de Nombre esta vacio");
            return false;
        }
        if (txtNombre.getText().length() > 50) {
            JOptionPane.showMessageDialog(null, "El campo de Nombre esta demasiado largo");
            return false;
        }

        if (txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
            return false;
        }
        if (txtDescripcion.getText().length() > 500) {
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta demasiado largo");
            return false;
        }

        if (txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo de Precio esta vacio");
            return false;
        }

        try {
            Float.parseFloat(txtPrecio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El campo de Precio no es un numero");
            return false;
        }

        if (txtNombre.getText().length() > 15) {
            JOptionPane.showMessageDialog(null, "El campo de Precio esta demasiado largo");
            return false;
        }

        if (cbFamilia.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "No haz seleccionado una familia");
            return false;
        }



        return true;

    }

    private void limpiar(){
        txtClave.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        cbFamilia.setSelectedIndex(0);
        tabla.clearSelection();
        radNuevo.setSelected(true);
    }

    private void traerDatosTabla(){
        if(tabla.getSelectedRow() == -1){
            return;
        }
        txtClave.setText(tabla.getValueAt(tabla.getSelectedRow(),0).toString());
        txtNombre.setText(tabla.getValueAt(tabla.getSelectedRow(),1).toString());
        txtDescripcion.setText(tabla.getValueAt(tabla.getSelectedRow(),2).toString());
        txtPrecio.setText(tabla.getValueAt(tabla.getSelectedRow(),3).toString());
        cbFamilia.setSelectedIndex(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(),4).toString()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLimpiar){
            limpiar();
            return;
        }
        if(e.getSource() == btnGrabar){
            if(validarCampos()){
                int famid = cbFamilia.getSelectedIndex();
                Articulo articulo = new Articulo(txtClave.getText(),txtNombre.getText(),txtDescripcion.getText(),txtPrecio.getText(), ""+famid);
                String artid = bd.insertarDatos(articulo);
                if(artid.charAt(0) == 'N'){
                    JOptionPane.showMessageDialog(null, "No es posible insertar datos");
                    return;
                }
                traerDatos();
                limpiar();
            }
            return;
        }

        if(e.getSource() == btnBorrar){
            if(tabla.getSelectedRow() == -1){
                JOptionPane.showMessageDialog(null, "No haz seleccionado una tupla a borrar");
                radNuevo.setSelected(true);
                return;
            }
            int[] filas = tabla.getSelectedRows();
            int cont=0;
            for(int fila:filas){
                bd.borrarTupla(tabla.getValueAt(fila-cont,0).toString());
                cont++;
            }
            traerDatos();
            radNuevo.setSelected(true);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(radNuevo.isSelected()){
            txtClave.setText("");
            txtNombre.setText("");
            txtDescripcion.setText("");
            txtPrecio.setText("");
            cbFamilia.setSelectedIndex(0);
            tabla.clearSelection();
            return;
        }
        if(radModificar.isSelected()){
            traerDatosTabla();


            return;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == tabla && radModificar.isSelected())
            traerDatosTabla();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
