package com.tenco.library.service;

import com.tenco.library.dao.BookDAO;
import com.tenco.library.dao.BorrowDAO;
import com.tenco.library.dao.StudentDAO;
import com.tenco.library.dto.Book;
import com.tenco.library.dto.Student;

import java.sql.SQLException;
import java.util.List;

// 비즈니스 로직을 처리하는 서비스 클래스
// VIEW 계층(화면) -> Service 계층 --> Data 계층
// 뷰 계층에서는 DAO를 직접 호출 하지 말고 항상 Service를 통해서 접근 한다.
public class LibraryService {

  private final BookDAO bookDAO = new BookDAO();
  private final StudentDAO studentDAO = new StudentDAO();
  private final BorrowDAO borrowDAO = new BorrowDAO();

  // 만약 화면단에서 도서 추가 기능 요청 (화면단 코드) 발생한다면
  // 서비스 단에서는 사용자가 입력한 데이터가 유효한지 유효성 검사도 하고
  // 입력한 데이터가 정상적이라면 DB에 반영할 예쩡

  // 도서 추가 기능(제목, 저자 필수 검증)
  public void addBook(Book book) throws SQLException {
    // 1. 유효성 검사
    if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
      throw new SQLException("도서 제목은 필수 입력 항목입니다.");
    }
    if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
      throw new SQLException("도서 저자는 필수 입력 항목입니다.");
    }
    bookDAO.addBook(book);
  }


  // 전체 도서 목록 조회 (대출 여부 상관 없이 다 출력)
  public List<Book> getAllBooks() throws SQLException {
    return bookDAO.getAllBooks();
  }


  // 책 제목으로 검색
  public List<Book> searchBooksByTitle(Book book) throws SQLException {
    if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
      throw new IllegalArgumentException("도서 제목은 필수 입력 항목입니다.");
    }
    return bookDAO.searchBooksByTitle(book.getTitle());
  }


  // 학생 등록 기능 (이름,학번,필수 검증)
  public void addStudent(Student student) throws SQLException {
    if (student.getName() == null || student.getName().trim().isEmpty())  {
      throw new IllegalArgumentException("학생 이름은 필수 입력 항목입니다.");
    }
    if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
      throw new IllegalArgumentException("학생 학번은 필수 입력 항목입니다.");
    }
    studentDAO.addStudent(student);
  }

  // 전체 학생 목록 조회
  public List<Student> getAllStudents() throws SQLException {
    return studentDAO.getAllStudents();
  }

  // 학번이 유효한지 조회(로그인 처리)

  /**
   *
   * @param studentId - String(PK 아님)
   * @return
   */
  public Student authenticateStudent(String studentId) throws SQLException {
    if (studentId == null || studentId.trim().isEmpty()) {
      throw new IllegalArgumentException("학번을 입력 해주세요");
    }
    return studentDAO.authenticateStudent(studentId);
  }

  // 도서 대출 요청

  /**
   *
   * @param bookId
   * @param studentId : 학번이 아니라 PK값
   * @throws SQLException
   */
  public void borrowBook(int bookId, int studentId) throws SQLException {
    if (bookId <= 0 || studentId <=0) {
      throw new SQLException("유효한 도서 ID 와 학생 ID를 입력해주세요");
    }
    borrowDAO.borrowBook(bookId, studentId);
  }

  // 도서 반납 처리
  public void returnBook(int bookId, int studentId) throws SQLException {
    if (bookId <= 0 || studentId <=0) {
      throw new SQLException("유효한 도서 ID 와 학생 ID를 입력해주세요");
    }
    borrowDAO.returnBook(bookId, studentId);
  }

  // TODO 관리자 기능 추가 예정

}
