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
public class ElementoEstructuranteBuilder {

    private Integer cantidadFilas;
    private Integer cantidadColumnas;
    private Integer cantidadBits;

    public ElementoEstructuranteBuilder setCantidadFilas(Integer cantidadFilas) {
        this.cantidadFilas = cantidadFilas;
        return this;
    }

    public ElementoEstructuranteBuilder setCantidadColumnas(Integer cantidadColumnas) {
        this.cantidadColumnas = cantidadColumnas;
        return this;
    }

    public ElementoEstructuranteBuilder setCantidadBits(Integer cantidadBits) {
        this.cantidadBits = cantidadBits;
        return this;
    }

    public ElementoEstructurante build() {
        return new ElementoEstructurante(cantidadFilas, cantidadColumnas, cantidadBits);
    }
}
