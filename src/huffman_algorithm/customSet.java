package huffman_algorithm;

import java.util.LinkedList;


class listElement{
    public char data;
    public int dataCounter;

    public listElement(char _data, int _dataCounter){
        data=_data;
        dataCounter=_dataCounter;
    }
}


public class customSet {

    public LinkedList<listElement>list;

    public customSet(){
        list= new LinkedList<>();
    }

    public void insert(char data){
        boolean insertedOrUpdated=true;
        int index=0;

        for(int i=0;i<list.size();++i){
            if(list.get(i).data==data) {
                insertedOrUpdated=false;
                index=i;
                break;
            }
        }

        if(insertedOrUpdated){
            list.add(new listElement(data,0));
        } else{
            list.set(index,new listElement(list.get(index).data,list.get(index).dataCounter+1));
        }
    }

}
