package carta;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Graficos extends JPanel implements ActionListener, ChangeListener {

  private static final int ANCHO = 600;
  private static final int ALTO = 800;

  private static int radioUno = ANCHO*4/10;
  private Font fuente = new Font("MV Boli", Font.PLAIN, 20);
  private JLabel r;
  private JLabel x;
  private JTextField rTexto;
  private JTextField xTexto;
  private JButton dibujar;
  private JButton interseccionConUnidad;
  private JButton interseccionConUnidad90;

  private Impedancia i1 = Impedancia.hacer(1.0,0.0);
  private Impedancia i2 = Impedancia.hacer(1.0,0.0);
  private Impedancia i3 = Impedancia.hacer(1.0,0.0);
  private boolean dibujar90 = false;
  private JLabel rot;
  private JSlider rotacion;
  private Double anguloRotado = 0.0;
  private JLabel cero;
  private JLabel cero25;

  Graficos() {
    setPreferredSize(new Dimension(ANCHO, ALTO));
    setLayout(null);
    setBackground(Color.WHITE);

    r = new JLabel("R");
    r.setFont(fuente);
    r.setSize(25,25);
    r.setLocation(ANCHO*2/10, ALTO*7/10+20);
    add(r);
    x = new JLabel("X");
    x.setFont(fuente);
    x.setSize(25,25);
    x.setLocation(ANCHO*2/10, ALTO*7/10 + 50);
    add(x);
    rTexto = new JTextField();
    rTexto.setFont(fuente);
    rTexto.setSize(50,25);
    rTexto.setLocation(ANCHO*2/10 + 30, ALTO*7/10 + 20);
    add(rTexto);
    xTexto = new JTextField();
    xTexto.setFont(fuente);
    xTexto.setSize(50,25);
    xTexto.setLocation(ANCHO*2/10 + 30, ALTO*7/10 + 50);
    add(xTexto);
    dibujar = new JButton("Dibujar");
    dibujar.setFont(fuente);
    dibujar.setSize(150,25);
    dibujar.setLocation(ANCHO*2/10, ALTO*7/10 + 80);
    dibujar.addActionListener(this);
    add(dibujar);
    interseccionConUnidad = new JButton("Sobre Unidad");
    interseccionConUnidad.setFont(fuente);
    interseccionConUnidad.setSize(200, 25);
    interseccionConUnidad.setLocation(ANCHO*4/10, ALTO*7/10+20);
    interseccionConUnidad.addActionListener(this);
    add(interseccionConUnidad);
    interseccionConUnidad90 = new JButton("Sobre Unidad 90");
    interseccionConUnidad90.setFont(fuente);
    interseccionConUnidad90.setSize(200, 25);
    interseccionConUnidad90.setLocation(ANCHO*4/10, ALTO*7/10+50);
    interseccionConUnidad90.addActionListener(this);
    add(interseccionConUnidad90);
    rotacion = new JSlider(0,360,0);
    rotacion.setSize(360,50);
    rotacion.setFont(new Font("MV Boli", Font.BOLD, 10));
    rotacion.setPaintTicks(true);
    rotacion.setMinorTickSpacing(15);
    rotacion.setPaintTrack(true);
    rotacion.setMajorTickSpacing(45);
    rotacion.setPaintLabels(true);
    rotacion.setLocation(ANCHO*2/10, ALTO*9/10-15);
    rotacion.addChangeListener(this);
    add(rotacion);
    rot = new JLabel("Rotacion ");
    rot.setFont(fuente);
    rot.setSize(125,25);
    rot.setLocation(ANCHO*4/10, ALTO*9/10 - 35);
    add(rot);
    cero = new JLabel("0λ");
    cero.setFont(fuente);
    cero.setSize(25,25);
    cero.setLocation(ANCHO*1/10 - 25, ANCHO*5/10 - 20);
    add(cero);
    cero25 = new JLabel("0.25λ");
    cero25.setFont(fuente);
    cero25.setSize(50,25);
    cero25.setLocation(ANCHO*9/10 + 5, ANCHO*5/10 - 20);
    add(cero25);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D) g;
    g2D.setFont(fuente);
    g2D.setStroke(new BasicStroke(2));
    g2D.setColor(Color.BLUE);
    dibujarCircunferencia(g2D,0.0,0.0,1.0);
    dibujarCircunferencia(g2D,0.5,0.0,0.5);
    g2D.drawLine(escalarVector(-1.0,0.0)[0].intValue(), escalarVector(-1.0,0.0)[1].intValue(), escalarVector(1.0,0.0)[0].intValue(), escalarVector(1.0,0.0)[1].intValue());
    g2D.setColor(Color.GREEN);
    dibujarImpedancia(g2D, i1);
    dibujarImpedancia(g2D, i2);
    dibujarImpedancia(g2D, i3);
    if (dibujar90) {
      g2D.setColor(Color.BLUE);
      dibujarCircunferencia(g2D, 0.0, 0.5, 0.5);
    }
    rot.setText("Rotacion " + rotacion.getValue());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == dibujar) {
      i1 = Impedancia.hacer(Double.valueOf(rTexto.getText()), Double.valueOf(xTexto.getText()));
      dibujar90 = false;
      i2 = Impedancia.hacer(1.0, 0.0);
      i3 = Impedancia.hacer(1.0, 0.0);
      anguloRotado = 0.0;
      rotacion.setValue(0);
    }
    if (e.getSource() == interseccionConUnidad) {
      Impedancia[] sobreUnidad = Carta.sobreUnidad(i1);
      i2 = sobreUnidad[0];
      i3 = sobreUnidad[1];
      dibujar90 = false;
    }
    if (e.getSource() == interseccionConUnidad90) {
      Impedancia[] sobreUnidad90 = Carta.sobreUnidad90(i1);
      i2 = sobreUnidad90[0];
      i3 = sobreUnidad90[1];
      dibujar90 = true;
    }
    repaint();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    Double rotar = Math.toRadians(rotacion.getValue()) - anguloRotado;
    anguloRotado = Math.toRadians(rotacion.getValue());
    i1 = Carta.rotarImpedancia(i1, rotar);
    repaint();
  }

  private static void dibujarCircunferencia(Graphics2D g2D, Double x, Double y, Double r) {
    Double radio = r * radioUno;
    Double esquinaX = ANCHO/2 + x*radioUno - radio;
    Double esquinaY = ANCHO/2 - y*radioUno - radio;
    g2D.drawOval(esquinaX.intValue(), esquinaY.intValue(), 2*radio.intValue(), 2*radio.intValue());
  }

  private static Double[] escalarVector(Double x, Double y) {
    Double x1 = ANCHO/2 + x*radioUno;
    Double y1 = ANCHO/2 - y*radioUno;
    return new Double[] {x1, y1};
  }

  private static void dibujarImpedancia(Graphics2D g2D, Impedancia imp) {
    Double[] rr = imp.coefRefleccionComplejo();
    Double[] xy = escalarVector(rr[0], rr[1]);
    g2D.drawLine(ANCHO/2,ANCHO/2,xy[0].intValue(), xy[1].intValue());
    g2D.drawString(imp.toString(), xy[0].intValue() - 25, xy[1].intValue() - 25);
  }
}
