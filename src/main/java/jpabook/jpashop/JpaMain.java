package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("member1");
            em.persist(member);

            Order order1 = new Order();
            order1.setOrderDate(LocalDateTime.now());
            order1.setMember(member);
            em.persist(order1);

            Order order2 = new Order();
            order2.setOrderDate(LocalDateTime.now());
            order2.setMember(member);

            em.persist(order2);

            em.flush();
            em.clear();

            System.out.println("===================================");

            List<Order> resultList = em.createQuery("select o from Order o join fetch o.member", Order.class)
                    .getResultList();

            for (Order newOrder : resultList) {
                System.out.println("member1.getName() = " + newOrder.getOrderDate());
            }
            System.out.println("===================================");


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
