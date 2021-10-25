package com.epam.esm.controller;

import com.epam.esm.config.ApplicationRunner;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.LinkMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TagService tagService;
    @MockBean
    private LinkMapper linkMapper;

    public static Object[][] createTagsAndDto() {
        return new Object[][]{
                {new TagDto(1, "ab")},
                {new TagDto(5, "bc")}
        };
    }

    @ParameterizedTest
    @MethodSource("createTagsAndDto")
    void shouldReturnStatusOkWhenFindById(TagDto tagDto) throws Exception {
        Mockito.when(tagService.findById(tagDto.getId())).thenReturn(tagDto);
        String url = "/v1/tags/" + tagDto.getId();
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatus4xxWhenFindById() throws Exception {
        String url = "/v1/tags/m";
        mockMvc.perform(get(url)).andExpect(status().is4xxClientError());
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void shouldThrowExceptionWhenFindById(Long id) {
        doThrow(new ResourceNotFoundException()).when(tagService).findById(any(Long.class));
        String url = "/v1/tags/{id}";
        assertThrows(NestedServletException.class, () -> mockMvc.perform(get(url, id)));
    }

    @Test
    void shouldReturnNoContentStatusWhenDelete() throws Exception {
        doNothing().when(tagService).delete(any(Long.class));
        mockMvc.perform(delete("/v1/tags/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnCreatedStatusWhenCreate() throws Exception {
        TagCreateDto tagCreateDto = new TagCreateDto("name");
        Mockito.when(tagService.create(new TagCreateDto("name"))).thenReturn(new TagDto(1, "name"));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(tagCreateDto);
        mockMvc.perform(post("/v1/tags/").contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(status().isCreated());
    }
}
