package com.xma.task3;

import java.time.LocalDate;

public record Comment
        (String authorName,
         LocalDate createdAt,
         boolean moderated,
         String message) {
}
