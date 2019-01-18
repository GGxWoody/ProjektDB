/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
@Table(name="sprzedawca")
public class Sprzedawca implements Serializable{
    
    @Column(name="id_sprzedawcy",unique = true)
    @Id
    @GeneratedValue
    private int sprzedawcaId;
    
    @Column(name = "imie_sprzedawcy")
    private String imie;
    
    @Column(name = "nazwisko_sprzedawcy")
    private String nazwisko;
    
    @Column(name = "pesja_sprzedawcy")
    private long pensja;
    
    @OneToMany(mappedBy = "sprzedawca",cascade = CascadeType.REMOVE,orphanRemoval = true)
    private List<Sprzedaz> sprzedaze;
    

    public Sprzedawca(String imie, String nazwisko, long pensja) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pensja = pensja;
    }
    
    public Sprzedawca () {}

    public int getSprzedawcaId() {
        return sprzedawcaId;
    }

    public void setSprzedawcaId(int sprzedawcaId) {
        this.sprzedawcaId = sprzedawcaId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public long getPensja() {
        return pensja;
    }

    public void setPensja(long pensja) {
        this.pensja = pensja;
    }

    @Override
    public String toString() {
        return  nazwisko + " " + imie;
    }


    
    
    
}
