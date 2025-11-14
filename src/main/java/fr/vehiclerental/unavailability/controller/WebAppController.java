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
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unavailability.class))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnavailabilityNotFind.class)))})
    @RequestMapping(path = "/unavailability/{id}", method = RequestMethod.GET)
    public List<Unavailability> getUnavailability(@Parameter(description = "Identifiant du soucis", required = true) @PathVariable(value = "id") int id) {
        try {
            return unavaibilityDAO.findById(id);
        } catch (Exception e) {
            throw new UnavailabilityNotFind();
        }
    }

/*
    @Operation(summary = "Crée un nouvel entretien dans la base de données", description = "Requête pour crée/ajouter un entretien dans la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationAdd.class))), @ApiResponse(responseCode = "405", description = "Erreur métier",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class),
                    examples = {
                            @ExampleObject(
                                    name = "Client introuvable",
                                    value = """
                                            {
                                              "timestamp": "2025-11-06T15:00:00",
                                              "status": 404,
                                              "error": "Client introuvable",
                                              "message": "Client not found with ID : 1"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Client déjà réservé",
                                    value = """
                                            {
                                              "timestamp": "2025-11-06T15:00:00",
                                              "status": 404,
                                              "error": "Client déjà réservé",
                                              "message": "Ce client dispose déjà d'une réservation en cours"
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "Véhicule déjà réservé",
                                    value = """
                                            {
                                              "timestamp": "2025-11-06T15:00:00",
                                              "status": 404,
                                              "error": "Véhicule déjà réservé",
                                              "message": "Le véhicule 12 est déjà réservé pour cette période"
                                            }
                                            """
                            )
                    }
            )
    )})

 */

    @RequestMapping(value = "/unavailability", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addUnavailability(@Validated @RequestBody Unavailability informations) {
        try {
            Map<String, Object> response = new HashMap<>();
            Unavailability unavailability = new Unavailability();
            unavailability.setTypeVehicle(informations.getTypeVehicle());
            unavailability.setDescription(informations.getDescription());
            unavailability.setTime(informations.getTime());
            unavaibilityDAO.save(unavailability);
            response.put("success", true);
            response.put("message", "Votre soucis a été ajouté !");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new UnavailabilityNotFind();
        }
    }


    @Operation(summary = "Mettre à jour un soucis dans la base de données", description = "Requête pour mettre a jour un soucis dans la base de données ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été modifié !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Maintenance not found with ID : 1\",\n" + "  \"status\": 404\n" + "}")
    }))})
    @PutMapping("/unavailability/{id}")
    public ResponseEntity<Map<String, Object>> editUnavailability(@Parameter(description = "Identifiant d'un soucis", required = true) @PathVariable(value = "id") int idSoucis, @Validated @RequestBody Unavailability unavailabilityRequest) {
        try {
            List<Unavailability> unavailability = unavaibilityDAO.findById(idSoucis);
            if (unavailability == null || unavailability.isEmpty()) {
                throw new UnavailabilityNotFind();
            } else {
                Map<String, Object> response = new HashMap<>();
                unavaibilityService.editUnavailability(unavailability.getFirst(), unavailabilityRequest, unavaibilityDAO);
                response.put("success", true);
                response.put("message", "Votre soucis a été modifié !");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Operation(summary = "Supprimer un soucis de la base de données", description = "Requête pour supprimer un soucis de la base de données")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Opération réussi", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" + "    \"success\": true,\n" + "    \"message\": \"Votre reservation a été supprimé !\"\n" + "}"))), @ApiResponse(responseCode = "405", description = "Échec de l'opération ", content = @Content(mediaType = "application/json", examples = {@ExampleObject(name = "Erreur générale", value = "{\n" + "  \"localDateTime\": \"2025-11-03T08:25:00\",\n" + "  \"message\": \"Reservation not found with ID : 1 \",\n" + "  \"status\": 404\n" + "}")
    }))})
    @DeleteMapping("/unavailability/{id}")
    public ResponseEntity<Map<String, Object>> deleteUnavailability(@Parameter(description = "Identifiant du soucis", required = true) @PathVariable(value = "id") int idSoucis) {
        List<Unavailability> unavailability = unavaibilityDAO.findById(idSoucis);
        if (unavailability == null || unavailability.isEmpty()) {
            throw new UnavailabilityNotFind();
        } else {
            unavaibilityDAO.delete(unavailability.getFirst());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Votre soucis a été supprimé !");
            return ResponseEntity.ok(response);
        }
    }
}

