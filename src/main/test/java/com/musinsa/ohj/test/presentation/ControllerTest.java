package com.musinsa.ohj.test.presentation;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({SpringExtension.class})
public class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected final JsonMapper jsonMapper = JsonMapper.builder().build();
}
