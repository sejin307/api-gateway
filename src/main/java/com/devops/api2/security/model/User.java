package com.devops.api2.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

   @JsonIgnore
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
   @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ", allocationSize = 1)
   private Long id;

   @Column(name = "username", length = 50, unique = true)
   @NotNull
   private String username;

   @JsonIgnore
   @Column(name = "password", length = 100)
   @NotNull
   private String password;

   @Column(name = "firstname", length = 50)
   @NotNull
   private String firstname;

   @Column(name = "lastname", length = 50)
   @NotNull
   private String lastname;

   @Column(name = "email", length = 50)
   @NotNull
   private String email;

   @JsonIgnore
   @Column(name = "activated")
   @NotNull
   private boolean activated;

   @ManyToMany
   @JoinTable(
      name = "user_authority",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
   @BatchSize(size = 20)
   private Set<Authority> authorities = new HashSet<>();

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstname() {
      return firstname;
   }

   public void setFirstname(String firstname) {
      this.firstname = firstname;
   }

   public String getLastname() {
      return lastname;
   }

   public void setLastname(String lastname) {
      this.lastname = lastname;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id.equals(user.id);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }

   @Override
   public String toString() {
      return "User{" +
         "username='" + username + '\'' +
         ", password='" + password + '\'' +
         ", firstname='" + firstname + '\'' +
         ", lastname='" + lastname + '\'' +
         ", email='" + email + '\'' +
         ", activated=" + activated +
         '}';
   }
}
