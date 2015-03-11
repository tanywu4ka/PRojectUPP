package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
public class StaffMember {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int 		id;
	private String staff_login;
	private String staff_pass;
	private String staff_name;
	private String role;
	
	public StaffMember() {
		
	}

	@Override
	public String toString() {
		return "Login: " + staff_login + ", name: " + staff_name + ", role: " + role;
	}

	public int getId() {
		return id;
	}

	public String getStaff_login() {
		return staff_login;
	}

	public String getStaff_pass() {
		return staff_pass;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public String getRole() {
		return role;
	}
	
	
	
	

}
