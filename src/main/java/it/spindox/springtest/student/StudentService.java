package it.spindox.springtest.student;

import it.spindox.springtest.student.dto.StudentCreationDTO;
import it.spindox.springtest.student.dto.StudentUpdateDTO;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public List<StudentModel> findAllStudents() {
        return studentRepository.findAll();
    }

    public StudentModel createStudent(StudentCreationDTO studentCreationDTO) {
        StudentModel student = modelMapper.map(studentCreationDTO, StudentModel.class);
        return studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Studente non trovato.");
        }
        studentRepository.deleteById(id);
    }

    public StudentModel updateStudent(Integer id, StudentUpdateDTO studentUpdateDTO){
        StudentModel student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Studente non trovato"));
        modelMapper.map(studentUpdateDTO, student);
        return studentRepository.save(student);
    }
}
