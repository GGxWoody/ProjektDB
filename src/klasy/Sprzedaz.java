/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasy;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "Sprzedaz")
public class Sprzedaz implements Serializable{
    
    @Column(name = "id_sprzedazy")
    @Id
    @GeneratedValue
    private int sprzedazId;
    
    @ManyToOne
    @JoinColumn(name = "id_sprzedawcy",nullable = false)
    private Sprzedawca sprzedawca;
    
    @ManyToOne
    @JoinColumn(name = "id_klienta",nullable = false)
    private Klient klient;

    @ManyToOne
    @JoinColumn(name = "id_artykulu",nullable = false)
    private Artykul artykul;

    
    
    public Sprzedaz() {
    }

    public int getSprzedazId() {
        return sprzedazId;
    }

    public void setSprzedazId(int sprzedazId) {
        this.sprzedazId = sprzedazId;
    }

    public Sprzedawca getSprzedawca() {
        return sprzedawca;
    }

    public void setSprzedawca(Sprzedawca sprzedawca) {
        this.sprzedawca = sprzedawca;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Artykul getArtykul() {
        return artykul;
    }

    public void setArtykul(Artykul artykul) {
        this.artykul = artykul;
    }
    
    public String getSprzedawcaa(){
       return sprzedawca.getNazwisko() + " " + sprzedawca.getImie();
    }
    
    public String getKlientt(){
        return klient.getNazwisko() + " " + klient.getImie();
    }
    
    public String getArtykull(){
        return artykul.getNazwa();
    }
      
}
