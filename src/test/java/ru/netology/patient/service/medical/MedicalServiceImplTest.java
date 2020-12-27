package ru.netology.patient.service.medical;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MedicalServiceImplTest {

    private static PatientInfoRepository patientInfoRepository;
    private static SendAlertService sendAlertService;

    @Test
    public void checkBloodPressureWithSendingTest() {
        //given:
        PatientInfo patientInfo = new PatientInfo("1", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(patientInfo);
        sendAlertService = Mockito.mock(SendAlertService.class);
        BloodPressure bloodPressure = new BloodPressure(120, 90);
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        //when:
        medicalService.checkBloodPressure("1", bloodPressure);

        //then:
        Mockito.verify(sendAlertService, Mockito.times(1)).send(message);
    }

    @Test
    public void checkBloodPressureWithoutSendingTest() {
        //given:
        PatientInfo patientInfo = new PatientInfo("1", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(patientInfo);
        sendAlertService = Mockito.mock(SendAlertService.class);
        BloodPressure bloodPressure = new BloodPressure(120, 80);
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        //when:
        medicalService.checkBloodPressure("1", bloodPressure);

        //then:
        Mockito.verify(sendAlertService, Mockito.times(0)).send(message);
    }

    @Test
    public void checkTemperatureWithSendingTest() {
        //given:
        PatientInfo patientInfo = new PatientInfo("1", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(patientInfo);
        sendAlertService = Mockito.mock(SendAlertService.class);
        BigDecimal temperature = new BigDecimal("35");
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        //when:
        medicalService.checkTemperature("1", temperature);

        //then:
        Mockito.verify(sendAlertService, Mockito.times(1)).send(message);
    }

    @Test
    public void checkTemperatureWithoutSendingTest() {
        //given:
        PatientInfo patientInfo = new PatientInfo("1", "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("1"))
                .thenReturn(patientInfo);
        sendAlertService = Mockito.mock(SendAlertService.class);
        BigDecimal temperature = new BigDecimal("36.65");
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);
        String message = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        //when:
        medicalService.checkTemperature("1", temperature);

        //then:
        Mockito.verify(sendAlertService, Mockito.times(0)).send(message);
    }

}