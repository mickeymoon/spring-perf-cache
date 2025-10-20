package com.example.cache.controller;

import com.example.cache.dao.CacheDao;
import com.example.cache.exception.KeyNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CacheController.class)
class CacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacheDao cacheDao;

    @Test
    void putShouldStoreValueThroughDao() throws Exception {
        mockMvc.perform(post("/put")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"key\":\"alpha\",\"value\":\"beta\"}"))
                .andExpect(status().isCreated());

        verify(cacheDao).put(eq("alpha"), eq("beta"));
    }

    @Test
    void getShouldReturnValueFromDao() throws Exception {
        when(cacheDao.get("k"))
                .thenReturn("v");

        mockMvc.perform(get("/get")
                        .param("key", "k"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"k\":\"v\"}"));
    }

    @Test
    void getShouldReturnNotFoundWhenDaoThrowsKeyNotFound() throws Exception {
        when(cacheDao.get("missing"))
                .thenThrow(new KeyNotFoundException("missing"));

        mockMvc.perform(get("/get")
                        .param("key", "missing"))
                .andExpect(status().isNotFound());
    }
}
