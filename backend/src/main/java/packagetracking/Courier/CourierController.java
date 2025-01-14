package packagetracking.Courier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courier")
@CrossOrigin(origins = "http://localhost:3000")
public class CourierController {

    @Autowired
    private CourierService courierService;

    // POST pentru a adăuga un curier
    @PostMapping
    public Courier addCourier(@RequestBody Courier courier) {
        return courierService.createCourier(courier);
    }

    // GET pentru a obține toți curierii
    @GetMapping
    public List<Courier> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    // PUT pentru a actualiza un curier
    @PutMapping("/{id}")
    public Courier updateCourier(@PathVariable int id, @RequestBody Courier courier) {
        return courierService.updateCourier(id, courier);
    }

    // DELETE pentru a șterge un curier
    @DeleteMapping("/{id}")
    public void deleteCourier(@PathVariable int id) {
        courierService.deleteCourier(id);
    }

}
