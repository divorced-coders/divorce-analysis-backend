package com.nighthawk.spring_portfolio.mvc.fibonacci;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@Entity
public class FiboRetracementLevel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Getter
  @Setter
  private double value;

}
