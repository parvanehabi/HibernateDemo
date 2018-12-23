package com.fanap.hibernate.data;

import com.fanap.hibernate.data.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Query;
import java.util.List;


public class HibernateUtils {

    public static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    static {

        List<Object> admins = select("Admin",null,null,null);
        if(admins.size()==0) {
            Admin admin = new Admin();
            admin.setFirstName("Fatemeh");
            admin.setEmail("akbari@gmail.com");
            admin.setLastName("akbari");
            admin.setUsername("admin");
            admin.setPassword("admin");
            save(admin);
        }
    }

    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
    }

    public static List<Object> select(String entityName, String whereClauseColumn, String whereClauseOperator, Object parameterObject){

        StringBuilder stringBuilder = new StringBuilder("from ");
        stringBuilder.append(entityName);

        if(whereClauseColumn != null) {
            stringBuilder.append(" where ");
            stringBuilder.append(whereClauseColumn);
            stringBuilder.append(whereClauseOperator + ":");
            stringBuilder.append("obj");
        }

        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery(stringBuilder.toString());
        if(whereClauseColumn != null){
            query.setParameter("obj",parameterObject);
        }

        List<Object> list = query.getResultList();

        tx.commit();
        session.close();

        return list;
    }


    public static void save(Object o){
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
    }

    public static void delete(Object o){
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(o);
        tx.commit();
        session.close();
    }

    public static void update(Object o){
        Session session = SESSION_FACTORY.openSession();
        Transaction tx = session.beginTransaction();
        session.update(o);
        tx.commit();
        session.close();
    }
}
