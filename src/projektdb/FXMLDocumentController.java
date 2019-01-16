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
import klasy.Sprzedaz;
import org.hibernate.criterion.Order;

public class FXMLDocumentController implements Initializable {

    private ConnectionDB connection;
    //Artykul TAB
    @FXML
    private TextField nazwaArtykulu;
    @FXML
    private TextField cenaArtykulu;
    @FXML
    private Button dodajArtykul;
    @FXML
    private TableView<Artykul> tabelaArtykul;
    @FXML
    private TableColumn tabelaNazwaArtykul;
    @FXML
    private TableColumn tabelaCenaArtykul;
    @FXML
    private Label errorArtykul;
    @FXML
    private Button usunArtykul;
    @FXML
    private Button editArtykul;

    //Sprzedawca TAB
    @FXML
    private TextField imieSprzedawcy;
    @FXML
    private TextField nazwiskoSprzedawcy;
    @FXML
    private TextField pensjaSprzedawcy;
    @FXML
    private Button dodajSprzedawce;
    @FXML
    private TableView<Sprzedawca> tabelaSprzedawca;
    @FXML
    private TableColumn tabelaImieSprzedawca;
    @FXML
    private TableColumn tabelaNazwiskoSprzedawca;
    @FXML
    private TableColumn tabelaPensjaSprzedawca;
    @FXML
    private Label errorSprzedawca;
    @FXML
    private Button usunSprzedawce;
    @FXML
    private Button editSprzedawca;

    //Klient TAB
    @FXML
    private TextField imieKlienta;
    @FXML
    private TextField nazwiskoKlienta;
    @FXML
    private Button dodajKlienta;
    @FXML
    private TableView<Klient> tabelaKlient;
    @FXML
    private TableColumn tabelaImieKlient;
    @FXML
    private TableColumn tabelaNazwiskoKlient;
    @FXML
    private Label errorKlient;
    @FXML
    private Button usunKlienta;
    @FXML
    private Button editKlient;

    //Sprzedaz TAB
    @FXML
    private Button dodajSprzedaz;
    @FXML
    private TableView<Sprzedaz> tabelaSprzedaz;
    @FXML
    private TableColumn tabelaSprzedawcaSprzedaz;
    @FXML
    private TableColumn tabelaKlientSprzedaz;
    @FXML
    private TableColumn tabelaArtykulSprzedaz;
    @FXML
    private Button usunSprzedaz;
    @FXML
    private Label errorSprzedaz;
    @FXML
    private Button editSprzedaz;
    @FXML
    private ChoiceBox<Sprzedawca> choiceSprzedawca;
    @FXML
    private ChoiceBox<Klient> choiceKlient;
    @FXML
    private ChoiceBox<Artykul> choiceArtykul;

    @FXML
    void dodajArtykul(ActionEvent event) {
        if (nazwaArtykulu.getLength() == 0 || cenaArtykulu.getLength() == 0) {
            errorArtykul.setText("Złe dane wejściowe");
            errorArtykul.setAlignment(Pos.CENTER);
        } else {
            connection = new ConnectionDB();
            Artykul artykul = new Artykul(nazwaArtykulu.getText(), Integer.parseInt(cenaArtykulu.getText()));
            connection.addArtykul(artykul);
            connection.closeConnectionWithTransaction();
            updateTableArtykul();
            updateChoiceArtykul();
            updateTableSprzedaz();
            nazwaArtykulu.setText("");
            cenaArtykulu.setText("");
        }
    }

    @FXML
    void dodajSprzedawce(ActionEvent event) {
        if (imieSprzedawcy.getLength() == 0 || nazwiskoSprzedawcy.getLength() == 0 || pensjaSprzedawcy.getLength() == 0) {
            errorSprzedawca.setText("Złe dane wejściowe");
            errorSprzedawca.setAlignment(Pos.CENTER);
        } else {
            connection = new ConnectionDB();
            Sprzedawca sprzedawca = new Sprzedawca(imieSprzedawcy.getText(), nazwiskoSprzedawcy.getText(), Integer.parseInt(pensjaSprzedawcy.getText()));
            connection.addSprzedawca(sprzedawca);
            connection.closeConnectionWithTransaction();
            updateTableSprzedawca();
            updateChoiceSprzedawca();
            imieSprzedawcy.setText("");
            nazwiskoSprzedawcy.setText("");
            pensjaSprzedawcy.setText("");
        }
    }

