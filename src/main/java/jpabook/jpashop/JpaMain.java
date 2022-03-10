package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("최윤호");
            em.persist(member);

            Order order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setStatus(OrderStatus.ORDER);
            order.setMember(member);
            em.persist(order);

            em.flush();
            em.clear();

            System.out.println("===================================");

            String jpql= "select o From Order o where o.member.id = :memberId";

            Order findOrder = em.createQuery(jpql, Order.class).setParameter("memberId", 1L).getSingleResult();

            System.out.println("===================================");

            System.out.println("findOrder.getOrderDate() = " + findOrder.getOrderDate());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
