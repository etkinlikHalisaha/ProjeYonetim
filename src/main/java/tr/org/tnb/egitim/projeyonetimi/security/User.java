package tr.org.tnb.egitim.projeyonetimi.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import tr.org.tnb.egitim.projeyonetimi.BaseEntity;

@Entity
@Table(name = "t_user")
@NamedQuery(name = User.NQ.getByUsername, 
			query = "select u from User u where u.username = :username")
public class User extends BaseEntity implements UserDetails {
	interface NQ {
		String getByUsername = "User.getByUsername";
	}
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private boolean enabled = true;
	private List<String> roleList = new ArrayList<>();
	
	@Column(nullable = false)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(nullable = false)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(nullable = false)
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@Column(nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "t_user_rol")
	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public void addRole(String role) {
		if (!this.roleList.contains(role)) {
			this.roleList.add(role);
		}
	}
	
	public void remove(String role) {
		this.roleList.remove(role);
	}
	
	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		for (String role : this.roleList) {
			authorityList.add(new SimpleGrantedAuthority(role));
		}
		return authorityList;
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// ------------------
	
	@Transient
	public boolean isKatip() {
		return this.roleList.contains("ROLE_KATIP");
	}

	@Transient
	public boolean isAdmin() {
		return this.roleList.contains("ROLE_ADMIN");
	}
	
}
