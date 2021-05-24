package com.example.todo.demo.controller;

import com.example.todo.demo.config.MockUTF8Configuration;
import com.example.todo.demo.entity.Todo;
import com.example.todo.demo.entity.TodoDto;
import com.example.todo.demo.service.TodoService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({MockUTF8Configuration.class})
public class ToDoTests {
    @Autowired
    private TodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @BeforeEach
    void setUp() {
        Todo todo = Todo.createFrom("안녕1");
        todoService.saveTodo(todo);
    }

    @Test
    void todo_save_api_test() throws Exception {
        //given
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("todo", "hi");

        //when
        final ResultActions actions = mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("response.todo", is("hi")))
                .andDo(document("/todo/save",
                        preprocessRequest(prettyPrint(), modifyUris().port(8443)),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("todo").type(JsonFieldType.STRING).description("Todo 메시지")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("API 성공 여부"),
                                fieldWithPath("response.seq").type(JsonFieldType.NUMBER).description("Todo Seq 번호"),
                                fieldWithPath("response.todo").type(JsonFieldType.STRING).description("Todo 메시지")
                        )
                ));
    }

    @Test
    void todo_modify_api_test() throws Exception {
        // given
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("todo", "hi2");

        Todo todo = Todo.createFrom("생성");
        TodoDto todoDto = todoService.saveTodo(todo);

        // when
        final ResultActions actions = mockMvc.perform(put("/todo/{seq}", todoDto.getSeq())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("response.todo", is("hi2")))
                .andDo(document("/todo/modify",
                        preprocessRequest(prettyPrint(), modifyUris().port(8443)),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("seq").description("seq 번호")
                        ),
                        requestFields(
                                fieldWithPath("todo").type(JsonFieldType.STRING).description("Todo 메시지")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("API 성공 여부"),
                                fieldWithPath("response.seq").type(JsonFieldType.NUMBER).description("Todo Seq 번호"),
                                fieldWithPath("response.todo").type(JsonFieldType.STRING).description("Todo 메시지")
                        )
                ));
    }

    @Test
    void todo_get_api_test() throws Exception {
        //given
        Todo todo = Todo.createFrom("안녕2");
        todoService.saveTodo(todo);

        //when
        final ResultActions actions = mockMvc.perform(get("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                .andExpect(jsonPath("$.response", is(notNullValue())))
                .andExpect(jsonPath("$.response").isArray())
                .andDo(document("/todo/get-all",
                        preprocessRequest(prettyPrint(), modifyUris().port(8443)),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("API 성공 여부"),
                                fieldWithPath("response.[].seq").type(JsonFieldType.NUMBER).description("Todo Seq 번호"),
                                fieldWithPath("response.[].todo").type(JsonFieldType.STRING).description("Todo 메시지")
                        )
                ));
    }

    @Test
    void todo_delete_api_test() throws Exception {
        //given

        //when
        final ResultActions actions = mockMvc.perform(delete("/todo/{seq}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                .andDo(document("/todo/delete",
                        preprocessRequest(prettyPrint(), modifyUris().port(8443)),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("seq").description("seq 번호")
                        ),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("API 성공 여부"),
                                fieldWithPath("response").type(JsonFieldType.STRING).description("SUCCESS")
                        )
                ));
    }

    @Test
    void todo_delete_all_api_test() throws Exception {
        //given

        //when
        final ResultActions actions = mockMvc.perform(delete("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success", is(true)))
                .andDo(document("/todo/delete-all",
                        preprocessRequest(prettyPrint(), modifyUris().port(8443)),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("API 성공 여부"),
                                fieldWithPath("response").type(JsonFieldType.STRING).description("SUCCESS")
                        )
                ));
    }
}
