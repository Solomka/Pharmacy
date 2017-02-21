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
import com.upp.apteka.service.PurchaseService;

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

	@Autowired
	private PurchaseService purchaseService;

	// @Override
	public List<Prescription> getAll(int offset, int limit) {
		return prescriptionRepository.getAll(offset, limit);
	}

	// @Override
	public Long create(Prescription prescription) {
		return prescriptionRepository.create(prescription);
	}

	// @Override
	public Prescription read(Long key) {
		return prescriptionRepository.read(key);
	}

	// @Override
	public void update(Prescription prescription) {
		prescriptionRepository.update(prescription);

	}

	// @Override
	public boolean delete(Long key) {
		if (purchaseService.findByPrescription(key).size() != 0)
			return false;
		return prescriptionRepository.delete(key);
	}

	// @Override
	public List<Prescription> findByQuery(String query, Date start, Date finishDate, boolean or, Boolean sold) {
		return prescriptionRepository.findByQuery(query, start, finishDate, or, sold);
	}

	// @Override
	public List<Prescription> findByQuery(String query, Date start, Date finishDate, int offset, int limit, boolean or,
			Boolean sold) {
		return prescriptionRepository.findByQuery(query, start, finishDate, offset, limit, or, sold);
	}

	// @Override
	public Long create(Long doctorId, Long patientId, Date date, List<ChooseMedicineDto> dtos) {
		Doctor doctor = doctorService.read(doctorId);
		Patient patient = patientService.read(patientId);

		Prescription prescription = new Prescription();
		prescription.setDate(date);
		prescription.setDoctor(doctor);
		prescription.setPatient(patient);

		List<PrescriptionMedicine> set = new ArrayList<PrescriptionMedicine>();

		for (ChooseMedicineDto cmd : dtos) {
			PrescriptionMedicine pm = new PrescriptionMedicine();

			pm.setMedicine(medicineService.read(cmd.getMedicineId()));
			pm.setPrescription(prescription);
			pm.setPackQuantity(cmd.getQuantity());
			pm.setPackBought(0);

			set.add(pm);
		}

		prescription.setPrescriptionMedicines(set);
		return prescriptionRepository.create(prescription);
	}

	// @Override
	public void update(Long prescriptionId, Long doctorId, Long patientId, Date date, List<ChooseMedicineDto> dtos) {
		Doctor doctor = doctorService.read(doctorId);
		Patient patient = patientService.read(patientId);

		Prescription prescription = prescriptionRepository.read(prescriptionId);
		prescription.setId(prescriptionId);
		prescription.setDate(date);
		prescription.setDoctor(doctor);
		prescription.setPatient(patient);

		List<PrescriptionMedicine> set = prescription.getPrescriptionMedicines();

		outer: for (int i = 0; i < set.size(); i++) {
			for (ChooseMedicineDto cmd : dtos)
				if (set.get(i).getMedicine().getId() == cmd.getMedicineId())
					continue outer;

			if (set.get(i).getPackBought() == 0) {
				set.remove(i);
				--i;
			}
		}

		outer: for (ChooseMedicineDto cmd : dtos) {

			for (PrescriptionMedicine pm : set) {
				if (pm.getMedicine().getId() == cmd.getMedicineId()) {

					if (cmd.getQuantity() > pm.getPackBought())
						pm.setPackQuantity(cmd.getQuantity());
					continue outer;
				}
			}

			PrescriptionMedicine pm = new PrescriptionMedicine();

			pm.setMedicine(medicineService.read(cmd.getMedicineId()));
			pm.setPrescription(prescription);
			pm.setPackQuantity(cmd.getQuantity());
			pm.setPackBought(0);

			set.add(pm);
		}

		prescriptionRepository.update(prescription);
	}

	// @Override
	public List<Prescription> getUnboughtPrescriptions(Long customerId) {
		return prescriptionRepository.getUnboughtPrescriptions(customerId);
	}

	//@Override
	public int count(String query, Date startDate, Date endDate, boolean b, Boolean sold) {
		return prescriptionRepository.count(query, startDate, endDate, b, sold);
	}

}
