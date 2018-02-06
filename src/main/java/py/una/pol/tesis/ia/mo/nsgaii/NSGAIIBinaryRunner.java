package py.una.pol.tesis.ia.mo.nsgaii;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SinglePointCrossover;
import org.uma.jmetal.operator.impl.mutation.BitFlipMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.BinaryProblem;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.solution.BinarySolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.una.pol.tesis.ia.mo.problemas.ProblemaSEBinarioMO;
import py.una.pol.tesis.pdi.ElementoEstructurante;
import py.una.pol.tesis.pdi.ElementoEstructuranteBuilder;
import py.una.pol.tesis.pdi.Imagen;

/**
 * Class for configuring and running the NSGA-II algorithm (binary encoding)
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NSGAIIBinaryRunner extends AbstractAlgorithmRunner {
    
    private static final String IMAGE_DIR_IN= "db2/";
    private static final String IMAGE_DIR_OUT= "result/";

    /**
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        final File[] imageFiles = new File(IMAGE_DIR_IN).listFiles();
        List<String> mimetypes = new ArrayList<>();
        mimetypes.add("application/octet-stream");
        for (File imageFile : imageFiles) {
            System.out.println("***RUN image->" + imageFile.getName());
            try {
                run(new Imagen(imageFile, mimetypes));
                break;//momentaneo para ejecutar una sola imagen
            } catch (IOException ex) {
                Logger.getLogger(NSGAIIBinaryRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void run(Imagen imagen) {
        BinaryProblem problem;
        Algorithm<List<BinarySolution>> algorithm;
        CrossoverOperator<BinarySolution> crossover;
        MutationOperator<BinarySolution> mutation;
        SelectionOperator<List<BinarySolution>, BinarySolution> selection;

        ElementoEstructuranteBuilder eeBuilder = new ElementoEstructuranteBuilder()
                .setCantidadFilas(7)
                .setCantidadColumnas(7)
                .setCantidadBits(1);

        problem = new ProblemaSEBinarioMO(imagen, eeBuilder);

        double crossoverProbability = 0.75;
        crossover = new SinglePointCrossover(crossoverProbability);

        double mutationProbability = 0.025;
        mutation = new BitFlipMutation(mutationProbability);

        selection = new BinaryTournamentSelection<>();

        algorithm = new NSGAIIBuilder<>(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxEvaluations(0)
                .setPopulationSize(1)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<BinarySolution> population = algorithm.getResult();
        for (BinarySolution binarySolution : population) {
            ElementoEstructurante ee = eeBuilder.build(binarySolution);
            Imagen imagenMejorada = imagen.mejorarImagen(ee);
            
            //ESCRIBIR LA IMAGEN RESULTANTE EN DISCO COMO RESULTADO
        }
        
        long computingTime = algorithmRunner.getComputingTime();
        JMetalLogger.logger.log(Level.INFO, "Total execution time: {0}ms", computingTime);

        System.out.println("SOLUCION FINAL");
        printFinalSolutionSet(population);
    }
}
