package com.example.spring.test.unitaire.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1 = new Book(1L,"Atomic Habits","How to build better habits",5);
    Book RECORD_2 = new Book(2L,"Thinking fast and slow","How to create good mental models about thinking",4);
    Book RECORD_3 = new Book(3L,"Grokking Algorithms","Learn algorithms the fun way",5);

    @Before
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllRecords_success() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));

        Mockito.when(bookRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$[2].name",is("Grokking Algorithms")))
                .andDo(print());
    }

    @Test
    public void getBookById_success() throws Exception {

        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(Optional.ofNullable(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("Atomic Habits")));
    }

    @Test
    public void createRecord_success() throws Exception{
        Book record = Book.builder()
                .bookId(4L)
                .name("Introduction to C")
                .summary("The name but longer")
                .rating(5)
                .build();

        Mockito.when(bookRepository.save(record)).thenReturn(record);

        String content = objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/books/saved")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("Introduction to C")));
    }

    @Test
    public void updateBookRecord_success() throws Exception{
        Book updateRecord = Book.builder()
                .bookId(1L)
                .name("Update Book Name")
                .summary("Update Summary")
                .rating(1)
                .build();

        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(Optional.ofNullable(RECORD_1));
        Mockito.when(bookRepository.save(updateRecord)).thenReturn(updateRecord);

        String updateContent = objectWriter.writeValueAsString(updateRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/books/updated")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonPath("$.name",is("Update Book Name")));

    }

    @Test
    public void deleteBookById_success() throws Exception{
        Mockito.when(bookRepository.findById(RECORD_2.getBookId())).thenReturn(Optional.ofNullable(RECORD_2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
