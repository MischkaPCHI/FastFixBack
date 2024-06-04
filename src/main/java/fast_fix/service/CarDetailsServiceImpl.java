package fast_fix.service;

import fast_fix.domain.dto.CarDetailsDto;
import fast_fix.domain.dto.CarInsuranceCompanyDto;
import fast_fix.domain.dto.FuelStationDto;
import fast_fix.domain.entity.CarDetails;
import fast_fix.domain.entity.CarInsuranceCompany;
import fast_fix.domain.entity.User;
import fast_fix.mapping.CarDetailsMapper;
import fast_fix.mapping.CarInsuranceCompanyMapper;
import fast_fix.repository.CarDetailsRepository;
import fast_fix.repository.CarInsuranceCompanyRepository;
import fast_fix.repository.UserRepository;
import fast_fix.service.interfaces.CarDetailsService;
import fast_fix.service.interfaces.TankerkoenigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDetailsServiceImpl implements CarDetailsService {

    @Autowired
    private CarDetailsRepository carDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarInsuranceCompanyRepository carInsuranceCompanyRepository;

    @Autowired
    private TankerkoenigService tankerkoenigService;

    @Autowired
    private CarDetailsMapper carDetailsMapper;

    @Autowired
    private CarInsuranceCompanyMapper carInsuranceCompanyMapper;

    @Override
    public CarDetailsDto getCarDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return carDetailsMapper.toDto(user.getCarDetails());
    }

    @Override
    public CarDetailsDto updateFuelType(Long userId, String fuelType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CarDetails carDetails = user.getCarDetails();
        carDetails.setFuelType(fuelType);
        carDetailsRepository.save(carDetails);
        return carDetailsMapper.toDto(carDetails);
    }

    @Override
    public CarDetailsDto updateInsuranceCompany(Long userId, Long insuranceCompanyId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CarDetails carDetails = user.getCarDetails();
        CarInsuranceCompany insuranceCompany = carInsuranceCompanyRepository.findById(insuranceCompanyId)
                .orElseThrow(() -> new RuntimeException("Insurance company not found"));
        carDetails.setInsuranceCompany(insuranceCompany);
        carDetailsRepository.save(carDetails);
        return carDetailsMapper.toDto(carDetails);
    }

    @Override
    public CarDetailsDto updateLastMaintenanceDate(Long userId, LocalDate lastMaintenanceDate) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CarDetails carDetails = user.getCarDetails();
        carDetails.setLastMaintenanceDate(lastMaintenanceDate);
        carDetailsRepository.save(carDetails);
        return carDetailsMapper.toDto(carDetails);
    }

    @Override
    public List<String> getFuelTypes() {
        return tankerkoenigService.getFuelTypes();
    }

    @Override
    public List<FuelStationDto> getStationsNearby(String fuelType, double latitude, double longitude, int radius) {
        return tankerkoenigService.getStationsNearby(fuelType, latitude, longitude, radius);
    }

    @Override
    public List<CarInsuranceCompanyDto> getAllInsuranceCompanies() {
        return carInsuranceCompanyRepository.findAll()
                .stream()
                .map(carInsuranceCompanyMapper::toDto)
                .collect(Collectors.toList());
    }
}