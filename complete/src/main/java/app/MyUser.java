package app;

/**
 * Created by jaydatta on 3/18/17.
 */
public class MyUser {

    private  String name;
    private  String age;
    private  String id;
    private  String sex;
    private  String tableName=null;

    public MyUser(){

    }

    public MyUser(String name, String id, String age, String sex){

        this.name = name;
        this.age = age;
        this.id = id;
        this.sex = sex.equals("male") ? "male" : "female";
        setTableName();
    }

    public void setTableName(){
        tableName = name+"_"+id+"_"+age+"_"+sex;
    }


    public  String getTableName(){
        return tableName;
    }

    @Override
    public String toString(){

        return "("+name+" TEXT, "+age+" TEXT, "+id+" TEXT,"+ sex+" TEXT"+")";
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}