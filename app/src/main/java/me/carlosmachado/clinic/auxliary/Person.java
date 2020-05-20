package me.carlosmachado.clinic.auxliary;

public class Person {

    private String nome;
    private int id;

    public Person(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    @Override
    public String toString()
    {
        return nome;
    }

}
