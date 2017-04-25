package nhl.nhlodds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class TeamSelection extends AppCompatActivity implements Database.AsyncResponse {
    public String team, abbreviation, games_played, wins, losses, rank, points, streak;
    public int logo;
    public List<String> teams, abbreviations;

    private String games_played_query, wins_query, losses_query, overtime_losses_query, rank_query, points_query, streak_query;

    public void ExecuteQueries() {
        new Database(TeamSelection.this, games_played_query).execute();
        new Database(TeamSelection.this, wins_query).execute();
        new Database(TeamSelection.this, losses_query).execute();
        new Database(TeamSelection.this, overtime_losses_query).execute();
        new Database(TeamSelection.this, rank_query).execute();
        new Database(TeamSelection.this, points_query).execute();
        new Database(TeamSelection.this, streak_query).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        teams = Arrays.asList(getResources().getStringArray(R.array.teams));
        abbreviations = Arrays.asList(getResources().getStringArray(R.array.abbreviations));

        final Button start_button = (Button)findViewById(R.id.start_button);
        start_button.setVisibility(View.INVISIBLE);

        final ProgressBar progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.INVISIBLE);

        final ArrayAdapter team_adapter = ArrayAdapter.createFromResource(this, R.array.teams, R.layout.team_spinner);
        team_adapter.setDropDownViewResource(R.layout.team_spinner_dropdown);
        final Spinner team_spinner = (Spinner) findViewById(R.id.team_spinner);
        team_spinner.setAdapter(team_adapter);
        team_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progress_bar.setVisibility(View.VISIBLE);
                team = parent.getItemAtPosition(position).toString();
                abbreviation = abbreviations.get(teams.indexOf(team));
                logo = teams.indexOf(team);

                games_played = wins = losses = rank = points = streak = "";
                games_played_query = ";current_standings;games_played;name_abbreviation='" + abbreviation + "'";
                wins_query = ";current_standings;wins;name_abbreviation='" + abbreviation + "'";
                losses_query = ";current_standings;losses;name_abbreviation='" + abbreviation + "'";
                overtime_losses_query = ";current_standings;overtime;name_abbreviation='" + abbreviation + "'";
                rank_query = ";current_standings;current_ranking;name_abbreviation='" + abbreviation + "'";
                points_query = ";current_standings;points;name_abbreviation='" + abbreviation + "'";
                streak_query = ";current_standings;streak;name_abbreviation='" + abbreviation + "'";
                ExecuteQueries();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stats = new Intent(TeamSelection.this, Stats.class);
                stats.putExtra("team", team);
                stats.putExtra("abbreviation", abbreviation);
                stats.putExtra("logo", logo);
                stats.putExtra("games_played", games_played);
                stats.putExtra("wins", wins);
                stats.putExtra("losses", losses);
                stats.putExtra("rank", rank);
                stats.putExtra("points", points);
                stats.putExtra("streak", streak);

                TeamSelection.this.startActivity(stats);
            }
        });
    }

    @Override
    public void processFinish(String output) {
        final ProgressBar progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        final Button start_button = (Button) findViewById(R.id.start_button);

        output = output.replace("(","");
        output = output.replace(")","");
        output = output.replace(",","");
        output = output.replace("'","");

        if (games_played.equals("")) {
            games_played = output;
        }
        else if (wins.equals("")) {
            wins = output;
        }
        else if (losses.equals("")) {
            losses = output;
        }
        else if (losses.length() < 4) {
            losses = losses + "-" + output;
        }
        else if (rank.equals("")) {
            rank = output;
        }
        else if (points.equals("")) {
            points = output;
        }
        else if (streak.equals("")) {
            streak = output;
            progress_bar.setVisibility(View.INVISIBLE);
            start_button.setVisibility(View.VISIBLE);
        }
    }
}
