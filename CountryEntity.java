package prayagya.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="country_master")
public class CountryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="country_id")
	private Integer cuntryId;
	
	@Column(name="country_name")
	private String cuntryName;

	public Integer getCuntryId() {
		return cuntryId;
	}

	public void setCuntryId(Integer cuntryId) {
		this.cuntryId = cuntryId;
	}

	public String getCuntryName() {
		return cuntryName;
	}

	public void setCuntryName(String cuntryName) {
		this.cuntryName = cuntryName;
	}

	

}
