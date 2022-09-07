//package ft.school21.fix_utils.Database;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class ImplementTrans implements Runnable{
//
//    private  SessionFactory factory;
//    private  ImplementTrans implementTrans;
//    public Transaction transaction;
//
//    public  ImplementTrans()
//    {
////        try {
////            factory = configureSessionFactory();
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//        factory = new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(ImplementTrans.class)
//                .buildSessionFactory();
//        Session session = factory.getCurrentSession();
//    }
//
//    private SessionFactory configureSessionFactory() throws IOException {
//        Configuration configuration = new Configuration();
//        InputStream inputStream = this.getClass().getClassLoader().
//                getResourceAsStream("hibernate.cfg.xml");
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.load(inputStream);
//        configuration.setProperties(hibernateProperties);
//
//        // configuration.addAnnotatedClass(Foo.class);
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
//                applySettings(configuration.getProperties()).build();
//        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//        return sessionFactory;
//    }
//
////    public static ImplementTrans getImplementTrans()
////    {
////        if (implementTrans == null)
////        {
////            implementTrans = new ImplementTrans();
////            factory = new Configuration()
////                    .configure("hibernate.cfg.xml")
////                    .addAnnotatedClass(ImplementTrans.class)
////                    .buildSessionFactory();
////        }
////        return implementTrans;
////    }
//
//
////    public void setFactory(SessionFactory factory) {
////        this.factory = factory;
////    }
//
////    public SessionFactory getFactory()
////    {
////        return factory;
////    }
//
//    public void AddTransaction(Transaction transaction)
//    {
//        Session session = factory.getCurrentSession();
//        session.beginTransaction();
//        session.save(transaction);
//        session.getTransaction().commit();
//    }
//
//    public Transaction getTransaction() {
//        return transaction;
//    }
//
//    public void setTransaction(Transaction transaction) {
//        this.transaction = transaction;
//    }
//
//    @Override
//    public void run() {
//        AddTransaction(transaction);
////        Session session = factory.getCurrentSession();
////        session.beginTransaction();
////        session.save(transaction);
////        session.getTransaction().commit();
//    }
//}
