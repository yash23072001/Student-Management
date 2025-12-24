package com.example.LearningRESTAPIs.Service.Impl;

import com.example.LearningRESTAPIs.DTO.AddStudentRequestDTO;
import com.example.LearningRESTAPIs.DTO.StudentDTO;
import com.example.LearningRESTAPIs.Entity.Student;
import com.example.LearningRESTAPIs.Repository.StudentRepository;
import com.example.LearningRESTAPIs.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students
                .stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .toList();
    }

    @Override
    public StudentDTO getStudentsById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found with Id: " + id));
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public @Nullable StudentDTO createNewStudent(AddStudentRequestDTO addStudentRequestDTO) {
        Student newStudent = modelMapper.map(addStudentRequestDTO, Student.class);
        Student student = studentRepository.save(newStudent);
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public void deleteById(Long id) {
        if (!studentRepository.existsById(id)){
            throw new IllegalArgumentException("Student does not exists by Id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public @Nullable StudentDTO updateStudent(Long id, AddStudentRequestDTO addStudentRequestDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with Id: " + id));
        modelMapper.map(addStudentRequestDTO, student);

       student = studentRepository.save(student);
       return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public @Nullable StudentDTO updatePartialStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with Id: " + id));

        updates.forEach((field, value) -> {
            switch (field){
                case "name": student.setName((String) value);
                break;
                case "email": student.setEmail((String) value);
                break;
                default:
                    throw new IllegalArgumentException("Field is not supported");
            }
        });

        Student saveStudent = studentRepository.save(student);
        return modelMapper.map(student, StudentDTO.class);
    }
}