    @FXML
    void dodajKlienta(ActionEvent event) {
        if (imieKlienta.getLength() == 0 || nazwiskoKlienta.getLength() == 0) {
            errorKlient.setText("Złe dane wejściowe");
            errorKlient.setAlignment(Pos.CENTER);
        } else {
            connection = new ConnectionDB();
            Klient klient = new Klient(imieKlienta.getText(), nazwiskoKlienta.getText());
            connection.addKlient(klient);
            connection.closeConnectionWithTransaction();
            updateTableKlient();
            updateChoiceKlient();
            imieKlienta.setText("");
            nazwiskoKlienta.setText("");
        }

    }

    @FXML
    void dodajSpzedaz(ActionEvent event) {
        if (choiceSprzedawca.getSelectionModel().getSelectedItem() != null) {
            if (choiceKlient.getSelectionModel().getSelectedItem() != null) {
                if (choiceArtykul.getSelectionModel().getSelectedItem() != null) {
                    Sprzedawca sprzedawca = choiceSprzedawca.getSelectionModel().getSelectedItem();
                    Klient klient = choiceKlient.getSelectionModel().getSelectedItem();
                    Artykul artykul = choiceArtykul.getSelectionModel().getSelectedItem();
                    connection = new ConnectionDB();
                    connection.addSprzedaz(sprzedawca, klient, artykul);
                    connection.closeConnectionWithTransaction();
                    updateTableSprzedaz();
                    choiceArtykul.setValue(null);
                    choiceKlient.setValue(null);
                    choiceSprzedawca.setValue(null);
                } else {
                    errorSprzedaz.setText("Wybierz artykul");
                }
            } else {
                errorSprzedaz.setText("Wybierz klienta");
            }
        } else {
            errorSprzedaz.setText("Wybierz sprzedawce");
        }
    }

    @FXML
    void editSprzedawca() {
        if (tabelaSprzedawca.getSelectionModel().getSelectedItem() != null) {
            Sprzedawca sprzedawca = tabelaSprzedawca.getSelectionModel().getSelectedItem();
            if (imieSprzedawcy.getText().isEmpty() == false) {
                sprzedawca.setImie(imieSprzedawcy.getText());
            }
            if (nazwiskoSprzedawcy.getText().isEmpty() == false) {
                sprzedawca.setNazwisko(nazwiskoSprzedawcy.getText());
            }
            if (pensjaSprzedawcy.getText().isEmpty() == false) {
                sprzedawca.setPensja(Integer.parseInt(pensjaSprzedawcy.getText()));
            }
            connection = new ConnectionDB();
            connection.updateSprzedawca(sprzedawca);
            connection.closeConnectionWithTransaction();
            updateTableSprzedawca();
            updateChoiceSprzedawca();
            updateTableSprzedaz();
            imieSprzedawcy.setText("");
            nazwiskoSprzedawcy.setText("");
            pensjaSprzedawcy.setText("");
        } else {
            errorSprzedawca.setText("Wybiez rekord do edycji");
        }
    }

    @FXML
    void editKlient() {
        if (tabelaKlient.getSelectionModel().getSelectedItem() != null) {
            Klient klient = tabelaKlient.getSelectionModel().getSelectedItem();
            if (imieKlienta.getText().isEmpty() == false) {
                klient.setImie(imieKlienta.getText());
            }
            if (nazwiskoKlienta.getText().isEmpty() == false) {
                klient.setNazwisko(nazwiskoKlienta.getText());
            }
            connection = new ConnectionDB();
            connection.updateKlient(klient);
            connection.closeConnectionWithTransaction();
            updateTableKlient();
            updateChoiceKlient();
            updateTableSprzedaz();
            imieKlienta.setText("");
            nazwiskoKlienta.setText("");
        } else {
            errorKlient.setText("Wybiez rekord do edycji");
        }
    }

