package com.my.parking.myparking.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author siddharthdwivedi
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Integer id;
    private String productName;
    private Integer price;
    private String description;
    //    @OneToMany(mappedBy = "product")
//    private Set<Order> orderSet;
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
    private Date createdDate = new Date();

    @Override
    public String toString() {
        return "ID: " + this.id + "| ProductName: " + this.productName + "| Price Rs.: " + this.price + "| Description: " + description;
    }

}
