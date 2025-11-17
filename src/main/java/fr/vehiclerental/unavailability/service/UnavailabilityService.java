package fr.vehiclerental.unavailability.service;

import fr.vehiclerental.unavailability.entity.Unavailability;
import fr.vehiclerental.unavailability.exception.BadRequestException;
import fr.vehiclerental.unavailability.exception.UnavailabilityNotFind;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service

public class UnavailabilityService {

    @Autowired
    private UnavaibilityDAO unavaibilityDAO;

    /**
     * Méthode pour récuperer une indisponibilité précise
     * @param id id de l'indisponibilité
     * @return Réponse
     */
    public List<Unavailability> oneUnavailability(int id) {
        try {
            return unavaibilityDAO.findById(id);
        } catch (Exception e) {
            throw new UnavailabilityNotFind();
        }
    }

    /**
     * Méthode de vérifiction pour l'ajout d'une indisponibilité
     * @param informations Information de la requete
     * @return Réponse
     */
    public Map<String, Object> addUnavailabilityService(Unavailability informations) {
        try {
            Map<String, Object> response = new HashMap<>();
            Unavailability unavailability = new Unavailability();
            unavailability.setTypeVehicle(informations.getTypeVehicle());
            unavailability.setDescription(informations.getDescription());
            unavailability.setTime(informations.getTime());
            unavaibilityDAO.save(unavailability);
            response.put("success", true);
            response.put("message", "Votre soucis a été ajouté !");
            return response;
        } catch (Exception e) {
            throw new UnavailabilityNotFind();
        }
    }

    /**
     * Méthode de vérifiction pour la modifiation d'une indisponibilité
     * @param idSoucis id de l'indisponibilité
     * @param unavailabilityRequest information de la requete de l'indisponibilité
     * @return Réponse
     */
    public Map<String, Object> editUnavailabilityService(int idSoucis, Unavailability unavailabilityRequest) {
        try {
            List<Unavailability> unavailability = unavaibilityDAO.findById(idSoucis);
            if (unavailability == null || unavailability.isEmpty()) {
                throw new UnavailabilityNotFind();
            } else {
                Map<String, Object> response = new HashMap<>();
                this.editUnavailability(unavailability.getFirst(), unavailabilityRequest, unavaibilityDAO);
                response.put("success", true);
                response.put("message", "Votre soucis a été modifié !");
                return response;
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * Méthode de vérifiction pour la suppresion d'une indisponibilité
     * @param idSoucis id de l'indisponibilité
     * @return Réponse
     */
    public Map<String, Object> deleteUnavailabilityService(int idSoucis) {
        List<Unavailability> unavailability = unavaibilityDAO.findById(idSoucis);
        if (unavailability == null || unavailability.isEmpty()) {
            throw new UnavailabilityNotFind();
        } else {
            unavaibilityDAO.delete(unavailability.getFirst());
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Votre soucis a été supprimé !");
            return response;
        }
    }

    public void createMoto1() {
        Unavailability unavailability = new Unavailability();
        unavailability.setTypeVehicle("motorcycle");
        unavailability.setDescription("La chaîne des motos doit être retendue tous les 1000 km ou tous les ans.");
        unavailability.setTime(1);
        unavaibilityDAO.save(unavailability);
    }

    public void createMoto2() {
        Unavailability unavailability = new Unavailability();
        unavailability.setTypeVehicle("motorcycle");
        unavailability.setDescription("Le liquide de frein doit être changé tous les ans.");
        unavailability.setTime(1);
        unavaibilityDAO.save(unavailability);
    }

    public void createUtilityCar1() {
        Unavailability unavailability = new Unavailability();
        unavailability.setTypeVehicle("utility, car");
        unavailability.setDescription("La courroie de distribution doit être changée tous les 100 000 Km.");
        unavailability.setTime(3);
        unavaibilityDAO.save(unavailability);
    }

    public void createUtilityCar2() {
        Unavailability unavailability = new Unavailability();
        unavailability.setTypeVehicle("utility, car");
        unavailability.setDescription("Les pneus sont changés chaque année.");
        unavailability.setTime(1);
        unavaibilityDAO.save(unavailability);
    }

    public void createUtility1() {
        Unavailability unavailability = new Unavailability();
        unavailability.setTypeVehicle("utility");
        unavailability.setDescription("Les suspensions sont changées tous les 2 ans.");
        unavailability.setTime(2);
        unavaibilityDAO.save(unavailability);
    }

    /**
     * Méthode pour crée les soucis
     */
    public void saveInitialData() {
        createMoto1();
        createMoto2();
        createUtilityCar1();
        createUtilityCar2();
        createUtility1();
    }

    /**
     * Méthode pour modifier un soucis
     *
     * @param findindUnavailability     Soucis récupérer via son id plus tot
     * @param unavailabilityBodyRequest Soucis réqcuperer venant de la requete
     * @param unavailabilityDao         DAO de unavailability
     */
    public void editUnavailability(Unavailability findindUnavailability, Unavailability unavailabilityBodyRequest, UnavaibilityDAO unavailabilityDao) {
        findindUnavailability.setTypeVehicle(unavailabilityBodyRequest.getTypeVehicle());
        findindUnavailability.setDescription(unavailabilityBodyRequest.getDescription());
        findindUnavailability.setTime(unavailabilityBodyRequest.getTime());
        unavailabilityDao.save(findindUnavailability);
    }
}