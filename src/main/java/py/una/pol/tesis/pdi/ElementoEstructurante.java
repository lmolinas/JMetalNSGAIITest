/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.tesis.pdi;

/**
 *
 * @author Arturo
 */
public class ElementoEstructurante {

    private Integer cantidadFilas;
    private Integer cantidadColumnas;
    private Integer cantidadBits;
    private String[][][] ee;

    /**
     *
     * @param cantidadFilas
     * @param cantidadColumnas
     * @param cantidadBits cantidad de bits de profundidad del elemento
     * estructurante (SE)
     */
    public ElementoEstructurante(Integer cantidadFilas, Integer cantidadColumnas, Integer cantidadBits) {
        this.cantidadFilas = cantidadFilas;
        this.cantidadColumnas = cantidadColumnas;
        this.cantidadBits = cantidadBits;
        ee = new String[cantidadFilas][cantidadColumnas][cantidadBits];
    }

    /**
     *
     * @param cantidadFilas
     * @param cantidadColumnas Parametro cantidadBits=1; cantidad de bits de
     * profundidad del elemento estructurante (SE)
     */
    public ElementoEstructurante(Integer cantidadFilas, Integer cantidadColumnas) {
        this(cantidadFilas, cantidadColumnas, 1);
    }

    public Integer getCantidadFilas() {
        return cantidadFilas;
    }

    public void setCantidadFilas(Integer cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
    }

    public Integer getCantidadColumnas() {
        return cantidadColumnas;
    }

    public void setCantidadColumnas(Integer cantidadColumnas) {
        this.cantidadColumnas = cantidadColumnas;
    }

    public Integer getCantidadBits() {
        return cantidadBits;
    }

    public void setCantidadBits(Integer cantidadBits) {
        this.cantidadBits = cantidadBits;
    }

    /**
     * Tamanho del elemento estructurante
     *
     * @return
     */
    public Integer tamanho() {
        return this.cantidadFilas * this.cantidadColumnas;
    }

    /**
     * Cantidad de filas del primer cuadrante del elemento estructurante
     *
     * @return
     */
    public Integer cantidadFilasQ1() {
        return this.cantidadFilas / 2 + this.cantidadFilas % 2;
    }

    /**
     * Cantidad de columnas del primer cuadrante del elemento estructurante
     *
     * @return
     */
    public Integer cantidadColumnasQ1() {
        return this.cantidadColumnas / 2 + this.cantidadColumnas % 2;
    }

    /**
     * Tamanho del primer cuadrante del elemento estructurante
     *
     * @return
     */
    public Integer tamanhoQ1() {
        return this.cantidadFilasQ1() * this.cantidadColumnasQ1();
    }

    public void addBitQ1andExpandToQn(Integer fila, Integer columna, Integer profundidad, String valor) {
        ee[fila][columna][profundidad] = valor;
        ee[fila][this.cantidadColumnas - 1 - columna][profundidad] = valor;
        ee[this.cantidadFilas - 1 - fila][columna][profundidad] = valor;
        ee[this.cantidadFilas - 1 - fila][this.cantidadColumnas - 1 - columna][profundidad] = valor;
    }

    public void imprimir() {
        System.out.println("ELEMENTO ESTRUCTURANTE(EE)");
        imprimirGenerico(cantidadFilas, cantidadColumnas, cantidadBits);
    }

    public void imprimirQ1() {
        System.out.println("1ER CUADRANTE DE EE");
        imprimirGenerico(this.cantidadFilasQ1(), cantidadColumnasQ1(), cantidadBits);
    }

    private void imprimirGenerico(Integer topeFilas, Integer topeColumnas, Integer topeProfundidad) {
        for (int i = 0; i < topeFilas; i++) {
            for (int j = 0; j < topeColumnas; j++) {
                System.out.print("[");
                for (int k = 0; k < topeProfundidad; k++) {
                    System.out.print(ee[i][j][k]);
                }
                System.out.print("]\t");
            }
            System.out.println();
        }
    }
}
