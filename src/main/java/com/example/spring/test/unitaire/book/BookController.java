package com.example.spring.test.unitaire.book;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping(value = "/books")
public class BookController {
    private final BookRepository bookRepository;

    private final static Logger logger = LoggerFactory.getLogger(BookController.class);

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBookRecords(){
        return bookRepository.findAll();
    }

    @GetMapping(value = "/{bookId}")
    public Book getBookById(@PathVariable(value = "bookId")Long bookId){
        return bookRepository.findById(bookId)
                .orElseThrow(()->{
                    try {
                        logger.error("bookId not found " + bookId);
                        throw new Exception("Book not found " + bookId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @PostMapping("/saved")
    public Book createBookRecord(@Valid @RequestBody Book bookRecord){
        return bookRepository.save(bookRecord);
    }

    @PutMapping("/updated")
    public Book updateBookRecord(@Valid @RequestBody Book bookRecord) throws Exception {
        if(bookRecord == null || bookRecord.getBookId() == null){
            throw new Exception("BookRecord or ID must not null!");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookRecord.getBookId());
        if(optionalBook.isEmpty()){
            logger.error("Book with ID: "+ bookRecord.getBookId() +" does not exist");
            throw new Exception("Book with ID: "+ bookRecord.getBookId() +" does not exist");
        }

        Book existingBookRecord = optionalBook.get();
        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setSummary(bookRecord.getSummary());
        existingBookRecord.setRating(bookRecord.getRating());

        return bookRepository.save(existingBookRecord);
    }

    @DeleteMapping(value = "/{bookId}")
    public void deleteBookById(@PathVariable(value = "bookId")Long bookId) throws Exception{
        if(bookRepository.findById(bookId).isEmpty()){
            logger.error("bookId "+ bookId +" is not present");
            throw new Exception("bookId "+ bookId +" is not present");
        }
        bookRepository.deleteById(bookId);
    }

}
