package com.test.todo.service;

import com.test.todo.dao.TodoActivityRepository;
import com.test.todo.dto.TodoActivityDTO;
import com.test.todo.entity.TodoActivityBean;
import com.test.todo.util.ResultMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoActivityServiceImpl implements  TodoActivityService{

    private final TodoActivityRepository todoActivityRepository;

    public TodoActivityServiceImpl(TodoActivityRepository todoActivityRepository) {
        this.todoActivityRepository = todoActivityRepository;
    }

    @Override
    public ResultMessage<?> findTodoActivity() {
        return new ResultMessage<>(true, ResultMessage.MESSAGE_TODO_ACTIVITY_FIND, convertModelToDTO(todoActivityRepository.findAllByOrderByCreatedDatetimeDesc()));
    }

    private List<TodoActivityDTO> convertModelToDTO(List<TodoActivityBean> todoActivityBeanList){
        List<TodoActivityDTO> result = new ArrayList<TodoActivityDTO>();
        for(TodoActivityBean bean : todoActivityBeanList){
            result.add(TodoActivityDTO.builder().id(bean.getId()).activityFeedMessage(bean.getActivityFeed()).activityTime(bean.getCreatedDatetime()).build());
        }
        return result;
    }
}
