import javax.swing.*;

public class Vista extends JFrame {

    private JButton btnActualizar, btnInsertar;
    private JComboBox<String> cmbCriterio, cmbOpcion;
    private JLabel lblOpcion;
    private JNumero txtAumento;

    public Vista() {
        super("Proyecto 1");
        HazInterfaz();
    }

    private void HazInterfaz() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        JLabel lblCriterio = new JLabel("Seleccione el criterio", SwingConstants.CENTER);
        lblCriterio.setBounds(getWidth() / 8, getHeight() / 7, 5 * getWidth() / 16, getHeight() / 16);
        add(lblCriterio);
        cmbCriterio = new JComboBox<String>(new String[] { "Seleccione", "Tienda", "Empleado", "Estado" });
        cmbCriterio.setBounds(getWidth() / 8, getHeight() / 5, 5 * getWidth() / 16, getHeight() / 16);
        add(cmbCriterio);

        lblOpcion = new JLabel("Seleccione ID", SwingConstants.CENTER);
        lblOpcion.setBounds(2 * getWidth() / 8 + 5 * getWidth() / 16, getHeight() / 7, 5 * getWidth() / 16,
                getHeight() / 16);
        lblOpcion.setVisible(true);
        cmbOpcion = new JComboBox<String>();
        cmbOpcion.setBounds(2 * getWidth() / 8 + 5 * getWidth() / 16, getHeight() / 5, 5 * getWidth() / 16,
                getHeight() / 16);
        add(lblOpcion);
        add(cmbOpcion);

        eliminarSeleccion();

        JLabel tAumento = new JLabel("Aumento", SwingConstants.CENTER);
        tAumento.setBounds(getWidth() / 2 - 5 * getWidth() / 32, 5 * getHeight() / 12 - getHeight() / 32,
                5 * getWidth() / 16, getHeight() / 16);
        add(tAumento);
        txtAumento = new JNumero(10);
        txtAumento.setBounds(getWidth() / 2 - 5 * getWidth() / 32, 5 * getHeight() / 12 + getHeight() / 32,
                5 * getWidth() / 16, getHeight() / 16);
        add(txtAumento);

        btnActualizar = new JButton("Aumentar Precio");
        btnActualizar.setBounds(2 * getWidth() / 8, getHeight() - getHeight() / 4 - getHeight() / 16,
                3 * getWidth() / 16, getHeight() / 16);
        add(btnActualizar);

        btnInsertar = new JButton("Insertar Tuplas");
        btnInsertar.setBounds(2 * getWidth() / 8 + 3 * getWidth() / 16 + 1 * getWidth() / 8,
                getHeight() - getHeight() / 4 - getHeight() / 16, 3 * getWidth() / 16, getHeight() / 16);
        add(btnInsertar);

        setVisible(true);
    }

    public void eliminarSeleccion() {
        // lblOpcion.setVisible(false);
        cmbOpcion.setEnabled(false);
        cmbOpcion.removeAllItems();
    }

    public void mostrarSeleccion() {
        cmbOpcion.setEnabled(true);
    }

    public void setEscuchador(Controlador c) {
        cmbCriterio.addItemListener(c);
        btnInsertar.addActionListener(c);
        btnActualizar.addActionListener(c);
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public JComboBox<String> getCmbCriterio() {
        return cmbCriterio;
    }

    public JNumero getTxtAumento() {
        return txtAumento;
    }

    public JComboBox<String> getCmbOpcion() {
        return cmbOpcion;
    }
}
