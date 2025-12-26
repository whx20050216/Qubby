package com.adplatform.qubby.model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discussions")
@Data
public class Discussion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reply> replies = new ArrayList<>();
}