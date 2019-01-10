package projektdb;


import klasy.*;

public class Test {
    public static void main(String[] args) {  
	Sprzedawca sprzedawca = new Sprzedawca("ąłżóńćś", "Drzewo", 1000);
        ConnectionDB connection = new ConnectionDB();
        connection.addSprzedawca(sprzedawca);
        connection.closeConnection();
    }
}
