package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;

    private String street;

    private String zipcode;

    private DeliveryStatus status;

    @OneToOne(mappedBy = "delivery")
    private Order order;

}
