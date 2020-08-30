package com.blog.MyBlog.service;

import com.blog.MyBlog.dto.SubpostDto;
import com.blog.MyBlog.exceptions.SpringPostException;
import com.blog.MyBlog.mapper.SubpostMapper;
import com.blog.MyBlog.model.Subpost;
import com.blog.MyBlog.repository.SubpostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubpostService {

    private  SubpostRepository subpostRepository;
    private SubpostMapper subpostMapper;

    @Transactional
    public SubpostDto save(SubpostDto subpostDto) {
        Subpost save = subpostRepository.save(subpostMapper.mapDtoToSubpost(subpostDto));
        subpostDto.setId(save.getId());
        return subpostDto;
    }

    @Transactional(readOnly = true)
    public List<SubpostDto> getAll() {
        return subpostRepository.findAll()
                .stream()
                .map(subpostMapper::mapSubpostToDto)
                .collect(toList());
    }

    public SubpostDto getSubpost(Long id) {
        Subpost subpost = subpostRepository.findById(id)
                .orElseThrow(() -> new SpringPostException("No subpost found with ID - " + id));
        return subpostMapper.mapSubpostToDto(subpost);
    }
}
