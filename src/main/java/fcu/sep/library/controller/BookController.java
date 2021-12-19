package fcu.sep.library.controller;

import fcu.sep.library.exception.ResourceNotFoundException;
import fcu.sep.library.model.Book;
import fcu.sep.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    //Get all Book
    @GetMapping("/book")
    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }

    //Create new Book
    @PostMapping("/book")
    public Book createBook(@Valid @RequestBody Book book){
        return bookRepository.save(book);
    }

    //Get a single Book
    @GetMapping("/book/{isbn}")
    public Book getBookById(@PathVariable(value="isbn")Long Bookisbn){
        return bookRepository.findById(Bookisbn)
                .orElseThrow(()->new ResourceNotFoundException("Book","isbn",Bookisbn));
    }

    //Update Student
    @PutMapping("/book/{isbn}")
    public Book updateNote(@PathVariable(value = "isbn") Long Bookisbn,
                              @Valid @RequestBody Book bookDetails) {

        Book book = bookRepository.findById(Bookisbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", Bookisbn));

        book.setName(bookDetails.getName());
        book.setType(bookDetails.getType());
        book.setAuthor(bookDetails.getAuthor());

        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }

    //Delete Book
    @DeleteMapping("/book/{isbn}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "isbn") Long Bookisbn) {
        Book book = bookRepository.findById(Bookisbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "isbn", Bookisbn));

        bookRepository.delete(book);

        return ResponseEntity.ok().build();
    }
}
