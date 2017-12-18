package py.una.pol.tesis.ia.mo.problemas;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.binarySet.BinarySet;
import py.una.pol.tesis.pdi.ElementoEstructurante;
import py.una.pol.tesis.pdi.ElementoEstructuranteBuilder;

/**
 * Class representing problem ProblemaSEBinarioMO
 */
@SuppressWarnings("serial")
public class ProblemaSEBinarioMO extends AbstractBinaryProblem {

    private static final int NRO_DE_OBJETIVOS = 2;

    private ElementoEstructuranteBuilder eeBuilder;
    private int[] bitsPerVariable;
    private int cont = 1;

    /**
     * Creates a default instance of problem ZDT5 (11 decision variables)
     */
    public ProblemaSEBinarioMO() {
        this(new ElementoEstructuranteBuilder()
                .setCantidadFilas(3)
                .setCantidadColumnas(3)
                .setCantidadBits(1));
    }

    /**
     * Creates a instance of problem ZDT5
     *
     * @param eeBuilder Constructor de Elementos Estructurantes
     */
    public ProblemaSEBinarioMO(ElementoEstructuranteBuilder eeBuilder) {
        this.eeBuilder = eeBuilder;

        setName(this.getClass().getSimpleName());
        setNumberOfObjectives(NRO_DE_OBJETIVOS);

        ElementoEstructurante ee = this.eeBuilder.build();
        setNumberOfVariables(ee.tamanhoQ1());
        this.bitsPerVariable = new int[ee.tamanhoQ1()];
        for (int var = 0; var < ee.tamanhoQ1(); var++) {
            this.bitsPerVariable[var] = ee.getCantidadBits();
        }
    }

    @Override
    protected int getBitsPerVariable(int index) {
        if ((index < 0) || (index >= this.getNumberOfVariables())) {
            throw new JMetalException("Index value is incorrect: " + index);
        }
        return bitsPerVariable[index];
    }

    /**
     * Evaluate() method
     *
     * @param solution
     */
    @Override
    public void evaluate(BinarySolution solution) {
        System.out.println("SOLUTION: " + cont++);
        ElementoEstructurante ee = eeBuilder.build();
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
        ee.imprimir();
        System.out.println("\n");
        ee.imprimirQ1();
        System.out.println("\nFIN\n");

        double[] f = new double[solution.getNumberOfObjectives()];
        f[0] = calcularSSIM(ee);
        f[1] = calcularContraste(ee);
    }

    private double calcularSSIM(ElementoEstructurante ee) {
        return 1;
    }

    private double calcularContraste(ElementoEstructurante ee) {
        return 1;
    }
}
