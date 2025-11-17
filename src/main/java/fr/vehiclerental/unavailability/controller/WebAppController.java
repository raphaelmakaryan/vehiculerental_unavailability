package fr.vehiclerental.unavailability.controller;

import fr.vehiclerental.unavailability.entity.*;
import fr.vehiclerental.unavailability.exception.*;
import fr.vehiclerental.unavailability.service.UnavaibilityDAO;
import fr.vehiclerental.unavailability.service.UnavailabilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

@RestController
public class WebAppController {
    private final UnavaibilityDAO unavaibilityDAO;
    private final UnavailabilityService unavaibilityService;

    public WebAppController(UnavaibilityDAO unavaibilityDAO, UnavailabilityService unavaibilityService) {
        this.unavaibilityDAO = unavaibilityDAO;
        this.unavaibilityService = unavaibilityService;
    }

    @Operation(summary = "Home page")
    @RequestMapping("/")
    public String index() {
        return "Welcome to the Vehicle Rental company's Unavailability API!";
    }

    @Operation(summary = "Voir tout les soucis de la base de données ", description = "Requête pour la récupération de tout les soucis de la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unavailability.class)))})
    @GetMapping("/unavailability")
    public List<Unavailability> unavailability() {
        return unavaibilityDAO.findAll();
    }


    @Operation(summary = "Voir un soucis spécifique de la base de données", description = "Requête pour la soucis d'un entretien de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unavailability.class))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"The maintenance schedule defined for this vehicle was not found. \",\n" + "  \"status\": 404\n" + "}")}))})
    @RequestMapping(path = "/unavailability/{id}", method = RequestMethod.GET)
    public List<Unavailability> getUnavailability(@Parameter(description = "Identifiant du soucis", required = true) @PathVariable(value = "id") int id) {
        return unavaibilityService.oneUnavailability(id);
    }

    @Operation(summary = "Crée un nouveau soucis dans la base de données", description = "Requête pour crée/ajouter un soucis dans la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre soucis a été ajoutée !\"\n" + "}")))})
    @RequestMapping(value = "/unavailability", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addUnavailability(@Validated @RequestBody Unavailability informations) {
        return ResponseEntity.ok(unavaibilityService.addUnavailabilityService(informations));
    }


    @Operation(summary = "Mettre à jour un soucis dans la base de données", description = "Requête pour mettre a jour un soucis dans la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été modifié !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Maintenance not found with ID : 1\",\n" + "  \"status\": 404\n" + "}")
    }))})
    @PutMapping("/unavailability/{id}")
    public ResponseEntity<Map<String, Object>> editUnavailability(@Parameter(description = "Identifiant d'un soucis", required = true) @PathVariable(value = "id") int idSoucis, @Validated @RequestBody Unavailability unavailabilityRequest) {
        return ResponseEntity.ok(unavaibilityService.editUnavailabilityService(idSoucis, unavailabilityRequest));
    }

    @Operation(summary = "Supprimer un soucis de la base de données", description = "Requête pour supprimer un soucis de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre soucis a été supprimé !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Unavailability not found with ID : 1 \",\n" + "  \"status\": 404\n" + "}")
    }))})
    @DeleteMapping("/unavailability/{id}")
    public ResponseEntity<Map<String, Object>> deleteUnavailability(@Parameter(description = "Identifiant du soucis", required = true) @PathVariable(value = "id") int idSoucis) {
        return ResponseEntity.ok(unavaibilityService.deleteUnavailabilityService(idSoucis));
    }
}

