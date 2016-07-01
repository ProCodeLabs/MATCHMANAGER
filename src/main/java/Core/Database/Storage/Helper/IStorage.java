package Core.Database.Storage.Helper;

import Core.Data.Match;
import Core.Data.Player;
import Core.Data.Team;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IStorage
{
	CompletableFuture<Void> addPlayer( Player player );

	CompletableFuture<Void> updatePlayer( Player targetPlayer, Player newPlayer );

	CompletableFuture<Player> getPlayer( int id );

	CompletableFuture<List<Player>> getAllTeamPlayers( String teamName );


	CompletableFuture<Void> addTeam( String matchName );

	CompletableFuture<Void> addPlayerToTeam( Match match, Player user );

	CompletableFuture<Team> getTeam( String name );

	CompletableFuture<Void> updateTeam( Team targetTeam, Team newTeam );


	CompletableFuture<Void> addMatch( Team teamA, Team teamB );

	CompletableFuture<Void> endMatch( Team winner );

	CompletableFuture<Void> updateMatch( Match match );

	CompletableFuture<Match> getMatch( int id );


	CompletableFuture<List<Team>> getAllTeams();

	CompletableFuture<List<Match>> getAllMatches();

}
