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
@Table(name = "Emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private java.util.UUID id;

    @Column(name = "_month")
    private String month;

    @Column(name = "source")
    private String from;

    @Column(name = "topic")
    private String topic;

    @Column(name = "message")
    private String message;

    @Column(name = "opened")
    private Boolean opened;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;
}
