package pl.edu.agh.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "costs")
    private Double costs;

    @Column(name = "work_hours")
    private Double workHours;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable=false)
    private Company company;

    @OneToMany(mappedBy = "offer")
    private List<Investment> investments;
}
