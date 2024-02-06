package it.spindox.springtest.student;

import it.spindox.springtest.student.dto.StudentCreationDTO;
import it.spindox.springtest.student.dto.StudentUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentModel>>findAllStudents() {
        List<StudentModel> studentList = studentService.findAllStudents();
        return new ResponseEntity<List<StudentModel>>(studentList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentModel> createStudent(@Valid @RequestBody StudentCreationDTO studentCreationDTO){
        StudentModel studentCreated = studentService.createStudent(studentCreationDTO);
        return new ResponseEntity<StudentModel>(studentCreated, HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<StudentModel> updateStudent(@PathVariable Integer id, @Valid @RequestBody StudentUpdateDTO studentUpdateDTO){
        StudentModel updatedStudent = studentService.updateStudent(id, studentUpdateDTO);
        return ResponseEntity.ok(updatedStudent);
    }
}
