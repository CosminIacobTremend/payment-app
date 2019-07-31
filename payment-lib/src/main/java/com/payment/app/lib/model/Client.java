package com.payment.app.lib.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "client")
@EqualsAndHashCode(of = "id")
public class Client {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;
  @Column(name = "cnp")
  public String cnp;
  @Column(name = "first_name")
  public String firstName;
  @Column(name = "last_name")
  public String lastName;
  @Column(name = "email")
  public String email;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
      orphanRemoval = true, mappedBy = "client")
  public Set<Account> accounts;

}
