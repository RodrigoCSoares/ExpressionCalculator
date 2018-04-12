package com.rodrigosoares;

import java.lang.reflect.Method;

import static java.lang.Float.valueOf;

public class Pilha <X> implements Cloneable{
    private Object pilha[];
    private int ultimo = -1;

    //CONSTRUTORES
    public Pilha(int tam) throws Exception{
        if(tam<1)
            throw new Exception("Tamanho invalido!");

        pilha = new Object[tam];
    }

    public Pilha(Pilha<X> modelo)throws Exception{
        if(modelo==null)
            throw new Exception("Modelo inválido");

        this.ultimo = modelo.ultimo;
        this.pilha = new Object[modelo.pilha.length];

        for(int i=0; i<this.pilha.length;i++)
            this.pilha[i]=modelo.pilha[i];
    }


    //METODOS ESPECIFICOS DESSA CLASSE
    public void guarde(X x) throws Exception {
        if(x=="" || x==null)
            throw new Exception("Valor invalido!");

        if((ultimo+1)==pilha.length)
            throw new ArrayIndexOutOfBoundsException("Pilha cheia!");

        ultimo++;

        if(x instanceof Cloneable)
            pilha[ultimo] = meuCloneDe(x);
        else
            pilha[ultimo] = x;

    }

    public void retira() throws Exception{
        if((ultimo)<0)
            throw new Exception("Fila vazia!");

        pilha[ultimo] = null;
        ultimo--;
    }

    public X getItem()throws Exception {
        if(ultimo==-1)
            throw new Exception("Vazio!");
        return (X)pilha[ultimo];
    }

    public boolean cheia() {
        return ultimo==pilha.length-1;
    }

    public boolean vazia() {
        if (ultimo<0)
            return true;
        else
            return false;

    }

    public int getQuantosElementos() {
        return ultimo+1;
    }


    //METODOS OBRIGATORIOS
    public boolean equals(Object obj) {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if(this.getClass()!=obj.getClass())
            return false;

        Pilha<X> p = (Pilha<X>)obj;

        if(this.getQuantosElementos()!=p.getQuantosElementos())
            return false;

        int thisUlt = this.ultimo;

        while(thisUlt>=0) {
            if(!this.pilha[thisUlt].equals(p.pilha[thisUlt]))
                return false;

            thisUlt--;
        }
        return true;
    }

    public String toString() {
        String ret="";

        ret+="[ "+pilha[0];

        for(int i=1; i<=ultimo ;i++) {
            ret+=", "+pilha[i];
        }

        ret+=" ]";
        return ret;
    }

    public int hashCode() {
        int ret = 9;
        ret = 7*ret + valueOf(this.ultimo).hashCode();

        int qtd = this.ultimo;
        while(qtd>=0){
            ret = 7*ret + this.pilha[qtd].hashCode();
            qtd--;
        }

        return ret;
    }

    //METODOS FACULTATIVOS
    public Object clone(){
        Pilha<X> ret = null;
        try {
            ret = new Pilha<X>(this);
        }catch (Exception error){
        }
        return ret;
    }

    private X meuCloneDe(X modelo){
        X ret = null;
        try {
            Class<?> classe = modelo.getClass();
            Class<?> []parametrosFormais = null;
            Method metodo = classe.getMethod("clone", parametrosFormais);
            Object[] parametroReal = null;
            ret = (X)metodo.invoke(modelo,parametroReal);

        }catch (Exception e){
        }
        return ret;
    }

}
