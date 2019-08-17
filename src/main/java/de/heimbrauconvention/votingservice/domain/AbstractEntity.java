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
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Date getCreationTime() {
    	return creationTime;
    }
    
    public void setCreationTime(Date creationTime) {
    	this.creationTime = creationTime;
    }
    
    public Date getModificationTime() {
    	return modificationTime;
    }
    
    public void setModificationTime(Date modificationTime) {
    	this.modificationTime = modificationTime;
    }

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}
    
    

}