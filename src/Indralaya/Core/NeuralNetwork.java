package Indralaya.Core;
// Backpropagation biasa tanpa momentum dan weight decay

import Indralaya.UI.FirstUI;
import java.text.*;
import java.util.*;

public class NeuralNetwork{

    double[][] output, output2, output3, galatInformasi, weightInput, weightOutput, weightBias,
               bobotOutput, bobotBias;
    double[] nilaiHidden, nilaiHidden2, outputHidden, outputHidden2, totalGalat;
    double minWeightDecay, error;
    int index1, index2, index3, index4, index5, index6, epoch;
    boolean berhasil;
    
    public void mainNN(double[][] input, int[][] target, double learningRate, double minimumError) {

        weightInput = new double[input.length][input[0].length];
        for (index1 = 0; index1 < input.length; index1++) {
            for (index2 = 0; index2 < input[0].length; index2++) {
                weightInput[index1][index2] = getRandom();
            }
        }

        weightBias = new double[input.length][3];
        for (index1 = 0; index1 < input.length; index1++) {
            for (index2 = 0; index2 < weightBias[0].length; index2++) {
                weightBias[index1][index2] = getRandom();
            }
        }
        weightOutput = new double[target.length][target[0].length];
        for (index1 = 0; index1 < target.length; index1++) {
            for (index2 = 0; index2 < target[0].length; index2++) {
                weightOutput[index1][index2] = getRandom();
            }
        }
        
       
        outputHidden = new double[input.length];
        nilaiHidden = new double[outputHidden.length];
        output = new double[target.length][target[0].length];
        galatInformasi = new double[output.length][output[0].length];
        totalGalat = new double[outputHidden.length];

        System.out.println("Output Layer NN Pertama = "+output.length+"||"+output[0].length);
        System.out.println("Galat Informasi = "+output.length+"||"+output[0].length);
        System.out.println("Total Galat = "+outputHidden.length);
        System.out.println("Length of Output Hidden = "+outputHidden.length);
        System.out.println("Weight Input Length = "+weightInput.length+"||"+weightInput[0].length);
        System.out.println("Input Length = "+input.length+"||"+input[0].length);
        
        
        berhasil = false;
        epoch = 0;
        while (berhasil != true) {
            for (index1 = 0; index1 < input.length; index1++) {
                double nilaiKalkulasi = 0;
                //Calculate Hidden Layer
                for (index2 = 0; index2 < input[0].length; index2++) {
                    nilaiKalkulasi += input[index1][index2] * weightInput[index1][index2];
                }
                nilaiKalkulasi += weightBias[index1][0];
                nilaiHidden[index1] = nilaiKalkulasi;
                outputHidden[index1] = hyperbolicTangenSigmoid(nilaiHidden[index1]);

                //Calculate Output Layer
                double nilaiKalkulasiOutput = 0;
                for (index2 = 0; index2 < weightOutput[0].length; index2++) {
                    nilaiKalkulasiOutput += outputHidden[index1] * weightOutput[index1][index2];
                    nilaiKalkulasiOutput += weightBias[index1][index2 + 1];
                    output[index1][index2] = logSigmoid(nilaiKalkulasiOutput);
                }

            }
            //Hitung Mean Square Error
            error = meanSquareError(output, target);

            if (error > minimumError) {
                //Memasukan nilai Galat Informasi

                for (index1 = 0; index1 < galatInformasi.length; index1++) {
                    for (index2 = 0; index2 < galatInformasi[0].length; index2++) {
                        galatInformasi[index1][index2] = (target[index1][index2] - output[index1][index2]) * (output[index1][index2]);
                    }
                }

                for (index1 = 0; index1 < totalGalat.length; index1++) {
                    totalGalat[index1] = (galatInformasi[index1][0] * weightOutput[index1][0]
                            + galatInformasi[index1][1] * weightOutput[index1][1]) * outputHidden[index1];
                }
                weightBias = updateBias(learningRate, outputHidden, galatInformasi, weightBias, totalGalat);
                weightOutput = updateWeightOutput(learningRate, outputHidden, galatInformasi, weightOutput);
                weightInput = updateWeightInput(learningRate, weightInput, input, totalGalat);

                System.out.println("Epoch ke - " + epoch + " :" + error);
                berhasil = false;
                epoch++;
            } else {
                berhasil = true;
                //objekInterface.tampilTrainingPertama(input, target, output, epoch, error);
                System.out.println("Epoch ke - " + epoch + " : " + error);
                for (index1 = 0; index1 < output.length; index1++) {
                    System.out.print("Input : ");
                    for (index2 = 0; index2 < input[0].length; index2++) {
                        System.out.print(input[index1][index2] + ",");
                    }
                    System.out.println("Hasil " + output[index1][0] + " Target = " + target[index1][0] + "||||"
                            + output[index1][1] + " Target = " + target[index1][1]);
                }
                minWeightDecay = weightDecay(target, output, weightInput, weightOutput);
                
            }

        }

    }
    
