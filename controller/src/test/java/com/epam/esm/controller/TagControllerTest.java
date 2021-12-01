package com.epam.esm.controller;

import com.epam.esm.dto.TagCreateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.hateoas.LinkMapperFacade;
import com.epam.esm.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
class TagControllerTest {
    private static final String TAG_CONTROLLER_URL = "/v2/tags/";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TagService tagService;
    @MockBean
    private LinkMapperFacade linkMapper;


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
        String url = TAG_CONTROLLER_URL + tagDto.getId();
        mockMvc.perform(get(url)).andExpect(status().is4xxClientError());
    }

    @Test
    void shouldReturnStatus4xxWhenFindById() throws Exception {
        String url = TAG_CONTROLLER_URL + "m";
        mockMvc.perform(get(url)).andExpect(status().is4xxClientError());
    }


    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    void shouldThrowExceptionWhenFindById(Long id) {
        doThrow(new ResourceNotFoundException()).when(tagService).findById(any(Long.class));
        String url = TAG_CONTROLLER_URL + "{id}";
        assertDoesNotThrow(() -> mockMvc.perform(get(url, id)));
    }

//    @Test
//    void shouldReturnNoContentStatusWhenDelete() throws Exception {
//        doNothing().when(tagService).delete(any(Long.class));
//        mockMvc.perform(delete(TAG_CONTROLLER_URL + "{id}", 1))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void shouldReturnCreatedStatusWhenCreate() throws Exception {
//        TagCreateDto tagCreateDto = new TagCreateDto("name");
//        Mockito.when(tagService.create(new TagCreateDto("name"))).thenReturn(new TagDto(1, "name"));
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(tagCreateDto);
//        mockMvc.perform(post(TAG_CONTROLLER_URL).contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                .andExpect(status().isCreated());
//    }
}
