package com.smith.chartapp;

public interface Circunferencia {

  public static Double[] interseccion(Double x1, Double y1, Double r1, Double x2, Double y2, Double r2) {
    Double d = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    Double a = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);
    Double h = Math.sqrt(Math.pow(r1, 2) - Math.pow(a, 2));

    Double x3 = x1 + (a / d) * (x2 - x1);
    Double y3 = y1 + (a / d) * (y2 - y1);

    Double x4a = x3 + (h / d) * (y2 - y1);
    Double y4a = y3 - (h / d) * (x2 - x1);

    Double x4b = x3 - (h / d) * (y2 - y1);
    Double y4b = y3 + (h / d) * (x2 - x1);
    return new Double[] { x4a, y4a, x4b, y4b };
  }

}
