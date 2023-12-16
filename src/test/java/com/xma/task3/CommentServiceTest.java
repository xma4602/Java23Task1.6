package com.xma.task3;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class CommentServiceTest {

    List<Comment> comments = CommentGenerator.generate(20);
    CommentService service = new CommentService(comments);

    @Test
    void getModeratedFromNewToOld() {
        var result = service.getModeratedFromNewToOld();
        for (var res : result) {
            System.out.println(res);
        }
    }

    @Test
    void getModeratedFromNewToOld_PAGE() {
        var result = service.getModeratedFromNewToOld();
        for (var res : result) {
            System.out.println(res);
        }
        System.out.println();
        result = service.getModeratedFromNewToOld(1, 3);
        for (var res : result) {
            System.out.println(res);
        }
    }

    @Test
    void getModeratedAndNotModeratedByAuthor() {
        var result = service.getModeratedAndNotModeratedByAuthor("author1");
        for (var res : result) {
            System.out.println(res);
        }
    }

    @Test
    void getModeratedAndNotModeratedByAuthor_PAGE() {
        var result = service.getModeratedAndNotModeratedByAuthor("author1");
        for (var res : result) {
            System.out.println(res);
        }
        System.out.println();
        result = service.getModeratedAndNotModeratedByAuthor("author1", 1, 3);
        for (var res : result) {
            System.out.println(res);
        }

    }

    @Test
    void getAuthorNamesWithCommentsCreatedAfter() {
        var result = service.getAuthorNamesWithCommentsCreatedAfter(LocalDate.of(2023, 1, 15));
        for (var res : result) {
            System.out.println(res);
        }
    }

    @Test
    void getAuthorNamesWithCommentsCreatedAfter_PAGE() {
        LocalDate date = LocalDate.of(2023, 1, 15);
        var result = service.getAuthorNamesWithCommentsCreatedAfter(date);
        for (var res : result) {
            System.out.println(res);
        }
        System.out.println();
        result = service.getAuthorNamesWithCommentsCreatedAfter(date, 1, 3);
        for (var res : result) {
            System.out.println(res);
        }
    }

    static class CommentGenerator {
        static Random random = new Random(1);
        static int messageCount;

        public synchronized static List<Comment> generate(int count) {
            messageCount = 0;
            List<Comment> list = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                list.add(generate());
            }
            return list;
        }

        private synchronized static Comment generate() {
            int authorNumber = random.nextInt(4);
            int day = random.nextInt(1, 30);
            boolean moderated = random.nextBoolean();
            return new Comment(
                    "author" + (authorNumber + 1),
                    LocalDate.of(2023, 1, day),
                    moderated,
                    "message" + ++messageCount
            );
        }
    }
}