    public void NNDiscretizing(ArrayList<ArrayList<Double>>input, ArrayList<ArrayList<Integer>> target, double learningRate, double minimumError) {
        ArrayList<ArrayList<Double>> bobotInput = new ArrayList<ArrayList<Double>>();
        
        //Bobot Input
        for(index1=0;index1<input.size();index1++){
            ArrayList<Double> biAnak = new ArrayList<Double>();
            for(index2=0;index2<input.get(index1).size();index2++){
                biAnak.add(getRandom());
            }
            bobotInput.add(biAnak);
        }
        
        //Bobot Output
        bobotOutput = new double[target.size()][target.get(0).size()];
        for(index1=0;index1<bobotOutput.length;index1++){
            for(index2=0;index2<bobotOutput[0].length;index2++){
                bobotOutput[index1][index2]=getRandom();
            }
        }
        //Bobot Bias
        bobotBias = new double[target.size()][3];
        for(index1=0;index1<bobotBias.length;index1++){
            for(index2=0;index2<bobotBias[0].length;index2++){
                bobotBias[index1][index2]=getRandom();
            }
        }
        
        //Training
        outputHidden2 = new double[target.size()];
        nilaiHidden2 = new double[outputHidden.length];
        output3 = new double[target.size()][target.get(0).size()];
        galatInformasi = new double[output3.length][output3[0].length];
        totalGalat = new double[outputHidden2.length];

        boolean berhasil = false;
        int epoch = 0;
        while (berhasil != true) {
            for (index1 = 0; index1 < input.size(); index1++) {
                double nilaiKalkulasi = 0;
                //Calculate Hidden Layer
                for (index2 = 0; index2 < input.get(index1).size(); index2++) {
                    nilaiKalkulasi += input.get(index1).get(index2) * bobotInput.get(index1).get(index2);
                }
                nilaiKalkulasi += bobotBias[index1][0];
                nilaiHidden2[index1] = nilaiKalkulasi;
                outputHidden2[index1] = hyperbolicTangenSigmoid(nilaiHidden2[index1]);

                //Calculate Output Layer
                double nilaiKalkulasiOutput = 0;
                for (index2 = 0; index2 < bobotOutput[0].length; index2++) {
                    nilaiKalkulasiOutput += outputHidden2[index1] * bobotOutput[index1][index2];
                    nilaiKalkulasiOutput += bobotBias[index1][index2 + 1];
                    output3[index1][index2] = logSigmoid(nilaiKalkulasiOutput);
                }

            }
            //Hitung Mean Square Error
            double error = meanSquareError2(output3, target);

            if (error > minimumError) {
                //Memasukan nilai Galat Informasi

                for (index1 = 0; index1 < galatInformasi.length; index1++) {
                    for (index2 = 0; index2 < galatInformasi[0].length; index2++) {
                        galatInformasi[index1][index2] = (target.get(index1).get(index2)
                                - output3[index1][index2]) * (output3[index1][index2]);
                    }
                }

                for (index1 = 0; index1 < totalGalat.length; index1++) {
                    totalGalat[index1] = (galatInformasi[index1][0] * bobotOutput[index1][0]
                            + galatInformasi[index1][1] * bobotOutput[index1][1]) * outputHidden2[index1];
                }
                bobotBias = updateBias(learningRate, outputHidden2, galatInformasi, bobotBias, totalGalat);
                bobotOutput = updateWeightOutput(learningRate, outputHidden2, galatInformasi, weightOutput);
                bobotInput = updateBobotInput(learningRate, bobotInput, input, totalGalat);

                System.out.println("Epoch ke - " + epoch + " :" + error);
                berhasil = false;
                epoch++;
            } else {
                berhasil = true;
                System.out.println("Epoch ke - " + epoch + " : " + error);
                for (index1 = 0; index1 < output3.length; index1++) {
                    System.out.print("Input : " + input.get(index1));
                    
                    System.out.println("Hasil " + output3[index1][0] + " Target = " + target.get(index1).get(0) + "||||"
                            + output3[index1][1] + " Target = " + target.get(index1).get(1));
                }
                //minWeightDecay = weightDecay(target, output, weightInput, weightOutput);
            }

        }
    }
    
