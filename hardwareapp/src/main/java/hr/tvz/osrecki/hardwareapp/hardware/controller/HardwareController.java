package hr.tvz.osrecki.hardwareapp.hardware.controller;

import hr.tvz.osrecki.hardwareapp.hardware.model.HardwareCommand;
import hr.tvz.osrecki.hardwareapp.hardware.model.HardwareDTO;
import hr.tvz.osrecki.hardwareapp.hardware.service.HardwareService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("hardware")
@CrossOrigin(origins = "*")
public class HardwareController {

    private final HardwareService hardwareService;

    @GetMapping
    public List<HardwareDTO> getAllHardware() {
        return hardwareService.findAll();
    }


    @GetMapping("/search")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<HardwareDTO> search(@RequestParam(value = "query", required = true) String query) {
        return hardwareService.searchByCode(query);
    }

    @GetMapping("/{code}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<HardwareDTO> getHardware(@PathVariable String code) {
        return hardwareService
                .findByCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<HardwareDTO> save(@Valid @RequestBody final HardwareCommand command) {
        return hardwareService
                .save(command)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @PutMapping("/{code}")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<HardwareDTO> update(@PathVariable String code, @Valid @RequestBody final HardwareCommand updateHardwareCommand) {
        return hardwareService
                .update(code, updateHardwareCommand)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{code}")
    @Secured({"ROLE_ADMIN", "ROLE_DELETER"})
    public void delete(@PathVariable String code) {
        hardwareService.delete(code);
    }
}
