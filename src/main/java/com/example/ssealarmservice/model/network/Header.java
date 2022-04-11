package com.example.ssealarmservice.model.network;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class Header<T>{

    private T data;
    private LocalDateTime transaction_time;
    private HttpStatus status;
    private String description;


    // OK
    public static <T> Header<T> CREATED(@Nullable T data) {
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> Header<T> OK(@Nullable T data) {
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> Header<T> BAD_REQUEST(@Nullable String description){
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .description(description)
                .build();
    }

    public static <T> Header<T> FORBIDDEN(){
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN)
                .build();
    }


    public static <T> Header<T> UNAUTHORIZED(){
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }
}
