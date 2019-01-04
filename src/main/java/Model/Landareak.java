package Model;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author DM3-2-15
 */
public class Landareak {
    private String name,cName, description,color,flowers;
    private float size;
    public Landareak(){
        
    }
    public Landareak(String a,String b, String c, String d,Boolean e,String f){
        setName(a);
        setDescription(b);
        setColor(c);
        setSize(d);
        setFlowers(e);
        setCName(f);
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDescription(String desc){
        description=desc;
    }
    public void setColor(String col){
        color=col;
    }
    public void setSize(String height){
        size=Float.parseFloat(height);
    }
    public void setFlowers(Boolean flow){
        if (flow==true){
            flowers="yes";
        }
        else{
            flowers="no";
        }
        
    }
    public void setCName(String cname){
        cName=cname;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getColor(){
        return color;
    }
    public String getSize(){
        return size+"m";
    }
    public String getFlowers(){
        return flowers;
    }
    public boolean getFlowers(int i){
        if(flowers.equals("no")){
            return false;
        }
        else{
            return true;
        }
    }
    public String getCName(){
        return cName;
    }
}
