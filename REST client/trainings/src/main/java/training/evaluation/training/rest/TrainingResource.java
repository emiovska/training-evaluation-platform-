package training.evaluation.training.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training.evaluation.training.repository.TrainingRepository;
import training.evaluation.training.model.Training;
import org.springframework.http.ResponseEntity;


import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
@Api(description = "Resource to expose all available training endpoints", tags = {"TrainingResource"})
public class TrainingResource {

    @Autowired
    private TrainingRepository repository;

    //TrainingServices services = new TrainingServices();

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create new training record", notes = "The training must contain Name and Level property, Description is optional. ")
    public Training createTraining(@ApiParam(value = "Training in JSON format. Name and Level are required fields, Description field is optional. ID is autogenerated.", required = true) @Valid @RequestBody Training training) {
        repository.save(training);
        return training;
    }

    @RequestMapping(value = "/trainings", method = RequestMethod.GET)
    @ApiOperation(value = "Get all training records", notes = "Return list of training records")
    public Iterable<Training> getAllTrainings() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/training/{id}")
    @ApiOperation(value = "Delete training record", notes = "Delete training by ID, as a path variable")
    public ResponseEntity<String> deleteTraining(@ApiParam(value = "ID of the record that we need to delete", required = true) @PathVariable String id) {
        Optional<Training> training = repository.findById(id);
        if (training.isPresent()) {
            repository.delete(training.get());
            return new ResponseEntity<>("Training deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/training/{id}")
    @ApiOperation(value = "Update training record", notes = "Update training by ID, as a path variable. Request body is training in JSON format with new values - Name and Level are required, Description is optional")
    public ResponseEntity<Training> updateTraining(@ApiParam(value = "ID of the record that we need to update.", required = true) @PathVariable String id, @ApiParam(value = "Training object in JSON format with Name and Level as a required fields, Description field is optional", required = true) @RequestBody Training training) {
        Optional<Training> trainingData = repository.findById(id);

        if (trainingData.isPresent()) {
            Training tr = trainingData.get();
            tr.setName(training.getName());
            tr.setLevel(training.getLevel());
            tr.setDescription(training.getDescription());
            return new ResponseEntity<>(repository.save(tr), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/training/filterByName/{name}")
    @ApiOperation(value = "Filter training by name", notes = "Filter training by name. Return existing training record with searched name")
    public List<Training> filterByName(@ApiParam(value = "Name of the record that we search for.", required = true) @PathVariable String name) {
        return repository.findByName(name);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/training/filterByLevel/{level}")
    @ApiOperation(value = "Filter training by level", notes = "Filter training by level. Return list of existing training records with searched level")
    public List<Training> filterByLevel(@ApiParam(value = "Level of the record that we search for.", required = true) @PathVariable String level) {
        return repository.findByLevel(level);
    }



/*


    public void filters(){
        System.out.println("Filter by name  Code Architecture ");
        System.out.println( repository.findByName(" Code Architecture "));

        System.out.println("....");
        System.out.println("Filter by Level");
        for (Training training : repository.findByLevel("SSE")) {
            System.out.println(training);
        }
    }

    public List<Training> filterByLevel(String level) {
        return repository.findByLevel(level);
    }

    *//*public List<Training> filterByName(String name) {
        return Collections.singletonList(repository.findByName(name));
    }*/
}