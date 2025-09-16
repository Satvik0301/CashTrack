package com.satvik.financetracker.DTO.Request;

import lombok.*;

@Data
public class PageDTO {
    int page;
    int size;
    String sortBy;
    String sortDir;
}
