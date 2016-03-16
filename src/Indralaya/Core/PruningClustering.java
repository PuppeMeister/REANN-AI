/*
 * PruningClustering class yang berbeda dengan class yang di package sebelumnya
 * 
 */
package Indralaya.Core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rin Nadia
 */
public class PruningClustering {
    int index1, index2, index3, index4, index5;
    List<Double> nilaiPerCluster, nPerCluster;
    ArrayList<ArrayList<Integer>> indexLostNeuron, indexNotLost, indexNeuron,terurut;
    ArrayList<Integer> indexExclude;
    
    
    public void clustering(double[] nAktivasiHidden){
    terurut = new ArrayList<>();
    for(double a : nPerCluster){
        ArrayList<Integer> anak = new ArrayList<>();
        for(index1=0;index1<nAktivasiHidden.length;index1++){
            if(nAktivasiHidden[index1]== a){
                anak.add(index1);
            }
        }
        terurut.add(anak);
        }
    }
    public ArrayList<ArrayList<Integer>> getTerurut(){
        return terurut;
    }
    
    public double [] doDiscretize(double e, double[] nAktivasiHidden){    
        double[] nAkHiddenWork = new double[nAktivasiHidden.length];
        List<Double> listAkHidden = new ArrayList<>();
        //System.out.println(" Nilai Aktivasi Hidden Layer");
        for (index1 = 0; index1 < nAktivasiHidden.length; index1++) {
            //System.out.println(nAktivasiHidden[index1]);
            nAkHiddenWork[index1]=nAktivasiHidden[index1];
            listAkHidden.add(nAktivasiHidden[index1]);
        }
        ArrayList<Integer> jmlhPerCluster = new ArrayList<>();
        //Array List nilaiPerCluster tidak digunakan karena malfunction
        nilaiPerCluster = new ArrayList<>();
        
        List<Integer> indexArray = new ArrayList<>();
        int indexNilai = 0;
        for(index1=0;index1<nAktivasiHidden.length;index1++){
            if(nAkHiddenWork[index1]!=0){
                double uji = nAkHiddenWork[index1];
                int jmlhCluster =1;
                double totalCluster = nAkHiddenWork[index1];
                indexArray.add(index1);
                
                for(index2=index1+1;index2<nAktivasiHidden.length;index2++){
                    if(nAkHiddenWork[index2]!=0){
                        double hasil = uji - nAkHiddenWork[index2];
                        if(hasil<e |hasil==e){
                            jmlhCluster+=1;
                            totalCluster+= nAkHiddenWork[index2];
                            nAkHiddenWork[index2]=0;
                            indexArray.add(index2);
                        }
                    }
                    
                }
               double total = totalCluster/jmlhCluster;
               jmlhPerCluster.add(jmlhCluster);
               //Tidak Berjalan Dengan Baik
               nilaiPerCluster.add(total);
               
               for(index3=0;index3<indexArray.size();index3++){
                   int idx = indexArray.get(index3);
                   nAktivasiHidden[idx]= total;
               }
               
               nAkHiddenWork[index1]=0;
               indexArray.removeAll(indexArray);
            }
        }

        return nAktivasiHidden;
     }
    
    
    public List<Double> nilaiPerCluster(double[] nAktivasi){
    /** 
    * Dibuat karena array List nilaiPerCluster, tidak dapat menampung semua elemen
    * Penyebab tidak diketahui
    **/
    nPerCluster = new ArrayList<>();
    double[] arrayBaru = new double[nAktivasi.length];
    
    for(index1=0;index1<arrayBaru.length;index1++){
        arrayBaru[index1] = nAktivasi[index1];
    }
    for(index1=0;index1<arrayBaru.length;index1++){
        if(arrayBaru[index1]!=111.1){
            nPerCluster.add(arrayBaru[index1]);
            for(index2=arrayBaru.length-1;index2>index1;index2--){
                if(arrayBaru[index2]==arrayBaru[index1]){
                    arrayBaru[index2]=111.1;
                }
            }
            arrayBaru[index1]=111.1;
        }
    }
    for(index1=0;index1<nPerCluster.size();index1++){
        if(nPerCluster.get(index1).isNaN()==true){
            nPerCluster.remove(index1);
        }
    }
    
    return nPerCluster;
        
    }
    
    public void pruning(double[][] weightInput, double[][] weightOutput, double threshold){
        indexLostNeuron = new ArrayList<>();
        
        for(index1=0;index1<weightInput.length;index1++){
            ArrayList<Integer> indexInside = new ArrayList<>();
            for(index2=0;index2<weightInput[0].length;index2++){
                double nilai1= weightOutput[index1][0]*weightInput[index1][index2];
                double nilai2= weightOutput[index1][1]*weightInput[index1][index2];
                
                double nilaiMax = 0;
                if(nilai1<nilai2){
                    nilaiMax = nilai2;
                    
                }
                else{
                    nilaiMax = nilai1;
                }
                if(nilaiMax<0){
                    nilaiMax*=-1;
                }
                
                if(nilaiMax<4*threshold){
                   indexInside.add(index2);
                    //lostNeuron.add(hiddenLayer);
                }
            }
            indexLostNeuron.add(indexInside);
        }
            //Parsing is kedalam arraylist indexExclude dan indexNotLost
            indexNotLost = new ArrayList<>();
            indexExclude = new ArrayList<>();
            //Only for testing
            indexNeuron = new ArrayList<>();
            
            for(index1=0;index1<indexLostNeuron.size();index1++){
                if(indexLostNeuron.get(index1).size()!=10){
                    
                    ArrayList<Integer> indexNL = new ArrayList<>();
                    //Index Pada Index Lost Neuron
                    indexNL.add(index1);
                    for(index2=0;index2<10;index2++){
                        int cek = 0;
                        for(index3=0;index3<indexLostNeuron.get(index1).size();index3++){
                            if(index2 == indexLostNeuron.get(index1).get(index3)){
                                cek++;
                            }
                        }
                        if(cek==0){
                            indexNL.add(index2);
                        }
                    }
                    indexNotLost.add(indexNL);
                    indexNeuron.add(indexNL);
                 }
                else{
                    ArrayList<Integer> indexN = new ArrayList<>();
                    indexN.add(111);
                    indexNeuron.add(indexN);
                    indexExclude.add(index1);
                }
            }
    }
   public ArrayList<ArrayList<Integer>> lostNeuron(){
       return indexLostNeuron;
   }
   public ArrayList<ArrayList<Integer>> indexNeuron(){
       return indexNeuron;
   }
   public ArrayList<ArrayList<Integer>> notLostNeuron(){
       return indexNotLost;
   }
   public ArrayList<Integer> lostExclude(){
       return indexExclude;
   }
   public List<Double> returnNilaiCluster(){
       return nilaiPerCluster;
   }
   
   
}
