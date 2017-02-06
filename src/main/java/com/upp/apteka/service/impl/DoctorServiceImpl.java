package com.upp.apteka.service.impl;

import java.awt.Container;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.upp.apteka.bo.Doctor;
import com.upp.apteka.dto.DoctorDto;
import com.upp.apteka.repository.DoctorRepository;
import com.upp.apteka.service.DoctorService;
import com.upp.apteka.validator.DoctorValidator;
import com.upp.apteka.validator.ValidationError;

import javassist.NotFoundException;

@Service
public class DoctorServiceImpl implements DoctorService{
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private ApplicationContext applicationContext;

	//@Override
	public List<Doctor> getAll(int offset, int limit) {
		return doctorRepository.getAll(offset, limit);
	}

	//@Override
	public Long create(Doctor doctor) {
		return doctorRepository.create(doctor);
	}

	//@Override
	public Doctor read(Long key) {
		return doctorRepository.read(key);
	}

	//@Override
	public void update(Doctor doctor) {
		doctorRepository.update(doctor);
	}

	//@Override
	public boolean delete(Long key) {
		return doctorRepository.delete(key);
	}

	//@Override
	public List<Doctor> findByQuery(String query, boolean or) {
		return doctorRepository.findByQuery(query, or);
	}

	//@Override
	public List<Doctor> findByQuery(String query, int offset, int limit, boolean or) {
		return doctorRepository.findByQuery(query, offset, limit, or);
	}

	//@Override
	public List<ValidationError> processAdding(Container container) throws NotFoundException {
		DoctorDto doctorDto = applicationContext.getBean(DoctorDto.class);
		doctorDto.readFromContext(container);

		DoctorValidator doctorValidator = applicationContext.getBean(DoctorValidator.class);

		List<ValidationError> errors = doctorValidator.validate(doctorDto);
		doctorDto.showErrors(errors, container);

		if (errors.isEmpty())
			create(new Doctor(doctorDto));

		return errors;
	}

	//@Override
	public List<ValidationError> processEditing(Container container, Long id) throws NotFoundException {
		DoctorDto doctorDto = applicationContext.getBean(DoctorDto.class);
		doctorDto.readFromContext(container);

		DoctorValidator doctorValidator = applicationContext.getBean(DoctorValidator.class);

		List<ValidationError> errors = doctorValidator.validate(doctorDto);
		doctorDto.showErrors(errors, container);

		Doctor doctor = new Doctor(doctorDto);
		doctor.setId(id);
		
		if (errors.isEmpty())
			update(doctor);

		return errors;
	}
}
