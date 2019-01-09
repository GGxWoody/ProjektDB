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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "Sprzedaz")
public class Sprzedaz implements Serializable{
    
    @Column(name = "id_sprzedazy")
    @Id
    @GeneratedValue
    private int sprzedazId;
    
    @ManyToOne
    @JoinColumn(name = "id_sprzedawcy")
    private Sprzedawca sprzedawca;
    
    @ManyToOne
    @JoinColumn(name = "id_klienta")
    private Klient klient;


    
    
    public Sprzedaz() {
    }

    public int getSprzedazId() {
        return sprzedazId;
    }    
}
