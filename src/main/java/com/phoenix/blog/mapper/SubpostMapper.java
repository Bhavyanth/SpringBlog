package com.phoenix.blog.mapper;

import com.phoenix.blog.DTO.SubpostDto;
import com.phoenix.blog.Bean.Post;
import com.phoenix.blog.Bean.Subpost;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubpostMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subpost.getPosts()))")
    SubpostDto mapSubpostToDto(Subpost subpost);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subpost mapDtoToSubpost(SubpostDto subpostDto);
}
