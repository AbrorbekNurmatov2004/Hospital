package hospital.medflow.service.base;

import hospital.medflow.criteria.BaseCriteria;
import hospital.medflow.criteria.DataList;
import hospital.medflow.dto.medicalRecord.MedicalRecordCreateDto;
import hospital.medflow.dto.medicalRecord.MedicalRecordDto;
import java.util.List;

public interface MedicalRecordService {

    DataList<List<MedicalRecordDto>> getAll(BaseCriteria build);

    MedicalRecordDto create(MedicalRecordCreateDto dto);

    List<MedicalRecordDto> getPatientHistory(String id);

    MedicalRecordDto get(String id);

    void delete(String id);

}
