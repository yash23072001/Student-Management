package com.example.LearningRESTAPIs.Controller;

import com.example.LearningRESTAPIs.DTO.AddStudentRequestDTO;
import com.example.LearningRESTAPIs.DTO.StudentDTO;
import com.example.LearningRESTAPIs.Entity.Student;
import com.example.LearningRESTAPIs.Service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudent(){
//        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentsById(id));
    }

    @PostMapping("")
    public ResponseEntity<StudentDTO> createnewStudent(@RequestBody @Valid AddStudentRequestDTO addStudentRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createNewStudent(addStudentRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
                                                    @RequestBody AddStudentRequestDTO addStudentRequestDTO){
        return ResponseEntity.ok(studentService.updateStudent(id, addStudentRequestDTO));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> updatePartialStudent(@PathVariable Long id,
                                                           @RequestBody Map<String, Object> updates){
        return ResponseEntity.ok(studentService.updatePartialStudent(id, updates));

    }
}
