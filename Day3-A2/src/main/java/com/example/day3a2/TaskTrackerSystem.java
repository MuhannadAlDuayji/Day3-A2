package com.example.day3a2;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskTrackerSystem {

    private int id ;
    private String title ;
    private String description;
    private String status;
    private String deadLine;
}
