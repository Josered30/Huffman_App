package huffman_algorithm;

class node<T extends Comparable<T>>{

    public node<T> left_child;
    public node<T> right_child;
    public node<T> parent;
    public int toParentWeight;
    public char _char;
    public T data;

    public node(T _data, char char_){
        left_child = null;
        right_child = null;
        parent =null;
        toParentWeight = 0;
        data = _data;
        _char = char_;
    }

    public boolean isLeaf(){
        if(left_child==null && right_child==null)
            return true;
        return false;
    }
}

