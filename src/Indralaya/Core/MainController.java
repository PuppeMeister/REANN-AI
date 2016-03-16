/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Indralaya.Core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rin Nadia
 */
public class MainController {
        
        double threshold;
        double[] inputBaru, outputHidden, nilaiAktivasi;
        double[][] weightInput, weightOutput, weightBias, weightOutputBaru, weightBiasBaru,
                   keluaranTrainingPertama;
        List<Integer> patternExclude;
        int index1, index2, index3, index4, index5;
        PengolahanInput objekInput;
        NeuralNetwork objekNN;
        PruningClustering objekPC;
        
        public MainController(){
            objekInput = new PengolahanInput();
            objekNN = new NeuralNetwork();
            objekPC = new PruningClustering();
        }
        /*public static void main(String[] args) {
            PengolahanInput objekInput = new PengolahanInput();
            NeuralNetwork objekNN = new NeuralNetwork();
            PruningClustering objekPC = new PruningClustering();
            
            //objekInput.parsingInput();
            double[][] input = objekInput.dataInput();
            int[][] target = objekInput.target();
            
            double minimumError = 0.1;
            double learningRate = 0.5;
            
            //First Training For Pruning
            objekNN.mainNN(input, target, learningRate, minimumError);
            double[][] weightInput = objekNN.giveWeightInput();
            double[][] weightOutput = objekNN.giveWeightOutput();
            double[][] weightBias = objekNN.giveWeightBias();
            double threshold = objekNN.minWeightDecay;
            
            
            //Pruning
            objekPC.pruning(weightInput, weightOutput, threshold);
            ArrayList<ArrayList<Integer>> lostNeuron = objekPC.lostNeuron();
            ArrayList<ArrayList<Integer>> indexNotLost = objekPC.notLostNeuron();
            ArrayList<Integer> indexExclude = objekPC.lostExclude();
            ArrayList<ArrayList<Integer>> indexNeuron = objekPC.indexNeuron();
            
           //objekInput.inputTargetDiscretize(input, indexNeuron, target);
           objekInput.inputTargetDiscretize2(input, indexNotLost, target);
            ArrayList<ArrayList<Double>> inputBaru = objekInput.inputBaru();
            ArrayList<ArrayList<Integer>> targetBaru = objekInput.targetBaru();
            
            System.out.println("Jumlah pattern exclude" + indexExclude.size());
            System.out.println("Jumlah pattern include" + indexNotLost.size());
            System.out.println("Jumlah pattern Lost" + lostNeuron.size());
            
            for(int index1=0;index1<lostNeuron.size();index1++){
                System.out.println("Pattern ke - " + index1);
                System.out.println("Lost = "+lostNeuron.get(index1)+" Not Lost = "
                                    + indexNeuron.get(index1));
            }
            for(int index1=0;index1<targetBaru.size();index1++){
                System.out.println("Pattern ke - " + index1);
                System.out.println("Input = "+inputBaru.get(index1)+" Target = "
                                    + targetBaru.get(index1));
            }
            //Training Input Baru
            objekNN.NNDiscretizing(inputBaru, targetBaru, learningRate, minimumError);
            double[] nInputLayer = objekNN.giveOutputHidden2();
            double[][] bobotOutput = objekNN.giveBobotOutput();
            double[][] bobotBias = objekNN.giveBobotBias();
            //Discretizing
            System.out.println("Hidden Value");
            for(int i = 0; i<nInputLayer.length; i++){
                System.out.println(nInputLayer[i]);
            }
            System.out.println("Hasil Discretizing");
            double[] nDiscretize = objekPC.doDiscretize(0.1, nInputLayer);
            List<Double> nilaiPerCluster = objekPC.returnNilaiCluster();
            for(int i = 0; i<nDiscretize.length; i++){
                System.out.println(nDiscretize[i]);
            }
            
            double nErrorDiscre = objekNN.mainNN(nDiscretize, bobotOutput, bobotBias, targetBaru);
            double[][] outputDiscre = objekNN.giveOutput2();
            
            
            System.out.println("Hasil Clustering");
            System.out.println("Error = " + nErrorDiscre);
            for(int i=0; i<nDiscretize.length; i++){
                System.out.println("Node ke - " + i
                                    +"Nilai Discretizing = "+nDiscretize[i]
                                    +" Output = " + outputDiscre[i][0]
                                    +" Target  = " + targetBaru.get(i).get(0)
                                    +" Output  = " + outputDiscre[i][1]
                                    +" Target  = " + targetBaru.get(i).get(1));
            }
           
            List<Double> nPerCluster = objekPC.nilaiPerCluster(nDiscretize);
            RuleExtraction objekRE = new RuleExtraction();
            objekRE.extraction(indexNotLost, outputDiscre, nDiscretize, targetBaru, inputBaru, indexNeuron, input,
                              nPerCluster);
           
        }*/
         public void trainingPertama(String alamatFile, double minimumError, double learningRate, int banyakData){
             objekInput.parsingInput(alamatFile, banyakData);
             
             double[][] input = objekInput.dataInput();
             int[][] target = objekInput.target();
             objekNN.mainNN(input, target, learningRate, minimumError);
             keluaranTrainingPertama = objekNN.giveOutput();
         }
        
         public double[][] getKeluaranTrainingPertama(){
            return keluaranTrainingPertama;
         }
}
