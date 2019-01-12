package projektdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import klasy.Artykul;
import klasy.Klient;
import klasy.Sprzedawca;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import sun.security.krb5.internal.KDCOptions;

public class FXMLDocumentController implements Initializable{
    
    private ConnectionDB connection;
    //Artykul TAB
    @FXML private TextField nazwaArtykulu;
    @FXML private TextField cenaArtykulu;
    @FXML private Button dodajArtykul;
    @FXML private TableView<Artykul> tabelaArtykul;
    @FXML private TableColumn tabelaNazwaArtykul;
    @FXML private TableColumn tabelaCenaArtykul;
    @FXML private Label errorArtykul;
    @FXML private Button usunArtykul;
    
    //Sprzedawca TAB
    @FXML private TextField imieSprzedawcy;
    @FXML private TextField nazwiskoSprzedawcy;
    @FXML private TextField pensjaSprzedawcy;
    @FXML private Button dodajSprzedawce;
    @FXML private TableView<Sprzedawca> tabelaSprzedawca;
    @FXML private TableColumn tabelaImieSprzedawca;
    @FXML private TableColumn tabelaNazwiskoSprzedawca;
    @FXML private TableColumn tabelaPensjaSprzedawca;
    @FXML private Label errorSprzedawca;
    @FXML private Button usunSprzedawce;
    
    //Klient TAB
    @FXML private TextField imieKlienta;
    @FXML private TextField nazwiskoKlienta;
    @FXML private Button dodajKlienta;
    @FXML private TableView<Klient> tabelaKlient;
    @FXML private TableColumn tabelaImieKlient;
    @FXML private TableColumn tabelaNazwiskoKlient;
    @FXML private Label errorKlient;
    @FXML private Button usunKlienta;
    
    //Sprzedaz TAB
    @FXML private Button dodajSprzedaz;
    
    

    @FXML
    void dodajArtykul(ActionEvent event) {
        if (nazwaArtykulu.getLength() == 0 || cenaArtykulu.getLength() == 0) {
            errorArtykul.setText("Złe dane wejściowe");
            errorArtykul.setAlignment(Pos.CENTER);
        }else{
            connection = new ConnectionDB();
            Artykul artykul = new Artykul(nazwaArtykulu.getText(),Integer.parseInt(cenaArtykulu.getText()));
            connection.addArtykul(artykul);
            connection.closeConnectionWithTransaction();
            updateTableArtykul();
        }
    }
    
    @FXML
    void dodajSprzedawce(ActionEvent event){
        if (imieSprzedawcy.getLength() == 0 || nazwiskoSprzedawcy.getLength() == 0  || pensjaSprzedawcy.getLength() == 0) {
            errorSprzedawca.setText("Złe dane wejściowe");
            errorSprzedawca.setAlignment(Pos.CENTER);
        }else{
            connection = new ConnectionDB();
            Sprzedawca sprzedawca = new Sprzedawca(imieSprzedawcy.getText(), nazwiskoSprzedawcy.getText(),Integer.parseInt(pensjaSprzedawcy.getText()));
            connection.addSprzedawca(sprzedawca);
            connection.closeConnectionWithTransaction();
            updateTableSprzedawca();
        }
    }
    
    @FXML
    void dodajKlienta(ActionEvent event){
        if (imieKlienta.getLength() == 0 || nazwiskoKlienta.getLength() == 0) {
            errorKlient.setText("Złe dane wejściowe");
            errorKlient.setAlignment(Pos.CENTER);
        }else{
            connection = new ConnectionDB();
            Klient klient = new Klient(imieKlienta.getText(), nazwiskoKlienta.getText());
            connection.addKlient(klient);
            connection.closeConnectionWithTransaction();
            updateTableKlient();
        }
        
    }
    
    @FXML
    void dodajSpzedaz(ActionEvent event){
        Sprzedawca sprzedawca = tabelaSprzedawca.getSelectionModel().getSelectedItem();
        Klient klient = tabelaKlient.getSelectionModel().getSelectedItem();
        Artykul artykul = tabelaArtykul.getSelectionModel().getSelectedItem();
        connection = new ConnectionDB();
        connection.makeSell(sprzedawca, klient, artykul);
        connection.closeConnectionWithTransaction();
    }
    
