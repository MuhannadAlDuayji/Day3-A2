package com.example.day3a2;


import com.github.javafaker.Faker;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
public class Controller {
    ArrayList<TaskTrackerSystem> taskTrackerSystem = new ArrayList<>();
    static final Faker fakerData = new Faker();
    static int ids = 1;
    static Random random = new Random();
    static final String [] status = new String[]{"Done","Not done"};

    @GetMapping("/tasks")
    public ArrayList<TaskTrackerSystem> getTasks(){
        return taskTrackerSystem;
    }
    @GetMapping("/tasks/{index}")
    public TaskTrackerSystem getTask(@PathVariable int index){
        return taskTrackerSystem.get(index);
    }

    @GetMapping("/tasks/{title}")
    public TaskTrackerSystem getTaskByTitle(@PathVariable String title){

        Optional<TaskTrackerSystem> test = taskTrackerSystem.stream().
                filter(p -> p.getTitle().equals(title)).
                findAny();
        if(test.isPresent())
            return test.get();

        throw new RuntimeException();
    }

    @PostMapping ("/add")
    public ApiResponse addTask(@RequestBody TaskTrackerSystem task){
        taskTrackerSystem.add(task);
        return new ApiResponse("task added!");
    }

    @PutMapping("/update/{index}")
    public ApiResponse updateTask(@PathVariable int index, @RequestBody TaskTrackerSystem task){

        taskTrackerSystem.set(index,task);
        return new ApiResponse("task updated");
    }

    @PutMapping("/status/{index}/{status}")
    public ApiResponse updateStatus(@PathVariable int index, @PathVariable String status){

        taskTrackerSystem.get(index).setStatus(status);
        return new ApiResponse("task updated");
    }

    @DeleteMapping("/delete/all")
    public ApiResponse deleteAllTask(){
        taskTrackerSystem = null;
        taskTrackerSystem = new ArrayList<>();
        return new ApiResponse("All data deleted");
    }

    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteTask(@PathVariable int index){
        taskTrackerSystem.remove(index);
        return new ApiResponse("task deleted!");
    }



    @GetMapping("/generate/tasktrackersystem")
    public ApiResponse generateTasks() throws NoSuchAlgorithmException {

        for (int i = 0; i < 30; i++) {
            taskTrackerSystem.add(new TaskTrackerSystem(ids++,randomTitle(),randomDescription(),randomStatus(),randomDeadLine()));
        }
        return new ApiResponse("tasks generated");
    }
    private String randomTitle(){
        return fakerData.book().title();
    }
    private String randomDescription(){ return fakerData.book().publisher();}
    private String randomStatus() throws NoSuchAlgorithmException {

        return status[random.nextInt(2)];
    }
    private String randomDeadLine() throws NoSuchAlgorithmException {
        return LocalDate.now().toString();
    }


}
