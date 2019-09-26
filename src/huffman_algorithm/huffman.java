package huffman_algorithm;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class huffman {
     private tree _tree = new tree();
     private tree readTree = new tree();
     private BigInteger[] auxArray = null;
     private node<Integer> auxNode = null;
     private String auxString = "";
     private HashMap<Character, String> hash = null;
     private String filePathText;
     private String filePathResult;
     private String filePathTree;
     private String filePathDecoded;
     private String encodeText;
     private String treeString;
     private String characterSet;
     private byte extraZeros;
     private boolean pathsSetter;

     public huffman() {
          hash = new HashMap<>();
          filePathText = "";
          filePathResult = "";
          filePathTree = "";
          filePathDecoded = "";
          extraZeros = 0;
          encodeText = "";
          treeString = "";
          characterSet = "";
          pathsSetter = false;
     }

     private String reverseString(String str) {
          StringBuilder sb = new StringBuilder(str);
          sb.reverse();
          return sb.toString();
     }

     private void displayArray() {
          for (int i = 0; i < _tree.getArray().size(); ++i) {
               System.out.print(auxArray[i] + " ");
          }
     }

     public void setPaths(String pathName ,String resultPath, String fileName) {
          filePathText = pathName;
          filePathResult = resultPath+fileName+"-compressed.txt";
          filePathTree =  resultPath+fileName+"-tree.txt";
          filePathDecoded =  resultPath+fileName+"-decompressed.txt";
          pathsSetter = true;
     }

     private void createCodes() {

          for (int i = 0; i < _tree.getArray().size(); ++i) {

               auxString = "";
               auxNode = _tree.getArray().get(i);
               // System.out.println(auxNode.data);

               while (auxNode != _tree.getRoot()) {
                    auxString = auxString.concat(String.valueOf(auxNode.toParentWeight));
                    auxNode = auxNode.parent;
               }

               auxString = reverseString(auxString);
               // System.out.println(auxString);
               hash.put(_tree.getArray().get(i)._char, auxString);
          }
     }

     private void processFile(customSet set) {
          for (listElement i : set.list)
               _tree.addToArray(i.dataCounter, i.data);
          // System.out.println();

          _tree.createHuffmanTree();
          // _tree.displayArray(_tree.getArray());

          createCodes();
     }

     public void readFile() throws IOException {
          // System.out.println(new File(".").getAbsoluteFile());

          customSet set = new customSet();
          FileReader fr = new FileReader(filePathText);
          BufferedReader br = new BufferedReader(fr);
          auxString = "";
          char _char;
          String line;

          while ((line = br.readLine()) != null) {
               for (int i = 0; i < line.length(); ++i) {
                    _char = line.charAt(i);
                    // System.out.print(_char + "," + (int) (_char) + " ");
                    set.insert(_char);
                    auxString += _char;
               }
               set.insert((char) 13);
               auxString += (char) 13;
          }

          br.close();
          processFile(set);
     }

     private void writeFile() throws IOException {
          File file = new File(filePathResult);
          FileReader fr = new FileReader(filePathText);
          BufferedReader br = new BufferedReader(fr);
          String line, strAux = "";

          // creates the file
          file.createNewFile();

          // creates a FileWriter Object
          // Writes the content to the file

          while ((line = br.readLine()) != null) {
               for (int i = 0; i < line.length(); ++i) {
                    // System.out.print(hash.get(line.charAt(i)));
                    strAux = strAux + hash.get(line.charAt(i));
               }
               strAux = strAux + hash.get((char) 13);
          }

          extraZeros = bitsCollection(strAux, file);
          br.close();

          //     Path path = Paths.get(filePathResult);
          //   byte[] fileContents = Files.readAllBytes(path);

     }

     private byte bitsCollection(String bitsString, File file) throws IOException {

          FileOutputStream fos = new FileOutputStream(file);
          String auxStr = "";
          byte data;
          ByteBuffer b;
          byte extraZeros = 0;
          StringBuilder remove;

          while (!bitsString.isEmpty()) {
               if (bitsString.length() >= 7) {
                    auxStr = bitsString.substring(0, 7);
                    data = (byte) Integer.parseInt(auxStr, 2);
               } else {
                    extraZeros = (byte) ((byte) 7 - bitsString.length());

                    for (int i = 0; i < extraZeros; ++i)
                         bitsString = bitsString + "0";

                    data = (byte) Integer.parseInt(bitsString, 2);
               }

               remove = new StringBuilder(bitsString);
               remove.replace(0, 7, "");
               bitsString = remove.toString();

               b = ByteBuffer.allocate(1);
               b.put(data);
               fos.write(b.array());
          }
          fos.flush();
          fos.close();

          return extraZeros;
     }

     private void writeTree() throws IOException {
          File file = new File(filePathTree);
          _tree.writeTree(file, extraZeros);
     }

     public boolean compress() throws IOException {

          File f = new File(filePathText);
          pathsSetter = false;

          if (f.isFile()) {
               readFile();
               writeFile();
               writeTree();
               pathsSetter = true;
          }

          return pathsSetter;
     }

     private void readEncodeText(BufferedReader br) throws IOException {
          StringBuilder bitString;
          Path path = Paths.get(filePathResult);
          byte[] fileContents = Files.readAllBytes(path);

          for (int i = 0; i< fileContents.length; ++i) {
               bitString = new StringBuilder(Integer.toBinaryString((fileContents[i] & 0xFF) + 0x100).substring(1));
               bitString.deleteCharAt(0);
               encodeText += bitString;
          }
     }


     private void readTreeFile(BufferedReader br) throws IOException {
          String line;
          byte[] aux;
          StringBuilder bitString;
          byte extraZeros = 0, i = 0;

          while ((line = br.readLine()) != null) {

               aux = line.getBytes();
               if (i == 0) {
                    for (int j = 0; j < aux.length; ++j) {
                         bitString = new StringBuilder(Integer.toBinaryString((aux[j] & 0xFF) + 0x100).substring(1));
                         bitString.deleteCharAt(0);
                         treeString += bitString.toString();
                    }
               }

               if (i == 1) {
                    extraZeros = line.getBytes()[0];
                    bitString = new StringBuilder(treeString);

                    while (extraZeros > 0) {
                         bitString.deleteCharAt(bitString.length() - 1);
                         --extraZeros;
                    }
                    treeString = bitString.toString();
               }

               if (i == 2) {
                    characterSet = line;
               }

               if (i == 3) {
                    extraZeros = line.getBytes()[0];
                    bitString = new StringBuilder(encodeText);

                    while (extraZeros > 0) {
                         bitString.deleteCharAt(bitString.length() - 1);
                         --extraZeros;
                    }
                    encodeText = bitString.toString();
               }

               ++i;
          }

     }

     private void readCompressedFile() throws IOException {
          FileReader fr = new FileReader(filePathResult);
          BufferedReader br = new BufferedReader(fr);
          readEncodeText(br);

          fr = new FileReader(filePathTree);
          br = new BufferedReader(fr);
          readTreeFile(br);
     }

     private String decodeText(String data) {

          String decodedText = "";
          node<Integer> nodeAux = readTree.getRoot();

          for (int i = 0; i < data.length(); ++i) {

               if (nodeAux.isLeaf()) {
                    if (nodeAux._char != (char) (219))
                         decodedText += nodeAux._char;
                    else
                         decodedText += (char) 13;

                    nodeAux = readTree.getRoot();
                    --i;
               } else {
                    if (data.charAt(i) == '0')
                         nodeAux = nodeAux.left_child;
                    else
                         nodeAux = nodeAux.right_child;

               }
          }
          return decodedText;
     }

     public void writeDecodedFile() throws IOException {
          FileWriter fileWriter = new FileWriter(filePathDecoded);
          fileWriter.write(decodeText(encodeText));
          fileWriter.close();
     }

     public boolean uncompress() throws IOException {
          File f = new File(filePathResult);
          if (f.isFile()) {
               treeString = encodeText = "";
               readCompressedFile();

               // System.out.println();
               // System.out.println(encodeText);
               // System.out.println(treeString);

               readTree = new tree();
               readTree.createBinaryTree(new StringBuilder(treeString), new StringBuilder(characterSet));
               writeDecodedFile();
          }

          return pathsSetter;
     }


     public String _readFile() throws IOException {
          // System.out.println(new File(".").getAbsoluteFile());

          FileReader fr = new FileReader(filePathDecoded);
          BufferedReader br = new BufferedReader(fr);
          String text,line;
          text=line="";

          while ((line = br.readLine()) != null) {
               text+=line;
               text+=(char)13;
          }
          return text;
     }

     public HashMap<Character,String> getHashMap(){ return  hash;}

}