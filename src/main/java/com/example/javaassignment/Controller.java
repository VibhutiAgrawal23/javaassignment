package com.example.javaassignment;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "APIs for goal",
                version = "1.0",
                description = "Goal API performing CRUD operations",
                license = @License(name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "#", name = "Vibhuti", email = "vibhuti.agrawal@geminisolutions.com")
        )
)

@RestController
public class Controller {
    @Autowired
    private final GService service;
    Logger log = LoggerFactory.getLogger(Controller.class);

    @RequestMapping("/goals")
    public String index() {
        return "Hello!";
    }

    public Controller(GService service) {
        this.service = service;
    }

    //GET method
    @GetMapping("/goals")
    public ResponseEntity<Object> getTotalGoals() {
        log.info("Request received for fetching total goals");
        List<goals> goalsList = service.list();
        log.info("Total goals fetched");
        return new ResponseEntity<Object>(goalsList, HttpStatus.OK) ;
    }

    @Operation(summary = "Getting goals",
            responses = {
                    @ApiResponse(description = "The goal",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = goals.class))),
                    @ApiResponse(responseCode = "400", description = "Goal not found")})


    @GetMapping("/goals/{GoalId}")
    public ResponseEntity<Object> getGoalDetailsById(@PathVariable String GoalId) {
        log.info("Request received for fetching goals by "+GoalId);
        goals goal = service.get(GoalId);
        if(goal==null){
            return new ResponseEntity<Object>("Goal doesn't exist", HttpStatus.OK);
        }
        log.info("Fetched Goal Accessed");
        return new ResponseEntity<Object>(goal,HttpStatus.OK);
    }

    @Operation(summary = "New Goal Created", description = "Returns object after creating a goal")

    //POST method
    @PostMapping(value = "/goals")
    public ResponseEntity<Object>  createNewGoal(@RequestBody goals goal) {
        log.info("Request received for creating new goal with goalId "+goal.getGoalId());
        if(service.check(goal.getGoalId())==null) {
            service.save(goal);
            log.info("Goal Created Successfully");
            return new ResponseEntity<Object>("Goal Created successfully", new HttpHeaders(), HttpStatus.CREATED);
        } else {
            log.info("Goal Already Exists, Operation Failed");
            return new ResponseEntity<Object>("Goal Creation Failed", new HttpHeaders(), HttpStatus.OK);
        }

    }

    @Operation(summary = "Update goal content", responses = {
            @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = goals.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Goal not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})

    //PUT method
    @RequestMapping(value = "/goals/{GoalId}", method = RequestMethod.PUT)
    public ResponseEntity<Object>  updateGoal(@RequestBody goals goal, @PathVariable String GoalId){
        log.info("Request received for updating goal of "+GoalId);
        if(service.get(GoalId)!=null) {
            service.update(goal,GoalId);
            log.info("Goal Updated Successfully");
            return new ResponseEntity<Object>("Goal Updated Successfully",HttpStatus.OK);
        } else {
            log.info("Operation Failed");
            return new ResponseEntity<Object>("Goal Update Failed", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a content of Goal by GoalId")

    //DELETE method
    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object>  deleteGoal(@PathVariable String GoalId){
        log.info("Request received to delete goal");
        if(service.get(GoalId)!=null) {
            goals goal= service.delete(GoalId);
            log.info("Goal Deleted Successfully");
            return new ResponseEntity<Object>(goal,HttpStatus.OK);
        } else {
            log.info("Operation Unsuccessful");
            return new ResponseEntity<Object>("Goal Deletion Unsuccessful", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
}
