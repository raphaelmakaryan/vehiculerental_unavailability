package fr.vehiclerental.unavailability.service;

import fr.vehiclerental.unavailability.entity.Unavailability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service

public class UnavailabilityService {

    public List<Unavailability> requestUnavaibility(int idUnavailability, UnavaibilityDAO unavaibilityDao) {
        List<Unavailability> response = unavaibilityDao.findById(idUnavailability);
        if (response != null) {
            return response;
        } else {
            return new ArrayList<>();
        }
    }

    public void editUnavailability(Unavailability findindUnavailability, Unavailability unavailabilityBodyRequest, UnavaibilityDAO unavailabilityDao) {
        findindUnavailability.setTypeVehicle(unavailabilityBodyRequest.getTypeVehicle());
        findindUnavailability.setDescription(unavailabilityBodyRequest.getDescription());
        findindUnavailability.setTime(unavailabilityBodyRequest.getTime());
        unavailabilityDao.save(findindUnavailability);
    }
}