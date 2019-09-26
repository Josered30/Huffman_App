package App;
public class TableElement{

    private char data;
    private String code;

    public TableElement(){
        this.data='\0';
        this.code="";
    }

    public TableElement(char data, String code){
        this.data=data;
        this.code=code;
    }


    public char getData() {return data;}

    public void setData(char data){this.data=data;}

    public String getCode(){return code;}

    public void setCode(String code) {this.code=code;}

}
