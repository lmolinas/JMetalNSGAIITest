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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import py.una.pol.tesis.ia.mo.problemas.SEProblemMO;

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

        problem = new SEProblemMO(obtenerMatrizDeLaImagen(""));//agregar la imagen

        double crossoverProbability = 0.75;
        crossover = new SinglePointCrossover(crossoverProbability);

        double mutationProbability = 0.025;//1.0 / problem.getNumberOfBits(0) ;
        mutation = new BitFlipMutation(mutationProbability);

        selection = new BinaryTournamentSelection<>();

        algorithm = new NSGAIIBuilder<>(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxIterations(800*40)
                .setPopulationSize(40)
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

    private static Mat obtenerMatrizDeLaImagen(String fileName) {
        Mat imagen = Imgcodecs.imread(fileName, Imgcodecs.IMREAD_GRAYSCALE);
        imagen.reshape(256, 256);

        Mat rz = new Mat();
        Imgproc.resize(imagen, rz, new Size(256, 256));
        rz.convertTo(rz, CvType.CV_8UC1);
        return rz;
    }
}
