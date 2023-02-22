package pl.edu.agh.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Investments")
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "price_change")
    private Double priceChange;

    @Column(name = "costs_change")
    private Double costsChange;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "finished")
    private Boolean finished;

    @ManyToOne
    @JoinColumn(name = "offer_id", nullable=false)
    private Offer offer;
}
