package com.poc.api.donnee.domaine.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "userauthentification")
@Getter
@Setter
@AllArgsConstructor
public class UserAuthentification implements Serializable, UserDetails {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String login;
    private String password;
    private String mail;
    private String roleName;
    @OneToMany(mappedBy = "userauthentification")
	Set<Comment> comment;
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();

        this.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });
        return authorities;
	}
	
	public List<String> getRoleList(){
        if(this.roleName.length() > 0){
            return Arrays.asList(this.roleName.split(","));
        }
        return new ArrayList<>();
    }

	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	
	@Override
	public String getUsername() {
		return this.mail;
	}
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}
	@Override
	public boolean isEnabled() {
		return false;
	}

	public UserAuthentification(String mail, String login, String password, String roleName) {
		super();
		this.login = login;
		this.password = password;
		this.mail = mail;
		this.roleName = roleName;
	}

	public UserAuthentification() {
	}
	
	

}
