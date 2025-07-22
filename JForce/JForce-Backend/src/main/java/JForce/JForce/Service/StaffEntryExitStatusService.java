package JForce.JForce.Service;

import JForce.JForce.Domain.Staff;
import JForce.JForce.Domain.StaffEntryExitStatus;
import JForce.JForce.Repository.StaffEntryExitStatusRepository;
import JForce.JForce.Repository.StaffRepository;
import JForce.JForce.Service.DTO.EntryExitResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffEntryExitStatusService {

    private final StaffEntryExitStatusRepository statusRepository;
    private final StaffRepository staffRepository;

    public StaffEntryExitStatus saveOrUpdate(Long staffId, StaffEntryExitStatus newStatus) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId));
        newStatus.setStaffId(staff);
        return statusRepository.save(newStatus);
    }

    public Optional<StaffEntryExitStatus> getByStaffId(Long staffId) {
        return statusRepository.findByStaffId_Id(staffId);
    }

    public List<StaffEntryExitStatus> getAll() {
        return statusRepository.findAll();
    }

    public Optional<EntryExitResponseDTO> getDTOByStaffId(Long staffId) {
        return statusRepository.findByStaffId_Id(staffId)
                .map(e -> new EntryExitResponseDTO(
                        e.getId(),
                        e.getStaffId().getName(),
                        e.getStaffId().getSurname(),
                        e.getStaffId().getUsername(),
                        e.getEntryDate(),
                        e.getPosition(),
                        e.getTitle(),
                        e.getExitDate(),
                        e.getExitReason()
                ));
    }

    public List<EntryExitResponseDTO> getAllAsDTO() {
        return statusRepository.findAll().stream()
                .map(e -> new EntryExitResponseDTO(
                        e.getId(),
                        e.getStaffId().getName(),
                        e.getStaffId().getSurname(),
                        e.getStaffId().getUsername(),
                        e.getEntryDate(),
                        e.getPosition(),
                        e.getTitle(),
                        e.getExitDate(),
                        e.getExitReason()
                )).toList();
    }

    public String generateCsvOfAllEntryExit() {
        List<EntryExitResponseDTO> dtoList = getAllAsDTO();

        StringBuilder sb = new StringBuilder();
        sb.append("Name;Surname;Username;Entry Date;Position;Title;Exit Date;Reason\n");

        for (EntryExitResponseDTO dto : dtoList) {
            sb.append(dto.getName()).append(";")
                    .append(dto.getSurname()).append(";")
                    .append(dto.getUsername()).append(";")
                    .append(dto.getEntryDate() != null ? dto.getEntryDate() : "").append(";")
                    .append(dto.getPosition() != null ? dto.getPosition() : "").append(";")
                    .append(dto.getTitle() != null ? dto.getTitle() : "").append(";")
                    .append(dto.getExitDate() != null ? dto.getExitDate() : "").append(";")
                    .append(dto.getExitReason() != null ? dto.getExitReason() : "")
                    .append("\n");
        }

        return sb.toString();
    }


}
