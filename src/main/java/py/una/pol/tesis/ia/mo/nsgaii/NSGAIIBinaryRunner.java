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
import org.uma.jmetal.problem.multiobjective.zdt.ZDT5;

public class NSGAIIBinaryRunner extends AbstractAlgorithmRunner {

    public static void main(String[] args) throws
            Exception {

        BinaryProblem problem;
        Algorithm<List<BinarySolution>> algorithm;
        CrossoverOperator<BinarySolution> crossover;
        MutationOperator<BinarySolution> mutation;
        SelectionOperator<List<BinarySolution>, BinarySolution> selection;

        String referenceParetoFront = "";
        if (args.length == 2) {
            referenceParetoFront = args[1];
        } else {
            referenceParetoFront = "";
        }

        problem = new ZDT5();

        double crossoverProbability = 0.9;
        crossover = new SinglePointCrossover(crossoverProbability);

        double mutationProbability = 0.025;//1.0 / problem.getNumberOfBits(0) ;
        mutation = new BitFlipMutation(mutationProbability);

        selection = new BinaryTournamentSelection<BinarySolution>();

        algorithm = new NSGAIIBuilder<BinarySolution>(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxIterations(25000)
                .setPopulationSize(100)
                .build();

        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        List<BinarySolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        printFinalSolutionSet(population);

        if (!referenceParetoFront.equals("")) {
            printQualityIndicators(population, referenceParetoFront);
        }
    }
}
