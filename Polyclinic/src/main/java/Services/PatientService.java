package Services;

import Entities.Department;
import Entities.Patient;
import Interfaces.DepartmentRepository;
import Interfaces.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    public PatientService(PatientRepository patientRepository, DepartmentRepository departmentRepository) {
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    public Patient createPatient(Patient patient, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        patient.setDepartment(department);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setBirthDate(updatedPatient.getBirthDate());
        patient.setPhone(updatedPatient.getPhone());
        patient.setEmail(updatedPatient.getEmail());
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public List<Patient> searchPatients(String query) {
        return patientRepository.findByFirstNameContainingOrLastNameContaining(query, query);
    }

    public PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public DepartmentRepository getDepartmentRepository() {
        return departmentRepository;
    }

    @Override
    public String toString() {
        return "PatientService{" +
                "patientRepository=" + patientRepository +
                ", departmentRepository=" + departmentRepository +
                '}';
    }
}
