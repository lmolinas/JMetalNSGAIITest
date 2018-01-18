package py.una.pol.tesis.ia.mo.nsgaii;

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
import py.una.pol.tesis.ia.mo.problemas.ProblemaSEBinarioMO;
import py.una.pol.tesis.pdi.ElementoEstructuranteBuilder;

/**
 * Class for configuring and running the NSGA-II algorithm (binary encoding)
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class NSGAIIBinaryRunner extends AbstractAlgorithmRunner {

    /**
     * @param args Command line arguments.
     * @throws org.uma.jmetal.util.JMetalException
     * @throws java.io.IOException
     * @throws SecurityException
     * @throws ClassNotFoundException Invoking command: java
     * org.uma.jmetal.runner.multiobjective.NSGAIIBinaryRunner problemName
     * [referenceFront]
     */
    public static void main(String[] args) throws
            Exception {

        BinaryProblem problem;
        Algorithm<List<BinarySolution>> algorithm;
        CrossoverOperator<BinarySolution> crossover;
        MutationOperator<BinarySolution> mutation;
        SelectionOperator<List<BinarySolution>, BinarySolution> selection;

        problem = new ProblemaSEBinarioMO(new ElementoEstructuranteBuilder()
                .setCantidadFilas(7)
                .setCantidadColumnas(7)
                .setCantidadBits(1));

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
        long computingTime = algorithmRunner.getComputingTime();

        JMetalLogger.logger.log(Level.INFO, "Total execution time: {0}ms", computingTime);

        System.out.println("SOLUCION FINAL");
        printFinalSolutionSet(population);
    }
}
