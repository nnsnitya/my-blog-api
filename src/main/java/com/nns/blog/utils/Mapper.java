package com.nns.blog.utils;

import com.nns.blog.dto.common.CategoryDto;
import com.nns.blog.dto.common.PostDto;
import com.nns.blog.dto.common.UserDto;
import com.nns.blog.entities.Category;
import com.nns.blog.entities.Post;
import com.nns.blog.entities.User;

public class Mapper {

    //mapper methods for post
    public static Post mapToPost(PostDto postDto) {
        return Post.builder()
                .postId(postDto.postId())
                .title(postDto.title())
                .content(postDto.content())
                .build();
    }
    public static PostDto mapToPostDto(Post post) {
        return new PostDto(post.getPostId(), post.getTitle(), post.getContent(),
                post.getImageName(), post.getAddedDate(), mapToCategoryDto(post.getCategory()), mapToUserDto(post.getUser()));
    }

    //mapper methods for Category
    public static Category mapToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .categoryId(categoryDto.categoryId())
                .categoryTitle(categoryDto.categoryTitle())
                .categoryDesc(categoryDto.categoryDescription())
                .build();
    }
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(category.getCategoryId(), category.getCategoryTitle(), category.getCategoryDesc());
    }

    //mapper methods for User
    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .name(userDto.name())
                .email(userDto.email())
                .password(userDto.password())
                .about(userDto.about())
                .build();
    }
    public static UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getAbout());
    }
}
