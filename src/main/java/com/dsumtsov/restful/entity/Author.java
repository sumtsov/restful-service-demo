package com.dsumtsov.restful.entity;

import com.dsumtsov.restful.exception.ResourceExistsException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Entity
@Data
@NoArgsConstructor
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "fio", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    public void addBook(@NonNull Book book) {
        if (book.getAuthor() != null)
            throw new IllegalStateException("Author is already assigned to the book");
        if (getBooks().contains(book))
            throw new ResourceExistsException(format("Author '%s' already has a book '%s'", this.name, book.getTitle()));
        book.setAuthor(this);
        getBooks().add(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Book)) return false;
        Author author = (Author) o;
        return new EqualsBuilder()
                .append(email, author.getEmail())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(email)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("email", email)
                .append("birthday", birthday)
                .toString();
    }
}