    @FXML
    void usunSprzedawce(){
        Sprzedawca sprzedawca = tabelaSprzedawca.getSelectionModel().getSelectedItem();
        connection = new ConnectionDB();
        connection.deleteSprzedawca(sprzedawca);
        connection.closeConnectionWithTransaction();
        updateTableSprzedawca();
    }
    
    @FXML
    void usunKlienta(){
        Klient klient = tabelaKlient.getSelectionModel().getSelectedItem();
        connection = new ConnectionDB();
        connection.deleteKlient(klient);
        connection.closeConnectionWithTransaction();
        updateTableKlient();       
    }
    
    @FXML 
    void usunArtykul(){
        Artykul artykul = tabelaArtykul.getSelectionModel().getSelectedItem();
        connection = new ConnectionDB();
        connection.deleteArtykul(artykul);
        connection.closeConnectionWithTransaction();
        updateTableArtykul();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTableArtykul();
        updateTableSprzedawca();
        updateTableKlient();
        numericOnlyFields();
    }
    
    public void updateTableArtykul(){
        tabelaNazwaArtykul.setCellValueFactory(new PropertyValueFactory<Artykul, String>("nazwa"));
        tabelaCenaArtykul.setCellValueFactory(new PropertyValueFactory<Artykul, Integer>("cena"));
        tabelaArtykul.getColumns().clear();
        tabelaArtykul.setItems(getArtykul()); 
        tabelaArtykul.getColumns().addAll(tabelaNazwaArtykul,tabelaCenaArtykul);
    }
    
    public void updateTableSprzedawca(){
        tabelaImieSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, String>("imie"));
        tabelaNazwiskoSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, String>("nazwisko"));
        tabelaPensjaSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, Integer>("pensja"));
        tabelaSprzedawca.getColumns().clear();
        tabelaSprzedawca.setItems(getSprzedawca());
        tabelaSprzedawca.getColumns().addAll(tabelaImieSprzedawca,tabelaNazwiskoSprzedawca,tabelaPensjaSprzedawca);
    }
    
    public void updateTableKlient(){
        tabelaImieKlient.setCellValueFactory(new PropertyValueFactory<Artykul, String>("imie"));
        tabelaNazwiskoKlient.setCellValueFactory(new PropertyValueFactory<Artykul, String>("nazwisko"));
        tabelaKlient.getColumns().clear();
        tabelaKlient.setItems(getKlient()); 
        tabelaKlient.getColumns().addAll(tabelaImieKlient,tabelaNazwiskoKlient);
    }
   
     public ObservableList<Artykul> getArtykul() {
        ObservableList<Artykul> artykulList = FXCollections.observableArrayList();
        connection = new ConnectionDB();
        List<Artykul> aList = connection.session.createCriteria(Artykul.class).list();
        for (Artykul artykul : aList) {
            artykulList.add(artykul);
        }
        connection.closeConnectionWithOutTransaction();
        return artykulList;
    }
     
     public ObservableList<Sprzedawca> getSprzedawca() {
         ObservableList<Sprzedawca> sprzedawcaList = FXCollections.observableArrayList();
         connection = new ConnectionDB();
         List<Sprzedawca> sList = connection.session.createCriteria(Sprzedawca.class).list();
         for (Sprzedawca sprzedawca : sList) {
             sprzedawcaList.add(sprzedawca);
         }
         connection.closeConnectionWithOutTransaction();
         return sprzedawcaList;
     }
     
     public ObservableList<Klient> getKlient(){
         ObservableList<Klient> klientList = FXCollections.observableArrayList();
         connection = new ConnectionDB();
         List<Klient> kList = connection.session.createCriteria(Klient.class).list();
         for (Klient klient : kList) {
             klientList.add(klient);
         }
         connection.closeConnectionWithOutTransaction();
         return klientList;
     }
     
     public void numericOnlyFields(){
        cenaArtykulu.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    cenaArtykulu.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        pensjaSprzedawcy.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    pensjaSprzedawcy.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }   
}
