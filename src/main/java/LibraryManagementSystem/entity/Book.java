package LibraryManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String language;

    @Column(name = "availability_status", nullable = false)
    private String status;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private Timestamp dateTimeUpdate;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "book"
    )
    private List<TransactionDetail> transactionDetails = new ArrayList<>();

    @Column(nullable = false)
    private int quantity;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "author", nullable = false, updatable = false)
    private String author;

    @Override
    public String toString() {
        return id + name + type + language + author;
    }
}
