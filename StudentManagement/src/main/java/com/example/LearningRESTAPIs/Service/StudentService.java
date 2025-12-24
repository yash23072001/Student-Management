package com.example.LearningRESTAPIs.Service;

import com.example.LearningRESTAPIs.DTO.AddStudentRequestDTO;
import com.example.LearningRESTAPIs.DTO.StudentDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

public interface StudentService {
    List<StudentDTO> getAllStudents();

    StudentDTO getStudentsById(Long id);

    @Nullable StudentDTO createNewStudent(AddStudentRequestDTO addStudentRequestDTO);


    void deleteById(Long id);

    @Nullable StudentDTO updateStudent(Long id, AddStudentRequestDTO addStudentRequestDTO);

    @Nullable StudentDTO updatePartialStudent(Long id, Map<String, Object> updates);
}
