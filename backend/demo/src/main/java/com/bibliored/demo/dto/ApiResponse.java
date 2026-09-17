package com.bibliored.demo.dto;

public record ApiResponse<T>(String mensaje, T data) {
}
