package br.com.jortec.minhasmotos.dominio;

/**
 * Created by Jorliano on 11/10/2015.
 */
public class Moto {
    private String Modelo;
    private String Marca;
    private int foto;


    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
