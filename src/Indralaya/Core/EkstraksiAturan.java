/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Indralaya.Core;

import java.util.ArrayList;

/**
 *
 * @author Rin Nadia
 */
public class EkstraksiAturan {

    int i, n, m;
    ArrayList<Integer> idxBaru;

    public void ekstraksiGreedy(ArrayList<ArrayList<Integer>> indexNotLost,
            ArrayList<ArrayList<Integer>> terurut,
            ArrayList<ArrayList<Double>> inputBaru,
            ArrayList<ArrayList<Integer>> targetBaru) {
        System.out.println("Sebelum");
        System.out.println(terurut);
        for (i = 0; i < terurut.size(); i++) {
            for (n = 0; n < terurut.get(i).size(); n++) {
                if (targetBaru.get(terurut.get(i).get(n)).get(0) != 0) {
                    terurut.get(i).remove(n);
                }
            }
        }
        System.out.println("Sesudah");
        System.out.println(terurut);

        int indexTerkecil1 = 0;
        int indexTerkecil2 = 0;
        for (i = 0; i < terurut.size(); i++) {
            for (n = 1; n < terurut.get(i).size(); n++) {
                if (inputBaru.get(terurut.get(i).get(n)).size()
                        < inputBaru.get(terurut.get(indexTerkecil1).get(indexTerkecil2)).size()) {
                    indexTerkecil1 = i;
                    indexTerkecil2 = n;
                }
            }
        }
        int besar = inputBaru.get(terurut.get(indexTerkecil1).get(indexTerkecil2)).size();
        //System.out.println(inputBaru.get(terurut.get(indexTerkecil1).get(indexTerkecil2)).size());
        //Remove yang tidak sama
        System.out.println(besar);

        idxBaru = new ArrayList<>();
        for (i = 0; i < terurut.size(); i++) {
            for (n = 0; n < terurut.get(i).size(); n++) {
                if (inputBaru.get(terurut.get(i).get(n)).size() == besar) {
                    idxBaru.add(terurut.get(i).get(n));
                }
            }
        }
        terurut.removeAll(terurut);
        System.out.println("Hasil = ");
        System.out.println(idxBaru);

        for (i = 0; i < idxBaru.size(); i++) {
            for (m = idxBaru.size() - 1; m > 1; m--) {
                n = 1;
                boolean kosong = false;
                boolean cek = false;
                while (kosong != true || cek != true) {
                    if (n > indexNotLost.get(idxBaru.get(i)).size()) {
                        kosong = true;
                    } else {
                        if (indexNotLost.get(idxBaru.get(i)).get(n) == indexNotLost.get(idxBaru.get(m)).get(n)) {
                             cek = true;
                            /*if (inputBaru.get(idxBaru.get(i)).get(n - 1) == inputBaru.get(idxBaru.get(m)).get(n - 1)) {
                                cek = true;
                            }*/
                        }
                    }
                }
                if(cek==true){
                    idxBaru.remove(m);
                }
            }
        }

    }

    public ArrayList<Integer> getIdxHasil() {
        return idxBaru;
    }
}
