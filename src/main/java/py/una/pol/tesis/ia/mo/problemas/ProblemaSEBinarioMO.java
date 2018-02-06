package py.una.pol.tesis.ia.mo.problemas;

import org.uma.jmetal.problem.impl.AbstractBinaryProblem;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.binarySet.BinarySet;
import py.una.pol.tesis.pdi.ElementoEstructurante;
import py.una.pol.tesis.pdi.ElementoEstructuranteBuilder;
import py.una.pol.tesis.pdi.Imagen;

/**
 * Class representing problem ProblemaSEBinarioMO
 */
@SuppressWarnings("serial")
public class ProblemaSEBinarioMO extends AbstractBinaryProblem {

    private static final int NRO_DE_OBJETIVOS = 2;

    private final Imagen imagen;
    private final ElementoEstructuranteBuilder eeBuilder;
    private final int[] bitsPerVariable;
    private int cont = 1;

    /**
     * Creates a instance of problem ZDT5
     *
     * @param imagen imagen Original del problema
     * @param eeBuilder Constructor de Elementos Estructurantes
     */
    public ProblemaSEBinarioMO(Imagen imagen, ElementoEstructuranteBuilder eeBuilder) {
        this.imagen = imagen;
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
        ElementoEstructurante ee = eeBuilder.build(solution);
        Imagen imagenMejorada = this.imagen.mejorarImagen(ee);
        solution.setObjective(0, imagenMejorada.getContraste());
        solution.setObjective(1, this.imagen.calcularSSIM(imagenMejorada));
        
        ee.imprimir();
        System.out.println("\n");
        ee.imprimirQ1();
        System.out.println("\nFIN\n");
    }
}