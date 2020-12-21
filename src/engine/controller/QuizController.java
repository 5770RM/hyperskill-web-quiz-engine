package engine.controller;

import engine.exception.QuizNotFoundException;
import engine.model.*;
import engine.repository.CompletionRepository;
import engine.repository.QuizRepository;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


@RestController
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CompletionRepository completionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @PostMapping(path = "/api/register")
    public ResponseEntity<String> register(@Valid @RequestBody User user) {
        if(userRepository.findByEmail(user.getEmail()) == null && user.getPassword().length() >= 5) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<String>("You have successfully logged in",HttpStatus.OK);

        } else {
            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping(path = "/api/quizzes", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(((User)principal).getUsername());
        quizRepository.save(quiz);
        if (currentUser.getQuizList() == null) currentUser.setQuizList(new ArrayList<Integer>());
        currentUser.getQuizList().add(quiz.getId());
        userRepository.save(currentUser);
        return quiz;
    }


    @DeleteMapping(path = "/api/quizzes/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(((User)principal).getUsername());

        Quiz quiz = quizRepository.findQuizById(id);

        if (quiz == null) return new ResponseEntity<String>("Quiz not found", HttpStatus.NOT_FOUND);

        if (currentUser.getQuizList().contains(quiz.getId())) {
            currentUser.getQuizList().remove((Object)quiz.getId());
            quizRepository.delete(quiz);
            return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
        } else {
            System.out.println(Arrays.toString(currentUser.getQuizList().toArray()));
            return new ResponseEntity<String>("Forbidden", HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {
        if (quizRepository.findQuizById(id) == null) {
            throw new QuizNotFoundException();
        } else {
            return quizRepository.findQuizById(id);
        }

    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));

        return quizRepository.findAll(paging);
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<CompletionEntity> getAllCompletedQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "completedAt") String sortBy) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(((User)principal).getUsername());

        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());

        return completionRepository.findAllByEmail(currentUser.getUsername(), paging);
    }

    

    @PostMapping (path = "/api/quizzes/{id}/solve")
    public Feedback getAnswer(@PathVariable int id, @Valid @RequestBody(required = false) Answer answer) {
            if (answer == null) answer = new Answer(new int[]{});
            if (Arrays.equals(quizRepository.findQuizById(id).getAnswer(), answer.getAnswer())) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User currentUser = userRepository.findByEmail(((UserDetails)principal).getUsername());

                completionRepository.save(new CompletionEntity(id, currentUser.getUsername(), new Date()));

                System.out.println(id);

                return new Feedback(true, "Congratulations, you're right!");
            } else {
                return new Feedback(false, "Wrong answer! Please, try again.");
            }

    }


    @PostMapping (path = "/actuator/shutdown")
    public void shutDown() {

    }
    }


