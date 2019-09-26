package huffman_algorithm;

import java.util.ArrayList;

public class heapSort {

    public heapSort(){ }

    private void swap(ArrayList<node<Integer>> vect, int a , int b){
        node<Integer> temp = vect.get(a);
        vect.set(a,vect.get(b));
        vect.set(b,temp);
    }

    private void heapify(ArrayList<node<Integer>> vect,int size, int root){

        int child = root;
        int left = 2*root+1;
        int right =2*root+2;

        if(left < size && vect.get(child).data < vect.get(left).data) child = left;
        if(right < size && vect.get(child).data < vect.get(right).data) child=right;

        if (child != root) {
            swap(vect,child,root);
            heapify(vect,size,child);
        }

    }

    public void HeapSort(ArrayList<node<Integer>> vect) {

        for (int i = (vect.size()/ 2) - 1; i >= 0; --i)
            heapify(vect,vect.size(),i);

        for (int i = vect.size() - 1; i >= 0; --i) {
            swap(vect,0,i);
            heapify(vect,i,0);
        }

    }

}
