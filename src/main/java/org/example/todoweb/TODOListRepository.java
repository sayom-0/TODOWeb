package org.example.todoweb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class TODOListRepository
{
    private SessionFactory sessionFactory;
    private Dictionary<Integer, Integer> itemIdDictionary;

    public TODOListRepository()// Used to translate front end ids expected to be starting from 1 and incremented by 1 to database ids which arent lowered when a item is deleted
    {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try
        {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e)
        {
            StandardServiceRegistryBuilder.destroy( registry );
            System.out.println("Error in building SessionFactory");
        }

        itemIdDictionary = new Hashtable<Integer, Integer>();
    }

    protected void tearDown() throws Exception
    {
        if ( sessionFactory != null )
        {
            sessionFactory.close();
        }
    }

    private void updateIdTranslations()
    {
        List<TODOItem> dbItems = getItems();

        //Make a front end numerical list starting from 1 and incremented by 1
        for(int i = 0; i < dbItems.size(); i++)
        {
            itemIdDictionary.put(i, dbItems.get(i).GetId());
        }
    }

    public void addItem(String description)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new TODOItem(description));
        session.getTransaction().commit();
        session.close();

        //Update the front end id list
        updateIdTranslations();
    }

    public void deleteItem(int index)
    {
        //Update front end id list
        updateIdTranslations();

        //Translate front end line item to backend id
        int translatedIndex = itemIdDictionary.get(index);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(new TODOItem(translatedIndex));
        session.getTransaction().commit();
        session.close();
    }

    public List<TODOItem> getItems()
    {
        List<TODOItem> dbItems;

        //Fetch db items
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        dbItems = session.createQuery("select i from TODOItem i", TODOItem.class).list();

        return dbItems;
    }

    public void clearList()
    {
        List<TODOItem> items = getItems();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for(TODOItem item : items)
        {
            session.remove(item);
        }
        session.getTransaction().commit();
        session.close();
    }
}