package packagetracking.Courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {

    @Autowired
    private CourierRepository courierRepository;

    // Creare curier
    public Courier createCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    // Obține toți curierii
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    // Actualizează un curier
    public Courier updateCourier(Integer id, Courier courier) {
        Courier existingCourier = courierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Courier not found"));

        if (courier.getName() != null) {
            existingCourier.setName(courier.getName());
        }
        if (courier.getEmail() != null) {
            existingCourier.setEmail(courier.getEmail());
        }
        if (courier.getStatus() != null) {
            existingCourier.setStatus(courier.getStatus());
        }
        return courierRepository.save(existingCourier);
    }

    // Șterge un curier
    public void deleteCourier(Integer id) {
        if (!courierRepository.existsById(id)) {
            throw new IllegalArgumentException("Courier not found");
        }
        courierRepository.deleteById(id);
    }


    public Courier getCourierById(Integer id) {
        return courierRepository.findById(id).orElse(null); // returnează null dacă nu găsește curierul
    }
}
