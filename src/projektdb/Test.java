package projektdb;


import klasy.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Test {
    public static void main(String[] args) {  
	Sprzedawca sprzedawca = new Sprzedawca("ąłżóńćś", "Drzewo", 1000);
        
        Configuration configuration = 
     new Configuration().configure("hibernate.cfg.xml");
  
        configuration.addAnnotatedClass(Sprzedawca.class).addAnnotatedClass(Artykul.class).addAnnotatedClass(Sprzedaz.class).addAnnotatedClass(Klient.class);
   ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
     applySettings(configuration.getProperties()).build();
  
   
   SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        
   Session session = factory.openSession();
        
   Transaction transaction = session.beginTransaction();
        
   session.save(sprzedawca);
        
   transaction.commit();

   session.close();        
   factory.close();

   StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
}
