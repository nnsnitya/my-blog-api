package com.nns.blog.repositories;

import com.nns.blog.entities.Post;
import com.nns.blog.entities.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    PostImage findByPost(Post post);
}
