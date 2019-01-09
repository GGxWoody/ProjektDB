/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasy;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Table(name = "Klient")
public class Klient implements Serializable{
    
    @Column(name = "id_klienta")
    @Id
    @GeneratedValue
    private int klientID;
    
    @Column(name = "imie_klienta")
    private String imie;
    
    @Column(name = "nazwisko_klienta")
    private String nazwisko;
    
    @OneToMany(mappedBy = "klient")
    private List<Sprzedaz> sprzedaze;

    public Klient(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

    public Klient() {
    }

    public int getKlientID() {
        return klientID;
    }

    public void setKlientID(int klientID) {
        this.klientID = klientID;
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

    @Override
    public String toString() {
        return "Klient{" + "klientID=" + klientID + ", imie=" + imie + ", nazwisko=" + nazwisko + '}';
    }
    
}
