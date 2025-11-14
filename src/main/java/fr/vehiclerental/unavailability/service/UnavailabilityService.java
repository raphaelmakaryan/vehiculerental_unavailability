package fr.vehiclerental.unavailability.service;

import fr.vehiclerental.unavailability.entity.Unavailability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service

public class UnavailabilityService {

    @Autowired
    private UnavaibilityDAO unavaibilityDAO;

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
     * @param findindUnavailability Soucis récupérer via son id plus tot
     * @param unavailabilityBodyRequest Soucis réqcuperer venant de la requete
     * @param unavailabilityDao DAO de unavailability
     */
    public void editUnavailability(Unavailability findindUnavailability, Unavailability unavailabilityBodyRequest, UnavaibilityDAO unavailabilityDao) {
        findindUnavailability.setTypeVehicle(unavailabilityBodyRequest.getTypeVehicle());
        findindUnavailability.setDescription(unavailabilityBodyRequest.getDescription());
        findindUnavailability.setTime(unavailabilityBodyRequest.getTime());
        unavailabilityDao.save(findindUnavailability);
    }
}