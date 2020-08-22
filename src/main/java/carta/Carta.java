package carta;

public interface Carta {

  public static Impedancia[] sobreUnidad(Impedancia imp) {
    Double[] sobreUnidad = Circunferencia.interseccion(0.0,0.0,imp.coefRefleccion(),0.5,0.0,0.5);
    return new Impedancia[] {Impedancia.hacerDeReflexion(sobreUnidad[0], sobreUnidad[1]), Impedancia.hacerDeReflexion(sobreUnidad[2], sobreUnidad[3])};
  }

  public static Impedancia[] sobreUnidad90(Impedancia imp) {
    Double[] sobreUnidad = Circunferencia.interseccion(imp.resistencia()/(imp.resistencia()+1.0),0.0,1.0/(imp.resistencia()+1.0),0.0,0.5,0.5);
    return new Impedancia[] {Impedancia.hacerDeReflexion(sobreUnidad[0], sobreUnidad[1]), Impedancia.hacerDeReflexion(sobreUnidad[2], sobreUnidad[3])};
  }

  public static Impedancia rotarImpedancia(Impedancia imp, Double angulo) {
    angulo = -angulo;
    Double coa = imp.coefRefleccionComplejo()[0];
    Double cob = imp.coefRefleccionComplejo()[1];
    return Impedancia.hacerDeReflexion(coa*Math.cos(angulo) - cob*Math.sin(angulo), coa*Math.sin(angulo) + cob*Math.cos(angulo));
  }
}