    @FXML
    void editArtykul() {
        if (tabelaArtykul.getSelectionModel().getSelectedItem() != null) {
            Artykul artykul = tabelaArtykul.getSelectionModel().getSelectedItem();
            if (nazwaArtykulu.getText().isEmpty() == false) {
                artykul.setNazwa(nazwaArtykulu.getText());
            }
            if (cenaArtykulu.getText().isEmpty() == false) {
                artykul.setCena(Integer.parseInt(cenaArtykulu.getText()));
            }
            connection = new ConnectionDB();
            connection.updateArtykul(artykul);
            connection.closeConnectionWithTransaction();
            updateTableArtykul();
            updateChoiceArtykul();
            updateTableSprzedaz();
            nazwaArtykulu.setText("");
            cenaArtykulu.setText("");
        } else {
            errorArtykul.setText("Wybiez rekord do edycji");
        }
    }

    @FXML
    void editSprzedaz() {
        if (tabelaSprzedaz.getSelectionModel().getSelectedItem() != null) {
            Sprzedaz sprzedaz = tabelaSprzedaz.getSelectionModel().getSelectedItem();
            if (choiceArtykul.getValue() != null) {
                sprzedaz.setArtykul(choiceArtykul.getValue());
            }
            if (choiceSprzedawca.getValue() != null) {
                sprzedaz.setSprzedawca(choiceSprzedawca.getValue());
            }
            if (choiceKlient.getValue() != null) {
                sprzedaz.setKlient(choiceKlient.getValue());
            }
            connection = new ConnectionDB();
            connection.updateSprzedaz(sprzedaz);
            connection.closeConnectionWithTransaction();
            updateTableSprzedaz();
            choiceArtykul.setValue(null);
            choiceKlient.setValue(null);
            choiceSprzedawca.setValue(null);
        } else {
            errorSprzedaz.setText("Wybiez rekord do edycji");
        }
    }

    @FXML
    void usunSprzedawce() {
        if (tabelaSprzedawca.getSelectionModel().getSelectedItem() != null) {
            Sprzedawca sprzedawca = tabelaSprzedawca.getSelectionModel().getSelectedItem();
            connection = new ConnectionDB();
            connection.deleteSprzedawca(sprzedawca);
            connection.closeConnectionWithTransaction();
            updateTableSprzedawca();
            updateChoiceSprzedawca();
            updateTableSprzedaz();
            imieSprzedawcy.setText("");
            nazwiskoSprzedawcy.setText("");
            pensjaSprzedawcy.setText("");
        } else {
            errorSprzedawca.setText("Wybiez rekord do usunięcia");
        }
    }

    @FXML
    void usunKlienta() {
        if (tabelaKlient.getSelectionModel().getSelectedItem() != null) {
            Klient klient = tabelaKlient.getSelectionModel().getSelectedItem();
            connection = new ConnectionDB();
            connection.deleteKlient(klient);
            connection.closeConnectionWithTransaction();
            updateTableKlient();
            updateChoiceKlient();
            updateTableSprzedaz();
            imieKlienta.setText("");
            nazwiskoKlienta.setText("");
        } else {
            errorKlient.setText("Wybiez rekord do usunięcia");
        }
    }

    @FXML
    void usunArtykul() {
        if (tabelaArtykul.getSelectionModel().getSelectedItem() != null) {
            Artykul artykul = tabelaArtykul.getSelectionModel().getSelectedItem();
            connection = new ConnectionDB();
            connection.deleteArtykul(artykul);
            connection.closeConnectionWithTransaction();
            updateTableArtykul();
            updateChoiceArtykul();
            updateTableSprzedaz();
            nazwaArtykulu.setText("");
            cenaArtykulu.setText("");
        } else {
            errorArtykul.setText("Wybiez rekord do usunięcia");
        }
    }

