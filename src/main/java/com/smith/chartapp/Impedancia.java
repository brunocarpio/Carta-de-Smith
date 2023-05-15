package com.smith.chartapp;

public interface Impedancia {

  public static Impedancia hacer(Double res, Double reac) {
    return new Ohm(res, reac);
  }

  public static Impedancia hacerDeReflexion(Double coa, Double cob) {
    Double res = (1.0 - Math.pow(coa, 2) - Math.pow(cob, 2)) / (Math.pow(coa, 2) - 2 * coa + Math.pow(cob, 2) + 1.0);
    Double reac = (2 * cob) / (Math.pow(coa, 2) - 2 * coa + Math.pow(cob, 2) + 1.0);
    return new Ohm(res, reac);
  }

  public static Impedancia inversa(Double res, Double reac) {
    return Impedancia.hacer(res / (Math.pow(res, 2) + Math.pow(reac, 2)),
        -reac / (Math.pow(res, 2) + Math.pow(reac, 2)));
  }

  public Double coefRefleccion();

  public Double[] coefRefleccionComplejo();

  public Double roe();

  // angulo en radianes medido desde exe +x
  public Double longElectrica();

  // distancia en lambdas medido desde -x hacia el generador
  public Double distanciaElectrica();

  public Double resistencia();

  public Double reactancia();
}
