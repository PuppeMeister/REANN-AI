/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Indralaya.Core;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rin Nadia
 */
public class PengolahanInput {

    CSVReader pembaca;
    int index1, index2, index3, index4;
    String[] strings;
    double[][] dataInput;
    int[][] target;
    ArrayList<ArrayList<Double>> inputBaru;
    ArrayList<ArrayList<Integer>> targetBaru;
    
    
 public void parsingInput(String alamatFile, int banyakData) {
         
        try {
            pembaca = new CSVReader(new FileReader(alamatFile));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PengolahanInput.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
        strings = pembaca.readNext();
        dataInput = new double[banyakData][strings.length-2];
        target = new int[banyakData][2];    
            index1 = 0;
            while((strings = pembaca.readNext())!=null) {
                if (strings[1].equals("Enak")) {
                    dataInput[index1][0] = 1;
                } else if (strings[1].equals("Kurang Enak")) {
                    dataInput[index1][0] = 0.5;
                } else {
                    dataInput[index1][0] = 0;
                }

                if (strings[2].equals("Pas")) {

                    dataInput[index1][1] = 1;

                } else if (strings[2].equals("Lebih")) {

                    dataInput[index1][1] = 0.5;

                } else {
                    dataInput[index1][1] = 0;
                }
                
                if (strings[3].equals("Enak")) {

                    dataInput[index1][2] = 1;

                } else if (strings[3].equals("Kurang Enak")) {
                    dataInput[index1][2] = 0.5;
                } else {
                    dataInput[index1][2] = 0;
                }
                
                if (strings[4].equals("Nyaman")) {

                   dataInput[index1][3] = 1;

                } else if (strings[4].equals("Kurang Nyaman")) {
                    dataInput[index1][3] = 0.5;
                } else {
                    dataInput[index1][3] = 0;
                }
                
                if (strings[5].equals("Bersih")) {

                    dataInput[index1][4] = 1.0;

                } else if (strings[5].equals("Kurang Bersih")) {
                    dataInput[index1][4] = 0.5;
                } else {
                    dataInput[index1][4] = 0;
                }
                
                if (strings[6].equals("Cepat")) {

                    dataInput[index1][5] = 1;

                } else {
                    dataInput[index1][5] = 0;
                }
                
                if (strings[7].equals("Ramah")) {

                    dataInput[index1][6] = 1;

                } else if (strings[7].equals("Kurang Ramah")) {
                    dataInput[index1][6] = 0.5;
                } else {
                    dataInput[index1][6] = 0;
                }
                
                if (strings[8].equals("Murah")) {

                    dataInput[index1][7] = 1;

                } else if (strings[8].equals("Normal")) {

                    dataInput[index1][7] = 0.5;

                } else {
                    dataInput[index1][7] = 0;
                }
                
                if (strings[9].equals("1")) {

                    dataInput[index1][8] = 1;

                } else {

                    dataInput[index1][8] = 0;
                }

                if (strings[10].equals("Strategis")) {

                    dataInput[index1][9] = 1;

                }
                else if(strings[10].equals("Kurang Strategis")){
                    dataInput[index1][9] = 0.5;
                }
                else {
                    dataInput[index1][9] = 0;
                }

               
                if (strings[11].equals("Puas")) {

                    target[index1][0] = 0;
                    target[index1][1] = 1;

                } else {

                   target[index1][0] = 1;
                   target[index1][1] = 0;
                }
                
                index1++;

            }

        } catch (IOException ex) {
            Logger.getLogger(PengolahanInput.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public double[][] dataInput(){
        return dataInput;
    }
    public int[][] target(){
        return target;
    }
    
    //Untuk Skenarion semua input tidak sama
    
    /*public List<Integer> parsingIndexLost(){
        
    }*/
    
    public double[] parsingInput2(double[][] inputLama, int indexPertama, ArrayList<ArrayList<Integer>> lostNeuron ){
        
        double[] inputBaru = new double [inputLama[0].length - lostNeuron.get(indexPertama).size()];
        
        index4=0;
        for(index2=0;index2<inputLama[0].length;index2++){
            int cek = 0;
            for(index3=0;index3<lostNeuron.get(indexPertama).size();index3++){
                if(index2==lostNeuron.get(indexPertama).get(index3)){
                    cek++;
                }
            }
            if(cek==0){
                inputBaru[index4] = inputLama[indexPertama][index2];
                index4++;
                }
            }
        
        
        return inputBaru;
    }
    
    public void inputTargetDiscretize(double[][] inputLama, ArrayList<ArrayList<Integer>> indexNeuron, int[][] target ){
        
        inputBaru = new ArrayList<ArrayList<Double>>();
        targetBaru = new ArrayList<ArrayList<Integer>>();
        
        for(index1=0;index1<indexNeuron.size();index1++){
            
            if(indexNeuron.get(index1).get(0) !=111){
                
                ArrayList<Integer> targetAnak = new ArrayList<Integer>();
                if(target[index1][0]==0){
                    //Puas
                    targetAnak.add(0);targetAnak.add(1);
                }
                else{
                    targetAnak.add(1);targetAnak.add(0);
                }
                
                ArrayList<Double> inputBaruAnak = new ArrayList<Double>();
                for(index2=0;index2<indexNeuron.get(index1).size();index2++){
                    double a = inputLama[index1][index2];
                    inputBaruAnak.add(a);
                }
                inputBaru.add(inputBaruAnak);
                targetBaru.add(targetAnak);
                
            }
        }
       
    }
    
    public void inputTargetDiscretize2(double[][] inputLama, ArrayList<ArrayList<Integer>> indexNotLost, int[][] target ){
        
        inputBaru = new ArrayList<ArrayList<Double>>();
        targetBaru = new ArrayList<ArrayList<Integer>>();
        
        for(index1=0;index1<indexNotLost.size();index1++){
            
                ArrayList<Integer> targetAnak = new ArrayList<Integer>();
                if(target[indexNotLost.get(index1).get(0)][0]==0){
                    //Puas
                    targetAnak.add(0);targetAnak.add(1);
                }
                else{
                    targetAnak.add(1);targetAnak.add(0);
                }
                
                ArrayList<Double> inputBaruAnak = new ArrayList<Double>();
                for(index2=1;index2<indexNotLost.get(index1).size();index2++){
                    double a = inputLama[indexNotLost.get(index1).get(0)][indexNotLost.get(index1).get(index2)];
                    inputBaruAnak.add(a);
                }
                inputBaru.add(inputBaruAnak);
                targetBaru.add(targetAnak);
            }
        }
       
    
public ArrayList<ArrayList<Double>> inputBaru(){
    return inputBaru;
}
public ArrayList<ArrayList<Integer>> targetBaru(){
    return targetBaru;
}
    
    //Untuk Skenario semua input sama
    public double[][] parsingInput(double[][] inputLama, List<Integer> indexTidakHilang){
        double[][] inputBaru = new double[inputLama.length][indexTidakHilang.size()];
        for(index1=0;index1<inputBaru.length;index1++){
            for(index2=0;index2<inputBaru[0].length;index2++){
                   inputBaru[index1][index2] = inputLama[index1][indexTidakHilang.get(index2)];     
                }
            }
        
        return inputBaru;
    }
}

