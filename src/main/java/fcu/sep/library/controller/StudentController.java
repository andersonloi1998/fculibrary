package fcu.sep.library.controller;

import fcu.sep.library.model.Student;
import fcu.sep.library.exception.ResourceNotFoundException;
import fcu.sep.library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    //Get all Student
    @GetMapping("/student")
    public List<Student> getAllStudent(){
        return studentRepository.findAll();
    }

    //Create new Student
    @PostMapping("/student")
    public Student createStudent(@Valid @RequestBody Student student){
        return studentRepository.save(student);
    }

    //Get a single Student
    @GetMapping("/student/{studentId}")
    public Student getStudentById(@PathVariable(value="studentId")Long StudentstudentId){
        return studentRepository.findById(StudentstudentId)
                .orElseThrow(()->new ResourceNotFoundException("Student","studentId",StudentstudentId));
    }

    //Update Student
    @PutMapping("/student/{studentId}")
    public Student updateNote(@PathVariable(value = "studentId") Long StudentstudentId,
                           @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findById(StudentstudentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentId", StudentstudentId));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());

        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }

    //Delete Student
    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "studentId") Long StudentstudentId) {
        Student student = studentRepository.findById(StudentstudentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "studentId", StudentstudentId));

        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }
}
