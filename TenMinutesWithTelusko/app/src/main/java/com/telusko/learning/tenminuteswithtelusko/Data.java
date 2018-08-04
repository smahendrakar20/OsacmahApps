package com.telusko.learning.tenminuteswithtelusko;

/**
 * Created by mohanmmohadikar on 7/6/18.
 */

public class Data {

    private int id;
    private String que;
    private String op1;
    private String op2;
    private String op3;
    private String op4;





    public  Data(int id,String que, String op1, String op2, String op3, String op4){

        this.id= id;
        this.que = que;
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
        this.op4 = op4;



    }

    public int getId() {
        return id;
    }
    public String getQue() {
        return que;
    }

    public String getOp1() {
        return op1;
    }

    public String getOp2() {
        return op2;
    }

    public String getOp3() {
        return op3;
    }

    public String getOp4() {
        return op4;
    }
}
