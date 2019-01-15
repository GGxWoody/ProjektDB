package projektdb;

import klasy.Artykul;
import klasy.Klient;
import klasy.Sprzedawca;
import klasy.Sprzedaz;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class ConnectionDB {

    private final String url = "hibernate.cfg.xml";
    private Configuration configuration = null;
    private SessionFactory factory = null;
    Session session = null;
    private Transaction transaction = null;

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

    public void closeConnectionWithTransaction() {
        this.transaction.commit();
        this.session.close();
        this.factory.close();
    }

    public void closeConnectionWithOutTransaction() {
        this.session.close();
        this.factory.close();
    }

    public void addSprzedawca(Sprzedawca sprzedawca) {
        this.session.save(sprzedawca);
    }

    public void addArtykul(Artykul artykul) {
        this.session.save(artykul);
    }

    public void addKlient(Klient klient) {
        this.session.save(klient);
    }

    public void addSprzedaz(Sprzedawca sprzedawca, Klient klient, Artykul artykul) {
        Criteria criteriaSprzedawca = this.session.createCriteria(Sprzedawca.class);
        criteriaSprzedawca.add(Restrictions.eq("imie", sprzedawca.getImie()));
        criteriaSprzedawca.add(Restrictions.and(Restrictions.eq("nazwisko", sprzedawca.getNazwisko())));
        criteriaSprzedawca.add(Restrictions.and(Restrictions.eq("pensja", sprzedawca.getPensja())));
        criteriaSprzedawca.add(Restrictions.and(Restrictions.eq("sprzedawcaId", sprzedawca.getSprzedawcaId())));

        Criteria criteriaKlient = this.session.createCriteria(Klient.class);
        criteriaKlient.add(Restrictions.eq("imie", klient.getImie()));
        criteriaKlient.add(Restrictions.and(Restrictions.eq("nazwisko", klient.getNazwisko())));
        criteriaKlient.add(Restrictions.and(Restrictions.eq("klientID", klient.getKlientID())));

        Criteria criteriaArtykul = this.session.createCriteria(Artykul.class);
        criteriaArtykul.add(Restrictions.eq("nazwa", artykul.getNazwa()));
        criteriaArtykul.add(Restrictions.and(Restrictions.eq("cena", artykul.getCena())));
        criteriaArtykul.add(Restrictions.and(Restrictions.eq("artykulId", artykul.getArtykulId())));

        Sprzedawca sprzedawca1 = (Sprzedawca) criteriaSprzedawca.uniqueResult();
        Klient klient1 = (Klient) criteriaKlient.uniqueResult();
        Artykul artykul1 = (Artykul) criteriaArtykul.uniqueResult();

        if (!sprzedawca1.equals(null)) {
            if (!klient1.equals(null)) {
                if (!artykul1.equals(null)) {
                    Sprzedaz sprzedaz = new Sprzedaz();
                    sprzedaz.setSprzedawca(sprzedawca1);
                    sprzedaz.setKlient(klient1);
                    sprzedaz.setArtykul(artykul1);
                    if (!sprzedaz.equals(null)) {
                        this.session.save(sprzedaz);
                        System.out.println("Dodano Sprzedaz");
                    }
                } else {
                    System.out.println("Bledny artykul");
                }

            } else {
                System.out.println("Bledny klient");
            }

        } else {
            System.out.println("Bledny sprzedawca");
        }
    }

    public void deleteSprzedawca(Sprzedawca sprzedawca) {
        Criteria criteria = this.session.createCriteria(Sprzedawca.class);
        criteria.add(Restrictions.eq("imie", sprzedawca.getImie()));
        criteria.add(Restrictions.and(Restrictions.eq("nazwisko", sprzedawca.getNazwisko())));
        criteria.add(Restrictions.and(Restrictions.eq("pensja", sprzedawca.getPensja())));
        criteria.add(Restrictions.and(Restrictions.eq("sprzedawcaId", sprzedawca.getSprzedawcaId())));

        Sprzedawca sprzedawca1 = (Sprzedawca) criteria.uniqueResult();
        try {
            this.session.delete(sprzedawca1);
            System.out.println("Sprzedawca usuniety");
        } catch (Exception e) {
            System.out.println("Blad usuwania");
            e.printStackTrace();
        }
    }

    public void deleteKlient(Klient klient) {
        Criteria criteria = this.session.createCriteria(Klient.class);
        criteria.add(Restrictions.eq("imie", klient.getImie()));
        criteria.add(Restrictions.and(Restrictions.eq("nazwisko", klient.getNazwisko())));
        criteria.add(Restrictions.and(Restrictions.eq("klientID", klient.getKlientID())));
        Klient klient1 = (Klient) criteria.uniqueResult();
        try {
            this.session.delete(klient1);
            System.out.println("Klient usuniety");
        } catch (Exception e) {
            System.out.println("Blad usuwania");
            e.printStackTrace();
        }

    }

    public void deleteArtykul(Artykul artykul) {
        Criteria criteria = this.session.createCriteria(Artykul.class);
        criteria.add(Restrictions.eq("nazwa", artykul.getNazwa()));
        criteria.add(Restrictions.and(Restrictions.eq("cena", artykul.getCena())));
        criteria.add(Restrictions.and(Restrictions.eq("artykulId", artykul.getArtykulId())));
        Artykul artykul1 = (Artykul) criteria.uniqueResult();
        try {
            this.session.delete(artykul1);
            System.out.println("Artykul usuniety");
        } catch (Exception e) {
            System.out.println("Blad usuwania");
            e.printStackTrace();
        }

    }

    public void deleteSprzedaz(Sprzedaz sprzedaz) {
        Criteria criteria = this.session.createCriteria(Sprzedaz.class);
        criteria.add(Restrictions.eq("sprzedazId", sprzedaz.getSprzedazId()));
        criteria.add(Restrictions.and(Restrictions.eq("sprzedawca", sprzedaz.getSprzedawca())));
        criteria.add(Restrictions.and(Restrictions.eq("klient", sprzedaz.getKlient())));
        criteria.add(Restrictions.and(Restrictions.eq("artykul", sprzedaz.getArtykul())));

        Sprzedaz sprzedaz1 = (Sprzedaz) criteria.uniqueResult();
        try {
            this.session.delete(sprzedaz1);
            System.out.println("Sprzedaz usunieta");
        } catch (Exception e) {
            System.out.println("Blad usuwania");
            e.printStackTrace();
        }
    }
    
    public void updateSprzedawca(Sprzedawca sprzedawca) {
        try {
            this.session.update(sprzedawca);
            System.out.println("Sprzedawca edytowany");
        } catch (Exception e) {
            System.out.println("Blad edycji");
            e.printStackTrace();
        }
    }
    
    public void updateKlient(Klient klient){
        try {
            this.session.update(klient);
            System.out.println("Klient edytowany");
        } catch (Exception e) {
            System.out.println("Blad edycji");
            e.printStackTrace();
        }
    }
        
    public void updateArtykul(Artykul artykul){
        try {
            this.session.update(artykul);
            System.out.println("Artykul edytowany");
        } catch (Exception e) {
            System.out.println("Blad edycji");
            e.printStackTrace();
        }
    }
    
    public void updateSprzedaz(Sprzedaz sprzedaz){
        try {
            this.session.update(sprzedaz);
            System.out.println("Sprzedaz edytowana");
        } catch (Exception e) {
            System.out.println("Blad edycji");
            e.printStackTrace();
        }
    }
}
