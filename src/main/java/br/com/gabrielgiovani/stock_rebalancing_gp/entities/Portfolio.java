package br.com.gabrielgiovani.stock_rebalancing_gp.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "portfolio", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "user_id"})
})
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, columnDefinition = "DECIMAL(5,2)")
    private Double investmentPercentage;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "portfolio")
    private final Set<Category> categories;

    public Portfolio() {
        this.categories = new HashSet<>();
    }

    public Portfolio(String name, String description, Double investmentPercentage, User user) {
        this.name = name;
        this.description = description;
        this.investmentPercentage = investmentPercentage;
        this.categories = new HashSet<>();
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getInvestmentPercentage() {
        return investmentPercentage;
    }

    public void setInvestmentPercentage(Double investmentPercentage) {
        this.investmentPercentage = investmentPercentage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }
}