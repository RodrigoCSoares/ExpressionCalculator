package com.rodrigosoares;// Throwable


import java.lang.reflect.Method;

public class Fila <X> implements Cloneable
{
    private Object[] item;
    private int      inicio =  0;
    private int      fim    = -1;
    private int      qtd    =  0;

    private X meuCloneDeX (Object modelo)
    {
        //return (X)modelo.clone();
        X ret=null;
        try
        {
            Class<?> classe = modelo.getClass();
            Class<?>[] tipoDoParametroFormal = null; // null pq nao tem parametros
            Method metodo = classe.getMethod ("clone", tipoDoParametroFormal);
            Object[] parametroReal = null; // null pq nao tem parametros
            ret = (X)metodo.invoke (modelo, parametroReal);
        }
        catch (Exception erro)
        {}
        return ret;
    }

    public Fila (int capacidade) throws Exception
    {
        if (capacidade<=0)
        {
            Exception erro;
            erro = new Exception ("Capacidade invalida");
            throw erro;
        }

        this.item = new Object [capacidade];
    }

    public void guarde (X x) throws Exception
    {
        if (x==null)
            throw new Exception ("Informacao ausente");

        if (this.qtd==this.item.length)
            throw new Exception ("Nao cabe");

        this.fim++;
        if (this.fim==this.item.length)
            this.fim = 0;

        if (x instanceof Cloneable)
            this.item[this.fim] = meuCloneDeX (x);
        else
            this.item[this.fim] = x;

        this.qtd++;
    }

    public X getUmItem () throws Exception
    {
        if (this.qtd==0)
            throw new Exception ("Vazio");

        if (this.item[this.inicio] instanceof Cloneable)
            return meuCloneDeX (this.item[this.inicio]);
        else
            return (X)this.item[this.inicio];
    }

    public void jogueForaUmItem () throws Exception
    {
        if (this.qtd==0)
            throw new Exception ("Vazio");

        this.item[this.inicio] = null;

        this.inicio++;
        if (this.inicio==this.item.length)
            this.inicio=0;

        this.qtd--;
    }

    public boolean cheia ()
    {
        return this.qtd==this.item.length;
    }

    public boolean vazia ()
    {
        return this.qtd==0;
    }

    public int getQuantosElementos ()
    {
        return this.qtd;
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        //if (!(obj instanceof Fila<String>))
        if (this.getClass() != obj.getClass())
            return false;

        Fila<X> f = (Fila<X>)obj;

        if (this.qtd != f.qtd)
            return false;

        int atualThis=this.inicio,
                atualF   =f   .inicio,
                qtd      =this.qtd;

        while (qtd>0)
        {
            if (!this.item[atualThis].equals (
                    f   .item[atualF   ]))
                return false;

            qtd--;

            atualThis++;
            if (atualThis==this.item.length)
                atualThis=0;

            atualF++;
            if (atualF==f.item.length)
                atualF=0;
        }

        return true;
    }

    public String toString ()
    {
        String ret="";

        int atual = this.inicio,
                qtd   = this.qtd;

        while (qtd>0)
        {
            ret += this.item[atual];
            qtd--;

            if (qtd>0)
                ret += "|";

            atual++;
            if (atual==this.item.length)
                atual=0;
        }

        return ret;
    }

    public int hashCode ()
    {
        int ret = 666; // so nao pode ser zero

        ret = 7*ret + new Integer(this.inicio).hashCode();
        ret = 7*ret + new Integer(this.fim   ).hashCode();
        ret = 7*ret + new Integer(this.qtd   ).hashCode();

        int atual = this.inicio,
                qtd   = this.qtd;

        while (qtd>0)
        {
            //desnecessario pois sei que o guardeUmItem nao guarda null no vetor
            //if (this.item[atual]!=null)
            ret = 7*ret + this.item[atual].hashCode();

            qtd--;

            atual++;
            if (atual==this.item.length)
                atual=0;
        }

        return ret;
    }

    public Fila (Fila<X> modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        this.qtd    = modelo.qtd;
        this.inicio = modelo.inicio;
        this.fim    = modelo.fim;
        this.item   = new Object [modelo.item.length];

        for (int i=0; i<modelo.item.length; i++)
            this.item[i] = modelo.item[i];
    }

    public Object clone ()
    {
        Fila<X> ret=null;

        try
        {
            ret = new Fila<X> (this);
        }
        catch (Exception erro)
        {} // sei que nao vai dar excecao, pq o construtor de copia da excecao quando o modelo � null; aqui estou passando para ser modelo o this e o this nunca � nulo; nunca; nunquinha; em metodo nenhum

        return ret;
    }
}






