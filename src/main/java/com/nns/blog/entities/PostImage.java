package com.nns.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "minio_post_image")
@Getter
@Setter
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Post post;
    private String objectKey;
    private String url;
    private Boolean isThumbnail = false;
//    private Integer displayOrder;     //will add if more than one post-images will be added in future
    private String contentType;
}
