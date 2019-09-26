package huffman_algorithm;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class tree {

    private node<Integer> root;
    private ArrayList<node<Integer>>nodeArray;
    private ArrayList<node<Integer>>auxArray;
    private heapSort heap_Sort;
    String bitStr;
    String elementStr;

    public tree(){
        root=null;
        heap_Sort = new heapSort();
        nodeArray=new ArrayList<>();
    }

    public void addToArray(int data, char _char){
        nodeArray.add(new node<>(data,_char));
    }

    public void displayArray(ArrayList<node<Double>> _nodeArray){
        int size = _nodeArray.size();

        for(int i=0; i<size;++i)
            System.out.print(_nodeArray.get(i).data + ","+_nodeArray.get(i)._char+ " ");

        System.out.println();

    }

    public void createHuffmanTree(){


        node<Integer> nodeAux, nodeAux2, nodeAux3;
        auxArray=new ArrayList<>(nodeArray);  //create a copy

        while(nodeArray.size()>1){

            heap_Sort.HeapSort(nodeArray);
            nodeAux2 = nodeArray.get(0);
            nodeAux3= nodeArray.get(1);

            //displayArray(nodeArray);

            if(nodeArray.size()>2)
                nodeAux = new node<>(nodeAux2.data + nodeAux3.data, '\0');
            else {
                root = new node<>(nodeAux2.data + nodeAux3.data, '\0');
                nodeAux = root;
            }

            if (nodeAux2.data < nodeAux3.data) {
                nodeAux.left_child = nodeAux2;
                nodeAux.right_child = nodeAux3;

            } else {
                nodeAux.left_child = nodeAux3;
                nodeAux.right_child = nodeAux2;
            }

            nodeAux.left_child.toParentWeight = 0;
            nodeAux.right_child.toParentWeight = 1;

            nodeArray.get(0).parent = nodeAux;
            nodeArray.get(1).parent = nodeAux;

            nodeArray.remove(0); //remove index 0
            nodeArray.set(0,nodeAux); //put in index 0 which was index 1 until index 0 was removed

           // binaryTreePrinter.printNode(nodeAux);
        }
    }


    private node<Integer> createBinaryTreeUtil(StringBuilder bitString, StringBuilder data, node<Integer>_root, int toParentWeight) {

        if (!bitString.toString().isEmpty() || !data.toString().isEmpty()) {
            if (bitString.toString().charAt(0) == '0') {
                _root = new node<Integer>(0, data.toString().charAt(0));
                _root.toParentWeight = toParentWeight;
                bitString.deleteCharAt(0);
                data.deleteCharAt(0);

            } else {
                _root = new node<Integer>(0, '\0');
                _root.toParentWeight=toParentWeight;

                bitString.deleteCharAt(0);

                _root.left_child= createBinaryTreeUtil(bitString, data, _root.left_child, 0);
                _root.right_child=createBinaryTreeUtil(bitString, data, _root.right_child, 1);
            }

        }
        return _root;
    }


    private void writeTreeUtil(node<Integer> _node){
        if (_node.isLeaf()) {
            bitStr=bitStr+"0";
            if(_node._char!=(char)13)
             elementStr=elementStr+_node._char;
            else
                elementStr=elementStr+ (char)219;

        } else {
            bitStr=bitStr+"1";
            writeTreeUtil(_node.left_child);
            writeTreeUtil(_node.right_child);
        }
    }


    byte writeBytes(FileOutputStream fos ) throws IOException {

        StringBuilder removeAux;
        ByteBuffer b;
        byte extraZeros = 0;
        byte _byte;

        while (!bitStr.isEmpty()) {

            if (bitStr.length() >= 7)
                _byte = (byte) Integer.parseInt(bitStr.substring(0, 7), 2);
            else {
                extraZeros = (byte)((byte)7 - bitStr.length());
                for (int i = 0; i < extraZeros; ++i)
                    bitStr = bitStr + "0";

                _byte = (byte) Integer.parseInt(bitStr, 2);
            }

            removeAux = new StringBuilder(bitStr);
            removeAux.replace(0,7,"");
            bitStr = removeAux.toString();

            b = ByteBuffer.allocate(1);
            b.put(_byte);
            fos.write(b.array());
        }
        fos.flush();

        return extraZeros;
    }


    public void writeTree(File file, byte extraZeros) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bitStr= new String();
        byte extraZerosTreeByteArray=0;
        elementStr=bitStr="";

        writeTreeUtil(root);
        extraZerosTreeByteArray= writeBytes(fos);
        bw.newLine();

        bw.write(extraZerosTreeByteArray);
        bw.newLine();

        bw.write(elementStr);
        bw.newLine();

        bw.write(extraZeros);

        bw.close();
        fos.close();
    }




    public void createBinaryTree(StringBuilder bitString, StringBuilder data){
        root =createBinaryTreeUtil(bitString,data,root,0);
    }


    public node<Integer> getRoot(){ return root;}

    public ArrayList<node<Integer>> getArray(){ return auxArray; }


}