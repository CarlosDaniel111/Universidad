import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener{

    private JButton btnImportaciones,btnVentas;
    private BaseDeDatos baseDeDatos;
    public Main(){
        super("Proyecto ETL");
        baseDeDatos = new BaseDeDatos("localhost","EmpresaOLTP","sa","sa");
        HazInterfaz();
        HazEscuchas();
    }

    private void HazEscuchas() {
        btnImportaciones.addActionListener(this);
        btnVentas.addActionListener(this);
    }

    private void HazInterfaz() {
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JLabel lblTitulo = new JLabel("Proyecto ETL",JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial",Font.BOLD,20));
        add(lblTitulo,BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2,20,20));
        btnImportaciones = new JButton("ETL Importaciones");
        btnVentas = new JButton("ETL Ventas");
        panel.add(btnImportaciones);
        panel.add(btnVentas);
        add(panel);

        setVisible(true);

    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnImportaciones){
            System.out.println("Importaciones");
            if(baseDeDatos.insertarImportaciones("resources/synergy_logistics_database111.csv","resources/ImportacionAutos111.CVS")){
                JOptionPane.showMessageDialog(null,"ETL Importaciones realizado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"Ocurrio un error");
            }
            return;
        }

        if(e.getSource() == btnVentas){
            System.out.println("Ventas");
            if(baseDeDatos.insertarVentas("resources/TicketH.CVS","resources/TicketD.CVS")){
                JOptionPane.showMessageDialog(null,"ETL Ventas realizado exitosamente");
            }else{
                JOptionPane.showMessageDialog(null,"Ocurrio un error");
            }
            return;
        }
    }
}