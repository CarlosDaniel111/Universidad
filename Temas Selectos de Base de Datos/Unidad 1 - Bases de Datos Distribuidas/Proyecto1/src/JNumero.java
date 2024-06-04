import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JNumero extends JTextField implements KeyListener {
    protected int tamano;

    public JNumero() {
        this(3);
    }

    public JNumero(int tamano) {
        this.tamano = tamano;
        this.addKeyListener(this);
    }

    public int getCantidad() {
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(getText());
        } catch (Exception e) {
        }
        return cantidad;
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.isControlDown()) {
            evt.consume();
            return;
        }
        if (evt.getKeyCode() == 36 || evt.getKeyCode() == 37 || evt.getKeyCode() == 39) {
            evt.consume();
            return;
        }

    }

    @Override
    public void keyReleased(KeyEvent evt) {
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        if (this.getText().length() == tamano) {
            Toolkit.getDefaultToolkit().beep();
            evt.consume();
            return;
        }
        char car = evt.getKeyChar();
        if ("-0123456789".indexOf(car) == -1) {
            evt.consume();
            return;
        }
        if (!this.getText().isEmpty() && car == '-') {
            evt.consume();
            return;
        }
    }
}