     public double mainNN(double[] input, double[][] weightOutput, double[][] weightBias,
                        ArrayList<ArrayList<Integer>> target){	
	        output2 = new double[target.size()][target.get(0).size()];
              	
              	for(index1=0;index1<output2.length;index1++){
                double a = 0;
                double b = 0;
                
                a = input[index1]*weightOutput[index1][0]+weightBias[index1][1];
                b = input[index1]*weightOutput[index1][1]+weightBias[index1][2];
                output2[index1][0] = logSigmoid(a);
                output2[index1][1] = logSigmoid(b);
                }
                double error = meanSquareError2(output2,target);
                return error;                
      }
    public double getRandom() {
        //return randomWeightMultiplier * (rand.nextDouble() * 2 - 1); // [-1;1[
        Random rand = new Random();
        return 1 * (rand.nextDouble() * 1 - -1);
    }

    public double hyperbolicTangenSigmoid(double d) {
        return (Math.exp(d * 2.0) - 1.0) / (Math.exp(d * 2.0) + 1.0);
    }

    public double logSigmoid(double u) {

        return (1 / (1 + Math.pow(Math.E, (-1 * u))));

    }

    public double meanSquareError(double[][] keluaran, int[][] target) {

        double a = 0;
        for (index1 = 0; index1 < keluaran.length; index1++) {
            for (index2 = 0; index2 < keluaran[0].length; index2++) {
                a += Math.pow(keluaran[index1][index2] - target[index1][index2], 2);
            }
        }
        a = a * 0.5;
        return a;
    }
    public double meanSquareError2(double[][] keluaran, ArrayList<ArrayList<Integer>> target) {

        double a = 0;
        for (index1 = 0; index1 < keluaran.length; index1++) {
            for (index2 = 0; index2 < keluaran[0].length; index2++) {
                a += Math.pow(keluaran[index1][index2] - target.get(index1).get(index2), 2);
            }
        }
        a = a * 0.5;
        return a;
    }

    public double[][] updateWeightOutput(double learningRate, double[] outputHidden, double[][] galat, double[][] oldWeight) {

        double[][] newWeight = new double[galat.length][galat[0].length];

        for (index1 = 0; index1 < newWeight.length; index1++) {
            for (index2 = 0; index2 < newWeight[0].length; index2++) {
                newWeight[index1][index2] = learningRate * galat[index1][index2] * outputHidden[index1];
                newWeight[index1][index2] += oldWeight[index1][index2];
            }
        }
        return newWeight;
    }

    public double[][] updateBias(double learningRate, double[] outputHidden, double[][] galat, double[][] bias, double[] totalGalat) {
        double[][] newBias = new double[bias.length][bias[0].length];

        for (index1 = 0; index1 < newBias.length; index1++) {
            for (index2 = 0; index2 < newBias[0].length; index2++) {
                if (index2 == 0) {
                    newBias[index1][0] = learningRate * totalGalat[index1];
                    newBias[index1][0] += bias[index1][0];
                } else {
                    newBias[index1][index2] = learningRate * galat[index1][index2 - 1];
                    newBias[index1][index2] += bias[index1][index2];
                }
            }
        }

        return newBias;

    }

    public double[][] updateWeightInput(double learningRate, double[][] weightInput, double[][] input, double[] totalGalat) {
        double[][] newWeightInput = new double[input.length][input[0].length];

        for (index1 = 0; index1 < newWeightInput.length; index1++) {
            for (index2 = 0; index2 < newWeightInput[0].length; index2++) {
                newWeightInput[index1][index2] = (learningRate * totalGalat[index1] * input[index1][index2]) + weightInput[index1][index2];
            }
        }
        return newWeightInput;
    }
    
