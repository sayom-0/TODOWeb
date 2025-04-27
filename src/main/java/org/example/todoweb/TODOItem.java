package org.example.todoweb;

import jakarta.persistence.*;

@Entity
@Table(name = "Item")
public class TODOItem

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    public TODOItem(int index)
    {
        id = index;
    }

    public TODOItem(String description)
    {
        this.description = description;
    }

    public TODOItem() {

    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return description;
    }

    public Integer GetId()
    {
        return id;
    }
}