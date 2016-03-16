/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Indralaya.Core;

import java.util.ArrayList;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rin Nadia
 */
public class RuleExtraction {

    ArrayList<ArrayList<String>> hasil;
    ArrayList<ArrayList<Integer>> terurutBersih, terurutBersihWork;
    ArrayList<Integer> jumlah;
    int i, m, n;

    public void getPuas(ArrayList<ArrayList<Integer>> terurut,
            ArrayList<ArrayList<Integer>> targetBaru) {

        terurutBersih = new ArrayList<>();
        for (i = 0; i < terurut.size(); i++) {
            ArrayList<Integer> anak = new ArrayList<>();
            for (n = 0; n < terurut.get(i).size(); n++) {
                if (targetBaru.get(terurut.get(i).get(n)).get(0) == 0) {
                    anak.add(terurut.get(i).get(n));
                }
            }
            terurutBersih.add(anak);
        }
        terurut.removeAll(terurut);
    }

    public void removeLongRule(ArrayList<ArrayList<Double>> inputBaru) {
        jumlah = new ArrayList<>();
        for (i = 0; i < terurutBersih.size(); i++) {
            for (n = 0; n < terurutBersih.get(i).size(); n++) {
                for (m = terurutBersih.get(i).size() - 1; m > n; m--) {
                    if (inputBaru.get(terurutBersih.get(i).get(n)).size() == inputBaru.get(terurutBersih.get(i).get(m)).size()) {
                        terurutBersih.get(i).remove(m);
                    }
                    /*else{
                     jumlah.add(inputBaru.get(terurutBersih.get(i).get(m)).size());
                     }*/
                }
            }
        }

        for (i = 0; i < terurutBersih.size(); i++) {
            for (n = 0; n < terurutBersih.get(i).size(); n++) {
                jumlah.add(inputBaru.get(terurutBersih.get(i).get(n)).size());
            }
            // jumlah.add(4444);
        }
        System.out.println("Hasil sebelum diremove");
        System.out.println(jumlah);

        int terkecil =0;
        for(n=jumlah.size()-1;n>0;n--){
            if(jumlah.get(n)<jumlah.get(n-1)){
                terkecil = jumlah.get(n);
            }
            else{
                terkecil = jumlah.get(n-1);
            }
        }
        System.out.println("Terkecil");
        System.out.println(terkecil);
    }

    public void removeSpesificWithGeneral(ArrayList<ArrayList<Double>> inputBaru) {
        for (i = 0; i < terurutBersih.size(); i++) {
            for (n = 0; n < terurutBersih.get(i).size(); n++) {
                for (m = terurutBersih.get(i).size() - 1; m > n; m--) {
                    if (inputBaru.get(terurutBersih.get(i).get(n)).size() > inputBaru.get(terurutBersih.get(i).get(m)).size()) {
                        int a = terurutBersih.get(i).get(n);
                        terurutBersih.get(i).add(n, terurutBersih.get(i).get(m));
                        terurutBersih.get(i).add(m, a);
                    }
                }
            }
        }
    }

    public ArrayList<ArrayList<Integer>> getTerurutBersih() {
        return terurutBersih;
    }

    public ArrayList<Integer> getJumlah() {
        return jumlah;
    }
}