    public ArrayList<ArrayList<Double>> updateBobotInput(double learningRate,
            ArrayList<ArrayList<Double>> oldBobot,
            ArrayList<ArrayList<Double>> input, double[] totalGalat) {
        
        ArrayList<ArrayList<Double>> newBobot = new ArrayList<ArrayList<Double>>();

        for (index1 = 0; index1 < oldBobot.size(); index1++) {
            ArrayList<Double> newBobotAnak = new ArrayList<Double>();
            for (index2 = 0; index2 < oldBobot.get(index1).size(); index2++) {
                newBobotAnak.add(learningRate * totalGalat[index1] * input.get(index1).get(index2)
                                 + oldBobot.get(index1).get(index2));
            }
            newBobot.add(newBobotAnak);
        }
        return newBobot;
    }
    
    public double weightDecay(int[][] target, double[][] keluaran, double[][] weightInput, double[][] weightOutput) {
   // void weightDecayTipe2(int[][] target, double[][] keluaran, double[][] weightInput, double[][] weightOutput){
        double[] crossEntropy = new double[target.length];
        double[] weightDecay = new double[target.length];

        //Hittung Cross Entropy tiap pattern
        for (index1 = 0; index1 < crossEntropy.length; index1++) {
            crossEntropy[index1] = 0;
            for (index2 = 0; index2 < target[0].length; index2++) {
                crossEntropy[index1] += target[index1][index2] * Math.log(keluaran[index1][index2])
                        + (1 - target[index1][index2]) * Math.log(1 - keluaran[index1][index2]);
            }
            crossEntropy[index1] *= -1;
        }
        //Hitung Penalty term tiap pattern + cross entropy
        int beta = 10;
        //Based On Kamruzzaman
        /*double e1 = 0.05;
        double e2 = 0.0001;*/
        //Based On Rudy Setiono
        double e1 = 0.1;
        double e2 = 0.0001;
        for (index1 = 0; index1 < weightDecay.length; index1++) {
            double a = 0;
            double b = 0;
            double c = 0;
            double d = 0;

            for (index2 = 0; index2 < weightInput[0].length; index2++) {
                a += beta * Math.pow(weightInput[index1][index2], 2) / 1 + beta * Math.pow(weightInput[index1][index2], 2);
            }

            for (index2 = 0; index2 < weightOutput[0].length; index2++) {
                b += beta * Math.pow(weightOutput[index1][index2], 2) / 1 + beta * Math.pow(weightOutput[index1][index2], 2);
            }

            for (index2 = 0; index2 < weightInput[0].length; index2++) {
                c += Math.pow(weightInput[index1][index2], 2);
            }
            for (index2 = 0; index2 < weightOutput[0].length; index2++) {
                d += Math.pow(weightOutput[index1][index2], 2);
            }

            double penaltyTerm = e1 * (a + b) + e2 * (c + d);

            weightDecay[index1] = crossEntropy[index1] + penaltyTerm;
        }
        // bubble sort untuk nyari yang paling kecil

             for (index1 = 0; index1 < weightDecay.length; index1++) {
                  for (index2 = weightDecay.length; index2 > index1; index2--) {
                    if (weightDecay[index1] > weightDecay[index2 - 1]) {                    
                    double temp = weightDecay[index1];
                    weightDecay[index1] = weightDecay[index2-1];
                    weightDecay[index2-1] = temp;
                }

            }
        }
         double varWeightDecay = weightDecay[0];
        //weightDecay
        return varWeightDecay;
    }

    public double[][] giveWeightInput() {

        return weightInput;

    }

    public double[][] giveWeightOutput() {
        return weightOutput;
    }

    public double[][] giveWeightBias() {
        return weightBias;
    }

    public double[] giveNilaiHidden() {
        return nilaiHidden;
    }

    public double[] giveOutputHidden() {
        return outputHidden;
    }
    
    public double[] giveOutputHidden2() {
        return outputHidden2;
    }
    public double[][] giveOutput() {
        return output;
    }
    public double[][] giveOutput2() {
        return output2;
    }
     public double[][] giveOutput3() {
        return output3;
    }
      public double[][] giveBobotOutput() {
        return bobotOutput;
    }

    public double[][] giveBobotBias() {
        return bobotBias;
    }
    public double minDC() {
        return minWeightDecay;
    }
    public boolean checkBerhasil(){
        return berhasil;
    }
    public int getEpoch(){
        return epoch;
    }
    public double error(){
        return error;
    }
}
