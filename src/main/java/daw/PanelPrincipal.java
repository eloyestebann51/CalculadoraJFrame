package daw;

/**
 *
 * @author eloy
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelPrincipal extends JPanel implements ActionListener {

    // Atributos de la clase (privados)
    private PanelBotones botonera;
    private JTextArea areaTexto;
    private int tipoOperacion;

    // Constructor
    public PanelPrincipal() {
        initComponents();
        tipoOperacion = -1; // No hay operaciones en la calculadora
    }

    // Se inicializan los componentes gráficos y se colocan en el panel
    private void initComponents() {
        // Creamos el panel de botones
        botonera = new PanelBotones();
        // Creamos el área de texto
        areaTexto = new JTextArea(10, 50);
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.white);

        //Establecemos layout del panel principal
        this.setLayout(new BorderLayout());
        // Colocamos la botonera y el área texto
        this.add(areaTexto, BorderLayout.NORTH);
        this.add(botonera, BorderLayout.SOUTH);
        for (JButton boton : this.botonera.getgrupoBotones()) {
            boton.addActionListener(this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if (o instanceof JButton boton) {
            String textoBoton = boton.getText();

            switch (textoBoton) {
                case "C": // Limpiar
                    areaTexto.setText("");
                    tipoOperacion = -1;
                    break;
                case "+":
                case "-":
                case "*": 
                case "/":
                    // Añadimos al areaTexto que ya tenemos el boton pulsado
                    areaTexto.setText(areaTexto.getText() + " " + textoBoton + " ");
                    break;
                case "=":
                    try {
                        
                        // Lo convertimos en un array para separar cada parte
                        String texto = areaTexto.getText();
                        String[] partes = texto.split(" ");

                        // Si hay tres partes, guardamos los operandos y el operador
                        if (partes.length == 3) {
                            double operando1 = Double.parseDouble(partes[0]);
                            String operador = partes[1];
                            double operando2 = Double.parseDouble(partes[2]);
                            double resultado = 0;

                            // Segun el operador realizamos su operacion
                            switch (operador) {
                                case "+" ->
                                    resultado = operando1 + operando2;
                                case "-" ->
                                    resultado = operando1 - operando2;
                                case "*" ->
                                    resultado = operando1 * operando2;
                                case "/" -> {
                                    if (operando2 != 0) {
                                        resultado = operando1 / operando2;
                                    } else {
                                        areaTexto.setText("Error: Division por 0");
                                        return;
                                    }
                                }
                                default -> {
                                    areaTexto.setText("Operador no valido");
                                    return;
                                }
                            }

                            areaTexto.setText(String.valueOf(resultado));
                        } else {
                            areaTexto.setText("Formato incorrecto");
                        }

                    } catch (NumberFormatException ex) {
                        areaTexto.setText("Error en numero");
                    }
                    break;
                default:
                    // Asumimos que es un numero
                    areaTexto.setText(areaTexto.getText() + textoBoton);
                    break;
            }
        }
    }

}
