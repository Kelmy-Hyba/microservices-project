package com.example.club.Controllers;

import com.example.club.Models.Club;
import com.example.club.dtos.ClubDto;
import com.example.club.Services.ClubService;
import com.example.club.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping("/user/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return clubService.getUserById(userId);
    }

    @GetMapping
    public List<ClubDto> getAllClubs() {
        return clubService.getAllClubs().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @GetMapping("/{id}")
    public ClubDto getClubById(@PathVariable Long id) {
        Club club = clubService.getClubById(id);
        return convertToDto(club);
    }

    @PostMapping("/add")
    public ClubDto createClub(@RequestBody ClubDto clubDto) {
        Club club = convertToEntity(clubDto);
        Club savedClub = clubService.createClub(club);
        return convertToDto(savedClub);
    }

    @PutMapping("/{id}")
    public ClubDto updateClub(@PathVariable Long id, @RequestBody ClubDto clubDto) {
        Club club = convertToEntity(clubDto);
        Club updatedClub = clubService.updateClub(id, club);
        return convertToDto(updatedClub);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.deleteClub(id);
        return ResponseEntity.noContent().build();
    }

    // Méthode pour convertir Club en ClubDto
    private ClubDto convertToDto(Club club) {
        ClubDto clubDto = new ClubDto();
        clubDto.setId(club.getId());
        clubDto.setNom(club.getNom());
        clubDto.setTypeClub(club.getTypeClub());
        clubDto.setDescription(club.getDescription());
        // Ajoutez les autres propriétés nécessaires
        return clubDto;
    }

    // Méthode pour convertir ClubDto en Club
    private Club convertToEntity(ClubDto clubDto) {
        Club club = new Club();
        club.setId(clubDto.getId());
        club.setNom(clubDto.getNom());
        club.setTypeClub(clubDto.getTypeClub());
        club.setDescription(clubDto.getDescription());
        // Ajoutez les autres propriétés nécessaires
        return club;
    }

    // Endpoint pour créer un utilisateur
    @PostMapping("/users/add")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return clubService.createUser(userDto);
    }
}
