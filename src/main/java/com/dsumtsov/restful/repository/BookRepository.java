package com.dsumtsov.restful.repository;

import com.dsumtsov.restful.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByTitle(String title);
}
