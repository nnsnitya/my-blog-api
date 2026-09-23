package com.nns.blog.dto.responses;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostImageDto {

    private Long id;
    private String objectKey;
    private String url;
    private Boolean isThumbnail = false;
    //    private Integer displayOrder;     //will add if more than one post-images will be added in future
    private String contentType;
}
