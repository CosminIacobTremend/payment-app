package com.payment.app.lib.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "account")
@EqualsAndHashCode(of = "id")
@NamedEntityGraph(name = "Account.client",
    attributeNodes = @NamedAttributeNode("client")
)
public class Account {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "iban")
  public String iban;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  public Client client;

}
