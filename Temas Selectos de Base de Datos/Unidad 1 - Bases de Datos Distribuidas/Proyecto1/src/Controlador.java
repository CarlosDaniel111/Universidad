import javax.swing.*;
import java.awt.event.*;

public class Controlador implements ItemListener, ActionListener {
    private Vista vista;
    private BaseDeDatos bd;

    public Controlador(Vista vista, BaseDeDatos bd) {
        this.vista = vista;
        this.bd = bd;
    }

    @Override
    public void itemStateChanged(ItemEvent evt) {
        if (evt.getStateChange() != ItemEvent.SELECTED)
            return;
        if (evt.getSource() == vista.getCmbCriterio()) {
            if (vista.getCmbCriterio().getSelectedIndex() == 0) {
                vista.eliminarSeleccion();
                return;
            }
            bd.llenarCombo(vista.getCmbOpcion(), "ID" + (String) vista.getCmbCriterio().getSelectedItem());
            vista.mostrarSeleccion();
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == vista.getBtnInsertar()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            String archivo1, archivo2;
            int result = -1;
            while (result != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Seleccione el archivo TicketH");
                result = fileChooser.showOpenDialog(vista);
            }
            archivo1 = fileChooser.getSelectedFile().getAbsolutePath();
            result = -1;
            while (result != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "Seleccione el archivo TicketD");
                result = fileChooser.showOpenDialog(vista);
            }
            archivo2 = fileChooser.getSelectedFile().getAbsolutePath();

            if (bd.insertarRegistros(archivo1, archivo2)) {
                JOptionPane.showMessageDialog(null, "TUPLAS INSERTADAS CORRECTAMENTE", "Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "ERROR AL INSERTAR LAS TUPLAS", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (evt.getSource() == vista.getBtnActualizar()) {

            if (vista.getCmbCriterio().getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un criterio", "Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (vista.getCmbOpcion().getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Debes seleccionar un ID", "Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int cantidad = vista.getTxtAumento().getCantidad();
            if (cantidad == 0) {
                JOptionPane.showMessageDialog(null, "Debes ingresar una cantidad valida", "Mensaje",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String criterio = "ID" + (String) vista.getCmbCriterio().getSelectedItem();
            String id = (String) vista.getCmbOpcion().getSelectedItem();

            if (bd.actualizarPrecio(criterio, id, cantidad)) {
                JOptionPane.showMessageDialog(null, "Aumento realizado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al hacer el aumento", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
