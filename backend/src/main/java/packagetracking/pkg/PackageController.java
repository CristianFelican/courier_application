    package packagetracking.pkg;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import packagetracking.Courier.Courier;
    import packagetracking.Courier.CourierService;

    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/package") // Ruta de bază pentru toate rutele de pachet
    @CrossOrigin(origins = "http://localhost:3000")
    public class PackageController {

        @Autowired
        private PackageService packageService;
        @Autowired
        private CourierService courierService;

        @Autowired
        private SentEmailService sentEmailService;

        // POST pentru a adăuga un pachet
        @PostMapping
        public Package addPackage(@RequestBody Package pkg) {
            return packageService.createPackage(pkg);
        }

        // GET pentru a obține toate pachetele
        @GetMapping
        public List<Package> getAllPackages() {
            return packageService.getAllPackages();
        }

        @PutMapping("/{id}")
        public Package updatePackage(@PathVariable Integer id, @RequestBody Package pkg) {
            return packageService.updatePackage(id, pkg);
        }
        // PUT pentru a actualiza un pachet
        @PatchMapping("/assign/{id}")
        public void assignPackage(@PathVariable int id, @RequestBody Package pkg) {
            packageService.assignPackageToCourierAndNotify(id, pkg);
        }

        @PatchMapping("/{id}")
        public Package updatePackageStatus(@PathVariable int id, @RequestBody Map<String, String> statusUpdate) {
            String statusString = statusUpdate.get("status");
            Status status = Status.valueOf(statusString); // Conversia din string în enum
            return packageService.updatePackageStatus(id, status);
        }
        // DELETE pentru a șterge un pachet
        @DeleteMapping("/{id}")
        public void deletePackage(@PathVariable int id) {
            packageService.deletePackage(id);
        }
        @GetMapping("/courier/{courierId}")
        public List<Package> getPackagesByCourier(@PathVariable int courierId) {
            return packageService.getPackagesByCourier(courierId);
        }


    }
