package training.evaluation.training.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import training.evaluation.training.model.Training;
import training.evaluation.training.service.ITrainingServices;
import org.springframework.http.ResponseEntity;

import java.util.List;
import javax.validation.Valid;

@CrossOrigin(origins = {"${origins}"})
@RestController
@RequestMapping(value = "/api")
@Api(description = "Resource to expose all available training endpoints", tags = {"TrainingResource"})
public class TrainingResource {

    @Autowired
    ITrainingServices services;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "Create new training record", notes = "The training must contain Name and Level property, Description is optional. ")
    public Training createTraining(@ApiParam(value = "Training in JSON format. Name and Level are required fields, Description field is optional. ID is autogenerated.", required = true) @Valid @RequestBody Training training) {
        return services.createTraining(training);
    }

    @RequestMapping(value = "/trainings", method = RequestMethod.GET)
    @ApiOperation(value = "Get all training records", notes = "Return list of training records")
    public Iterable<Training> getAllTrainings() {

        return services.getAllTrainings();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/training/{id}")
    @ApiOperation(value = "Delete training record", notes = "Delete training by ID, as a path variable")
    public ResponseEntity<String> deleteTraining(@ApiParam(value = "ID of the record that we need to delete", required = true) @PathVariable String id) {
        return services.deleteTraining(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/training/{id}")
    @ApiOperation(value = "Update training record", notes = "Update training by ID, as a path variable. Request body is training in JSON format with new values - Name and Level are required, Description is optional")
    public ResponseEntity<Training> updateTraining(@ApiParam(value = "ID of the record that we need to update.", required = true) @PathVariable String id, @ApiParam(value = "Training object in JSON format with Name and Level as a required fields, Description field is optional", required = true) @RequestBody Training training) {
        return services.updateTraining(id, training);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/training/filterByName/{name}")
    @ApiOperation(value = "Filter training by name", notes = "Filter training by name. Return existing training record with searched name")
    public List<Training> filterByName(@ApiParam(value = "Name of the record that we search for.", required = true) @PathVariable String name) {
        return services.findByNameStartingWith(name);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/training/filterByLevel/{level}")
    @ApiOperation(value = "Filter training by level", notes = "Filter training by level. Return list of existing training records with searched level")
    public List<Training> filterByLevel(@ApiParam(value = "Level of the record that we search for.", required = true) @PathVariable String level) {
        return services.filterByLevel(level);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/training/filterByNameAndLevel/{name}/{level}")
    @ApiOperation(value = "Filter training by name and level", notes = "Filter training by name and level. Return list of existing training records with searched level that start with name field")
    public List<Training> filterByNameAndLevel(@ApiParam(value = "Name of the record that we search for.", required = true) @PathVariable String name, @ApiParam(value = "Level of the record that we search for.", required = true) @PathVariable String level) {
        return services.findByNameStartingWithAndLevel(name, level);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadPicture/{name}")
    @ApiOperation(value = "Upload picture to training by name", notes = "Find training by name and upload picture")
    public Training singleFileUpload(@ApiParam(value = "File to upload", required = true) @RequestParam("file") MultipartFile multipart, @ApiParam(value = "Name of the training that we need to upload picture", required = true) @PathVariable("name") String name) {
        return services.setTrainingPicture(multipart, name);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/retrieve/{name}")
    @ApiOperation(value = "Retrieve picture to training by name", notes = "Find training by name and retrieve picture")
    public String retrieveFile(@ApiParam(value = "Name of the training that we need to retrieve picture", required = true) @PathVariable("name") String name) {
        return services.getTrainingPicture(name);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/training/filterByUserLevel")
    @ApiOperation(value = "Filter training by logged user level", notes = "Filter training by level. Return list of existing training records with level of the logged")
    public List<Training> filterByUserLevel(@ApiParam(value = "Level of the record that we search for.", required = true) @RequestHeader(value = "Authorization") String authorizationValue) {
        return services.getAllTrainingsByUserLevel(authorizationValue);
    }
}
