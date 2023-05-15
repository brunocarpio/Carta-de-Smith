package com.smith.chartapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Ohm implements Impedancia {

  private final Double resistencia;
  private final Double reactancia;
  private final Double[] coef = new Double[2];

  public Ohm(Double res, Double reac) {
    resistencia = res;
    reactancia = reac;
    coef[0] = (Math.pow(resistencia, 2) - 1.0 + Math.pow(reactancia, 2))
        / (Math.pow(resistencia + 1.0, 2) + Math.pow(reactancia, 2));
    coef[1] = (2.0 * reactancia) / (Math.pow(resistencia + 1.0, 2) + Math.pow(reactancia, 2));
  }

  @Override
  public Double coefRefleccion() {
    return Math.sqrt(Math.pow(coef[0], 2) + Math.pow(coef[1], 2));
  }

  @Override
  public Double[] coefRefleccionComplejo() {
    return new Double[] { coef[0], coef[1] };
  }

  @Override
  public Double roe() {
    return (1 + coefRefleccion()) / (1 - coefRefleccion());
  }

  @Override
  public Double longElectrica() {
    // Double angulo = Math.toDegrees(Math.atan(coef[1]/coef[0]));
    // return (angulo<0)? (angulo+360.0) % 360 : angulo;
    return Math.atan2(coef[1], coef[0]);
  }

  @Override
  public Double distanciaElectrica() {
    return (Math.PI - longElectrica()) * (0.25 / Math.PI);
  }

  @Override
  public Double resistencia() {
    return resistencia;
  }

  @Override
  public Double reactancia() {
    return reactancia;
  }

  @Override
  public String toString() {
    return "(" + round(resistencia, 2) + ", " + round(reactancia, 2) + ")" + round(distanciaElectrica(), 2) + "λ";

  }

  public static double round(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException();

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }
}
