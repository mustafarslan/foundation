package com.foundation.core.service;

import com.foundation.core.dto.GreetingResponseDto;
import com.foundation.core.entity.Greet;
import com.foundation.core.repository.GreetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Profile("dev")
public class GreetService {

    private final ModelMapper modelMapper;
    private final GreetRepository greetRepository;

    public GreetingResponseDto greet() {
        Greet greet = greetRepository.findById(1L);
        return modelMapper.map(greet, GreetingResponseDto.class);
    }
}