    @FXML
    void usunSprzedaz() {
        if (tabelaSprzedaz.getSelectionModel().getSelectedItem() != null) {
            Sprzedaz sprzedaz = tabelaSprzedaz.getSelectionModel().getSelectedItem();
            connection = new ConnectionDB();
            connection.deleteSprzedaz(sprzedaz);
            connection.closeConnectionWithTransaction();
            updateTableSprzedaz();
            choiceArtykul.setValue(null);
            choiceKlient.setValue(null);
            choiceSprzedawca.setValue(null);
        } else {
            errorSprzedaz.setText("Wybiez rekord do usunięcia");
        }
    }

    @FXML
    void szukajSpredawca() {
        long pensja;
        connection = new ConnectionDB();
        String imie = imieSprzedawcy.getText();
        String nazwisko = nazwiskoSprzedawcy.getText();
        if (pensjaSprzedawcy.getText().length() != 0) {
            pensja = Integer.parseInt(pensjaSprzedawcy.getText());
        } else {
            pensja = 0;
        }

        ObservableList<Sprzedawca> sprzedawca = FXCollections.observableArrayList();
        List<Sprzedawca> sprzedawcas = connection.searchSprzedawca(imie, nazwisko, pensja);
        for (Sprzedawca sprzedawca1 : sprzedawcas) {
            sprzedawca.add(sprzedawca1);
        }
        tabelaImieSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, String>("imie"));
        tabelaNazwiskoSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, String>("nazwisko"));
        tabelaPensjaSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, Integer>("pensja"));
        tabelaSprzedawca.getColumns().clear();
        tabelaSprzedawca.setItems(sprzedawca);
        tabelaSprzedawca.getColumns().addAll(tabelaImieSprzedawca, tabelaNazwiskoSprzedawca, tabelaPensjaSprzedawca);
        connection.closeConnectionWithOutTransaction();
        imieSprzedawcy.setText("");
        nazwiskoSprzedawcy.setText("");
        pensjaSprzedawcy.setText("");
    }

    @FXML
    void szukajKlient() {
        connection = new ConnectionDB();
        String imie = imieKlienta.getText();
        String nazwisko = nazwiskoKlienta.getText();
        ObservableList<Klient> klient = FXCollections.observableArrayList();
        List<Klient> klients = connection.searchKlient(imie, nazwisko);
        for (Klient klient1 : klients) {
            klient.add(klient1);
        }
        tabelaImieKlient.setCellValueFactory(new PropertyValueFactory<Artykul, String>("imie"));
        tabelaNazwiskoKlient.setCellValueFactory(new PropertyValueFactory<Artykul, String>("nazwisko"));
        tabelaKlient.getColumns().clear();
        tabelaKlient.setItems(klient);
        tabelaKlient.getColumns().addAll(tabelaImieKlient, tabelaNazwiskoKlient);
        connection.closeConnectionWithOutTransaction();
        imieKlienta.setText("");
        nazwiskoKlienta.setText("");
    }

    @FXML
    void szukajArtykul() {
        connection = new ConnectionDB();
        int cena;
        String nazwa = nazwaArtykulu.getText();
        if (cenaArtykulu.getLength() != 0) {
            cena = Integer.parseInt(cenaArtykulu.getText());
        } else {
            cena = 0;
        }
        ObservableList<Artykul> artykul = FXCollections.observableArrayList();
        List<Artykul> artykuls = connection.searchArtykul(nazwa, cena);
        for (Artykul artykul1 : artykuls) {
            artykul.add(artykul1);
        }
        tabelaNazwaArtykul.setCellValueFactory(new PropertyValueFactory<Artykul, String>("nazwa"));
        tabelaCenaArtykul.setCellValueFactory(new PropertyValueFactory<Artykul, Integer>("cena"));
        tabelaArtykul.getColumns().clear();
        tabelaArtykul.setItems(artykul);
        tabelaArtykul.getColumns().addAll(tabelaNazwaArtykul, tabelaCenaArtykul);
        connection.closeConnectionWithOutTransaction();
        nazwaArtykulu.setText("");
        cenaArtykulu.setText("");
    }

    @FXML
    void szukajSprzedaz() {
        connection = new ConnectionDB();
        Sprzedawca sprzedawca = choiceSprzedawca.getValue();
        Klient klient = choiceKlient.getValue();
        Artykul artykul = choiceArtykul.getValue();
        ObservableList<Sprzedaz> sprzedaz = FXCollections.observableArrayList();
        List<Sprzedaz> sprzedazs = connection.searchSprzedaz(sprzedawca, klient, artykul);
        for (Sprzedaz sprzedaz1 : sprzedazs) {
            sprzedaz.add(sprzedaz1);
        }
        tabelaSprzedawcaSprzedaz.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("sprzedawcaa"));
        tabelaKlientSprzedaz.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("klientt"));
        tabelaArtykulSprzedaz.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("artykull"));
        tabelaSprzedaz.getColumns().clear();
        tabelaSprzedaz.setItems(sprzedaz);
        tabelaSprzedaz.getColumns().addAll(tabelaSprzedawcaSprzedaz, tabelaKlientSprzedaz, tabelaArtykulSprzedaz);
        connection.closeConnectionWithOutTransaction();
        choiceArtykul.setValue(null);
        choiceKlient.setValue(null);
        choiceSprzedawca.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTableArtykul();
        updateTableSprzedawca();
        updateTableKlient();
        updateTableSprzedaz();
        numericOnlyFields();
        updateChoiceSprzedawca();
        updateChoiceKlient();
        updateTableArtykul();
        updateChoiceArtykul();
        restirictLength();
    }

    public void updateTableArtykul() {
        tabelaNazwaArtykul.setCellValueFactory(new PropertyValueFactory<Artykul, String>("nazwa"));
        tabelaCenaArtykul.setCellValueFactory(new PropertyValueFactory<Artykul, Integer>("cena"));
        tabelaArtykul.getColumns().clear();
        tabelaArtykul.setItems(getArtykul());
        tabelaArtykul.getColumns().addAll(tabelaNazwaArtykul, tabelaCenaArtykul);
    }

    public void updateChoiceArtykul() {
        ObservableList<Artykul> artykul = FXCollections.observableArrayList();
        artykul.add(null);
        artykul.addAll(getArtykul());
        choiceArtykul.setItems(artykul);
    }

    public void updateTableSprzedawca() {
        tabelaImieSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, String>("imie"));
        tabelaNazwiskoSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, String>("nazwisko"));
        tabelaPensjaSprzedawca.setCellValueFactory(new PropertyValueFactory<Sprzedawca, Integer>("pensja"));
        tabelaSprzedawca.getColumns().clear();
        tabelaSprzedawca.setItems(getSprzedawca());
        tabelaSprzedawca.getColumns().addAll(tabelaImieSprzedawca, tabelaNazwiskoSprzedawca, tabelaPensjaSprzedawca);
    }

    public void updateChoiceSprzedawca() {
        ObservableList<Sprzedawca> sprzedawca = FXCollections.observableArrayList();
        sprzedawca.add(null);
        sprzedawca.addAll(getSprzedawca());
        choiceSprzedawca.setItems(sprzedawca);
    }

    public void updateTableSprzedaz() {
        tabelaSprzedawcaSprzedaz.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("sprzedawcaa"));
        tabelaKlientSprzedaz.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("klientt"));
        tabelaArtykulSprzedaz.setCellValueFactory(new PropertyValueFactory<Sprzedaz, String>("artykull"));
        tabelaSprzedaz.getColumns().clear();
        tabelaSprzedaz.setItems(getSprzedaz());
        tabelaSprzedaz.getColumns().addAll(tabelaSprzedawcaSprzedaz, tabelaKlientSprzedaz, tabelaArtykulSprzedaz);
    }

    public void updateTableKlient() {
        tabelaImieKlient.setCellValueFactory(new PropertyValueFactory<Artykul, String>("imie"));
        tabelaNazwiskoKlient.setCellValueFactory(new PropertyValueFactory<Artykul, String>("nazwisko"));
        tabelaKlient.getColumns().clear();
        tabelaKlient.setItems(getKlient());
        tabelaKlient.getColumns().addAll(tabelaImieKlient, tabelaNazwiskoKlient);
    }

    public void updateChoiceKlient() {
        ObservableList<Klient> klient = FXCollections.observableArrayList();
        klient.add(null);
        klient.addAll(getKlient());
        choiceKlient.setItems(klient);
    }

    public ObservableList<Artykul> getArtykul() {
        ObservableList<Artykul> artykulList = FXCollections.observableArrayList();
        connection = new ConnectionDB();       
        List<Artykul> aList = connection.session.createCriteria(Artykul.class).addOrder(Order.asc("nazwa")).list();
        for (Artykul artykul : aList) {
            artykulList.add(artykul);
        }
        connection.closeConnectionWithOutTransaction();
        return artykulList;
    }

    public ObservableList<Sprzedawca> getSprzedawca() {
        ObservableList<Sprzedawca> sprzedawcaList = FXCollections.observableArrayList();
        connection = new ConnectionDB();
        List<Sprzedawca> sList = connection.session.createCriteria(Sprzedawca.class).addOrder(Order.asc("nazwisko")).list();
        for (Sprzedawca sprzedawca : sList) {
            sprzedawcaList.add(sprzedawca);
        }
        connection.closeConnectionWithOutTransaction();
        return sprzedawcaList;
    }

    public ObservableList<Sprzedaz> getSprzedaz() {
        ObservableList<Sprzedaz> sprzedazList = FXCollections.observableArrayList();
        connection = new ConnectionDB();
        List<Sprzedaz> sList = connection.session.createCriteria(Sprzedaz.class).list();
        for (Sprzedaz sprzedaz : sList) {
            sprzedazList.add(sprzedaz);
        }
        connection.closeConnectionWithOutTransaction();
        return sprzedazList;
    }

    public ObservableList<Klient> getKlient() {
        ObservableList<Klient> klientList = FXCollections.observableArrayList();
        connection = new ConnectionDB();
        List<Klient> kList = connection.session.createCriteria(Klient.class).addOrder(Order.asc("nazwisko")).list();
        for (Klient klient : kList) {
            klientList.add(klient);
        }
        connection.closeConnectionWithOutTransaction();
        return klientList;
    }

    public void numericOnlyFields() {
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
    
    public void restirictLength(){
        imieSprzedawcy.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (imieSprzedawcy.getText().length() >= 16) {
                        imieSprzedawcy.setText(imieSprzedawcy.getText().substring(0, 16));
                    }
                }
            }
        });
        nazwiskoSprzedawcy.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (nazwiskoSprzedawcy.getText().length() >= 16) {
                        nazwiskoSprzedawcy.setText(nazwiskoSprzedawcy.getText().substring(0, 16));
                    }
                }
            }
        });
        pensjaSprzedawcy.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (pensjaSprzedawcy.getText().length() >= 9) {
                        pensjaSprzedawcy.setText(pensjaSprzedawcy.getText().substring(0, 9));
                    }
                }
            }
        });
        imieKlienta.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (imieKlienta.getText().length() >= 16) {
                        imieKlienta.setText(imieKlienta.getText().substring(0, 16));
                    }
                }
            }
        });
        nazwiskoKlienta.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (nazwiskoKlienta.getText().length() >= 16) {
                        nazwiskoKlienta.setText(nazwiskoKlienta.getText().substring(0, 16));
                    }
                }
            }
        });
        nazwaArtykulu.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (nazwaArtykulu.getText().length() >= 16) {
                        nazwaArtykulu.setText(nazwaArtykulu.getText().substring(0, 16));
                    }
                }
            }
        });
        cenaArtykulu.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (cenaArtykulu.getText().length() >= 9) {
                        cenaArtykulu.setText(cenaArtykulu.getText().substring(0, 9));
                    }
                }
            }
        });
    }
}