package com.rodrigosoares;

public class OperatorsTable {
    private boolean[][] table = new boolean[7][7];

    //CONSTRUCTOR
    public OperatorsTable(){
        this.fill();
    }

    //CLASS METHODS
    private void fill(){
        for (int y=0; y<7; y++)
            for (int x=0; x<7; x++){
                if(y==0 || y==1){
                    table[x][y] = false;
                }
                else if (y==2 || y==3){
                    if(x>0 && x<4)
                        table[x][y]=true;
                    else
                        table[x][y]=false;
                }
                else if(y==4 || y==5) {
                    if (x == 0 || x == 6)
                        table[x][y] = false;
                    else
                        table[x][y] = true;
                }
                else {
                    if (x == 6)
                        table[x][y] = false;
                    else
                        table[x][y] = true;
                }
            }
    }

    public boolean check(Character op1, Character op2) throws Exception{
        int nX=-1, nY=-1;

        switch (op1){
            case '(': nX = 0;
                break;
            case '^': nX = 1;
                break;
            case '*': nX = 2;
                break;
            case '/': nX = 3;
                break;
            case '+': nX = 4;
                break;
            case '-': nX = 5;
                break;
            case ')': nX = 6;
                break;
        }

        switch (op2){
            case '(': nY = 0;
                break;
            case '^': nY = 1;
                break;
            case '*': nY = 2;
                break;
            case '/': nY = 3;
                break;
            case '+': nY = 4;
                break;
            case '-': nY = 5;
                break;
            case ')': nY = 6;
                break;
        }
        if(nX==-1 || nY==-1)
            throw new Exception("Operação inválida!");

        return (table[nX][nY]);
    }

    //REQUIRED METHODS
    public String toString(){
        String ret="";
        for (int x=0; x<7; x++) {
            ret+="\n";
            for (int y = 0; y < 7; y++){
                ret+=table[x][y]+", ";
            }
        }
        return ret;
    }
}
