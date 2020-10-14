package com.phoenix.blog.service;

import com.phoenix.blog.DTO.SubpostDto;
import com.phoenix.blog.exceptions.SpringPostException;
import com.phoenix.blog.mapper.SubpostMapper;
import com.phoenix.blog.Bean.Subpost;
import com.phoenix.blog.DAO.SubpostDAO;
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

    private SubpostDAO subpostDAO;
    private SubpostMapper subpostMapper;

    @Transactional
    public SubpostDto save(SubpostDto subpostDto) {
        Subpost save = subpostDAO.save(subpostMapper.mapDtoToSubpost(subpostDto));
        subpostDto.setId(save.getId());
        return subpostDto;
    }

    @Transactional(readOnly = true)
    public List<SubpostDto> getAll() {
        return subpostDAO.findAll()
                .stream()
                .map(subpostMapper::mapSubpostToDto)
                .collect(toList());
    }

    public SubpostDto getSubpost(Long id) {
        Subpost subpost = subpostDAO.findById(id)
                .orElseThrow(() -> new SpringPostException("No subpost found with ID - " + id));
        return subpostMapper.mapSubpostToDto(subpost);
    }
}
