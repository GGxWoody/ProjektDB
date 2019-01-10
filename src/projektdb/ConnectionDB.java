package projektdb;


import klasy.Artykul;
import klasy.Klient;
import klasy.Sprzedawca;
import klasy.Sprzedaz;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ConnectionDB{

    private final String url = "hibernate.cfg.xml";
    private Configuration configuration = null;
    private SessionFactory factory= null;
    private Session session = null;
    private Transaction transaction=null;



    public ConnectionDB() {
        this.configuration = new Configuration().configure(url);

        this.configuration.addAnnotatedClass(Sprzedawca.class);
        this.configuration.addAnnotatedClass(Klient.class);
        this.configuration.addAnnotatedClass(Sprzedaz.class);
        this.configuration.addAnnotatedClass(Artykul.class);
      

        this.factory = this.configuration.buildSessionFactory();
        this.session = this.factory.openSession();
        this.transaction = this.session.beginTransaction();
    }

    public void closeConnection(){
        this.transaction.commit();
        this.session.close();
        this.factory.close();
    }
    
    public void addSprzedawca(Sprzedawca sprzedawca){
        this.session.save(sprzedawca);
    }
}