package com.example.club.Services;

import com.example.club.FeignClient.UserClient;
import com.example.club.Models.Club;
import com.example.club.Repositories.ClubRepository;
import com.example.club.dtos.UserDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClubService {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private UserClient userClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

  /*  public void sendMessage(String message) {
        kafkaTemplate.send("haha", message);
    }*/


    private static final Logger logger = LoggerFactory.getLogger(ClubService.class);

    // Utilisation de RestTemplate pour récupérer une ressource
    public String getResourceFromOtherService() {
        String url = "http://localhost:9090/api/users";
        return restTemplate.getForObject(url, String.class);
    }

    // Utilisation de RestTemplate pour créer un utilisateur

    public UserDto createUser(UserDto userDto) {
        String url = "http://localhost:9090/api/users/add";
        ResponseEntity<UserDto> response = restTemplate.postForEntity(url, userDto, UserDto.class);
        return response.getBody();
    }



    // Utilisation de FeignClient pour récupérer un utilisateur par ID

    //@CircuitBreaker(name = "stockService", fallbackMethod = "handleGetStockFailure")
    public UserDto getUserById(String userId) {
        return userClient.getUserById(userId);
    }

    @Recover
    public UserDto handleGetStockFailure(String userId, Exception e) {
        logger.error("Failed to fetch user with userId: {}", userId, e);
        return new UserDto(); // Retourne une valeur par défaut en cas d'erreur
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long id) {
        return clubRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Club not found"));
    }

    public Club createClub(Club club) {
        return clubRepository.save(club);
    }

    public Club updateClub(Long id, Club updatedClub) {
        Club existingClub = getClubById(id);
        // Update fields as necessary
        return clubRepository.save(existingClub);
    }

    public void deleteClub(Long id) {
        clubRepository.deleteById(id);
    }
}
