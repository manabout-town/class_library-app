package com.tenco.library.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Borrow {
  private int id;
  private int studentId;
  private int bookId;
  private LocalDate borrowDate;
  private LocalDate returnDate;
}
