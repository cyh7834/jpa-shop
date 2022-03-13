package jpabook.jpashop;

import jpabook.jpashop.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setName("member " + i);
                em.persist(member);
            }

            em.flush();
            em.clear();

            System.out.println("===================================");

            List<Member> resultList = em.createQuery("select m from Member as m order by m.name", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(30)
                    .getResultList();

            System.out.println("===================================");


            for (Member member : resultList) {
                System.out.println("member.getName() = " + member.getName());
            }

            List<String> query1 = em.createQuery("select function('group_concat', m.name) from Member m", String.class)
                    .getResultList();

            for (String s : query1) {
                System.out.println("s = " + s);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
