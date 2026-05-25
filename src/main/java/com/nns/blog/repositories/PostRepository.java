package com.nns.blog.repositories;

import com.nns.blog.entities.Category;
import com.nns.blog.entities.Post;
import com.nns.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

}
