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
import javax.persistence.ManyToMany;

@Entity
@Table(name = "Artykul")
public class Artykul implements Serializable{
    
    @Column(name = "id_artykulu")
    @Id
    @GeneratedValue
    private int artykulId;
    
    @Column(name = "nazwa_artykulu")
    private String nazwa;
    
    @Column(name = "cena_artykulu")
    private int cena;
    

    public Artykul(String nazwa, int cena) {
        this.nazwa = nazwa;
        this.cena = cena;
    }

    public Artykul() {
    }

    public int getArtykulId() {
        return artykulId;
    }

    public void setArtykulId(int artykulId) {
        this.artykulId = artykulId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Artykul{" + "artykulId=" + artykulId + ", nazwa=" + nazwa + ", cena=" + cena + '}';
    }
    
}
