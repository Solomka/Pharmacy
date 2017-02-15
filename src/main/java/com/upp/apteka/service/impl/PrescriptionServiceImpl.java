package com.upp.apteka.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.bo.Patient;
import com.upp.apteka.bo.Prescription;
import com.upp.apteka.bo.PrescriptionMedicine;
import com.upp.apteka.dto.ChooseMedicineDto;
import com.upp.apteka.repository.MedicineRepository;
import com.upp.apteka.repository.PrescriptionRepository;
import com.upp.apteka.service.DoctorService;
import com.upp.apteka.service.PatientService;
import com.upp.apteka.service.PrescriptionService;

@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private MedicineRepository medicineService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Override

	public List<Prescription> getAll(int offset, int limit) {
		return prescriptionRepository.getAll(offset, limit);
	}

	//@Override
	public Long create(Prescription prescription) {
		return prescriptionRepository.create(prescription);
	}

	//@Override
	public Prescription read(Long key) {
		Prescription prescription = prescriptionRepository.read(key);
		System.out.println(prescription.getPrescriptionMedicines());
		return prescription;
	}

	//@Override
	public void update(Prescription prescription) {
		prescriptionRepository.update(prescription);

	}

	//@Override
	public boolean delete(Long key) {
		return prescriptionRepository.delete(key);
	}

	//@Override
	public List<Prescription> findByQuery(String query, Date start, Date finishDate, boolean or, Boolean sold) {
		return prescriptionRepository.findByQuery(query, start, finishDate, or, sold);
	}

	//@Override
	public List<Prescription> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or,
			Boolean sold) {
		return prescriptionRepository.findByQuery(query, start, finishDate, offset, limit, or, sold);
	}

	@Override
	public Long create(Long doctorId, Long patientId, Date date, List<ChooseMedicineDto> dtos) {
		Doctor doctor = doctorService.read(doctorId);
		Patient patient = patientService.read(patientId);

		Prescription prescription = new Prescription();
		prescription.setDate(date);
		prescription.setDoctor(doctor);
		prescription.setPatient(patient);

		Long id = prescriptionRepository.create(prescription);

		prescription.setId(id);

		List<PrescriptionMedicine> set = new ArrayList<>();

		for (ChooseMedicineDto cmd : dtos) {
			PrescriptionMedicine pm = new PrescriptionMedicine();

			pm.setMedicine(medicineService.read(cmd.getMedicineId()));
			pm.setPrescription(prescription);
			pm.setPackQuantity(cmd.getQuantity());
			pm.setPackBought(0);

			set.add(pm);
		}

		prescription.setPrescriptionMedicines(set);
		prescriptionRepository.update(prescription);

		return id;
	}

}
