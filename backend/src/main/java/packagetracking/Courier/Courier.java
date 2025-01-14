package packagetracking.Courier;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Courier manager;



}
