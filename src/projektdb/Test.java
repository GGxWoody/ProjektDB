package projektdb;


import klasy.*;

public class Test {
    public static void main(String[] args) {  
	Sprzedawca sprzedawca = new Sprzedawca("Marcin", "Kolano", 2350);
        ConnectionDB connection = new ConnectionDB();
        connection.addSprzedawca(sprzedawca);
        connection.closeConnectionWithTransaction();
    }
}
