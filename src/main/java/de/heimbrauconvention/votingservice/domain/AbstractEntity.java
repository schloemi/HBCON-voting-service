package de.heimbrauconvention.votingservice.domain;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue
    private Long id;
	
	@Column(name = "public_id", length = 255, unique = true)
	private String publicId;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "creation_time")
    private Date creationTime;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "modification_time")
	private Date modificationTime;
    
    @PrePersist
    public void prePersist() {
        Date now = new Date();
        this.creationTime = now;
        this.modificationTime = now;
        this.publicId = UUID.randomUUID().toString();
    }
     
    @PreUpdate
    public void preUpdate() {
        this.modificationTime = new Date();
    }

}