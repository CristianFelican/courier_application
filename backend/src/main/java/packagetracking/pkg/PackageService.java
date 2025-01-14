package packagetracking.pkg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import packagetracking.Courier.Courier;
import packagetracking.Courier.CourierService;

import java.util.List;

@Service
public class PackageService {
    @Autowired
    private CourierService courierService;

    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private SentEmailService sentEmailService;
    // Creare pachet
    public Package createPackage(Package pkg) {
        return packageRepository.save(pkg);
    }

    // Obține toate pachetele
    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    // Actualizează un pachet
    public Package updatePackage(Integer id, Package pkg) {
        Package existingPackage = packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        if(pkg.getDeliveryAddress() != null) {
            existingPackage.setDeliveryAddress(pkg.getDeliveryAddress());
        }
        if(pkg.getStatus() != null) {
            existingPackage.setStatus(pkg.getStatus());
        }
        if(pkg.getPayOnDelivery() != null) {
            existingPackage.setPayOnDelivery(pkg.getPayOnDelivery());
        }

        return packageRepository.save(existingPackage);
    }
    // Căutare pachete după status
    public List<Package> getPackagesByStatus(String status) {
        // Convertim String-ul status într-un enum
        Status statusEnum = Status.valueOf(status.toUpperCase());
        return packageRepository.findByStatus(statusEnum);
    }
    // Șterge un pachet
    public void deletePackage(Integer id) {
        if (!packageRepository.existsById(id)) {
            throw new IllegalArgumentException("Package not found");
        }
        packageRepository.deleteById(id);
    }

    // Atribuie un pachet unui curier
    public Package assignPackageToCourier(Integer id, Package pkg) {
        Package existingPackage = packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        if (pkg.getCourier() != null) {
            existingPackage.setCourier(pkg.getCourier());
            existingPackage.setStatus(pkg.getStatus() != null ? pkg.getStatus() : existingPackage.getStatus());
        }

        return packageRepository.save(existingPackage);
    }

    public Package assignPackageToCourierAndNotify(Integer id, Package pkg) {
        // Atribuim pachetul curierului

        Package assignedPackage = this.assignPackageToCourier(id, pkg);

        // Verificăm dacă pachetul a fost atribuit corect unui curier
        if (assignedPackage.getCourier() == null) {
            System.out.println("Eroare: Pachetul nu a fost atribuit niciunui curier.");
            return assignedPackage;
        }

        // Obținem ID-ul curierului din pachetul atribuit
        Integer courierId = assignedPackage.getCourier().getId();
        System.out.println("ID-ul curierului: " + courierId);  // Debugging: Verificăm ID-ul curierului

        // Verificăm dacă curierul există în baza de date folosind ID-ul
        Courier courier = courierService.getCourierById(courierId);
        if (courier == null) {
            System.out.println("Eroare: Curierul nu a fost găsit.");
            return assignedPackage;
        }

        // Verificăm dacă curierul are un email valid
        if (courier.getEmail() != null && !courier.getEmail().isEmpty()) {
            // Creăm conținutul emailului
            String emailBody = "Bună ziua, " + courier.getName() + "!\n\n" +
                    "Ți-au fost atribuite colete noi de livrat. Te rugăm să te asiguri că acestea ajung la destinațiile corespunzătoare.\n\n" +
                    "Îți dorim mult succes și o zi productivă!\n\n" +
                    "Cu respect,\nEchipa de livrări";

            String emailSubject = "Colete noi de livrat";

            // Trimiterea emailului
            sentEmailService.sendPackageAssignedEmail(courier.getEmail(), emailBody, emailSubject);
        } else {
            System.out.println("Eroare: Curierul nu are un email valid.");
        }

        // Returnăm pachetul atribuit
        return assignedPackage;
    }

    public Package updatePackageStatus(Integer id, Status status) {
        Package existingPackage = packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        // Setează doar statusul pachetului
        existingPackage.setStatus(status);

        return packageRepository.save(existingPackage);  // Salvează pachetul cu statusul nou
    }
    public List<Package> getPackagesByCourier(int courierId) {
        return packageRepository.findByCourierId(courierId); // presupunând că ai o metodă findByCourierId în repository
    }

}