package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.*;
import street.pet.repository.ChartRepository;
import street.pet.repository.ChartSearch;
import street.pet.repository.PetRepository;
import street.pet.repository.VetRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChartService {

    private final ChartRepository chartRepository;
    private final PetRepository petRepository;
    private final VetRepository vetRepository;

    /**
     * 차트 생성
     */
    @Transactional
    public Long Chart(Long petId, Long vetId) {
        //엔티티 조회
        Pet pet = petRepository.findOne(petId);
        Vet vet = vetRepository.findOne(vetId);

        //차트 생성
        Chart chart = Chart.createChart(vet, pet);
        chartRepository.save(chart);
        return chart.getId();
    }

    /**
     * 차트 조회
     */
    public Chart findOne(Long chartId) {
        return chartRepository.findOne(chartId);
    }

    public List<Chart> findAll() {
        return chartRepository.findAll();
    }

    /**
     * GET API 전용 조회 함수
     */
    public List<Chart> findByPet(Long id) {
        return chartRepository.findByPetWithPetVet(id);
    }

    public List<Chart> findByVet(Long id) {
        return chartRepository.findByVetWithPetVet(id);
    }

    public List<Chart> findById(Long id) {
        return chartRepository.findByIdWithPetVet(id);
    }

    public List<Chart> findAllWithPetVet(int offset, int limit) {
        return chartRepository.findAllWithPetVet(offset, limit);
    }

    public List<Chart> findAllWithSearch(ChartSearch chartSearch) {
        return chartRepository.findAllWithSearch(chartSearch);
    }

    /**
     * 차트 취소
     */
    @Transactional
    public void cancelChart(Long chartId) {
        Chart chart = chartRepository.findOne(chartId);
        chart.cancel();
    }

    /**
     * 차트 수정
     */
    @Transactional
    public Long updateChart(Long id, ChartStatus status) {
        Chart chart = chartRepository.findOne(id);
        chart.update(status);
        return chart.getId();
    }
}
