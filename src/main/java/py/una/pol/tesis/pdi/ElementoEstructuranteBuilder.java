/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.una.pol.tesis.pdi;

import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.binarySet.BinarySet;

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

    public ElementoEstructurante build(BinarySolution solution) {
        ElementoEstructurante ee = this.build();
        for (int i = 0; i < solution.getNumberOfVariables(); i++) {
            BinarySet variableValue = solution.getVariableValue(i);
            for (int j = 0; j < variableValue.getBinarySetLength(); j++) {
                ee.addBitQ1andExpandToQn(i / ee.cantidadColumnasQ1(),//fila
                        i % ee.cantidadColumnasQ1(),//columna
                        j,//profundidad
                        variableValue.get(j) ? "1" : "0"//valor
                );
            }
        }
        return ee;
    }
}
