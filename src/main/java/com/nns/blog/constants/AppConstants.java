package com.nns.blog.constants;

public final class AppConstants {
    private AppConstants() {
        throw new AssertionError("No instances for this!");
    }

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "10";
    public static final String SORT_BY = "postId";
    public static final String SORT_DIR = "asc";

    public static final Long NORMAL_USER = Long.valueOf(502);
    public static final Long ADMIN_USER = Long.valueOf(501);
}
