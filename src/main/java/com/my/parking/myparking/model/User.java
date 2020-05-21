package com.my.parking.myparking.model;

import com.my.parking.myparking.emumconstants.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author siddharthdwivedi
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private Integer id;
    private String name;
    private String userName;
    private String password;
    private String address;
    private Date dateOfBirth;
    private Role role;
    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Product> productSet = new HashSet<>();
    private Date createdDate = new Date();

    public void addProduct(Product product) {
        if (!productSet.contains(product)) {
            productSet.add(product);
            product.setUser(this);
        }
    }

    public void removeProduct(Product product) {
        productSet.remove(product);
        product.setUser(null);
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", UserName: " + this.userName + ", Role: " + this.role
                + ", Address: " + this.address + ", Dob: " + this.dateOfBirth;
    }
}
