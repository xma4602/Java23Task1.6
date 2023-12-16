package com.xma.task3;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public class CommentService {

    public static final Comparator<Comment> MODERATED_COMPARATOR = Comparator.comparing(Comment::moderated).reversed();
    public static final Comparator<Comment> CREATED_AT_COMPARATOR = Comparator.comparing(Comment::createdAt).reversed();

    private final List<Comment> comments;

    public CommentService(List<Comment> comments) {
        this.comments = comments;
    }

    public Comment[] getModeratedFromNewToOld() {
        return getModeratedFromNewToOld(0, Long.MAX_VALUE);
    }

    public Comment[] getModeratedFromNewToOld(long pageNumber, long pageSize) {
        Stream<Comment> stream = comments.stream()
                .filter(Comment::moderated)
                .sorted(CREATED_AT_COMPARATOR);
        return toPage(pageNumber, pageSize, stream, Comment[]::new);

    }

    public Comment[] getModeratedAndNotModeratedByAuthor(String authorName) {
        return getModeratedAndNotModeratedByAuthor(authorName, 0, Long.MAX_VALUE);
    }

    public Comment[] getModeratedAndNotModeratedByAuthor(String authorName, long pageNumber, long pageSize) {
        Stream<Comment> stream = comments.stream()
                .filter(x -> x.authorName().equals(authorName))
                .sorted(MODERATED_COMPARATOR);
        return toPage(pageNumber, pageSize, stream, Comment[]::new);

    }

    public String[] getAuthorNamesWithCommentsCreatedAfter(LocalDate date) {
        return getAuthorNamesWithCommentsCreatedAfter(date, 0, Long.MAX_VALUE);
    }

    public String[] getAuthorNamesWithCommentsCreatedAfter(LocalDate date, long pageNumber, long pageSize) {
        Stream<String> stream = comments.stream()
                .filter(x -> x.createdAt().isAfter(date))
                .map(Comment::authorName)
                .distinct()
                .sorted();
        return toPage(pageNumber, pageSize, stream, String[]::new);
    }

    private <T> T[] toPage(long pageNumber, long pageSize, Stream<T> stream, IntFunction<T[]> generator) {
        return stream
                .skip(pageNumber * pageSize)
                .limit(pageSize)
                .toArray(generator);
    }

}
