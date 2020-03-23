package dk.kvalitetsit.consentservice.entity;

import javax.persistence.*;
import java.util.Date;

@Entity(name="Municipality")
@Table(name="municipality")
public class Municipality {
	
	@Id
	private int id;
	
	@Column(nullable = false, name= "name")
	private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
