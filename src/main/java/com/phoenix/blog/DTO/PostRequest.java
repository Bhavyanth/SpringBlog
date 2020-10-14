package com.phoenix.blog.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private String subpostName;
    private String postName;
    private String url;
    private String description;

    public String getSubpostName() {
        return subpostName;
    }
}
