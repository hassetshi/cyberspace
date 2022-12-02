package io.cyberspace.ipldashboard.controller;


import io.cyberspace.ipldashboard.model.Match;
import io.cyberspace.ipldashboard.model.Team;
import io.cyberspace.ipldashboard.repository.MatchRepository;
import io.cyberspace.ipldashboard.repository.TeamRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team")
    public Iterable<Team> getAllTeam(){
        return this.teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName){

       Team team =  this.teamRepository.findByTeamName(teamName);

       team.setMatches(matchRepository.findLatestMatchesbyTeam(teamName,4));

       return team;
    }

    @GetMapping("/team/{teamName}/matches/{year}")
    public List<Match> getMatchForTeam(@PathVariable String teamName, @PathVariable int year){
       System.out.println("team name" + teamName + "-- year " + year);
        LocalDate startDate = LocalDate.of(year,1,1);
        LocalDate endDate = LocalDate.of(year + 1,1,1);

        return this.matchRepository.getMatchesByTeamBetweenDates(
                teamName,
                startDate,
                endDate
        );

    }
}
